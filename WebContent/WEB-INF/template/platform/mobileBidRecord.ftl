<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户中心-首页-sjct888.com</title>

<meta name="description" content="">
<meta name="author" content="">


		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
		<meta name="mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${base}/css/framework7.min.css">
		<link rel="stylesheet" href="${base}/css/newRega205.css">
		<link href="${base}/css/loginAPP.css" rel="stylesheet" type="text/css">
		<link href="${base}/css/styleApp.css" rel="stylesheet" type="text/css">
</head>
<#include "/common/resource.ftl">
	<body class="newReg">
	
<div class="logo">
	<img src="${base}/platform/img/logo.gif" />
</div>


		<div class="regSuccess">
			<div class="regSuccess_t" style="padding-bottom: 0.85rem;">
	   			<div class="title" style="margin-left:5%;margin-bottom: -0.2rem;padding: 0rem 0;">
	   				<span style="margin-top:5px;text-align:center"><font style="font-size:20px;color:#ffb23f">投标记录</font></span>
	   			</div>
	   			<div class="repayment"id="messageManage_tab2" >
                	<table class="table table-hover table-h" style="margin-left:0;">
                    <thead>
                      <tr style="background:#f9f9f9;">
                        <th style="width:15%;text-align:center;">用户</th>
                        <th style="width:20%;text-align: center;">投标金额</th>
                        <th style="width:30%;text-align: center;">投标时间</th>
                      </tr>
                      
                    </thead>
                    <tbody id="tbjl_datas" text-align="center" >
                        </tbody>
                  </table>
                
		        	  			<div id="nodata" class="load" style="display:none">
		        	 	 					<p>暂无数据</p>
		        	  			</div>
	       		 					<div id="Pagination1" class="pagination pagination-right">
               			 </div>
				</div>
				</div>
       		 </div>

<div class="bottom">
		<a class="page" href="${base}/index.do">
		<img src="${base}/images/page.png"><span style="margin-top: 4px;">主页</span>	</a>
		<a class="lnd" href="${base}/userIndex/index.do">
		<img src="${base}/images/lnd.png"><span style="margin-top: 4px;">账户</span></a>
		<a class="out" id="exitBtn" href="${base}/loginAction/logout.do">
		<img src="${base}/images/out.png"><span style="margin-top: 4px;">退出</span></a>
</div>
<script>
var errMes = '${errMes}';
var sucMes = '${sucMes}';
var isFistLoad1 = true;
var isFistLoad2 = true;
var isFistLoad3 = true;
var isFistLoad4 = true;
if(errMes!=''){
  showDialogNew(true,errMes,'E','提醒信息');
}

if(sucMes!=''){
  showDialogNew(true,sucMes,'S','提醒信息');
}
</script>
<script type="text/javascript">
$(document).ready(function() {

ajaxQuery(1,true);
});
	//加载ajax记录
	function ajaxQuery(divVid){
// 		alert("222");
// 		$("#tbjl_datas").html(""); 
// 		$("#hkjl_datas").html(""); 
// 		$("#zrjl_datas").html(""); 
			$.ajax({				
				type:"POST",
				url:"ajaxCreditorRight.do",
				dataType:"json",
				data:"approveStatusCd=" + "0" + "&loanApproveId=" + '${loanApproveId}',
				complete:function() {
				},
				
				success:function(data) {
					 $.each(data.list, function(i, n){
					 	var row = $('<tr></tr>');
// 					 	row.append($("<td>" + (i+1) +"</td>")); 
					 	row.append($("<td style=\"text-align: center;\">" + HidChar(n.userCode) +"</td>")); 
					 	row.append($("<td  style=\"text-align: center;\">" + fmoney(n.crAmt,2) +"</td>"));  
// 					 	row.append($("<td>" + n.tenderTypeCd +"</td>")); 
					 	row.append($("<td  style=\"text-align: center;\">" + timeStamp2String(n.sysCreateTime) +"</td>"));
					 	row.appendTo("#tbjl_datas");    
					 }); 
				},
				error:function(text) {
					//alert('请求后台出错.');
				} 
			});
			
		}
	//截取/替换字符串(隐藏字符串）
	 function HidChar(str)
	 {
		 var s=str.substring(0,1); //取第一个字符
		 var strlen = "";
		 for(var i = 1;i < str.length;i++)
		  {
		  	strlen += "*";
		  }
		 s =s + strlen;	
	 	 return s;
	}


</script>
</body>
</html>