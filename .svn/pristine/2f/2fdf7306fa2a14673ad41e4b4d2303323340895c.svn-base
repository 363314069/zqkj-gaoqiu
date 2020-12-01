$(function(){
var api={
	page:{url:T.serverurl + "security/param/page", type:"post"},
	list:{url:T.serverurl + "security/param/list",type:"get"},
	del:{url:T.serverurl + "security/param/del",type:"post"},
	delbyguids:{url:T.serverurl + "security/param/delbyguids",type:"post"},
	info:{url:T.serverurl + "security/param/info",type:"get"},
	save:{url:T.serverurl + "security/param/save",type:"post"},
	update:{url:T.serverurl + "security/param/update",type:"post"}
}
var parentId = T.p("parentId");
layui.use([ 'laypage', 'layer', 'table' ], function() {
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	table = layui.table; // 表格
	$.tabfield = [
		{ checkbox : true, fixed : true }, 
		{ title: 'id', field: 'id', width: 50, fixed: 'left' },
		{ title: '参数名称', field: 'name', width: 200 },			
		{ title: '参数Key', field: 'key', width: 120 },			
		{ title: '参数值', field: 'value', width: 120 },			
		{ title: '类型', field: 'type', width: 80 },			
		{ title: '层级', field: 'level', width: 80 },			
		{ title: '是否父节点', field: 'isParent', width: 80 },		
		{ title: '状态', field: 'state', width: 80 ,templet: function(d){return d.state == 1?'启用':'禁用'}},			
		{ title: '备注', field: 'remark', width: 80 },			
		{ fixed : 'right', width : 160, align : 'center', toolbar : '#tabbtn'}
	];

	// 执行一个 table 实例
	function initTable(){
		table.render({
			elem : '#table',
			id : 'id',
			url : api.page.url, // 数据接口
			method : api.page.type,
			headers : {token:T.token},
			where: {"parentId":parentId},
			page : true, // 开启分页
			limit: 10,
			cols : [ $.tabfield ]
		});
	}
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
					data: {guid:data.guid},
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
			location.href = "param_form.html?id=" + data.id + "&parentId=" + parentId;
		}
	});

	$("[yf-opt=cur-del]").click(function() {
		var checkStatus = table.checkStatus('id');
		var guids = $.getRows(checkStatus.data);
		if (!guids)
			return;
		confirm('确定要删除选中的记录？', function() {
			$.ajax({
				type : api.delbyguids.type,
				url : api.delbyguids.url,
				traditional: true,
				data : {guids:guids},
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
	initTable();
	$("a[yf-opt=cur-add]").attr("href","param_form.html?parentId=" + parentId);
});
});