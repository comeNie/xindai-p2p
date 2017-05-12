
<div id="myRecharge_tab2" class="myRecharge"  style="display:none">
	<div class="repayment repayment_h">
		
     		<div class="clear"></div>
      			<div class="recharge">
					<form id="paymentCardForm" class="form-horizontal" action="paymentCard.do" method="post" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
                      	<input type="hidden" id="bank" name="bank"/>
	                    <fieldset>
		                	<h2>理财卡：</h2> 
		                	<div class="control-group">
		                    	<label class="control-label">账户余额：</label>
		                        <div class="controls">
		                        	<span><#if userIndexVO.accountBalAmt??>${userIndexVO.accountBalAmt}<#else>0</#if>元</span>
		                        </div>
		                	</div>
		                	<div class="control-group">
		                 		<label class="control-label"><em>*</em>充值卡：</label>
		                   		<div class="controls">
		                              <input type="text" class="span3" name="cardCd" id="cardCd" data-rule="充值卡:required;">&nbsp;<span></span>
		                        </div>
		                   	</div>
		                    <div class="control-group">
		                    	<label class="control-label" ><em>*</em>充值密码：</label>
		                     	<div class="controls">
	                            	<input type="password" class="span3" name="password" id="password" data-rule="充值密码:required;">&nbsp;<span></span>
		                   		</div>
		                    </div>
                            <div class="btnbar"><button class="btn btn-primary" id="submitCard">充值</button></div>
	                  	</fieldset>
        			</form>
				</div>
	</div>
</div>
<script type="text/javascript">
//<![CDATA[
   // if($("#reMessage").val()!=""){
   //   showDialog(true,$("#reMessage").val());
   // 	showDialogNew(true,$("#reMessage").val(),'S','提醒信息');
   // 	$("#reMessage").val("");
   // }
 //]]>
</script>
