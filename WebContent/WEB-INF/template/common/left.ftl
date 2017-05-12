 <div class="left">
   <div class="menu" id="menuLeft">
                	<h2 vid="userinfohead1" class="user_panelh2  user_panelh2bordercolor" ><img src="${base}/platform/img/userinfoicon01.png" />世纪创投</h2>
					<ul id="userinfohead1">
					<div><li><a href="${base}/userIndex/index.do?myVid=userinfohead1">我的首页</a></li></div>
					<li><a href="${base}/userSecurity/index.do?myVid=userinfohead1">个人信息</a></li>
					<li>
					<a href="${base}/userMessage/messageManage.do?myVid=us">我的推广</a></li>
   <!-- 	<li><a  href="${base}/userMessage/messageManage.do?myVid=userinfohead5">消息管理</a></li> -->
					</ul>
                    <h2 vid="userinfohead3" class="user_panelh2" ><img src="${base}/platform/img/userinfoicon02.png" />资金管理</h2>  <#--少一张图片-->
					<ul id="userinfohead3" style="display:none;"><li><a  href="${base}/userFund/myRecharge.do?myVid=userinfohead3">我要充值</a></li><li><a  href="${base}/userFund/myPayment.do?myVid=userinfohead3">交易流水</a></li><li><a  href="${base}/userFund/myDrawal.do?myVid=userinfohead3">我要提现</a></li><li><a  href="${base}/userFund/myBankCard.do?myVid=userinfohead3">我的银行卡</a></li></ul>
                    
                    <h2 vid="userinfohead2" class="user_panelh2" ><img src="${base}/platform/img/userinfoicon02.png" />投资管理</h2>  
					<ul id="userinfohead2" style="display:none;"><li><a  href="${base}/userFinancial/myCreditor.do?myVid=userinfohead2">我的投资</a></li>
					<#-- <li><a  href="${base}/userFinancial/myAutoTender.do?myVid=userinfohead2">自动投标</a></li> -->
					</ul>
                    
                    
                    
                    <h2 vid="userinfohead4" class="user_panelh2"><img src="${base}/platform/img/userinfoicon03.png" />借款管理</h2>
					<ul id="userinfohead4" style="display:none;"><li><a  href="${base}/userLoan/myLoanInt.do?myVid=userinfohead4">借款意向</a></li><li><a  href="${base}/userLoan/myLoan.do?myVid=userinfohead4">我的借款</a></li><li><a  href="${base}/userLoan/myRepay.do?myVid=userinfohead4">还款管理</a></li></ul>
                    <#-- 
                    <h2 vid="userinfohead5" class="user_panelh2"><img src="${base}/platform/img/userinfoicon04.png" />消息管理</h2>
					<ul id="userinfohead5" style="display:none;"><li><a  href="${base}/userMessage/noteSet.do?myVid=userinfohead5">通知设置</a></li><li><a  href="${base}/userMessage/messageManage.do?myVid=userinfohead5">消息管理</a></li></ul>
					
					-->
                   
                    
                </div>
  </div>
<script type = "text/javascript">
$(document).ready(function(){
	$("#menuLeft").find("h2").click(function(){
		$("#menuLeft").find("h2").removeClass("user_panelh2bordercolor");
		$("#menuLeft").find("ul").hide();
		
		$(this).addClass("user_panelh2bordercolor");
		$("#"+$(this).attr("vid")).show();
    });
   var myVid = "${myVid}";
   if(myVid != ""){
	var clickVid = $("h2[vid='${myVid}']");   //表示选取标记中的属性，并匹配
	clickVid.click();
   } 
});	
</script>
         