<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户中心-首页-sjct888.com</title>
<!-- 移动端 借款意向页面 -->
<meta name="description" content="">
<meta name="author" content="">
<#include "/common/resource.ftl">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
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
		
			<div class="title"
				style="margin-left: 5%; margin-bottom: -0.2rem; padding: 0rem 0;">
				<span style="margin-top: 5px; text-align: center"><font
					style="font-size: 20px; color: #ffb23f">还款管理</font></span>
			</div>
            
               <div class="tab_table_N creditor auto_css">
                <div class="nav">
                    <ul id="myRepayNav">
                        <li id="tab1" class="current"><a href="javascript:void(0);">还款中借款</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div id="myRepay_tab1" class="myRepay">
                    <table class="table table-hover">
                    <thead>
                      <tr>
                        <th style="width:20%;text-align:center;">借款标题</th>
                        <th style="width:16%;text-align:center;">金额</th>
                        <th style="width:10%;text-align:center;">年利率</th>
                        <th style="width:8%;text-align:center;">期数</th>
                        <th style="width:20%;text-align:center;">还款方式</th>
                        <!-- <th>状态</th> -->
                        <th style="width:30%;text-align:center;">操作</th>
                      </tr>
                    </thead>
                    <tbody id="datas">
                    </tbody>
                  </table>
                  <div id="loading" class="load" style="display:none">
	        	 	 <p>正在加载数据....</p>
	        	  </div>
	        	  <div id="nodata" class="load" style="display:none">
	        	 	 <p>暂无数据</p>
	        	  </div>
	       		 <div id="Pagination" class="pagination pagination-right">
	                </div>
	       		 </div>
            </div>	
            
        </div>
        <div class="clear"></div>        
    </div>

<div class="bottom">
		<a class="page" href="${base}/index.do"> <img
			src="${base}/images/page.png"><span style="margin-top: 4px;">主页</span>
		</a> <a class="lnd" href="${base}/userIndex/index.do"> <img
			src="${base}/images/lnd.png"><span style="margin-top: 4px;">账户</span></a>
		<a class="out" id="exitBtn" href="${base}/loginAction/logout.do">
			<img src="${base}/images/out.png"><span
			style="margin-top: 4px;">退出</span>
		</a>
	</div>
	
