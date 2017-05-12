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
               <div class="tab_table_N creditor auto_css">
                <div class="nav">
                    <ul id="myAutoTenderNav">
                        <li  class="current" id="tab1" ><a <a href="javascript:void(0);">自定义投标</a></li>
                        <li id="tab2"><a href="javascript:void(0);">一键投标</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
                	<form class="form-horizontal" id="submitForm1" onSubmit="return check();"   method="post" action="${base}/userFinancial/addAutoTenderConfigSubmit.do" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}" >
                	<div id="myAutoTender_tab1" class="repayment">
                	<input type="hidden" id="status" name="status" 
                	value=""
                	/>
                	<input type="hidden" id="id" name="id" 
                	value=""
                	/>
                	<input type="hidden" id="custId" name="custId" 
                	value=""
                	/>
                	<input type="hidden" id="userCode" name="userCode" 
                	value=""
                	/>
                	<input type="hidden" id="userId" name="userId" 
                	value=""
                	/>
					<input type="hidden" id="isAuto" name="isAuto" value="0" />
					<input type="hidden" id="autoFlag" name="autoFlag" value="${autoFlag}" />
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="person_info">
                      <tr>
                        <td colspan="2" align="center">
                          	  自动投标状态： 关闭状态<br/>
                            <span class="text-grey">(大于等于1000元才可开启自动投标工具)</span>
                        </td>
                      </tr>
                      <tr>
                        <td width="100px" align="right">年化利率：</td>
                        <td>
                        <input type="text" id="yearRateBe" name="yearRateBe" style="width:50px;max-length:5" value=""/>
                                                  ～
                        <input type="text" id="yearRateEn" name="yearRateEn" style="width:50px;max-length:5" value=""/>
                        		</td>
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
                        value=""
                        />元</td>
                      </tr>
                      <tr>
                        <td width="100px" align="right">每标最大投标：</td>
                        <td><input type="text" id="eachMaxBid" name="eachMaxBid"
                        value=""
                        />元</td>
                      </tr>
                    </table>
                        <div class="btnbar">
                            <button class="ui-button ui-button-blue ui-button-mid" id="openBtn">提交</button>
                            <button class="ui-button ui-button-yellow ui-button-mid" id="cancelBtn">取消</button>
                            <button class="ui-button ui-button-blue ui-button-mid" id="openBtnAuto">开户自动工具</button>
                            <button class="ui-button ui-button-yellow ui-button-mid" id="cancelBtnAuto">取消自动工具</button>
                        </div>
                </div>
                </form>
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
	
	
	//loadData($('#autoFlag').val());
	//alert($('#autoFlag').val());
	initTab("tab" + (parseInt($('#autoFlag').val())+1));
	changeTab();
	
	setDecimalInput("yearRate");
	setDecimalInput("accRetain");
	setDecimalInput("eachMaxBid");
	
	
	$("#openBtn").click(function(){
		
		$("#status").val(1);
		
		//$("#submitForm").submit();
	});	
	
	$("#cancelBtn").click(function(){
		$("#status").val(0);
	
	    
		//$("#submitForm").submit();
	});
	
	
	$("#openBtnAuto").click(function(){
		
		$("#status").val(1);
		
		//$("#submitForm").submit();
	});	
	
	$("#cancelBtnAuto").click(function(){
		$("#status").val(0);
	    

		//$("#submitForm").submit();
	});
	
});


function check(){
	if( parseInt($("#loanPeriodBe").val())>parseInt($("#loanPeriodEn").val())){
			//showDialog(true,"请正确设置借款期限！");
			showDialogNew(true,"请正确设置借款期限！",'E','提醒信息');
			return false;
	}
	
	var dj1 = $("#creditRateBe").val();
	var dj2 = $("#creditRateEn").val();
	if( dj1.length==1 ){
		dj1 = dj1 + "0";
	}
	if( dj2.length==1 ){
		dj2 = dj2 + "0";
	}
	
	
	var dj1_code =  (parseInt(dj1.substring(0,1).charCodeAt())*10) + "" + (parseInt(dj1.substring(1,2).charCodeAt())*1)+ "";
	
	var dj2_code =  (parseInt(dj2.substring(0,1).charCodeAt())*10)+ "" + (parseInt(dj2.substring(1,2).charCodeAt())*1)+ "";
	
	if( dj1_code>dj2_code ){
		showDialogNew(true,"请正确设置信用评级范围!",'E','提醒信息');
		return false;
	}
	
	if( $('#yearRateBe').val()> $('#yearRateEn').val() ){
		showDialogNew(true,"请正确设置年化利率范围!["+$('#yearRateBe').val()+"大于" + $('#yearRateEn').val() + "]",'E','提醒信息');
		return false;
	}
	

	
	return true;
}

