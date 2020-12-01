$(function(){
var api = {
	savelist:{url:T.serverurl + "security/usermap/savelist", type:"post"},
	pagebyroleguid:{url:T.serverurl + "security/user/pagebyroleguid", type:"post"},
	pagenotbyroleguid:{url:T.serverurl + "security/user/pagenotbyroleguid", type:"post"},
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
	];
	// 执行一个 table 实例
	var tabpamt = {
		elem : '#table',
		id : 'id',
		url : api.pagenotbyroleguid.url, // 数据接口
		method : api.pagenotbyroleguid.type,
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
	};
	table.render(tabpamt);


	$("#add").click(function() {
		var checkStatus = table.checkStatus('id');
		var guids = $.getRows(checkStatus.data);
		if (!guids)
			return;
		confirm('确定要添加选中的用户？', function() {
			$.ajax({
				type : api.savelist.type,
				url : api.savelist.url,
				data : {userGuids:guids, sourceGuid:roleGuid},
				traditional: true,
				success : function(r) {
					if (r.code == 0) {
						alert('成功添加' + r.count + '个用户', function(index) {
							table.reload('id', {});
						});
					} else {
						alert(r.msg);
					}
				}
			});
		});
	});
	$("#reply").click(function(){
		$(this).attr('href','roleuser.html?roleGuid=' + roleGuid);
	});
	$("#groupTree").parent().hover(function(){
		$(this).children("#groupTree").show();
	},function(){
		$(this).children("#groupTree").hide();
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
				rootPId: -1
			}
		},
		callback: {
			onClick: zTreeOnClick
		}
	};
	
	function zTreeOnClick(event, treeId, treeNode) {
		tabpamt.where={"roleGuid" : roleGuid, "userGroupGuid" : treeNode.guid};
		table.render(tabpamt);
		$("#groupTree").hide();
	};
	
	$.post(api.usergroupList.url,{orderBy:"sort"}, function(r) {
		if(r.code != 0){
			alert(r.msg);
			return;
		}
		//r.data.unshift({id:0, parentId:-1, guid:"", name:'组织结构', open:true, isEditBtn:true, isRemoveBtn:true, drop:false})
		//$.usergroupList = r.data;
		var treeObj = $.fn.zTree.init($("#groupTree"), setting, r.data);
		treeObj.expandAll(true);
	});
});
});