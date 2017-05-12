<!DOCTYPE html>
<html lang="en">
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
				<a href="${base}/loan/index.do">我要理财</a><a href="${base}/transfer/list.do">债券转让列表</a><a href="javascript:void(0);">债券转让详细页</a>
			</div>
        </div>

		<div class="new-info-page">
        	<div class="new-info-box-one">
				<img src="${base}/platform/img/ya${transferVo.loanTypeCd}.png" /><span class="title">${transferVo.loanName}</span><span class="username">${transferVo.loanCustId}</span>
			</div>
			<div class="bond-info-box-two">
				<div class="float1 float1-s">
					<div>转让金额</div>
					<div class="number">${transferVo.tranTtlAmt?string(',###.00')}</div>
					<div class="new-info-bottom">    
						<span class="conspan">还款方式</span><@sysCodeVal codeType='repayTypeCd' codeValue='${transferVo.repayTypeCd}' /></span>
						<div class="clear" style="margin:0px;"></div>
					</div>
					<div ><span class="conspan">剩余时间</span><span>${loanDetailVO.remainDay}天</span></div>
				</div>
				<div class="float2">
					<div><span class="sp-time1">剩余期限 </span> <span class="sp-year1" >年利率</span><span class="sp-prize" >接手奖金</span></div>
					<div class="number"><span class="sp-time1">${transferVo.surplusPeriod}个月</span><span class="sp-year1" >${transferVo.loanRate}%</span><span class="sp-prize" >￥${transferVo.takeAmt?string(',##0.00')}</span></div>
					
					<div class="new-info-bottom">
						<span class="conspan">保障机制</span><span class="conspan spancolor000">本息保障</span><span class="conspan2">起息日</span><span class="conspan spancolor000">隔日</span>   <!--起息日是否就是这样按隔日算-->
						<div class="clear" style="margin:0px;"></div>
					</div>
					<div><span class="conspan">转让进度:</span>
					  <div class="ProgressBar">
                          <span>${transferVo.completeness}%</span>
                          <div style="width:${transferVo.completeness}% ;"><span>${transferVo.completeness}%</span></div>
                      </div>
				   </div>
				</div>
		<#if transferVo.crtStatusCd !="2">
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
					<div class="con-one" >
						<span class="sp-one">剩余金额</span><span class="sp-mon sp-right">￥${transferVo.remainCount}*${transferVo.limitAmt}???</span>
						
					</div>
					<div class="con-two" >
						<span class="sp-one" >可用金额</span><span class="sp-mon">￥0.00</span><span class="sp-two" ><a href="${base}/userFund/myRecharge.do" style="color:blue">充值</a></span>
					</div>
					<div class="con-three" ><input type="text" value="   请输入金额" /></div>   <#--￥${custAccountVO.avaiBal}-->
					<div class="con-two" >
						<span class="sp-one" >可获得的接手奖金</span><span class="sp-two">￥0.00</span>     <#--￥${transferVo.takeAmt?string(',##0.00')}-->
					</div>
					<div ><div class="con-four" >购买</div></div>
				</div>
			</#if>
				<div class="clear"></div>
			</div>
        </div>

		<div class="new-info-page" style="margin-top:30px;margin-bottom:15px;">
        	<div class="new-info-box-one" style="margin:0px;">
				<div class="new-title" id="all-nav-box">
					<div class="new-tile-navon" vid="load-info-one">借款人档案</div>
					<div vid="load-info-three">还款记录</div>
					<div vid="load-info-four">转让记录</div>
					<#--<div vid="load-info-five">留言板</div>-->
					<div class="clear"></div>	
				</div>
			</div>
			 <#include "/platform/transferDetailNoLogin_jkxq.ftl">
             <#include "/platform/transferDetailNoLogin_hkbx.ftl">
             <#include "/platform/transferDetailNoLogin_jrjl.ftl">
  			 <#include "/platform/transferDetailNoLogin_lyb.ftl">
			
        </div>
            <input type="hidden" name="limitAmt" id="limitAmt" value="${transferVo.limitAmt}" /> 
	        <input type="hidden" name="usableBuyCount" id="usableBuyCount" value="${custAccountVO.buyCount}" /> 
			<input type="hidden" name="remainCount" id="remainCount" value="${transferVo.remainCount}" /> 
			<input type="hidden" name="loanApproveId" id="loanApproveId" value="${transferVo.loanApproveId}" /> <!--  发布编号id -->
			<input type="hidden" name="creditorId" id="creditorId" value="${transferVo.creditorId}" />    <!--转让申请id-->
     
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
			$("#load-info-three").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
		}
		
		if(divVid == 'load-info-three'){
			$("#load-info-three").show();
			$("#load-info-one").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
		}
		
		if(divVid == 'load-info-four'){
			$("#load-info-four").show();
			$("#load-info-one").hide();
			$("#load-info-three").hide();
			$("#load-info-five").hide();
		}
		
		if(divVid == 'load-info-five'){
			$("#load-info-five").show();
			$("#load-info-one").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
		}
		
		
	});
});
</script>
</body>
</html>