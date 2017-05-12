<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户中心-首页-sjct888.com</title>

<meta name="description" content="">
<meta name="author" content="">
<#include "/common/resource.ftl">
</head>
<body>
<div style="min-width:1349px">
<#include "/common/header.ftl">
<div class="aboutus" style="background-color:#f2f2f2;padding-top:5px;">
	<div class="con" style="border:0px;">
    	<div class="left_menu" style="margin-left:20px;margin-right:20px;width:140px;background-color:#f2f2f2;">
            <ul id="all">
            	<li lid="one" class="current"><a href="#">名词解释</a><img style="display:none;" src="${base}/platform/img/aboutusbk.png"/></li>
                <li lid="two"><a href="${base}/helpManager/ptjs.do" target="_self">平台介绍</a></li>
				<li lid="three"><a href="${base}/helpManager/wyjk.do"target="_self">我要借款</a></li>
				<li lid="four"><a href="${base}/helpManager/wylc.do"target="_self">我要理财</a></li> 
				<li lid="five"><a href="${base}/helpManager/lxhfy.do"target="_self">利息和费用</a></li>
				<li lid="six"><a href="${base}/helpManager/zxzh.do"target="_self">世纪创投账户</a></li>
            </ul>            
        </div>


        <div id="one" class="right_con" style="width:820px;float:right;margin-top:10px; height:auto;">
        	<div class="text">
            	<h2 class="about-title">名词解释</h2>
				<div id="contn">
					<div class="contnall">
						<span>1.借款人</span>
						<div class="contninfo">也称贷款人，是指已经或准备在网站上进行贷款活动的用户。凡18周岁以上的中国大陆地区公民，都可以成为借款人。</div>
					</div>

					<div class="contnall">
						<span>2.理财人</span>
						<div class="contninfo">也称投资人，是指已经或准备在网站上进行出借活动的用户。凡18周岁以上的中国大陆地区公民，都可以成为理财人。</div>
					</div>
				
					<div class="contnall">
						<span>3.借款标</span>
						<div class="contninfo">当借款人成功发布一个借款请求时，我们将包含各种贷款信息的该请求称为一个借款标。</div>
					</div>
					<div class="contnall">
						<span>4.招标</span>
						<div class="contninfo">是指一个借款人发出一个贷款请求，即创建一个借款标的行为。</div>
					</div>
					<div class="contnall">
						<span>5.投标</span>
						<div class="contninfo">是指理财人将其账户内指定的金额出借给其指定的借款标的行为。</div>
					</div>
					<div class="contnall">
						<span>6.满标</span>
						<div class="contninfo">是指一个借款标在投标期限内足额筹集到所需资金。</div>
					</div>
					<div class="contnall">
						<span>7.放款</span>
						<div class="contninfo">指一个借款标满标后且已符合放款标准，世纪创投将把所筹资金打入借款人在世纪创投的账户中，即成功贷款。</div>
					</div>
					<div class="contnall">
						<span>8.流标</span>
						<div class="contninfo">是指一个借款标的投标期限已过，但是贷款没有足额筹集齐，即贷款失败。</div>
					</div>
					<div class="contnall">
						<span>9.投标金额</span>
						<div class="contninfo">是指理财人对借款标进行投标的金额。</div>
					</div>
					<div class="contnall">
						<span>10.账户余额</span>
						<div class="contninfo">指用户账户的所有金额（含冻结金额和可用余额）。</div>
					</div>
					<div class="contnall">
						<span>11.可用金额</span>
						<div class="contninfo">指用户可自由支配的金额。</div>
					</div>
					
                   <div class="contnall">
						<span>12.展期</span>
						<div class="contninfo">指借款用户在协议约定时间7天内未进行足额还款，此标的的状态为展期。</div>
					</div>
					<div class="contnall">
						<span>13.逾期</span>
						<div class="contninfo">指借款用户超出协议约定时间7天未进行足额还款，此时标的状态为逾期。</div>
					</div>
					<div class="contnall">
						<span>14.严重逾期</span>
						<div class="contninfo">逾期时间超过30天时，从第31天开始该标的状态为严重逾期（针对借款用户）。</div>
					</div>
				  </div>
				</div>
            </div>
            </div>
            </div>
<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#contn").find("span").click(function(){
		
		if($(this).nextAll().is(":visible")==false){//表示下面所有的span都不可见的话
			$(this).attr("style","color:#00a8e8;"); //点击的这个就赋予颜色
			$(this).nextAll().show();               //同时让其显示     
		}else{
			$(this).attr("style","");              //样式就为空
			$(this).nextAll().hide();              //让其隐藏
		}
		
	});

	//$(".left_menu").find("li").click(function(){
	//$(".left_menu").find("li").removeClass("current");
	//$(this).addClass("current");
	//});
	
	
	
	
});
</script>
</body>
</html>