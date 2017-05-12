<div id="myLoan_tab3" class="myLoan" style="display:none">
                    <table class="table table-hover">
                    <thead>
                      <tr>
                        <th style="width:20%;text-align: center;">借款标题</th>
                        <th style="width:18%;text-align: center;">金额</th>
                        <th style="width:15%;text-align: center;">年利率</th>
                        <th style="width:12%;text-align: center;">期数</th>
                        <th style="width:20%;text-align: center;">还款方式</th>
                        <th style="width:15%;text-align: center;">状态</th>
                      </tr>
                    </thead>
                    <tbody id="datas3">
                      
                    </tbody>
                  </table>
                   
       		 <div id="Pagination3" class="pagination pagination-right"> </div>
</div>
<script type="text/javascript">
//<![CDATA[
function ajaxQuery3(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryMyRepay.do",
				dataType:"json",
				data:"requestPage="+requestPage+"&pageSize="+itemPerPage,
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
			        $.each(data.list, function(i, n){//REPAY_TYPE_CD
			        	var row = $("<tr><td style=\"width:20%;text-align: center;\" ><a target='_blank' href=\"${base}/loan/detail.do?loanApproveId="+n.loan_approve_id + "\" title=\""+n.LOAN_NAME+"\">"+n.LOAN_NAME+"</td>" + 
			        	"<td style=\"width:18%;text-align: center;\" >" + fmoney(n.LOAN_AMT,2) + "</td>" + 
			        	"<td style=\"width:15%;text-align: center;\" >" + n.LOAN_RATE + "%</td>" + 
			        	"<td style=\"width:12%;text-align: center;\" >" + n.SURPLUS_PERIOD + "/" + n.TOTAL_PERIOD + "</td>" + 
			        	"<td style=\"width:20%;text-align: center;\" >" + n.REPAY_TYPE_CD + "</td>" + 
			        	"<td style=\"width:15%;text-align: center;\" >" + n.LOAN_STATUS_CD + "</td></tr>");
			        	row.attr("id","ready"+i);//改变绑定好数据的行的id
			        	row.appendTo("#datas3");
	                });
	                // 分页
	                if(isInit){
	                	pageQuery3(data.totalCount);
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
	function pageQuery3(dataSize){
	    var optInit = $.extend({
		items_per_page:itemPerPage,
		num_display_entries:10,
		current_page:0,
		num_edge_entries:2,
		prev_text:"上一页",
		next_text:"下一页",
		callback:pageselectCallback3
		});
	    $("#Pagination3").pagination(dataSize, optInit);
	}
	
	// 分页组件回调函数
	function pageselectCallback3(page_index, j){
        if(!isFistLoad3){    
			ajaxQuery3(page_index+1,false);
		}
		isFistLoad3=false;   
        return false;
    }
 //]]>
</script>