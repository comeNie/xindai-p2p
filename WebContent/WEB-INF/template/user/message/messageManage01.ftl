<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=1349">
<#include "/common/resource.ftl">
</head>
<body>
<div id="messageManage_tab1" class="repayment">
	<div
		style="font-size: 20px; font-family: '宋体'; margin-left: 3%; line-height: 30px">
		我的推荐码: <font
			style="color: #FA9600; font-size: 20px; font-family: '宋体'; margin-left: 1%">
			<#if refereeStatus == "0"> <a class="btn btn-primary"
			data-toggle="modal" href="#myModal1"
			style="background: #ffb23f; border-radius: 5px; margin-top: -8px; margin-left: 10px"
			onclick="insertProInfo();"> <font style="color: #fff">申请推荐码</font></a>

			<#elseif refereeStatus == "1" || refereeStatus == "4" ||
			refereeStatus == "6"> <font
			style="font-size: 14px; line-height: 30px">推荐码申请审核中</font> <#elseif
			refereeStatus == "2"> ${refereeCode} <span style="margin-left: 30px"><button
					class="btn btn-primary" type="button" style="border-radius: 10px">
					<a target="_black" href='${base}/userMessage/recommendProtocol.do'><font
						style="font-size: 16px; color: #fff">查看推广协议</font></a>
				</button></span> <#elseif refereeStatus == "7"> <font
			style="font-size: 14px; line-height: 30px">推荐码申请不通过</font> <#elseif
			refereeStatus == "3" || refereeStatus == "5"> <a
			class="btn btn-primary" data-toggle="modal" href="#myModal1"
			style="background: #ffb23f; border-radius: 5px; margin-top: -8px; margin-left: 10px"
			onclick="insertProInfo();"><font style="color: #fff">再次申请</font></a>
			<#else> <font style="font-size: 14px; line-height: 30px">(温馨提示：您还未获得推荐码，参与投资成功1笔即可申请获得推荐码。)</font>
			</#if>
		</font>

	</div>

	<!--                 <div class="clear"></div> -->
	<div
		style="font-size: 20px; font-family: '宋体'; margin-left: 3%; margin-top: 3%">推广链接:</div>
	<li style="width: 25%; margin-left: 38%"><img
		src="${base}/userMessage/loadImage.do?url=${url}" /></li>
	<li style="text-align: center; font-size: 20px;"><a
		href="${base}/register/gotoRegister.do?recommender=${refereeCode}">${url}</a></li>
