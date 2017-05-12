<div id = "myLoan_tab2" class="myLoan" style="display:none">
       <table class="table table-hover">
                    <thead>
                      <tr>
                        <th style="width:20%;text-align: center;">标题</th>
                       <!-- <th>期限</th>-->
                        <th style="width:15%;text-align: center;">金额</th>
                        <th style="width:10%;text-align: center;">年利率</th>
                        <th style="width:22%;text-align: center;">还款方式</th>
                        <th style="width:25%;text-align: center;">结束日期</th>
                        <th style="width:8%;text-align: center;">进度</th>
                       <!-- <th>状态</th> -->
                       <!--  <th>操作</th>-->
                      </tr>
                    </thead>
                     <tbody id="datas2">
                    
                    </tbody>
     </table>
                  <div id="Pagination2" class="pagination pagination-right"></div>
</div>
<script type="text/javascript">
//<![CDATA[
function ajaxQuery2(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryApproveLoan.do",
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
			        $.each(data.list, function(i, n){//
			        	var row = $("<tr></tr>");
			        	row.attr("id","ready"+i);//改变绑定好数据的行的id
			        	row.append($("<td style=\"width:20%;text-align: center;\" ><a target='_blank' href=\"${base}/loan/detail.do?loanApproveId="+n.ID + "\" title=\""+n.LOAN_NAME+"\">"+n.LOAN_NAME+"</a></td>"));
			        	//row.append($("<td style=\"width:15%;text-align: center;\" >"+n.LOAN_TERM+"</td>"));
			        	row.append($("<td style=\"width:15%;text-align: center;\" >"+fmoney(n.LOAN_AMT,2)+"</td>"));
			        	row.append($("<td style=\"width:10%;text-align: center;\" >"+n.LOAN_RATE+"%</td>"));
			        	row.append($("<td style=\"width:22%;text-align: center;\" >"+n.REPAY_TYPE_CD+"</td>"));
			        	row.append($("<td style=\"width:25%;text-align: center;\" >"+n.TENDER_DUE_TIME+"</td>"));
			        	row.append($("<td style=\"width:8%;text-align: center;\" ><div class=\"progress-circle\" style=\"background-position:-"+ (n.PROGRESS*54) +"px 0px;\">"+n.PROGRESS+"%</div></td>"));
			        	//row.append($("<td style=\"width:20%;text-align: center;\" >"+n.APPROVE_STATUS_CD+"</td>"));
			        	//row.append("<td style=\"width:20%;text-align: center;\" ><a href='#'>撤标</a>&nbsp&nbsp<a href='#'>借款</td>");
			        	row.appendTo("#datas2");
	                });
	                // 分页
	                if(isInit){
	                	pageQuery2(data.totalCount);
	                }
					
	                $("#loading2").hide();
	                $('tr[id^=ready]').show();
			         
				},
				error:function(text) {
					// alert('请求后台出错');
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
         // ajaxQuery2(page_index+1,false);
        //return false;
        if(!isFistLoad2){    
			ajaxQuery2(page_index+1,false);
		}
		isFistLoad2=false;   
        return false;
    }
 //]]>
</script>