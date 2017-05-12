<!DOCTYPE HTML>


<html>

<!-- Mirrored from www.qc288.com/shift/projectDetail.do?id=505 by HTTrack Website Copier/3.x [XR&CO'2014], Sun, 06 Sep 2015 08:55:46 GMT -->
<!-- Added by HTTrack --><meta http-equiv="content-type" content="text/html;charset=UTF-8" /><!-- /Added by HTTrack -->
<head>
<title>世纪创投</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<link href="${base}/css/styleApp.css" rel="stylesheet" type="text/css">
<link href="${base}/css/ProductApp.css" rel="stylesheet" type="text/css">
<script src="${base}/js/jqueryApp-1.8.3.min.js" type="text/javascript"></script> 
<script src="${base}/js/styleApp.js" type="text/javascript"></script> 
<script src="${base}/js/jQuery.fontFlexApp.js"  type="text/javascript"></script>
 <link rel="stylesheet" type="text/css" href="${base}/platform/bootstrap/css/ui-dialog.css">
<#include "/common/resource.ftl">
<link rel="stylesheet" type="text/css" href="${base}/platform/bootstrap/css/style.css">
</head>

<body>

<div class="logo">
	<img src="${base}/platform/img/logo.gif" />

</div>

<div class="head_title" style="height:40px">
	<a href="javascript:history.back(-1)" style="margin-top:-6px"> <</a>
<p style="margin-top: 10px;margin-left: 10%;">	${loanDetailVO.loanName}</p>

</div>

<div class="pro_capacity subject_zhuan" style=" height: 130px; ">


	<ul class="pro_Algorithm" style="width:100%">
		<li style="width:30%">
			<p>年化收益</p>
			<p class="p" style="">${loanDetailVO.loanRate}%</p>
		</li>
<!-- 		<span></span> -->
        <li style="width:36%">
			<p >
				标的总额:<p class="p">${loanDetailVO.loanAmt?string(',##0.00')}</p><img src="${base}/platform/img/repayment01.png">
			</p>
		</li>
		<li style="width:30%">
			<p>期限</p>
			<p class="p">${loanDetailVO.loanTerm}月</p>
		</li>
	
	</ul>
</div>
						
<input type="hidden" name="verify" value="" id="verify"/>

<div class="pro_Algorithm2"style="margin-bottom: 70px;">
	<ul>
		<li class="li">
		<i><img src="${base}/images/licai${loanDetailVO.loanTypeCd}.png""></i>
		&nbsp;&nbsp;&nbsp;&nbsp;${loanDetailVO.loanName}<font style="color:#ff23bf;float:right;margin-right:5px">
		<#if collateralInfoVO.collateralAtt1??>
		<a href="${base}/loan/mobileInfoDisclosure.do?custId=${loanDetailVO.custId}"><font style="color:#ffb23f ">查看信息披露&nbsp;&nbsp;></font></a>
		<#else>
		<a onclick="info()"><font style="color:#ffb23f ">信息披露查看&nbsp;&nbsp;></font></a>
		</#if>
		</li>
		<li><span>投资进度:</span><div class="ProgressBar" style="width:10%" ></div><span   style="color:#ed0000;width:115px;font-size:18px">${loanDetailVO.completeness}%</span></li>
		<li><span>还款方式:</span><@sysCodeVal codeType='repayTypeCd' codeValue='${loanDetailVO.repayTypeCd}' /></li>

			<#if loanRepayInfoVo.sumRepayAmt??>
			<li><span>待还本息(元)</span><em>￥${loanRepayInfoVo.sumRepayAmt?string(',##0.00')}</em></i></li>
			<#else>
			<li><span>待还本息(元)</span><em>￥0.00</em></i></li>
			</#if>
			<#if loanRepayInfoVo.restPeriods??>
			<li><span>剩余期数(月)</span><em>${loanRepayInfoVo.restPeriods}（月）</em></i></li>
			<#else>
			<li><span>剩余期数(月)</span><em>0（月）</em></i></li>
			</#if>
			<#if loanRepayInfoVo.nextRepayDate??>
			<li><span>下一个还款日</span><em>${loanRepayInfoVo.nextRepayDate?string('yyyy-MM-dd')}</em></i></li>
			<#else>
			<li><span>下一个还款日</span><em></em></i></li>
			</#if>
			
		         
                   
		
<!-- 	<li><span>最小投标金额:</span>￥500.00元<input type="hidden" value="500.00" id="minmoney" /></li> -->
	</ul>
</div>

<input type="hidden" id="bid" value="505"/>
		<input type="hidden" id="idStr" value="505"/>
<div class="bottom">
		<a class="page" href="${base}/index.do">
		<img src="${base}/images/page.png"><span style="margin-top: 4px;">主页</span>	</a>
		<a class="lnd" href="${base}/userIndex/index.do">
		<img src="${base}/images/lnd.png"><span style="margin-top: 4px;">账户</span></a>
		<a class="out" id="exitBtn" href="${base}/loginAction/logout.do">
		<img src="${base}/images/out.png"><span style="margin-top: 4px;">退出</span></a>
	</div>


</body>
<script type="text/javascript">
	function info(){
		alert("暂无信息披露！");
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
    
	var wwid=$(".pro_capacity").width();
	wwid=wwid/2-34;
	$(".circle").attr("style","left:"+wwid+"px");
$(window).resize(function () {
	var wwid=$(".pro_capacity").width();
	wwid=wwid/2-34;
	$(".circle").attr("style","left:"+wwid+"px");
})
});

</script>

<script type="text/javascript">

 


