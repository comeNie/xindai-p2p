<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户中心-首页-sjct888.com</title>

<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=1349">
<#include "/common/resource.ftl">
</head>
<body>
<div style="min-width:1349px;margin:0 auto;max-width:1400px;">
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
        <div class="user_panel">
        	<#include "/common/left.ftl">
        	<input type="hidden" id="tabIndex" name="tabIndex" value="<#if tabIndex??>${tabIndex}<#else>tab1</#if>"/>
            <div class="right">
               <div class="tab_table_N creditor auto_css">
                <div class="nav">
                    <ul id="myRechargeNav">
                    	<li id="tab1" class="current"><a href="javascript:void(0);">我要充值</a></li>
<!--                         <li id="tab2"><a href="javascript:void(0);">充值卡</a></li> -->
                    </ul>
                </div>
                <div class="clear"></div>
                <#include "/user/fund/myRecharge01.ftl">
<!--                 <#include "/user/fund/myRecharge02.ftl"> -->
                	<div id="loading" class="load" style="display:none">
        	 			<p>正在提交数据....</p>
        	  		</div>
					<div id="nodata" class="load" style="display:none">
						<p>暂无数据</p>
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
$(document).ready(function(){
	changeTab();// 加载页签切换效果
	var $tabIndex =  $("#tabIndex").val();
	$("#"+$tabIndex).addClass("current").siblings().removeClass("current");
	$("div[class='myRecharge']").hide();
	$("#myRecharge_" + $tabIndex).show();
}); 
function changeTab() {
   	$("#myRechargeNav li").click(function () {
		var liId = $(this).attr('id');
		$(this).addClass("current").siblings().removeClass("current");
		$("div[class='myRecharge']").hide();
		
		$("#myRecharge_" + liId).show();
	});
}

 //]]>
</script>
</body>
</html>