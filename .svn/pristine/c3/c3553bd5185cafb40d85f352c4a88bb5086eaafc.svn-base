$(function(){
var provinceGuid = T.p("provinceGuid");
$('legend').append(guid?'修改':'添加');
var api={
	provinceList:{url:T.serverurl + "security/area/list", type:"get"},
	provinceInfo:{url:T.serverurl + "security/area/info", type:"get"},
	provinceByGuidList:{url:T.serverurl + "security/area/listByGuids", type:"post"},
	info:{url:T.serverurl + "security/organization/info",type:"get"},
	save:{url:T.serverurl + "security/organization/save",type:"post"},
	update:{url:T.serverurl + "security/organization/update",type:"post"}
}
layui.use('form', function() {
	var form = layui.form, 
	layer = layui.layer;
	//vm.initInfo(id);
	form.on('submit(save)', function(data) {
		var type = api.save.type;
		var url = api.save.url;
		if (guid) {
			type = api.update.type;
			url = api.update.url;;
		}
		if(data.field.state == undefined)
			data.field.state = 0;
		else 
			data.field.state = 1;
		data.field.provinceGuid = $("form input[name='provinceGuid']").attr("guid");
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
					alertMsg(r);
				}
			}
		});
		return false;
	});
	$('#back').on('click', function(data) {
		//$.back();
		window.location.href = "organization.html";
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
		async: {
			enable: true,
			type: api.provinceList.type,
			url: function(treeId, treeNode){
				var url = api.provinceList.url;
				if(treeNode)
					return url;
				return rul = url + "?parentId=0";
			},
			autoParam: ["id=parentId"],
			dataFilter: function(treeId, parentNode, responseData){
				return responseData.data;
			}
		},
		callback:{
			onClick:function(event, treeId, treeNode) {
				$.zTreeOnClick(event, treeId, treeNode, $(".selectztree").children(".ztree"));
			}
		}
	}
	$(".selectztree").initztree(setting);
	function initProvince(provinceGuid){
		$.ajax({
			type : api.provinceInfo.type,
			url : api.provinceInfo.url,
			data:{guid:provinceGuid},
			success : function(r) {
				if (r.code == 0 && r.data) {
					$("input[name=provinceGuid]").val(r.data.name);
					$("input[name=provinceGuid]").attr("guid", r.data.guid);
					form.render();
				}
			}
		});
	}
	if(provinceGuid){
		initProvince(provinceGuid);
	}
	if(guid){
		$.ajax({
			type : api.info.type,
			url : api.info.url,
			data:{guid:guid},
			success : function(r) {
				if (r.code == 0) {
					/*
					layer.open({
						type: 1,
						area: ['420px', '580px'],
						content: $('#form'),
						cancel: function(){ 
							$("#form").hide();
  						}
					});
					*/
					form.val("form", r.data);
					initProvince(r.data.provinceGuid);
					form.render();
				} else {
					alert(r.msg);
				}
			}
		});
	}
});
});