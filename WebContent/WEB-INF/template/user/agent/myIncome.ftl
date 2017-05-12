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
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
        <div class="user_panel">
        	<#include "/common/left.ftl">
            <div class="right">
             
               <div class="tab_table_N creditor auto_css">
                <div class="nav">
                    <ul>
                        <li  class="current"><a href="javascript:void(0);">我的收益</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div class="repayment">
                    <table class="table table-hover">
                    <thead>
                      <tr>
                        <th style="width:20%;text-align: center;">推广人</th>
                        <th style="width:20%;text-align: center;">被推广人</th>
                        <th style="width:15%;text-align: center;">推广类型</th>
                        <th style="width:25%;text-align: center;">推广时间</th>
                        <th style="width:20%;text-align: center;">奖励金额</th>
                        
                      </tr>
                    </thead>
                    <tbody  id="datas">
                    	
                    </tbody>
                  </table>
                </div>
       		 </div>
            </div>	
        </div>
        <div class="clear"></div>        
    </div>
</div>	

<script type="text/javascript">
//<![CDATA[
	var itemPerPage = 10;

	$(document).ready(function(){
		
		ajaxQuery(1, true);
	});

	function ajaxQuery(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQuerymyIncome.do",
				dataType:"json",
				data:"requestPage=" + requestPage + "&pageSize=" + itemPerPage,
				complete:function() {
				},
				success:function(data) {
					$.each(data.list, function(i, n){
					 	var row = $('<tr></tr>');
					 	row.append($("<td style=\"width:20%;text-align: center;\" >" + n.userCode +"</td>")); 
					 	row.append($("<td style=\"width:20%;text-align: center;\"  >" + n.byUserCode +"</td>")); 
					 	row.append($("<td style=\"width:15%;text-align: center;\"  >" + n.promotTypeCd +"</td>"));  
					 	row.append($("<td style=\"width:25%;text-align: center;\"  >" + timeStamp2String(n.txDate) +"</td>"));  
					 	row.append($("<td style=\"width:20%;text-align: center;\" >" + fmoney(n.promotAmt,2) +"</td>")); 
 
					 	row.appendTo("#datas");    
					 }); 
					 
					  // 分页
	                if(isInit){
	                	pageQuery(data.totalCount);
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