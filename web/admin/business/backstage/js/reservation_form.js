$(function(){
$('legend').append(guid?'修改':'添加');
var api = {
	list:{url:T.serverebusiness + "business/reservation/list", type:"post"},
	info:{url:T.serverebusiness + "business/reservation/info", type:"get"},
	save:{url:T.serverebusiness + "business/reservation/save", type:"post"},
	update:{url:T.serverebusiness + "business/reservation/update", type:"post"},
	uploadFile:{url:T.serverebusiness + "/business/common/uploadimage",type:"post"}
}
layui.use('form', function() {
	var laydate = layui.laydate;
	var form = layui.form, layer = layui.layer, upload = layui.upload;

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
				icon: 5,
				title: "提示"
			});
		}
	});
	laydate.render({
		elem: '#startTime' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'datetime'
	});
	laydate.render({
		elem: '#endTime' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'datetime'
	});

	laydate.render({
		elem: '#timeJson' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'time'
		,format: 'HH:mm'
		,range: true
	});

	form.on('submit(save)', function(data) {
		var type = api.save.type;
		var url = api.save.url;
		if (guid) {
			ype = api.update.type;
			url = api.update.url;
		}
		if(data.field.state == undefined)
			data.field.state = 0;
		else 
			data.field.state = 1;

		data.field.price = parseInt(data.field.price*100);

		//地区编码
		data.field.addressCode = "86"+data.field.province+data.field.city+data.field.area;

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

	province(0);

	form.on("select(province)", function(data){
		city(data.value);
	});
	form.on("select(city)", function(data){
		area(data.value);
	});

	if(guid){
		$.ajax({
			type : api.info.type,
			url : api.info.url,
			data : {t:(new Date()), guid:guid},
			success : function(r) {
				if(r.code == 0 && r.data){
					if(isNull(r.data.addressCode)){
						var addressCode = r.data.addressCode;
						$('#province').val(addressCode.substring(2,4));
						city(addressCode.substring(2,4));
						$('#city').val(addressCode.substring(4,8));
						area(addressCode.substring(4,8));
						$('#area').val(addressCode.substring(8));
					}
					r.data.price = r.data.price/100;
					$("#upload-normal-img").attr("src",T.serverebusiness + r.data.img);
					form.val("form", r.data);
					$("#content").html(r.data.content);
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


	//加载省
	function province(code) {
		$("#province").empty();
		$.ajax({
			type : "post",
			url : T.serverebusiness + "security/area/threelevellink",
			data : {countryCode:86,code:code},
			async : false,
			success : function(r) {
				if (r.code === 0) {
					$.each(r.data,function (i, obj) {
						if(i == 0){
							$("#province").append('<option value="'+obj.code+'" selected="selected">'+obj.name+'</option>');
							city(obj.code);
						}else{
							$("#province").append('<option value="'+obj.code+'">'+obj.name+'</option>');
						}
					});
					form.render();
				} else {
					alertMsg(r);
				}
			}
		});
	}

	//根据省的code加载市
	function city(code) {
		$("#city").empty();
		$.ajax({
			type : "post",
			url : T.serverebusiness + "security/area/threelevellink",
			data : {countryCode:86,code:code},
			async : false,
			success : function(r) {
				if (r.code === 0) {
					$.each(r.data,function (i, obj) {
						if(i == 0){
							$("#city").append('<option value="'+obj.code+'" selected="selected">'+obj.name+'</option>');
							area(obj.code);
						}else{
							$("#city").append('<option value="'+obj.code+'">'+obj.name+'</option>');
						}
					});
					form.render();
				} else {
					alertMsg(r);
				}
			}
		});
	}

	//加载区县
	function area(code) {
		$("#area").empty();
		$.ajax({
			type : "post",
			url : T.serverebusiness + "security/area/threelevellink",
			data : {countryCode:86,code:code},
			async : false,
			success : function(r) {
				if (r.code === 0) {
					$.each(r.data,function (i, obj) {
						$("#area").append('<option value="'+obj.code+'">'+obj.name+'</option>');
					});
					form.render();
				} else {
					alertMsg(r);
				}
			}
		});
	}
});
});