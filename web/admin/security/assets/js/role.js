$(function(){
var api = {
	delbyguids:{url:T.serverurl + "security/role/delbyguids", type:"post"},
	del:{url:T.serverurl + "security/role/del", type:"post"},
	page:{url:T.serverurl + "security/role/page", type:"post"},
	module:{url:T.serverurl + "security/module/list", type:"post"},
	authorizedGetRole:{url:T.serverurl + "security/authorized/getrole", type:"post"},
	authorizedSaveRole:{url:T.serverurl + "security/authorized/saverole", type:"post"}
}
layui.use([ 'laypage', 'layer', 'table' ], function() {
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	table = layui.table; // 表格
	var treeObj = null;
	$.tabfield = [
		{ checkbox : true, fixed : true }, 
		{ title: 'id', field: 'id', width: 50, fixed: 'left' },
		{ title: '名称', field: 'name', width: 180 },			
		{ title: '角色类型', field: 'type', width: 90 },			
		{ title: '状态', field: 'state', width: 80, templet: function(d){return d.state == 1?'启用':'禁用'}},			
		{ fixed : 'right', width : 210, align : 'center', toolbar : '#tabbtn'}
	];
	// 执行一个 table 实例
	table.render({
		elem : '#table',
		id : 'id',
		url : api.page.url, // 数据接口
		method : api.page.type,
		headers : {token:T.token},
		page : true, // 开启分页
		limit: 10,
		cols : [ $.tabfield ],// 表头
		done: function(res, page, count){  
            
		}         
	});

	// 监听工具条
	table.on('tool(default)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data, // 获得当前行数据
		layEvent = obj.event; // 获得 lay-event 对应的值
		if (layEvent === 'detail') {
			var div = $("<div>");
			for(var i = 0; i < $.tabfield.length; i++){
				if($.tabfield[i].title && $.tabfield[i].title != "id" && $.tabfield[i].title != "guid"){
					var field = $("<div>");
					field.append($.tabfield[i].title + ":");
					if($.tabfield[i].field == "state")
						field.append(data[$.tabfield[i].field]==1?'启用':'禁用');
					else
						field.append(data[$.tabfield[i].field]?data[$.tabfield[i].field]:"");
					div.append(field);
				}
			}
			alert(div.html());
		} else if (layEvent === 'del') {
			layer.confirm('真的删除行么', function(index) {
				$.ajax({
					type : api.del.type,
					url : api.del.url,
					data : {id:data.id},
					success : function(r) {
						if (r.code == 0) {
							alert('成功删除了' + r.count + '记录', function(index) {
								table.reload('id', {});
							});
						} else {
							alert(r.msg);
						}
					}
				});
			});
		} else if (layEvent === 'edit') {
			location.href = "role_form.html?id=" + data.id;
		} else if (layEvent === 'addmodule') {
			//location.href = encodeURI("moduleauthmap_add.html?roleGuid=" + data.guid + "&name=" + data.name);
			$.post(api.authorizedGetRole.url, {guid:data.guid}, function(r) {
				if(r.code != 0){
					alert(r.msg);
					return;
				}
				var guids = {};
				$.each(r.data, function(i, obj) {
					guids[obj.moduleGuid] = true;
				});
				var nodes = treeObj.transformToArray(treeObj.getNodes());
				$.each(nodes, function(i, node) {
					if(guids[node.guid]){
						treeObj.checkNode(node, true, false );
					} else {
						treeObj.checkNode(node, false, false );
					}
				});
				layer.open({
					type: 1,
					title:data.name + '权限',
					btn: ['提交'],
					btnAlign: 'c',
					area: ['500px', '600px'],
					content: $('#zTree'),
					cancel: function(){ 
						$("#zTree").hide();
					},
					yes: function(index, layero){
						//按钮【按钮一】的回调
						layer.close(index);
						$("#zTree").hide();
						addmodule(data);
					}
				});
			});
		} else if(layEvent === 'userset') {
			location.href = 'roleuser.html?roleGuid=' + data.guid
		}
	});

	$("#del").click(function() {
		var checkStatus = table.checkStatus('id');
		var guids = $.getRows(checkStatus.data);
		if (!guids)
			return;
		confirm('确定要删除选中的记录？', function() {
			$.ajax({
				type : api.delbyguids.type,
				url : api.delbyguids.url,
				data : {guids:guids},
				traditional: true,
				success : function(r) {
					if (r.code == 0) {
						alert('成功删除了' + r.count + '记录', function(index) {
							table.reload('id', {});
						});
					} else {
						alert(r.msg);
					}
				}
			});
		});
	});
	
	var setting = {
		view: {
			showIcon: false
		},
		check: {
			enable: true,
			chkboxType :{ "Y" : "ps", "N" : "ps" }
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId: -1
			},
			key: {
				name: "name"
			}
		}
	};

	$.post(api.module.url, 
		{orderBy:"sort"},
		function(r) {
			if(r.code != 0){
				alert(r.msg);
				return;
			}
			r.data.unshift({id:0, parentId:-1, name:'权限结构', open:true, isEditBtn:true, isRemoveBtn:true})
			$.usergroupList = r.data;
			treeObj = $.fn.zTree.init($("#zTree"), setting, r.data);
			//treeObj.expandAll(true);
			var nodes = treeObj.getNodes()[0].children;
			if(nodes)
				for (var i = 0; i < nodes.length; i++) {
					treeObj.expandNode(nodes[i], true);
				}
	});
	
	function addmodule(data){
		var nodes = treeObj.getCheckedNodes(true);
		var guids = new Array();
		$.each(nodes, function (index, obj){
			if(obj.guid)
				guids.push(obj.guid);
		});
		$.ajax({
			type : api.authorizedSaveRole.type,
			url : api.authorizedSaveRole.url,
			data : {sourceGuid:data.guid, moduleguids : guids.join()},
			success : function(r) {
				if(r.code != 0){
					alert(r.msg);
					return;
				}
			}
		});
	}
});
});