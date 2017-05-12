<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>世纪创投</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<link href="${base}/css/styleApp.css" rel="stylesheet" type="text/css">
<link href="${base}/css/listApp.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${base}/platform/bootstrap/css/style.css">  
<script src="${base}/js/jqueryAPP-1.8.3.min.js" type="text/javascript"></script> 
<script src="${base}/js/styleApp.js" type="text/javascript"></script> 
<script src="${base}/js/jQuery.fontFlexApp.js"  type="text/javascript"></script>
<style> 
.subject{float:left;width:95%;padding:0px 2.5%;height:auto;background:#f9f9f9;}
@media screen and (max-width:367px){
	 .upTo{float:right;margin-top: -25px;}
	 }
 @media screen and (min-width:356px) and (max-width:367px){
	 .upTo{float:right;margin-top:0px;}
	 }
</style>

</head>

<body>
<div style="min-width:100%">
<div class="logo">
	<img src="${base}/platform/img/logo.gif" />		
</div>
<div class="head_title"style="height:40px">
	<a href="javascript:history.back(-1)" style="margin-top:-10px"><h1 style="width:30px;height:40px"> <</h1></a>
	<p style="margin-top: 6px;margin-left: -35%;">投资列表</p>
</div>

            <div class="subject subject_1" id="apend">
	<a href="${base}/loan/detail.do?loanApproveId=33" class="su_a"> </a>
    
    <div class="subject subject_1" id="datas" ></div>
<!--     <button class="button" style="margin-bottom: 100px;margin-left:26%;width:50%;height:60px"><a href="${base}/loan/list.do"><font style="color:#ffffff;">查看全部理财</font></a></button>    -->
    <div style="margin-bottom:100px">&nbsp;</div>
    
</div>
 
</div>
	<div class="bottom">
		<a class="page" href="${base}/index.do">
		<img src="${base}/images/page.png"><span>主页</span>	</a>
		<a class="lnd" href="${base}/userIndex/index.do">
		<img src="${base}/images/lnd.png"><span>账户</span></a>
		<a class="out" id="exitBtn" href="${base}/loginAction/logout.do">
		<img src="${base}/images/out.png"><span>退出</span></a>
	</div>
</body>
<!-- 百分比 -->
<script type="text/javascript"> 

   $(function() {
                $('.list_nav span').fontFlex(18, 26, 26);
                $('.rel_1').fontFlex(12, 12, 16);
                $('.login span').fontFlex(18, 26, 26);
                $('.li span').fontFlex(16, 20, 24)
                
            });
$(function() {

    $('.circle').each(function(index, el) {
        var num = $(this).find('span').text() * 3.6;
        if (num<=180) {
            $(this).find('.right').css('transform', "rotate(" + num + "deg)");
        } else {
            $(this).find('.right').css('transform', "rotate(180deg)");
            $(this).find('.left').css('transform', "rotate(" + (num - 180) + "deg)");
        };
    });

});
// var bullets = document.getElementById('position').getElementsByTagName('li');

// var banner = Swipe(document.getElementById('mySwipe'), {
// 	auto: 4000, 
// 	continuous: true,
// 	disableScroll:false,
// 	callback: function(pos) {
// 		var i = bullets.length;
// 		while (i--) {
// 			bullets[i].className = ' ';
// 		}
// 		bullets[pos].className = 'cur';
// 	}
// })


</script>

<script type="text/javascript" charset="utf-8">

$(document).ready(function() {
    //每页显示数据条数
    var itemPerPage=100000000;
	// 查询列表
	ajaxQuery(1,true);
	friendLink();

	function ajaxQuery(requestPage,isInit){
		var loanType = "";
		var term  = "";
		var rate  =  "";
		//alert("rate=="+rate);
		var creditLevel  = "";
		
		
		$('tr[id^=ready]').hide();
		$("#nodata").hide();
		$("#loading").hide();
		$.ajax({
			type:"POST",
			url:"ajaxQuery.do",
			dataType:"json",
			data:"loanType="+loanType+"&term="+term+"&rate="+rate+"&creditLevel="+creditLevel+"&requestPage="+requestPage+"&pageSize="+itemPerPage,
			complete:function(){
			},
			success:function(data) {
				$('tr[id^=ready]').remove();
				
				if(data.list == null){
				   $("#loading").hide();
				   $("#nodata").show();
				}
		        $.each(data.list, function(i, n){
// 					alert(fmoney(n.loanAmt,2));
		        	 var row = $("<ul class=\"subject_jing\"></ul>");
			        	row.attr("id","ready"+i);//改变绑定好数据的行的id	
			        	
			        	row.append($("<li class=\" li \"style=\"padding:5px 20px;\"><img style=\"position:absolute;top:5px;width:22px\"  src=\"${base}/images/licai"+n.loanTypeCd+".png\"/>&nbsp;<a style=\"margin-left:30px\" href=\"${base}/loan/detail.do?loanApproveId="+n.loanApproveId+"\" title=\""+n.loanName+"\"><div style=\"width:120px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;\"><font style=\"color:#333333\">"+n.loanName+"</font></div></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<div style=\"float:right\" class=\"upTo\">期限:<span><i>"+n.loanTerm+"</i>个月</span></li></li>"));
			        	row.append($("<li class=\"\" style=\"line-height:15px;margin-left:6%;margin-right:4%;margin-top:5px\"><a href=\"${base}/loan/detail.do?loanApproveId="+n.loanApproveId+"\" title=\""+n.loanName+"\"><font style=\"font-size:10px;color:#ffb23f\">"+n.remark+"</font></a></li>"));
			        	row.append($("<li class=\"li1\"><span>年化收益</span>"+n.loanRate+"%</li>"));
	                    row.append($("<li class=\"li1\"><i>还款方式:&nbsp;&nbsp;<i>"+n.repayTypeCdStr+"</li>"));
// 	                    row.append($("<li class=\"li1\"><span>年化收益</span>"+n.loanRate+"%</li>"));
// 	                    row.append($("<td style=\"text-align:center;\"><div class=\"progress-circle\" style=\"background-position:-"+ (n.completeness*54) +"px 0px;\">"+n.completeness+"%</div></td>"));
	                    row.append($("<li class=\"li1\">金额："+n.loanAmt+"</li>"));

						row.append($("<li class=\"circle\" style=\"text-align:center;\"><div class=\"progress-circle\" style=\"background-position:-"+ (n.completenessRound*54) +"px 0px;\">"+n.completeness+"%</div></li>"));
						 row.append($("<li class=\"circle\"><a href='${base}/loan/detail.do?loanApproveId=" + n.loanApproveId + "'><button style=\"margin-top: 100%;background:#ffb23f;color:#fff;height:20px;width:50px;border-radius:5px \"><span>投标</span></button></a></li>"));
						//row.append($("<div class=\"circle\"><div class=\"pie_left\"><div class=\"left\"></div></div><div class=\"pie_right\"><div class=\"right\"></div></div><div class=\"mask\"><span>"+n.completeness+"</span>%</div></div>"));
	                    row.appendTo("#datas");//添加到模板的容器中

                    
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
		ajaxQuery(page_index+1,false);
	}
	isFistLoad1=false;
    return false;
    
}



});


</script>


</html>