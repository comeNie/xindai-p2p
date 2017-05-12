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
    	<div class="left_menu" style="margin-left:50px;margin-right:20px;background-color:#f2f2f2;width:140px;">
        	<!--<h2>关于我们</h2>-->
            <ul>
            	<li><a href="${base}/about/aboutUs.do">公司简介</a><img  style="display:none;" src="${base}/platform/img/aboutusbk.png"/></li>
                <li><a href="${base}/about/aboutZz.do">公司资质</a></li>
                 <li><a href="${base}/about/aboutUsCareers.do">招贤纳士</a></li>
               <li><a href="${base}/about/aboutUsPartner.do">合作伙伴</a></li>
                <li><a href="${base}/about/aboutUsContact.do">联系我们</a></li>
                <li  class="current"><a href="${base}/about/aboutUsAnnouncement.do">联系客服</a></li>
                <li><a href="${base}/about/aboutUsManager.do">管理团队</a></li>
                <li><a href="${base}/about/aboutUsInfo.do">社会责任</a></li>  
                <li><a href="${base}/about/aboutUsNetBuild.do">新手入门</a></li>
				<li><a href="${base}/about/aboutUsNews.do">官方公告</a></li>	
				<li><a href="${base}/about/sjctVip.do">世纪创投VIP</a></li>		
            </ul>            
		</div>
        <div class="right_con" style="width:820px;margin-top:10px;height:auto">
        	<div class="text">
	        	<div style="text-align:center">
	        	<h2>&nbsp;<font size="4">关于网站数据迁移的公告</font></h2>
	        	</div>
	        	<div style="text-align:center">
	        	<h3>&nbsp;<font size="2"> 发布时间：2014-07-29 21:13:46</font></h3>
	        	</div>
        	<p>&nbsp;&nbsp;</p>
            <p>尊敬的世纪创投用户：</p> 
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好！</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;感谢你们一直以来对世纪创投毫无保留的支持，为了能提供更好的用户体验，世纪创投集合十余开发人员，历时半年有余，全新开发自主网贷系统。现在，新系统开发已进入尾声，逐步开始网站更新工作。世纪创投将于2014年7月29日18:00起至2014年7月31日18:00进行数据迁移准备工作，在此期间，网站实名认证、银行卡审核、提现等功能暂停服务，其他功能正常运行，给你们带来的不便，我们深表歉意。</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我们坚信，你们的支持永远是世纪创投前进的动力！</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如您对网站的服务有任何的建议或意见，请联系世纪创投客服电话：400-888-7586。</p>  
			<p>&nbsp;</p> 
			<p>&nbsp;</p>
			<div style="text-align:right">
	            <p>世纪创投运营团队</p>
	            <p>2014年7月29日</p>  
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