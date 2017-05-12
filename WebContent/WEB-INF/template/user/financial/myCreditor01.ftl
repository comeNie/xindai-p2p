<head>
<style>
.user_panel .right p {
text-align: left;
margin: 0 auto;
}
</style>
</head>

<div class="myCreditor" id="myCreditor_tab1">
	<table class="table table-hover">
		<thead>
			<tr>
				<th style="width:15%;text-align: center;">借款标题</th>
				<!--<th>标的总额</th> -->
				<!--<th>投资金额</th> -->
				<th style="width:8%;text-align: center;">年利率</th>
				<th style="width:18%;text-align: center;">剩余债权</th>
				<th style="width:12%;text-align: center;">收益金额</th>
				<th style="width:12%;text-align: center;">剩余期数/期数</th>
				<!--<th>状态</th>-->
				<th style="width:15%;text-align: center;">收款日期</th>
				<th style="width:15%;text-align: center;"></th>
			</tr>
		</thead>
		<tbody id="datas1">
		</tbody>
	</table>
	<div id="Pagination1" class="pagination pagination-right"></div>
</div>

 <div id="myModal" style="text-align:left" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">投资协议</h3>
  </div>
  <div class="modal-body">
  <h1  style="text-align:center;font-size:18px">投资协议</h1> 
  <p style="float:right">合同编号:<br>签约日期:</p>
  <p> &nbsp;&nbsp; </p>
 
  <p style="text-indent:20px">协议三方定义:</p>
  <p style="text-indent:20px">贷出者:_______ 以下称“贷出方”；</p>
  <p style="text-indent:20px">借入者:__________以下称“借入方”；</p>
  <p style="text-indent:20px">管理方: 深圳市世纪创投互联网金融服务有限公司 ，以下称“管理方”。</p>
  <p style="text-indent:20px">郑重承诺:</p>
  <p style="text-indent:20px">（1）管理方是世纪创投，为借贷双方提供P2P个人借贷信息、信用咨询等交易信息管理服务。</p>
  <p style="text-indent:20px">（2）借入方应当将真实有效的信息提供给管理方，并承诺杜绝发生各种恶意逃避还款义务的情况，包括但不限于借用他人身份信息、双重身份证件等。</p>
  <p style="text-indent:20px">（3）贷出方承诺对本协议涉及的借款具有完全的支配能力，是其自有闲散资金，为其合法所得；并承诺其提供给管理方的信息是真实有效的，承担一切因虚假信息而导致债权不能实现的法律后果。</p>
  <p style="text-indent:20px">（4）贷出方和借入方同意成立借贷关系，并由管理方提供平台化信息对接服务。各方经协商一致，特在管理方所在地签订如下协议，共同遵照履行:</p>
  <p style="text-indent:20px">贷出者:</p>
  <table border="1">
  	<tr>
  		<td>出借人名称</td>
  		<td>借出金额(人民币)</td>
  		<td>借款期限</td>
  		<td>年利率</td>
  		<td>借款开始日期</td>
  		<td>借款到期日期</td>
  		<td>&nbsp;&nbsp;</td>
  		<td>总还款本息(人民币)</td>
  	</tr>
  	<tr>
  		<td></td>
  		<td>&nbsp;&nbsp;</td>
  		<td>&nbsp;&nbsp;</td>
  		<td>&nbsp;&nbsp;</td>
  		<td>&nbsp;&nbsp;</td>
  		<td>&nbsp;&nbsp;</td>
  		<td>&nbsp;&nbsp;</td>
  		<td>&nbsp;&nbsp;</td>
  	</tr>
  </table >
  <p style="text-indent:20px">三方约定:</p>
  <p style="text-indent:20px">由管理方负责将贷出方的款项划转至借款方开立的账户。</p>
  <p style="text-indent:20px">第一条 借款基本信息</p>
  <table border="1" width="500px">
  	<tr>
  		<td>借款详细用途:</td>
  		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  	</tr>
  	<tr>
  		<td>借款本金数额:</td>
  		<td></td>
  	</tr>
  	<tr>
  		<td>应偿还本息数额:</td>
  		<td></td>
  	</tr>
  	<tr>
  		<td>还款分期月数:</td>
  		<td></td>
  	</tr>
  	<tr>
  		<td>借款利率:</td>
  		<td></td>
  	</tr>
  	<tr>
  		<td>还款方式:</td>
  		<td></td>
  	</tr>
  	<tr>
  		<td>借款描述:</td>
  		<td></td>
  	</tr>
  	<tr>
  		<td>还款日:</td>
  		<td></td>
  	</tr>
  	<tr>
  		<td>还款起止日期:</td>
  		<td></td>
  	</tr>
  </table>
  <p style="text-indent:20px">分期还款列表</p>
  <table border="1" width="500px" height="100px">
  	<thead>
  	<tr>
  		<td>期数</td>
  		<td>年利率</td>
  		<td>应还时间</td>
  		<td>应还本息</td>
  		<td>还款本金</td>
  		<td>还款利息</td>
  	</tr>
  	</thead>
		<tbody id="datas2"></tbody>
  </table>
  <p style="text-indent:20px">第二条 各方权利和义务</p>
  <p style="text-indent:20px">贷出方的权利和义务 :</p>
  <p style="text-indent:20px">（1）贷出方同意管理方在本合同第一条项下借款审核完毕后将借款划拨给借入方。</p>
  <p style="text-indent:20px">（2）贷出方享有其所出借款项所带来的利息收益。</p>
  <p style="text-indent:20px">（3）如借入方违约，借入方同意管理方将已获得的借入方的信息告知贷出方。 </p>
  <p style="text-indent:20px">（4）贷出方可以根据自己的意愿进行本协议下其对借入方债权的转让。在贷出方的债权转让成功后，借入方需对债权受让人继续履行本协议下各项义务，债权转让通知以电子邮件或手机短信方式送达发布邮件或者短信的第二天视为送达，借入方不得拒绝履行还款义务。 （详细阅读“债权转让及受让协议”）</p>
  <p style="text-indent:20px">（5）贷出方应主动缴纳由利息所得带来的可能的税费。</p>
  <p style="text-indent:20px">（6）如借入方还款不足约定本金、利息、逾期罚息、违约金的，各方同意各自按照其借出款项比例收取还款。</p>
  <p style="text-indent:20px">（7）贷出方可授权管理方代为追缴借入方逾期未还款项。</p>
  <p style="text-indent:20px">借入方权利和义务 。</p>
  <p style="text-indent:20px">（1）借入方必须按期足额向贷出方支付本金和利息。 </p>
  <p style="text-indent:20px">（2）借入方必须足额向管理方支付平台成交服务费用。</p>
  <p style="text-indent:20px">（3）借入方承诺所借款项不用于任何违法用途。 </p>
  <p style="text-indent:20px">（4）借入方应确保其提供的信息和联系方式的真实性，不得提供虚假信息或隐瞒重要事实。 </p>
  <p style="text-indent:20px">（5）借入方有权了解其在管理方的信用评审进度及结果。 </p>
  <p style="text-indent:20px">（6）借入方不得将本协议项下的任何权利义务转让给任何其他方。</p>
  <p style="text-indent:20px">（7）由于转账有延时性，借入方向平台还款充值时次日才可到账，故借入方需在实际还款到期日前一天将钱转入世纪创投平台在第三方机构开立的账户中，方便贷出方及时提现。</p>
  <p style="text-indent:20px">管理方的权利和义务:</p>
  <p style="text-indent:20px">（1）贷出方和借入方同意管理方有权代贷出方每期收取贷出方出借款项所对应的借入方每期偿还的本息，代收后按照贷出方的要求进行处置。</p>
  <p style="text-indent:20px">（2）贷出方同意委托管理方在合同第一条项下借款审核完毕后将此笔借款直接划付至借入方账户。</p>
  <p style="text-indent:20px">（3）贷出方和借入方同意管理方有权代贷出方在有必要时对借入方进行催收工作，包括但不限于电话通知、发律师函、登门催讨、对借入方提起诉讼等。	</p>
  <p style="text-indent:20px">（4）管理方有权向借入方收取双方约定的平台成交服务费，并在有必要时对借入方进行催收工作，包括但不限于电话通知、发律师函、登门催讨、对借入方提起诉讼等。</p>
  <p style="text-indent:20px">（5）管理方作为贷出方和借入方双方的居间人，促成双方交易，贷出方和借入方双方行为所产生的法律后果由相应各方承担。如因借入方或贷出方或其他方（包括但不限于技术问题）造成的延误或错误，管理方不承担任何责任。 </p>
  <p style="text-indent:20px">（6）管理方应对贷出方和借入方的信息及本协议内容保密；如任何一方违约，或因相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构等），管理方有权披露。 </p>
  <p style="text-indent:20px">第三条 平台成交服务费</p>
  <p style="text-indent:20px">1、在本协议中“平台成交服务费”是指因管理方为借入方提供信用咨询、评估、还款提醒、账户管理、还款特殊情况沟通等系列信用相关服务而由借入方支付给管理方的报酬。</p>
  <p style="text-indent:20px">2、借入方和管理方协商一致可以调整平台成交服务费，无需经过贷出方同意。</p>
  <p style="text-indent:20px">第四条 违约责任 </p>
  <p style="text-indent:20px">1、合同各方均应严格履行合同义务，非经各方协商一致或依照本协议约定，任何一方不得解除本协议。</p>
  <p style="text-indent:20px">2、任何一方违约，违约方应承担因违约使得其他各方产生的费用和损失，包括但不限于调查、诉讼费、律师费等，应由违约方承担。如违约方为借入方的，贷出方有权立即解除本协议，并要求借入方立即偿还未偿还的本金、利息、逾期罚息、违约金等。如本协议提前解除时，借入方在网站的账户里有任何余款，管理方有权按照本协议第四条第3项的清偿顺序将借入方的余款用于清偿，并要求借入方支付因此产生的相关费用。</p>
  <p style="text-indent:20px">3、借入方的每期还款均应按照如下顺序清偿: </p>
  <p style="text-indent:20px">（1）逾期罚息以及违约金；</p>
  <p style="text-indent:20px">（2）平台逾期催收费； </p>
  <p style="text-indent:20px">（3）拖欠的利息； </p>
  <p style="text-indent:20px">（4）拖欠的本金； </p>
  <p style="text-indent:20px">（5）正常的利息； </p>
  <p style="text-indent:20px">（6）正常的本金； </p>
  <p style="text-indent:20px">（7）根据本协议产生的其他全部费用； </p>
  <p style="text-indent:20px">4、借入方应严格履行还款义务，如借入方违约，则应按照下述条款向贷出方支付违约金。
