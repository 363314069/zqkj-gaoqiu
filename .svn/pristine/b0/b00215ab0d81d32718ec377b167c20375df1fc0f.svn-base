$.fn.extend({
	/**下拉目录树
	 * $("input[name=provinceGuid]").parent().initztree(data);
	 **/
	initztree: function(setting, data) {
		$(this).mouseover(function() {
			$(this).children(".ztree").show();
		}).mouseout(function() {
			$(this).children(".ztree").hide();
		});
		if(data)
			$.fn.zTree.init($(this).children(".ztree"), setting, data);
		else
			$.fn.zTree.init($(this).children(".ztree"), setting);
	},

	
	/**
	 * 左右拖拉
	 *  $("#ewresize").ewresize();
	 */
	ewresize: function() {
		var resize = $(this);
		var left = resize.children('.left');
		var right = resize.children('.right');
		left.mousedown(function(e) {
			right.hide();
			$(document).mousemove(function(e) {
				left.width(e.pageX < 20 ? 20 : e.pageX);
				right.width(resize.width() - e.pageX - 1);
			}).mouseup(function() {
				$(document).off("mousemove");
				right.show();
			});
		});
		left.children().mousedown(function(event) {
			event.stopPropagation();
		});
	}
});
$.extend({
	zTreeOnClick: function(event, treeId, treeNode, ztreeobj) {
		ztreeobj.hide();
		ztreeobj.parent().children("input").val(treeNode.name);
		ztreeobj.parent().children("input").attr("guid", treeNode.guid);
	},
	// 选择单条记录
	getRow : function(rows) {
		if (!rows || rows.length == 0) {
			alert("请选择一条记录");
			return;
		}
		if (rows.length > 1) {
			alert("只能选择一条记录");
			return;
		}
		return rows[0].guid;
	},
	// 选择多条记录
	getRows : function(rows) {
		var guid = new Array();
		if (!rows || rows.length == 0) {
			alert("请选择一条记录");
			return;
		}
		for (var i = 0; i < rows.length; i++) {
			guid.push(rows[i].guid);
		}
		return guid;
	},
	state : '<div>{{d.state == 1?"启用":"禁用"}}</div>'
});
$(function() {
	$("#ewresize").ewresize();
});