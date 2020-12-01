$(function(){
var api = {
	list:{url:T.serverurl + "security/province/list",type:"get"},
	del:{url:T.serverurl + "security/province/del",type:"post"},
	info:{url:T.serverurl + "security/province/info",type:"get"},
	save:{url:T.serverurl + "security/province/save",type:"post"},
	update:{url:T.serverurl + "security/province/update",type:"post"}
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
				rootPId: -1
			},
			keep: {
				parent: true
			},
			key: {
				name: "name"
			}
		},
		callback: {
			onClick: zTreeOnClick
			//onDrop: zTreeOnDrop,
			//beforeDrop: zTreeBeforeDrop
		},
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false,
			drag: {
				isCopy: false,
				isMove: false
			}
		},
		async: {
			enable: true,
			type: api.list.type,
			url: api.list.url,
			autoParam: ["id=parentId"],
			dataFilter: function(treeId, parentNode, responseData){
				return responseData.data;
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
	    	alert("请误移到根目录");
	    }
	    return !(targetNode == null || (moveType != "inner" && !targetNode.parentTId));
	};
	function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
		//alert(treeNodes.length + treeNodes[0].name+ "," + (targetNode ? (targetNode.tId + ", " + targetNode.name) : "isRoot" ) + "," +treeNodes[0].getParentNode().name);
		curTreeNode = treeNodes[0];
		var data = {
			field: {}
		};
		data.field.id = treeNodes[0].id;
		data.field.guid = treeNodes[0].guid;
		data.field.parentId = treeNodes[0].getParentNode().id;
		data.field.state = treeNodes[0].state;
		data.field.name = treeNodes[0].name;
		data.field.level = treeNodes[0].level;
		data.field.moduleSort = treeNodes[0].getIndex();
		data.field.indexId = treeNodes[0].getParentNode().indexId + "," + treeNodes[0].getParentNode().id;
		add = false;
		var url = api.update.url;
		update(data, url);
	};
	function zTreeOnClick(event, treeId, treeNode) {
		curTreeNode = treeNode;
		$("form").find("input").val("");
		$("form")[0].reset();  
		if($(event.target).is(".add")){
			var formbox = $('#form');
			add = true;
			var level = curTreeNode.level ? curTreeNode.level + 1 : 1;
			form.val("form", {parentId:treeNode.id, parentName:treeNode.name, level:level});
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
		} else {
			open("organization.html?guid=" + (curTreeNode.guid ? curTreeNode.guid : "") , "iframe");
		}
	};
	
	function del(){
		layer.confirm('真的删除行么', function(index) {
			$.ajax({
				type : api.del.type,
				url : api.del.url,
				data: {guid: curTreeNode.guid},
				success : function(r) {
					if (r.code == 0) {
						alert('成功删除了' + r.count + '记录', function(index) {
							
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
			data:{guid:curTreeNode.guid, id:curTreeNode.id},
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
					r.data.parentName = curTreeNode.getParentNode().name;//parentNode.moduleName;
					//$('#form').initForm({jsonValue:r.data});
					form.val("form", r.data);
					//form.render();
				} else {
					alert(r.msg);
				}
			}
		});
	}
	/*
	$.post(T.serverurl + "/security/province/list?parentId=0", function(r) {
		if(r.code != 0){
			alert(r.msg);
			return;
		}
		r.data.unshift({id:0, parentId:-1, name:"地区管理", open:true, isEditBtn:true, isRemoveBtn:true, iconOpen:"../js/zTree_v3-3.5.33/css/zTreeStyle/img/diy/1_open.png",iconClose:"../js/zTree_v3-3.5.33/css/zTreeStyle/img/diy/1_close.png"})
		treeObj = $.fn.zTree.init($("#zTree"), setting, r.data);
		//treeObj.expandAll(true);
		var nodes = treeObj.getNodes()[0].children;
		for (var i = 0; i < nodes.length; i++) {
			treeObj.expandNode(nodes[i], true);
		}
	});*/
	var rootdata = [{id:0, parentId:-1, name:"地区管理",isParent:true , open:true, isEditBtn:true, isRemoveBtn:true, iconOpen:"../common/js/zTree_v3-3.5.33/css/zTreeStyle/img/diy/1_open.png",iconClose:"../common/js/zTree_v3-3.5.33/css/zTreeStyle/img/diy/1_close.png"}];
	treeObj = $.fn.zTree.init($("#zTree"), setting, rootdata);
	treeObj.expandNode(treeObj.getNodes()[0], true);
	form.on('submit(save)', function(data) {
		update(data);
		return false;
	});
	
	form.on('submit(back)', function(data) {
		layer.closeAll();
		$("#form").hide();
		return false;
	});
	
	function update(data, url){
		var type = "POST";
		if(!url){
			type = api.save.type;
			url = api.save.url;
			if (!add) {
				type = api.update.type;
				url = api.update.url;
			}
		}
		if(data.field.state == undefined)
			data.field.state = 0;
		else 
			data.field.state = 1;
		$.ajax({
			type : "POST",
			url : url,
			data : data.field,
			success : function(r) {
				if (r.code === 0) {
					if (!add) {
						if(r.data.name){
							curTreeNode.name = r.data.name;
							curTreeNode.state = r.data.state;
							curTreeNode.remark = r.data.remark;
						}
						treeObj.updateNode(curTreeNode);
					} else {
						curTreeNode.isParent = true;
						treeObj.reAsyncChildNodes(curTreeNode, "refresh");
					}
					alert('操作成功', function(index) {
						//$.back();
						layer.closeAll();
						$("#form").hide();
					});
				} else {
					alert("<div>" + r.msg + "</div>" + JSON.stringify(r.data)); 
				}
			}
		});
	}
});
});