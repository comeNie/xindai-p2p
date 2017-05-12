<style>
.selected{
color:white;  background:#ffb23f;  border-radius: 5px;
}
</style>

<script>
var errMes = '${errMes}';
var sucMes = '${sucMes}';
var isFistLoad1 = true;
var isFistLoad2 = true;
var isFistLoad3 = true;
var isFistLoad4 = true;
if(errMes!=''){
  showDialogNew(true,errMes,'E','提醒信息');
}

if(sucMes!=''){
  showDialogNew(true,sucMes,'S','提醒信息');
}
</script>
<div class="header">
    <div class="head-nav">
    	<div class="head-nav-bd">
        	<div class="logo" style="margin-top:10px;>
            	<a href="${base}/index.do"><img src="${base}/platform/img/logo.gif"></a>
            </div>
            <div class="menu">
            	<ul>
        		<li ><a href="${base}/index.do">首页</a></li> 
                    <li id="mylend"><a href="${base}/loan/list.do">我要投资</a>
                
                    </li>
                    <li  class="selected" id="myLoan"><a href="${base}/loan/goToLoan.do?productNo=1">我要借款</font></a>
               
                    </li>
                    <li><a href="${base}/pmClassroomAction/pmClassroom.do">安全保障</a></li>
                </ul>	
            </div>
             <div class="menu-right"> 
                
                <div id="loginSessionDiv" <#if Session.loginInfo?exists>style="display:none;"</#if>>
            	<#--<a id="loginBtn" href="javascript:void(0);">登录</a>--> 
            	<a id="loginbutt" href="${base}/loginAction/login.do">登录</a>
                <#--<div id="loginDiv" class="signbox" style="display:none;">
                	<form id="loginForm" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
                	<p>邮箱/手机认证用户可直接登录 </p>
                	<p><span id="errorMsg" style="color:Red"></span></p>
                    <p><input id="username" type="text" name="username"  placeholder="请输入用户名" class="input-sm"/></p>
                    <p><input id="password" type="password" name="password" placeholder="请输入密码" class="input-sm"/></p>
                    <p><input id="nochecklogin" name="nochecklogin" type="checkbox" checked/>&nbsp;<span>十天免登录</span><a href="javascript:void(0)">忘记密码？</a></p>
                    <p><button id="ajaxLoginBtn" type="button" class="btn btn-primary">登录</button></p>
                    </form>
                </div>-->
                <a id="registerBtn" href="${base}/register/index.do">注册</a>
                </div>
                

                <div id="accountSessionDiv"  <#if !Session.loginInfo??>style="display:none; "</#if>>

                <div id="accountSessionDiv" style="<#if !Session.loginInfo??> display:none;</#if>width:180px;">

                <!--不是客户信息，就不显示-->
	                <a id="myaccountBtn" href="${base}/userIndex/index.do">您好,${Session.loginInfo.userCode}</a>
	                <a id="exitBtn" href="${base}/loginAction/logout.do">退出</a>
                </div>
            </div>
           <div class="clearfix"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	startRecRequest();	
	setInterval(startRecRequest,30000);

	var h = $(document).height();
	$(".overlay").css({"height": h });
		
	$('#mylend').hover(function() {
		$('#mylend_second').show();	
	}, function() {
		$('#mylend_second').hide();		
	});
	
    $('#myLoan').hover(function() {
		$('#myLoan_second').show();	
	}, function() {
		$('#myLoan_second').hide();		
	});
	
	
	$('#loginBtn').click(function(){
	    if($("#loginDiv").is(":visible")){  
	    	$('#loginDiv').hide();
	    }else{
	    	$('#loginDiv').show();
		}
	});
	
 
	
//	$('#loginForm').keydown(function(e){
//	 var e = e || event,
// 	keycode = e.which || e.keyCode;
//	if (keycode==13){
//		ajaxLogin();
//	  }
//	});

});



function startRecRequest(){
		$.ajax({
				type:"POST",
				url:"${base}/userMessage/ajaxQueryMsgCnt.do",
				dataType:"json",
				
				complete:function(){
				},
				success:function(data) {
				    if(data.status == 1){
				    	$('#recNum').html("("+data.cnt+")");
				    }
				},
				error:function(text) {
					$('#recNum').html("(0)");
				} 
			});
}


</script>