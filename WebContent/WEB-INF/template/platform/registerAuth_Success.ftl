<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>世纪创投</title>

<meta name="description" content="">
<meta name="author" content="">
<#include "/common/resource.ftl">
</head>
<script>
!function(w,d,e){
var _orderno= ${Session.loginInfo.custId};  //替换此处!;
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('NZs.pis.GQ9Kkc0KnoHwSbI0aPxatP')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);
</script>
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
                        <li class="ui-step-done">
                            <div class="ui-step-line">-</div>
                            <div class="ui-step-icon">
                                <i class="iconfont">&#xe610;</i>
                                <i class="ui-step-number">2</i>
                                <span class="ui-step-text">手机验证</span>
                            </div>
                        </li>
                        <li class="ui-step-active">
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
                   <form id="authForm" class="form" action="${base}/register/authRealName.do" method="post" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
                	<h3>恭喜，您的帐号已经注册成功</h3>
                    <p>超过80%的用户立即进行实名认证，账户更安全理财更便捷</p>
                    <p><label>姓名：</label>
	                    <span class="input_sm">
	                    	<input type="text" size="18" id="realname" name="realname" data-rule="姓名:required;"/>
	                    </span>
                    </p>
                    <p><label>身份证号：</label>
                    	<span class="input_sm">
                   	    	<input type="text" size="18" id="idcard" name="idcard" />
                   	    </span>
                   	    <span class="auth-error">${errorMsg}</span>
                   	</p>
                    <p class="reg-btnbar">
                        <button type="submit" class="btn btn-primary">认证</button>
                        <a class="btn btn-warning" href="${base}/userIndex/index.do" >跳过此步</a></p>
                   </form>
                </div>
            </div>
           </div>
            </div>
        </div>
    
<#include "/common/footer.ftl">
</div>
<script type="text/javascript">

/* $(document).ready(function () {
    $('#authForm').validator({
		rules: {
			idcard: function(element, param, field) {
				return isIdCardNo(element.value) || '请正确输入您的身份证号码';
		 }
		 },
		fields: {
			idcard: '身份证号码:required;idcard;'
		}
	});
}); */

</script>
</body>
</html>