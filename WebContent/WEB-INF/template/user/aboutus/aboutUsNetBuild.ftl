<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>世纪创投</title>
<meta property="qc:admins" content="174237764760521101166654" />
<meta name="description" content="">
<meta name="author" content="">
<#include "/common/resource.ftl">
</head>

<body>
<div style="min-width:1349px">
<#include "/common/header.ftl">
	<div class="aboutus" style="background-color:#f5f5f5;padding-top:5px;">
	<div class="con" style="border:0px;">
    	<div class="left_menu" style="margin-left:50px;margin-right:20px;background-color:#f2f2f2;width:140px;">
        	<!--<h2>关于我们</h2>-->
            <ul>
            	<li><a href="${base}/about/aboutUs.do">公司简介</a><img  style="display:none;" src="${base}/platform/img/aboutusbk.png"/></li>
                 <li><a href="${base}/about/aboutZz.do">公司资质</a></li>
                  <li><a href="${base}/about/aboutUsCareers.do">招贤纳士</a></li>
               <li><a href="${base}/about/aboutUsPartner.do">合作伙伴</a></li>
                <li><a href="${base}/about/aboutUsContact.do">联系我们</a></li>
                <li><a href="${base}/about/aboutUsAnnouncement.do">联系客服</a></li>
                <li><a href="${base}/about/aboutUsManager.do">管理团队</a></li>
                <li><a href="${base}/about/aboutUsInfo.do">社会责任</a></li>  
                <li class="current"><a href="${base}/about/aboutUsNetBuild.do">新手入门</a></li>
				<li><a href="${base}/about/aboutUsNews.do">官方公告</a></li>
				<li><a href="${base}/about/sjctVip.do">世纪创投VIP</a></li>			
            </ul>            
		</div>
        <div class="right_con" style="width:820px;margin-top:10px;height:auto">
        	<div class="text">
        	<h2 class="about-title">&nbsp;新手入门</h2>
        	<h3 class="about-title">&nbsp;如何注册</h3>
        	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.进入世纪创投所首页，点击右上角“注册”按钮。</p>
        	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.此时您已进入注册页面，根据提示信息，填写您的注册昵称、密码、确认密码、手机号、推荐码、验证码、点击“下一步 ”输入手机验证码。</p>
        	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.为了保证您账户的安全及享受网站更多的服务，请在注册完成后尽快进行实名认证。</p>
            <h3 class="about-title">&nbsp;如何投资</h3>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.登录个人账户，点击“我要投资”，选择一个未投满的标，点击“投资”。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.进入“投资”详情页后，点击“充值”选择银行卡充值。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.充值完成后，输入投标金额后确认投标，即可完成投资。</p>
            <h3 class="about-title">&nbsp;如何提现</h3>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.登录个人账户，点击“提现”。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.选中绑定的银行卡，填入提现金额。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.点击“提现申请”即完成提现步骤。（当日到账，周六日、节假日不支持提现）</p>
            <h3 class="about-title">&nbsp;如何推荐别人注册</h3>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.登录个人账户，进行任意一笔基本投资。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.完成投资后，点开“我的推广”即可获得自己的专属二维码及专属链接。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.让他人扫码你的专属二维码，或者直接点击你的专属链接，即可进入注册页面。</p>
            </div>
        </div>
    </div>
</div>

            <div class="clear"></div>
 <#include "/common/footer.ftl">
 </div>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
    <!--[if lte IE 6]>
    <script type="text/javascript" src="js/bootstrap-ie.js"></script>
    <![endif]-->
<script type="text/javascript">

//<![CDATA[
$(document).ready(function(){


	$(".left_menu").find("li").click(function(){
	$(".left_menu").find("li").removeClass("current");
	$(this).addClass("current");
	});
});
 //]]>
 
</script>  
</body>
</html>