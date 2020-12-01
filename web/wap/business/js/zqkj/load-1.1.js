$(function(){
	var v = "1.0.0";
	$("#footer").load("footer.html?v=" + v, function(){
		//console.log($(this).html());
		$(this).find('li').click(function(){
			switch($(this).index()) {
				case 0:
				   location.href = 'panicBuying_list.html?f=0';
				   break;
			     case 1:
			        location.href = 'H5_reservation_list.html?f=1';
			        break;
			     case 2:
					location.href = 'coupons_list.html?f=2';
			        break;
				case 3:
					location.href = 'H5_activity_list.html?f=3';
					break;
				default:
					location.href = 'my_info.html?f=4';
					break;
			}
			var f = T.p('f');
			if(f){
				this.dom.find('li').removeClass('select');
				this.dom.find('li').eq(this.index).addClass('select');
			} else {
				this.dom.find('li').removeClass('select');
			}
		});
	});
	$("#main-nav").load("main-nav.html?v=" + v);
});