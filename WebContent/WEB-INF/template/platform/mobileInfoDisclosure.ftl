<html>
<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
		<meta name="mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<script src="${base}/js/prefixfree.min.js"></script>
		<link href="${base}/css/loginAPP.css" rel="stylesheet" type="text/css">
		<link href="${base}/css/styleApp.css" rel="stylesheet" type="text/css">
		<style>
		.red_text {
			float: left;
			width: 90%;
			margin-left: 5%;
			height: 5px;
			line-height: 5px;
			font-size: 18px;
			text-indent: 5px;
			color: #000;
			}
			.realName_success .title, .regSuccess .title {
			padding: .1rem 0;
			font-size: .34rem;
			color:#ffb23f;
			}
		</style>
	</head>
	<body class="newReg">
<div class="logo">
	<img src="${base}/platform/img/logo.gif" />
</div>
<div class="title" style="margin-left:5%;margin-bottom: -0.2rem;padding: 0rem 0;">
	   				<span style="margin-top:5px;text-align:center"><font style="font-size:20px;color:#ffb23f">信息披露</font></span>
</div>
					
					
						<div style="text-align:center">
							<#if collateralInfoVO.collateralAtt1??>
						 		 <div>
								    <img style="width:200px;height:250px;margin-top:20px" src="${base}/${collateralInfoVO.collateralAtt1}" />
								  </div>
								  <#else></#if>
								  <#if collateralInfoVO.collateralAtt2??>
								   <div>
								    <img style="width:200px;height:250px; margin-top:20px" src="${base}/${collateralInfoVO.collateralAtt2}" />
								  </div>
								   <#else></#if>
								  <#if collateralInfoVO.collateralAtt3??>
								 <div>
								     <img style="width:200px;height:250px;margin-top:20px" src="${base}/${collateralInfoVO.collateralAtt3}" />
								  </div>
								   <#else></#if>
						</div>

<div class="bottom">
		<a class="page" href="${base}/index.do">
		<img src="${base}/images/page.png"><span style="margin-top: 4px;">主页</span>	</a>
		<a class="lnd" href="${base}/userIndex/index.do">
		<img src="${base}/images/lnd.png"><span style="margin-top: 4px;">账户</span></a>
		<a class="out" id="exitBtn" href="${base}/loginAction/logout.do">
		<img src="${base}/images/out.png"><span style="margin-top: 4px;">退出</span></a>
</div>
	</body>
	
</html>
