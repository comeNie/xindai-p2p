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
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
        <div class="user_panel">
        	<#include "/common/left.ftl">
            <div class="right">
               <div class="tab_table_N creditor auto_css">
                <div class="nav">
                    <ul id="myCreditorNav">
                        <li id="tab1" class="current"><a href="javascript:void(0);">我关注的好友</a></li>
                        <li id="tab2"><a href="javascript:void(0);">添加好友</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
					<#include "/user/index/myFriend01.ftl">
					<#include "/user/index/myFriend02.ftl">
					
					<div id="loading" class="load" style="display:none">
        	 			<p>正在加载数据....</p>
        	  		</div>
					<div id="nodata" class="load" style="display:none">
						<p>暂无数据</p>
					</div>			
        		</div>
        <div class="clear"></div>        
    </div>
</div>	
<#include "/common/footer.ftl">
<script type="text/javascript">
//<![CDATA[

var currentindex=1;
var itemPerPage = 9;//每页显示数据条数

$(document).ready(function(){
	ajaxQuery1(0, true);
	changeTab();// 加载页签切换效果
});

function changeTab() {
   	$("#myCreditorNav li").click(function () {
		var liId = $(this).attr('id');
		$(this).addClass("current").siblings().removeClass("current");
		$("div[class='repayment']").hide();
		$("#myFriend_" + liId).show();
		if(liId=="tab1"){
			ajaxQuery1(0, true);
		}else{
			ajaxQuery2(0, true);
		}
		
	});
}


