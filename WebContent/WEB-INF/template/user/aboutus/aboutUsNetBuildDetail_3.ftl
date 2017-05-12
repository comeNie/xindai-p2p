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
                <li><a href="${base}/about/aboutUsAnnouncement.do">联系客服</a></li>
                <li><a href="${base}/about/aboutUsManager.do">管理团队</a></li>
                <li><a href="${base}/about/aboutUsInfo.do">社会责任</a></li>  
                <li class="current"><a href="${base}/about/aboutUsNetBuild.do">新手入门</a></li>
				<li><a href="${base}/about/aboutUsNews.do">官方公告</a></li>
				<li><a href="${base}/about/sjctVip.do">世纪创投VIP</a></li>			
            </ul>            
		</div>
        <div class="right_con" style="width:820px;float:right;margin-top:10px;height:auto">
        	<div class="text">
	        	<div style="text-align:center">
	        	<h3>&nbsp;<font size="4">福建网点</font></h3>
	        	</div>
	        	<h2 class="about-title">&nbsp;&nbsp;<font size="3">福建聚镒投资有限公司</font></h2>
	        	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;代理区域：厦门市</p>
	            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系地址：厦门市思明区鹭江道268号远洋大厦22F  </p> 
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话：0592-2218983  </p>
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系人：洪先生</p> 
				<h2 class="about-title">&nbsp;<font size="3">证照公示</font></h2>
				<div style="padding:0px;"><img src="${base}/platform/img/aboutusWeb3_1.png" align="center" hspace="12" /></div>
				<div style="padding:0px;"><img src="${base}/platform/img/aboutusWeb3_2.png" align="center" hspace="12" /></div>
				<div style="padding:0px;"><img src="${base}/platform/img/aboutusWeb3_3.png" align="center" hspace="12" /></div>
				<div style="padding:0px;"><img src="${base}/platform/img/aboutusWeb3_4.png" align="center" hspace="12" /></div>
	            <p>&nbsp;</p>
	            <div style="text-align:right">
            		<p><a href="${base}/about/aboutUsNetBuild.do"><strong><font size="3" color="blue">返回列表</font></strong></p>  
	            </div>
	            </div>
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