</div>
<div id="myModal" style="position: absolute; z-index: 999999"
	class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-header" style="text-align: left">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h3 id="myModalLabel">推广协议需知</h3>
	</div>
	<div class="modal-body">
		<h1 style="text-align: center; font-size: 18px">推广协议需知</h1>
		<p>
			<strong>甲方：深圳市世纪创投互联网金融服务有限公司</strong>
		</p>
		<p>地址：</p>
		<p>法定代表人：</p>
		<p>&nbsp;&nbsp;&nbsp;</p>
		<p>
			<strong>乙方：</strong>
		</p>
		<p>身份证号：</p>
		<p>住址：</p>
		<p>&nbsp;&nbsp;</p>
		<p>鉴于：</p>
		<p style="text-indent: 20px">1.甲方是一家依法设立并存续的互联网企业，并依法运营世纪创投平台，为客户提供P2P互联网金融信息中介服务。</p>
		<p style="text-indent: 20px">2.乙方是依法设立并存续的企业/已满18周岁并具有完全民事行为能力的自然人，并已成为世纪创投平台的实名注册会员。</p>
		<p style="text-indent: 20px">3.乙方自愿申请成为甲方运营的世纪创投平台的推广人员，依法合规的推广世纪创投平台，甲方经审核通过，愿意接受乙方的申请，将平台推广相关事宜委托给乙方。</p>
		<p style="text-indent: 20px">为此，根据中国相关法律法规，甲、乙双方，经友好协商一致，就甲方委托乙方为甲方推广甲方运营的世纪创投平台的相关事宜达成以下协议，以资各方共同信守。</p>
		<p style="text-indent: 20px">
			<strong>一、委托事项</strong>
		</p>
		<p style="text-indent: 20px">甲方委托乙方推广世纪创投平台，为甲方发展世纪创投平台的会员并促成被推荐会员在世纪创投平台上进行投融资等活动。</p>
		<p style="text-indent: 20px">
			<strong>二、推广费用</strong>
		</p>
		<p style="text-indent: 20px">甲方支付给乙方的推广费用按照乙方推广的实际效果执行，具体包括两个部分：</p>
		<p style="text-indent: 20px">1.乙方每促成一个单位或个人在世纪创投平台上实名注册成功，甲方向乙方支付【】元人民币；</p>
		<p style="text-indent: 20px">2.乙方促成其推荐的会员在世纪创投平台上投资的，甲方向乙方支付本次投资金额【】%的推广费。</p>
		<p style="text-indent: 20px">甲方向乙方支付的以上推广费用包括了乙方在推广过程中所支出的费用及相应收益。</p>
		<p style="text-indent: 20px">
			<strong>三、双方的权利、义务与责任</strong>z
		</p>
		<p style="text-indent: 20px">（一）甲方的权利、义务与责任</p>
		<p style="text-indent: 20px">1、甲方承诺为乙方的履行本协议项下的推广事宜提供必要的便利。</p>
		<p style="text-indent: 20px">2、甲方保证世纪创投平台依法、正常地运营，以便乙方推荐他人在世纪创投平台上进行实名注册和投融资等相关活动。</p>
		<p style="text-indent: 20px">3、甲方保证按照本协议约定向甲方支付相应的推广费用。</p>
		<p style="text-indent: 20px">4、甲方在对乙方提交的相关资料（包括但不限于【身份证复印件、企业营业执照、工作证明、户口本复印件、学历证明复印件、征信报告】）审核通过后，将为乙方生成专属二维码，以便乙方完成本协议项下的推广事宜。</p>
		<p style="text-indent: 20px">5、甲方有权利向乙方就本协议约定的推广事宜提出合理的要求，乙方应当按照甲方的要求从事推广事宜。</p>
		<p style="text-indent: 20px">6、甲方有权利随时要求乙方停止本协议约定的推广事宜，乙方在接到甲方通知时，应当立即停止相应的推广。</p>
		<p style="text-indent: 20px">（二）乙方的权利、义务与责任</p>
		<p style="text-indent: 20px">1、乙方承诺按照甲方要求提供申请专属二维码所需的全部资料，并保证提供的资料及相关信息是真实的、完整的、合法的，不存在虚假、编造、隐瞒、违法等情形，否则乙方将承担由此而产生的全部法律责任。</p>
		<p style="text-indent: 20px">2、乙方承诺遵守甲方及世纪创投平台发布的规则和我国的法律法规，依法合规地使用专属二维码，完成本协议项下的推广工作。</p>
		<p style="text-indent: 20px">3、乙方自愿为甲方推广世纪创投平台，并承诺不滥用专属二维码，不对第三人就平台及相关事宜做虚假、夸大的宣传等，否则由此而产生的法律后果由本人承担，与甲方及平台无关。</p>
		<p style="text-indent: 20px">4、乙方有权向甲方就本协议约定的推广事宜提出合理的要求，并依本协议约定收取相应推广费用。</p>
		<p style="text-indent: 20px">5、乙方承诺推广工作不会侵犯任何第三方的权利；若发生侵犯第三方的权利的情形，由乙方承担全部责任，但乙方发现甲方运营的世纪创投平台内容违法时提前终止本合同的权利。</p>
		<p style="text-indent: 20px">
			<strong>四、保密</strong>
		</p>
		<p style="text-indent: 20px">甲、乙双方应当保守在履行本协议过程中获知的对方商业秘密，否则应当承担由此给对方造成的全部损失。</p>
		<p style="text-indent: 20px">
			<strong>五、其他</strong>
		</p>
		<p style="text-indent: 20px">1、因不可抗力或者其他意外事件，或者使得本合同的履行不可能、不必要或者无意义的，任一方均可以解除本合同。</p>
		<p style="text-indent: 20px">2、双方当事人对本合同的订立、解释、履行、效力等发生争议的，应友好协商解决；协商不成的，双方同意向甲方主要营业地的人民法院起诉。</p>
		<p style="text-indent: 20px">3、本协议经甲、乙双方签章并经甲方审核通过后生效。（以下无正文，为签章页）</p>
		<p>甲方：深圳市世纪创投互联网金融服务有限公司</p>
		<p>&nbsp;</p>
		<p>乙方：</p>
		<p style="float: right">年 &nbsp;&nbsp;&nbsp;&nbsp; 月
			&nbsp;&nbsp;&nbsp;&nbsp; 日</p>
	</div>
	<div class="modal-footer" style="text-align: center">
		<!--	<a style="float:left"> <input name="chk" id="chk"  type="checkbox" data-rule="checked;" data-msg-checked="请同意我们的条款" checked >&nbsp;我已经阅读并同意以上条例</a>-->
		<!--<a class="btn btn-primary" id="btn01" aria-hidden="true"  data-toggle="modal" data-target="#myModal1">下一步</a>-->
		<button class="btn btn-primary " data-dismiss="modal"
			aria-hidden="true">确定</button>
	</div>
