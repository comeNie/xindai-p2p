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
               <div class="tab_table_N creditor auto_css">
                <div class="nav">
                    <ul>
                        <li  class="current"><a href="javascript:void(0);">债权转让列表</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div class="repayment">
                    <table class="table table-hover">
                    <thead>
                      <tr>
                        <th style="width:20%;text-align: center;">借款标题</th>
                        <th style="width:14%;text-align: center;">债权金额</th>
                        <th style="width:14%;text-align: center;">剩余金额</th>
                        <th style="width:14%;text-align: center;">转让金额</th>
                        <th style="width:14%;text-align: center;">利率（%）</th>
                        <th style="width:8%;text-align: center;">进度</th>
                        <th style="width:16%;text-align: center;">状态</th>
                      </tr>
                    </thead>
                    <tbody id="datas">
                    	
                    </tbody>
                  </table>
                  <div id="Pagination" class="pagination pagination-right"></div>
                </div>
       		 </div>
            </div>	
        </div>
        <div class="clear"></div>        
    </div>
</div>	
<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
//<![CDATA[
	var currentindex = 1;
	var itemPerPage = 10;//每页显示数据条数

	$(document).ready(function(){					   
	    ajaxQuery(1,true);
	});
	function ajaxQuery(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryCreditorTran.do",
				dataType:"json",
				data:"requestPage=" + requestPage + "&pageSize=" + itemPerPage,
				complete:function() {
				},
				success:function(data) {
					$('tr[id^=ready]').remove();
			        $.each(data.list, function(i, n){
			        	var row = $("<tr></tr>");
			        	row.attr("id","ready"+i);//改变绑定好数据的行的id
			        	row.append($("<td style=\"width:20%;text-align: center;\" ><a target='_blank' href=\"${base}/loan/detail.do?loanApproveId="+n.LOAN_APPROVE_ID + "\" title=\""+n.loanName+"\">"+ n.loan_name + "</td>"));
			        	row.append($("<td style=\"width:14%;text-align: center;\" >" + fmoney(n.cr_amt,2) + "</td>"));
			        	row.append($("<td style=\"width:14%;text-align: center;\" >" + fmoney(n.unretrieve_amt,2) + "</td>"));
			        	row.append($("<td style=\"width:14%;text-align: center;\" >" + fmoney(n.tran_ttl_amt,2) + "</td>"));
			        	row.append($("<td style=\"width:14%;text-align: center;\" >" + n.rate + "</td>"));
			        	row.append($("<td style=\"width:8%;text-align: center;\" >" + n.completeness + "%</td>"));
			        	row.append($("<td style=\"width:16%;text-align: center;\" >" + n.crt_status_cd + "</td>"));
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
		//ajaxQuery(page_index+1,false);
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