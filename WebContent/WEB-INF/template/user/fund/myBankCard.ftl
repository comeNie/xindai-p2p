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
                        <li class="current"><a href="javascript:void();">我的银行卡</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div class="repayment" id="bankSelectDiv">
                     
                </div>
                <p style="display:none"><a href="${base}/userFund/addBankCard.do"><button class="button_ui">添加银行卡</button></a></p>
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
	var itemPerPage = 9;


	$(document).ready(function(){
		var $submit = $("#submit");
		var $dealAmt = $("#dealAmt");
		var $usableBalAmt = $("#usableBalAmt");
		var $bankid = $("#bankid");
		var $submitForm = $("#submitForm");
		
		ajaxQuery(1, true);
		//insertProInfo();
		//insertBankName();
	});
	

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
						data.list = new Array();
					}
					var divarr = new Array();
				    var rowIndex = 0;  
		            for(x=0;x<1;x++){
		            	var be = 3*x;
		            	var en = 3*(x+1);
		            	
		                rowDiv = $('<div></div>');
						rowDiv.addClass("row");	
						rowDiv.attr("id","ind_" + rowIndex);
						divarr[rowIndex] = rowDiv;
						rowIndex++;
						
		                for(i=be;i<en;i++){
		                	n = data.list[i];
		                	var row = $('<div></div>');  
				        	row.addClass("item current");
				        	if( n ){
					        	row.attr("bankid",n.ID);
					        	row.append($("<p><img src=\"${base}/platform/img/card01.gif\" align=\"absmiddle\"/>" + n.BANK_NAME + "</p>"));
					        	row.append($("<p>" + n.ACCOUNT_NUM + "</p>"));
					        	row.append($("<p>" + n.AREA_CITY + "</p>"));
                                row.append($("<p><a class=\"itemclose iconfont\" onclick=\"deleteCard('" + n.ID + "')\" href=\"#\">&#xf003e;</a></p>"));
					        	row.appendTo(rowDiv);
					        }else{
					        	row.attr("bankid","newBank_"+i);
					        	row.append($("<p>&nbsp;</p>"));
					        	row.append($("<p><a href=\"javascript:void(0);\" onclick=\"showAddBankDiv('${custName}','${certificateNum}')\">添加银行卡</a></p>"));   
					        	row.append($("<p>&nbsp;</p>"));	
					        	row.appendTo(rowDiv);
					        }
		                }
		               
		            }    
		                
		                
		                for(i=0;i<divarr.length;i++){
		                	divarr[i].appendTo($("#bankSelectDiv"));
		                }
		                
		                
						//银行选择
						$("#bankSelectDiv .item").click(function () {
							$("#bankid").val($(this).attr('bankid'));
						}).hover(function(){
                            $(this).find(".itemclose").show();
                        },function(){
                            $(this).find(".itemclose").hide();
                        });
					
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}
		
	//显示添加银行卡
	function showAddBankDiv(accountName,certificateNum){
		var arr = new Array();
		var obj1 = new HtmlObj("span",null,"真实姓名","accountName","accountName",accountName,"");
		var obj2 = new HtmlObj("span",null,"身份证号","repayedInterestAmt","repayedInterestAmt",certificateNum,"");
		var obj3 = new HtmlObj("input","text","银行卡号","accountNum","accountNum","","data-rule='银行卡号:required;digits; length[11~19];'");
		var obj4 = new HtmlObj("input","text","确认银行卡号","accountNum1","accountNum1","","data-rule='确认银行卡号:required;match(accountNum);'");
		var obj5 = new HtmlObj("select","","开户银行","bankType","bankType","","onchange='getBankName();' data-rule='开户银行:required;'");
		var obj6 = new HtmlObj("select","","省份","areaProvince_sel","areaProvince_sel",""," onchange='insertCityInfo();' data-rule='省份:required;'");
		var obj7 = new HtmlObj("select","","市区","areaCity_sel","areaCity_sel",""," onchange='onChangeCityName();' data-rule='市区:required;'");
		var obj8 = new HtmlObj("input","hidden","省份","areaProvince","areaProvince","","");
		var obj9 = new HtmlObj("input","hidden","市区","areaCity","areaCity","","");
		var obj10 = new HtmlObj("input","hidden","开户银行","bankName","bankName","","");
		
 
		arr[0] = obj1;
		arr[1] = obj2;
		arr[2] = obj3;
		arr[3] = obj4;
		arr[4] = obj5;
		arr[5] = obj6;
		arr[6] = obj7;
		arr[7] = obj8;
		arr[8] = obj9;
		arr[9] = obj10;
		var arrStr = converToHtml(arr,"addBankInfo(formStr)");
		dialog({
				 title:'添加银行卡',
				 content:arrStr,
				 width: 450,
				 zIndex: 100,
		  		  onfocus:function(){
			        insertBankName();
			        insertProInfo();
		  		  }
		 }).showModal();
					
	}
	
	//增加银行卡
	function addBankInfo(formStr){

		showAjaxAjaxLoading();
			$.ajax({
				type:"GET",
				url:"ajaxAddBankCard.do",
				dataType:"json",
				data:formStr,
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
					hidenAjaxAjaxLoading();
					if(data.status=="1"){
					  ajaxQuery(1,true);
					  showDialogNew(true,data.reMes,'S','提醒信息');
					}else{
						showDialogNew(true,data.reMes,'E','提醒信息');
					}					
				}
				
			});
	}
	
	 
	// 删除银行卡
    function deleteCard(cardId) {
    	if (confirm("确认删除该银行卡吗？")) {
    		$.ajax({
				type:"POST",
				url:"ajaxDelBankCard.do",
				dataType:"json",
				data:{
						cardId:cardId
					  },
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
					if(data.status=="1"){
						showDialogNew(true,data.reMes,'S','提醒信息');
						ajaxQuery(1, true);
					}else{
						showDialogNew(true,data.reMes,'E','提醒信息');
					}
					
					
				}
				
			});
    		
    	}
    }
    
    function insertProInfo(){
    		$.ajax({
				type:"POST",
				url:"ajaxQueryPro.do",
				dataType:"json",
				
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
					if(data.status!="1"){
						showDialog(true,data.reMes);	
					}else{
						$("#areaProvince_sel").empty();
						$("#areaProvince_sel").prepend("<option value=''>请选择</option>");   
						
						$.each(data.list, function(i, n){
							$("#areaProvince_sel").append("<option value='" + n.id + "'>" + n.name + "</option>");   
						});
						
						
					}
					
				}
				
			});
    }
    
    function onChangeCityName(){
    	$("#areaCity").val($("#areaCity_sel").children('option:selected').text());
    }
    
    function getBankName(){
    	$("#bankName").val($("#bankType").children('option:selected').text());
    }
    
    
    function insertCityInfo(){
    	$("#areaProvince").val($("#areaProvince_sel").children('option:selected').text());
        $.ajax({
				type:"POST",
				url:"ajaxQueryCity.do",
				dataType:"json",
				data:{
						proId:$("#areaProvince_sel").children('option:selected').val()
					  },
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
					if(data.status!="1"){
						showDialog(true,data.reMes);	
					}else{
						$("#areaCity_sel").empty();
						$("#areaCity_sel").prepend("<option value=''>请选择</option>");   
						
						$.each(data.list, function(i, n){
							$("#areaCity_sel").append("<option value='" + n.id + "'>" + n.name + "</option>");   
						});
					}
					
				}
				
			});
    }
    
    
    function insertBankName(){
    		$.ajax({
				type:"POST",
				url:"ajaxQueryBank.do",
				dataType:"json",
				
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
					if(data.status!="1"){
						showDialog(true,data.reMes);	
					}else{
						$("#bankType").empty();
						$("#bankType").prepend("<option value=''>请选择</option>");   
						
						$.each(data.list, function(i, n){
							$("#bankType").append("<option value='" + n.id + "'>" + n.name + "</option>");   
						});
						
						
					}
					
				}
				
			});
    }

 //]]>
</script>
</body>
</html>