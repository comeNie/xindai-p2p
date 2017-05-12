<!DOCTYPE html>  <!-- 登录 -->
<html lang="en">
<head>
<meta charset="utf-8">
<title>登录</title>

<meta name="description" content="">
<meta name="author" content="">
<link href="${base}/css/styleApp.css" rel="stylesheet" type="text/css">
<link href="${base}/css/loginAPP.css" rel="stylesheet" type="text/css">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<script src="${base}/js/jqueryAPP-1.8.3.min.js" type="text/javascript"></script> 

<link rel="stylesheet" type="text/css" href="${base}/platform/bootstrap/css/style.css">
<link rel="stylesheet" type="text/css" href="${base}/platform/js/validator-0.7.3/jquery.validator.css">
<link rel="stylesheet" type="text/css" href="${base}/platform/bootstrap/css/pagination.css">
<link rel="stylesheet" href="${base}/platform/bootstrap/css/datepicker.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/platform/bootstrap/css/ui-dialog.css">
<link rel="shortcut icon" href="${base}/images1/sjt.ico">
<script type="text/javascript" src="${base}/platform/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${base}/platform/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${base}/platform/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${base}/platform/js/idcard.validator.js"></script>
<script type="text/javascript" src="${base}/platform/js/validator-0.7.3/jquery.validator.js"></script>
<script type="text/javascript" src="${base}/platform/js/validator-0.7.3/local/zh_CN.js"></script>
<script type="text/javascript" src="${base}/js/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/platform/js/dialog-min.js"></script>
<style>
.code1 {
float: left;
width: 90%;
margin-left: 3%;
height: 55px;
line-height: 55px;
position: relative;
margin-top: 25px;}
.n-top, .n-right, .n-bottom, .n-left 
{
display: inline-block;
line-height: 0;
vertical-align: top;
outline: 0;
margin-top: -90px;
margin-left: 250px;
}
.red_text {
float: left;
width: 55%;
margin-left: 5%;
height: 35px;
line-height: 55px;
font-size: 22px;
text-indent: 5px;
color: #a2a2a2;
}
.n-right .msg-wrap {
margin-left: -240px;
}
.login-msg {
margin-left: -90px;
margin-top: 20px;}

 @media screen and (max-width:397px){
	 .aa{vertical-align: top;cursor:pointer;margin-top: -95px;margin-right:10px;float:right}
	 }
 @media screen and (min-width:397px){
	 .aa{vertical-align: top;cursor:pointer;margin-top: -40px;margin-right:10px;float:right}
	 }
 @media screen and (width:397px){
	 .aa{vertical-align: top;cursor:pointer;margin-top: -95px;margin-right:10px;float:right}
	 }
</style>

</head>

<body >
<!-- <#include "/common/header_e.ftl"> -->
<div class="logo" style="background:#f9f9f9">
	<img src="${base}/platform/img/logo.gif" />
</div>
           <form id="loginForm"  style="margin-top: -2%;" class="form" action="${base}/loginAction/loginIndex.do" method="post"  onsubmit="return false;" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
		    <span class="login-msg"  <#if !errMes??>style="display:none;"</#if>><img src="${base}/platform/img/info.gif" width="18px" align="absbottom"/>&nbsp;${errMes}</span>
<!-- 				<p class="red_text">用户</p> -->
				<div class="code1" style="margin-left:5%">
					<input class="name"  type="text" id="username" style="background-image: url(../images/userName.png);background-repeat: no-repeat;background-position: 10px center;text-indent: 50px;"  name="username" value=""data-rule="登录名:required;"  placeholder=" 账号 ／手机号／邮箱 "/>
				</div>
<!-- 				<p class="red_text" style="line-height: 70px;">密码</p> -->
				<div class="code1" style="margin-top:25px;margin-left:5%">
					<input  type="password" name="password" id="password" style="background-image: url(../images/password.png);background-repeat: no-repeat;background-position: 10px center;text-indent:60px;"  value=""data-rule="密码:required;"placeholder="请输入密码" />
				</div>
<!-- 				 <p class="red_text">验证码</p> -->
                          <div class="code1" style="margin-left:5%">
                            	<input class="input-small" name="j_captcha" type="text" placeholder="验证码 "  style="background-image: url(../images/yanzheng.png);background-repeat: no-repeat;background-position: 10px center;text-indent:60px;"  data-rule="验证码:required;length[4];remote[get:ajaxCaptcha.do]" value="" />
                            	<span class="msg-box n-right" for="j_captcha"></span>
                              	<img class="aa" id="captachaImg"  src="${base}/captcha.jpg"/>
<!--                             	<a style="vertical-align: top;margin-top: -40px;" id="refreshBtn"><img style="cursor:pointer;"  src="${base}/platform/img/refresh.png"/></a> -->
                          </div>	
             


	           <button class="submit" id="loginsubmit"  style="margin-top:25px"><font style="font-size:2em">登录</font></button>
	           <div style="padding:3%;padding-bottom:5%;float:left">
	            <p class="otherinfo"><span class="forgetpwd"><a href="${base}/loginAction/findPwd.do">忘记密码?</a></span>
	         
	            </div>
<!-- 	            <div style="padding:3%;float:right"> -->
<!-- 	            <span class="toreg">还没有帐号? <a >立即注册！</a></span></p> -->
			      
<!-- 			    </div> -->
                  
           </form>
         <table style="width:100%">
 			<tr>
 			<td align="center"><hr style="border-top:1px solid #000;width:50px" /></td>
 			<td align="center">还不是世纪创投会员？</td>
 			<td align="center"><hr style="border-top:1px solid #000;width:50px" /></td>
 			</tr>
 			</table>
 		<div  style="padding-bottom:120px">	<a class="submit" href="${base}/register/index.do" style="text-align:center" ><font style="font-size:2em;text-align:center">立即注册</font></a></div>
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
 
$(function(){
	
	var zhi=$(".baizhi .wai i").html();
	var zhi1=100-zhi;
	$(".baizhi #aaa").animate({top:zhi1+"%"},600);
	
	$("body").append("<div class=\"bottom\"><a class=\"page\" href=\"${base}/index.do\"><img src=\"${base}/images/page.png\" /><span>主页</span>	</a><a class=\"lnd\" href=\"${base}/userIndex/index.do\"><img src=\"${base}/images/lnd.png\" /><span>账户</span></a><a class=\"out\" id=\"exitBtn\" href=\"${base}/loginAction/logout.do\"><img src=\"${base}/images/out.png\" /><span>退出</span></a></div>");
		$(".out").click(function(){
			 location.href='logout.do';
		})
	})
</script>
</body>
</html>