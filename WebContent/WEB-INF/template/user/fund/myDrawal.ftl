<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户中心-首页-sjct888.com</title>

<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=1349;"/>
<#include "/common/resource.ftl">
</head>
<body>
<div style="min-width:1349px;margin:0 auto;max-width:1400px">
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
        <div class="user_panel">
        	<#include "/common/left.ftl">
            <div class="right">
             <div class="tab_table_N creditor auto_css">
                <div class="nav">
                    <ul>
                        <li class="current"><a href="javascript:void();">我要提现</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div class="repayment repayment_h">
                	 <input type="hidden" id="bankid" name="bankid" value="<#if bankid??>${bankid}<#else></#if>"/>
                	 <input type="hidden" id="isCashPassword" name="isCashPassword" value="${isCashPassword}"/>
                	 <input type="hidden" id="drawalRate" name="drawalRate" value="<#if drawalRate??>${drawalRate}<#else>0.01</#if>"/>
         
                     <div class="row" id="bankSelectDiv">
                    	
                     </div>
               
                    <hr class="line">
                    <div class="clear"></div>
                 	
                 	<div class="recharge">
          			<form class="form-horizontal" method="post" id="submitForm" action="${base}/userFund/myDrawalSubmit.do" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
                        <fieldset>
                                  <h2>填写提现金额：</h2> 
                                  <div class="control-group">
                                    <label class="control-label">账户余额：</label>
                                      <div class="controls">
                                        <span>${userIndexVO.accountBalAmt}元</span>
                                      </div>
                                  </div>
                                  
                                  <div class="control-group">
                                    <label class="control-label"><em>*</em>可用金额：</label>
                                     <div class="controls">
                                     <span id="usableBalAmt">${userIndexVO.usableBalAmt}元</span>
                                    </div>
                                  </div>
                                  
                                  <div class="control-group">
                                      <label class="control-label" for="dealAmt">提现金额：</label>
                                        <div class="controls">
                                          <input type="text" id="dealAmt" name="dealAmt" class="span3"  data_rule="提现金额:required;" /><span>元&nbsp;</span>
                                       	  <input type="hidden" id="outAccount" name="outAccount" class="span3" >
                                       	  <input type="hidden" id="bankType" name="bankType" class="span3"/>
                                       	  <label style="margin-top:1%;color:#ffb23f">（请输入100的倍数）</label>
                                       </div>
                                  </div>
                                <div class="control-group">
                                  <label class="control-label">提现费用：</label>
                                    <div class="controls">
                                      <span id="feeAmtShow">${drawalRate}元</span>
                                      <input type="hidden" id="feeAmt" name="feeAmt" class="span3" />
                                      <input type="hidden" id="tradePwd" name="tradePwd" class="span3" />
                                    </div>
                                </div>
                                  <div class="btnbar"><a class="btn btn-primary" href="javascript:void(0);" onclick="showTXDiv();">提现申请</a></div>
                            </fieldset>
                      </form>
                      <table class="table table-hover table-h">
                            <thead>
                              <tr>
                              	<th style="width:32px;text-align: center;">序号</th>
                              	<th style="width:20%;text-align: center;">提现银行卡</th>
                                <th style="width:145px;text-align: center;">提现时间</th>
                                <th style="width:80px;text-align: center;">提现金额</th>
                                <th style="width:80px;text-align: center;">提现费用</th>
                                <th style="width:80px;text-align: center;">提现状态</th>
                              </tr>
                            </thead>
                            <tbody id="data_card">
                              
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
        </div>
        <div class="clear"></div>        
    </div>
