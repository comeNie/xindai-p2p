<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户中心-首页-sjct888.com</title>

<meta name="description" content="">
<meta name="author" content="">
<#include "/common/resource.ftl">
</head>
<body>
<div style="min-width:1349px">
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
        <div class="user_panel">
        	<#include "/common/left.ftl">
            <div class="right">
               <div class="tab_table_N creditor">
               <div class="nav">
                    <ul id="myRepayNav">
                        <li id="tab1" class="current"><a href="javascript:void(0);">还款</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div id="myRepay_tab1" class="myRepay">
                    <table class="table">
	                    <thead>
	                      <tr>
	                      	<th>还款日期</th>
	                        <th>还款本金</th>
	                        <th>还款利息</th>
	                        <th>本息总额</th>
	                        <!--<th>已还本息</th>-->
	                        <!--<th>已付罚息</th>-->
	                        <th>状态</th>
	                        <th>操作</th>
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
		<input type="hidden" id="loanId" value="${loanId}" />
	</div>
</div>
</div>
<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
//<![CDATA[
	$(document).ready(function(){					   
	// 查询列表
		ajaxQuery(1,true);
	});


	var currentindex=1;
	var itemPerPage=10;//每页显示数据条数
	var loanId = $("#loanId").val();// 
	function ajaxQuery(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryMyRepayDetail.do",
				dataType:"json",
				data:"loanId=" + loanId +"&requestPage="+requestPage+"&pageSize="+itemPerPage,
				complete:function(){
				},
				success:function(data) {
					$('tr[id^=ready]').remove();
			        $.each(data.list, function(i, n){//REPAY_TYPE_CD
			        	var row = $("<tr></tr>");
			        	row.attr("id","ready"+i);//改变绑定好数据的行的id
			        	row.append($("<td>" + n.repayplan_date + "</td>"));
			        	row.append($("<td>" + n.principal_amt + "</td>"));
			        	row.append($("<td>" + n.interest_amt + "</td>"));
			        	row.append($("<td>" + n.principa_interest_amt + "</td>"));
			        	//row.append($("<td>" + n.repayed_amt + "</td>"));
			        	//row.append($("<td>" + n.repayed_penalty_amt + "</td>"));
			        	row.append($("<td>" + n.repay_status_cd + "</td>"));
			        	if(n.repay_status_cd!="已还"){
			        		row.append($("<td><a class=\"Operation ui-button ui-button-blue ui-button-mid\" href='javascript:void(0);' onclick='return showPreRepayDiv("+loanId+"," + n.id + ");'>还款</a></td>"));
			        	}else{
			        		row.append($("<td><span class=\"Operation ui-button ui-button-gray ui-button-mid\" >已还<span></td>"));
			        	}
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
					 alert('请求后台出错.');
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
        ajaxQuery(page_index+1,false);
        return false;
    }
    
    // 还款
    function repay(loanId,repayPlanId) {
    	if (confirm("确定还款吗？")) {
    		$.ajax({
				type:"POST",
				url:"ajaxRepay.do",
				dataType:"json",
				data:"loanId=" + loanId + "&repayPlanId=" + repayPlanId,
				complete:function(){
				},
				success:function(data) {
					showDialogNew(true,data.message,'S','提醒信息');
				},
				error:function(text) {
				//alert("后台错误");
				} 
			});
    	}
    }
    
    
    
    //显示还款界面
	function showPreRepayDiv(loanId,repayPlanId){
	   showAjaxAjaxLoading();
		$.ajax({
				type:"POST",
				url:"ajaxQueryRepayInfo.do",
				dataType:"json",
				data:"loanId="+loanId + "&repayPlanId=" + repayPlanId,
				complete:function(){
				},
				success:function(data) {
					hidenAjaxAjaxLoading();
					if( data.status==1 ){
						var arr = new Array();
							var obj1 = new HtmlObj("span",null,"本次还款本金","repayedPrincipalAmt","repayedPrincipalAmt",data.repayedPrincipalAmt,"");
							var obj2 = new HtmlObj("span",null,"本次还款利息","repayedInterestAmt","repayedInterestAmt",data.repayedInterestAmt,"");
 							var obj3 = new HtmlObj("span",null,"本次还款罚息","repayedPenaltyAmt","repayedPenaltyAmt",data.repayedPenaltyAmt,"");
 							var obj4 = new HtmlObj("span",null,"正常管理费","loanChargeAmt","loanChargeAmt",data.loanChargeAmt,"");
							var obj5 = new HtmlObj("span",null,"展期管理费","delayChargeAmt","delayChargeAmt",data.delayChargeAmt,"");
							var obj6 = new HtmlObj("span",null,"逾期管理费","overdueChargeAmt","overdueChargeAmt",data.overdueChargeAmt,"");
							var obj7 = new HtmlObj("span",null,"提前还款违约金","prepaymentChargeAmt","prepaymentChargeAmt",data.prepaymentChargeAmt,"");
							var obj8 = new HtmlObj("span",null,"本次还款总金额","ttlRepayedAmt","ttlRepayedAmt",data.ttlRepayedAmt,"");
							arr[0] = obj1;
							arr[1] = obj2;
							arr[2] = obj3;
							arr[3] = obj4;
							arr[4] = obj5;
							arr[5] = obj6;
							arr[6] = obj7;
							arr[7] = obj8;
							var arrStr = converToTable(arr,1);
							dialog({
									 title:'还款明细',
									 content:arrStr,
									 width: 300,
									 height:290,
									 zIndex: 100,
									 okValue:'还款',
									 ok:function(){
								        repay_new(loanId,repayPlanId);
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
	
	// 还款
    function repay_new(loanId,repayPlanId) {
    	if (confirm("确定还款吗？")) {
    		showAjaxAjaxLoading();
    		$.ajax({
				type:"POST",
				url:"ajaxRepay.do",
				dataType:"json",
				data:"loanId=" + loanId + "&repayPlanId=" + repayPlanId,
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