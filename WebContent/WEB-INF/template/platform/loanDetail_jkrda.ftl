<head>
<script src="${base}/js/prefixfree.min.js"></script>
<style>
.gallery {
  width:100%;
  margin: 30px auto;
  padding: 5px;
 /*background: #333;*/
}

.gallery > div {
  position: relative;
  float: left;
  padding: 8px;
}

.gallery > div > img {
  width: 250px;
  transition: .1s transform;
  transform: translateZ(0);
  /* hack */
}

.gallery > div:hover {
  z-index: 1;
}

.gallery > div:hover > img {
  transform: scale(2, 2);
  transition: .3s transform;
}

.cf:before, .cf:after {
  display: table;
  content: "";
  line-height: 0;
}

.cf:after {
  clear: both;
}
</style>
</head>
<#--<div id="loanDetailJkrda" class="bulk_user_content1">
   <div class="bulk_user_box">
	   <h2>用户信息</h2>
	   <div class="row">
		  <div class="item">
		     <span class="title">用户名</span><span class="value"><a href="javascript:void(0);">${loanDetailVO.userCode}</a></span>
		  </div>
		  <div class="item">
		    <span class="title">年龄</span><span class="value">${customerDetailVO.age}</span>
		  </div>
		  
		  <div class="item">
		     <span class="title">学历</span><span class="value"><@sysCodeVal codeType='educationCd' codeValue='${customerDetailVO.educationCd}' /></span>
		  </div>
	   </div>
	   
	   <div class="row">
	      <div class="item">
		   <span class="title">婚姻状况</span><span class="value"><@sysCodeVal codeType='maritalStatusCd' codeValue='${customerDetailVO.maritalStatusCd}' /></span>
		  </div>
		  <div class="item">
		   <span class="title">公司名称</span><span class="value">${customerDetailVO.jobCompanyName}</span>
		  </div>
		  <div class="item">
		    <span class="title">公司性质</span><span class="value"><@sysCodeVal codeType='companyTypeCd' codeValue='${customerDetailVO.companyIndustryCd}'/></span>
		  </div>
	   </div>
	   <div class="row">
	      <div class="item">
		  <span class="title">职位</span><span class="value"><@sysCodeVal codeType='jobPosition' codeValue='${customerDetailVO.jobPosition}'/></span>
		  </div>
		  <div class="item">
		   <span class="title">所处部门</span><span class="value">${jobDeptName}</span>
		  </div>
		  <div class="item">
		   <span class="title">月收入<span><span class="value"><@sysCodeVal codeType='jobIncomeCd' codeValue='${customerDetailVO.jobIncomeCd}' /></span>
		  </div>
	   </div>
   </div>
   <div class="bulk_user_box">
	   <h2>信用档案</h2>
	   <div class="row">
		  <div class="item">
		   <span class="title">信用等级</span><span class="value">${loanDetailVO.creditRate}</span>
		  </div>
		  <div class="item">
		   <span class="title">信用额度</span><span class="value">${creditReportDetailVO.creditAmt}(元)</span>
		  </div>
		  <div class="item">
		   <span class="title">剩余额度</span><span class="value">${creditReportDetailVO.remainAmt}(元)</span>
		  </div>
	   </div>
	   <div class="row">		  
		   <div class="item">
		   <span class="title">借款总额</span><span class="value">${creditReportDetailVO.loanTolAmt}(元)</span>
		  </div>
		  <div class="item">
		   <span class="title">借款余额</span><span class="value">${creditReportDetailVO.loanBal}(元)</span>
		  </div>
		  <div class="item">
		   <span class="title">逾期金额</span><span class="value">${creditReportDetailVO.overdueAmt}(元)</span>
		  </div>
		   
	   </div>
	   <div class="row">
		  <div class="item">
		    <span class="title">申请笔数</span><span class="value">${creditReportDetailVO.applyLoanNum}</span>
		  </div>
		  
		  <div class="item">
		    <span class="title">成功笔数</span><span class="value">${creditReportDetailVO.approveNum}</span>
		  </div>
		  
		  <div class="item">
		   <span class="title">还清笔数</span><span class="value">${creditReportDetailVO.payOffNum}</span>
		  </div>
	   </div>
	   <div class="row">
		  <div class="item">
		    <span class="title">逾期次数</span><span class="value">${creditReportDetailVO.overdueNum}</span>
		  </div>
		  <div class="item">
		   <span class="title">严重逾期次数</span><span class="value">${creditReportDetailVO.serOverdueNum}</span>
		  </div>
		  <div class="item">
		       &nbsp;
		  </div>
	   </div>
   </div>
   <div class="clear"></div>
   <div class="bulk_user_box">
       <h2>审核状态</h2>
	   <table class="table">
		<thead>
		  <tr>
			<th>认证项目</th>
			<th>认证状态</th>
			<th>通过日期</th>
		  </tr>
		</thead>
		<tbody>
		  <#list (authDetailVOList)! as list>
		  <tr>
			<td><@sysCodeVal codeType='authItemCd' codeValue='${list.authItemCd}' /></td>
			<td><a href="javascript:void(0);"><img src="${base}/platform/img/big_fish.gif" align="absmiddle"/></a></td>
			<td>${list.sysCreateTime}</td>
		  </tr>
		  </#list>
		</tbody>
	   </table>
   </div> 
   <div class="bulk_user_box">
    <h2>借款描述</h2>
	<p>
	<#if loanDetailVO.remark??>${loanDetailVO.remark}<#else></#if>
	</p>
   </div>
