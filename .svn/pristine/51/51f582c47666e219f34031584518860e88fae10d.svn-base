$(function(){
	var v = "1.0.1";
	$("#footer").load(T.basepath + "./template/footer.html?v=" + v, function(){
		//console.log($(this).html());
		$(this).find('li').click(function(){
			var f = T.p('f');
			if(f){
				this.dom.find('li').removeClass('select');
				this.dom.find('li[name="f' + f + '"]').addClass('select');
			} else {
				this.dom.find('li').removeClass('select');
			}
		});
	});
	$("#main-nav").load(T.basepath + "./template/main-nav.html?v=" + v);
});