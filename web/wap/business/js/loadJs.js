(function (root, factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD. Register as an anonymous module.
    define([], factory);
  } else if (typeof module === 'object' && module.exports) {
    // Node. Does not work with strict CommonJS, but
    // only CommonJS-like environments that support module.exports,
    // like Node.
    module.exports = factory();
  } else {
    // Browser globals (root is window)
    root.L = factory();
  }
}(this, function(){
	return {
		loadJS: function (url, success) {
			var domScript = document.createElement('script');
			domScript.src = url;
			success = success || function() {};
			//domScript.onload = domScript.onreadystatechange = function() {
			if (navigator.userAgent.indexOf("MSIE") > 0) {
				domScript.onreadystatechange = function() {
					if ('loaded' === this.readyState || 'complete' === this.readyState) {
						success();
						this.onload = this.onreadystatechange = null;
						this.parentNode.removeChild(this);
					}
				}
			} else {
				domScript.onload = function() {
					success();
					this.onload = this.onreadystatechange = null;
					this.parentNode.removeChild(this);
				}
			}
			document.getElementsByTagName('head')[0].appendChild(domScript);
		},
		loadCss: function (path) {
			var link = document.createElement('link');
			link.href = path;
			link.rel = 'stylesheet';
			link.type = 'text/css';
			head.appendChild(link);
			document.getElementsByTagName('head')[0].appendChild(domScript);
		},
		fileList: new Array(),
		function load(arr) {
			if (arr) {
				if (arr instanceof Array) {
					fileList = fileList.concat(arr);
				} else {
					fileList.push(arr);
				}
			}
		}
	}
})