function changeTab() {
   	$("#myAutoTenderNav li").click(function () {
		var liId = $(this).attr('id');
		$(this).addClass("current").siblings().removeClass("current");
		if( liId=="tab1" ){
			loadData(0);
			$('#autoFlag').val(0);
			
			$('#yearRateBe').attr('readonly',false);
			$('#yearRateEn').attr('readonly',false);
			$('#loanPeriodBe').attr('readonly',false);
			$('#loanPeriodEn').attr('readonly',false);
			$('#creditRateBe').attr('readonly',false);
			$('#creditRateEn').attr('readonly',false);
			$('#accRetain').attr('readonly',false);
			$('#eachMaxBid').attr('readonly',false);
			
		}else if( liId=="tab2" ){
			loadData(1);
			$('#autoFlag').val(1);
			
			$('#yearRateBe').attr('readonly',true);
			$('#yearRateEn').attr('readonly',true);
			$('#loanPeriodBe').attr('readonly',true);
			$('#loanPeriodEn').attr('readonly',true);
			$('#creditRateBe').attr('readonly',true);
			$('#creditRateEn').attr('readonly',true);
			$('#accRetain').attr('readonly',true);
			$('#eachMaxBid').attr('readonly',true);
		}
	});	
}


function loadData(autoFlag){
	$('#openBtn').hide();
	$('#cancelBtn').hide();
	$('#openBtnAuto').hide();
	$('#cancelBtnAuto').hide();	
			

	showAjaxAjaxLoading();
	$('#id').val('');
	$('#custId').val('');
	$('#isAuto').val('');
	$('#yearRateBe').val('');
	$('#yearRateEn').val('');
	$('#loanPeriodBe').val('');
	$('#loanPeriodEn').val('');
	$('#creditRateBe').val('');
	$('#creditRateEn').val('');
	$('#accRetain').val('');
	$('#eachMaxBid').val('');
	$('#userCode').val('');
	$('#userId').val('');
	
	$('#isAuto').val(autoFlag);
	
	$.ajax({
				type:"POST",
				url:"ajaxAutoTenderConfig.do",
				dataType:"json",
				data:"autoFlag=" + autoFlag ,
				complete:function() {
				},
				success:function(data) {
					var dataObj;
					
					if(data.list!=''){
						dataObj = data.list[0];
						$('#yearRateBe').val(dataObj.yearRateBe);
						$('#yearRateEn').val(dataObj.yearRateEn);
						$('#loanPeriodBe').val(dataObj.loanPeriodBe);
						$('#loanPeriodEn').val(dataObj.loanPeriodEn);
						$('#creditRateBe').val(dataObj.creditRateBe);
						$('#creditRateEn').val(dataObj.creditRateEn);
						$('#accRetain').val(dataObj.accRetain);
						$('#eachMaxBid').val(dataObj.eachMaxBid);
						
						
						
					}
						if( data.totalCount==1 ){
							$('#openBtn').show();
						}else if( data.totalCount==2 ){
							$('#cancelBtn').show();
						}else if( data.totalCount==3 ){
							$('#openBtnAuto').show();
						}else if( data.totalCount==4 ){
							$('#cancelBtnAuto').show();	
						}
					hidenAjaxAjaxLoading();
					
					
				},
				error:function(text) {
					 //alert('请求后台出错.');
					 hidenAjaxAjaxLoading();
				} 
			});
}

function setDecimalInput(idKey){
		//金额输入框
		$("#"+idKey).on("keyup",function(){
			if(/^(-?\d+)(\.|\.\d+)?$/.test($(this).val())){
				str=$(this).val();
			}
			$("#"+idKey).val(str);
		});	
}


function initTab(liId) {
   		//alert(liId);
		$("#"+liId).addClass("current").siblings().removeClass("current");
		if( liId=="tab1" ){
			loadData(0);
			$('#autoFlag').val(0);
			
			$('#yearRateBe').attr('readonly',false);
			$('#yearRateEn').attr('readonly',false);
			$('#loanPeriodBe').attr('readonly',false);
			$('#loanPeriodEn').attr('readonly',false);
			$('#creditRateBe').attr('readonly',false);
			$('#creditRateEn').attr('readonly',false);
			$('#accRetain').attr('readonly',false);
			$('#eachMaxBid').attr('readonly',false);
			
		}else{
			loadData(1);
			$('#autoFlag').val(1);
			
			$('#yearRateBe').attr('readonly',true);
			$('#yearRateEn').attr('readonly',true);
			$('#loanPeriodBe').attr('readonly',true);
			$('#loanPeriodEn').attr('readonly',true);
			$('#creditRateBe').attr('readonly',true);
			$('#creditRateEn').attr('readonly',true);
			$('#accRetain').attr('readonly',true);
			$('#eachMaxBid').attr('readonly',true);
		}
		
}


 //]]>
</script>
</body>
</html>