function guanzhu(friendId,friendName){
	if (confirm("确认要关注【" + friendName + "】吗？")) {
    	$.ajax({
				type:"POST",
				url:"ajaxAddFriend.do",
				dataType:"json",
				data:"friendId=" + friendId,
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
						$("#_" + friendId + "_p").html("已关注");
					}
					showDialogNew(true,data.reMes,'S','提醒信息');
					ajaxQuery2(0,true);
				}
				
			});
    }
}	
 	
	function ajaxQuery1(requestPage,isInit){
		var searchTxt = $('#friendName').val();
		$.ajax({
				type:"POST",
				url:"ajaxQuerymyFriend.do",
				dataType:"json",
				data:"requestPage=" + requestPage + "&pageSize=" + itemPerPage + "&friendName=" + searchTxt,
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
					$('#more1').remove();
					if( requestPage==0 ){
						$("#friendDiv").html("");
					}
					if( data.list==null ){
						
					}else{
						var divarr = new Array();
				        var rowIndex = 0;
				        $.each(data.list, function(i, n){
				        	var rowDiv;
				        	var aObj;
				            if(i%3==0){
				            	rowDiv = $('<div></div>');
								rowDiv.addClass("row");	
								rowDiv.attr("id","ind_" + rowIndex);							
								divarr[rowIndex] = rowDiv;
								rowIndex++;
								
				            }else{
				            	rowDiv = divarr[rowIndex-1];
				            }
				            aObj = $('<a></a>');
				            aObj.attr("href","#");
				        	var row = $('<div></div>');  
				        	row.addClass("item");

                            var html = "<div class=\"left\"><img src=\"" + checkPhoto(n.friendPhoto) + "\" align=\"absmiddle\"/></div>" +
                                    "<div class=\"right\">" +
                                    "<p>" + n.friendName + "</p>" +
                                    "<p><a href=\"javascript:void(0);\" onclick=\"cancelGuanzhu('" + n.id + "','" + n.friendName + "')\">取消关注</a></p><br/>" +
                                    "<p><a href=\"javascript:void(0);\" onclick=\"showSendMsgDiv('" + n.friendId + "','" + n.friendName + "')\">发送</a></p>" +
                                    "</div>";
                            row.append(html);
				        	
				        	/*row.append($("<div class=\"left\"><img src=\"" + checkPhoto(n.friendPhoto) + "\" align=\"absmiddle\"/></div>"));
                            row.append($("<div class=\"right\">"));
                            row.append($("<p>" + n.friendName + "</p>"));
                            row.append($("<p><a href=\"javascript:void(0);\" onclick=\"cancelGuanzhu('" + n.id + "','" + n.friendName + "')\">取消关注</a></p><br/>"));
                            row.append($("<p><a href=\"javascript:void(0);\" onclick=\"showSendMsgDiv('" + n.friendId + "','" + n.friendName + "')\">发送</a></p>"));
                            row.append($("</div>"));*/
                            
                            row.appendTo(aObj);
				        	aObj.appendTo(rowDiv);
				        	
		                });
		                
		                for(i=0;i<divarr.length;i++){
		                	divarr[i].appendTo($("#friendDiv"));
		                	$('<hr class="line">').appendTo($("#friendDiv"));
		                }
		                
		                if( data.list.length==itemPerPage ){
		                	$("<div id=\"more1\" class=\"more\"><a href=\"javascript:void(0);\" onclick=\"javascript:ajaxQuery1("+(requestPage+1) +",true)\">更多好友</a></div>").appendTo($("#friendDiv"));
		                }			
					}
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}

//加载信息
	function ajaxQuery2(requestPage,isInit){
		var searchTxt = $('#strangerName').val();		
		$.ajax({
				type:"POST",
				url:"ajaxQuerymyStranger.do",
				dataType:"json",
				data:"requestPage=" + requestPage + "&pageSize=" + itemPerPage + "&friendName=" + searchTxt,
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
					$('#more2').remove();
					if( requestPage==0 ){
						$("#StrangerDiv").html("");
					}
					if( data.list==null ){
						
					}else{
						var divarr = new Array();
				        var rowIndex = 0;
				        $.each(data.list, function(i, n){
				        	var rowDiv;
				        	var aObj;
				            if(i%3==0){
				            	rowDiv = $('<div></div>');
								rowDiv.addClass("row");	
								rowDiv.attr("id","uind_" + rowIndex);							
								divarr[rowIndex] = rowDiv;
								rowIndex++;
								
				            }else{
				            	rowDiv = divarr[rowIndex-1];
				            }
				            aObj = $('<a></a>');
				            aObj.attr("href","#");
				        	var row = $('<div></div>');  
				        	row.addClass("item");


                            var html = "<div class=\"left\"><img src=\"" + checkPhoto(n.friendPhoto) + "\" align=\"absmiddle\"/></div>" +
                                    "<div class=\"right\">" +
                                    "<p>" + n.friendName + "</p>" +
                                    "<p id=\"_" + n.friendId + "_p\"><a href=\"javascript:void(0);\" onclick=\"guanzhu('" + n.friendId + "','" + n.friendName + "')\">关注</a></p>" +
                                    "</div>";
                            row.append(html);
				        	
				        	/*row.append($("<div class=\"left\"><img src=\"" + checkPhoto(n.friendPhoto) + "\" align=\"absmiddle\"/></div>"));
                            row.append($("<div class=\"right\">"));
                            row.append($("        <p>" + n.friendName + "</p>"));
                            row.append($("        <p id=\"_" + n.friendId + "_p\" style=\"color:blue;font-size:10px;\"><a href=\"javascript:void(0);\" onclick=\"guanzhu('" + n.friendId + "','" + n.friendName + "')\">关注</a></p>"));
                            row.append($("</div>"));*/
				        	
							row.appendTo(aObj);
				        	aObj.appendTo(rowDiv);
				        	
		                });
		                
		                for(i=0;i<divarr.length;i++){
		                	divarr[i].appendTo($("#StrangerDiv"));
		                	$('<hr class="line">').appendTo($("#StrangerDiv"));
		                }
		                if( data.list.length==itemPerPage ){
		                	$("<div  id=\"more2\" class=\"more\"><a href=\"javascript:void(0);\" onclick=\"javascript:ajaxQuery2("+(requestPage+1) +",true)\">更多好友</a></div>").appendTo($("#StrangerDiv"));
		                }
						
					}
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}
		
		

function cancelGuanzhu(friendId,friendName){
	if (confirm("确认要取消关注【" + friendName + "】吗？")) {
    	$.ajax({
				type:"POST",
				url:"ajaxCancelFriend.do",
				dataType:"json",
				data:"friendId=" + friendId,
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
					showDialogNew(true,data.reMes,'S','提醒信息');
					ajaxQuery1(0, true);
				}
			});
    }
}



function showSendMsgDiv(friendId,friendName){
	var arr = new Array();
	var obj1 = new HtmlObj("p",null,"发送到：","recUserName","recUserName",friendName,"");
	var obj2 = new HtmlObj("input","hidden",null,"recUserId","recUserId",friendId,"");
	var obj3 = new HtmlObj("input","text","标题：","msgTitle","msgTitle","","data-rule='标题:required;'");
	var obj4 = new HtmlObj("textarea",null,"信息内容：","msgContent","msgContent","","data-rule='信息内容:required;'");
	arr[0] = obj1;
	arr[1] = obj2;
	arr[2] = obj3;
	arr[3] = obj4;
	
	var arrStr = converToHtml(arr,"send(formStr);");
	dialog({
			 title:'发送站内信',
			 content:arrStr
	 }).showModal();
}

 
 
function send(formStr){	
	$.ajax({
				type:"POST",
				url:"../userMessage/ajaxSendMsg.do",
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
					if(data.status=="1"){
						showDialogNew(true,data.reMes,'S','提醒信息');
					}else{
						showDialogNew(true,data.reMes,'E','提醒信息');
					}
				}
			});
}


function checkPhoto(imgUrl){
	if( imgUrl=='myfile/noface.jpg' || imgUrl== null || imgUrl == ""){
		return '${base}/platform/img/uer_img.gif';
	}else{
		return imgUrl;
	}
}
 //]]>
</script>
</body>
</html>