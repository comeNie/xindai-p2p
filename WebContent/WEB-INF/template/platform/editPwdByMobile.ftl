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
        <div class="product_index">
        	<div class="logincombox">
        		 <div class="regstep">
                    <ol class="ui-step ui-step-blue ui-step-4">
                       <li class="">
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
                        <li class="ui-step-start ui-step-active">
                            <div class="ui-step-line">-</div>
                            <div class="ui-step-icon">
                                <i class="iconfont">&#xe610;</i>
                                <i class="ui-step-number">3</i>
                       			<span class="ui-step-text">密码重设</span>
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
        			<form class="ui-form" method="post" action="editPwdByMobileAuth.do" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
        				<div id="findPswByMobile" class="reg-con" >
	  					 	<div class="inputs">
	  					 	
	  					 		<table width="50%" border="0" cellpadding="0" cellspacing="0" class="tableT_reg" align="center">
  		                  			<th><em>*</em>新密码：</th>
                           				<td>
                           					<input class="ui-input" type="password" name="password" id="password"  data-rule="新密码:required; password;" />
							                <input class="ui-input" type="hidden" name="authCode" id="authCode" value="${authCode}" />
							               	<input class="ui-input" type="hidden" name="mobile" id="mobile"  value="${mobile}" />
                            			</td>
                         		 	</tr>
                          			<th><em>*</em>确定新密码：</th>
                           				<td>
			                            	<input class="ui-input" type="password" name="configPassword" id="configPassword"  data-rule="确认新密码:required;match(password); password;" /> 
			                            </td> 
			                        </tr> 
			  						<tr>
			  							<td></td> 
				  					 	<td  align="left">
											<input type="submit" value="提 交"  class="Operation ui-button ui-button-blue ui-button-mid" />
										</td>
								    </tr>
						   		</table>
	  					 	</div>
	  					 </div>
  					 </form>
  				  	</div>	
            </div>
        </div>
    </div>

<#include "/common/footer.ftl">
</div>
</body>
</html>