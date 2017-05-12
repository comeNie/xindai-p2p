<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户中心-首页-sjct888.com</title>

<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport"content="width=1349;"/> 
<#include "/common/resource.ftl">
</head>
<body>
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
    	<div class="user_con">
        	<div class="user_h">
            	<div class="left">
                	
                    <div class="info" style="margin-top:10px;">
                     <!-- 您是VIP -->
                      <#if userIndexVO.isVip == "1">
                        <a style="width: 31px;height: 30px;background: url(${base}/images/vip2.png) no-repeat;float: left;display: inline;" title="您是VIP" href="#"></a>
                       <!-- 您不是VIP -->
                      <#else>
                       <a style="width: 31px;height: 30px;background: url(${base}/images/vip1.png) no-repeat;float: left;display: inline;" title="您还不是VIP" href="#"></a>
                      </#if>
                        <p style="margin-left:30%;margin-top:3%">安全等级：<a href="${base}/userSecurity/index.do?myVid=userinfohead1"><em>${secLevel}</em></a>
                        <div class="ProgressBar"style="width:168px">
                          <span>${progress}%</span>
                          <div style="width:${progress}% ;"><span>${progress}%</span></div>
                      	</div>
                      </p><!--需要弄一个进度条来动态的显示-->
                       <p>
                           <a href="${base}/userSecurity/index.do?myVid=userinfohead1"><#if isIdCard == "false"><img title="未进行实名认证" src="${base}/platform/img/usericon-01-off.png"/><#else><img title="实名认证,已被认证" src="${base}/platform/img/usericon-01-on.png"/></#if></a>
                       &nbsp;&nbsp;&nbsp;&nbsp;	   <a href="${base}/userSecurity/index.do?myVid=userinfohead1"><#if isMobile == "false"><img title="未进行手机绑定" src="${base}/platform/img/usericon-02-off.png"/><#else><img title="手机已绑定" src="${base}/platform/img/usericon-02-on.png"/></#if></a>
<!--                        	   <a href="${base}/userSecurity/index.do?myVid=userinfohead1"><#if isEmail == "false"><img title="未进行邮箱绑定" src="${base}/platform/img/usericon-03-off.png"/><#else><img title="邮箱已绑定" src="${base}/platform/img/usericon-03-on.png"/></#if></a> -->
                     &nbsp;&nbsp;&nbsp;&nbsp;  	   <a href="${base}/userSecurity/index.do?myVid=userinfohead1"><#if isCashPassword == "false"><img title="提现密码未设置" src="${base}/platform/img/usericon-04-off.png"/><#else><img title="提现密码已设置" src="${base}/platform/img/usericon-04-on.png"/></#if></a>
                        </p>
