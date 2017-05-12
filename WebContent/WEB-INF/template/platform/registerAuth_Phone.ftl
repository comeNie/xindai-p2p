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
        	<div class="regStep_process">
            </div>
            <div class="reg_com">
                <div class="regstep">
                    <ol class="ui-step ui-step-blue ui-step-4">
                        <li class="ui-step-start ui-step-done">
                            <div class="ui-step-line">-</div>
                            <div class="ui-step-icon">
                                <i class="iconfont">&#xe610;</i>
                                <i class="ui-step-number">1</i>
                                <span class="ui-step-text">填写账户信息</span>
                            </div>
                        </li>
                        <li class="ui-step-active">
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
                                <span class="ui-step-text">实名认证</span>
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
              	<div class="reg_content">
              		<form id="registerForm" class="form" action="${base}/register/authSmsMessage.do" method="post" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
	                	<p>
                            <label><em>*</em>手机号码：</label>
                            <span><strong>${mobile}</strong></span>
                        </p>
	                	<input type="hidden" name="userName" size="18" class="span2" value="${userName}"></span>
	                	<input type="hidden" name="mobile" size="18" class="span2" value="${mobile}"></span>
	                    <p>
                            <label><em>*</em>验证码：</label>
                            <span class="input_sm"><input type="text" name="authCode" size="6" class="span2" data-rule="验证码:required;length[6];"></span>
                            <span class="auth-error">${errMsg}</span>
                        </p>
						<p><span>短信已发至您的手机，请输入短信中的验证码，确保您的手机号真实有效。</span></p>
	                    <p class="reg-btnbar">
	                    	<button type="submit" class="btn btn-primary">下一步</button>
	                    	<input type="button" id="getAuthCodeBtn" class="btn btn-primary" value="再次获取验证码"/>
	                    </p>
	                </form>
                </div>  
            </div>
           </div>
            </div>
        </div>

<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
var wait=60; 
function time(o) { 
    if (wait == 0) { 
        o.removeAttribute("disabled");           
        o.value="再次获取验证码"; 
        wait = 60; 
    } else { 
        o.setAttribute("disabled", true); 
        o.value=wait+"秒后可以重新发送"; 
        wait--; 
        setTimeout(function() { 
            time(o) 
        }, 
        1000) 
    } 
}
var butt = document.getElementById('getAuthCodeBtn');
time(butt);

$("#getAuthCodeBtn").click(function(){
	sendAuthMessage();
	time(this);p
});

function sendAuthMessage(){
	$.ajax({
			type:"POST",
			url:"ajaxSendAuthMessage.do",
			dataType:"json",
			data:"mobile=${mobile}",
			success:function(data) {
			},
			error:function(text) {
				//alert('请求后台出错.');
			} 
		});
	}
</script>
</body>
</html>