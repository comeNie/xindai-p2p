<form class="form-horizontal" id="submitForm" method="post" action="${base}/userFinancial/addAutoTenderConfigSubmit.do" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}" >
                <div id="myAutoTender_tab1" class="repayment">
                	<input type="hidden" id="status" name="status" 
                	value="<#if configBO.status??>${configBO.status}<#else>1</#if>"
                	/>
                	<input type="hidden" id="id" name="id" 
                	value="<#if configBO.id??>${configBO.id}<#else></#if>"
                	/>
                	<input type="hidden" id="custId" name="custId" 
                	value="<#if configBO.custId??>${configBO.custId}<#else>0</#if>"
                	/>
					<input type="hidden" id="isAuto" name="isAuto" value="0" />
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="person_info">
                      <tr>
                        <td width="100px" align="right">年化利率：</td>
                        <td><input type="text" id="yearRate" name="yearRate" style="width:50px;max-length:5"
                        value="<#if configBO.yearRate??>${configBO.yearRate}<#else>0</#if>"
                         />元(大于等于1000元才可开启自动投标工具)</td>
                      </tr>
                      <tr>
                        <td width="100px" align="right">借款期限：</td>
                        <td>
                        	<select id="loanPeriodBe" name="loanPeriodBe" />
                        		<option value="1">1</option>
                        		<option value="3">3</option>
                        		<option value="6">6</option>
                        		<option value="9">9</option>
                        		<option value="12">12</option>
                        		<option value="18">18</option>
                        		<option value="24">24</option>
                        		<option value="36">36</option>
                        	</select>
                        	～
                        	<select id="loanPeriodEn" name="loanPeriodEn" />
                        		<option value="1">1</option>
                        		<option value="3">3</option>
                        		<option value="6">6</option>
                        		<option value="9">9</option>
                        		<option value="12">12</option>
                        		<option value="18">18</option>
                        		<option value="24">24</option>
                        		<option value="36">36</option>
                        	</select>
                        	个月
                        </td>
                      </tr>
                      <tr>
                        <td width="100px" align="right">账户余额：</td>
                        <td>
	                        <input type="text" id="accBal" name="accBal"
	                        value="<#if configBO.accBal??>${configBO.accBal}<#else>0</#if>"
	                        />元
                        </td>
                      </tr>
                      <tr>
                        <td width="100px" align="right">信用评级范围：</td>
                        <td>
                        	<select id="creditRateBe" name="creditRateBe">
                        		<@sysCodeSelect codeType='creditRate'/>
                        	</select>
                        		～
                        	<select id="creditRateEn" name="creditRateEn">
                        		<@sysCodeSelect codeType='creditRate'/>
                        	</select>
                       </td>
                      </tr>
                      <tr>
                        <td width="100px" align="right">账户保留：</td>
                        <td><input type="text" id="accRetain" name="accRetain"
                        value="<#if configBO.accRetain??>${configBO.accRetain}<#else></#if>"
                        />元</td>
                      </tr>
                      <tr>
                        <td width="100px" align="right">每标最大投标：</td>
                        <td><input type="text" id="eachMaxBid" name="eachMaxBid"
                        value="<#if configBO.eachMaxBid??>${configBO.eachMaxBid}<#else></#if>"
                        />元</td>
                      </tr>
                      <tr>
                      	<td colspan="2">
                      		aaa：${configBO.isAuto}   bbb：${configBO.status}
                      		<#if configBO.id??>
                      			<#if configBO.isAuto==0 && configBO.status==0>
                      				<button class="ui-button ui-button-blue ui-button-mid" id="openBtn">提交1</button>
                      			<#else>
                      				<button class="ui-button ui-button-yellow ui-button-mid" id="cancelBtn">取消</button>
                      			</#if>
                      		
                      		<#else>
                      			<button class="ui-button ui-button-blue ui-button-mid" id="openBtn">提交2</button>
                      		</#if>
                      	</td>
                      </tr>
                    </table>
                </div>
 </form>     		 
<script type="text/javascript">
//<![CDATA[
$(document).ready(function(){
	var loanPeriodBe="<#if configBO.loanPeriodBe??>${configBO.loanPeriodBe}<#else></#if>";
	var loanPeriodEn="<#if configBO.loanPeriodEn??>${configBO.loanPeriodEn}<#else></#if>";
	
	$("#loanPeriodBe").val(loanPeriodBe);
	$("#loanPeriodEn").val(loanPeriodEn);
	
	setDecimalInput("yearRate");
	setDecimalInput("accBal");
	setDecimalInput("accRetain");
	setDecimalInput("eachMaxBid");
	
	
	$("#openBtn").click(function(){
		
		$("#status").val(1);
		
		alert(parseInt($("#loanPeriodBe").val())>parseInt($(""#loanPeriodEn").val()));
		/**
		if( parseInt($("#loanPeriodBe").val())>parseInt($(""#loanPeriodEn").val())){
			//showDialog(true,"请正确设置借款期限！");
		    showDialogNew(true,"请正确设置借款期限！",'E','提醒信息');
			return;
		}
		*/
		$("#submitForm").submit();
	});	
	
	$("#cancelBtn").click(function(){
		$("#status").val(0);
		alert($("#status").val());
		/**
		if( parseInt($("#loanPeriodBe").val())>parseInt($(""#loanPeriodEn").val())){
			//showDialog(true,"请正确设置借款期限！");
			showDialogNew(true,"请正确设置借款期限！",'E','提醒信息');
			return;
		}
		*/
		$("#submitForm").submit();
	});	

});

function setDecimalInput(idKey){
		//金额输入框
		$("#"+idKey).on("keyup",function(){
			if(/^(-?\d+)(\.|\.\d+)?$/.test($(this).val())){
				str=$(this).val();
			}
			$("#"+idKey).val(str);
		});	
}

 //]]>
</script>
</body>
</html>