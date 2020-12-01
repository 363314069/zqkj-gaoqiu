//lat Y  lng x
var BMapGL = {
	court: {
		maxX: 0,
		maxY: 0,
		minX: 1000,
		minY: 1000,
		ratio: 1000000,	//比例
		sw: {"lat": 1000, "lng": 1000},
		ne: {"lat": 0, "lng": 0},
		viewBox:{width:800, height:800},
		getWidth:function(){
			return (this.maxX - this.minX) * this.ratio;
		},
		getHeight:function(){
			return (this.maxY - this.minY) * this.ratio;
		}
	},
	setX: function(x) {
		if (this.court.minX > x) {
			this.court.minX = x;
			this.court.sw.lat = x;
		}
		if (this.court.maxX < x) {
			this.court.maxX = x;
			this.court.ne.lat = x;
		}
	},
	setY: function(y) {
		if (this.court.minY > y) {
			this.court.minY = y;
			this.court.sw.lng = y;
		}
		if (this.court.maxY < y) {
			this.court.maxY = y;
			this.court.ne.lng = y;
		}
	},
	toxy: function(lng, lat, minX, minY) {
		if(!minX){
			minX = this.court.minX;
		}
		if(!minY){
			minY = this.court.minY;
		}
		return {
			x: (lng - minX) * this.court.ratio,
			y: (lat - minY) * this.court.ratio
		}
	},
	toSvgPoint: function(lng, lat, overlay) {
		var point = this.toxy(lng, lat, overlay.minX, overlay.minY);
		point.x = point.x * overlay.ratio + overlay.x;
		point.y = point.y * overlay.ratio + overlay.y;
		return point;
	},
	Bounds: function(bounds) {
		//"sw":{"lat":40.04901378768745,"lng":116.06479983943217},"ne":{"lat":40.06668555618903,"lng":116.08779646048761},"minX":116.06479983943217,"minY":40.04901378768745,"maxX":116.08779646048761,"maxY":40.06668555618903}
		var that = this;
		that.sw = {"lat":bounds.minY,"lng":bounds.minX};
		that.ne = {"lat":bounds.maxY,"lng":bounds.maxX};
		that.minX = bounds.minX;
		that.minY = bounds.minY;
		that.maxX = bounds.maxX;
		that.maxY = bounds.maxY;
		that.getSouthWest = function() {
			return that.sw
		}
		that.getNorthEast = function() {
			return that.ne
		}
		that.getBounds = function() {
			return that;
		}
		that.toString = function() {
			return "Bounds"
		};
	},
	Polyline: function(points) {
		var that = this;
		that.points = points;
		that.getPath = function() {
			return that.points
		};
		if (points.length > 0) {
			that.maxX = points[0].lng;
			that.maxY = points[0].lat;
			that.minX = points[0].lng;
			that.minY = points[0].lat;
		}
		points.forEach(function(point, j) {
			if (that.maxX < point.lng) {
				that.maxX = point.lng;
			}
			if (that.maxY < point.lat) {
				that.maxY = point.lat;
			}
			if (that.minX > point.lng) {
				that.minX = point.lng;
			}
			if (that.minY > point.lat) {
				that.minY = point.lat;
			}
		})
		that.getBounds = function() {
			return new BMapGL.Bounds({
				minX: that.minX, minY: that.minY, 
				maxX: that.maxX, maxY: that.maxY
			});
		}
		that.toString = function() {
			return "Polyline"
		};
	},
	Polygon: function(points) {
		var that = this;
		that.points = points;
		that.getPath = function() {
			return that.points
		};
		if (points.length > 0) {
			that.maxX = points[0].lng;
			that.maxY = points[0].lat;
			that.minX = points[0].lng;
			that.minY = points[0].lat;
		}
		points.forEach(function(point, j) {
			if (that.maxX < point.lng) {
				that.maxX = point.lng;
			}
			if (that.maxY < point.lat) {
				that.maxY = point.lat;
			}
			if (that.minX > point.lng) {
				that.minX = point.lng;
			}
			if (that.minY > point.lat) {
				that.minY = point.lat;
			}
		})
		that.getBounds = function() {
			return new BMapGL.Bounds({
				minX: that.minX, minY: that.minY, 
				maxX: that.maxX, maxY: that.maxY
			});
		}
		that.toString = function() {
			return "Polygon"
		};
	},
	Point: function(lng, lat) {
		var that = this;
		that.lng = lng;
		that.lat = lat;
		that.toString = function() {
			return "Point"
		};
		that.equals = function(e) {
			return (that.lat === e.lat && that.lng === e.lng)
		}
		that.getPoint = function(){
			return that.that;
		}
	},
	Circle: function(point, radius) {
		that.point = point;
		that.radius = radius;
		that.getRadius = function() {
			return that.radius;
		}
		that.getCenter = function() {
			return that.point;
		}
		var r = radius / 111000;
		that.maxX = point.lng + r;
		that.maxY = point.lat + r;
		that.minX = point.lng - r;
		that.minY = point.lat - r;
		that.getBounds = function() {
			return new BMapGL.Bounds({
				minX: that.minX, minY: that.minY, 
				maxX: that.maxX, maxY: that.maxY
			});
		}
		that.toString = function() {
			return "Circle"
		};
	},
	init: function (data){
		var that = this;
		var court = new Array();
		data.forEach(function(overlay, i){
			switch(overlay.type) {
				case 'marker': {
					//overlay.point = e.overlay.getPosition();
					//court.push(overlay.point);
					break;
				}
				case 'polyline': {
					//overlay.point = e.overlay.getPath();
					polyline = new BMapGL.Polyline(overlay.point);
					polyline.data = overlay;
					polyline.index = i;
					court.push(polyline);
					var bounds = polyline.getBounds();
					that.setX(bounds.minX);
					that.setY(bounds.minY);
					that.setX(bounds.maxX);
					that.setY(bounds.maxY);
					break;
				}
				case 'rectangle': {
					//ctx.fillStyle="#FF0000";
					bounds = new BMapGL.Bounds(overlay.point);
					bounds.data = overlay;
					bounds.index = i;
					court.push(bounds);
					var bounds = bounds.getBounds();
					that.setX(bounds.minX);
					that.setY(bounds.minY);
					that.setX(bounds.maxX);
					that.setY(bounds.maxY);
					break;
				}
				case 'polygon': {
					polygon = new BMapGL.Polygon(overlay.point);
					polygon.data = overlay;
					polygon.index = i;
					court.push(polygon);
					var bounds = polygon.getBounds();
					that.setX(bounds.minX);
					that.setY(bounds.minY);
					that.setX(bounds.maxX);
					that.setY(bounds.maxY);
					break;
				}
				case 'circle': {
					//中心点
					circle = new BMapGL.Circle(overlay.point, overlay.radius);
					circle.data = overlay;
					circle.index = i;
					court.push(circle);
					var bounds = circle.getBounds();
					that.setX(bounds.minX);
					that.setY(bounds.minY);
					that.setX(bounds.maxX);
					that.setY(bounds.maxY);
					break;
				}
			}
		});
		return court;
	},
	getSvg: function(overlay){
		var that = this;
		var viewBox = that.toxy(overlay.maxX, overlay.maxY, overlay.minX, overlay.minY);
		overlay.width = viewBox.width = overlay.height = viewBox.height = that.court.viewBox.width > that.court.viewBox.height ? that.court.viewBox.width : that.court.viewBox.height;
		//overlay.height = viewBox.height = that.court.viewBox.height;
		overlay.ratio = viewBox.ratio = (viewBox.width > viewBox.height ? viewBox.width : viewBox.height) / (viewBox.x > viewBox.y ? viewBox.x : viewBox.y);
		overlay.x = viewBox.x = (viewBox.width - viewBox.x * viewBox.ratio) / 2;
		overlay.y = viewBox.y = (viewBox.height - viewBox.y * viewBox.ratio) / 2;
		
		var svg = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 ' + that.court.viewBox.width + ' ' + that.court.viewBox.height + '">';
		svg += '<symbol id="svgpath' + overlay.index + '" viewBox="0 0 ' + viewBox.width + ' ' + viewBox.height + '">';
		var point = {};
		switch(overlay.toString()) {
			case 'Marker': {
				point = that.toxy(overlay.lng, overlay.lat);
				svg += '<circle cx="' + point.x + '" cy="' + point.y + '" r="10" stroke="black" stroke-width="2" fill="red"/>';
				break;
			}
			case 'Polyline': {
				var paths = new Array();
				overlay.points.forEach(function(point, i){
					point = that.toSvgPoint(point.lng, point.lat, overlay);
					paths.push(point.x + "," + point.y);
				});
				svg += '<polygon points="' + paths.join(' ') + '"/>';
				break;
			}
			case 'Rectangle': {
				var paths = new Array();
				overlay.points.forEach(function(point, i){
					point = that.toSvgPoint(point.lng, point.lat, overlay);
					paths.push(point.x + "," + point.y);
				});
				svg += '<polygon points="' + paths.join(' ') + '"/>';
				break;
			}
			case 'Polygon': {
				var paths = new Array();
				overlay.points.forEach(function(point, i){
					point = that.toSvgPoint(point.lng, point.lat, overlay);
					paths.push(point.x + "," + point.y);
				});
				svg += '<polygon points="' + paths.join(' ') + '"/>';
				break;
			}
			case 'Circle': {
				point = that.toSvgPoint(point.lng, point.lat, overlay);
				svg += '<circle cx="' + point.x + '" cy="' + point.y + '" r="10" stroke="black" stroke-width="5" fill="red"/>';
				break;
			}
		}
		//测试画起始点
		//var point = that.toSvgPoint(overlay.points[0].lng, overlay.points[0].lat, overlay);
		//svg += '<circle cx="' + point.x + '" cy="' + point.y + '" r="20" stroke="black" stroke-width="2" fill="red"></circle>';
		//svg += '<use xlink:href="#people" x="' + overlay.points[0].lng + '" y="' + overlay.points[0].lat + '" width="200" height="200"></use>';
		svg += '</symbol>';
		svg += '<symbol name="people" id="people' + overlay.index + '" viewBox="0 0 ' + viewBox.width + ' ' + viewBox.height + '">';
		//svg += '<use xlink:href="#people" x="100" y="100" width="200" height="200"></use>';
		//svg += '<circle cx="' + (point.x + point.) + '" cy="' + (point.y) + '" r="20" stroke="black" stroke-width="2" fill="red"></circle>';
		svg += '</symbol>';
		svg += '<g name="move"><g name="rotate"><g name="flip">'
		svg += '<use name="people" xlink:href="#people' + overlay.index + '" x="0" y="0" width="' + viewBox.width + '" height="' + viewBox.height + '" style="fill:none;stroke:purple;stroke-width:5;"></use>';
		svg += '<use name="svgpath" xlink:href="#svgpath' + overlay.index + '" x="0" y="0" width="' + viewBox.width + '" height="' + viewBox.height + '" style="fill:none;stroke:none;stroke-width:5;"></use></svg>';
		svg += '</g></g></g>';
		return svg;
	}
};