<!--                         <p style="margin-top:18px;"><font style="color:#000000;font-size:20px;font-family:Times">我的推荐码：</font><font style="color:#ed0000;font-size:20px;">${userIndexVO.referee_code}</font></p> -->
                    </div>
                </div>
                <div class="right">   <!--我的世纪创投币获取不到-->
                	<div class="user_con_info_hd">
                        
                        <div class="cell user_con_info2">账户余额</div>
                        <div class="cell user_con_info4">可用余额</div>
                        <div class="cell user_con_info3">冻结余额</div>
                    </div>

                    <div class="user_con_info_bd">
                       
                        <div class="cell user_con_info2"><em>${userIndexVO.accountBalAmt}</em>元</div>
                        <div class="cell user_con_info4"><em>${userIndexVO.usableBalAmt}</em>元</div>
                        <div class="cell user_con_info3"><em>${userIndexVO.freezeBalAmt}</em>元</div>
                    </div>

					<div class="info-btn">
						<div><a href="${base}/userFund/myRecharge.do"><button type="submit" class="button_ui" id="registsubmit">充值</button></a></div>
						<div><a href="${base}/userFund/myDrawal.do"><button type="submit" class="button_ui_i" id="registsubmit">提现</button></a></div>
					</div>

                </div>
            </div>	
        </div>
        <div class="clear"></div>
        <div class="user_panel">
        	<#include "/common/left.ftl">
            <div class="right">
             <table class="table_W" style="background-color:#f7f7f7;font-size:18px;">
                    <thead>
                      <tr>
                        <th colspan="2" width="25%">账户净资产</th>
                        <th colspan="2" width="15%">投资资产</th>
                        <th colspan="2" width="15%">借款负债</th>
                        <th colspan="2" width="14%">账户余额</th>
                        <th width="20%">冻结余额</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td width="14%">${userIndexVO.accoutPureAmt}元</td>
                        <td width="15%">=</td>
                        <td  width="14%">${userIndexVO.unretrieveAmt}元</td>
                        <td  width="15%">+</td>
                        <td  width="14%">-${userIndexVO.loanBalAmt}元</td>
                        <td  width="15%">+</td>
                        <td  width="14%">${userIndexVO.accountBalAmt}元</td>
                        <td  width="15%">+</td>
                         <td  width="14%">${userIndexVO.freezeBalAmt}元</td>
                      </tr>
                    </tbody>
              </table>
				
				<div class="titlebox">
				  <div class="titlediv">投资账户</div><a href="${base}/userFinancial/myCreditor.do?myVid=userinfohead2">投资明细</a>
				 </div>
               <table class="tableT">
                <thead>
                  <tr>
                    <th width="25%">投资产品</th>
                    <th width="25%">投资笔数</th>
                    <th width="25%">累计投资</th>
                    <th width="25%">累计收益</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td width="25%">债权投资</td>
                    <td width="25%">${fundAccountVo.buyCount}</td>
                    <td width="25%">¥${fundAccountVo.crAmt}</td>
                    <td width="25%"><em>¥${fundAccountVo.totalProfitAmt}</em></td>
                  </tr>
                </tbody>
              </table>
			  
			  <div class="titlebox">
			   <div class="titlediv">借款账户</div><a href="${base}/userLoan/myLoan.do?myVid=userinfohead4">借款明细</a>
			   </div>
               <table class="tableT">
                <thead>
                  <tr>
<!--                     <th width="20%">信用额度</th> -->
<!--                     <th width="20%">剩余额度</th> -->
                    <th width="20%">申请借款笔数</th>
                    <th width="20%">成功借款笔数</th>
					 <th width="20%">还清笔数</th>
					  <th width="20%">借款总金额</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
<!--                     <td width="20%">¥${creditReportDetailVO.creditAmt}</td> -->
<!--                     <td width="20%">¥${creditReportDetailVO.remainAmt}</td> -->
                    <td width="20%">${creditReportDetailVO.applyLoanNum}笔</td>
                    <td width="20%">${creditReportDetailVO.approveNum}笔</td>
					<td width="20%">${creditReportDetailVO.payOffNum}笔</td>
					<td>¥${creditReportDetailVO.loanTolAmt}</td>
                  </tr>
                </tbody>
              </table>
			  <div style="height:20px;width:100%;"></div>
			  <table class="tableT">
                <thead>
                  <tr>
                   
                    <th width="20%">借款余额</th>
                    <th width="20%">逾期总额</th>
                    <th width="20%">累计逾期次数</th>
                    <th width="20%">其中:严重逾期</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    
                    <td>¥${creditReportDetailVO.loanBal}</td>
                    <td>¥${creditReportDetailVO.overdueAmt}</td>
                    <td>${creditReportDetailVO.overdueNum}次</td>
                    <td>${creditReportDetailVO.serOverdueNum}次</td>
                  </tr>
                </tbody>
              </table>
             <#-- <div class="adver"><a href="javascript:void(0);"><img src="${base}/platform/img/tempimg003.png"/></a></div>-->
            </div>	
        </div>
        <div class="clear"></div>        
    </div>
</div>	

</body>
</html>