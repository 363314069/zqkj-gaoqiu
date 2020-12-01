$(function(){
var api = {
    receive:{url:T.serverebusiness + "/business/reserve/receive", type:"post"},
	page:{url:T.serverebusiness + "/business/reserve/page", type:"post"}
};
layui.use([ 'laypage', 'layer', 'table' ], function() {
	var laypage = layui.laypage, // 分页
	layer = layui.layer, // 弹层
	table = layui.table; // 表格
	//var userGroupGuid = T.p("userGroupGuid");
	$.tabfield = [
		{ checkbox : true, fixed : true }, 
		{ title: 'id', field: 'id', width: 50, fixed: 'left' },
		//{ title: '应用程序ID', field: 'appId', width: 80 },			
		{ title: '用户名称', field: 'userName', width: 150 },
        { title: '用户电话', field: 'userPhone', width: 150 },
        { title: '预定商家', field: 'activityName', width: 300 },
        { title: '预定状态', field: 'state', width: 100 ,templet: function(d){
            if(d.state == 0){
            	$("#tabbtn").append('<a class="layui-btn layui-btn-xs layui-icon" lay-event="edit">&#xe642;</a>');
                return '申请预定'
            }else if(d.state == 1){
                return '已接单'
            }else if(d.state == 2){
                return '取消接单'
            }else{
                return ''
            }
        }},
		{ title: '预订开始时间', field: 'startTime', width: 200 },
		{ title: '预订结束时间', field: 'endTime', width: 200 },
		{ title: '创建时间', field: 'createTime', width: 200 },
		{ fixed : 'right', width : 170, align : 'center', toolbar : '#tabbtn'}
	];
	// 执行一个 table 实例
	table.render({
		elem : '#table',
		id : 'id',
		url : api.page.url, // 数据接口
		method : api.page.type,
		//contentType : 'application/json',
		headers : {token:T.token},
        //where: {"limit":"10"},
		//where: userGroupGuid?{"userGroupGuid":userGroupGuid}:{},
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
		}/* else if (layEvent === 'del') {
			layer.confirm('真的删除行么', function(index) {
				//obj.del(); // 删除对应行（tr）的DOM结构
				//layer.close(index);
				// 向服务端发送删除指令
				$.ajax({
					type : api.del.type,
					url : api.del.url,
					data : {guid : data.guid},
					success : function(r) {
						if (r.code == 0) {
							alert('成功删除了' + r.data + '记录', function(index) {
								table.reload('id', {});
								// obj.del(); // 删除对应行（tr）的DOM结构
							});
						} else {
							alert(r.msg);
						}
					}
				});
			});
		}*/ else if (layEvent === 'edit') {
            $.ajax({
                type : api.receive.type,
                url : api.receive.url,
                data : {id : data.id,state:1},
                success : function(r) {
                    if (r.code == 0) {
                        alert('接单成功', function(index) {
                            table.reload('id', {});
                            // obj.del(); // 删除对应行（tr）的DOM结构
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
		}
	});

	/*$("#del").click(function() {
		var checkStatus = table.checkStatus('id');
		var guids = $.getRows(checkStatus.data);
		if (!guids)
			return;
		confirm('确定要删除选中的记录？', function() {
			$.ajax({
				type : api.delbyguids.type,
				url : api.delbyguids.url,
				data : {guids : guids},
				traditional: true,
				success : function(r) {
					if (r.code == 0) {
						alert('成功删除了' + r.data + '记录', function(index) {
							table.reload('id', {});
						});
					} else {
						alert(r.msg);
					}
				}
			});
		});
	});*/
});
});