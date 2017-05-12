<!DOCTYPE html>  <!-- 登录 -->
<html lang="en">
<head>
<meta charset="utf-8">
<title>登录</title>

<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport"content="width=1349;"/> 
<#include "/common/resource.ftl">
</head>
<body>
<div style="min-width:1080px">
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
			<div class="reg_index bulk_standard">
            	 <div class="logincombox">
                 	<div class="left">
                    	<h2>用户登录</h2>
                    	<form id="loginForm" class="form" action="${base}/loginAction/loginIndex.do" method="post"  onsubmit="return false;" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
                        <span class="login-msg" <#if !errMes??>style="display:none;"</#if>><img src="${base}/platform/img/info.gif" align="absbottom"/>&nbsp;${errMes}</span>
                            <p class="input"><input type="text" class="span2" id="username"
                                                                             name="username" value=""
                                                                             data-rule="登录名:required;" 
                                                                             placeholder=" 账号 ／手机号／邮箱 "></p>
                            <p class="input2"><input type="password" class="span2"
                                                                              name="password" id="password" value=""
                                                                              data-rule="密码:required;"
                                                                              placeholder="请输入密码"></p>
                            <p class="input3"><input class="span2" name="j_captcha" type="text" data-rule="验证码:required;length[4];remote[get:ajaxCaptcha.do]" value="" placeholder="验证码"/>
                                <span class="login-img"><img id="captachaImg" style="cursor:pointer;" src="${base}/captcha.jpg"/></span>
                                <span class="login-img-text" style="cursor:pointer;"><a id="refreshBtn" ><img src="${base}/platform/img/ico-refresh.gif" alt=""/></a></span>
                                <span class="msg-box n-right" for="j_captcha"></span>
                            </p>
                        <p class="otherlogin"><span class="qq"></span><span class="weibo"></span></p>
                        <div class="login-button">
                           <button type="submit"  id="loginsubmit"   class="ui-button-rrd-blue ui-button-rrd-blue-large">登录</button>
                       </div>
                        <p class="otherinfo"><span class="forgetpwd"><a href="${base}/loginAction/findPwd.do">忘记密码?</a></span><span class="toreg">还没有帐号? <a href="${base}/register/index.do">立即注册！</a></span></p>
                    </div>
                    <div class="right"></div>
                    </form>
                 </div>	
		    </div>
    </div>
</div>
</div>
<script type="text/javascript">
$(document).ready(function() {

   $("#captachaImg").click(function(){
   		refreshCaptcha();
   });
   
   $("#refreshBtn").click(function(){
   		refreshCaptcha();
   });
   
   function refreshCaptcha(){
        var randomVal = Math.random();
   		$("#captachaImg").attr("src",'${base}/captcha.jpg?'+randomVal);
   }
 });
</script>
</body>
</html>