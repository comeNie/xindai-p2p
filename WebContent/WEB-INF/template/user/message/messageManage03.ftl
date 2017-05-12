 <div id="messageManage_tab3" class="repayment" style="display:none">
                	<table class="table table-hover table-h">
                    <thead>
                      <tr>
                        <th style="width:20%;text-align: center;">接收人</th>
                        <th style="width:30%;text-align: center;">主题</th>
                        <th style="width:25%;text-align: center;">时间</th>
                        <th style="width:25%;text-align: center;">操作</th>
                      </tr>
                    </thead>
                    <tbody id="datas03">
                      
                      
                    </tbody>
                  </table>
</div>
	 
<script type="text/javascript">
//<![CDATA[
var itemPerPage = 10;

$(document).ready(function(){
	ajaxQuery3(0);
});


	function ajaxQuery3(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQuerySendMessage.do",
				dataType:"json",
				data:"requestPage=" + requestPage + "&pageSize=" + itemPerPage,
				complete:function() {
				},
				success:function(data) {
					$('tr[id^=ready]').remove();
			        $.each(data.list, function(i, n){
			        	var row = $("<tr></tr>");
			        	row.append($("<td style=\"width:30%;text-align: center;\">" + n.recusername+"</td>"));
			        	row.append($("<td style=\"width:20%;text-align: center;\">" + n.titletxt + "</td>"));
			        	row.append($("<td style=\"width:25%;text-align: center;\">" + n.sendtime + "</td>"));
			        	tdStr = "<td style=\"width:25%;text-align: center;\">" 
			        		  + "<a href=\"javascript:void(0);\" onclick=\"showContentDiv('"+n.recusername+"','" + n.titletxt + "','" + n.contenttxt + "','" + n.msgid + "','" + n.readstatus + "')\">查看</a>"
			        		  + "<a onclick=\"deleteMsg('" + n.msgid + "','3')\" href=\"javascript:void(0);\">删除</a>"
			        		  + "</td>"
			        	row.append($(tdStr));
			        	row.appendTo("#datas03");
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