$(function(){
var api = {
	page:{url:T.serverurl + "security/user/pagelist", type:"post"}
};
layui.use([ 'laypage', 'layer', 'table' ], function() {
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	table = layui.table; // 表格
	var userGroupGuid = T.p("userGroupGuid");
	$.tabfield = [
		{ checkbox : true, fixed : true }, 
		{ title: 'id', field: 'id', width: 50, fixed: 'left' },
		{ title: '登录名称', field: 'loginName', width: 100 },
		{ title: '姓名', field: 'name', width: 80 },
		{ title: '联系电话', field: 'phone', width: 150 },
		{ title: '微信号', field: 'weChat', width: 80 },			
		{ title: 'QQ号', field: 'qqCode', width: 80 },			
		{ title: '状态', field: 'state', width: 80, templet: function(d){return d.state == 1?'启用':'禁用'} },
		{ fixed : 'right', width : 170, align : 'center', toolbar : '#tabbtn'}
	];
	// 执行一个 table 实例
	table.render({
		elem : '#table',
		id : 'id',
		url : api.page.url, // 数据接口
		method : api.page.type,
		headers : {token:T.token},
		where: userGroupGuid?{"userGroupGuid":userGroupGuid}:{},
		page : true, // 开启分页
		limit: 10,
		cols : [ $.tabfield ],
		done: function(res, page, count){ 
			if($.isEmptyObject(res.data) || res.data.length == 0){
				return;
			}
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
		} else if (layEvent === 'coupons') {
			location.href = "valet_coupons.html?userGuid=" + data.guid;
		} else if (layEvent === 'couponsUpdate') {
			location.href = "couponsuser.html?userGuid=" + data.guid;
		}
	});

	$("#select").click(function() {
		table.reload('id', {where: {name:$("#selectName").val(),phone:$("#selectPhone").val()/*,type:$("#selectType").val()*/}});
	});
});
});