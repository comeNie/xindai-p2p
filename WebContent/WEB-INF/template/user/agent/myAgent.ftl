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
                        <li  class="current"><a href="javascript:void(0);">我的经济人</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div class="repayment">
                    <table class="table table-hover">
                    <thead>
                      <tr>
                      	<th>序号</th>
                        <th>用户名</th>
                        <th>身份证</th>
                        <th>手机号</th>
                        <th>团队长名称</th>
                       
                      </tr>
                    </thead>
                    <tbody id="datas">
                    	
                    </tbody>
                  </table>
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
	var itemPerPage = 10;

	$(document).ready(function(){
		ajaxQuery(1, true);
	});

	function ajaxQuery(requestPage,isInit){
		$.ajax({
				type:"GET",
				url:"ajaxQueryMyAgent.do",
				dataType:"json",
				data:"requestPage=" + requestPage + "&pageSize=" + itemPerPage,
				complete:function() {
				},
				success:function(data) {
					$.each(data.list, function(i, n){
					 	var row = $('<tr></tr>');
					 	row.append($("<td>" + ${list_index + 1} +"</td>")); 
					 	row.append($("<td>" + n.userID +"</td>")); 
					 	row.append($("<td>" + n.cardId +"</td>"));  
					 	row.append($("<td>" + n.tel +"</td>")); 
					 	row.append($("<td>" + n.leaderID +"</td>"));  
					 	row.appendTo("#datas");    
					 }); 
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}
 //]]>
</script>
</body>
</html>