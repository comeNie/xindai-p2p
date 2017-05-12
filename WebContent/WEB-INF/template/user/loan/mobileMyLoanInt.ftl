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
					style="font-size: 20px; color: #ffb23f">我的借款意向</font></span>
			</div>


			<div class="tab_table_N creditor auto_css">
				<div class="nav">
					<ul id="myRepayNav">
						<li id="tab1" class="current"><a href="javascript:void(0);">我的借款意向</a></li>
					</ul>
				</div>
				<div class="clear"></div>
				<div id="myRepay_tab1" class="myRepay" text-align="center">
					<table class="table table-hover" align="center">
						<thead>
							<tr>
								<th style="width: 20%; text-align: center;">申请人</th>
								<th style="width: 15%; text-align: center;">期限(月)</th>
								<th style="width: 15%; text-align: center;">年利率</th>
								<th style="width: 18%; text-align: center;">金额</th>
								<th style="width: 20%; text-align: center;">备注</th>
								<th style="width: 12%; text-align: center;">状态</th>
							</tr>
						</thead>
						<tbody id="datas">
						</tbody>
					</table>
					<div id="loading" class="load" style="display: none">
						<p>正在加载数据....</p>
					</div>
					<div id="nodata" class="load" style="display: none">
						<p>暂无数据</p>
					</div>
					<div id="Pagination" class="pagination pagination-right"></div>
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
	});

	var currentindex=1;
	var itemPerPage=10;//每页显示数据条数
	
	function ajaxQuery(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryMyLoanInt.do",
				dataType:"json",
				data:"requestPage="+requestPage+"&pageSize="+itemPerPage,
				complete:function(){
				},
				success:function(data) {
					$('tr[id^=ready]').remove();
			        $.each(data.list, function(i, n){//REPAY_TYPE_CD
			        	var row = $("<tr></tr>");
			        	row.attr("id","ready"+i);//改变绑定好数据的行的id
			        	row.append($("<td style=\"width:20%;text-align: center;\" >"+n.cust_name+"</td>"));
			        	row.append($("<td style=\"width:15%;text-align: center;\" >"+n.loan_term+"月</td>"));
			        	row.append($("<td style=\"width:15%;text-align: center;\" >"+n.expect_loan_rate+"%</td>"));
			        	row.append($("<td style=\"width:18%;text-align: center;\" >"+fmoney(n.apply_amt,2)+"元</td>"));
			        	row.append($("<td style=\"width:20%;text-align: center;\" >"+n.refuse_reason+"</td>"));
			        	row.append($("<td style=\"width:12%;text-align: center;\" >"+n.PROCESS_STATUS_CD+"</td>"));
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
        //return false;
        
        if(!isFistLoad1){    
			ajaxQuery(page_index+1,false);
		}
		isFistLoad1=false;
		return false;
    }
 
 //]]>
</script>
</body>
</html>