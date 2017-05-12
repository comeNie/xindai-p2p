<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>世纪创投</title>

<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
<meta name="author" content="">
<link rel="stylesheet" type="text/css" href="${base}/platform/js/validator-0.7.3/jquery.validator.css">
<script type="text/javascript" src="${base}/platform/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${base}/platform/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${base}/platform/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${base}/platform/js/idcard.validator.js"></script>
<script type="text/javascript" src="${base}/platform/js/validator-0.7.3/jquery.validator.js"></script>
<script type="text/javascript" src="${base}/platform/js/validator-0.7.3/local/zh_CN.js"></script>
<script type="text/javascript" src="${base}/js/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/platform/js/dialog-min.js"></script>
<script charset="utf-8" src="http://wpa.b.qq.com/cgi/wpa.php"></script>
<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="${base}/platform/bootstrap/js/bootstrap.js"></script>
<link href="${base}/css/styleApp.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${base}/css/newRega205.css">
 <link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
<style>
body{color:#ed0000}
a:hover, a:focus {
color:#FA9600;
text-decoration: underline;
}
.realName_success .title, .regSuccess .title {
padding: 2.5rem 0;
font-size: 2.34rem;
color: #6E6E6E;}
.n-top, .n-right, .n-bottom, .n-left {
display: inline-block;
line-height: 0;
vertical-align: top;
outline: 0;
margin-left: -22px;
margin-top:5px;
}
.n-yellow .msg-wrap {
position: absolute;
z-index: 1;
padding: 4px 6px;
font-size: 14px;
border: 0px solid transparent;
background-color: #fff;
border-color: #fff;
color: #ed0000;
box-shadow: 0 1px 3px #fff;
border-radius: 2px;
}
.n-left .n-arrow, .n-right .n-arrow {
width: 0px;
height: 12px;
top: 6px;}
*, *:before, *:after {
/* -webkit-box-sizing: border-box; */
-moz-box-sizing: inherit;
box-sizing: inherit;
}
.n-yellow .msg-wrap .n-icon {
 background-image: url("images1/validator_simple.png"); 
}
</style>
</head>
<script>
!function(w,d,e){
var _orderno= ${Session.loginInfo.custId};  //替换此处!;
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('NZs.pis.GQ9Kkc0KnoHwSbI0aPxatP')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);
</script>
<body class="newReg">
<div class="logo">
	<img src="${base}/platform/img/logo.gif" />
</div>
<hr style="border-top:10px solid #FDF8F8;" />
<p style="text-align:center;padding: .2rem 0;font-family: 'Microsoft YaHei';font-size:24px;color:#ffb23f ">恭喜，您的帐号已经注册成功</p><br>
<p style="text-align:center;font-family: 'Microsoft YaHei';font-size:18px;"><font style="color:#333">超过80%的用户立即进行实名认证，<br>账户更安全理财更便捷</font></p>
		<div class="regSuccess">
			<div class="regSuccess_t">
<!-- 	   			<div class="title" style="padding: .2rem 0;"> -->
<!-- 	   				<span>身份认证</span> -->
<!-- 	   			</div> -->
				<hr style="border:none;width:100%; border-top:8px solid #F8F8F8;" />
				 <form id="authForm" class="form" action="${base}/register/authRealName.do" method="post" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
                	
                   <div class="container">
                   	<div class="row">
                   		<div class="col-sm-12">

	                    	<input type="text" style="height:40px;width:93%"" class="form-control" size="18" id="realname" name="realname"  placeholder="真实姓名" data-rule="姓名:required;"/>

	                    </div>
                    </div>
                    <div class="row">
                    	<div class="col-sm-12">
                   	    	<input type="text" class="form-control" style="height:40px;width:93%;margin-top:25px" size="18" id="idcard" placeholder="身份证号" name="idcard" />
                   	    
                   	    <span class="auth-error"style="margin-left: 3%;"><font style="font-size:13px">${errorMsg}</font></span>
                   	    </div>
                  	</div>
                  	<table style="margin:0 auto"border="1">
                  		<tr>
	                 <td>   <button  type="submit" class="reg_btn" style="height: 2.82rem;margin: 1.6rem auto 0;width: 7.5rem;line-height:1rem;background:#ffb23f;font-size:14px;border-radius:10px">下一步</button></td>
	                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	                <td>  <a class="btn btn-warning" href="${base}/userIndex/index.do" style="background:#ffb23f;border-radius: 10px;height: 1.7rem;margin-top:19px;border-color:#ffb23f;width: 5.5rem;" >跳过此步</a></td>
	                  
	                  	</tr>
	                  </div>
                  </table>
                   </form>
<div class="title" style="text-align:center">
				<p>如有疑问，请联系客服：</p><font style="color:#ffb23f">400-017-4688</font>
			</div>

		</div>

</body>
</html>