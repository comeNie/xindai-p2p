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
                <li><a href="${base}/about/aboutUsInfo.do">社会责任露</a></li>  
                <li><a href="${base}/about/aboutUsNetBuild.do">新手入门</a></li>
				<li><a href="${base}/about/aboutUsNews.do">官方公告</a></li>	
				<li><a href="${base}/about/sjctVip.do">世纪创投VIP</a></li>	
            </ul>            
		</div>
        <div class="right_con" style="width:820px;float:right;margin-top:10px;height:auto">
        	<div class="text">
	        	<div style="text-align:center">
	        	<h3>&nbsp;<font size="4">关于世纪创投提现手续费变更的公告</font></h2>
	        	</div>
	        	<div style="text-align:center">
	        	<h3>&nbsp;<font size="2"> 发布时间：2014-07-31 10:45:28</font></h2>
	        	</div>
        	<p>&nbsp;&nbsp;</p>
            <p>尊敬的世纪创投用户：</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好！</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为了给广大新老用户营造更加简易、便捷的投资理财环境，自2014年8月1日起，世纪创投对充值、提现规则进行变更。具体变更明细如下：</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、更换第三方支付机构：汇潮支付；</p>  
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、费用：</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）充值费用：免费；</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）提现费用：提现金额×0.25% （注：禁止通过平台进行信用卡套现，一经发现，收取提现金额的2%）；单笔提现金额上限调至20万元。</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、提现到账时间：</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）工作日当天下午三点钟之前提交提现申请，到账时间为当日；</p> 
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）工作日当天下午三点钟之后提交提现申请，到帐日为一个工作日，非工作日顺延。 （注：节假日、双休日不作为工作日计算）</p> 
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