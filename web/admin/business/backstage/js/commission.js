$(function(){
	var api = {
		page:{url:T.serverurl + "integral/userextend/page", type:"post"},
		userListByGuids:{url:T.serverurl + "security/user/listByGuids", type:"post"},
		userCommission:{url:T.serverurl + "integral/commission/page", type:"post"},
		orderList:{url:T.serverurl + "pay/order/listByGuids", type:"post"}
	};
	layui.use([ 'laypage', 'layer', 'table' ], function() {
		var laypage = layui.laypage, // 分页
			layer = layui.layer, // 弹层
			table = layui.table; // 表格
		//var userGroupGuid = T.p("userGroupGuid");
		$.tabfield = [
			/*{ checkbox : true, fixed : true },*/
			{ title: 'id', field: 'id', width: 50, fixed: 'left' },
			//{ title: '应用程序ID', field: 'appId', width: 80 },
			{ title: '用户名称', field: 'userGUID', width: 300, templet: '<div><span id="userGUID-{{d.userGUID}}">{{d.userGUID}}</span></div>' },
			{ title: '类型', field: 'type', width: 100 ,templet: function(d){
					if(d.type == 1){
						return '佣金'
					}else if(d.type == 2){
						return '积分'
					}else if(d.type == 3){
						return '等级'
					}else{
						return ''
					}
				}},
			{ title: '未结算金额', field: 'extLong', width: 150,templet: function(d){ return d.extLong/100} },
			{ title: '累计金额', field: 'extLong', width: 150,templet: function(d){ return d.extLong/100} },
			{ fixed : 'right', width : 170, align : 'center', toolbar : '#tabbtn'}
		];


		$.tabcommission =
			[{
				field: 'createTime', //字段名
				title: '产生时间', //标题
				width: 200,
				sort: false //是否允许排序 默认：false
				//fixed: 'left' //固定列
			}, {
				field: 'integral', //字段名
				title: '佣金', //标题
				width: 100,
				sort: false, //是否允许排序 默认：false
				templet: function(d){ return d.integral/100}
				//fixed: 'left' //固定列
			}, {
				field: 'sourceGuid', //字段名
				title: '产品名称', //标题
				width: 300,
				sort: false, //是否允许排序 默认：false
				templet: '<div><span name="sourceGuid-{{d.sourceGuid}}"></span></div>'
				//fixed: 'left' //固定列
			}];


		// 执行一个 table 实例
		table.render({
			elem : '#table',
			id : 'id',
			url : api.page.url, // 数据接口
			method : api.page.type,
			//contentType : 'application/json',
			headers : {token:T.token},
			//where: {"limit":"10"},
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
                	userGuids.push(obj.userGUID);
                });
                if(userGuids.length == 0){
                    return;
                }
				$.ajax({
					type:api.userListByGuids.type,
					traditional: true,
					url:api.userListByGuids.url,
					data : {"guids":userGuids},
					success : function(r) {
						if (r.code == 0) {
							$.each(r.data, function(i, obj){
								$("#userGUID-" + obj.guid).text(obj.name);
								//res.data[$("#userGUID-" + obj.guid).parent().parent().parent().attr("data-index")].userGUID = obj.name;
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
			}else if (layEvent === 'detailed') {
				layui.use(['table', 'form'], function() {
					layer.open({
						type: 1,
						title: '佣金详细',
						area: ['700px', '500px'], //宽高
						content: $('#openProductBox'),
						end : function() {
							$("#openProductBox").hide();
						},
						success: function() {
							table.render({
								elem : '#commission',
								url : api.userCommission.url, // 数据接口
								method : api.userCommission.type,
								//contentType : 'application/json',
								headers : {token:T.token},
								where: {"creator":data.userGUID},
								page : true, // 开启分页
								limit: 10,
								cols: [$.tabcommission],
								done: function(res, page, count){
									if($.isEmptyObject(res.data) || res.data.length == 0){
										return;
									}
									var sourceGuid = new Array();
									$.each(res.data, function(i, obj){
										sourceGuid.push(obj.sourceGuid);
									});
									$.ajax({
										type:api.orderList.type,
										traditional: true,
										url:api.orderList.url,
										data : {"guids":sourceGuid},
										success : function(r) {
											if (r.code == 0) {
												$.each(r.data, function(i, obj){
													var tds = $("span[name=sourceGuid-" + obj.guid + "]");
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
						}
					});
				});
			}
		});
	});
});