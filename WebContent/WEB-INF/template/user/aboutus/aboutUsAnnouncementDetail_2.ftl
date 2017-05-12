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
<#include "/common/header.ftl">
	<div class="aboutus" style="background-color:#f5f5f5;padding-top:5px;">
	<div class="con" style="border:0px;">
    	<div class="left_menu" style="margin-left:20px;margin-right:20px;background-color:#f2f2f2;width:140px;">
        	<!--<h2>关于我们</h2>-->
            <ul>
            	<li><a href="${base}/about/aboutUs.do">公司简介</a><img  style="display:none;" src="${base}/platform/img/aboutusbk.png"/></li>
                  <li><a href="${base}/about/aboutZz.do">公司资质</a></li>
                  <li><a href="${base}/about/aboutUsCareers.do">招贤纳士</a></li>
               <li><a href="${base}/about/aboutUsPartner.do">合作伙伴</a></li>
                <li><a href="${base}/about/aboutUsContact.do">联系我们</a></li>
                <li  class="current"><a href="${base}/about/aboutUsAnnouncement.do">官方新闻</a></li>
                <li><a href="${base}/about/aboutUsManager.do">管理团队</a></li>
                <li><a href="${base}/about/aboutUsInfo.do">社会责任</a></li>  
                <li><a href="${base}/about/aboutUsNetBuild.do">新手入门</a></li>
				<li><a href="${base}/about/aboutUsNews.do">官方公告</a></li>	
				<li><a href="${base}/about/sjctVip.do">世纪创投VIP</a></li>	
            </ul>            
		</div>
        <div class="right_con" style="width:820px;float:right;margin-top:10px;height:auto">
        	<div class="text">
	        	<div style="text-align:center">
	        	<h2>&nbsp;<font size="4">关于联通运营商网络升级的公告</font></h2>
	        	</div>
	        	<div style="text-align:center">
	        	<h3>&nbsp;<font size="2">发布时间：2013-12-26 15:01:54 </font></h3>
	        	</div>
        	<p>&nbsp;&nbsp;</p>
            <p>尊敬的世纪创投用户:</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好！</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;接联通运营商通知,为提高网络稳定性，提供更加流畅的网络服务,联通运营商将于2013年12月25日下午7:00至2013年12月30日对线路进行升级调整。在此期间, 联通用户访问该网站将会出现登入迟缓的现象，由此给您带来的不便，敬请谅解！</p> 
			<p>&nbsp;</p>
			 
			<p>&nbsp;</p>
			<div style="text-align:right">
	            <p>世纪创投运营团队</p>
	            <p>2013年12月26日</p>
	            <p>&nbsp;</p>
            	<p><a href="${base}/about/aboutUsAnnouncement.do"><strong><font size="3" color="blue">返回列表</font></strong></p> 
            </div>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
        	</div>
    	</div>
</div>

            <div class="clear"></div>
 <#include "/common/footer.ftl">
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