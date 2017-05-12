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
<div style="min-width:1349px;margin:0 auto;max-width:1400px;">
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
        <div class="user_panel">
        	<#include "/common/left.ftl">
            <div class="right">
               <div class="tab_table_N creditor auto_css">
                <div class="nav">
                    <ul id="myLoanNav">
                        <li  class="current" id="tab1"><a href="javascript:void(0);" style="margin-top: -14px;">我的推广</a></li>
                        <li id="tab2"><a href="javascript:void(0);" style="margin-top: -14px;">推荐明细</a></li>
<!--                         <li id="tab3"><a href="javascript:void(0);" style="margin-top: -14px;">还款中借款</a></li> -->
<!--                         <li id="tab4"><a href="javascript:void(0);" style="margin-top: -14px;">已还清借款</a></li> -->
                    </ul>
                </div>
                <div class="clear"></div>
                 <#include "/user/message/messageManage01.ftl"> 
                 <#include "/user/message/messageManage02.ftl"> 
       		     <div id="loading" class="load" style="display:none">
	        	 	 <p>正在加载数据....</p>
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

var currentindex=1;
var itemPerPage=10;//每页显示数据条数

$(document).ready(function(){					   
	ajaxQuery1(1,true);
	changeTab();// 加载页签切换效果
});
function changeTab() {
   	$("#myLoanNav li").click(function () {
		var liId = $(this).attr('id');
		
		if(liId == 'tab1'){
		  ajaxQuery1(1,true);
		}
		
		
		if(liId == 'tab2'){
		  ajaxQuery2(1,true);
		}
	    if(liId == 'tab3'){
		  ajaxQuery3(1,true);
		}
		
		if(liId == 'tab4'){
		  ajaxQuery4(1,true);
		}
		$(this).addClass("current").siblings().removeClass("current");
		$("div[class='repayment']").hide();
		$("#messageManage_" + liId).show();
	});
}
 //]]>
</script>
</body>
</html>