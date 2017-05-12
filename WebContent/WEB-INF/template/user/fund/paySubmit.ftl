<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>世纪创投-充值-sjct888.com</title>

<meta name="description" content="">
<meta name="author" content="">
</head>
<body onload="E_FORM.submit();">
	<div class="wapper" style="display: none" onMouseout="hidden();">
		<div class="main">
			<div class="user_panel">
				<div class="right">
					<div class="tab_table_N creditor">
						<div class="myRecharge" id="myRecharge_tab1">
							<form action="https://gateway.gopay.com.cn/Trans/WebClientAction.do" method="post"
								name="E_FORM" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
								<table>
									
									<tr>
										<td width="40%" align=right>版本号(version):</td>
										<td align=center><input type="text" id="version"
											name="version" value="${version}" size="50"
											readonly="readonly" /></td>
									</tr>
								
									<tr>
										<td width="40%" align=right>字符集(charset):</td>
										<td align=center><input type="text" id="charset"
											name="charset" value="${charset}" size="50" readonly="readonly"/> <br/>
											1:GBK,2:UTF-8 </td>
									</tr>
								
									<tr>
										<td width="40%" align=right>语言种类(language)：</td>
										<td align=center><input type="text" id="language"
											name="language" value="${language}" size="50" /> <br/>1:ZH,2:EN</td>
									</tr>
									<tr>
										<td width="40%" align=right>加密方式(signType)：</td>
										<td align=center><input type="text" id="signType"
											name="signType" value="${signType}" size="50" /> <br/>1:MD5,2:SHA</td>
									</tr>
								
								       <tr>
										<td width="40%" align=right>交易代码(tranCode):</td>
										<td align=center><input type="text" id="tranCode" name="tranCode"
											value="${tranCode}" size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>商户ID(merchantID):</td>
										<td align=center><input type="text" id="merchantID"
											name="merchantID" value="${merchantID}" size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>订单号(merOrderNum):</td>
										<td align=center><input type="text" id="merOrderNum"
											name="merOrderNum" value="${merOrderNum}" size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>交易金额(tranAmt):</td>
										<td align=center><input type="text" id="tranAmt" name="tranAmt"
											value="${tranAmt}"size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>手续费(feeAmt):</td>
										<td align=center><input type="text" id="feeAmt" name="feeAmt"
											value="${feeAmt}" size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>币种(currencyType):</td>
										<td align=center><input type="text" id="currencyType"
											name="currencyType" value="${currencyType}" size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>商户返回页面地址 (frontMerUrl):</td>
										<td align=center><input type="text" id="frontMerUrl" name="frontMerUrl"
											value="${frontMerUrl}" size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>商户后台通知地址(backgroundMerUrl):</td>
										<td align=center><input type="text" id="backgroundMerUrl" name="backgroundMerUrl"
											value="${backgroundMerUrl}"  size="50" /></td>
									</tr>
								
								
								       <tr>
										<td width="40%" align=right>交易时间(tranDateTime):</td>
										<td align=center><input type="text" id="tranDateTime"
											name="tranDateTime" value="${tranDateTime}" size="50" /></td>
									</tr>
									
									<tr>
										<td width="40%" align=right>国付宝转入账户（virCardNoIn）:</td>
										<td align=center><input type="text" id="virCardNoIn"
											name="virCardNoIn" value="${virCardNoIn}" size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>用户浏览器IP（tranIP）:</td>
										<td align=center><input type="text" id="tranIP" name="tranIP"
											value="${tranIP}" size="50" /></td>
									</tr>
								
								
								        <tr>
										<td width="40%" align=right>订单是否允许重复提交（isRepeatSubmit）:</td>
										<td align=center><input type="text" id="isRepeatSubmit" name="isRepeatSubmit"
											value="${isRepeatSubmit}"  size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>商品名称（goodsName）:</td>
										<td align=center><input type="text" id="goodsName"
											name="goodsName" value="${goodsName}"  size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>商品详情（goodsDetail）:</td>
										<td align=center><input type="text" id="goodsDetail"
											name="goodsDetail" value="${goodsDetail}"size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>买方姓名（buyerName）:</td>
										<td align=center><input type="text" id="buyerName" name="buyerName"
											value="${buyerName}" size="50" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>买方联系方式（buyerContact）:</td>
										<td align=center><input type="text" id="buyerContact" name="buyerContact"
											value="${buyerContact}" size="50" /></td>
									</tr>
								
								     	<tr>
										<td width="40%" align=right>商户备用信息字段1（merRemark1）:</td>
										<td align=center><input type="text" id="merRemark1" name="merRemark1"
											value="${merRemark1}" size="50" /></td>
									</tr>        
								       <tr>
										<td width="40%" align=right>商户备用信息字段2（merRemark2）:</td>
										<td align=center><input type="text" id="merRemark2" name="merRemark2"  
											value="${merRemark2}"size="50" /></td>
									   </tr>  
								
								        <tr>
										<td width="40%" align=right>银行代码（bankCode）:</td>
										<td align=center><input type="text" id="bankCode" name="bankCode"
											value="${bankCode}"size="50" /></td>
									   </tr> 
								          <tr>
										<td width="40%" align=right>用户类型（userType）:</td>
										<td align=center><input type="text" id="userType" name="userType"
											value="${userType}" size="50" /></td>
									   </tr> 
								      <tr>
										<td width="40%" align=right>MD5加密报文(signValue):</td>
										<td align=center><input type="text" id="signValue"
											name="signValue" value="${signValue}" size="50"
											readonly="readonly" /></td>
									</tr>
									<tr>
										<td width="40%" align=right>国付宝服务器时间(时间戳防钓鱼):</td>
										<td align=center><input type="text" id="gopayServerTime"
											name="gopayServerTime" value="${gopayServerTime}" size="50"
											readonly="readonly" /></td>
									</tr>
									<tr>
										<td colspan=2>&nbsp;</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>