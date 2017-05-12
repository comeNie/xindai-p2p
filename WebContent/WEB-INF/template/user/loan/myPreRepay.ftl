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
                    <table class="table table-hover">
	                    <thead>
	                      <tr>
	                        <th>还款日期</th>
	                        <th>待还本息</th>
	                        <th>已还本息</th>
	                        <th>待还罚息</th>
	                        <th>已付罚息</th>
	                        <th>状态</th>
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
		<input type="hidden" id="loanApproveId" value="${loanApproveId}" />
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
	var loanApproveId = $("#loanApproveId").val();// 
	function ajaxQuery(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryMyRepayDetail.do",
				dataType:"json",
				data:"loanApproveId=" + loanApproveId +"&requestPage="+requestPage+"&pageSize="+itemPerPage,
				complete:function(){
				},
				success:function(data) {
					$('tr[id^=ready]').remove();
			        $.each(data.list, function(i, n){//REPAY_TYPE_CD
			        	var row = $("<tr></tr>");
			        	row.attr("id","ready"+i);//改变绑定好数据的行的id
			        	row.append($("<td>" + n.repayplan_date + "</td>"));
			        	row.append($("<td>" + n.principa_interest_amt + "</td>"));
			        	row.append($("<td>" + n.repayed_amt + "</td>"));
			        	row.append($("<td>" + n.unpaid_penalty_amt + "</td>"));
			        	row.append($("<td>" + n.repayed_penalty_amt + "</td>"));
			        	row.append($("<td>" + n.repay_status_cd + "</td>"));
			        	row.appendTo("#datas");
			        	//$("#template").hide();
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
        ajaxQuery(page_index+1,false);
        return false;
    }
    
    // 还款
    function repay(repayPlanId) {
    	if (confirm("确定还款吗？")) {
    		$.ajax({
				type:"POST",
				url:"ajaxRepay.do",
				dataType:"json",
				data:"loanId=" + loanApproveId + "&repayPlanId=" + repayPlanId,
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
    
    // 提前还款
    function repay2(repayPlanId) {
    	if (confirm("确定提前还款吗？")) {
    		$.ajax({
				type:"POST",
				url:"ajaxPreRepay.do",
				dataType:"json",
				data:"loanId=" + loanApproveId + "&repayPlanId=" + repayPlanId,
				complete:function(){
				},
				success:function(data) {
					alert(data);
				},
				error:function(text) {
					alert("后台错误");
				} 
			});
    	}
    }
 //]]>
</script>
</body>
</html>