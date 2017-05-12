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
					<div class="number">￥<em style="color:#f60;">${transferVo.tranTtlAmt?string(',##0.00')}</em></div>
					<div class="new-info-bottom">    
						<span class="conspan">还款方式</span><@sysCodeVal codeType='repayTypeCd' codeValue='${transferVo.repayTypeCd}' /></span>
						<div class="clear" style="margin:0px;"></div>
					</div>
					<div  ><span class="conspan">剩余时间</span><span ><em style="color:#f60;">${loanDetailVO.remainDay}</em>天</span></div>
				</div>
				<div class="float2">
					<div><span class="sp-time1">剩余期限 </span> <span class="sp-year1" >年利率</span><span class="sp-prize" >接手奖金</span></div>
					<div class="number"><span class="sp-time1"><em style="color:#f60;">${transferVo.surplusPeriod}</em>个月</span><span class="sp-year1" ><em style="color:#f60;">${transferVo.loanRate}</em>%</span><span class="sp-prize" >￥<em style="color:#f60;">${transferVo.takeAmt?string(',##0.00')}</em></span></div>
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
				<div class="float3">
					<div class="con-one" >
						<span class="sp-one">剩余金额</span><span class="sp-mon sp-right" >￥<em style="color:#f60;">${transferVo.remainCount}*${transferVo.limitAmt}???</em></span>
					</div>
					<div class="con-two" >
						<span class="sp-one" >可用金额</span><span class="sp-mon"><#if isLogin=="true">￥<em style="color:#f60;">${custAccountVO.avaiBal?string(',##0.00')}</em><#else>￥<em style="color:#f60;">0.00</em></#if></span><span class="sp-two" ><a href="${base}/userFund/myRecharge.do" style="color:blue">充值</a></span>
					</div>
					<div class="con-three" ><input type="text" value="   请输入金额" /></div>
					<div class="con-two" >
						<span class="sp-one" >可获得的接手奖金</span><span class="sp-two">￥<em style="color:#f60;">${transferVo.takeAmt?string(',##0.00')}</em></span>   
					</div>
					<div ><div class="con-four" >购买</div></div>
				</div>
				<div class="clear"></div>
			</div>
        </div>
        
			<input type="hidden" name="loanApproveId" id="loanApproveId" value="${transferVo.loanApproveId}" /> <!--  发布编号id -->
			
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
		<#if isLogin == "true">
			<#include "platform/transferDetail_jkxq.ftl">
        	<#include "/platform/transferDetail_hkbx.ftl">
        	<#include "/platform/transferDetail_jrjl.ftl">
       		<#include "/platform/transferDetail_lyb.ftl">
       	<#else>
       		 <#include "/platform/transferDetailNoLogin_jkxq.ftl">
             <#include "/platform/transferDetailNoLogin_hkbx.ftl">
             <#include "/platform/transferDetailNoLogin_jrjl.ftl">
  			 <#include "/platform/transferDetailNoLogin_lyb.ftl">
       	</#if>
        </div>
    </div>
</div>
<input type="hidden" name="loanApproveId" id="loanApproveId" value="${transferVo.loanApproveId}" /> <!--  发布编号id -->
<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
$(document).ready(function(){					   
	$("#all-nav-box div").click(function(){
	var divVid = $(this).attr('vid');    //new-tile-navongreen
	$(this).addClass("new-tile-navon").siblings().removeClass("new-tile-navon");     //这个是就是当点击时会在下面出现绿色线条
		//就是获取除点击的这个外的其他同胞元素，给点击了的添加一个样式，让其变成绿色，而这句话得本意的获得其他几个，同时让其他几个就去掉这个样式。	
	    
	    if(divVid == 'load-info-one'){   //借款人档案  不需要调用ajax
			$("#load-info-one").show();
			$("#load-info-two").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
		}
	    
	    if(divVid == 'load-info-three'){  //还款记录
			$("#load-info-three").show();
			$("#load-info-one").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
			ajaxQuery(divVid);
		}
		
		if(divVid == 'load-info-four'){   //转让记录
			$("#load-info-four").show();
			$("#load-info-one").hide();
			$("#load-info-three").hide();
			$("#load-info-five").hide();
			ajaxQuery(divVid);
		}
		 
		if(divVid == 'load-info-five'){   //留言板
			//alert("load-info-five");
			$("#load-info-five").show();
			$("#load-info-one").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
		    ajaxQuery(divVid);
		} 
	    
	});
});
//加载ajax记录
function ajaxQuery(divVid){
		$("#hkbx_datas").html(""); 
		$("#jrjl_datas").html(""); //清楚里面的东西
	 if(divVid == 'load-info-three'){
			$.ajax({
				type:"POST",
				url:"ajaxRepayPlanDetail.do",
				dataType:"json",
				data:"transferId=" + $("#loanApproveId").val(),
				complete:function() {
				},
				success:function(data) {
					 $.each(data.list, function(i, n){
					 	var row = $('<tr></tr>');
					 	row.append($("<td>" + n.period +"</td>")); 
					 	row.append($("<td>" + n.repayplanDate +"</td>"));  
					 	row.append($("<td>" + fmoney(n.principalAmt,2) +"</td>")); 
					 	row.append($("<td>" + fmoney(n.interestAmt,2) +"</td>"));  
					 	row.append($("<td>" + n.repayStatusCd +"</td>"));
					 	row.appendTo("#hkbx_datas");    
					 }); 
				},
				error:function(text) {
					//alert('请求后台出错.');
				} 
			});
		}else{                            //要是就两个的话就不需要用什么else if()这种方式呢。
			$.ajax({
				type:"POST",
				url:"ajaxTranList.do",
				dataType:"json",
				data:"transferId=" + $("#loanApproveId").val(),
				complete:function() {
				},
				success:function(data) {
					 $.each(data.list, function(i, n){
					 	var row = $('<tr></tr>');
					 	row.append($("<td>" + (i+1) +"</td>")); 
					 	row.append($("<td>" + n.userCode +"</td>")); 
					 	row.append($("<td>" + n.crCount +"</td>"));  
					 	row.append($("<td>" + fmoney(n.crAmt,2) +"</td>"));  
					 	//row.append($("<td>" + n.tenderTypeCd +"</td>")); 
					 	row.append($("<td>" + timeStamp2String(n.sysCreateTime) +"</td>"));
					 	row.appendTo("#jrjl_datas");    
					 }); 
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}
	}
</script>
</body>
</html>