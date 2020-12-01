var defaultTimeJson = '';
var couponsteatime = '';
$(function(){
	$('legend').append(guid?'修改':'添加');
	var couponsteatimeGuid = T.p("couponsteatimeGuid");

	var dates,ids;//定义一个数组  日期数组 id数组
	var api = {
		list:{url:T.serverebusiness + "business/couponsdateteatime/list", type:"post"},
		info:{url:T.serverebusiness + "business/couponsdateteatime/info", type:"get"},
		save:{url:T.serverebusiness + "business/couponsdateteatime/save", type:"post"},
		updateList:{url:T.serverebusiness + "business/couponsdateteatime/updatelist", type:"post"},
		couponsteatimeInfo:{url:T.serverebusiness + "business/couponsteatime/info", type:"post"},
		paramInfo:{url:T.serverebusiness + "security/param/info", type:"post"},
		paramList:{url:T.serverebusiness + "security/param/list",type:"post"},
		paramListByGuids:{url:T.serverebusiness + "security/param/listByGuids",type:"post"}
	};
	layui.use('form', function() {
		var form = layui.form,
			layer = layui.layer, laydate = layui.laydate;
		form.on('submit(save)', function(data) {
			var type = api.updateList.type;
			var url = api.updateList.url;

			if(data.field.state == "undefield")
				data.field.state = 0;
			else
				data.field.state = 1;

			var startEnds = new Array();
			var intervals = new Array();	//间隔分钟
			var costPrices = new Array();	//原价
			var prices = new Array();		//价格
			var discounts = new Array();	//折扣
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

			$("input[name='discounts']").each(function() {
				discounts.push($(this).val())
			});


			//费用包含 用二进制计算  然后存入数据库
			/*var priceIncludes = new Array();
			$("div[name='checkboxDiv']").each(function(index,data){
				var value = 0;
				$(data).find('input:checkbox[name=priceInclude]:checked').each(function () {
					value = value + parseInt($(this).val());
				});
				priceIncludes.push(value);
			});*/


			var  paramsttt = new Array();
			for(var idInt = 0;idInt < ids.length; idInt++){
				var arr = new Array();
				var  params = {};
				params['id'] = ids[idInt];
				params['date'] = dates[idInt];
				params['teatimeCouponsGuid'] = couponsteatimeGuid;
				params['type'] = 0;
				params['state'] = 0;
				params['reservationGuid'] = couponsteatime.reservationGuid;
				params['introductionGuid'] = couponsteatime.introductionGuid;
				for(var i = 0; i < startEnds.length; i++){
					var object = {};
					object['startTime'] = startEnds[i].split(" - ")[0];	//时间段起始时间
					object['endTime'] = startEnds[i].split(" - ")[1];	//时间段结束时间
					object['interval'] = intervals[i];	//间隔分钟
					object['state'] = 0;	//状态
					//object['costPrice'] = costPrices[i]*100;	//原价
					//object['price'] = prices[i]*100;	//价格
					//object['discount'] = discounts[i];	//折扣
					object['costPrice'] = couponsteatime.price;	//原价
					object['price'] = couponsteatime.price;	//价格
					object['discount'] = 0;	//折扣

					//object['priceInclude'] = priceIncludes[i];	//费用包含
					arr.push(object);
				}
				params['timeJson']=JSON.stringify(arr);
				paramsttt.push(params);
			}
			$.ajax({
				type : type,
				url : url,
				data : JSON.stringify(paramsttt),
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

		$('#addr').on('click', function(data) {

			dates =[];//定义一个数组  日期数组
			$('input[name="id"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数
				dates.push($(this).attr("date"));//将选中的值添加到数组chk_value中
			});

			ids =[];//定义一个数组  id数组
			for(var i=0;i<dates.length;i++){
				ids.push($("#"+dates[i]).val());
			}

			if(ids.length > 0){
				$("#chooseDate").val(dates);
				layer.open({
					type: 1,
					skin: 'layui-layer-rim', //加上边框
					area: ['750px', '750px'], //宽高
					content: $("#formId")
				});
			}else{
				layer.msg('请选择要设置的日期');
			}
		});


		laydate.render({
			elem: '#dateTime' //或 elem: document.getElementById('test')、elem: lay('#test') 等
			,type: 'date'
			,value: new Date()
			,done: function(value, date, endDate){//日期时间被切换后的回调
				//加载列表数据
				loadUlList(couponsteatimeGuid,10,value);
				form.render();
			}
		});



		$('#reduce').on('click', function(data) {
			var dateTimes = getDateStr(-10,$("#dateTime").val());
			$("#dateTime").val(dateTimes[dateTimes.length-1]);
			//加载列表数据
			loadUlList(couponsteatimeGuid,10,$("#dateTime").val());
			form.render();
		});
		$('#increase').on('click', function(data) {
			var dateTimes = getDateStr(10,$("#dateTime").val());
			$("#dateTime").val(dateTimes[dateTimes.length-1]);
			//加载列表数据
			loadUlList(couponsteatimeGuid,10,$("#dateTime").val());
			form.render();
		});


		//加载参数表 费用包含全部参数
		var paramArray;
		$.ajax({
			type : api.paramInfo.type,
			url : api.paramInfo.url,
			data : {t:(new Date()), key:100000},
			async: false,
			success : function(r) {
				if(r.code == 0 && r.data){
					$.ajax({
						type : api.paramList.type,
						url : api.paramList.url,
						data : {t:(new Date()), parentId:r.data.id},
						async: false,
						success : function(r) {
							if(r.code == 0 && r.data){
								paramArray = r.data;
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



		//记录费用包含参数guid
		var paramGuid;
		//查询场地信息获取默认timeJson
		$.ajax({
			type : api.couponsteatimeInfo.type,
			url : api.couponsteatimeInfo.url,
			data : {guid:couponsteatimeGuid},
			async: false,
			success : function(r) {
				if(r.code == 0 && r.data){
					couponsteatime = r.data;
					defaultTimeJson = r.data.timeJson;
					paramGuid = r.data.priceInclude;
				} else {
					alertMsg(r.msg);
				}
			}
		});


		//var paramStr = "";
		//费用包含参数不为空
		/*if(isNull(paramGuid)){
			var paramGuids = paramGuid.split(",");
			//根据guids查询  参数集合
			$.ajax({
				type : api.paramListByGuids.type,
				url : api.paramListByGuids.url,
				data : {"guids":paramGuids},
				traditional: true,
				async: false,
				success : function(r) {
					if(r.code == 0 && r.data){
						$.each(paramArray,function (pi, piobj) {
							var paramSing = 0;
							$.each(r.data,function (i, obj) {
								if(obj.guid == piobj.guid){
									paramStr +='<input type="checkbox" name="priceInclude" title="'+obj.name+'" value="'+obj.value+'" checked><div class="layui-unselect layui-form-checkbox"><span>'+obj.name+'</span><i class="layui-icon layui-icon-ok"></i></div>';
									paramSing = 1;
									return false;
								}
							});
							if(paramSing == 0){
								paramStr +='<input type="checkbox" name="priceInclude" title="'+piobj.name+'" value="'+piobj.value+'"><div class="layui-unselect layui-form-checkbox"><span>'+piobj.name+'</span><i class="layui-icon layui-icon-ok"></i></div>';
							}
						});
					} else {
						alertMsg(r);
					}
				}
			});
		}else{
			$.each(paramArray,function (pi, piobj) {
				if(piobj.type == 1){
					paramStr +='<input type="checkbox" name="priceInclude" title="'+piobj.name+'" value="'+piobj.value+'" checked><div class="layui-unselect layui-form-checkbox"><span>'+piobj.name+'</span><i class="layui-icon layui-icon-ok"></i></div>';
				}else{
					paramStr +='<input type="checkbox" name="priceInclude" title="'+piobj.name+'" value="'+piobj.value+'"><div class="layui-unselect layui-form-checkbox"><span>'+piobj.name+'</span><i class="layui-icon layui-icon-ok"></i></div>';
				}
			});
		}*/


		//加载列表数据
		loadUlList(couponsteatimeGuid,10);
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

			/*var div7 = $('<div class="layui-inline">');
			div7.append('<label class="layui-form-label">费用包含</label>');
			var div7_1 = $('<div class="layui-input-block" name="checkboxDiv">');
			div7_1.append(paramStr);*/
			var but = $('<button type="button" class="layui-btn layui-btn-sm layui-btn-normal editdelete" ><i class="layui-icon"></i> 删除</button>');
			div1_1.append('<label class="layui-form-label">时间段</label><div class="layui-input-inline"><input type="text" name="startEnd" id="startEnd"  autocomplete="off" lay-verify="required" class="layui-input startEnd"></div>');
			div1_2.append('<label class="layui-form-label">间隔分钟</label><div class="layui-input-inline"><input type="text" name="intervals"  autocomplete="off" lay-verify="required" class="layui-input"></div>');
			div1_3.append('<label class="layui-form-label" style="display: none">价格</label><div style="display: none" class="layui-input-inline"><input type="text" name="prices"  autocomplete="off" class="layui-input"></div>');
			div1_4.append('<label class="layui-form-label" style="display: none">原价</label><div style="display: none" class="layui-input-inline"><input type="text" name="costPrices"  autocomplete="off" class="layui-input"></div>');
			div1_5.append('<label class="layui-form-label" style="display: none">折扣</label><div style="display: none" class="layui-input-inline"><input type="text" name="discounts" autocomplete="off" class="layui-input"></div>')
			div1_6.append('<label class="layui-form-label">人数</label><div class="layui-input-inline"><input type="text" name="peopleNumber" lay-verify="required" autocomplete="off" class="layui-input"></div>')
			//div1.append(div1_1).append(div1_2).append(div1_3).append(div1_4).append(div1_5).append(div1_6).append(but).append(div7.append(div7_1));
			div1.append(div1_1).append(div1_2).append(div1_3).append(div1_4).append(div1_5).append(div1_6).append(but);
			$("#timeJsons").append(div1);
			form.render('checkbox');
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
		form.render();
	});
});


//加载列表数据
function loadUlList(couponsteatimeGuid,sum,date){
	//根据场地guid查询，时间段设置，渲染页面
	$.ajax({
		type : "post",
		url : T.serverebusiness + "business/couponsdateteatime/list",
		data : {t:(new Date()), couponsteatimeGuid:couponsteatimeGuid},
		async: false,
		success : function(r) {
			if(r.code == 0 && r.data){
				var dateJsonArr = new Array();
				/*$.each(r.data,function (i, obj) {
                    dateJsonArr.push(obj.date);
                });*/
				loadDateList(dateJsonArr,r.data,sum,date);
			} else {
				alertMsg(r.msg);
			}
		}
	});
}


//获取指定时间多少天后（前）的数组  dayCount 天数条件   date  时间条件
function getDateStr(dayCount,date) {
	//日期条件为空的话获取当前时间
	if(!isNull(date)){
		var myDate = new Date();
		var month = myDate.getMonth()+1;
		date = myDate.getFullYear() +"-"+ month +"-"+ myDate.getDate();
	}
	var dates = new Array();
	if(dayCount > 0){
		for(var i=0; i<dayCount; i++){
			var dd = new Date(date);
			dd.setDate(dd.getDate()+i);//获取AddDayCount天后的日期
			var y = dd.getFullYear();
			var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
			var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0
			dates.push(y+"-"+m+"-"+d);
		}
	}else{
		for(var i=0; i>dayCount; i--){
			var dd = new Date(date);
			dd.setDate(dd.getDate()+i);//获取AddDayCount天后的日期
			var y = dd.getFullYear();
			var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
			var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0
			dates.push(y+"-"+m+"-"+d);
		}
	}
	return dates;
}

//加载日期数据列表
function loadDateList(dateJsonArr,jsonData,sum,date) {
	$("#dateList").empty(); //先清空列表
	var defaultTimeJsonarr;
	if (isNull(defaultTimeJson)) {
		defaultTimeJsonarr = JSON.parse(defaultTimeJson);
	}

	var dateArr = getDateStr(sum,date);
	//循环10天数据
	for(var dateInt = 0; dateInt < dateArr.length; dateInt++){
		var sgin = 0;//标记用来跳出循环
		//判断这个10天中是否有日期设置
		$.each(jsonData,function (i, obj) {
			if(dateArr[dateInt] == formatDate(obj.date,"yyyy-MM-dd")){
				//有这个元素，加载后跳出循环进行下一次
				var li = $("<li>");
				li.append(dateArr[dateInt]+'<input name ="id" type ="checkbox" value ="'+obj.id+'" date="'+dateArr[dateInt]+'" id="'+dateArr[dateInt]+'" />');
				var li_ul = $("<ul class='timeList'>");
				var li_ul_li = $("<li>");
				li_ul_li.append('上午');
				var li_ul_li_ul = $("<ul class='timeItems'>");
				//上午数据需要循环
				li_ul_li_ul.append('<li></li>');
				var li_ul_li2 = $("<li>");
				li_ul_li2.append('下午');
				var li_ul_li_ul2 = $("<ul class='timeItems'>");
				//下午数据需要循环
				li_ul_li_ul2.append('<li></li>');

				//循环默认时间数据填充
				var timeJsonarr = JSON.parse(obj.timeJson);
				for(var timeInt = 0;timeInt < timeJsonarr.length;timeInt++){
					var startTime = timeJsonarr[timeInt].startTime;
					var index = startTime.lastIndexOf("\:");
					var	int = startTime.substring(0,index);
					if(parseInt(int) < 12){
						//上午
						li_ul_li_ul.append('<li>'+startTime+'</li>');
					}else{
						//下午
						li_ul_li_ul2.append('<li>'+startTime+'</li>');
					}
				}

				$("#dateList").append(li.append(li_ul.append(li_ul_li.append(li_ul_li_ul)).append(li_ul_li2.append(li_ul_li_ul2))));
				sgin = 1;
				return false;
			}
		});

		//没有这个元素进行，默认数据加载
		if(sgin == 0){
			var li = $("<li>");
			li.append(dateArr[dateInt]+'<input name ="id" type ="checkbox" value ="" date="'+dateArr[dateInt]+'" id="'+dateArr[dateInt]+'" />');
			var li_ul = $("<ul class='timeList'>");
			var li_ul_li = $("<li>");
			li_ul_li.append('上午');
			var li_ul_li_ul = $("<ul class='timeItems'>");


			var li_ul_li2 = $("<li>");
			li_ul_li2.append('下午');
			var li_ul_li_ul2 = $("<ul class='timeItems'>");


			//循环默认时间数据填充
			for(var defaultInt = 0;defaultInt < defaultTimeJsonarr.length;defaultInt++){
				var startTime = defaultTimeJsonarr[defaultInt].startTime;
				var index = startTime.lastIndexOf("\:");
				var	int = startTime.substring(0,index);
				if(parseInt(int) < 12){
					//上午
					li_ul_li_ul.append('<li>'+startTime+'</li>');
				}else{
					//下午
					li_ul_li_ul2.append('<li>'+startTime+'</li>');
				}
			}
			$("#dateList").append(li.append(li_ul.append(li_ul_li.append(li_ul_li_ul)).append(li_ul_li2.append(li_ul_li_ul2))));
		}
	}
}

//格式化日期字符串
function formatDate(date, format) {
	if (!date) return;
	if (!format) format = "yyyy-MM-dd";
	switch(typeof date) {
		case "string":
			date = new Date(date.replace(/-/, "/"));
			break;
		case "number":
			date = new Date(date);
			break;
	}
	if (!date instanceof Date) return;
	var dict = {
		"yyyy": date.getFullYear(),
		"M": date.getMonth() + 1,
		"d": date.getDate(),
		"H": date.getHours(),
		"m": date.getMinutes(),
		"s": date.getSeconds(),
		"MM": ("" + (date.getMonth() + 101)).substr(1),
		"dd": ("" + (date.getDate() + 100)).substr(1),
		"HH": ("" + (date.getHours() + 100)).substr(1),
		"mm": ("" + (date.getMinutes() + 100)).substr(1),
		"ss": ("" + (date.getSeconds() + 100)).substr(1)
	};
	return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {
		return dict[arguments[0]];
	});
}