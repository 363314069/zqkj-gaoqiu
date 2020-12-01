$(function(){
var api={
	provinceList:{url:T.serverurl + "security/area/list", type:"get"},
	provinceByGuidList:{url:T.serverurl + "security/area/listByGuids", type:"post"},
	page:{url:T.serverurl + "security/organization/page", type:"post"},
	list:{url:T.serverurl + "security/organization/list",type:"get"},
	del:{url:T.serverurl + "security/organization/del",type:"post"},
	delbyguids:{url:T.serverurl + "security/organization/delbyguids",type:"post"},
	info:{url:T.serverurl + "security/organization/info",type:"get"},
	save:{url:T.serverurl + "security/organization/save",type:"post"},
	update:{url:T.serverurl + "security/organization/update",type:"post"}
}
layui.use([ 'laypage', 'layer', 'table' ], function() {
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	table = layui.table; // 表格
	$.tabfield = [
		{ checkbox : true, fixed : true }, 
		{ title: 'id', field: 'id', width: 50, fixed: 'left' },
		//{ title: '地区', field: 'provinceName', width: 80, templet: '<div><span name="guid-{{d.provinceGuid}}">{{d.provinceGuid}}</span></div>'},
		{ title: '机构名称', field: 'name', width: 200 },			
		//{ title: '域名或关键字', field: 'key', width: 80 },
		//{ title: '服务器地址', field: 'serverUrl', width: 80 },	
		//{ title: 'Token', field: 'token', width: 80 },			
		//{ title: '登录api', field: 'loginApi', width: 80 },			
		//{ title: '回调api', field: 'callbackApi', width: 80 },			
		//{ title: '子站点', field: 'subSite', width: 80 },			
		//{ title: '机构地址', field: 'address', width: 80 },			
		{ title: '联系人', field: 'contacts', width: 100 },
		{ title: '联系电话', field: 'phone', width: 150 },
		{ title: '邮件地址', field: 'mail', width: 150 },
		{ title: '微信号', field: 'webChat', width: 150 },
		{ title: 'QQ号', field: 'qQCode', width: 150 },
		//{ title: 'logo文件', field: 'logoFile', width: 80 },			
		//{ title: '显示图标', field: 'showTitle', width: 80 },			
		//{ title: '使用CSS文件', field: 'useCSS', width: 80 },			
		//{ title: '状态', field: 'state', width: 80 ,templet: function(d){return d.state == 1?'启用':'禁用'}},			
		//{ title: '备注', field: 'remark', width: 80 },			
		{ fixed : 'right', width : 220, align : 'center', toolbar : '#tabbtn'}
	];

	// 执行一个 table 实例
	function initTable(){
		table.render({
			elem : '#table',
			id : 'id',
			url : api.page.url, // 数据接口
			method : api.page.type,
			headers : {token:T.token},
			page : true, // 开启分页
			limit: 10,
			cols : [ $.tabfield ],// 表头
			where: (guid == null || guid == "")?{}:{"provinceGuid":guid},
			done: function(res, page, count){ 
				if($.isEmptyObject(res.data) || res.data.length == 0){
					return;
				}
				var provinceGuids = new Array();
				$.each(res.data, function(i, obj){
					provinceGuids.push(obj.provinceGuid);
				});
	            $.ajax({
	            	type:api.provinceByGuidList.type,
	            	traditional: true,
	            	url:api.provinceByGuidList.url,
	            	data : {"guids":provinceGuids},
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
				//obj.del(); // 删除对应行（tr）的DOM结构
				//layer.close(index);
				// 向服务端发送删除指令
				$.ajax({
					type : api.del.type,
					url : api.del.url,
					data: {guid:data.guid},
					success : function(r) {
						if (r.code == 0) {
							alert('成功删除了' + r.count + '记录', function(index) {
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
			location.href = "organization_form.html?guid=" + data.guid;
		} else if (layEvent === 'ip') {
			location.href = "ip.html?organizationGuid=" + data.guid;
		} else if (layEvent === 'reservation') {
			location.href = "/admin/business/backstage/reservation.html?organizationGuid=" + data.guid;
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
});
});