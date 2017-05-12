
<!DOCTYPE html>  <!--  注册 -->
<html lang="en">
<head>
<meta charset="utf-8">
<title>世纪创投</title>
 <meta name="viewport"content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no minimal-ui">
<meta name="description" content="">
<meta name="author" content="">
<link href="${base}/css/styleApp.css" rel="stylesheet" type="text/css">
<link href="${base}/css/loginAPP.css" rel="stylesheet" type="text/css">
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
.n-top, .n-right, .n-bottom, .n-left 
{
display: inline-block;
line-height: 0;
vertical-align: top;
outline: 0;
margin-left: 180px;
margin-top:-90px
}
.n-yellow .n-tip, .n-yellow .n-ok, .n-yellow .n-loading 
{
background-color: #f8fdff;
border-color: #ddd;
color: #333;
box-shadow: 0 1px 3px #ccc;

}
.n-arrow b, .n-arrow i {
line-height: 0px;
}
.red_text {
float: left;
width: 55%;
margin-left: 5%;
height: 35px;
line-height: 55px;
font-size: 20px;
text-indent: 5px;
color: #a2a2a2;}
</style>
</head>
<body>
<div class="logo">
	<img src="${base}/platform/img/logo.gif" />
</div>
<div class="head_title" style="height:40px">
	<a href="javascript:history.back(-1)" style="margin-top:-6px"> <</a>
	<p style="margin-top: 10px;margin-left: -35%;">我要借款</p>
</div>
       
               <form id="goToLoanForm" class="form" action="${base}/loan/applyLoan.do" method="post"  autocomplete="off" data-validator-option="{theme:'yellow_right_effect',stopOnError:true}"> 
						<p class="red_text">标的名称</p>
                        <div class="code1">
                        	 <input type="hidden" name="productId"  class="span3" value="${productNo}" />
                        	
                            	<input type="text"     name="loanName"  data-rule="标的名称:required;length[3~50];"/>
						</div>
						<p class="red_text">期望年利率(%)</p>
						<div class="code1">
                             <input type="text" placeholder="请输入${leastRateYear*100}到${mostRateYear*100}"  name="expectLoanRate" name="expectLoanRate" data-rule="期望年利率:required;myfloor;" data-rule-myfloor="[/^\d{1,2}?$/, '请输入整数']"/> 
                        </div>
                        <p class="red_text">贷款金额</p>
                        <div class="code1">    
                           <input type="text"  placeholder="输入金额要大于1000"  name="applyAmt"  data-rule="贷款金额:required;"/>
                        </div>
                        <p class="red_text">贷款期限(月)</p>
                          <div id="phoneTr" class="code1">
                            	<input type="text"  name="loanTerm"  value=""  data-rule="贷款期限:required;" />
                          </div>
                          <p class="red_text">借款用途</p>
                          <div id="phoneTr" class="code1">
                            	 <textarea id="myarea" rows="4" style="width:100%;border: 1px solid #cecece;overflow-x:hidden;overflow-y:hidden" onKeyUp="keypress2()" onblur="keypress2()"></textarea>
                          </div>
                         <p class="red_text" style="margin-top: 25px;">验证码</p>
                          <div class="code1">
                            	<input class="bm-inp-v"  type="text" value="" name="j_captcha"data-rule="验证码:required;length[4];remote[get:ajaxCaptcha.do]"/>
                            	<span class="msg-box n-right" for="j_captcha"></span>
                              	<img style="vertical-align: top;cursor:pointer;margin-top: -40px;float:right;margin-right:10px" id="captachaImg"  src="${base}/captcha.jpg"/>
<!--                             	<a style="vertical-align: top;margin-top: -40px;" id="refreshBtn"><img style="cursor:pointer;"  src="${base}/platform/img/refresh.png"/></a> -->
                          </div>
                   <div style="margin-left:20px">
					<span><input name="allowp" type="checkbox" data-rule="checked;" data-msg-checked="请同意我们的条款" checked />&nbsp;
					<a  href="${base}/loan/mobileLoanInfo.do" >我已经阅读并同意使用条款和隐私条款</a></span>
					</div>
                       <div style="line-height:20px"  class="code1"><font style="color:#FA9600">借款人平台管理费，唯一指定账号：<br><font style="margin-top:1%">户名：中国工商银行深圳景田支行 <br>开户银行： 深圳市世纪创投互联网金融服务有限公司<br>卡号：<strong>4000 0298 0920 0323 581</strong> </font></font></div>
					</div> 
<!-- 					<div class="bor-mon-submit" style="position:absolute;top:120%;"> -->
				
<!-- 					</div> -->
					<div>
					
                    <div style="padding-bottom: 100px;">
                   
                    <button class="submit2" style="margin-top:64px"  onclick="$('#goToLoanForm').submit();" >提交申请</button>
                    </div>
                    </div>
                  </form>
</div>

<script src="${base}/platform/js/placeholders.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	
$(".bor-mon-inp").find("input").attr("style","color:#cccccc;");
$(".bor-mon-inp").find("input").attr("flagv","1");
$(".bor-mon-inp").find("input").focus(function(){$(this).attr("style","color:#000000;");if($(this).attr("flagv")=="1"){$(this).val("");$(this).attr("flagv","0")}});
//$(".bor-mon-box").find("input").blur(function(){$(this).attr("style","color:#eaeaea;");});
$(".bor-mon-textarea").find("textarea").attr("style","color:#cccccc;");
$(".bor-mon-textarea").find("textarea").attr("flagv","1");
$(".bor-mon-textarea").find("textarea").focus(function(){$(this).attr("style","color:#F82239;");if($(this).attr("flagv")=="1"){$(this).val("");$(this).attr("flagv","0")}});

$(".selectbyimg").click(function(){
	var tempIdStr=$(this).attr("vid");
	var tempObj=$("#"+tempIdStr);

    var selectinp = $(this).parent().find("input:text");
    var offset = selectinp.offset();
    $("#"+$(this).attr("vid")).css({
        top : offset.top + selectinp.outerHeight(),
        left : offset.left,
        width : selectinp.outerWidth()-2
    });
	$("#"+$(this).attr("vid")).find("li").click(function(){
        $("#"+tempIdStr+"inp").val($(this).html());
        $("."+tempIdStr+"inp").val($(this).attr("sval"));
        $(tempObj).hide();
    });
	$("#"+$(this).attr("vid")).show();
	
	});
	
   
   $("#captachaImg").click(function(){
   	    // alert("535555");
   		refreshCaptcha();
   });
   
   $("#refreshBtn").click(function(){
//    		 alert("535555");
   		refreshCaptcha();
   });
   
   function refreshCaptcha(){
        var randomVal = Math.random();
       // alert(randomVal);
   		$("#captachaImg").attr("src",'${base}/captcha.jpg?'+randomVal);    //其实这里头是一个方法,后面的randomVal是一个参数
   }
	
});
$(function(){
	
	var zhi=$(".baizhi .wai i").html();
	var zhi1=100-zhi;
	$(".baizhi #aaa").animate({top:zhi1+"%"},600);
	
		$("body").append("<div class=\"bottom\"><a class=\"page\" href=\"${base}/index.do\"><img src=\"${base}/images/page.png\" /><span>主页</span>	</a><a class=\"lnd\" href=\"${base}/userIndex/index.do\"><img src=\"${base}/images/lnd.png\" /><span>账户</span></a><a class=\"out\" id=\"exitBtn\" href=\"${base}/loginAction/logout.do\"><img src=\"${base}/images/out.png\" /><span>退出</span></a></div>");
		$(".out").click(function(){
// 			 location.href='loginAction/logout.do';
		})                                                                                                                               
	})

</script>
</body>

</html>
