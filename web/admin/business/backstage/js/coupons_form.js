$(function(){
$('legend').append(guid?'修改':'添加');
var api = {
	list:{url:T.serverurl + "business/coupons/list", type:"post"},
	info:{url:T.serverurl + "business/coupons/info", type:"get"},
	save:{url:T.serverurl + "business/coupons/savecoupons", type:"post"},
	update:{url:T.serverurl + "business/coupons/updatecoupons", type:"post"},
	organizationList:{url:T.serverurl + "security/organization/list", type:"post"},
	orgmapList:{url:T.serverurl + "business/couponsorgmap/list", type:"post"},
	orgListByGuids:{url:T.serverurl + "security/organization/listByGuids", type:"post"},
	vipCardList:{url:T.serverurl + "security/vipcard/list", type:"post"}
};
layui.use('form', function() {
	var laydate = layui.laydate;
	var form = layui.form, 
	layer = layui.layer;
	form.on('submit(save)', function(data) {
		var type = api.save.type;
		var url = api.save.url;
		if (guid) {
			type = api.update.type;
			url = api.update.url;
		}

		//获取checkbox[name='like']的值
		var arr = new Array();
		$("input:checkbox[name='organizationGuids']:checked").each(function(i){
			arr[i] = $(this).val();
		});

		if(data.field.state == undefined)
			data.field.state = 0;
		else
			data.field.state = 1;



		console.log(data.field.state);

		data.field.organizationGuids = arr;

		if(parseInt(data.field.moneyConform) < parseInt(data.field.faceValue)){
			alert("满多少使用金额不能小于优惠券面值金额");
			return false;
		}

		//面值金额
		data.field.faceValue = parseInt(data.field.faceValue*100);
		//满多少可以使用
		data.field.moneyConform = parseInt(data.field.moneyConform*100);
		//价格
		data.field.price = parseInt(data.field.price*100);
		$.ajax({
			type : type,
			url : url,
			data : JSON.stringify(data.field),
			contentType : 'application/json;charset=utf-8',
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
	laydate.render({
		elem: '#startTime' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'datetime'
	});
	laydate.render({
		elem: '#endTime' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'datetime'
	});
	$('#back').on('click', function(data) {
		$.back();
	});

	//监听使用范围下拉框
	form.on('select(scope)', function(dataObj){
		var values = $("#scope").val();
		if(values == 3 || values == 4){
			$("#organizationArray").empty();
			$.ajax({
				type : api.organizationList.type,
				url : api.organizationList.url,
				success : function(r) {
					if (r.code === 0) {
						var $html = "";
						$.each(r.data, function (index, item) {
							$html += '<input type="checkbox" name="organizationGuids"  title="' + item.name + '" value="'+ item.guid +'"><div class="layui-unselect layui-form-checkbox"><span>' + item.name + '</span><i class="layui-icon layui-icon-ok"></i></div>';
						});
						$("#organizationArray").append($html);
						//append后必须从新渲染
						form.render('checkbox');
						form.render('select');
					} else {
						alertMsg(r);
					}
				}
			});
			$("#organizationList").show();
		}else{
			$("#organizationArray").empty();
			$("#organizationList").hide();
		}
	});
	
	if(guid){
		$.ajax({
			type : api.info.type,
			url : api.info.url,
			data : {t:(new Date()), guid:guid},
			success : function(r) {
				if(r.code == 0 && r.data){
					selectVipCard(r.data.vipCardGuid);
					//面值金额
					r.data.faceValue = r.data.faceValue/100;
					//满多少可以使用
					r.data.moneyConform = r.data.moneyConform/100;
					//价格
					r.data.price = r.data.price/100;
					form.val("form", r.data);
					//如果scope 等于3或者4   则为指定商家
					if(r.data.scope == 3 || r.data.scope == 4){
						$("#organizationArray").empty();
						$.ajax({
							type : api.orgmapList.type,
							url : api.orgmapList.url,
							data : {couponsGuid:r.data.guid},
							success : function(r) {
								if (r.code === 0) {
									var orgGuid = new Array();
									$.each(r.data, function (index, item) {
										orgGuid.push(item.organizationGuid);
									});
									$.ajax({
										type : api.organizationList.type,
										url : api.organizationList.url,
										traditional: true,
										success : function(r) {
											if (r.code === 0) {
												$.each(r.data, function (index, item) {
													if(orgGuid.includes(item.guid)){
														$("#organizationArray").append('<input type="checkbox" name="organizationGuids"  title="' + item.name + '" value="'+ item.guid +'" checked><div class="layui-unselect layui-form-checkbox"><span>' + item.name + '</span><i class="layui-icon layui-icon-ok"></i></div>')
													}else{
														$("#organizationArray").append('<input type="checkbox" name="organizationGuids"  title="' + item.name + '" value="'+ item.guid +'"><div class="layui-unselect layui-form-checkbox"><span>' + item.name + '</span><i class="layui-icon layui-icon-ok"></i></div>')
													}
												});
												//append后必须从新渲染
                                                form.render('checkbox');
											} else {
												alertMsg(r);
											}
										}
									});
								} else {
									alertMsg(r);
								}
							}
						});
						$("#organizationList").show();
					}
					form.render();
				} else {
					alertMsg(r);
				}
			}
		});
	}else{
		selectVipCard();
	}
	form.render();
	
	function selectVipCard(guid) {
		$.ajax({
			type : api.vipCardList.type,
			url : api.vipCardList.url,
			success : function(r) {
				console.log(r);
				if(r.code == 0){
					$.each(r.data,function (i, obj) {
						if(guid == obj.guid){
							$("#vipCardGuid").append('<option value="'+obj.guid+'" selected="selected">'+obj.name+'</option>');
						}else{
							$("#vipCardGuid").append('<option value="'+obj.guid+'">'+obj.name+'</option>');
						}
					});
					form.render();
				}
			}
		})
	}
});


});
