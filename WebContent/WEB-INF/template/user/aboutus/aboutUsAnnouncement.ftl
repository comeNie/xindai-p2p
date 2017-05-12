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
                <h2 class="about-title">&nbsp;联系客服</h2>
                 <div>
           
            <p style="text-align:center"><i><font style="font-size:24px">深圳市世纪创投互联网金融服务有限公司</font></i>（客服中心）</p>
            <div>
							<ol  style="float:left;padding:4%">
									<li>
										<a target="_blank" href="#">
											<img  src="${base}/images/kefu1.png" src="${base}/images/kefu1.jpg" title="客服专员—小詹" /></a>
									</li>
									<li style="text-align:center">客服—秋子</li>
									<li style="text-align:center">
										<a target="_blank"href="#"title="点击这里给我发消息"><img src="${base}/images/kefu.jpg"/></a>
									</li>
									<li style="text-align:center;padding:5px">
										<a target="_blank"href="#"title="点击这里给我发消息"><img src="${base}/images/weixin.jpg"/>sjct666888</a>
									</li>
								</ol>

							<ol  style="float:left;padding:4%">
									<li>
										<a target="_blank" href="#">	
											<img  src="${base}/images/kefu2.png" src="images/hslogo_42.jpg" title="客服主管-小田" /></a>
									</li>
									<li style="text-align:center">客服-安琪</li>
									<li style="text-align:center">
										<a target="_blank"href="#"title="点击这里给我发消息"><img src="${base}/images/kefu.jpg"/></a>
									</li>
									<li style="text-align:center;padding:5px">
										<a target="_blank"href="#"title="点击这里给我发消息"><img src="${base}/images/weixin.jpg"/>sjct111</a>
									</li>
								</ol>
								<ol  style="float:left;padding:4%">
									<li>
										<a target="_blank" href="#">
											<img  src="${base}/images/kefu3.png" src="images/hslogo_42.jpg" title="客服主管-小田" /></a>
									</li>
									<li style="text-align:center">客服-彩彩</li>
									<li style="text-align:center">
										<a target="_blank"href="#"title="点击这里给我发消息"><img src="${base}/images/kefu.jpg"/></a>
									</li>
									<li style="text-align:center;padding:5px">
										<a target="_blank"href="#"title="点击这里给我发消息"><img src="${base}/images/weixin.jpg"/>sjctqun1</a>
									</li>
								</ol>
								<ol  style="float:left;padding:4%">
									<li>
										<a target="_blank" href="#">
											<img  src="${base}/images/kefu4.png" src="images/hslogo_42.jpg" title="客服主管-小田" /></a>
									</li>
									<li style="text-align:center">客服-筱筱</li>
									<li style="text-align:center">
										<a target="_blank"href="#"title="点击这里给我发消息"><img src="${base}/images/kefu.jpg"/></a>
									</li>
									<li style="text-align:center;padding:5px">
										<a target="_blank"href="#"title="点击这里给我发消息"><img src="${base}/images/weixin.jpg"/>sjctqun2</a>
									</li>
								</ol>
							</div>
        </div>
                
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