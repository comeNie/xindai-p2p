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
		<div class="reg_index">
			<div class="logincombox">
			 <div class="regstep">
                    <ol class="ui-step ui-step-blue ui-step-4">
                       <li class="">
                            <div class="ui-step-line">-</div>
                            <div class="ui-step-icon">
                                <i class="iconfont">&#xe610;</i>
                                <i class="ui-step-number">1</i>
                                <span class="ui-step-text">填写账户信息</span>
                            </div>
                        </li>
                        <li class="ui-step-start ui-step-active">
                            <div class="ui-step-line">-</div>
                            <div class="ui-step-icon">
                                <i class="iconfont">&#xe610;</i>
                                <i class="ui-step-number">2</i>
                       			<span class="ui-step-text">手机验证</span>
                            </div>
                        </li>
                        <li class="">
                            <div class="ui-step-line">-</div>
                            <div class="ui-step-icon">
                                <i class="iconfont">&#xe610;</i>
                                <i class="ui-step-number">3</i>
                                <span class="ui-step-text">重设密码</span>
                            </div>
                        </li>
                        <li class="ui-step-end">
                            <div class="ui-step-line">-</div>
                            <div class="ui-step-icon">
                                <i class="iconfont">&#xe610;</i>
                                <i class="iconfont ui-step-number">&#xe63d;</i>
                                <span class="ui-step-text">完成</span>
                            </div>
                        </li>
                    </ol>
                </div>
		  		 <div class="clear"></div>
  					<form class="ui-form" method="post" action="findPwdByMobileAuth.do" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
             			<div id="findPswByMobile" class="reg-con" >
  				  	  		<div class="inputs">
  					 			<table width="50%" border="0" cellpadding="0" cellspacing="0" class="tableT_reg" align="center">
  		                  			<th style="width:100px"><em>*</em>手机号：</th>
                           				<td>
                           				     <span >${mobile}</span>
                           					 <input class="ui-input" type="hidden" name="mobile" id="mobile" value="${mobile}"  data-rule="手机号码:required; mobile;" >
                            			</td>
                         		 	</tr>
                          			<th style="width:100px"><em>*</em>验证码：</th>
                           				<td style="padding:20px 0px 0px 20px;">
			                            	<input class="ui-input code" id="authCode"  name="authCode" placeholder="6位验证码" data-rule="手机验证码:required;" type="text">
                              				
                              				<input type="button" id="getAuthCodeBtn" class="btn-primary" value="获取验证码"/>
			                            </td> 
			                        </tr> 
			  						<tr>
			  							<td></td> 
				  					 	<td  align="left">
											<input type="submit" value="提 交"  class="Operation ui-button ui-button-blue ui-button-mid" />
										</td>
								    </tr>
						   		</table>
							</div>
	  					</form> 
  				  	</div>	
            </div>
        </div>
    </div>

<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
$(document).ready(function() {
 	$("#getAuthCodeBtn").click(function(){
	    var mobile = $("#mobile").val();
	   // alert(mobile);
		sendAuthMessage(mobile);
		var waitTime = 60;
		time(this,waitTime);
	});
	
	var getAuthBtn = document.getElementById("getAuthCodeBtn");
	time(getAuthBtn,60);
 });
 
function time(o,waitTime) {
    if(waitTime == 60){
      wait = waitTime;
    }
    if (wait == 0) { 
        o.removeAttribute("disabled");           
        o.value="再次获取验证码"; 
        wait = 60; 
    } else { 
        o.setAttribute("disabled", true); 
        o.value=wait+"秒后可以重新发送"; 
        --wait;
        setTimeout(function() { 
            time(o,wait) 
        }, 
        1000) 
    } 
}


function sendAuthMessage(phone){
   var mobile = phone;
   //alert(mobile);
	$.ajax({
			type:"POST",
			url:"${base}/register/ajaxSendAuthMessage.do",
			dataType:"json",
			data:"mobile=" + mobile,
			success:function(data) {
			},
			error:function(text) {
				//alert('发送短信失败.');
			} 
		});
	}
</script>
</body>
</html>