</div>

<div id="myModal1" class="modal hide fade " tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
	<div class="modal-header" style="text-align: left">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h3 id="myModalLabel">世纪创投推广申请人信息登记</h3>
	</div>
	<form id="applyReferee" class="form"     action="${base}/userMessage/applyReferee.do" method="post" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}" onSubmit="return beforeSubmit(this);">
		<div class="modal-body">
			<table>
				<tr>
					<td>真实姓名：</td>
					<td><input style="width: 167px; margin: 5px;" type="text"
						readonly="true" name="custName"
						value="${ Session.loginInfo.custName}" /></td>
				</tr>
				<tr>
					<td>固话(选填)：</td>
					<td><input style="width: 167px; margin: 5px;" type="text"
						name="phone" data-rule="length[7~11];" /></td>
				</tr>
				<tr>
					<td>出生日期：</td>
					<td><input style="width: 167px; margin: 5px;" type="text"
						name="birthDateStr" class="dfinput" onclick="WdatePicker();"
						style="width:200px" data-rule="出生日期:required;" /></td>
				</tr>
				<tr>
					<td>性别：</td>
					<td><select style="width: 167px; margin: 9px;" name="sex"
						class="select1" size="1" data-rule="性别:required;">
							<option value="">请选择</option>
							<option value="0">女</option>
							<option value="1">男</option>
					</select></td>
				</tr>
				<tr>
					<td>学历：</td>
					<td><select style="width: 167px; margin: 5px;"
						name="education" class="select" size="1" data-rule="学历:required;">
							<option value="">请选择</option>
							<option value="0">本科</option>
							<option value="1">大专</option>
							<option value="2">高中</option>
							<option value="3">初中</option>
							<option value="4">小学</option>
					</select></td>
				</tr>
				<tr>
					<td>婚姻状况：</td>
					<td><select style="width: 167px; margin: 5px;" name="marital"
						class="select" size="1" data-rule="婚姻状况:required;">
							<option value="">请选择</option>
							<option value="0">已婚</option>
							<option value="1">未婚</option>
							<option value="2">离异</option>
							<option value="3">丧偶</option>
					</select></td>
				</tr>
				<tr>
					<td colspan="2">省份：<select style="width: 122px; margin: 5px;"
						id="areaProvince_sel" name="provincial"
						width:52%;  class="select6" size="1" onchange='insertCityInfo();'
						data-rule="省份:required;"><option value="">请选择</option></select>
						城市：<select style="width: 108px; margin: 5px;" id="areaCity_sel"
						name="city" class="select6" size="1"
						onchange='onChangeCityName();insertAreaInfo();'
						data-rule="城市:required;"><option value="">请选择</option></select>
						地区：<select style="width: 108px; margin: 5px;" id="areaArea_sel"
						name="area" class="select6" size="1"
						onchange='onChangeAreaName();' data-rule="地区:required;"><option
								value="">请选择</option></select></td>
				</tr>
				<tr>
					<td>街道地址：</td>
					<td><input type="text" name="address" class="dfinput"
						style="width: 305px; margin: 5px;" data-rule="街道地址:required;" /></td>
				</tr>
			</table>
			</div>
			<div class="modal-footer">

		<a style="float: left"><input name="chk" type="checkbox" checked>&nbsp;<a
			style="float: left" href="#myModal" role="button"
			style="text-decoration:none" data-toggle="modal">我已经阅读并同意使用条款和隐私条款</a></a>
		<!--<a style="float:left"> <input name="chk" id="chk"  type="checkbox"  checked >&nbsp;我已经阅读并同意以上条例</a>-->
		<!-- <a style="float:left"  href="#myModal" role="button"style="text-decoration:none"  data-toggle="modal">点击阅读并同意使用条款和隐私条款</a></a>-->
		<a class="btn btn-primary" id="btn" aria-hidden="true">立即申请</a>
	</div>
	</form>
	
