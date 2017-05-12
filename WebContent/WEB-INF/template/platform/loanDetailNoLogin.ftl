<!DOCTYPE html>
<html lang="en"  style="min-width:1349px">
<head>
<meta charset="utf-8">
<title>世纪创投</title>

<meta name="description" content="">
<meta name="author" content="">
<#include "/common/resource.ftl">
</head>
<body>
<div style="min-width:1349px">
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
        <div class="new-nav-two">
        	<div class="nav-two-box">
				<a href="${base}/loan/index.do">我要理财</a>&gt;<a href="${base}/loan/list.do">投资散标</a>&gt;<a href="javascript:void(0);">详情页</a>
			</div>
        </div>

		<div class="new-info-page">
        	<div class="new-info-box-one">
				<img src="${base}/platform/img/ya${loanDetailVO.loanTypeCd}.png"/><span class="title">${loanDetailVO.loanName}</span><span class="username">${loanDetailVO.userCode}</span>
			</div>
			<div class="new-info-box-two">
				<div class="repayfloat1">
					<div>标的总额</div>
					<div class="number">${loanDetailVO.loanAmt?string(',###.00')}元</div>
					<div class="new-info-bottom"><span class="conspan">保障机制</span><span>本息保障</span></div>
					<input type="hidden" name="loanApproveId" id="loanApproveId" value="${loanDetailVO.loanApproveId}" /> 
				
				</div>
				<div class="repayfloat2">
					<div><span class="sp-year">年化收益率 </span> <span class="sp-time" >期限</span></div>
					<div class="number"><span class="sp-year">${loanDetailVO.loanRate}%</span><span class="sp-time" >${loanDetailVO.loanTerm}月</span></div>
					<div class="new-info-bottom"><span class="conspan">还款方式</span><span><@sysCodeVal codeType='repayTypeCd' codeValue='${loanDetailVO.repayTypeCd}' /></span></div>
					
				</div>
				<#if loanDetailVO.approveStatusCd != "0">  <#--只要前台页面的操作下不是   投标中（购买），就显示下面的页面，反之就是else里的页面-->
					<div class="repayfloat3">
						<div class="repay-con" >
							<span class="sp-one">待还本息</span><span class="sp-two">???????</span>
						</div>
						<div class="repay-con" >
							<span class="sp-one" >剩余期数</span><span class="sp-two">????</span>
						</div>
						<div class="repay-con" ><span class="sp-one" >下一个还款日</span><span class="sp-two">?????</span></div>
					</div>
				<#else>
					<div class="float3">
					   <form action="tender.do" method="post" id="tender" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
						<div class="con-one" >
							<span class="sp-one">剩余金额</span><span><em>${loanDetailVO.tenderBalAmt?string(',##0.00')}</em>元</span>
							<#--<span class="sp-two" >(${loanDetailVO.tenderBalCount}份)</span>-->
						</div>
						<div class="con-two" >
							<span class="sp-one" >账户余额</span><span><em>0.00</em>元</span>  <#--直接给它赋0值，毕竟未登录-->
							<span class="sp-two" ><a href="${base}/userFund/myRecharge.do">充值</a></span>
						</div>
						<div class="con-three" >
						     <input type="text" name="crAmt"  id="crAmt" placeholder="请输入50的倍数" ><span >(元)</span></div>
							 <input type="hidden" name="tenderLimitAmt" id="tenderLimitAmt" value="${loanDetailVO.tenderLimitAmt}" /> 
							 <input type="hidden" name="loanApproveId" id="loanApproveId" value="${loanDetailVO.loanApproveId}" /> 		
							 <input type="hidden" name="usableBuyCount" id="usableBuyCount" value="${custAccountVO.buyCount}" /> 
							 <input type="hidden" name="tenderBalCount" id="tenderBalCount" value="${loanDetailVO.tenderBalCount}" /> 						 
						<div ><div class="con-four" style="cursor:pointer;" onclick="$('#tender').submit();" >投标</div></div>
					   </div>
					  </form>
					</div>  
				</#if>
				
				<div class="clear"></div>
			</div>
        </div>
        
        
        <div class="new-info-page" style="margin-top:30px;margin-bottom:15px;">
        	<div class="new-info-box-one" style="margin:0px;">
				<div class="new-title" id="all-nav-box">
					<div class="new-tile-navon" vid="load-info-one">借款人档案</div>
					<div vid="load-info-two">投标记录</div>
					<div vid="load-info-three">还款记录</div>
					<div vid="load-info-four">转让记录</div>
					<#--<div vid="load-info-five">留言板</div>-->
					<div class="clear"></div>	
				</div>
			</div>
				  
				  
				  <#include "/platform/loanDetail_jkrda_no_logic.ftl">
				  <#include "/platform/loanDetail_tbjl_no_logic.ftl">
				  <#include "/platform/loanDetail_hkjl_no_logic.ftl">
				  <#include "/platform/loanDetail_zrjl_no_logic.ftl">
				  <#include "/platform/loanDetail_lyb_no_logic.ftl">
		  </div>
    </div>

<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#all-nav-box div").click(function () {
		var divVid = $(this).attr('vid');
		$(this).addClass("new-tile-navon").siblings().removeClass("new-tile-navon");
		//$("div[class='new-info-box-two']").hide();   //里头的class是指  这几个记录中都具有的class
		if(divVid == 'load-info-one'){
			$("#load-info-one").show();
			$("#load-info-two").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
		}
		
		if(divVid == 'load-info-two'){
			$("#load-info-two").show();
			$("#load-info-one").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
		}
		
		if(divVid == 'load-info-three'){
			$("#load-info-three").show();
			$("#load-info-one").hide();
			$("#load-info-two").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
		}
		
		if(divVid == 'load-info-four'){
			$("#load-info-four").show();
			$("#load-info-one").hide();
			$("#load-info-two").hide();
			$("#load-info-three").hide();
			$("#load-info-five").hide();
		}
		
		if(divVid == 'load-info-five'){
			$("#load-info-five").show();
			$("#load-info-one").hide();
			$("#load-info-two").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
		}
	});
});
</script>
</body>
</html>