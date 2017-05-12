<html>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<head>
<meta charset="utf-8">
<title>用户中心-首页-sjct888.com</title>
<link rel="stylesheet" href="${base}/css/framework7.min.css">
<link href="${base}/css/styleApp.css" rel="stylesheet" type="text/css">
<style>

.bai{
margin-top: 10px;
background-color: #fff;
border: 0px solid #ccc;
}

</style>
</head>
<body style="height:530px">
<div >
	<img style="width:100%" src="${base}/images/view.jpg" />
</div>

<div style="position:absolute;margin-top:-40px;margin-left:50px"><font style="color:#fff;font-size:18px">您好！${Session.loginInfo.userCode}</font></div>
	<div style="position:absolute;margin-top:-45px;margin-left:10px">
		<!-- 您是VIP -->
                      <#if userIndexVO.isVip == "1">
                        <a style="width: 31px;height: 30px;background: url(${base}/images/vip2.png) no-repeat;float: left;display: inline;" title="您是VIP" href="#"></a>
                       <!-- 您不是VIP -->
                      <#else>
                       <a style="width: 31px;height: 30px;background: url(${base}/images/vip1.png) no-repeat;float: left;display: inline;" title="您还不是VIP" href="#"></a>
                      </#if>
        </div>		
		<!-- Views-->
					<div class="page page-more" data-page="more" >							
							<div class="list-block" style="margin: 0px 0;">
								<ul  style="margin-bottom:200px">
									<li class="bai" style="margin-left:3%;margin-right:3%;height:40px">
<!-- 										<a href="about/about.html" > -->
<!-- 									        <div class="item-media"><i class="icon icon-profile"></i></div> -->
<!-- 									        <div class="item-content" style="padding-left: 15px;"> -->
									        <div >

											<table border="1"style="text-align:center;width:100%;">
												<tr>
													  <td width="49%"align="center"><button type="button" style="background:#ffb23f;margin-top:3px;border-radius:10px;width:100px;color:#fff;height:30px;"><a href="${base}/userFund/myRecharge.do?myVid=userinfohead3"><font style="color:#fff">充值</font></a></button></td>
													  <td width="2%"align="center"><div style="height:30px; width:1px; border-left:2px #E6E3E3 solid"></div></td>
													  <td width="49%"align="center"><button type="button" style="background:#f45221;margin-top:3px;border-radius:10px;width:100px;color:#fff;height:30px;"><a href="${base}/userFund/myDrawal.do?myVid=userinfohead3"><font style="color:#fff">提现</font></a></button></td>
												</tr>
											</table>
									      	</div>
									      	
<!-- 									      	</div> -->
									      	
<!-- 									    </a> -->
									</li>
									<li class="bai" style="margin-left:3%;margin-right:3%">
										
									        <div><i class="icon icon-bullhorn"></i></div>
									        <div class="item-content" style="padding-left: 15px;">
									        <div class="item-inner">
									        	<div class="item-title">账户可用余额：<font style="color:#ffb23f;font-size:22px"><em>${userIndexVO.usableBalAmt}</em>元</font></div>
									       
									        	<div class="item-after"><!-- <span class="badge">5</span> --></div>
									      	</div>
									      	</div>
									   
									</li>
								
									<hr style="height:1px;border:none;border-top:1px solid #FDF8F8;margin-left:40px;margin-right:40px" />
									<li class="bai" style="margin-left:3%;margin-right:3%;margin-top:0">
										<a href="${base}/userFund/myPayment.do?myVid=userinfohead3" style="width:100%" >
									        <div><i class="icon icon-stack"></i></div>
									        <div class="item-content" style="padding-left: 15px;width: 125px;">
												<img  src="${base}/images/liushui.png" >		        
									        	<div class="item-title">交易流水</div>
									        	
									      	
									      	</div>
									      	
									    	<font style="font-size:12px;color:#C2B9B9;float:right;margin-top:-8%;margin-right:10px">查看全部交易流水></font>
									    </a>
									</li>
									
									<li class="bai" style="margin-left:3%;margin-right:3%">
										<a href="${base}/userFinancial/myCreditor.do?myVid=userinfohead2"style="width:100%" >
									        <div><i class="icon icon-clipboard"></i></div>
									        <div class="item-content" style="padding-left: 15px;width: 125px;">
									        	<img  src="${base}/images/touzi.png">	
									        	<div class="item-title" >我的投资</div>
									      	</div>
									      	<div  style="float:right;margin-right:10px;margin-top:-8%;"><font style="font-size:12px;color:#C2B9B9;">查看全部投资明细></font></div>
									    </a>
									</li>
									<hr style="height:1px;border:none;border-top:1px solid #FDF8F8;margin-left:40px;margin-right:40px" />
									<!-- <li class="item-divider">为获得更好的使用体验，请定期更新版本</li> -->
									<li class="bai" style="margin-left:3%;margin-right:3%;margin-top:0">
										<a href="${base}/userMessage/messageManage.do?myVid=us"style="width:100%" >
									        <div><i class="icon icon-stack"></i></div>
									        <div class="item-content" style="padding-left: 15px;width: 142px;">
												<img  src="${base}/images/tuiguang.png">		        
									        	<div class="item-title">我的推广</div>
									        	<div class="item-after" ></div>
									      	
									      	</div>
									    </a>
									</li>
									<li class="bai" style="margin-left:3%;margin-right:3%">
										<a href="${base}/userLoan/myLoan.do?myVid=userinfohead4" style="width:100%">
									        <div><i class="icon icon-phone"></i></div>
									        <div class="item-content" style="padding-left: 15px;width:158px;">
									        <div class="item-inner">
									        <img  src="${base}/images/jiekuanren.png">
									        	<div class="item-title">借款管理</div>
									        	<div class="item-after"><!-- <span class="badge">5</span> --></div>
									      	</div>
									      	</div>
									    </a>
									</li>
									<li class="bai" style="margin-left:3%;margin-right:3%">
										<a href="${base}/userSecurity/index.do?myVid=userinfohead1"style="width:100%" >
									        <div><i class="icon icon-phone"></i></div>
									        <div class="item-content" style="padding-left: 15px;width: 157px;">
									        <div class="item-inner">
									        	<img  src="${base}/images/sava.png">
									        	<div class="item-title" >安全设置</div>
									        	<div class="item-after"><!-- <span class="badge">5</span> --></div>
									      	</div>
									      	</div>
									    </a>
									</li>
<!-- 									<li class="bai" style="margin-left:3%;margin-right:3%;"> -->
<!-- 										<a href="${base}/index.do" style="width:100%"> -->
<!-- 									        <div><i class="icon icon-phone"></i></div> -->
<!-- 									        <div class="item-content" style="padding-left: 15px;margin-bottom:200px;width: 160px;"> -->
<!-- 									        <div class="item-inner"> -->
<!-- 									       		<img  src="${base}/images/jiekuanren.png"> -->
<!-- 									        	<div class="item-title" >返回主页</div> -->
<!-- 									        	<div class="item-after"><span class="badge">5</span></div> -->
<!-- 									      	</div> -->
<!-- 									      	</div> -->
<!-- 									    </a> -->
<!-- 									</li> -->
									
								</ul>
							</div>
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