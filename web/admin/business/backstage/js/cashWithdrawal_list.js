$(function(){

	var api = {
		info:{url:T.serverurl + "/pay/order/info", type:"get"},
		list:{url:T.serverurl + "/pay/order/list", type:"post"},
		page:{url:T.serverurl + "/pay/order/page", type:"post"},
		listByGuids:{url:T.serverurl + "/security/user/listByGuids", type:"post"},
		cashwithdrawal:{url:T.serverurl + "/pay/order/cashwithdrawal", type:"post"}
	};


	layui.use([ 'laypage', 'layer', 'table' ], function() {
		var laypage = layui.laypage, // 分页
			layer = layui.layer, // 弹层
			table = layui.table; // 表格
		$.tabfield = [
			/*{ checkbox : true, fixed : true },*/
			{ title: 'id', field: 'id', width: 50, fixed: 'left' },
			{ title: '用户名称', field: 'userName', width: 150, templet: '<div><span name="guid-{{d.userGuid}}">{{d.userGuid}}</span></div>'},
			{ title: '名称', field: 'name', width: 150 },
			{ title: '状态', field: 'state', width: 100 ,templet: function(d){
					if(d.state == 0){
						return '失败'
					}else if(d.state == 1){
						return '成功'
					}
				}},
			{ title: '订单号', field: 'orderNumber', width: 200 },
			{ title: '提现状态', field: 'businConfi', width: 100 ,templet: function(d){
					if(d.businConfi == 1){
						return '待审核'
					}else if(d.businConfi == 2){
						return '已确认'
					}
				}},
			{ title: '流水号', field: 'serialNumber', width: 250 },
			{ title: '订单金额', field: 'orderMoney', width: 100 ,templet: function(d){ return d.orderMoney/100}},
			{ title: '创建时间', field: 'createTime', width: 200 },
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
			where: {"type":2,"orderBy":"createTime desc"},
			//where: userGroupGuid?{"userGroupGuid":userGroupGuid}:{},
			page : true, // 开启分页
			limit: 10,
			cols : [ $.tabfield ],
			done: function(res, page, count){
				if($.isEmptyObject(res.data) || res.data.length == 0){
					return;
				}
				var userGuids = new Array();
				$.each(res.data, function(i, obj){
					userGuids.push(obj.userGuid);
				});
				$.ajax({
					type:api.listByGuids.type,
					traditional: true,
					url:api.listByGuids.url,
					data : {"guids":userGuids},
					success : function(r) {
						if (r.code == 0) {
							$.each(r.data, function(i, obj){
								var tds = $("span[name=guid-" + obj.guid + "]");
								tds.each(function(){
									$(this).text(obj.name);
									res.data[$(this).parent().parent().parent().attr("data-index")].provinceName = obj.name;
								});
							});
						} else {
							alertMsg(r);
						}
					}
				});
			}
		});



		// 监听工具条
		table.on('tool(default)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
			// lay-filter="对应的值"
			var data = obj.data, // 获得当前行数据
				layEvent = obj.event; // 获得 lay-event 对应的值
			if (layEvent === 'adopt') {
				if(data.state == 1){
					alert('改用户已经审核通过')
				}else{
					layer.confirm('确认审核通过吗？', function(index) {
						$.ajax({
							type : api.cashwithdrawal.type,
							url : api.cashwithdrawal.url,
							data : {guid:data.guid},
							success : function(r) {
								if (r.code == 0) {
									alert('审核通过', function(index) {
										table.reload('id', {where:{"type":2,"orderBy":"createTime desc"}});
									});
								} else {
									alertMsg(r);
								}
							}
						});
					});
				}
			}
		});



		var form = layui.form;
		form.on("select", function(data){
			//执行重载
			table.reload('id', {where:{"type":2,"orderBy":"createTime desc"}});
			//alert(data.value); // 获取选中的值
		});
	});
});