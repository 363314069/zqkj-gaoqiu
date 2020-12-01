$(function(){
var api = {
	del:{url:T.serverurl + "security/usergroup/del", type:"post"},
	save:{url:T.serverurl + "security/usergroup/save", type:"post"},
	update:{url:T.serverurl + "security/usergroup/update", type:"post"},
	info:{url:T.serverurl + "security/usergroup/info", type:"get"},
	list:{url:T.serverurl + "security/usergroup/list", type:"post"}
}
layui.use(['laypage', 'layer', 'table', 'form'], function() {
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	form = layui.form, 
	table = layui.table; // 表格
	var curTreeNode = null;
	var add = true;
	var setting = {
		view: {
			//showIcon: false,
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false,
			showLine: true
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId: 0
			},
			keep: {
				parent: true
			}
		},
		callback: {
			onClick: zTreeOnClick,
			onDrop: zTreeOnDrop,
			beforeDrop: zTreeBeforeDrop
		},
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false,
			drag: {
				isCopy: false,
				isMove: false
			}
		}
	};

	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if ($("#btn_" + treeNode.tId).length > 0) return;
		var btn = $("<span>");
		btn.addClass("btn");
		btn.attr("id", "btn_" + treeNode.tId);
		if(!treeNode.isAddBtn)
			btn.append("<i class='iconfont layui-btn add' title='添加子节点'>&#xe654;</i>");
		if(!treeNode.isEditBtn)
			btn.append("<i class='iconfont layui-btn layui-btn-primary edit' title='修改节点'>&#xe68f;</i>");
		if(!treeNode.isRemoveBtn)
			btn.append("<i class='iconfont layui-btn remove' title='删除节点'>&#xe632;</i>");
		sObj.after(btn);
		
	};
	function removeHoverDom(treeId, treeNode) {
		//$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	function zTreeBeforeDrop(treeId, treeNodes, targetNode, moveType) {
		if(targetNode == null){
	    	alert("请误移到跟目录");
	    }
	    return !(targetNode == null || (moveType != "inner" && !targetNode.parentTId));
	};
	function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
	    //alert(treeNodes.length + treeNodes[0].name+ "," + (targetNode ? (targetNode.tId + ", " + targetNode.name) : "isRoot" ) + "," +treeNodes[0].getParentNode().name);
	    var data = {field:{}};
	    data.field.id = treeNodes[0].id;
	    data.field.guid = treeNodes[0].guid;
	    data.field.parentId = treeNodes[0].getParentNode().id;
	    data.field.state = treeNodes[0].state;
	    data.field.sort = treeNodes[0].getIndex();
	    add = false;
	    update(data);
	};
	function zTreeOnClick(event, treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj("usergroupZtree");
		curTreeNode = treeNode;
		if($(event.target).is(".add")){
			var formbox = $('#form');
			add = true;
			formbox.find("input[name='parentId']").val(treeNode.id);
			formbox.find("input[name='parentName']").val(treeNode.name);
			formbox.find("input[name='sort']").val(treeNode.children?treeNode.children.length:0);
			layer.open({
				type: 1,
				area: ['500px', '400px'],
				content: formbox,
				cancel: function(){ 
					$("#form").hide();
	  			}
			});
			form.render();
		} else if($(event.target).is(".edit")){
			//del(treeNode);
			//treeNode.name = treeNode.name + (n++);
			//treeObj.updateNode(treeNode);
			add = false;
			edit(treeNode);
		} else if($(event.target).is(".remove")){
			//treeObj.removeNode(treeNode);
			del(treeNode);
		} else{
			open("user.html?userGroupGuid=" + treeNode.guid, "ifream");
		}
	};
	function del(treeNode){
		layer.confirm('真的删除行么', function(index) {
			$.ajax({
				type : api.del.type,
				url : api.del.url,
				data : {guid:treeNode.guid},
				success : function(r) {
					if (r.code == 0) {
						alert('成功删除了' + r.count + '记录', function(index) {
							
						});
						var treeObj = $.fn.zTree.getZTreeObj("usergroupZtree");
						treeObj.removeNode(treeNode);
					} else {
						alert(r.msg);
					}
				}
			});
		});
	}
	function edit(treeNode){
		$.ajax({
			type : api.info.type,
			url : api.info.url,
			data : {guid: treeNode.guid},
			success : function(r) {
				if (r.code == 0) {
					layer.open({
						type: 1,
						area: ['500px', '400px'],
						content: $('#form'),
						cancel: function(){ 
							$("#form").hide();
  						}
					});
					r.data.parentName = treeNode.getParentNode().name;
					form.val("form", r.data);
					form.render();
				} else {
					alert(r.msg);
				}
			}
		});
	}
	$.post(api.list.url,{orderBy:"sort"}, function(r) {
		if(r.code != 0){
			alert(r.msg);
			return;
		}
		r.data.unshift({id:0, parentId:-1, guid:"", name:'组织结构', open:true, isEditBtn:true, isRemoveBtn:true, drop:false,iconOpen:"../common/js/zTree_v3-3.5.33/css/zTreeStyle/img/diy/1_open.png",iconClose:"../common/js/zTree_v3-3.5.33/css/zTreeStyle/img/diy/1_close.png"})
		$.usergroupList = r.data;
		var treeObj = $.fn.zTree.init($("#usergroupZtree"), setting, r.data);
		treeObj.expandAll(true);
	});
	
	form.on('submit(save)', function(data) {
		update(data);
		return false;
	});
	function update(data){
		var type = api.save.type;
		var url = api.save.url;
		if (!add) {
			type = api.update.type;
			url = api.update.url;
		}
		if(data.field.state == undefined)
			data.field.state = 0;
		else 
			data.field.state = 1;
		$.ajax({
			type : type,
			url : url,
			data : data.field,
			success : function(r) {
				if (r.code === 0) {
					var treeObj = $.fn.zTree.getZTreeObj("usergroupZtree");
					if (!add) {
						curTreeNode.name = r.data.name;
						treeObj.updateNode(curTreeNode);
					} else {
						treeObj.addNodes(curTreeNode, r.data);
					}
					layer.closeAll();
					$("#form").hide();
					alert("更新成功");
				} else {
					alertMsg(r);
				}
			}
		});
	}
});
});