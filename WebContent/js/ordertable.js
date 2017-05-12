/* 
$(function(){
	var $m = $(".num");
	var $n = $(".ordertable tr,.checktable tr");
	var $t = $(".ordertable th");
	var $b = $(".piclist li");
	
	$m.each(function(i,j){
		var i=1;
		$(j).find('.s1').click(function(){
			if(i>0){
				$(j).find("input").val(i-=1);
			}
		});
		$(j).find('.s2').click(function(){
			$(j).find("input").val(i+=1);
		});
	});
	
	$b.each(function(i,j){
		$(j).find('b').click(function(){
			$("<tr><td>Hello world!</td><td align='center'><div class='num'><span class='s1'>减</span><input type='text' value='1' /><span class='s2'>加</span></div></td><td align='center'>￥12</td><td align='center'><span class='ico_del'></span></td></tr>").insertAfter($t.parent());
			$('.ico_del').bind('click',function(){
				$(this).parent().parent().remove();
			});
			//alert($('tr').eq(1).html());
		});
		
	});
	 
	$n.each(function(i,j){
		$(j).find('.ico_del').click(function(){
			$(j).remove();
		});
	});

	$(".qkreg label input").click(function(){
		$(".qkreg p").toggleClass("regbox");
		$(".inreg").toggle();
	});

	
	
});
*/
function addNum(i){
	var inp = $(i).prev();
	var v = inp.val();
	inp.val(parseInt(v)+1);
	
}
function minusNum(i){
	var inp = $(i).next();
	var v = inp.val();
	if(v <= 0){
		inp.val(0);
	}else{
		inp.val(parseInt(v)-1);
	}
	
}
