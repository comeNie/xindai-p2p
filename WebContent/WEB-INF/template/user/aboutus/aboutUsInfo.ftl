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
                <li class="current"><a href="${base}/about/aboutUsInfo.do">社会责任</a></li>  
                <li><a href="${base}/about/aboutUsNetBuild.do">新手入门</a></li>
				<li><a href="${base}/about/aboutUsNews.do">官方公告</a></li>	
				<li><a href="${base}/about/sjctVip.do">世纪创投VIP</a></li>		
            </ul>            
		</div>
        <div class="right_con" style="width:820px;margin-top:10px;height:auto">
        	<div class="text">
        	<h2 class="about-title">&nbsp;社会责任</h2>
	          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;深圳市世纪创投互联网金融服务有限公司，一直致力于互联网金融。在如今实体经济受挫，整体大经济环境低迷的情况下，世纪创投作为互联网金融服务平台，从成立以来，一直努力帮助中小微企业解决融资难题，促进就业。为社会的发展，努力发光发热！</p> 
	          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;世纪创投严格把控每一个标的，对每一个借款人进行详尽的风险控制。为此，也制定出了一套严要求、高标准的风控体系！来保障投资者的资金安全！</p>
        	  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为了积极响应国家政策和号召，世纪创投与晋商银行签订了资金监管协议。引入银行对资金进行监督和管理，进一步保障资金的安全！</p>
        	   <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;世纪创投以人为本，非常注重员工的发展！无论新老员工，公司平时都注重对他们的专业知识的培训，让新员工能够尽快地融入公司，让老员工能够更加专业！提升自我的同时，更专业地为客户服务！</p>
        	  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;同时，世纪创投也注重环保、热心公益！相信，在公司全体同仁的努力下，世纪创投的明天会越来越美好！随着世纪创投的逐步壮大，世纪创投对社会的贡献也将越来越大！</p>
        	</div>
    	</div>
    	<p>&nbsp;</p>
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