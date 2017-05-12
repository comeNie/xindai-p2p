/**
 * 弹出首页弹出框
 */
function newWindows(container,src,title,width,height){	
	title = title || "";
	width = width || 700;
	height = height || 300;
	
	var id = "win_"+Math.random();
	var tip = $("<div class='tip'></div>");
	tip.css("width",width).css("height",height);
	tip.attr("id",id);
	var html = "<div class='tiptop'><span>"+title+"</span><a></a></div>"
    +"<div class='tipinfo' style='padding:0px;margin:0px; height:100%;'>"
    +"<iframe scrolling=\"auto\" src=\""+src+"\" frameborder=\"0\" style=\"width: 100%; height: 100%;\"> </iframe>"
    +"<div class=\"tipbtn\">"
    +"<input type=\"button\"  class=\"sure\" value=\"确定\" />&nbsp;"
    +"<input type=\"button\"  class=\"cancel\" value=\"取消\" />"
    +"</div>";
	tip.append(html);
    container.append(tip);
    //$("\"#"+id+"\"").fadeIn(200);
    $(".tip").fadeIn(200);
}

/**
 * 弹出首页弹出框
 */
function showWindow(src,title,width,height){	
	title = title || "后台管理";
	width = width || 500;
	height = height || 300;
	if(src.indexOf("?") > -1){
		src = src+"&randomid="+Math.random();
	}else{
		src = src+"?randomid="+Math.random();
	}
	$("#mainFrameHidden").attr("src",src);
	$("#mainDialogTitle").text(title);
	//mainDialog.width(width);
	//mainDialog.height(height);
	mainDialog.show();
}

function closeParMainDialog(){
	parent.closeMainDialog();
}
function refreshParent(){
	parent.refresh();
}
function refresh(){
	$(".refresh").click();
}
function closeMainDialog(){
	$("#mainFrameHidden").attr("src","");
	mainDialog.hide();
}

$(document).bind('ajaxError', function(event, request, settings, exception) {
	//alert("ajaxError"+request.status);
	if (request.status == 403) {
		//do whatever you wanted may be show a popup or just redirect
		alert("会话超时，请重新登录！");
		window.location = '#{request.contextPath}/';
	}
});
