$(function(){
var api = {
	del:{url:T.serverurl + "security/module/del", type:"post"},
	save:{url:T.serverurl + "security/module/save", type:"post"},
	update:{url:T.serverurl + "security/module/update", type:"post"},
	info:{url:T.serverurl + "security/module/info", type:"get"},
	list:{url:T.serverurl + "security/module/list", type:"post"},
	page:{url:T.serverurl + "security/module/page", type:"post"},
	delbyguids:{url:T.serverurl + "security/module/delbyguids", type:"post"},
	savemove:{url:T.serverurl + "security/module/savemove", type:"post"},
	usergroupList:{url:T.serverurl + "security/usergroup/list", type:"post"},
	usergroupListByGuids:{url:T.serverurl + "security/usergroup/listByGuids", type:"post"},
	moduleauthmapGetRole:{url:T.serverurl + "security/moduleauthmap/getrole", type:"post"},
	moduleauthmapSaveRole:{url:T.serverurl + "security/moduleauthmap/saverole", type:"post"}
};
layui.use(['laypage', 'layer', 'table', 'form'], function() {
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	form = layui.form, 
	table = layui.table; // 表格
	var treeObj = null;
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
			},
			key: {
				name: "name"
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
				isMove: true
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
	    data.field.indexId = treeNodes[0].getParentNode().indexId + "," + treeNodes[0].getParentNode().id;
	    add = false;
	    update(data, api.savemove);
	};
	function zTreeOnClick(event, treeId, treeNode) {
		curTreeNode = treeNode;
		$("form").find("input").val("");
		$("form")[0].reset();  
		if($(event.target).is(".add")){
			var formbox = $('#form');
			add = true;
			form.val("form", {parentId:treeNode.id, indexId:treeNode.indexId + "," + treeNode.id, parentName:treeNode.name, sort:(treeNode.children?treeNode.children.length:0)});
			layer.open({
				type: 1,
				area: ['420px', '580px'],
				content: formbox,
				cancel: function(){ 
					$("#form").hide();
	  			}
			});
		} else if($(event.target).is(".edit")){
			//del(treeNode);
			//treeNode.name = treeNode.name + (n++);
			//treeObj.updateNode(treeNode);
			add = false;
			edit();
		} else if($(event.target).is(".remove")){
			//treeObj.removeNode(treeNode);
			del();
		}
	};
	function del(){
		layer.confirm('真的删除行么', function(index) {
			$.ajax({
				type : api.del.type,
				url : api.del.url,
				data : {guid:curTreeNode.guid},
				success : function(r) {
					if (r.code == 0) {
						alert('成功删除了' + r.data + '记录', function(index) {
							
						});
						treeObj.removeNode(curTreeNode);
					} else {
						alert(r.msg);
					}
				}
			});
		});
	}
	function edit(){
		$.ajax({
			type : api.info.type,
			url : api.info.url,
			data : {id : curTreeNode.id, t : (new Date())},
			success : function(r) {
				if (r.code == 0) {
					layer.open({
						type: 1,
						area: ['420px', '580px'],
						content: $('#form'),
						cancel: function(){ 
							$("#form").hide();
  						}
					});
					//var parentNode = treeObj.getNodeByTId(curTreeNode.parentTId);
					r.data.parentName = curTreeNode.getParentNode().name;//parentNode.name;
					//$('#form').initForm({jsonValue:r.data});
					form.val("form", r.data);
					//form.render();
				} else {
					alert(r.msg);
				}
			}
		});
	}
	$.post(api.list.url, {orderBy:"sort"}, function(r) {
		if(r.code != 0){
			alert(r.msg);
			return;
		}
		$.each(r.data, function(i, obj){
			obj.name = getName(obj);
			switch (obj.type){
				case 1:
					obj.iconSkin = "type1";
					break;
				case 2:
					obj.iconSkin = "type2";
					break;
				case 3:
					obj.iconSkin = "type3";
					break;
				case 4:
					obj.iconSkin = "type4";
					break;
			}
		})
		r.data.unshift({id:0, parentId:-1, name:'权限结构', open:true, isEditBtn:true, isRemoveBtn:true})
		$.usergroupList = r.data;
		treeObj = $.fn.zTree.init($("#zTree"), setting, r.data);
		//treeObj.expandAll(true);
		var nodes = treeObj.getNodes()[0].children;
		if(nodes)
			for (var i = 0; i < nodes.length; i++) {
				treeObj.expandNode(nodes[i], true);
			}
	});
	
	form.on('submit(save)', function(data) {
		update(data);
		return false;
	});
	function getName(obj){
		return (obj.name + "(" + obj.type + "," + obj.link + ")");
	}
	function update(data, objapi){
		var type = api.save.type;
		var url = api.save.url;
		if(objapi){
			type = objapi.type;
			url = objapi.url;
		} else if(!add){
			type = api.update.type;
			url = api.update.url;
		}
		if(data.field.state == undefined || data.field.state == "0")
			data.field.state = 0;
		else 
			data.field.state = 1;
		$.ajax({
			type : type,
			url : url,
			data : data.field,
			success : function(r) {
				if (r.code === 0) {
					r.data.name = getName(r.data);
					if (!add) {
						if(r.data.name){
							curTreeNode.name = r.data.name;
							curTreeNode.link = r.data.link;
							curTreeNode.param = r.data.param;
							curTreeNode.sort = r.data.sort;
							curTreeNode.type = r.data.type;
							curTreeNode.state = r.data.state;
							curTreeNode.indexId = r.data.indexId;
						}
						treeObj.updateNode(curTreeNode);
					} else {
						treeObj.addNodes(curTreeNode, r.data.sort, r.data);
					}
					alert('操作成功', function(index) {
						//$.back();
						layer.closeAll();
						$("#form").hide();
					});
				} else {
					alertMsg(r);
				}
			}
		});
	}
});
});