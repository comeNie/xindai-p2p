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
                            <li class="gruy k_yellow"></li>
                            <li class="last last_cur"> </li>
                            <li class="gruy k_l "> </li>
                        </ul>
                    </div>
                    <div class="regStep_title">
                        <span class="first">填写账户信息</span>
                        <span>验证账户信息</span>
                        <span>完成</span>
                    </div>
                </div>
                <div class="clear"></div>
                <div id="sendsuccess" class="reg_content">
                	<p class="email">我们给您的邮箱<span>${email}<br></span>发送了一封验证邮件请按照邮箱提示在<a href="javascript:void(0);">24小时内激活您的帐号</a></p>
                    <p><span><button type="button" class="btn btn-primary">去邮箱查看</button></span><span><button class="btn btn-warning">重新发送</button></span></p>
                </div> 
            </div>
           </div>
            </div>
        </div>
    
<#include "/common/footer.ftl">
</div>
</body>
</html>