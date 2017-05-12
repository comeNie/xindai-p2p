<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>世纪创投</title>

<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport"content="width=1349;"/> 
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
                        <li class="ui-step-start ui-step-active">
                            <div class="ui-step-line">-</div>
                            <div class="ui-step-icon">
                                <i class="iconfont">&#xe610;</i>
                                <i class="ui-step-number">1</i>
                                <span class="ui-step-text">填写账户信息</span>
                            </div>
                        </li>
                        <li class="">
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
                <form class="ui-form" method="post" action="findPwdByMobile.do" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
             		<div id="findPswByMobile" class="reg-con" >
  				  	  <div class="inputs">
  					 	<table width="50%" border="0" cellpadding="0" cellspacing="0" class="tableT_reg" align="center">
  		                  <th><em>*</em>手机号：</th>
                            <td>
                            	 <input type="text" name="mobile" id="mobile" value="${mobile}"  data-rule="手机号码:required; mobile;remote[get:ajaxCheckMobile.do]" />
                            </td>
                          </tr>
<!--                           <th><em>*</em>验证码：</th> -->
<!--                            	<td> -->
<!--                             	<input class="input-small" name="j_captcha" type="text" data-rule="验证码:required;length[4];remote[get:ajaxCaptcha.do]" value="" /> -->
<!--                             	<img style="vertical-align: top;cursor:pointer;margin-left:120px;" id="captachaImg" src="${base}/captcha.jpg"/> -->
<!--                             	<img style="vertical-align: top; cursor:pointer;" id="refreshBtn"  src="${base}/platform/img/refresh.png"/> -->
<!--                             </td>  -->
<!--                           </tr>  -->
                           <div class="row bor-mon-inp"> 
						 <th><em>*</em>验证码：</th> </th>
<td>
                        <div class="inputwrap"><input style="width:100px;" class="bm-inp-v" type="text" value="" name="j_captcha" data-rule="验证码:required;length[4];remote[get:ajaxCaptcha.do]"/></div>
                            <img class="bm-img-v" id="captachaImg" src="${base}/captcha.jpg"/>
                        <a id="refreshBtn"><img  class="bm-img-c"  src="${base}/platform/img/refresh.png"/></a>
                        <span class="msg-box n-right" for="j_captcha"></span>
					</div>
					</td> 
					
                            
  						<tr> 
  							<td></td>
	  					 	<td align="left">
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