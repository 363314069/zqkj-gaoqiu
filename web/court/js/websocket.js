var ws = function(config){
	var wsUrl = config.url;
	var websocket;
	function createWebSocket() {
		try {
			if(websocket != null){
				websocket.close()
				websocket = null;
			}
		} catch (e) {
		}
			
		try {
			if ('WebSocket' in window) {
				websocket = new WebSocket(wsUrl);
			} else if ('MozWebSocket' in window) {
				websocket = new MozWebSocket(wsUrl);
			} else {
				_alert("当前浏览器不支持websocket协议,建议使用Chrome浏览器",3000);
			}
			initEventHandle();
		} catch (e) {
			reconnect();
		}
	}
	// 初始化事件函数
	function initEventHandle() {
		websocket.onclose = function () {
		   reconnect();
		};
		websocket.onerror = function (err) {
			reconnect();
		};
		if(config.onopen){
			websocket.onopen = function () {
				config.onopen(websocket);
				//heartCheck.reset().start();      //心跳检测重置
			};
		}
		if(config.onmessage){
			websocket.onmessage = function(msg) {
				config.onmessage(msg);
				//console.log("数据已接收..." + received_msg);
			};
		} else {
			websocket.onmessage = function(msg) {
				//config.onmessage(msg);
				console.log("数据已接收..." + received_msg);
			};
		}
	}
	// 重连
	function reconnect() {
		if (reconnect.lockReconnect) return;
		reconnect.lockReconnect = true;
		setTimeout(function () {     //没连接上会一直重连，设置延迟避免请求过多
			createWebSocket();
			reconnect.lockReconnect = false;
		}, 2000);
	}
	createWebSocket();
	return websocket;
}




