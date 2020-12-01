$(function(){
	var organizationGuid = T.p("organizationGuid");
var api = {
	list:{url:T.serverurl + "business/reservation/list", type:"post"},
	info:{url:T.serverurl + "business/reservation/info", type:"get"},
	del:{url:T.serverurl + "business/reservation/del", type:"post"},
	delbyguids:{url:T.serverurl + "business/reservation/delbyguids", type:"post"},
	page:{url:T.serverurl + "business/reservation/page", type:"post"},
	selectAdmin:{url:T.serverurl + "security/user/selectAdmin", type:"post"},
	bindingReservation:{url:T.serverurl + "security/user/bindingReservation", type:"post"}
}
layui.use([ 'laypage', 'layer', 'table' ], function() {
	var form = layui.form;
	var element = layui.element;
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	table = layui.table; // 表格
	$.tabfield = [
		{ checkbox : true, fixed : true }, 
		{ title: '流水号', field: 'id', width: 60, fixed: 'left' },
		{ title: '名称', field: 'name', width: 180 },
		{ title: '联系电话', field: 'phone', width: 180 },			
		{ title: '价格', field: 'price', width: 180 ,templet: function (d) { return d.price/100 }},
		/*{ title: '预定天数', field: 'days', width: 180 },*/
		{ title: '状态', field: 'state', width: 60, templet: function(d){return d.state == 1?'启用':'禁用'} },
		{ title: '开始时间', field: 'startTime', width: 180 },
		{ title: '结束时间', field: 'endTime', width: 180 },			
		{ fixed : 'right', width : 200, align : 'center', toolbar : '#tabbtn'}
	];
	// 执行一个 table 实例
	table.render({
		elem : '#table',
		id : 'id',
		url : api.page.url, // 数据接口
		method : api.page.type,
		where : {organizationGuid : organizationGuid},
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
			location.href = "reservation_form.html?guid=" + data.guid;
		} else if (layEvent === 'introduction') {
			location.href = "introduction.html?reservationGuid=" + data.guid;
		} else if (layEvent === 'addmodule') {
			addmodule(data);
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
						alertMsg(r);
					}
				}
			});
		});
	});

	//绑定管理人员
	function addmodule(data){
		//查询所有管理人员列表
		$.ajax({
			type : api.selectAdmin.type,
			url : api.selectAdmin.url,
			success : function(r) {
				console.log(r)
				if(r.code == 0 && r.data){
					//var forms = $('<form class="layui-form">');
					//var div = $('<div class="layui-form-item">');
					var forms = '<form class="layui-form"><input style="display: none" name="guid" value="'+data.guid+'">';
					forms += '<div class="layui-form-item" id="adminUserList">';

					$.each(r.data,function (i, obj) {
						//div.append('<input type="radio" name="sex" value="1" title="女">');
						if(isNull(obj.reservationGuid)){
							if(obj.reservationGuid == data.guid){
								forms += '<input type="radio" name="adminUser" value="'+obj.guid+'" title="'+obj.name+'" checked>';
							}
						}else{
							forms += '<input type="radio" name="adminUser" value="'+obj.guid+'" title="'+obj.name+'">';
						}
					})
					forms += '</div></from>';
					//forms.append(div);
					layer.open({
						type: 1,
						title:"设置管理员",
						skin: 'layui-layer-rim', //加上边框
						area: ['600px', '600px'], //宽高
						btn: ['保存','关闭'], //按钮
						content: forms,
						yes: function(index, layero){
							var adminUserGuid = $('#adminUserList input[name="adminUser"]:checked ').val();
							if(isNull(adminUserGuid)){
								$.ajax({
									type : api.bindingReservation.type,
									url : api.bindingReservation.url,
									data : {reservationGuid:data.guid, userGuid : adminUserGuid},
									success : function(r) {
										if(r.code == 0){
											alert("绑定成功！");
										}else{
											alert(r.msg);
										}
									}
								});
							}else{
								alert("请选择管理人员！");
							}
							layer.close(index);
						}
					});
					form.render();
				} else {
					alertMsg(r);
				}
			}
		});


		/*var ss = '<form class="layui-form"><div class="layui-form-item"><label class="layui-form-label">单选框</label>'+
			'<div class="layui-input-block"><input type="radio" name="sex" value="0" title="男">'+
			'<input type="radio" name="sex" value="1" title="女" checked></div></div><div class="layui-form-item">\n' +
			'    <div class="layui-input-block">\n' +
			'      <button class="layui-btn" lay-submit lay-filter="*">立即提交</button>\n' +
			'      <button type="reset" class="layui-btn layui-btn-primary">重置</button>\n' +
			'    </div>\n' +
			'  </div></form>';*/


		/*layer.open({
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
		});*/
	}
});

});
