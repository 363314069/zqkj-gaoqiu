$(function(){
$('legend').append(guid?'修改':'添加');
    var reservationGuid;
var api = {
	list:{url:T.serverurl + "business/couponsteatime/list", type:"post"},
	info:{url:T.serverurl + "business/couponsteatime/info", type:"get"},
	save:{url:T.serverebusiness + "business/couponsteatime/savecouponsteatime", type:"post"},
	update:{url:T.serverebusiness + "business/couponsteatime/updatecouponsteatime", type:"post"},
	introductionList:{url:T.serverurl + "/business/introduction/list", type:"post"},
	reservationList:{url:T.serverurl + "/business/reservation/list", type:"post"},
	couponsList:{url:T.serverebusiness + "business/coupons/orgcouponslist",type:"post"},
    couponsactmapList:{url:T.serverebusiness + "business/couponsactmap/list",type:"post"},
	uploadFile:{url:T.serverebusiness + "/business/common/uploadimage",type:"post"}
}
layui.use('form', function() {
	var form = layui.form, 
	layer = layui.layer , upload = layui.upload;
	var laydate = layui.laydate;


	//执行实例
	var uploadInst = upload.render({
		elem: '#imgs', //绑定元素
		accept: 'file',
		size: 307200,
		url: api.uploadFile.url, //上传接口
		method: 'POST',
		done: function(res){
			//上传完毕回调
			layer.alert("上传成功！", {
				icon: 1,
				title: "提示"
			});
			$("#img").val(res.data);
			$('#upload-normal-img').attr("src", T.serverebusiness + res.data);
		}
		,error: function(res){
			//请求异常回调
			layer.alert("后台出错", {
				icon: 2,
				title: "提示"
			});
		}
	});


	form.on('submit(save)', function(data) {
		var startEnds = new Array();
		var intervals = new Array();	//间隔分钟
		var costPrices = new Array();	//原价
		var prices = new Array();		//价格
		var discounts = new Array();	//折扣
		var peopleNumbers = new Array();	//人数
		$("input[name='startEnd']").each(function() {
			startEnds.push($(this).val())
		});
		$("input[name='intervals']").each(function() {
			intervals.push($(this).val())
		});
		$("input[name='costPrices']").each(function() {
			costPrices.push($(this).val())
		});
		$("input[name='prices']").each(function() {
			prices.push($(this).val())
		});
		$("input[name='discounts']").each(function() {
			discounts.push($(this).val())
		});
		$("input[name='peopleNumber']").each(function() {
			peopleNumbers.push($(this).val())
		});

		var  params = new Array();
		for(var i = 0; i < startEnds.length; i++){
			var object = {};
			object['startTime'] = startEnds[i].split(" - ")[0];	//时间段起始时间
			object['endTime'] = startEnds[i].split(" - ")[1];	//时间段结束时间
			object['interval'] = intervals[i];	//间隔分钟
			object['state'] = 0;	//状态
			//object['costPrice'] = parseInt(costPrices[i]*100);	//原价
			//object['price'] = parseInt(prices[i]*100);	//价格
			//object['discount'] = discounts[i];	//折扣
			object['costPrice'] = data.field.price;//原价
			object['price'] = data.field.price;	//价格
			object['discount'] = 0; //折扣


			object['peopleNumber'] = peopleNumbers[i];	//人数
			params.push(object);
		}
		var json = JSON.stringify(params);//JSON.stringify() 方法用于将 JavaScript 值转换为 JSON 字符串。


		var type = api.save.type;
		var url = api.save.url;
		if (guid) {
			type = api.update.type;
			url = api.update.url;
		}
		if(!isNull(data.field.state))
			data.field.state = 0;
		else
			data.field.state = 1;

		if(!isNull(data.field.preferential))
			data.field.preferential = 0;
		else
			data.field.preferential = 1;

		data.field.timeJson = json;
		data.field.price = parseInt(data.field.price*100);
		data.field.costPrice = parseInt(data.field.costPrice*100);

		//获取费用包含的复选框选择的值
		var priceInclude = new Array();
		$("input[name='priceInclude']:checked").each(function() {
			priceInclude.push($(this).val());
		});
		data.field.priceInclude = priceInclude.toString();


		var preferentialList = new Array();
		$("input[name='preferentialList']:checked").each(function() {
			preferentialList.push($(this).val());
		});
		data.field.preferentialList = preferentialList;
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
	
	$('#back').on('click', function(data) {
		$.back();
	});

	laydate.render({
		elem: '#startTime' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'datetime'
	});
	laydate.render({
		elem: '#endTime' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'datetime'
	});


	//监听优惠券是否启用
	form.on('switch(preferential)', function (data) {
		if(data.elem.checked){
			$("#couponsList").show();
			//查询商家优惠券关联表数据
			$.ajax({
				type:api.couponsList.type,
				url:api.couponsList.url,
				success:function (r) {
					$.each(r.data,function (i, obj) {
						$("#coupons").append('<input type="checkbox" name="preferentialList" title="'+obj.name+'" id="coupons-'+obj.guid+'" value="'+obj.guid+'"><div class="layui-unselect layui-form-checkbox"><span>'+obj.name+'</span><i class="layui-icon layui-icon-ok"></i></div>');
					});
					form.render();
				}
			});
		}else{
			$("#coupons").empty();
			$("#couponsList").hide();
			form.render();
		}
	});

	if(guid){
        var priceInclude;
		$.ajax({
			type : api.info.type,
			url : api.info.url,
			data : {t:(new Date()), guid:guid},
			success : function(r) {
				if(r.code == 0 && r.data){
                    reservationGuid = r.data.reservationGuid;
                    $("#upload-normal-img").attr("src",T.serverebusiness + r.data.img);
                    r.data.price = r.data.price/100;
                    if(isNull(r.data.priceInclude)){
                        priceInclude = r.data.priceInclude.split(",");
                    }
                    if(parseInt(r.data.preferential) == 1){  //0表示不用优惠券  1表示启用优惠券
                        $("#couponsList").show();
                        //加载优惠券选择的优惠券
                        $.ajax({
                            type:api.couponsList.type,
                            url:api.couponsList.url,
                            async : true,
                            success:function (r) {
                                $.each(r.data,function (i, obj) {
                                    $("#coupons").append('<input type="checkbox" name="preferentialList" title="'+obj.name+'" id="coupons-'+obj.guid+'" value="'+obj.guid+'"><div class="layui-unselect layui-form-checkbox"><span>'+obj.name+'</span><i class="layui-icon layui-icon-ok"></i></div>');
                                });
                                form.render();
                            }
                        });

                        //加载我已经选择的优惠券
                        $.ajax({
                            type : api.couponsactmapList.type,
                            url : api.couponsactmapList.url,
                            data : {t:(new Date()), activityGuid:guid},
                            async : true,
                            success : function(r) {
                                if(r.code == 0 && r.data){
                                    $.each(r.data,function (i, obj) {
                                        $('#coupons-'+obj.couponsGuid).attr("checked",'true');
                                    });
                                    form.render();
                                } else {
                                    alertMsg(r);
                                }
                            }
                        });
                    }
                    traverseTime(r.data.timeJson);


					form.val("form", r.data);
                    reservation(r.data.reservationGuid);
					introductionList(r.data.reservationGuid,r.data.introductionGuid);
					$("#content").html(r.data.content);
					form.render();
					ue = UE.getEditor('content');
				} else {
					alertMsg(r);
				}
			}
		});
	}else{
		ue = UE.getEditor('content');
		reservation();
	}

	form.on("select(reservationGuid)", function(data){
		introductionList(data.value);
	});

	form.render();

	// 添加按钮事件
	$(".formadd").on("click", function() {
		var div1 = $('<div class="layui-form-item">');
		var div1_1 = $('<div class="layui-inline">');
		var div1_2 = $('<div class="layui-inline">');
		var div1_3 = $('<div class="layui-inline">');
		var div1_4 = $('<div class="layui-inline">');
		var div1_5 = $('<div class="layui-inline">');
		var div1_6 = $('<div class="layui-inline">');
		var but = $('<button type="button" class="layui-btn layui-btn-sm layui-btn-normal editdelete" ><i class="layui-icon"></i> 删除</button>');
		div1_1.append('<label class="layui-form-label">时间段</label><div class="layui-input-inline"><input type="text" name="startEnd" id="startEnd"  autocomplete="off" lay-verify="required" class="layui-input startEnd"></div>');
		div1_2.append('<label class="layui-form-label">间隔分钟</label><div class="layui-input-inline"><input type="text" name="intervals"  autocomplete="off" lay-verify="required" class="layui-input"></div>');
		div1_3.append('<label class="layui-form-label" style="display: none">价格</label><div style="display: none" class="layui-input-inline"><input type="text" name="prices"  autocomplete="off" class="layui-input"></div>');
		div1_4.append('<label class="layui-form-label" style="display: none">原价</label><div style="display: none" class="layui-input-inline"><input type="text" name="costPrices"  autocomplete="off" class="layui-input"></div>');
		div1_5.append('<label class="layui-form-label" style="display: none">折扣</label><div style="display: none" class="layui-input-inline"><input type="text" name="discounts" autocomplete="off" class="layui-input"></div>')
		div1_6.append('<label class="layui-form-label">人数</label><div class="layui-input-inline"><input type="text" name="peopleNumber" lay-verify="required" autocomplete="off" class="layui-input"></div>')
		div1.append(div1_1).append(div1_2).append(div1_3).append(div1_4).append(div1_5).append(div1_6).append(but);
		$("#timeJsons").append(div1);

		// 删除按钮事件
		$(".editdelete").on("click", function() {
			$(this).parent().remove();
		});

		lay('.startEnd').each(function() {
			laydate.render({
				elem: this
				,type: 'time'
				,format: 'HH:mm'
				,range: true
			});
		});
	});

	function traverseTime(timeJsonStr) {
		var timeJson=eval(timeJsonStr);
		for(var i=0; i<timeJson.length ;i++){
			var div1 = $('<div class="layui-form-item">');
			var div1_1 = $('<div class="layui-inline">');
			var div1_2 = $('<div class="layui-inline">');
			var div1_3 = $('<div class="layui-inline">');
			var div1_4 = $('<div class="layui-inline">');
			var div1_5 = $('<div class="layui-inline">');
			var div1_6 = $('<div class="layui-inline">');
			var but = $('<button type="button" class="layui-btn layui-btn-sm layui-btn-normal editdelete" ><i class="layui-icon"></i> 删除</button>');
			div1_1.append('<label class="layui-form-label">时间段</label><div class="layui-input-inline"><input type="text" value="'+timeJson[i].startTime+' - '+timeJson[i].endTime+'" name="startEnd" id="startEnd"  autocomplete="off" lay-verify="required" class="layui-input startEnd"></div>');
			div1_2.append('<label class="layui-form-label">间隔分钟</label><div class="layui-input-inline"><input type="text" value="'+timeJson[i].interval+'" name="intervals"  autocomplete="off" lay-verify="required" class="layui-input"></div>');
			div1_3.append('<label class="layui-form-label" style="display: none">价格</label><div style="display: none" class="layui-input-inline"><input type="text" value="'+timeJson[i].price/100+'" name="prices"  autocomplete="off" class="layui-input"></div>');
			div1_4.append('<label class="layui-form-label" style="display: none">原价</label><div style="display: none" class="layui-input-inline"><input type="text" value="'+timeJson[i].costPrice/100+'" name="costPrices" autocomplete="off" class="layui-input"></div>');
			div1_5.append('<label class="layui-form-label" style="display: none">折扣</label><div style="display: none" class="layui-input-inline"><input type="text" value="'+timeJson[i].discount+'" name="discounts" autocomplete="off" class="layui-input"></div>')
			div1_6.append('<label class="layui-form-label">人数</label><div class="layui-input-inline"><input type="text" value="'+timeJson[i].peopleNumber+'" name="peopleNumber" lay-verify="required" autocomplete="off" class="layui-input"></div>')
			div1.append(div1_1).append(div1_2).append(div1_3).append(div1_4).append(div1_5).append(div1_6).append(but);
			$("#timeJsons").append(div1);

			// 删除按钮事件
			$(".editdelete").on("click", function() {
				$(this).parent().remove();
			});

			lay('.startEnd').each(function() {
				laydate.render({
					elem: this
					,type: 'time'
					,format: 'HH:mm'
					,range: true
				});
			});
		}
	}
});

    //加载球场列表  有参数为修改进入
	function reservation(reservationGuid){
		$.ajax({
			type:api.reservationList.type,
			traditional: true,
			url:api.reservationList.url,
			async:false,
			data : {"organizationGuid":T.usermsg.organizationGuid,guid:reservationGuid},
			success : function(r) {
				if (r.code == 0) {
					$.each(r.data, function(i, obj){
						if(!isNull(reservationGuid) && i == 0){
							introductionList(obj.guid);
						}else{
						    if(reservationGuid == obj.guid){
                                $('#reservationGuid').append('<option value="'+obj.guid+'" selected>'+obj.name+'</option>');
                            }else{
                                $('#reservationGuid').append('<option value="'+obj.guid+'">'+obj.name+'</option>');
                            }
                        }
					});
					layui.form.render('select');
				} else {
					alertMsg(r);
				}
			}
		});
	}


	//加载场地列表  introductionGuid修改时候掉用
	function introductionList(reservationGuid,introductionGuid){
		$('#introductionGuid').empty();
		$.ajax({
			type:api.introductionList.type,
			traditional: true,
			url:api.introductionList.url,
			async:false,
			data : {"reservationGuid":reservationGuid},
			success : function(r) {
				if (r.code == 0) {
					$.each(r.data, function(i, obj){
					    if(introductionGuid == obj.guid){
                            $('#introductionGuid').append('<option value="'+obj.guid+'" selected>'+obj.name+'</option>');
                        }else{
                            $('#introductionGuid').append('<option value="'+obj.guid+'">'+obj.name+'</option>');
                        }
					});
					layui.form.render('select');
				} else {
					alertMsg(r);
				}
			}
		});
	}
});