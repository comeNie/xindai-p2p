<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>世纪创投-充值-sjct888.com</title>

<meta name="description" content="">
<meta name="author" content="">

</head>
<body onload="E_FORM.submit();">
	<div class="wapper" style="display: none" onMouseout="hidden();" >
		<div class="main">
			<div class="user_panel">
				<div class="right">
					<div class="tab_table_N creditor">
						<div class="myRecharge" id="myRecharge_tab1">
							<form action="${requestUrl}" method="post"
								name="E_FORM" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
								<table>
										<!-- 报文参数 -->
									<tr>
										<td width="40%">1. 编码:</td>
										<td><input type="text" id="inputCharset"
											name="inputCharset" value=" ${inputCharset}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>2. 取货地址:</td>
										<td><input type="text" id="pickupUrl"
											name="pickupUrl" value="${pickupUrl}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>3. 商户系统通知地址:</td>
										<td><input type="text" id="receiveUrl"
											name="receiveUrl" value="${receiveUrl}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>4. 报文版本号:</td>
										<td><input type="text" id="version"
											name="version" value="${version}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>5. 语言:</td>
										<td><input type="text" id="language"
											name="language" value="${language}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>6. 签名方式:</td>
										<td><input type="text" id="signType"
											name="signType" value="${signType}" readonly="readonly"/></td>
									</tr>
									<!-- 买卖双方信息参数 -->
									<tr>
										<td>7. 商户号:</td>
										<td><input type="text" id="merchantId"
											name="merchantId" value="${merchantId}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>8. 付款人名称:</td>
										<td><input type="text" id="payerName"
											name="payerName" value="${payerName}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>9. 付款人联系email:</td>
										<td><input type="text" id="payerEmail"
											name="payerEmail" value="${payerEmail}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>10. 付款人电话:</td>
										<td><input type="text" id="payerTelephone"
											name="payerTelephone" value="${payerTelephone}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>11. 付款人证件号:</td>
										<td><input type="text" id="payerIDCard"
											name="payerIDCard" value="${payerIDCard}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>12. 合作伙伴的商户号:</td>
										<td><input type="text" id="pid"
											name="pid" value="${pid}" readonly="readonly"/></td>
									</tr>
									<!-- 业务参数 -->
									<tr>
										<td>13. 商户系统订单号:</td>
										<td><input type="text" id="orderNo"
											name="orderNo" value="${orderNo}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>14. 订单金额(单位分):</td>
										<td><input type="text" id="orderAmount"
											name="orderAmount" value="${orderAmount}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>15. 金额币种:</td>
										<td><input type="text" id="orderCurrency"
											name="orderCurrency" value="${orderCurrency}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>16. 商户的订单创建时间:</td>
										<td><input type="text" id="orderDatetime"
											name="orderDatetime" value="${orderDatetime}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>17. 订单过期时间:</td>
										<td><input type="text" id="orderExpireDatetime"
											name="orderExpireDatetime" value="${orderExpireDatetime}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>18. 商品名称:</td>
										<td><input type="text" id="productName"
											name="productName" value="${productName}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>19. 商品单价:</td>
										<td><input type="text" id="productPrice"
											name="productPrice" value="${productPrice}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>20. 商品数量:</td>
										<td><input type="text" id="productNum"
											name="productNum" value="${productNum}" readonly="readonly" /></td>
									</tr>
									<tr>
										<td>21. 商品标识:</td>
										<td><input type="text" id="productId"
											name="productId" value="${productId}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>22. 商品描述:</td>
										<td><input type="text" id="productDesc"
											name="productDesc" value="${productDesc}" readonly="readonly"/></td>
									</tr>  
									<tr>
										<td>23. 附加参数1:</td>
											<td><input type="text" id="ext1"
											name="ext1" value="${ext1}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>24. 附加参数2:</td>
										<td><input type="text" id="ext2"
											name="ext2" value="${ext2}" readonly="readonly"/></td>
									</tr>			
									<tr>
										<td>25. 支付方式:</td>
										<td><input type="text" id="payType"
											name="payType" value="${payType}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>26. 发卡行机构号:</td>
										<td><input type="text" id="issuerId"
											name="issuerId" value="${issuerId}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>27. 付款人支付卡号:</td>
										<td><input type="text" id="pan"
											name="pan" value="${pan}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>28. 贸易类型:</td>
										<td><input type="text" id="tradeNature"
											name="tradeNature" value="${tradeNature}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>29. 报文签名后内容:</td>
										<td><input type="text" id="signMsg"
											name="signMsg" value="${signMsg}" readonly="readonly"/></td>
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