</div>-->



<!--借款人档案 开始-->
			<div class="new-info-box-two" id="load-info-one">
				<div class="info-title-one"><img src="${base}/platform/img/backgroundimg01.png" /></div>
				<div class="info-title-two" >
					<table class="new-info-table">
					<tbody>
					  <tr>
					  <#--
						<td class="info-box-table-td" >信用等级</td>
						<td ><div class="info-box-lev" ><span class="value"><img src="${base}/platform/img/xydj${loanDetailVO.creditRate}.png"/></span></div></td>
						-->
						<td class="info-box-table-td" >借款余额</td>  
						<td >${creditReportDetailVO.loanBal?string(',##0.00')}(元)</td>
						<td class="info-box-table-td" >还清笔数</td>
						<td>${creditReportDetailVO.payOffNum}</td>
					  </tr>
					  <tr>
					  <#--
						<td>信用额度</td>
						<td>${creditReportDetailVO.creditAmt?string(',##0.00')}(元)</td>
						-->
						<td>逾期金额</td>
						<td>${creditReportDetailVO.overdueAmt?string(',##0.00')}(元)</td>
						<td>逾期次数</td>
						<td><span class="rednumber">${creditReportDetailVO.overdueNum}</span></td>
					  </tr>
					  <tr>
					   <#--
						<td>剩余额度</td>
						<td>${creditReportDetailVO.remainAmt?string(',##0.00')}(元)</td>
						-->
						<td>申请笔数</td>
						<td>${creditReportDetailVO.applyLoanNum}</td>
						<td>严重逾期</td>
						<td><span class="rednumber">${creditReportDetailVO.serOverdueNum}</span></td>
					  </tr>
					  <tr>
						<td>借款总额</td>
						<td>${creditReportDetailVO.loanTolAmt?string(',##0.00')}(元)</td>
						<td>成功笔数</td>
						<td>${creditReportDetailVO.approveNum}</td>
						<td></td>
						<td></td>
					  </tr>
					</tbody>
				  </table>
 						<!--  证件档案 -->
 						
				<div  style="margin-left: -13.3%;"><img src="${base}/images/zhengjianbackground.png" /></div>
				<#if collateralInfoVO.collateralAtt1??>
						<div class="gallery cf">
						 			<#if collateralInfoVO.collateralAtt1??>
								  <div>
								    <img style="width:220px;height:290px" src="${base}/${collateralInfoVO.collateralAtt1}" />
								  </div>
								  <#else></#if>
								  <#if collateralInfoVO.collateralAtt2??>
								  <div>
								    <img style="width:220px;height:290px;margin-left: 20px;" src="${base}/${collateralInfoVO.collateralAtt2}" />
								  </div>
								   <#else></#if>
								  <#if collateralInfoVO.collateralAtt3??>
								  <div>
								     <img style="width:220px;height:290px;margin-left: 20px;" src="${base}/${collateralInfoVO.collateralAtt3}" />
								  </div>
								   <#else></#if>
						</div>
 				<#else></#if>
				</div>
				<#if collateralInfoVO.collateralAtt1??>
				<div class="info-con" style="margin-top:35%" >
				
				<img src="${base}/platform/img/backgroundimg02.png" /></div>
				<div class="info-con-tb-box">
					<table class="new-info-table2">
					<thead>
					  <tr class="table-th-tr">
						<th>认证项目</th>
						<!--<th>认证详情</th>-->
						<th>认证状态</th>
						<th>通过日期</th>
					  </tr>
					</thead>
					<tbody>
					 <#list (authDetailVOList)! as list>
		              <tr>
			           <td><@sysCodeVal codeType='authItemCd' codeValue='${list.authItemCd}' /></td>
			           <td><a href="javascript:void(0);"><img src="${base}/platform/img/big_fish.gif" align="absmiddle"/></a></td>
			           <td>${list.sysCreateTime}</td>
		             </tr>
		            </#list>
					</tbody>
				  </table>
				</div>
 				<#else>
 					<div class="info-con" style="margin-top:7%" >
				
				<img src="${base}/platform/img/backgroundimg02.png" /></div>
				<div class="info-con-tb-box">
					<table class="new-info-table2">
					<thead>
					  <tr class="table-th-tr">
						<th>认证项目</th>
						<!--<th>认证详情</th>-->
						<th>认证状态</th>
						<th>通过日期</th>
					  </tr>
					</thead>
					<tbody>
					 <#list (authDetailVOList)! as list>
		              <tr>
			           <td><@sysCodeVal codeType='authItemCd' codeValue='${list.authItemCd}' /></td>
			           <td><a href="javascript:void(0);"><img src="${base}/platform/img/big_fish.gif" align="absmiddle"/></a></td>
			           <td>${list.sysCreateTime}</td>
		             </tr>
		            </#list>
					</tbody>
				  </table>
				</div>
 				</#if>
				<div class="info-con"><img  src="${base}/platform/img/backgroundimg03.png" /></div>
				<div class="info-con-text-box">
					<p>
	                   <#if loanDetailVO.remark??>${loanDetailVO.remark}<#else></#if>
	                </p>
				</div>
 
			</div>
			<!--借款人档案 结束-->