<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户中心-首页-sjct888.com</title>

<meta name="description" content="">
<meta name="author" content="">

<#include "/common/resource.ftl">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
		<meta name="mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${base}/css/framework7.min.css">
		<link rel="stylesheet" href="${base}/css/newRega205.css">
		<link href="${base}/css/loginAPP.css" rel="stylesheet" type="text/css">
		<link href="${base}/css/styleApp.css" rel="stylesheet" type="text/css">
</head>
	<body class="newReg">
<div class="logo">
	<img src="${base}/platform/img/logo.gif" />
</div>


		<div class="regSuccess">
			<div class="regSuccess_t" style="padding-bottom: 0.85rem;">
	   			<div class="title" style="margin-left:5%;margin-bottom: -0.2rem;padding: 0rem 0;">
	   				<span style="margin-top:5px;text-align:center"><font style="font-size:20px;color:#ffb23f">交易流水</font></span>
	   			</div>
	   			<div class="repayment"id="messageManage_tab2" >
                	<table class="table table-hover table-h" style="margin-left:0;">
                    <thead>
                      <tr style="background:#f9f9f9;height:20px;">
                        <th style="width:25%;text-align:left;">时间</th>
                        <th style="width:18%;text-align: center;">交易类型</th>
                        <th style="width:18%;text-align: center;">交易金额</th>
                        <th style="width:20%;text-align: center;">账户余额</th>
                      </tr>
                      
                    </thead>
                    <tbody id="datas" text-align="center" >
                        </tbody>
                  </table>
                  <div id="loading" class="load" style="display:none">
				        	 	 			<p>正在加载数据....</p>
				        	  	</div>
		        	  			<div id="nodata" class="load" style="display:none">
		        	 	 					<p>暂无数据</p>
		        	  			</div>
	       		 					<div id="Pagination1" class="pagination pagination-right">
               			 </div>
				</div>
				</div>
       		 </div>

<div class="bottom">
		<a class="page" href="${base}/index.do">
		<img src="${base}/images/page.png"><span style="margin-top: 4px;">主页</span>	</a>
		<a class="lnd" href="${base}/userIndex/index.do">
		<img src="${base}/images/lnd.png"><span style="margin-top: 4px;">账户</span></a>
		<a class="out" id="exitBtn" href="${base}/loginAction/logout.do">
		<img src="${base}/images/out.png"><span style="margin-top: 4px;">退出</span></a>
</div>
<script type="text/javascript">
//<![CDATA[
	$(document).ready(function(){
// 		var $startTime = $("#startTime");
// 		var $endTime = $("#endTime");
// 		var $reset = $("#reset");
// 		var $search = $("#search");
// 		//查询
// 		$search.on("click",function(){
// 			ajaxQuery(1,true);
// 			return false;
// 		});
// 		//重置
// 		$reset.on("click",function(){
// 			$startTime.val("");
// 			$endTime.val("");
// 			return false;
// 		});
		ajaxQuery(1,true);
	});
	
	var tradeType={};
//  	tradeType["0"]="充值";
 	tradeType["1"]="提现";
	tradeType["2"]="投资";
	tradeType["3"]="借款";
	tradeType["4"]="正常还款";
	tradeType["5"]="提前还款";
	tradeType["6"]="展期还款";
	tradeType["7"]="逾期还款";
	tradeType["8"]="平台垫付";
	tradeType["9"]="提现管理费";
	tradeType["10"]="充值管理费";
	tradeType["11"]="退回投资";
	tradeType["12"]="归还风险准备金";
	tradeType["13"]="提取风险准备金";
	tradeType["14"]="债权转让管理费";
	tradeType["15"]="接手奖金";
	tradeType["16"]="债权转让";
	tradeType["17"]="本金回收";
	tradeType["18"]="利息收益";
	tradeType["19"]="投资信息管理费";
	tradeType["20"]="展期管理费";
	tradeType["21"]="逾期管理费";
	tradeType["22"]="借款管理费";
	tradeType["23"]="提前还款违约金";
	tradeType["24"]="借款申请";
	tradeType["25"]="投资满标";
// 	tradeType["26"]="银行卡充值";
	tradeType["26"]="充值";
	tradeType["27"]="推广奖金";
	tradeType["28"]="第三方代还";
	tradeType["29"]="推荐人提成";
	tradeType["30"]="推荐奖励";
	tradeType["33"]="提现退回";
	tradeType["34"]="提现管理费退回";
	tradeType["35"]="vip投资收益";
	var currentindex = 1;
	var itemPerPage = 12;//每页显示数据条数
	function ajaxQuery(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryMyPayment.do",
				dataType:"json",
				data:"tradeType="+""+"&startTime="+""+"&endTime="+""+"&requestPage="+requestPage+"&pageSize="+itemPerPage,
				complete:function(){
				},
				success:function(data) {
					$('tr[id^=ready]').remove();
					if(data!=null){
				        $.each(data.list, function(i, n){//
				        	var row = $("<tr></tr>");
				        	row.attr("id","ready"+i);//改变绑定好数据的行的id
				            row.append($("<td style=\"width:30%;text-align: center;\" >"+n.TRADA_TIME+"</td>"));
				        	row.append($("<td style=\"width:30%;text-align: center;\" >"+tradeType[n.TRADE_TYPE_CD]+"</td>"));
				        	row.append($("<td style=\"width:20%;text-align: center;\" >"+fmoney(n.TRADE_AMT,2)+"</td>"));
				        	row.append($("<td style=\"width:20%;text-align: center;\" >"+fmoney(n.ACCOUNT_BAL,2)+"</td>"));
				        	//row.append($("<td>"+n.COMMENT+"</td>"));
				        	row.appendTo("#datas");
		                });
		                // 分页
		                if(isInit){
		                	pageQuery(data.totalCount);
		                }
						
		                $("#loading").hide();
		                $('tr[id^=ready]').show();
					}
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}
    // 分页查询
	function pageQuery(dataSize){
	    var optInit = $.extend({
		items_per_page:itemPerPage,
		num_display_entries:6,
		current_page:0,
		num_edge_entries:2,
		prev_text:"上一页",
		next_text:"下一页",
		callback:pageselectCallback
		});
	    $("#Pagination1").pagination(dataSize, optInit);
	}
	
	// 分页组件回调函数
	function pageselectCallback(page_index, j){
        //ajaxQuery(page_index+1,false);
        if(!isFistLoad1){
			ajaxQuery(page_index+1,false);
		}
		isFistLoad1=false;
        return false;
    }
    
 //]]>
</script>
<script>
var errMes = '${errMes}';
var sucMes = '${sucMes}';
var isFistLoad1 = true;
var isFistLoad2 = true;
var isFistLoad3 = true;
var isFistLoad4 = true;
if(errMes!=''){
  showDialogNew(true,errMes,'E','提醒信息');
}

if(sucMes!=''){
  showDialogNew(true,sucMes,'S','提醒信息');
}
</script>
</body>
</html>