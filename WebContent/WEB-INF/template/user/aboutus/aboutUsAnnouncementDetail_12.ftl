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
	        	<h3>&nbsp;<font size="4">关于世纪创投全新平台上线的公告</font></h2>
	        	</div>
	        	<div style="text-align:center">
	        	<h3>&nbsp;<font size="2"> 发布时间：2014-07-31 15:05:28</font></h2>
	        	</div>
        	<p>&nbsp;&nbsp;</p>
            <p>尊敬的用户：</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;感谢大家一直以来对世纪创投的支持与陪伴，经过半年多的开发与准备,世纪创投终于迎来全新平台的上线。新的世纪创投从用户交互界面、功能模块、费用管理等方面做了优化与创新，让您的体验更加顺畅便捷。</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>新平台的变化主要如下：</strong></p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、简约主题，全新的板块图标和界面排版。</p>  
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、简化用户注册流程，通过手机即可快速完成开户。</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、清晰直观的账户结构，账目信息一目了然。</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、投标/还款方式多样化，资金安排灵活多变。</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、债权转让市场全新开放，债权快速变现。</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、充值免费，提现手续费下调，用户提现当日到账。</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、加入网络征信联盟，借款人信息同步上传。</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;世纪创投运营团队致力于为每一位用户提供更好的的服务，我们将继续倾听每一位用户的心声与建议，不断优化和完善平台功能。再次感谢您的信任与陪伴，世纪创投因你更加精彩！</p> 
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<div style="text-align:right">
	            <p>世纪创投运营团队</p>
	            <p>2014年7月31日 </p> 
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