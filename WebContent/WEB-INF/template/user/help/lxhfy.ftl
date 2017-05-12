<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户中心-首页-sjct888.com</title>

<meta name="description" content="">
<meta name="author" content="">
<#include "/common/resource.ftl">
</head>
<body>
<div style="min-width:1349px;">
<#include "/common/header.ftl">
<div class="aboutus" style="background-color:#f2f2f2;padding-top:5px;">
	<div class="con" style="border:0px;">
    	<div class="left_menu" style="margin-left:6%;margin-right:20px;width:140px;background-color:#f2f2f2;">
           <ul id="all">
            	<li lid="one"><a href="${base}/helpManager/help.do">名词解释</a></li>
                <li lid="two"><a href="${base}/helpManager/ptjs.do" target="_self">平台介绍</a></li>
				<li lid="three"><a href="${base}/helpManager/wyjk.do" target="_self">我要借款</a></li>
				<li lid="four"><a href="${base}/helpManager/wylc.do" target="_self">我要理财</a></li> 
				<li lid="five" class="current"><a href="${base}/helpManager/lxhfy.do" target="_self">利息和费用</a><img style="display:none;" src="${base}/platform/img/aboutusbk.png"/></li>
				<li lid="six"><a href="${base}/helpManager/zxzh.do" target="_self">世纪创投账户</a></li>
            </ul>           
        </div>


         <div class="right_con" id="five" style="width:78%;float:right;margin-top:10px; height:auto;">
        	<div class="text">
              <h2 class="about-title">理财人费用</h2>
				<div id="contn">
				    <div class="contnall">
					  <span>1.充值提现费用</span>
						<div class="contninfo">充值费用：免费; 提现费用：每笔3元 （温馨提示：为防止恶意套现，充值未投资金额直接提现将收取1.8%手续费）
			            </div>
			        </div>    

<!-- 					<div class="contnall"> -->
<!-- 						<span>2.投资信息管理费</span> -->
<!-- 						<div class="contninfo">世纪创投将收取理财人的正常利息收益（不包含展期利息、逾期利息）的5%的费用作为投资信息管理费。</div> -->
<!-- 					</div> -->
				
<!-- 					<div class="contnall"> -->
<!-- 						<span>3.债权转让费用</span> -->
<!-- 						<div class="contninfo">债权转让的费用为转让管理费。平台向转出人收取，不向购买人收取任何费用。 -->
<!-- 											       转让管理费=成交金额*转让管理费率，转让管理费率目前按千分之三收取，具体金额以债权转让页面显示为准。 -->
<!-- 											      债权转让管理费在成交后直接从成交金额中扣除，不成交平台不向用户收取转让管理费。</div> -->
<!-- 					</div> -->
					
					
					
					
				<h2 class="about-title">借款人费用</h2>
				 <div id="contn">
<!-- 					<div class="contnall"> -->
<!-- 						<span>1.借款管理费</span> -->
<!-- 						<div class="contninfo">世纪创投每月收取借款人借款本金的千分之三作为平台的借款管理费。</div> -->
<!-- 					</div> -->
					<div class="contnall">
						<span>1.违约金</span>
						<div class="contninfo">当用户的借款发生逾期时，正常利息停止计算，此时需要承担违约金，有两种情况：
												<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）.如借入方未按本协议约定支付利息的，借入方应当按照累计预期利息、逾期天数向贷出方支付违约金，计算公式如下：
												 违约金=（（本金*年利率）/365）*3*逾期天数
												</p>
												<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）.如借款到期后，借入方未按本协议约定偿还本金，则借入方应当向贷出方支付借款本金10%的违约金。（如管理方代为支付借款本息，管理方有权按照前述约定向借入方主张违约责任，且违约金由管理方所有。）</p></div>
					</div>
					
					
<!-- 					<div class="contnall"> -->
<!-- 						<span>3.展期罚息</span> -->
<!-- 						<div class="contninfo">当借款人的借款发生展期时，正常利息停止计算，按照下面公司计算展期罚息： -->
<!--                                                                                                                                             展期罚息=展期本息×展期天数×年化借款利率×（100%+30%）/360</div> -->
<!-- 					</div> -->
					<div class="contnall">
						<span>2.世纪创投的借款利率范围</span>
						<div class="contninfo">
						世纪创投目前的借款年利率最低5%，最高年利率为16.8%。借款人在利率范围内根据自身资质和承受能力自行设定利率，同等条件下利率越高，满标概率越高。在世纪创投平台上借贷的最高年利率设定为不高于同期银行贷款年利率的4倍，且随着银行贷款利率的调整，世纪创投的利率上限也将随之调整。</div>
					</div>
					<div class="contnall">
						<span>3.逾期催收管理费</span>
						<div class="contninfo">借入方应严格履行还款付息义务，如借入方逾期还款超过3天，管理方将收取平台逾期催收费作为网站电话提醒和催收服务的费用。平台逾期催收费最低50元，最高为借款本金的1%。</div>
					</div>
<!-- 					<div class="contnall"> -->
<!-- 						<span>6.逾期管理费</span> -->
<!-- 						<div class="contninfo">借款人的借款发生逾期时，这次借款管理费按照下面公司计算逾期管理费 -->
<!--                                                                                                                                              逾期管理费=逾期本息×万分之二×逾期天数</div> -->
<!-- 					</div> -->
<!-- 					<div class="contnall"> -->
<!-- 						<span>7.充值提现费用</span> -->
<!-- 						<div class="contninfo">充值费用：免费 -->
<!-- 												提现费用：提现金额×0.25% -->
<!-- 												（注：禁止通过平台进行信用卡套现，一经发现，收取提现金额的2%）。</div> -->
<!-- 					</div> -->
					
				 </div>
				</div>	
			 
			  </div>	
				 
				 
				  </div>
				</div>
            </div>
<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#contn").find("span").click(function(){
		
		if($(this).nextAll().is(":visible")==false){//表示下面所有的span都不可见的话
			$(this).attr("style","color:#00a8e8;"); //点击的这个就赋予颜色
			$(this).nextAll().show();               //同时让其显示     
		}else{
			$(this).attr("style","");              //样式就为空
			$(this).nextAll().hide();              //让其隐藏
		}
		
	});

	//$(".left_menu").find("li").click(function(){
	//$(".left_menu").find("li").removeClass("current");
	//$(this).addClass("current");
	//});
});
</script>
</body>
</html>