<html>
<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
		<meta name="mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${base}/css/framework7.min.css">
		<link rel="stylesheet" href="${base}/css/newRega205.css">
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
		</style>
	</head>
	<body class="newReg">
<div class="logo">
	<img src="${base}/platform/img/logo.gif" />
</div>

<form id="paymentForm" class="form-horizontal" action="payment.do" target="_blank" method="post" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
		<input type="hidden" id="bank" name="bank"/>
		<fieldset>
		<div class="regSuccess">
			<div class="regSuccess_t">
	   			<div class="title" >
	   				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>我要充值</span>
	   			</div>
	   			<hr style="height:1px;border:none;border-top:1px solid #F3F1EE;" />
	   			<div class="title2">
	   			<p class="red_text"><font style="color:#2E2E2E">填写充值金额：</font></p>
	   			<p class="red_text"><font style="color:#2E2E2E">账户余额：</font><font style="color:#ed0000"><#if userIndexVO.accountBalAmt??>${userIndexVO.accountBalAmt}<#else>0</#if>(元)</font></p>
	   			<p class="red_text"><font style="color:#2E2E2E;float:left">充值金额(元)：</font></p>
	   			<div class="code1">
                             <input type="text" style="height:45px"  name="amount" id="amount"  onblur=""/> 
                 
                 <p class="red_text"><font style="color:#2E2E2E;width:30%;margin-left: -5%;">充值费用：</font></p>
                 <input type="hidden" class="span3" name="recFee" id="recFee" value="${fee}" onblur="">
                  <span id="fee" style="margin-left:-60%;margin-top:40px;"><font style="color:#ed0000">${fee}元</font></span><span></span>
                 </div>
                 <p class="red_text" style="margin-top:40px"><font style="color:#FA9600;font-size:8px">温馨提示：充值未投资直接提现将收取${exceedRate}%提现费用</strong></p>
	   			<button style="border-radius:10px;font-size:18px" class="reg_btn" id="submit"  onclick="show_a();">充 值</button>
	   			</div>

	   			</div>
			</div>
			</fieldset>
 </form>

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
	<script type="text/javascript">
//<![CDATA[
var $repaymentItem= $(".repayment .item input");
	var $amount= $("#amount");
	var $fee= $("#fee");
	var $paymentForm= $("#paymentForm");
	var $submit= $("#submit");
	var $bank= $("#bank");
	var str;
	//银行选择
	$repaymentItem.click(function(){
		var $this = $(this);
		$this.closest(".repayment").find(":checkbox").attr("checked",false);
		$this.attr("checked",true);
		$bank.val($this.attr("bank"));
	});
	
	
	//银行的显示与隐藏
	  
	$("#otherBanks").click(function(){
	  if($("#myDiv").css("display")=="none"){
	     $("#myDiv").show();
	     $("#hideOthers").show();
	     $("#otherBanks").hide();
	  }
	  else{
	     $("#myDiv").hide();
	     $("#hideOthers").hide();
	      
	 }
	});
	
	$("#hideOthers").click(function(){
		  if($("#myDiv").css("display")=="none"){
		     $("#myDiv").show();
		     $("#hideOthers").show();
		     $("#otherBanks").hide();
		  }
		  else{
		     $("#myDiv").hide();
		     $("#hideOthers").hide();
		     $("#otherBanks").show();
		 }
		});
	

	//金额输入框
		$amount.on("keyup",function(){
			
			if(/^([1-9][\d]{0,7}|0)([\.]{0,1}[\d]{0,2})?$/.test($(this).val())){
			}else{
			  $amount.val("");
			}
			
			if($amount.val() == ""){
			    var rFee = $("#recFee").val();
			}else{
				var rFee = (parseFloat($amount.val()) * parseFloat($("#recFee").val())).toFixed(2);
			}
			
			$fee.text(rFee+"元");     
		});	
	
	
	//充值提交
	$submit.on("click",function(){
// 		if($bank.val()==""){
// 			showDialogNew(true,"请先选择银行！",'E','提醒信息');
// 			return false;
// 		}
		if($amount.val()==""){
			showDialogNew(true,"请输入正确的金额！",'E','提醒信息');
			return false;
		}
		if($bank.val()=="NOCARD"){
		    $bank.val("");
		}
		$paymentForm.submit();
	});
	

</script>
</html>
