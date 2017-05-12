<div id = "myLoan_tab1" class="myLoan">
          <table class="table table-hover">
                    <thead>
                      <tr>
                        <th style="width:20%;text-align: center;">标题</th>
                       <!-- <th>产品名称</th> -->
                        <!-- <th>标的类型</th>-->
                        <th style="width:8%;text-align: center;">期限</th>
                        <th style="width:10%;text-align: center;">年利率</th>
                        <th style="width:15%;text-align: center;">申请金额</th>
                        <th style="width:16%;text-align: center;">还款方式</th>
                        <th style="width:18%;text-align: center;">申请日期</th>
                        <th style="width:15%;text-align: center;">申请状态</th>
                      </tr>
                    </thead>
                    <tbody id="datas1">
                    </tbody>
          </table>
                  <div id="Pagination1" class="pagination pagination-right"></div>
</div>
<script type="text/javascript">
//<![CDATA[
//借款申请列表
function ajaxQuery1(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryApplyLoan.do",
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
			        $.each(data.list, function(i, n){
			        	var row = $("<tr></tr>");
			        	row.attr("id","ready"+i);//改变绑定好数 	据的行的id
			        	row.append($("<td style=\"width:20%;text-align: center;\" ><a href='javascript:void(0);' title=\""+n.LOAN_NAME+"\">"+n.LOAN_NAME+"</td>"));
			        	//row.append($("<td style=\"width:8%;text-align: center;\" >"+n.PRODUCT_ID+"</td>"));
			        	//row.append($("<td style=\"width:8%;text-align: center;\" >"+n.LOAN_TYPE_CD+"</td>"));
			        	row.append($("<td style=\"width:8%;text-align: center;\" >"+n.LOAN_TERM+"</td>"));
			        	row.append($("<td style=\"width:8%;text-align: center;\" >"+n.EXPECT_LOAN_RATE+"%</td>"));
			        	row.append($("<td style=\"width:15%;text-align: center;\" >"+fmoney(n.APPLY_AMT,2)+"</td>"));
			        	row.append($("<td style=\"width:16%;text-align: center;\" >"+n.REPAY_TYPE_CD+"</td>"));
			        	row.append($("<td style=\"width:18%;text-align: center;\" >"+n.APPLY_DATE+"</td>"));
			        	row.append($("<td style=\"width:15%;text-align: center;\" >"+n.APPLY_STATUS_CD+"</td>"));
			        	
			        	row.appendTo("#datas1");
			        	//$("#template").hide();
	                });
	                // 分页
	                if(isInit){
	                	pageQuery1(data.totalCount);
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
	function pageQuery1(dataSize){
	    var optInit = $.extend({
		items_per_page:itemPerPage,
		num_display_entries:10,
		current_page:0,
		num_edge_entries:2,
		prev_text:"上一页",
		next_text:"下一页",
		callback:pageselectCallback1
		});
	    $("#Pagination1").pagination(dataSize, optInit);
	}
	
	// 分页组件回调函数
	function pageselectCallback1(page_index, j){
         // ajaxQuery1(page_index+1,false);
        //return false;
        if(!isFistLoad1){    
			ajaxQuery1(page_index+1,false);
		}
		isFistLoad1=false;   
        return false;
    }
 //]]>
</script>