违约金= 未付本息 * 1‰ * 逾期天数</p>
  <p style="text-indent:20px">5、借入方应严格履行还款义务，如借入方逾期还款超过3天，管理方将收取平台逾期催收费作为网站电话提醒和催收服务的费用。平台逾期催收费最低50元，最高为借款本金的1%。 </p>
  <p style="text-indent:20px">6、如果借入方逾期支付任何一期还款超过60天，本协议项下的全部借款本息提前到期，借入方应立即清偿本协议下尚未偿付的全部本金、利息、逾期罚息、违约金及根据本协议产生的其他全部费用。  </p>
  <p style="text-indent:20px">7、如果借入方逾期支付任何一期还款超过60天，平台方有权将借入方的“逾期记录”记入平台方逾期黑名单系统，平台方不承担任何法律责任。</p>
  <p style="text-indent:20px">8、如果借入方逾期支付任何一期还款超过60天，平台方有权将借入方违约失信的相关信息在媒体披露，平台方不承担任何责任。 </p>
  <p style="text-indent:20px">9、在借入方还清全部本金、利息、逾期罚息、违约金之前，逾期罚息、违约金的计算不停止。 </p>
  <p style="text-indent:20px">第五条 提前还款 </p>
  <p style="text-indent:20px">1、借入方可在借款期间任何时候提前偿还剩余借款。 </p>
  <p style="text-indent:20px">2、提前清偿全部剩余借款 。乙方提前清偿全部剩余借款时，应向甲方支付剩余全部本金和利息。 </p>
  <p style="text-indent:20px">3、提前偿还部分借款 </p>
  <p style="text-indent:20px">（1）借入方提前偿还部分借款，对贷款的总期限及月偿还利息数额不产生影响。 </p>
  <p style="text-indent:20px">（2）借入方提前偿还部分借款，仍应向贷出方支付全部借款利息。  </p>
  <p style="text-indent:20px">4、任何形式的提前还款不影响平台方向借入方收取在本协议第三条中说明的借款平台成交服务费。</p>
  <p style="text-indent:20px">第六条 法律及争议解决  </p>
  <p style="text-indent:20px">本协议的签订、履行、终止、解释均适用中华人民共和国法律，并由深圳人民法院管辖。</p>
  <p style="text-indent:20px">第七条 附则  </p>
  <p style="text-indent:20px">1、本协议采用电子文本形式制成，各方均认可该形式的法律效力。  </p>
  <p style="text-indent:20px">2、本协议本合同第一条项下借款提交审核之日生效。</p>
  <p style="text-indent:20px">3、本协议签订之日起至借款全部清偿之日止，借入方或贷出方有义务在下列信息变更三日内提供更新后的信息给平台方:本人、本人的家庭联系人及紧急联系人、工作单位、居住地址、住所电话、手机号码、电子邮箱、银行账户的变更。若因任何一方不及时提供上述变更信息而带来的损失或额外费用应由该方承担。</p>
  <p style="text-indent:20px">4、如果本协议中的任何一条或多条因违反法律法规而被认定无效，该无效条款并不影响本协议其他条款的效力。</p>
  <p style="text-indent:20px">特别提示：鉴于平台方是为借贷双方提供对接服务，促成双方建立借贷关系的一方，当平台方进行风控审核与财务审核时，发现风控审核与财务审核不符的，为了维护借贷双方的权益，保护借贷双方的资金安全，平台方有权取消该次交易，借贷双方扣缴的费用按照流标处理返还其各自账户，因借贷交易生成的列表予以删除。但借贷双方被第三方收取的费用（包含但不限于身份认证、学历认证等费用）不予返还。</p>
  <p style="text-indent:20px">（以下无正文）</p>
  <p style="text-indent:20px">贷出者:${aaa} }；</p>
  <p style="text-indent:20px">借入者:___________;</p>
  <p style="text-indent:20px">通信地址： 深圳市前海深港合作区前湾一路1号</p>
  <p style="text-indent:20px">管理方： 深圳市世纪创投互联网金融服务有限公司</p>

  
  </div>
  <div class="modal-footer">
    <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true"data-target="#myModal">确认</button>
    
  </div>
