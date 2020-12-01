jQuery.fn.extend({
	court:function (data){
		return {
			courts: new Array(),
			courtul:$(this),
			data:data,
			init:function(){
				that = this;
				that.courts = BMapGL.init(data);
				that.courts.forEach(function(overlay, i){
					var li = $("<li>");
					overlay.courtli = li;
					li.attr("i",i);
					that.courtul.append(li);
					var con = $("<div>");
					con.addClass("con");
					var svgdiv = $("<div>");
					var svgimg = BMapGL.getSvg(overlay);
					svgdiv.addClass("bgsvg");
					svgdiv.append(svgimg);
					var imgdiv = $("<div>");
					var img = $("<img>");
					imgdiv.addClass("bgimg");
					img.attr("src", overlay.data.img);
					imgdiv.append(img);
					con.append(imgdiv).append(svgdiv);
					li.append(con);
					var svgobj = svgdiv.find("svg");
					svgobj.append('');
					setSvgAttr(svgobj, overlay);
				});
				return that;
			},
		}
	}
});
var setSvgAttr = function(svgobj, overlay){
	var dataobj = overlay.data;
	if(!dataobj.move){
		dataobj.move = [0,0];
	}
	if(!dataobj.scale){
		dataobj.scale = [1,1];
	}
	if(!dataobj.rotate){
		dataobj.rotate = 0;
	}
	if(!dataobj.flip){
		dataobj.flip = [1,1];
	}
	//var rotate = dataobj.flip[0] == 180 ? -1 : (dataobj.flip[1] == 180 ? -1 : 1);
	//svgobj.parent().css("transform", " translate(" + dataobj.move[0] + "px," + dataobj.move[1] + "px)");
	//svgobj.css("transform",rotate + " rotate(" + dataobj.rotate + "deg)");
	
	svgobj.find("g[name=move]").attr("transform","translate(" + dataobj.move.join(" ") + ")");
	
	//设置旋转
	svgobj.find("g[name=rotate]").attr("transform", "rotate(" + dataobj.rotate + "," + overlay.width / 2 + "," + overlay.height / 2 + ")");
	
	//设置翻转
	var flip = dataobj.flip;
	flip.translate = [dataobj.flip[0] == -1 ? overlay.width : 0, dataobj.flip[1] == -1 ? overlay.height : 0];
	svgobj.find("g[name=flip]").attr("transform", "translate(" + flip.translate.join(" ") + ")scale(" + flip.join(" ") + ")");
	
	//设置路径缩放
	var use = svgobj.find("use[name=svgpath]");
	var scale = dataobj.scale;
	use.attr("transform","scale(" + scale.join(" ") + ")");
}
var svg = {
	obj:null,
	overlay: null,
	reg: new RegExp("([0-9\-\.]+),([0-9\-\.]+)", ""),
	regNumber:new RegExp("[0-9\-\.]+"),
	/**
	 * 设置旋转
	 * @param {Object} val
	 */
	rotate: function(val){
		if(event.ctrlKey && event.button==0 ){
			val = val / 10;
		}
		var rotate = $("#rotate").val();
		if(this.regNumber.test(rotate)){
			val = parseInt(rotate) + val;
			if(val == 360 || val == -360){
				val = 0;
			}
		}
		$("#rotate").val(val);
		this.setTransform();
	},
	move: function(x, y){
		if(event.ctrlKey && event.button==0 ){
			x = x / 10;
			y = y / 10;
		}
		var str = $("#move").val();
		var val = {x:x, y:y};
		var r = str.match(this.reg);     
		if(r) { 
			 val.x += parseInt(r[1]);
			 val.y += parseInt(r[2]);
		}
		$("#move").val(val.x + "," + val.y);
		this.setTransform();
	},
	scale: function(x, y){
		if(event.ctrlKey && event.button==0 ){
			x = x / 10;
			y = y / 10;
		}
		var str = $("#scale").val();
		var val = {x:x * 100, y:y * 100};
		var r = str.match(this.reg);     
		if(r) { 
			 val.x += parseInt(parseFloat(r[1]) * 100);
			 val.y += parseInt(parseFloat(r[2]) * 100);
		} else {
			val.x += 100;
			val.y += 100;
		}
		val.x = val.x / 100;
		val.y = val.y / 100;
		$("#scale").val(val.x + "," + val.y);
		this.setTransform();
	},
	flip: function(x, y){
		var str = $("#flip").val();
		var val = {x:x, y:y};
		var r = str.match(this.reg);
		if(r) { 
			if(x == 1){
				val.x = 1;
				val.y = val.y * parseInt(r[2]);
			} else {
				val.x = val.x * parseInt(r[1]);
				val.y = 1;
			}
		}
		$("#flip").val(val.x + "," + val.y);
		this.setTransform();
	},
	save: function(){
		var div = $("#datajson");
		div.show();
		var content = div.find(".content");
		content.text(JSON.stringify(data));
	},
	initTransform: function(){
		var dataobj = this.overlay.data;
		if(!dataobj.move){
			dataobj.move = [0,0];
		}
		if(!dataobj.scale){
			dataobj.scale = [1,1];
		}
		if(!dataobj.rotate){
			dataobj.rotate = 0;
		}
		if(!dataobj.flip){
			dataobj.flip = [0,0];
		}
		$("#move").val(dataobj.move.join(","));
		$("#scale").val(dataobj.scale.join(","));
		$("#rotate").val(dataobj.rotate);
		$("#flip").val(dataobj.flip.join(","));
	},
	setTransform: function(){
		if(this.obj){
			var svgobj = this.obj;
			//旋转
			var rotate = $("#rotate").val();
			//偏移
			var move = $("#move").val();
			var r = move.match(this.reg);
			if(r) { 
				move = move.split(",");
			} else {
				move = [0,0];
			}
			//缩放
			var scale = $("#scale").val();
			r = scale.match(this.reg);
			if(r) { 
				scale = scale.split(",");
			} else {
				scale = [1,1];
			}
			
			var flip = $("#flip").val();
			r = flip.match(this.reg);
			if(r) { 
				flip = flip.split(",");
			} else {
				flip = [0,0];
			}
			var dataobj = this.overlay.data;
			dataobj.move =  move;
			dataobj.scale =  scale;
			dataobj.rotate =  rotate;
			dataobj.flip =  flip;
			setSvgAttr(svgobj, this.overlay);
		}
	}
}