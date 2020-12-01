$(function(){
var organizationGuid = T.p("organizationGuid");
var api = {
	list:{url:T.serverurl + "security/ip/list", type:"post"},
	info:{url:T.serverurl + "security/ip/info", type:"get"},
	del:{url:T.serverurl + "security/ip/del", type:"post"},
	delbyguids:{url:T.serverurl + "security/ip/delbyguids", type:"post"},
	page:{url:T.serverurl + "security/ip/page", type:"post"}
}
layui.use([ 'laypage', 'layer', 'table' ], function() {
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	table = layui.table; // 表格
	$.tabfield = [
		{ checkbox : true, fixed : true }, 
		{ title: '主键', field: 'id', width: 60, fixed: 'left' },
		{ title: '机构GUID', field: 'organizationGuid', width: 180 },			
		{ title: '起始ip', field: 'startIp', width: 180 },			
		{ title: '结束IP', field: 'endIp', width: 180 },			
		{ title: '掩码', field: 'mask', width: 180 },			
		//{ title: 'ip类型', field: 'type', width: 180 },
		{ title: '状态', field: 'state', width: 60, templet: function(d){return d.state == 1?'启用':'禁用'} },
		{ title: '创建者', field: 'creator', width: 120 },
		{ title: '创建时间', field: 'createTime', width: 180 },			
		{ title: '审核时间', field: 'checkTime', width: 180 },			
		{ fixed : 'right', width : 160, align : 'center', toolbar : '#tabbtn'}
	];
	// 执行一个 table 实例
	table.render({
		elem : '#table',
		id : 'id',
		url : api.page.url, // 数据接口
		method : api.page.type,
		headers : {token:T.token},
		page : true, // 开启分页
		where: organizationGuid?{"organizationGuid":organizationGuid}:{},
		limit: T.pagesize,
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
				//obj.del(); // 删除对应行（tr）的DOM结构
				//layer.close(index);
				// 向服务端发送删除指令
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
							alertMsg(r);
						}
					}
				});
			});
		} else if (layEvent === 'edit') {
			location.href = "ip_form.html?guid=" + data.guid;
		}
	});

	$('[yf-opt="batchdel"]').click(function() {
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
						alertMsg(r);
					}
				}
			});
		});
	});
	$('[yf-opt="add"]').click(function() {
		$(this).attr("href", "ip_form.html?organizationGuid=" + organizationGuid);
	});
});
});