</div>
<script type="text/javascript">

	function beforeSubmit(form) {
		/* if (form.phone.value == '') {
			alert('固话不能为空！');
			form.phone.focus();
			return false;
		} */
		if (form.birthDateStr.value == '') {
			alert('出生日期不能为空！');
			form.birthDateStr.focus();
			return false;
		}
		if (form.sex.value == '') {
			alert('性别不能为空！');
			form.sex.focus();
			return false;
		}
		if (form.education.value == '') {
			alert('学历不能为空！');
			form.education.focus();
			return false;
		}
		if (form.marital.value == '') {
			alert('婚姻状况不能为空！');
			form.marital.focus();
			return false;
		}
		if (form.provincial.value == '') {
			alert('省份不能为空！');
			form.provincial.focus();
			return false;
		}
		if (form.city.value == '') {
			alert('城市不能为空！');
			form.city.focus();
			return false;
		}
		if (form.area.value == '') {
			alert('地区不能为空！');
			form.area.focus();
			return false;
		}
		if (form.address.value == '') {
			alert('街道不能为空！');
			form.address.focus();
			return false;
		}
		return true;
	}
</script>
<script>
	var errMes = '${errMes}';
	var sucMes = '${sucMes}';
	var isFistLoad1 = true;
	var isFistLoad2 = true;
	var isFistLoad3 = true;
	var isFistLoad4 = true;
	if (errMes != '') {
		showDialogNew(true, errMes, 'E', '提醒信息');

	}

	if (sucMes != '') {
		showDialogNew(true, sucMes, 'S', '提醒信息');
	}
</script>
<script type="text/javascript">
	$(function() {
		$('#btn').click(function() {
			if ($('input[name="chk"]').prop("checked")) {
				//   alert("您已同意以上条例");
				//  window.location.href='${base}/userMessage/applyReferee.do';
				$('#applyReferee').submit();
			} else
				alert('请同意推广协议');
		});
	})
	
