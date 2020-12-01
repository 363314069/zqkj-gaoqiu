$(function(){
var api = {
	list:{url:T.serverurl + "business/coupons/list", type:"post"},
	info:{url:T.serverurl + "business/coupons/info", type:"get"},
	del:{url:T.serverurl + "business/coupons/del", type:"post"},
	delbyguids:{url:T.serverurl + "business/coupons/delbyguids", type:"post"},
	page:{url:T.serverurl + "business/coupons/pagelist", type:"post"}
}
layui.use([ 'laypage', 'layer', 'table' ], function() {
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	table = layui.table; // 表格
	$.tabfield = [
		{ checkbox : true, fixed : true }, 
		{ title: '流水号', field: 'id', width: 60, fixed: 'left' },
		{ title: '名称', field: 'name', width: 180 },
		{ title: '面值金额', field: 'faceValue', width: 180, templet: function(d){return d.faceValue/100}  },
		{ title: '满多少可以使用', field: 'moneyConform', width: 180, templet: function(d){return d.moneyConform/100}  },
		{ title: '面值折扣', field: 'faceValueDiscount', width: 180 },			
		{ title: '开始时间', field: 'startTime', width: 180 },			
		{ title: '结束时间', field: 'endTime', width: 180 },			
		{ title: '价格', field: 'price', width: 180, templet: function(d){return d.price/100}  },
		{ title: '状态', field: 'state', width: 60, templet: function(d){return d.state == 1?'启用':'禁用'} },
		{ title: '发布数量', field: 'seleaseNumber', width: 180 },			
		{ title: '领取量', field: 'receiveAmount', width: 180 },			
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
				if($.tabfield[i].title && $.tabfield[i].title != "id" && $.tabfield[i].title != "GUID"){
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
			location.href = "coupons_form.html?guid=" + data.guid;
		}
	});

	$("#select").click(function() {
		table.reload('id', {where: {"name":$("#selectName").val()}});
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
						alertMsg(r);
					}
				}
			});
		});
	});
});
});