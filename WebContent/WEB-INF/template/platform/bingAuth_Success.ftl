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
                 <div class="regStep_h">
                    <div class="regStep_process">
                        <ul>
                            <li class="yellow k_f"> </li>
                            <li class="one"> </li>
                            <li class="yellow k_gruy"> </li>
                            <li class="two two_cur"> </li>
                            <li class="yellow k_yellow"></li>
                            <li class="last last_cur"> </li>
                            <li class="yellow k_l "> </li>
                        </ul>
                    </div>
                    <div class="regStep_title">
                        <span class="first">填写账户信息</span>
                        <span>验证账户信息</span>
                        <span>完成</span>
                    </div>
                </div>
                <div class="clear"></div>
                <div class="reg_content">
                   <form id="authForm" class="form" action="${base}/register/authRealName.do" method="post" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
                	<h3>恭喜，你的邮箱解绑或者绑定成功</h3>
                    <span><a href="${base}/index.do" >首页</a></span></p>
                   </form>
                </div>
            </div>
           </div>
            </div>
        </div>
   
<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
$(document).ready(function () {
});
</script>
</body>
</html>