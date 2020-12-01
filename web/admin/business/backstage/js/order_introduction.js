$(function(){
	var api = {
		info:{url:T.serverurl + "/pay/order/info", type:"get"},
		list:{url:T.serverurl + "/pay/order/list", type:"post"},
		page:{url:T.serverurl + "/pay/order/page", type:"post"},
		listByGuids:{url:T.serverurl + "/security/user/listByGuids", type:"post"},
		introductionList:{url:T.serverurl + "/business/introduction/list", type:"post"},
		reservationList:{url:T.serverurl + "/business/reservation/list", type:"post"},
		businconfi:{url:T.serverurl + "/pay/order/businconfi", type:"post"}
	};


	$.ajax({
		type:api.reservationList.type,
		traditional: true,
		url:api.reservationList.url,
		async:false,
		data : {"organizationGuid":T.usermsg.organizationGuid},
		success : function(r) {
			if (r.code == 0) {
				$.each(r.data, function(i, obj){
					$('#reservationSelect').append('<option value="'+obj.guid+'">'+obj.name+'</option>');
					if(i == 0){
						introductionList(obj.guid);
					}
				});
				layui.form.render('select');
			} else {
				alertMsg(r);
			}
		}
	});

	//加载场地列表
	function introductionList(reservationGuid){
		$('#introductionSelect').empty();
		$.ajax({
			type:api.introductionList.type,
			traditional: true,
			url:api.introductionList.url,
			async:false,
			data : {"reservationGuid":reservationGuid},
			success : function(r) {
				if (r.code == 0) {
					$.each(r.data, function(i, obj){
						$('#introductionSelect').append('<option value="'+obj.guid+'">'+obj.name+'</option>');
					});
					layui.form.render('select');
				} else {
					alertMsg(r);
				}
			}
		});
	}



	layui.use([ 'laypage', 'layer', 'table' ], function() {
		var laydate = layui.laydate;
		var laypage = layui.laypage, // 分页
			layer = layui.layer, // 弹层
			table = layui.table; // 表格
		$.tabfield = [
			/*{ checkbox : true, fixed : true },*/
			{ title: 'id', field: 'id', width: 50, fixed: 'left' },
			//{ title: '邀请人', field: 'inviterGuid', width: 150, templet: '<div><span name="inviterGuid-{{d.inviterGuid}}"></span></div>'},
			{ title: '购买人', field: 'userName', width: 150, templet: '<div><span name="guid-{{d.userGuid}}">{{d.userGuid}}</span></div>'},
			{ title: '名称', field: 'name', width: 300 },
            { title: '商家确认状态', field: 'businConfi', width: 100 ,templet: function(d){
                    if(d.businConfi == 1){
                        return '待确认'
                    }else if(d.businConfi == 2){
                        return '已确认'
                    }else if(d.businConfi == 3){
                        return '商家取消'
                    }else if(d.businConfi == 4){
                        return '商家修改'
                    }else if(d.businConfi == 5){
						return '商家修改用户确认'
					}
                }},
			{ title: '支付状态', field: 'state', width: 100 ,templet: function(d){
					if(d.state == 0){
						return '未支付'
					}else if(d.state == 1){
						return '已支付'
					}else if(d.state == 2){
						return '用户已取消'
					}
				}},
            { title: '备注', field: 'remark', width: 200 },
			{ title: '订单号', field: 'orderNumber', width: 200 },
			{ title: '流水号', field: 'serialNumber', width: 200 },
			{ title: '订单金额', field: 'orderMoney', width: 100 ,templet: function(d){ return d.orderMoney/100}},
			{ title: '支付时间', field: 'payTime', width: 100 },
			{ fixed : 'right', width : 200, align : 'center', toolbar : '#tabbtn'}
		];
		// 执行一个 table 实例
		table.render({
			elem : '#table',
			id : 'id',
			url : api.page.url, // 数据接口
			method : api.page.type,
			//contentType : 'application/json',
			headers : {token:T.token},
			where: {"goodsGuid":$("select[name='introductionSelect']").val(),"orderBy":"createTime desc"},
			//where: userGroupGuid?{"userGroupGuid":userGroupGuid}:{},
			page : true, // 开启分页
			limit: 10,
			cols : [ $.tabfield ],
			done: function(res, page, count){
				if($.isEmptyObject(res.data) || res.data.length == 0){
					return;
				}
				var userGuids = new Array();
				//var inviterGuids = new Array();
				$.each(res.data, function(i, obj){
					userGuids.push(obj.userGuid);
					//inviterGuids.push(obj.inviterGuid);
				});

				//加载购买人
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

				//加载邀请人
				/*$.ajax({
					type:api.listByGuids.type,
					traditional: true,
					url:api.listByGuids.url,
					data : {"guids":inviterGuids},
					success : function(r) {
						if (r.code == 0) {
							$.each(r.data, function(i, obj){
								var tds = $("span[name=inviterGuid-" + obj.guid + "]");
								tds.each(function(){
									$(this).text(obj.name);
									res.data[$(this).parent().parent().parent().attr("data-index")].provinceName = obj.name;
								});
							});
						} else {
							alertMsg(r);
						}
					}
				});*/
			}
		});

		var form = layui.form;
		form.on("select(reservationSelect)", function(data){
			introductionList(data.value);
			//执行重载
			table.reload('id', {where:{"goodsGuid":$("select[name='introductionSelect']").val()}});
		});
		form.on("select(introductionSelect)", function(data){
			//执行重载数据列表
			table.reload('id', {where:{"goodsGuid":data.value}});
		});


		// 监听工具条
		table.on('tool(default)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
			// lay-filter="对应的值"
			var data = obj.data, // 获得当前行数据
				layEvent = obj.event; // 获得 lay-event 对应的值
			if (layEvent === 'edit') {
				var div = $("<div>");

				var divDate = $('<div class="layui-form-item">');
				//divDate.append('<label class="layui-form-label">日期</label>');
				//var divDate_1 = $('<div class="layui-input-block">');
				//divDate_1.append('<input type="text" class="layui-input" name="updateDate" id="updateDate" lay-verify="required" value="'+data.date+'" placeholder="日期" autocomplete="off"/>');
				//divDate.append(divDate_1);

				var divTime = $('<div class="layui-form-item">');
				divTime.append('<label class="layui-form-label">时间</label>');
				var divTime_1 = $('<div class="layui-input-block">');
				divTime_1.append('<input type="text" class="layui-input" name="updateTime" id="updateTime" lay-verify="required" value="'+data.time+'" placeholder="时间" autocomplete="off"/>');
				divTime.append(divTime_1);

				var divBut = $('<div class="layui-form-item">');
				var divBut_1 = $('<div class="layui-input-block">');
				divBut_1.append('<button type="button" class="layui-btn" onclick="businconfi(\''+data.id+'\',2)">确认订单</button>');
				divBut_1.append('<button type="button" class="layui-btn layui-btn-warm" onclick="businconfi(\''+data.id+'\',3)">取消订单</button>');
				divBut_1.append('<button type="button" class="layui-btn layui-btn-normal" onclick="businconfi(\''+data.id+'\',4,\''+data.date+'\',\''+data.time+'\')">修改订单</button>');
				divBut.append(divBut_1);

				$(div).append(divDate).append(divTime).append(divBut);
				//页面层
				layer.open({
					type: 1,
					skin: 'layui-layer-rim', //加上边框
					area: ['420px', '240px'], //宽高
					content: div.html()
				});

				laydate.render({
					elem: '#updateTime'
					,type: 'time'
					,format: 'HH:mm'
				});

				/*laydate.render({
					elem: '#updateDate' //或 elem: document.getElementById('test')、elem: lay('#test') 等
				});*/
			}
		});


	});
});

//商家确认
function businconfi(id,businConfi) {
	if(businConfi == 4){
		if(/*!isNull($("#updateDate").val()) &&*/ !isNull($("#updateTime").val())){
			layer.msg('修改的时间不能为空');
			return false;
		}
	}
	$.ajax({
		type:'post',
		url:T.serverurl + "/pay/order/businconfi",
		data : JSON.stringify({"id":id,"businConfi":businConfi,/*"date":$("#updateDate").val(),*/"time":$("#updateTime").val()}),
		contentType:'application/json;charset=utf-8',
		success : function(r) {
			if (r.code == 0) {
				layer.msg('成功');
			} else {
				alertMsg(r);
			}
		}
	});
}