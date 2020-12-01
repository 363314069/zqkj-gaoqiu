$(function(){
var api = {
	del:{url:T.serverurl + "security/user/del", type:"post"},
	save:{url:T.serverurl + "security/user/save", type:"post"},
	update:{url:T.serverurl + "security/user/update", type:"post"},
	info:{url:T.serverurl + "security/user/info", type:"get"},
	list:{url:T.serverurl + "security/user/list", type:"post"},
	page:{url:T.serverurl + "security/user/pagelist", type:"post"},
	delbyguids:{url:T.serverurl + "security/user/delbyguids", type:"post"},
	usergroupList:{url:T.serverurl + "security/usergroup/list", type:"post"},
	usergroupListByGuids:{url:T.serverurl + "security/usergroup/listByGuids", type:"post"},
    userextendListByGuids:{url:T.serverurl + "integral/userextend/listByGuids", type:"post"},
	moduleList:{url:T.serverurl + "security/module/list", type:"post"},
	authorizedGetRole:{url:T.serverurl + "security/authorized/getrole", type:"post"},
	authorizedSaveRole:{url:T.serverurl + "security/authorized/saverole", type:"post"},
};
layui.use([ 'laypage', 'layer', 'table' ], function() {
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	table = layui.table; // 表格
	var userGroupGuid = T.p("userGroupGuid");
	$.tabfield = [
		{ checkbox : true, fixed : true }, 
		{ title: 'id', field: 'id', width: 50, fixed: 'left' },
		//{ title: '应用程序ID', field: 'appId', width: 80 },			
		{ title: '登录名称', field: 'loginName', width: 100 },			
		{ title: '分组', field: 'userGroupName', width: 150, templet: '<div><span id="guid-{{d.userGroupGuid}}">{{d.userGroupGuid}}</span></div>'},
        /*{ title: '佣金', field: 'userGuid', width: 150, templet: '<div><span id="userGuid-{{d.guid}}"></span></div>'},*/
		{ title: '姓名', field: 'name', width: 80 },			
		{ title: '联系电话', field: 'phone', width: 150 },
		{ title: '微信号', field: 'weChat', width: 80 },			
		{ title: 'QQ号', field: 'qqCode', width: 80 },			
		{ title: '状态', field: 'state', width: 80, templet: function(d){return d.state == 1?'启用':'禁用'} },
		{ title: '用户类型', field: 'state', width: 200, templet: function(d){
			if(d.type == 0){
				return '普通用户';
			}else if(d.type == 1){
				return '分销商';
			}else if(d.type == 2){
				return '管理员、分销商';
			}else if(d.type == 3){
				return '管理员';
			}else if(d.type == 4){
				return '普通用户、管理员';
			}else if(d.type == 5){
				return '普通用户、分销商';
			}else if(d.type == 6){
				return '普通用户、分销商、管理员';
			}
		}},
		{ fixed : 'right', width : 170, align : 'center', toolbar : '#tabbtn'}
	];
	// 执行一个 table 实例
	table.render({
		elem : '#table',
		id : 'id',
		url : api.page.url, // 数据接口
		method : api.page.type,
		//contentType : 'application/json',
		headers : {token:T.token},
		//where: {"limit":"10"},
		where: userGroupGuid?{"userGroupGuid":userGroupGuid}:{},
		page : true, // 开启分页
		limit: 10,
		cols : [ $.tabfield ],
		done: function(res, page, count){ 
			if($.isEmptyObject(res.data) || res.data.length == 0){
				return;
			}
			var userGroupGuids = new Array();
			$.each(res.data, function(i, obj){
				if(obj.userGroupGuid)
					userGroupGuids.push(obj.userGroupGuid);
			});
			if(userGroupGuids.length == 0){
				return;
			}else{
                $.ajax({
                    type:api.usergroupListByGuids.type,
                    traditional: true,
                    url:api.usergroupListByGuids.url,
                    data : {"guids":userGroupGuids},
                    success : function(r) {
                        if (r.code == 0) {
                            $.each(r.data, function(i, obj){
                                $("#guid-" + obj.guid).text(obj.name);
                                res.data[$("#guid-" + obj.guid).parent().parent().parent().attr("data-index")].userGroupName = obj.name;
                            });
                        } else {
                            alertMsg(r);
                        }
                    }
                });
			}
            /*var userGuids = new Array();
            $.each(res.data, function(i, obj){
                if(obj.guid)
                    userGuids.push(obj.guid);
            });
            if(userGuids.length == 0){
                return;
            }else{
                $.ajax({
                    type:api.userextendListByGuids.type,
                    traditional: true,
                    url:api.userextendListByGuids.url,
                    data : {"guids":userGuids},
                    success : function(r) {
                        if (r.code == 0) {
                            $.each(r.data, function(i, obj){
                                $("#userGuid-" + obj.userGUID).text(obj.extLong/100);
                            });
                        } else {
                            alertMsg(r);
                        }
                    }
                });
            }*/
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
				//obj.del(); // 删除对应行（tr）的DOM结构
				//layer.close(index);
				// 向服务端发送删除指令
				$.ajax({
					type : api.del.type,
					url : api.del.url,
					data : {guid : data.guid},
					success : function(r) {
						if (r.code == 0) {
							alert('成功删除了' + r.data + '记录', function(index) {
								table.reload('id', {});
								// obj.del(); // 删除对应行（tr）的DOM结构
							});
						} else {
							alert(r.msg);
						}
					}
				});
			});
		} else if (layEvent === 'edit') {
			location.href = "user_form.html?id=" + data.id;
		} else if (layEvent === 'addmodule') {
			addmodule(data);
		}if (layEvent === 'detail') {
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
		}
	});

	$("#select").click(function() {
		table.reload('id', {where: {name:$("#selectName").val(),phone:$("#selectPhone").val()/*,type:$("#selectType").val()*/}});
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
				data : {guids : guids},
				traditional: true,
				success : function(r) {
					if (r.code == 0) {
						alert('成功删除了' + r.data + '记录', function(index) {
							table.reload('id', {});
						});
					} else {
						alert(r.msg);
					}
				}
			});
		});
	});
	$("#add").click(function(){
		$(this).attr("href", "user_form.html?userGroupGuid=" + userGroupGuid);
	});
	var treeObj = null;
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
	
	$.post(api.moduleList.url, {orderBy:"sort"}, function(r) {
		if(r.code != 0){
			alert(r.msg);
			return;
		}
		r.data.unshift({id:0, parentId:-1, name:'权限结构', open:true, isEditBtn:true, isRemoveBtn:true})
		treeObj = $.fn.zTree.init($("#zTree"), setting, r.data);
		//treeObj.expandAll(true);
		var nodes = treeObj.getNodes()[0].children;
		if(nodes)
			for (var i = 0; i < nodes.length; i++) {
				treeObj.expandNode(nodes[i], true);
			}
	});
	
	function addmodule(data){
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
	}
});
});