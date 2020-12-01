var ue = null;
$(function(){
$('legend').append(guid?'修改':'添加');
var newsSortGuid = T.p("newsSortGuid");
var api = {
	list:{url:T.serverurl + "security/news/list", type:"post"},
	info:{url:T.serverurl + "security/news/info", type:"get"},
	save:{url:T.serverurl + "security/news/save", type:"post"},
	update:{url:T.serverurl + "security/news/update", type:"post"},
	newssortlist:{url:T.serverurl + "security/newssort/list",type:"get"},
	newssortinfo:{url:T.serverurl + "security/newssort/info",type:"get"}
}
layui.use('form', function() {
	var form = layui.form, 
	laydate = layui.laydate,
	layer = layui.layer;
	form.on('submit(save)', function(data) {
		var type = api.save.type;
		var url = api.save.url;
		if (guid) {
			ype = api.update.type;
			url = api.update.url;
		}
		if(data.field.state == "undefield")
			data.field.state = 0;
		else 
			data.field.state = 1;
		data.field.newsSortGuid = $("input[name=newsSortGuid]").attr("guid");
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
		$.back();
	});
	
	if(guid){
		$.ajax({
			type : api.info.type,
			url : api.info.url,
			data : {t:(new Date()), guid:guid},
			success : function(r) {
				if(r.code == 0 && r.data){
					form.val("form", r.data);
					$("#content").html(r.data.content);
					initztree(r.data.newsSortGuid);
					//ue.setContent("r.data.content", true);
					form.render();
					
					ue = UE.getEditor('content');
				} else {
					alertMsg(r);
				}
			}
		});
	} else {
		ue = UE.getEditor('content');
	}
	form.render();
	//alert(ue.getContent());
	//.setContent("日期时间选择器", false);
	//日期时间选择器
	laydate.render({
	    elem: '#releaseTime'
	    ,type: 'datetime'
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
			type: api.newssortlist.type,
			url: function(treeId, treeNode){
				var url = api.newssortlist.url;
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
	function initztree(newsSortGuid){
		$.ajax({
			type : api.newssortinfo.type,
			url : api.newssortinfo.url,
			data:{guid:newsSortGuid},
			success : function(r) {
				if (r.code == 0 && r.data) {
					$("input[name=newsSortGuid]").val(r.data.name);
					$("input[name=newsSortGuid]").attr("guid", r.data.guid);
					form.render();
				}
			}
		});
	}
	if(newsSortGuid){
		initztree(newsSortGuid);
	}
});
});