</div>
 
<script type="text/javascript">
//<![CDATA[
	// 回收中的债权
	function ajaxQuery1(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryCreditorRecovering.do", 
				dataType:"json",
				data:"requestPage=" + requestPage + "&pageSize=" + itemPerPage,
				complete:function(XMLHttpRequest,textStatus) {
					if(XMLHttpRequest.status == 535){  
					      //如果超时就处理 ，指定要跳转的页面 
					      dialog({
								title:"提醒信息",
								content:"登陆超时，请重新登陆！",
								okvalue:"确定",
								ok:function(){
								 	window.location.replace("${base}/loginAction/login.do");
								 },
								 onclose:function(){
								  window.location.replace("${base}/loginAction/login.do");
								 }
							}).showModal(); 
					}
				},
				success:function(data) {
					$('tr[id^=ready]').remove();
			        $.each(data.list, function(i, n){
			        	var row = $("<tr></tr>");	
			        	row.attr("id","ready"+i);//改变绑定好数据的行的id
			        	row.append($("<td style=\"width:15%;text-align: center;\" ><a target='_blank' href=\"${base}/loan/detail.do?loanApproveId="+n.loan_approve_id +"\" title=\""+n.loanName+"\">"+n.loan_name+"</a></td>"));
			        	//row.append($("<td>" + n.loan_amt + "</td>"));
			        	//row.append($("<td>" + n.cr_amt + "</td>"));
			        	
			        	row.append($("<td style=\"width:8%;text-align: center;\" >" + n.rate +"%"+ "</td>"));
			        	row.append($("<td style=\"width:18%;text-align: center;\" >"+fmoney(n.unretrieve_amt,2)+"</td>"));
			        	row.append($("<td style=\"width:12%;text-align: center;\" >" + fmoney(n.tot_profit_amt,2) + "</td>"));
			        	row.append($("<td style=\"width:5%;text-align: center;\" >" + n.surplus_period + "/"+n.total_period+"</td>"));
			        	//row.append($("<td>" + n.cr_status_cd + "</td>"));
			        	row.append($("<td style=\"width:15%;text-align: center;\" >" + n.next_gather_date + "</td>"));
			        	row.append($("<td style=\"width:15%;\" ><input type=\"submit\" onclick=\"window.open('${base}/userFinancial/creditorProtocol.do?loanApproveId="+n.loan_approve_id +"')\" class=\"btn btn-primary\" data-toggle=\"modal\"style=\"margin-left:-5px;font-size:14px;text-align: center;border-radius:10px;background:#ffb23f\" value=\"查看协议\" ></input></td>"));
			        	//row.append($("<td><a href=\"${base}/userFinancial/creditorTranDetail.do?creditorId=" + n.creditor_id + "\">债权转让</a></td>"));
			        	row.appendTo("#datas1");
	                });
	                // 分页
	                if(isInit){
	                	pageQuery1(data.totalCount);
	                }
					
	                $("#loading").hide();
	                $('tr[id^=ready]').show();
			         
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}
	
	// 分页查询
	function pageQuery1(dataSize){
	    var optInit = $.extend({
		items_per_page:itemPerPage,
		num_display_entries:10,
		current_page:0,
		num_edge_entries:2,
		prev_text:"上一页",
		next_text:"下一页",
		callback:pageselectCallback1
		});
	    $("#Pagination1").pagination(dataSize, optInit);
	}
	
	// 分页组件回调函数
	function pageselectCallback1(page_index, j){ 
        ajaxQuery1(page_index+1,false);
        if(!isFistLoad1){
			ajaxQuery1(page_index+1,false);
		}
		isFistLoad1=false;
        return false;
    }
 //]]>
</script>