</script>
<script type="text/javascript">
	//查询省列表
	function insertProInfo() {
		$
				.ajax({
					type : "POST",
					url : "${base}//userFund/ajaxQueryPro.do",
					dataType : "json",
					complete : function(XMLHttpRequest, textStatus) {
						if (XMLHttpRequest.status == 535) {
							//如果超时就处理 ，指定要跳转的页面 
							dialog(
									{
										title : "提醒信息",
										content : "登陆超时，请重新登陆！",
										okvalue : "确定",
										ok : function() {
											window.location
													.replace("${base}/loginAction/login.do");
										},
										onclose : function() {
											window.location
													.replace("${base}/loginAction/login.do");
										}
									}).showModal();
						}
					},
					success : function(data) {
						if (data.status != "1") {
							showDialog(true, data.reMes);
						} else {
							$("#areaProvince_sel").empty();
							$("#areaProvince_sel").prepend(
									"<option value=''>请选择</option>");

							$.each(data.list, function(i, n) {
								$("#areaProvince_sel").append(
										"<option value='" + n.id + "'>"
												+ n.name + "</option>");
							});
						}
					}
				});
	}
	//查询市区列表
	function insertCityInfo() {

		$
				.ajax({
					type : "POST",
					url : "${base}//userFund/ajaxQueryCity.do",
					dataType : "json",
					data : {
						proId : $("#areaProvince_sel").children(
								'option:selected').val()
					},
					complete : function(XMLHttpRequest, textStatus) {
						if (XMLHttpRequest.status == 535) {
							//如果超时就处理 ，指定要跳转的页面 
							dialog(
									{
										title : "提醒信息",
										content : "登陆超时，请重新登陆！",
										okvalue : "确定",
										ok : function() {
											window.location
													.replace("${base}/loginAction/login.do");
										},
										onclose : function() {
											window.location
													.replace("${base}/loginAction/login.do");
										}
									}).showModal();
						}
					},
					success : function(data) {
						if (data.status != "1") {
							showDialog(true, data.reMes);
						} else {
							$("#areaCity_sel").empty();
							$("#areaCity_sel").prepend(
									"<option value=''>请选择</option>");

							$.each(data.list, function(i, n) {
								$("#areaCity_sel").append(
										"<option value='" + n.id + "'>"
												+ n.name + "</option>");
							});
						}

					}

				});
	}
	function onChangeCityName() {
		$("#areaCity").val(
				$("#areaCity_sel").children('option:selected').text());
	}
	//查询地区列表
	function insertAreaInfo() {
		$
				.ajax({
					type : "POST",
					url : "${base}//userFund/ajaxQueryCity.do",
					dataType : "json",
					data : {
						proId : $("#areaCity_sel").children('option:selected')
								.val()
					},
					complete : function(XMLHttpRequest, textStatus) {
						if (XMLHttpRequest.status == 535) {
							//如果超时就处理 ，指定要跳转的页面 
							dialog(
									{
										title : "提醒信息",
										content : "登陆超时，请重新登陆！",
										okvalue : "确定",
										ok : function() {
											window.location
													.replace("${base}/loginAction/login.do");
										},
										onclose : function() {
											window.location
													.replace("${base}/loginAction/login.do");
										}
									}).showModal();
						}
					},
					success : function(data) {
						if (data.status != "1") {
							showDialog(true, data.reMes);
						} else {
							$("#areaArea_sel").empty();
							$("#areaArea_sel").prepend(
									"<option value=''>请选择</option>");

							$.each(data.list, function(i, n) {
								$("#areaArea_sel").append(
										"<option value='" + n.id + "'>"
												+ n.name + "</option>");
							});
						}

					}

				});
	}
	function onChangeAreaName() {
		$("#areaArea").val(
				$("#areaArea_sel").children('option:selected').text());
	}

	//<![CDATA[
	// var itemPerPage = 10;

	// $(document).ready(function(){
	// 	//ajaxQuery1(0);
	// });

	function ajaxQuery1(requestPage, isInit) {
		// 		$.ajax({
		// 				type:"POST",
		// 				url:"ajaxQuerySystemMessage.do",
		// 				dataType:"json",
		// 				data:"requestPage=" + requestPage + "&pageSize=" + itemPerPage,
		// 				complete:function() {
		// 				},
		// 				success:function(data) {
		// 					$('tr[id^=ready]').remove();
		// 			        $.each(data.list, function(i, n){
		// // 			        	var row = $("<tr></tr>");
		// // 			        	row.append($("<td style=\"width:30%;text-align: center;\">" + n.sendusername+"</td>"));
		// // 			        	row.append($("<td style=\"width:20%;text-align: center;\">" + n.titletxt + "</td>"));
		// // 			        	row.append($("<td style=\"width:25%;text-align: center;\">" + n.sendtime + "</td>"));
		// // 			        	tdStr = "<td style=\"width:25%;text-align: center;\">" 
		// // 			        		  + "<a href=\"javascript:void(0);\" onclick=\"showContentDiv('"+n.sendusername+"','" + n.titletxt + "','" + n.contenttxt + "','" + n.msgid + "','" + n.readstatus + "')\">查看</a>" 
		// // 			        		  + "<a onclick=\"deleteMsg('" + n.msgid + "','1')\" href=\"javascript:void(0);\">删除</a>"
		// // 			        		  + "</td>"
		// // 			        	row.append($(tdStr));
		// // 			        	row.appendTo("#datas01");
		// // 	                });
		// 				},
		// 				error:function(text) {
		// 					// alert('请求后台出错.');
		// 				} 
		// 			});
	}
	//]]>
</script>
</body>
</html>