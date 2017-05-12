// JavaScript Document


$(function(){
	
	// 图片左右翻滚
	var size = $('.slidebtn-01>ul>li').length;
	var frist = 0;
	var size1 = size-1;
	var datetime;
	var width=$(window).width();
	$(".slidepic-01 a").attr("style","width:"+width+"px;float:left;display: block;")
	
	$(window).resize(function(){
	var width=$(window).width();
		$(".slidepic-01 a").attr("style","width:"+width+"px;float:left;display: block;")
	
	});

	$('.slidebtn-01 li').click(function(){
		frist = $('.slidebtn-01 li').index(this);
		showpic(frist);
	}).eq(0).mouseover();
	
	$('.slidebox-01').hover(function(){
		clearInterval(datetime);
	},function(){
		datetime = setInterval(function(){
			showpic(frist)
			frist++;
			if(frist==size){
				frist=0;
			}
		},3000);
	}).trigger('mouseleave');

	function showpic(frist){
		var imgheight = $('.slidebox-01').width();
		$('.slidepic-01').stop(true,false).animate({left:-imgheight*frist},600)
		$('.slidebtn-01 li').removeClass('current').eq(frist).addClass('current');
	};
	 
		$(".table6 p span").each(function(i){
			$(this).click(function(){
				$(".table6 p span").attr("class","");
				$(this).attr("class","span");
				$(".table6 ul").hide();
				$(".table6 ul").eq(i).show();
			})
			
	}) 
	 $(document).ready(function () {
         var myar = setInterval('AutoScroll("#scrollDiv")', 3000);
         $("#scrollDiv").hover(function () { clearInterval(myar); }, function () { myar = setInterval('AutoScroll("#scrollDiv")', 3000) });
     });
})
function AutoScroll(obj) {
         $(obj).find("ul:first").animate({
             marginTop: "-49px"
         }, 500, function () {
             $(this).css({ marginTop: "0px" }).find("li:first").appendTo(this);
         });
     }