<script type="text/javascript">
//<![CDATA[
	$(document).ready(function(){					   
		ajaxQuery(1,true);
		changeTab();// 加载页签切换效果
	});

	function changeTab() {
	   	$("#myRepayNav li").click(function () {
			var liId = $(this).attr('id');
			$(this).addClass("current").siblings().removeClass("current");
			$("div[class='myRepay']").hide();
			$("#myRepay_" + liId).show();
		});
	}

	var currentindex=1;
	var itemPerPage=10;//每页显示数据条数
	
	function ajaxQuery(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryMyRepay.do",
				dataType:"json",
				data:"requestPage="+requestPage+"&pageSize="+itemPerPage,
				complete:function(){
				},
				success:function(data) {
					$('tr[id^=ready]').remove();
			        $.each(data.list, function(i, n){//REPAY_TYPE_CD
			        	var row = $("<tr></tr>");
			        	row.attr("id","ready"+i);//改变绑定好数据的行的id
			        	row.append($("<td style=\"width:20%;text-align:center;\"><a target='_blank' href=\"${base}/loan/detail.do?loanApproveId="+n.loan_approve_id + "\" title=\""+n.LOAN_NAME+"\">"+n.LOAN_NAME+"</a></td>"));
			        	row.append($("<td style=\"width:16%;text-align:center;\">"+fmoney(n.LOAN_AMT,2)+"元</td>"));
			        	row.append($("<td style=\"width:8%;text-align:center;\">"+n.LOAN_RATE+"%</td>"));
			        	row.append($("<td style=\"width:6%;text-align:center;\">"+n.SURPLUS_PERIOD+"/"+n.TOTAL_PERIOD+"</td>"));
			        	row.append($("<td style=\"width:20%;text-align:center;\">"+n.REPAY_TYPE_CD+"</td>"));
			        	//row.append($("<td>"+n.LOAN_STATUS_CD+"</td>"));
			        	row.append($("<td style=\"width:30%;text-align:center;\"><a class=\"Operation ui-button ui-button-blue \"  href=\"${base}/userLoan/myRepayDetail.do?loanId="+n.ID + "\">还款</a></td>"));
// 			        	<a class=\"Operation ui-button ui-button-blue \"  href='javascript:void(0);' onclick='return showPreRepayDiv(" + n.ID + ");'>提前还款</a></td>"));
			        	row.appendTo("#datas");
			      
	                });
	                // 分页
	                if(isInit){
	                	pageQuery(data.totalCount);
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
	function pageQuery(dataSize){
	    var optInit = $.extend({
		items_per_page:itemPerPage,
		num_display_entries:10,
		current_page:0,
		num_edge_entries:2,
		prev_text:"上一页",
		next_text:"下一页",
		callback:pageselectCallback
		});
	    $("#Pagination").pagination(dataSize, optInit);
	}
	
	// 分页组件回调函数
	function pageselectCallback(page_index, j){
       // ajaxQuery(page_index+1,false);
       // return false;
       if(!isFistLoad1){
			ajaxQuery(page_index+1,false);
		}
		isFistLoad1=false;
        return false;
       
    }
    
   	//提前还款
    function preRepay(loanId) {
    	if (confirm("确定提前还款吗？")) {
    		$.ajax({
				type:"POST",
				url:"ajaxPreRepay.do",
				dataType:"json",
				data:"loanId=" + loanId,
				complete:function(){
				},
				success:function(data) {
					alert(data.message);
				},
				error:function(text) {
					alert("后台错误");
				} 
			});
    	}
    }
    
    
    //显示提前还款界面
	function showPreRepayDiv(loanId){
		showAjaxAjaxLoading();
		$.ajax({
				type:"POST",
				url:"ajaxQueryPreRepayInfo.do",
				dataType:"json",
				data:"loanId="+loanId,
				complete:function(){
				},
				success:function(data) {
					hidenAjaxAjaxLoading();
					if( data.status==1 ){
							var arr = new Array();
							var obj1 = new HtmlObj("span",null,"本次还款本金","repayedPrincipalAmt","repayedPrincipalAmt",data.repayedPrincipalAmt,"");
							var obj2 = new HtmlObj("span",null,"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其中:正常本金","repayAmtNormal","repayAmtNormal",data.repayAmtNormal,"");
 							
 							var obj3 = new HtmlObj("span",null,"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其中:提前还款本金","repayAmtPre","repayAmtPre",data.repayAmtPre,"");
							var obj4 = new HtmlObj("span",null,"本次还款利息","repayedInterestAmt","repayedInterestAmt",data.repayedInterestAmt,"");
 							
 							var obj5 = new HtmlObj("span",null,"本次还款罚息","repayedPenaltyAmt","repayedPenaltyAmt",data.repayedPenaltyAmt,"");
							
 							var obj6 = new HtmlObj("span",null,"正常管理费","loanChargeAmt","loanChargeAmt",data.loanChargeAmt,"");
							var obj7 = new HtmlObj("span",null,"展期管理费","delayChargeAmt","delayChargeAmt",data.delayChargeAmt,"");
							
							var obj8 = new HtmlObj("span",null,"逾期管理费","overdueChargeAmt","overdueChargeAmt",data.overdueChargeAmt,"");
							var obj9 = new HtmlObj("span",null,"提前还款违约金","prepaymentChargeAmt","prepaymentChargeAmt",data.prepaymentChargeAmt,"");
							var obj10 = new HtmlObj("span",null,"本次还款总金额","ttlRepayedAmt","ttlRepayedAmt",data.ttlRepayedAmt,"");
 							
							arr[0] = obj1;
							arr[1] = obj2;
							arr[2] = obj3;
							arr[3] = obj4;
							arr[4] = obj5;
							arr[5] = obj6;
							arr[6] = obj7;
							arr[7] = obj8;
							arr[8] = obj9;
							arr[9] = obj10;
							var arrStr = converToTable(arr,1);
							dialog({
									 title:'提前还款金额明细',
									 content:arrStr,
									  width: 300,
									  height:350,
									  zIndex: 100,
									 okvalue:'提前还款',
									 ok:function(){
								        preRepay_new(loanId);
						    		  },
						    		 cancelValue:'取消',
						    		 cancel:function(){}
							 }).showModal();
					}else{
						showDialogNew(true,data.reMes,'E','提醒信息');
					}
				},
				error:function(text) {
					hidenAjaxAjaxLoading();
					showDialogNew(true,'请求后台出错.','E','提醒信息');
				} 
			});
	}
    
    
    //提前还款
    function preRepay_new(loanId) {
    	if (confirm("确定提前还款吗？")) {
			showAjaxAjaxLoading();
    		$.ajax({
				type:"POST",
				url:"ajaxPreRepay.do",
				dataType:"json",
				data:"loanId=" + loanId,
				complete:function(){
				},
				success:function(data) {
					hidenAjaxAjaxLoading();
					if(data.status==1){
						ajaxQuery(1,true);
					}
					showDialogNew(true,data.message,'S','提醒信息');
				},
				error:function(text) {
					hidenAjaxAjaxLoading();
					showDialogNew(true,"后台错误",'E','提醒信息');
				} 
			});
    	}
    }
 //]]>
</script>
</body>
</html>