</div>	
</div>	
<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
//<![CDATA[
	var currentindex = 1;
	var itemPerPage = 3;


	$(document).ready(function(){ 
		var $submit = $("#submit");     //提现申请
		var $dealAmt = $("#dealAmt");  //提现金额
		var $usableBalAmt = $("#usableBalAmt");   //可用金额
		var $bankid = $("#bankid");               //银行卡
		var $submitForm = $("#submitForm");       //表单
		
		ajaxQuery(1, true);
		loadRcd(1, true);
		
	 
		
		//金额输入框
		$dealAmt.on("keyup",function(){
			if(/^([1-9][\d]{0,7}|0)?$/.test($(this).val())){
			}else{
			  $dealAmt.val("");
			}
			
// 			if($dealAmt.val() == ""){
// 			    var $fee = $("#drawalRate").val()
// 			}else{
// 				var $fee = (parseFloat($dealAmt.val()) * parseFloat($("#drawalRate").val())).toFixed(2);
// 			}
			getFeeAmt($dealAmt.val());
			
// 			$("#feeAmtShow").text($fee + "元"); 
// 			$("#feeAmt").val(12);
		});	
		
		//银行卡管理
		$("#magSel").click(function(){ 
			window.location.href = "${base}/userFund/myBankCard.do";
        });
        
	});
	function getFeeAmt(dealAmt){
		$.ajax({
				type:"POST",
				url:"ajaxQueryWithdrawFee.do",
				dataType:"json",
				data:"dealAmt="+dealAmt,
				success:function(data) {
					$("#feeAmtShow").text(data + "元"); 
					$("#feeAmt").val(data);
				},
				error:function(text) {
					//alert('请求后台出错.');
				} 
			});
	}
   function showTXDiv(){
     	if($("#isCashPassword").val()=="false"){
				showDialogNew(true,"请先设置交易密码！",'E','提醒信息');
				return false;
		}
     	if($("#bankid").val()==""){
				showDialogNew(true,"请先选择银行！",'E','提醒信息');
				return false;
		}
			
		if($("#dealAmt").val()==""||$("#dealAmt").val()=="0"){
			    showDialogNew(true,"请输入正确的金额！",'E','提醒信息');
				return false;
		}
		if (!($("#dealAmt").val() % 100 == 0)) {
			showDialogNew(true,"请输入100的倍数",'E','提醒信息');
			return false;
		}
		
		if($("#dealAmt").val()!="" && $("#usableBalAmt").text()!="" &&
				parseFloat($("#dealAmt").val()) >= parseFloat($("#usableBalAmt").text())){
				showDialogNew(true,"提现金额不能大于可用金额！",'E','提醒信息');
				return false;
		}
	
		dialog({
				 title:'请输入交易密码',
				 content:"<label class='control-label'>交易密码：</label><div class='controls'><input type='password' id='password' name='password' class='span3' /></div>",
		         okValue:"提现",
		         ok:function(){
		            var password = $('#password').val();
		            if(password == ""){
		              showDialogNew(true,"交易密码不能为空",'E','提醒信息');
		              return false;
		            }
		            $('#tradePwd').attr("value",password);
		            showAjaxAjaxLoading();
					$('#submitForm').submit();
		         }
		 }).showModal();
   }
   
	
	//加载银行卡信息
	function ajaxQuery(requestPage,isInit){
		$.ajax({
				type:"POST",
				url:"ajaxQueryMyBankCard.do",
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
					$("#bankSelectDiv").html("");
					if( data.list==null ){
						$("#bankSelectDiv").html("&nbsp;&nbsp;&nbsp;您没有设置银行卡信息，请<a align=\"absmiddle\" style=\"cursor:hand;color:#ffb23f\" href=\"${base}/userFund/myBankCard.do\">点击</a>进行银行卡设置");
					}else{
				        $.each(data.list, function(i, n){       	
				        	var row = $('<div></div>');  
				        	row.addClass("item current");   
				        	row.attr("bankid",n.ID);
				        	row.attr("accountnum",n.ACCOUNT_NUM);
				        	row.attr("bankType",n.BANK_TYPE);
				        	row.append($("<p><img src=\"${base}/platform/img/card01.gif\" align=\"absmiddle\"/>" + n.BANK_NAME + "</p>"));
				        	row.append($("<p>" + n.ACCOUNT_NUM + "</p>"));
				        	row.append($("<p>" + n.AREA_CITY + "</p>"));
				        	row.appendTo("#bankSelectDiv");       	
		                });
						//银行选择
						$("#bankSelectDiv .item").click(function () {
                            $("#bankSelectDiv .item").removeClass("selected");
                            $(this).addClass("selected");
							$("#bankid").val($(this).attr('bankid'));
							$("#bankType").val($(this).attr('bankType'));
							$("#outAccount").val($(this).attr('accountnum'));
						});
					}
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}


	// 删除银行卡
    //function deleteCard(cardId) {
    	//if (confirm("确认删除该银行卡吗？")) {
    		//window.location.href = "${base}/userFund/deleteCard.do?cardId=" + cardId;
    	//}
  //  }
    
   
    $(".search_b span").live("click", function () {
		loadRcd(1,true);
	});
    
	    var requestPage = 1;    //当前的请求页
		var itemPerPage = 5;   //每页显示的笔数
    function loadRcd(requestPage,isInit){
       // alert("5efet");
       
        $('tr[id^=ready]').hide();
		$("#nodata").hide();
		$("#loading").show();
		$.ajax({
				type:"POST",
				url:"ajaxQueryTxjr.do",
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
				     if(data.list == null){
					   $("#loading").hide();
					   $("#nodata").show();
					}
					 $.each(data.list, function(i, n){       	
				        	var row = $('<tr></tr>');
				        	row.attr("id","ready"+i); //改变绑定好数据的行的id
						 	row.append($("<td style=\"width:32px;text-align: center;\" >" + (i) +"</td>")); //序号
						 	row.append($("<td style=\"width:20%;text-align: center;\" >" + n.tx_bankCard +"</td>")); //提现银行卡
						 	row.append($("<td style=\"width:145px;text-align: center;\" >" + n.tx_time +"</td>")); //提现时间
						 	row.append($("<td style=\"width:80px;text-align: center;\" >" + n.TX_AMT +"</td>"));  //提现金额
						 	row.append($("<td style=\"width:80px;text-align: center;\" >" + n.FEE_AMT +"</td>"));  //提现费用
						 	row.append($("<td style=\"width:80px;text-align: center;\" >" + n.tx_status +"</td>"));  //提现状态
						 	row.appendTo("#data_card");    	
		                });
		                
		                 // 分页
	                if(isInit){
	                	pageQuery(data.totalCount);
	                }
	                
	                $("#loading").hide();
	                $('tr[id^=ready]').show();
				},
				error:function(text) {
					//alert('请求后台出错.');
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
			loadRcd(page_index+1,false);
		}
		isFistLoad1=false;
        return false;
    }
    
 //]]>
</script>
</body>
</html>