$('#tender').validator({
	rules:{
	    multNumber:function(element,param,field){
	        return multNum(element.value,50) || '请输入50的倍数';
	   }
	},
	fields: {
			crAmt: '投资金额:required;integer[+0];range[0~${custAccountVO.accBal}];range[0~${loanDetailVO.tenderBalAmt}];multNumber;'
	}
});


$(document).ready(function() {
	$("#all-nav-box div").click(function () {
		var divVid = $(this).attr('vid');
		$(this).addClass("new-tile-navon").siblings().removeClass("new-tile-navon");
		//$("div[class='new-info-box-two']").hide();   //里头的class是指  这几个记录中都具有的class
		
		if(divVid == 'load-info-one'){   //借款人档案  不需要调用ajax
			$("#load-info-one").show();
			$("#load-info-two").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
		}
		
		if(divVid == 'load-info-two'){   //投标记录
			$("#load-info-two").show();
			$("#load-info-one").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
			ajaxQuery(divVid);
		}
		
		if(divVid == 'load-info-three'){  //还款记录
			$("#load-info-three").show();
			$("#load-info-one").hide();
			$("#load-info-two").hide();
			$("#load-info-four").hide();
			$("#load-info-five").hide();
			ajaxQuery(divVid);
		}
		
		if(divVid == 'load-info-four'){   //转让记录
			$("#load-info-four").show();
			$("#load-info-one").hide();
			$("#load-info-two").hide();
			$("#load-info-three").hide();
			$("#load-info-five").hide();
			ajaxQuery(divVid);
		}
		 
		if(divVid == 'load-info-five'){   //留言板
			$("#load-info-five").show();
			$("#load-info-one").hide();
			$("#load-info-two").hide();
			$("#load-info-three").hide();
			$("#load-info-four").hide();
		    ajaxQuery(divVid);
		} 
	});
	//加载ajax记录
	function ajaxQuery(divVid){
		$("#tbjl_datas").html(""); 
		$("#hkjl_datas").html(""); 
		$("#zrjl_datas").html(""); 
		if(divVid == 'load-info-two'){
			$.ajax({
				type:"POST",
				url:"ajaxCreditorRight.do",
				dataType:"json",
				data:"approveStatusCd=" + "0" + "&loanApproveId=" + $("#loanApproveId").val(),
				complete:function() {
				},
				success:function(data) {
					 $.each(data.list, function(i, n){
					 	var row = $('<tr></tr>');
					 	row.append($("<td>" + (i+1) +"</td>")); 
					 	row.append($("<td>" + HidChar(n.userCode) +"</td>")); 
					 	row.append($("<td>" + fmoney(n.crAmt,2) +"</td>"));  
					 	row.append($("<td>" + n.tenderTypeCd +"</td>")); 
					 	row.append($("<td>" + timeStamp2String(n.sysCreateTime) +"</td>"));
					 	row.appendTo("#tbjl_datas");    
					 }); 
				},
				error:function(text) {
					//alert('请求后台出错.');
				} 
			});
		}else if(divVid == 'load-info-three'){
			$.ajax({
				type:"POST",
				url:"ajaxRepayPlanDetail.do",
				dataType:"json",
				data:"approveStatusCd=" + "0" + "&loanApproveId=" + $("#loanApproveId").val(),
				complete:function() {
				},
				success:function(data) {
					 $.each(data.list, function(i, n){
					 	var row = $('<tr></tr>');
					 	row.append($("<td>" + n.period +"</td>")); 
					 	row.append($("<td>" + n.repayplanDate +"</td>"));  
					 	row.append($("<td>" + fmoney(n.principalAmt,2) +"</td>")); 
					 	row.append($("<td>" + fmoney(n.interestAmt,2) +"</td>"));  
					 	row.append($("<td>" + n.repayStatusCd +"</td>"));
					 	row.appendTo("#hkjl_datas");    
					 }); 
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}else if(divVid == 'load-info-four'){
			$.ajax({
				type:"POST",
				url:"ajaxTranList.do",
				dataType:"json",
				data:"approveStatusCd=" + "0" + "&loanApproveId=" + $("#loanApproveId").val(),
				complete:function() {
				},
				success:function(data) {
					 $.each(data.list, function(i, n){
					 	var row = $('<tr></tr>');
					 	row.append($("<td>" + (i+1) +"</td>")); 
					 	row.append($("<td>" + HidChar(n.userCode) +"</td>")); 
					 	row.append($("<td>" + n.crCount +"</td>"));  
					 	row.append($("<td>" + fmoney(n.crAmt,2) +"</td>"));  
					 	//row.append($("<td>" + n.tenderTypeCd +"</td>")); 
					 	row.append($("<td>" + timeStamp2String(n.sysCreateTime) +"</td>"));  
					 	
					 	row.appendTo("#zrjl_datas");    
					 }); 
				},
				error:function(text) {
					// alert('请求后台出错.');
				} 
			});
		}
	}
});
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
	 $(function(){
			
			var zhi=$(".baizhi .wai i").html();
			var zhi1=100-zhi;
			$(".baizhi #aaa").animate({top:zhi1+"%"},600);
			
			$("body").append("<div class=\"bottom\"><a class=\"page\" href=\"${base}/index.do\"><img src=\"${base}/images/page.png\" /><span>主页</span>	</a><a class=\"lnd\" href=\"${base}/userIndex/index.do\"><img src=\"${base}/images/lnd.png\" /><span>账户</span></a><a class=\"out\" id=\"exitBtn\" href=\"${base}/loginAction/logout.do\"><img src=\"${base}/images/out.png\" /><span>退出</span></a></div>");
				$(".out").click(function(){
					 location.href='loginAction/logout.do';
				})
			})
</script>
</html>