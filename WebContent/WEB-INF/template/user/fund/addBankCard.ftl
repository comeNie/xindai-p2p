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
                <div class="repayment">
                	<form class="form-horizontal" id="submitForm" method="post" action="${base}/userFund/addBankCardSubmit.do" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}" >
                    <table class="table table-hover">
                    	<tbody>
							<tr>
		                   		 <td>*真实姓名</td>
		                   		 <td><input type="text" value="${custName}" name="accountName" readonly="true" /></td>
							</tr>
							<tr>
		                   		 <td>*身份证号</td>
		                   		 <td><input type="text" value="${certificateNum}" readonly="true" /></td>
							</tr>
							<tr>
		                   		 <td>*银行卡号</td>
		                   		 <td><input type="text" name="accountNum" /></td>
							</tr>
							<tr>
		                   		 <td>*确认卡号</td>
		                   		 <td><input type="text" name="accountNum1" /></td>
							</tr>
							<tr>
		                   		 <td>*开户银行</td>
		                   		 <td><input type="text" name="bankName" />  *所属支行<input type="text" name="accountBank" /></td>
							</tr>
							<tr>
		                   		 <td>*开户行所在地</td>
		                   		 <td><input type="text" name="areaProvince" /><input type="text" name="areaCity" /></td>
							</tr>
	                    </tbody>
                  </table>
                  	<p><button type="submit" class="btn btn-primary">添加</button></p>
                  </form>
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
	var $submitForm = $("#submitForm");
	$submitForm.submit(function(){
		if($("input[name='accountNum']").val()!=$("input[name='accountNum1']").val()){
			//showDialog(true,"2次输入卡号不一致！"); 
			 showDialogNew(true,"2次输入卡号不一致！",'E','提醒信息');
			return false;
		}
		if($("input[name='accountNum']").val()=="" ){
			//showDialog(true,"请输入银行卡号！");
	        showDialogNew(true,"请输入银行卡号！",'E','提醒信息');
	    	return false;
		}
		if($("input[name='accountNum1']").val()=="" ){
			//showDialog(true,"请输入确认卡号！"); 
			showDialogNew(true,"请输入确认卡号！",'E','提醒信息');
			return false;
		}
		if($("input[name='bankName']").val()=="" ){
			//showDialog(true,"请输入开户银行！"); 
			showDialogNew(true,"请输入开户银行！",'E','提醒信息');
			return false;
		}
		if($("input[name='accountBank']").val()=="" ){
			showDialog(true,"请输入所属支行！"); 
			showDialogNew(true,"请输入所属支行！",'E','提醒信息');
			return false;
		}
		if($("input[name='areaProvince']").val()=="" || $("input[name='areaCity']").val()=="" ){
			showDialog(true,"请输入开户行所在地！"); 
			showDialogNew(true,"请输入开户行所在地！",'E','提醒信息');
			return false;
		}
		
		$.ajax({
			url : validateForm,
			type:"post",
			data:$submitForm.serialize(),
			dataType:"json",
			success:function(message){
				if(message==true){
					return true;
				}else{
					//showDialog(true,"该账号不合法！");
					showDialogNew(true,"该账号不合法！",'E','提醒信息');
				}
			}
		});
		return false;
	});				   
});
 //]]>
</script>
</body>
</html>