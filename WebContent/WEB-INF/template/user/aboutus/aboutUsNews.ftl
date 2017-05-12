<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>世纪创投</title>
<meta property="qc:admins" content="174237764760521101166654" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<meta name="description" content="">
<meta name="author" content="">
<#include "/common/resource.ftl">
</head>

<body>
<div style="min-width:1349px;max-width:100%">
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
                <li><a href="${base}/about/aboutUsNetBuild.do">新手入门</a></li>
				<li class="current"><a href="${base}/about/aboutUsNews.do">官方公告</a></li>
				<li><a href="${base}/about/sjctVip.do">世纪创投VIP</a></li>		
            </ul>            
		</div>
        <div class="right_con" style="width:820px;margin-top:10px;height:auto">
        	<div class="text">
                <h2 class="about-title">&nbsp;官方公告</h2>
                <ul class="news-list">
                <li>
                <a href="${base}/about/aboutUsAnnouncementDetail_14.do">
                    <span class="c">关于世纪创投收益建议调整的通知</span>
                    <span class="t">2015-12-31</span>
                </a>
                
                </li>
                <li>
                        <a href="${base}/about/aboutUsAnnouncementDetail_4.do">
                            <span class="c">世纪创投正式与晋商银行签订银行资金监管协议公告</span>
                            <span class="t">2015-11-17</span>
                        </a>
                        
                 </li>
                <li>
                        <a href="${base}/about/aboutUsAnnouncementDetail_5.do">
                            <span class="c">关于世纪创投VIP功能正式上线公告</span>
                            <span class="t">2015-11-09</span>
                        </a>
                        
                 </li>
                 <li>
                        <a href="${base}/about/aboutUsAnnouncementDetail_6.do">
                            <span class="c">关于世纪创投收益调整的通知</span>
                            <span class="t">2015-10-28</span>
                        </a>
                        
                 </li>
                <li>
                        <a href="${base}/about/aboutUsAnnouncementDetail_7.do">
                            <span class="c">【重磅福利第二弹】豪礼拿到手抽筋！！！</span>
                            <span class="t">2015-10-10</span>
                        </a>
                        
                    </li>
                	<li>
                        <a href="${base}/about/aboutUsAnnouncementDetail_8.do">
                            <span class="c">通知：首轮奖励活动将于10月8日截止</span>
                            <span class="t">2015-09-21</span>
                        </a>
                        
                    </li>
                    <li>
                        <a href="${base}/about/aboutUsAnnouncementDetail_9.do">
                            <span class="c">重磅消息！现在投资即有厚礼相送！</span>
                            <span class="t">2015-09-11</span>
                        </a>
                        
                    </li>
                    <li>
                     <a href="${base}/about/aboutUsNewsDetail_01.do	">
                            <span class="c">世纪创投平台上线公告</span>
                            <span class="t">2015-08-20</span>
                        </a>
                    </li>
                </ul>
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