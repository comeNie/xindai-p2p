<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户中心-首页-sjct888.com</title>
<!-- 移动端 我的投资页面 -->
<meta name="description" content="">
<meta name="author" content="">
<#include "/common/resource.ftl">
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="${base}/css/framework7.min.css">
<link rel="stylesheet" href="${base}/css/newRega205.css">
<link href="${base}/css/loginAPP.css" rel="stylesheet" type="text/css">
<link href="${base}/css/styleApp.css" rel="stylesheet" type="text/css">

</head>
<body class="newReg">
	<div class="logo">
		<img src="${base}/platform/img/logo.gif" />
	</div>


	<div class="regSuccess">
		<div class="regSuccess_t" style="padding-bottom: 0.85rem;">
			<div class="title"
				style="margin-left: 5%; margin-bottom: -0.2rem; padding: 0rem 0;">
				<span style="margin-top: 5px; text-align: center"><font
					style="font-size: 20px; color: #ffb23f">我的投资</font></span>
			</div>

			<div class="tab_table_N creditor auto_css" style="margin-top: 35px;">
				<div class="nav">
					<ul id="myCreditorNav">
						<li id="tab1" class="current"><a href="javascript:void(0)"
							style="margin-top: -14px">回收中</a></li>
						<li id="tab3"><a href="javascript:void(0)"
							style="margin-top: -14px">投标中</a></li>
						<li id="tab2"><a href="javascript:void(0)"
							style="margin-top: -14px">已结清</a></li>
					</ul>
				</div>
				<div class="clear"></div>
				<#include "/user/financial/mobileMyCreditor01.ftl"> <#include
				"/user/financial/mobileMyCreditor02.ftl"> <#include
				"/user/financial/mobileMyCreditor03.ftl">
				<div id="loading" class="load" style="display: none">
					<p>正在加载数据....</p>
				</div>
				<div id="nodata" class="load" style="display: none">
					<p>暂无数据</p>
				</div>
			</div>
		</div>
	</div>


	<div class="bottom">
		<a class="page" href="${base}/index.do"> <img
			src="${base}/images/page.png"><span style="margin-top: 4px;">主页</span>
		</a> <a class="lnd" href="${base}/userIndex/index.do"> <img
			src="${base}/images/lnd.png"><span style="margin-top: 4px;">账户</span></a>
		<a class="out" id="exitBtn" href="${base}/loginAction/logout.do">
			<img src="${base}/images/out.png"><span
			style="margin-top: 4px;">退出</span>
		</a>
	</div>


	<script type="text/javascript">
		var currentindex = 1;
		var itemPerPage = 10;//每页显示数据条数

		$(document).ready(function() {
			ajaxQuery1(1, true);
			changeTab();// 加载页签切换效果
		});

		function changeTab() {
			$("#myCreditorNav li").click(function() {
				var liId = $(this).attr('id');

				if (liId == 'tab1') {
					ajaxQuery1(1, true);
				}

				if (liId == 'tab2') {
					ajaxQuery2(1, true);
				}
				if (liId == 'tab3') {
					ajaxQuery3(1, true);
				}
				$(this).addClass("current").siblings().removeClass("current");
				$("div[class='myCreditor']").hide();
				$("#myCreditor_" + liId).show();
			});
		}
	</script>

</body>
</html>