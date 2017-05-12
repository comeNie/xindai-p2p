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
				<a href="${base}/loan/list.do">我要投资</a><a >投资散标详情</a>
			</div>
        </div>

		<div class="new-info-page">
        	<div class="new-info-box-one">
				<img src="${base}/platform/img/ya${loanDetailVO.loanTypeCd}.png"/><span class="title">${loanDetailVO.loanName}</span><span class="username">${loanDetailVO.userCode}</span>
			</div>
			<div class="new-info-box-two">
				<div class="repayfloat1">
					<div>标的总额</div>
					<#if loanDetailVO.loanAmt??>
						<div class="number"><em>${loanDetailVO.loanAmt?string(',##0.00')}</em>元</div>
					<#else>
					   <div class="number"><em>0</em>元</div>
					</#if>
<!-- 					<div class="new-info-bottom"><span class="conspan">保障机制</span><span>本息保障</span></div> -->
					<input type="hidden" name="loanApproveId" id="loanApproveId" value="${loanDetailVO.loanApproveId}" /> 
				</div>
				<div class="repayfloat2">
					<div><span class="sp-year">年化收益率 </span> <span class="sp-date" >期限</span></div>
					<div class="number"><span class="sp-year"><em>${loanDetailVO.loanRate}</em>%</span><span class="sp-time" ><em>${loanDetailVO.loanTerm}</em>月</span></div>
					<#if loanDetailVO.loanStatusCd == "1">
						<div class="new-info-bottom"><span class="conspan">还款方式</span><span><@sysCodeVal codeType='repayTypeCd' codeValue='${loanDetailVO.repayTypeCd}' /></span><img src="${base}/platform/img/repayment02.png" /></div>
					<#else>
					  <div class="new-info-bottom"><span class="conspan">还款方式</span><span><@sysCodeVal codeType='repayTypeCd' codeValue='${loanDetailVO.repayTypeCd}' /></span><img src="${base}/platform/img/repayment01.png" /></div>
					</#if>
					
				</div>
				<div class="repayfloat3">
					<div class="repay-con" >
					   <#if loanRepayInfoVo.sumRepayAmt??>
						 <span class="sp-one">待还本息(元)</span><span class="sp-two"><em>￥${loanRepayInfoVo.sumRepayAmt?string(',##0.00')}</em></span>
						<#else>
							<span class="sp-one">待还本息(元)</span><span class="sp-two"><em>￥0.00</em></span>
						</#if>
					</div>
					<div class="repay-con" >
						 <#if loanRepayInfoVo.restPeriods??>
						 	<span class="sp-one" >剩余期数(月)</span><span class="sp-two">${loanRepayInfoVo.restPeriods}（月）</span>
						<#else>
							<span class="sp-one" >剩余期数(月)</span><span class="sp-two">0（月）</span>
						</#if>
						
					</div>
					<div class="repay-con" >
					     <#if loanRepayInfoVo.nextRepayDate??>
						 	<span class="sp-one" >下一个还款日</span><span class="sp-two">${loanRepayInfoVo.nextRepayDate?string('yyyy-MM-dd')}</span>
						<#else>
							<span class="sp-one" >下一个还款日</span><span class="sp-two"></span>
						</#if>
					</div>
				</div>
				<div class="clear"></div>
			</div>
        </div>
        
        
        <div class="new-info-page" style="margin-top:30px;margin-bottom:15px;">
        	<div class="new-info-box-one" style="margin:0px;">
				<div class="new-title" id="all-nav-box">
					<div class="new-tile-navon" vid="load-info-one">借款人档案</div>
					<div vid="load-info-two">投标记录</div>
