$(function(){
$('legend').append(id?'修改':'添加');
var api={
	provinceList:{url:T.serverurl + "security/province/list", type:"get"},
	info:{url:T.serverurl + "security/user/info",type:"get"},
	save:{url:T.serverurl + "security/user/save",type:"post"},
	update:{url:T.serverurl + "security/user/update",type:"post"},
	usergroupList:{url:T.serverurl + "security/usergroup/list",type:"post"},
	organizationList:{url:T.serverurl + "security/organization/list",type:"post"},
	queryAdmin:{url:T.serverurl + "security/user/queryAdmin",type:"post"}
}
layui.use('form', function() {
	var form = layui.form, 
	layer = layui.layer;
	//vm.initInfo(id);
	form.on('submit(save)', function(data) {
		var type = api.save.type;
		var url = api.save.url;
		if (id) {
			type = api.update.type;
			url = api.update.url;
		}
		if(data.field.state == undefined)
			data.field.state = 0;
		else 
			data.field.state = 1;
		data.field.userGroupGuid = $("form").find("input[name=userGroupGuid]").attr("guid");

		//用户类型 0普通用户  1分销商   3管理员
		var typeList = new Array();
		$("input[name='typeList']:checked").each(function() {
			typeList.push(parseInt($(this).val()));
		});

		//0 普通用户   1分销商   3管理员   2管理员+分销商   4普通用户+管理员   5普通用户+分销商   6全选
		if(typeList.length == 1){
			data.field.type = typeList[0];
		}else if(typeList.length == 2){
			if(typeList[0]+typeList[1] == 1){// 0  1 普通用户和分销商
				data.field.type = 5;
			}else if(typeList[0]+typeList[1] == 3){// 0  3 普通用户和管理员
				data.field.type = 4;
			}else if(typeList[0]+typeList[1] == 4){// 1  3 分销商和管理员
				data.field.type = 2;
			}
		}else if(typeList.length == 3){
			data.field.type = 6; //3个为全选
		}

		/*var typeQuery = data.field.type;
		if(typeQuery == 2 || typeQuery == 3 || typeQuery == 4 || typeQuery == 6 ){
			var retuanFlag = true;
			$.ajax({
				type : api.queryAdmin.type,
				url : api.queryAdmin.url,
				data : {openid:data.field.openid},
				async: false,
				success : function(r) {
					if (r.code === 0 && r.data > 0) {
						alert("该用户已经被绑定管理员，不可重复设置管理人员！");
						retuanFlag = false;
					}
				}
			});
			if(!retuanFlag){
				return false;
			}
		}*/

		if(isNull(data.field.loginName)){
			$.ajax({
				type : api.info.type,
				url : api.info.url,
				data : {loginName:data.field.loginName},
				async: false,
				success : function(r) {
					if (r.code === 0 && isNull(r.data) && r.data.guid != data.field.guid) {
						alert("登录账号重复，请重新输入");
						return false;
					}else{
						$.ajax({
							type : type,
							url : url,
							data : data.field,
							success : function(r) {
								if (r.code === 0) {
									alert('操作成功', function(index) {
										$.back();
									});
								} else {
									alertMsg(r)
								}
							}
						});
					}
				}
			});
		}else{
			data.field.loginName = null;
			$.ajax({
				type : type,
				url : url,
				data : data.field,
				success : function(r) {
					if (r.code === 0) {
						alert('操作成功', function(index) {
							$.back();
						});
					} else {
						alertMsg(r)
					}
				}
			});
		}
		return false;
	});
	$('#back').on('click', function(data) {
		$.back();
	});
	
	var setting = {
		view: {
			showIcon: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId: 0
			}
		},
		callback: {
			onClick: zTreeOnClick
		}
	};
	
	function zTreeOnClick(event, treeId, treeNode) {
		var tree = $("#zTree");
		tree.hide();
		$("form").find("input[name=userGroupGuid]").val(treeNode.name);
		$("form").find("input[name=userGroupGuid]").attr("guid", treeNode.guid);
		form.render('select');
	};
	$.post(api.usergroupList.url, function(r) {
		if(r.code != 0){
			alert(r.msg);
			return;
		}
		//$.fn.zTree.init($("#userGroupZtree"), settingpr, r.data);
		$("#userGroup").initztree(setting, r.data);
	});
	$.post(api.organizationList.url, function(r) {
		if(r.code != 0){
			alert(r.msg);
			return;
		}
		var select = $("form").find("select[name=organizationGuid]");
		for (var i = 0; i < r.data.length; i++) {
			var option = $("<option>");
			option.val(r.data[i].guid);
			option.append(r.data[i].name);
			select.append(option);
			//r.data[i]
		}
		form.render('select');
	});
	if(id){
		$.get(api.info.url, {id:id}, function(r) {
			if(r.code == 0){
				//0 普通用户   1分销商   3管理员   2管理员+分销商   4普通用户+管理员   5普通用户+分销商   6全选
				console.log(r.data);
				if(r.data.type == 0){
					$("#ordinary").prop("checked", true);
				}else if(r.data.type == 1){
					$("#distributors").prop("checked", true);
				}else if(r.data.type == 2){
					$("#distributors").prop("checked", true);
					$("#administrator").prop("checked", true);
				}else if(r.data.type == 3){
					$("#administrator").prop("checked", true);
				}else if(r.data.type == 4){
					$("#ordinary").prop("checked", true);
					$("#administrator").prop("checked", true);
				}else if(r.data.type == 5){
					$("#ordinary").prop("checked", true);
					$("#distributors").prop("checked", true);
				}else if(r.data.type == 6){
					$("#ordinary").prop("checked", true);
					$("#distributors").prop("checked", true);
					$("#administrator").prop("checked", true);
				}
				form.val("form", r.data);
				form.render();
			} else {
				alertMsg(r);
			}
		});
	}
});
});