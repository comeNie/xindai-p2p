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
                <li class="current"><a href="${base}/about/aboutUsContact.do">联系我们</a></li>
                <li><a href="${base}/about/aboutUsAnnouncement.do">联系客服</a></li>
                <li><a href="${base}/about/aboutUsManager.do">管理团队</a></li>
                <li><a href="${base}/about/aboutUsInfo.do">社会责任</a></li>  
                <li><a href="${base}/about/aboutUsNetBuild.do">新手入门</a></li>
				<li><a href="${base}/about/aboutUsNews.do">官方公告</a></li>
				<li><a href="${base}/about/sjctVip.do">世纪创投VIP</a></li>			
            </ul>            
		</div>
        <div class="right_con" style="width:820px;margin-top:10px;height:auto">
        	<div class="text">
            	<h2 class="about-title">&nbsp;深圳市世纪创投互联网金融服务有限公司 </h2>
            	<h3 class="about-title"><strong>客户服务</strong> </h3>
                <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果您在使用世纪创投（sjct888.com）的过程中有任何疑问请您与世纪创投客服人员联系。</p> 
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客服电话：400-7566-678(免长途费) </p>
				<#--<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在线帮助：http://www.sjct888.com/callcenter.do</p>--> 
				<h3 class="about-title"><strong>世纪创投互联网金融体验中心</strong> </h3>
                <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地址：太原市平阳路平阳景苑5幢1002<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务时间：周一至周日9:00-21:00 </p>
                <h3 class="about-title"><strong>北京</strong> </h3>
                <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;电话：010-6056-3862</p> 
                <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地址：北京通州区通州北苑万达广场C座3025</p> 
                <h3 class="about-title"><strong>深圳</strong> </h3>
                <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地址：深圳市前海深港合作区前湾一路1号A栋201室</p> 
                <p>&nbsp;</p>
                <p>&nbsp;</p>
                <p>&nbsp;</p>
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