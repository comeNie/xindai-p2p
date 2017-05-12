<html>
<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
		<meta name="mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${base}/css/framework7.min.css">
		<link rel="stylesheet" href="${base}/css/newRega205.css">
		<link href="${base}/css/loginAPP.css" rel="stylesheet" type="text/css">
		<link href="${base}/css/styleApp.css" rel="stylesheet" type="text/css">
		<#include "/common/resource.ftl">
		<style>
		
		.red_text {
			float: left;
			width: 90%;
			margin-left: 5%;
			height: 5px;
			line-height: 5px;
			font-size: 18px;
			text-indent: 5px;
			color: #000;
			}
			.realName_success .title, .regSuccess .title {
			padding: .1rem 0;
			font-size: .34rem;
			color:#ffb23f;
			}
			.table-hover {
   			 margin:0px -16px;
    		}
		</style>
	</head>
	<body class="newReg">

<div class="logo">
	<img src="${base}/platform/img/logo.gif" />
</div>

		<div class="regSuccess">
			<div class="regSuccess_t">
	   			<div class="title" style="margin-left:5%;">
	   				<span style="margin-top:5px;text-align:center"><a href="${base}/userMessage/messageManage.do?myVid=us"><font style="font-size:20px">我的推广</font></a></span>
	   				<span style="margin-top:7px;margin-left:25px;background:#ffb23f;width:100px;border-radius:5px;text-align:center;vertical-align: middle;line-height:30px"><a href="#" ><font style="color:#fff;font-size:20px">推荐明细</font></a></span>
	   			</div>
	   			<hr style="height:1px;border:none;border-top:1px solid #F3F1EE;" />
	   			<div style="font-size:20px;font-family:'宋体';margin-left:5%;"><font style="color:#FA9600;font-size:20px;font-family:'宋体';margin-left:1%">
                 <div class="repayment" >
                	<table class="table table-hover table-h" style="max-width:150%;width:105%">
                    <thead>
                      <tr>
                        <th style="width:20%;text-align:left;text-indent:5px;font-size:14px;height:20px">投资人</th>
                        <th style="width:18%;text-align: center;font-size:14px;height:20px">投资金额</th>
                        <th style="width:20%;text-align: center;font-size:14px;height:20px">投资期限</th>
                        <th style="width:25%;text-align: center;font-size:14px;height:20px">投资时间</th>
                      </tr>
                      
                    </thead>
                    <tbody id="datas02"></tbody>
                  </table>
                  <div id="Pagination" class="pagination pagination-right" style="margin-bottom: 70px;"></div>
				</div>
			</font>
			</div>
		  </div>
		</div>
			
<div class="bottom" >
		<a class="page" href="${base}/index.do">
		<img src="${base}/images/page.png"><span style="margin-top: 4px;">主页</span>	</a>
		<a class="lnd" href="${base}/userIndex/index.do">
		<img src="${base}/images/lnd.png"><span style="margin-top: 4px;">账户</span></a>
		<a class="out" id="exitBtn" href="${base}/loginAction/logout.do">
		<img src="${base}/images/out.png"><span style="margin-top: 4px;">退出</span></a>
</div>
<script type="text/javascript">


var currentindex=1;
var itemPerPage=10;//每页显示数据条数
// alert("33");
		$(document).ready(function(){
			ajaxQuery(1,true);
		});

	function ajaxQuery(requestPage,isInit){
// 		alert("11");
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
			        	row.append($("<td style=\"text-align:left;\">" + n.CUST_NAME+"</td>"));
			        	row.append($("<td style=\"text-align:center\">" + n.CR_AMT + "</td>"));
			        	row.append($("<td style=\"text-align:center\">" + n.LOAN_TERM + "</td>"));
			        	row.append($("<td style=\"text-align:center\">" + n.DATE + "</td>"));
			        	row.appendTo("#datas02");
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
// 		alert("1");
		if(!isFistLoad1){
// 			alert("2");
			ajaxQuery(page_index+1,false);
		}
		isFistLoad1=false;
        return false;
    }


</script>
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
	</body>
	
</html>
