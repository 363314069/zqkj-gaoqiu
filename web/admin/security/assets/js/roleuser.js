$(function(){
var api = {
	pagebyroleguid:{url:T.serverurl + "security/user/pagebyroleguid", type:"post"},
	userDelByGuids:{url:T.serverurl + "security/user/delbyguids", type:"post"},
	delByUserGuids:{url:T.serverurl + "security/usermap/delbyuserguids", type:"post"},
	usergroupList:{url:T.serverurl + "security/usergroup/list", type:"post"},
	roleList:{url:T.serverurl + "security/role/list", type:"post"},
	usergroupListByGuids:{url:T.serverurl + "security/usergroup/listByGuids", type:"post"},
	moduleauthmapGetRole:{url:T.serverurl + "security/moduleauthmap/getrole", type:"post"},
	moduleauthmapSaveRole:{url:T.serverurl + "security/moduleauthmap/saverole", type:"post"}
};
layui.use([ 'laypage', 'layer', 'table' ], function() {
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	table = layui.table; // 表格
	var groupList = {};
	var roleGuid = T.p('roleGuid');
	$.tabfield = [
		{ checkbox : true, fixed : true }, 
		{ title: 'id', field: 'id', width: 50, fixed: 'left' },
		{ title: '应用程序ID', field: 'appId', width: 80 },
		{ title: '登录名称', field: 'loginName', width: 100 },
		{ title: '分组', field: 'userGroupName', width: 180, templet: '<div><span id="guid-{{d.userGroupGuid}}">{{d.userGroupGuid}}</span></div>'},
		{ title: '姓名', field: 'name', width: 80 },
		{ title: '联系人', field: 'contacts', width: 80 },			
		{ title: '联系电话', field: 'phone', width: 80 },				
		{ title: '状态', field: 'state', width: 80, templet: function(d){return d.state == 1?'启用':'禁用'}},			
		{ fixed : 'right', width : 170, align : 'center', toolbar : '#tabbtn'}
	];
	// 执行一个 table 实例
	function initTable(){
		
	}
	table.render({
		elem : '#table',
		id : 'id',
		url : api.pagebyroleguid.url, // 数据接口
		method : api.pagebyroleguid.type,
		//contentType : 'application/json',
		headers : {token:T.token},
		where: {"roleGuid":roleGuid},
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
			}
			$.ajax({
            	type:api.usergroupListByGuids.type,
            	url:api.usergroupListByGuids.url,
            	data : {"guids":userGroupGuids},
            	traditional: true,
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
	});

	// 监听工具条
	table.on('tool(default)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
		var data = obj.data, // 获得当前行数据
		layEvent = obj.event; // 获得 lay-event 对应的值
		if (layEvent === 'del') {
			layer.confirm('真的删除行么', function(index) {
				$.ajax({
					type : api.delByUserGuids.type,
					url : api.delByUserGuids.url,
					data : {userGuids:[obj.data.guid], sourceGuid:roleGuid},
					traditional: true,
					success : function(r) {
						if (r.code == 0) {
							alert('成功删除了' + r.count + '记录', function(index) {
								table.reload('id', {});
								// obj.del(); // 删除对应行（tr）的DOM结构
							});
						} else {
							alertMsg(r);
						}
					}
				});
			});
		}
	});
	$("#add").click(function() {
		location.href = "roleuser_add.html?roleGuid=" + roleGuid;
	});
	$("#del").click(function() {
		var checkStatus = table.checkStatus('id');
		var guids = $.getRows(checkStatus.data);
		if (!guids)
			return;
		confirm('确定要删除选中的记录？', function() {
			$.ajax({
				type : api.delByUserGuids.type,
				url : api.delByUserGuids.url,
				data : {userGuids:guids, sourceGuid:roleGuid},
				traditional: true,
				success : function(r) {
					if (r.code == 0) {
						alert('成功删除了' + r.count + '记录', function(index) {
							table.reload('id', {});
							// obj.del(); // 删除对应行（tr）的DOM结构
						});
					} else {
						alertMsg(r);
					}
				}
			});
		});
	});

	$.post(api.roleList.url, function(r) {
		if(r.code != 0){
			alert(r.msg);
			return;
		}
		var rolelist = $("#rolelist");
		$.each(r.data, function(i, data){
			var li = $("<li></li>");
			li.append(data.name);
			li.attr("guid", data.guid);
			rolelist.append(li);
			if(roleGuid == data.guid)
				rolelist.parent().find("span").text(data.name);
		});
		rolelist.children().click(function(){
			$(this).parent().hide();
			roleGuid = $(this).attr("guid");
			window.location.href = "roleuser.html?roleGuid=" + roleGuid;
		});
	});
	$("#rolelist").parent().hover(function(){
			$(this).children("#rolelist").show();
		},function(){
			$(this).children("#rolelist").hide();
		});
});
});