<!-- 					<div vid="load-info-three">还款记录</div> -->
<!-- 					<div vid="load-info-four">转让记录</div> -->
					<#--<div vid="load-info-five">留言板</div>-->
					<div class="clear"></div>	
				</div>
			</div>
			
			<#if isLogin == "true">
			    <#include "/platform/loanDetail_jkrda.ftl">
				<#include "/platform/loanDetail_tbjl.ftl">
				<#include "/platform/loanDetail_hkjl.ftl">
				<#include "/platform/loanDetail_zrjl.ftl">
		   		<#include "/platform/loanDetail_lyb.ftl">
		   	<#else>
		   		<#include "/platform/loanDetail_jkrda_no_logic.ftl">
				<#include "/platform/loanDetail_tbjl_no_logic.ftl">
				<#include "/platform/loanDetail_hkjl_no_logic.ftl">
				<#include "/platform/loanDetail_zrjl_no_logic.ftl">
				<#include "/platform/loanDetail_lyb_no_logic.ftl">
		   	</#if>
			
        </div>
     
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
		
		if(divVid == 'load-info-one'){   //借款人档案  不需要调用ajax
			$("#load-info-one").show();
			$("#load-info-two").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
		}
		
		if(divVid == 'load-info-two'){   //投标记录
			$("#load-info-two").show();
			$("#load-info-one").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
			ajaxQuery(divVid);
		}
		
		if(divVid == 'load-info-three'){  //还款记录
			$("#load-info-three").show();
			$("#load-info-one").hide();
			$("#load-info-two").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
			ajaxQuery(divVid);
		}
		
		if(divVid == 'load-info-four'){   //转让记录
			$("#load-info-four").show();
			$("#load-info-one").hide();
			$("#load-info-two").hide();
			$("#load-info-three").hide();
			$("#load-info-five").hide();
			ajaxQuery(divVid);
		}
		 
		if(divVid == 'load-info-five'){   //留言板
			$("#load-info-five").show();
			$("#load-info-one").hide();
			$("#load-info-two").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
		    ajaxQuery(divVid);
		} 
	});
	//加载ajax记录
	function ajaxQuery(divVid){
		$("#tbjl_datas").html(""); 
		$("#hkjl_datas").html(""); 
		$("#zrjl_datas").html(""); 
		if(divVid == 'load-info-two'){
			$.ajax({
				type:"POST",
				url:"ajaxCreditorRight.do",
				dataType:"json",
				data:"approveStatusCd=" + "0" + "&loanApproveId=" + $("#loanApproveId").val(),//前者表示借款发布状态  为 0 表示投标中，   后者是借款编号
				complete:function() {
				},
				success:function(data) {
					 $.each(data.list, function(i, n){
					 	var row = $('<tr></tr>');
					 	row.append($("<td>" + (i+1) +"</td>")); 
					 	row.append($("<td>" + HidChar(n.userCode) +"</td>")); 
					 	row.append($("<td>" + fmoney(n.crAmt,2) +"</td>"));  
					 	row.append($("<td>" + n.tenderTypeCd +"</td>")); 
					 	row.append($("<td>" + timeStamp2String(n.sysCreateTime) +"</td>"));  
					 	row.appendTo("#tbjl_datas");    
					 }); 
				},
				error:function(text) {
					//alert('请求后台出错.');
				} 
			});
		}else if(divVid == 'load-info-three'){
			$.ajax({
				type:"POST",
				url:"ajaxRepayPlanDetail.do",
				dataType:"json",
				data:"approveStatusCd=" + "0" + "&loanApproveId=" + $("#loanApproveId").val(),
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
					 	row.appendTo("#hkjl_datas");    
					 }); 
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}else if(divVid == 'load-info-four'){
			$.ajax({
				type:"POST",
				url:"ajaxTranList.do",
				dataType:"json",
				data:"approveStatusCd=" + "0" + "&loanApproveId=" + $("#loanApproveId").val(),
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
					 	
					 	row.appendTo("#zrjl_datas");    
					 }); 
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}
	}
});

	//截取/替换字符串(隐藏字符串）
	 function HidChar(str)
	 {
		 var s=str.substring(0,1); //取第一个字符
		 var strlen = "";
		 for(var i = 1;i < str.length;i++)
		  {
		  	strlen += "*";
		  }
		 s =s + strlen;	
	 	 return s;
 	}
</script>
</body>
</html>