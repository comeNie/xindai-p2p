<div id="messageManage_tab2" class="repayment" style="display:none">
 	<h2>我的推荐投资明细：</h2>
                	<table class="table table-hover table-h">
                    <thead>
                      <tr>
                        <th style="width:30%;text-align:left;text-indent:20px">投资人</th>
                        <th style="width:20%;text-align: center;">投资金额</th>
                        <th style="width:20%;text-align: center;">投资期限</th>
                        <th style="width:25%;text-align: center;">投资时间</th>
<!--                         <th style="width:25%;text-align: center;">操作</th> -->
                      </tr>
                    </thead>
                    <tbody id="datas02">

                      
                    </tbody>
                  </table>
                  <div id="Pagination2" class="pagination pagination-right"></div>
</div>
<script type="text/javascript">
//<![CDATA[
	// 已结清的债权
	function ajaxQuery2(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryMyRecommend.do",
				dataType:"json",
				data:"requestPage=" + requestPage + "&pageSize=" + itemPerPage,
				complete:function(XMLHttpRequest,textStatus) {
					if(XMLHttpRequest.status == 535){  
					      //如果超时就处理 ，指定要跳转的页面 
					      dialog({
								title:"提醒信息",
								content:"登陆超时，请重新登陆！",
								okvalue:"确定",
								ok:function(){
								 	window.location.replace("${base}/loginAction/login.do");
								 },
								 onclose:function(){
								  window.location.replace("${base}/loginAction/login.do");
								 }
							}).showModal(); 
					}
				},
				success:function(data) {
					$('tr[id^=ready]').remove();
			        $.each(data.list, function(i, n){
			        	var row = $("<tr></tr>");	
			        	row.attr("id","ready"+i);
			        	row.append($("<td style=\"text-align:left;text-indent:20px\">" + n.CUST_NAME+"</td>"));
			        	row.append($("<td style=\"text-align:center\">" + n.CR_AMT + "</td>"));
			        	row.append($("<td style=\"text-align:center\">" + n.LOAN_TERM + "</td>"));
			        	row.append($("<td style=\"text-align:center\">" + n.DATE + "</td>"));
			        	row.appendTo("#datas02");
	                });
	                if(isInit){
	                	pageQuery2(data.totalCount);
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
	function pageQuery2(dataSize){
	    var optInit = $.extend({
		items_per_page:itemPerPage,
		num_display_entries:10,
		current_page:0,
		num_edge_entries:2,
		prev_text:"上一页",
		next_text:"下一页",
		callback:pageselectCallback2
		});
	    $("#Pagination2").pagination(dataSize, optInit);
	}
	
	// 分页组件回调函数
	function pageselectCallback2(page_index, j){
        //ajaxQuery2(page_index+1,false);
        if(!isFistLoad2){
			ajaxQuery2(page_index+1,false);
		}
		isFistLoad2=false;
        return false;
    }
    
 //]]>
</script>