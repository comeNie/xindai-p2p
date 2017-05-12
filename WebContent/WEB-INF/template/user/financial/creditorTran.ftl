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
<div style="min-width:1349px">
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
        <div class="user_panel">
        	<#include "/common/left.ftl">
            <div class="right">
               <div class="tab_table_N creditor">
                <div class="clear"></div>
                <div class="repayment">
                	
                    <table class="table table-hover">
                    <tbody>
						<tr>
		                 	 <td>标题</td>
		                	 <td><lable>${myCreditorTranVO.loanName}</label>
		                	 	<input type="hidden" id="crId" name="crId" value="${myCreditorTranVO.id}" />	
		                	 	<input type="hidden" id="loanName" name="loanName" value="${myCreditorTranVO.loanName}" />
		                	 	<input type="hidden" id="tranRate" name="tranRate" value="<#if tranRate??>${tranRate}<#else>0.01</#if>"/>	
		                	 </td>
		                	 <td>标的总额</td>
		                	 <td><lable>￥${myCreditorTranVO.loanAmt}</label>
		                	 	<input type="hidden" id="loanAmt" name="loanAmt" value="${myCreditorTranVO.loanAmt}" />
		                	 </td>
						</tr>
						<tr>
							 <td>年利率</td>
		                	 <td><lable>${myCreditorTranVO.loanRate}%</label></td>
		                 	 <td>剩余天数</td>
		                	 <td><lable>${myCreditorTranVO.remainDay}天</label></td>
						</tr>
						
						<tr>
							 <td>开始日期</td>
		                	 <td><lable>${myCreditorTranVO.loanDate}</label></td>
		                 	 <td>结束日期</td>
		                	 <td><lable>${myCreditorTranVO.loanDueDate}</label></td>
						</tr>
						
						<tr>
		                 	 <td>债权金额</td>
		                	 <td><lable>￥${myCreditorTranVO.crAmt}</label></td>
		                	 <td>剩余期数</td>
		                	 <td><lable>${myCreditorTranVO.surplusPeriod}</label></td>
						</tr>
						
						<tr>
		                 	 <td>可转让份数</td>
		                	 <td><lable>${myCreditorTranVO.canTranCount}份</label></td>
		                	 <td>可转让金额</td>
		                	 <td><lable>￥${myCreditorTranVO.unretrieveAmt}</label></td>
						</tr>
						<tr>
		                 	 <td>转让份数</td>
		                	 <td><input type="text" id="tranTtlCount" name="tranTtlCount" />份</td>
		                	 <td>转让金额</td>
		                	 <td>￥<input type="text" id="tranTtlAmt" name="tranTtlAmt" readonly /></td>
						</tr>
						<tr>
		                 	 <td>接手奖金</td>
		                	 <td>￥<input type="text" id="takeAmt" name="takeAmt" /></td>
		                	 <td>转让管理费</td>
		                	 <td>￥<input type="text" id="tranFee" name="tranFee" readonly /></td>
						</tr>
						<tr>
		                 	 <td>期限</td>
		                	 <td>
		                	 <select id="tranTerm" name="tranTerm">
		                	 	<option value="3">3天</option>
		                	 	<option value="5">5天</option>
		                	 	<option value="7">7天</option>
		                	 </select>
		                	 <!--<input type="text" id="tranTerm" name="tranTerm" size="3" />天-->
		                	 </td>
		                	 <td>&nbsp;</td>
		                	 <td>&nbsp;</td>
						</tr>
                    </tbody>
                  </table>
                  <p><button class="button_ui" id="submit">债权转让</button></p>
                  
                </div>
       		 </div>
            </div>	
        </div>
        <div class="clear"></div>        
    </div>
</div>	
<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
//<![CDATA[
$(document).ready(function(){
	var $tranTtlCount = $("#tranTtlCount");
	var $tranTerm = $("#tranTerm");
	var $submit = $("#submit");
	var $submitForm = $("#submitForm");	
		   
	//金额输入框
	$tranTtlCount.on("keyup",function(){
		if(/^(-?\d+)?$/.test($(this).val()))
			str=$(this).val();
		$tranTtlCount.val(str);
			
		var $tranTtlAmt = ($tranTtlCount.val()*parseFloat('${myCreditorTranVO.crAmt}')/parseFloat('${myCreditorTranVO.turnoverCount}')).toFixed(2);
		$("#tranTtlAmt").val( $tranTtlAmt ); 
		$("#tranFee").val( (parseFloat($tranTtlAmt) * parseFloat( $('#tranRate').val() )).toFixed(2) );
	});	
	
	$tranTerm.on("keyup",function(){
		if(/^(-?\d+)?$/.test($(this).val()))
			str=$(this).val();
		$tranTerm.val(str);
	});
	
	$submit.click(function(){
		
		$canTranCount = parseInt('${myCreditorTranVO.canTranCount}');
		
		//验证转让份数
		if( parseInt($tranTtlCount.val()) > $canTranCount){
			showDialogNew(true,"转让份数（" + $tranTtlCount.val() + "）不能大于可转让份数" + $canTranCount + "）",'E','提醒信息');
			return false;
		}else if( parseInt($tranTtlCount.val())==$canTranCount){
			$("#tranTtlAmt").val('${myCreditorTranVO.unretrieveAmt}');
		}
		
		var $remainDay = parseInt('${myCreditorTranVO.remainDay}');
		
		if( parseInt($tranTerm.val()) > $remainDay ){
			showDialogNew(true,"期限（" + $tranTerm.val() + "）不能大于剩余天数（" + $remainDay + "）",'E','提醒信息');
			return false;
		}
		showAjaxAjaxLoading();
 
    		$.ajax({
				type:"POST",
				url:"ajaxCreditorTran.do",
				dataType:"json",
				data:{
					crId:$('#crId').val(),
					loanAmt:$('#loanAmt').val(),
					loanName:$('#loanName').val(),
					takeAmt:$('#takeAmt').val(),
					tranFee:$('#tranFee').val(),
					tranRate:$('#tranRate').val(),
					tranTerm:$('#tranTerm').val(),
					tranTtlAmt:$('#tranTtlAmt').val(),
					tranTtlCount:$('#tranTtlCount').val()
				},
				complete:function(){
				},
				success:function(data) {
					//alert(data.reMes);
					hidenAjaxAjaxLoading();
					//showDialog(true,data.reMes);
					showDialogNew(true,sucMes,'S','提醒信息');
				},
				error:function(text) {
					hidenAjaxAjaxLoading();
					//alert("后台错误");
					//showDialog(true,"后台错误");
					showDialogNew(true,errMes,'E','提醒信息');
				} 
			});
    	
		
		
	});	
});
 //]]>
</script>

</body>
</html>