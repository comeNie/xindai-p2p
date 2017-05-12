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
                <li  class="current"><a href="${base}/about/aboutUsAnnouncement.do">联系客服</a></li>
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
	        	<h3>&nbsp;<font size="4">关于深圳地区现场推广活动的公告</font></h2>
	        	</div>
	        	<div style="text-align:center">
	        	<h3>&nbsp;<font size="2"> 发布时间：2013-12-26 14:31:46</font></h2>
	        	</div>
        	<p>&nbsp;&nbsp;</p>
            <p>尊敬的用户：</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;深圳地区预定于将在如下地点安排现场推广活动，现公告如下：</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>时间：</strong>2013年12月27-28日&nbsp;&nbsp;&nbsp;&nbsp; <strong>地点：</strong>深圳市龙岗区阳光天健城花园</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>时间：</strong>2014年1月3-4日  &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>地点：</strong>深圳市龙岗区龙城国际花园</p>  
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;特此公告！</p> 
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<div style="text-align:right">
	            <p>世纪创投运营团队</p>
	            <p>2013年12月26日 </p>
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