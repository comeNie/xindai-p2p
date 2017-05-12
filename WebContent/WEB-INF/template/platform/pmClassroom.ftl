<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>世纪创投</title>

<meta name="description" content="">
<meta name="author" content="">
<#include "/common/resource.ftl">
</head>
<body>
<div  style="min-width:1349px;max-width:1400px;margin:0 auto"">
<div style="margin:0 auto;">
<#include "/common/header_d.ftl">
</div>
</div>
<div class="wapper">
	<div class="main">
		<div class="new-info-page" style="margin-top:30px;margin-bottom:15px;">
        	<div class="new-info-box-one" style="margin:0px;">
				<div class="new-title" id="all-nav-box">
					<div vid="load-info-one" class="new-tile-navon">我要投资</div>
					<div vid="load-info-two">我要借款</div>
					<div  vid="load-info-three">理财安全</div>
					<div class="clear"></div>	
				</div>
			</div>
			
			<#include "platform/wantFinancial.ftl">
			<#include "platform/financialSecurity.ftl">
			<#include "platform/borrowMoney.ftl">

        </div>
        
    </div>
</div>	      

<#include "/common/footer.ftl">

<script type="text/javascript">
$(document).ready(function() {
	$("#all-nav-box div").click(function(){
	     var divVid = $(this).attr("vid");
	     $(this).addClass("new-tile-navon").siblings().removeClass("new-tile-navon");
	     if(divVid == 'load-info-one'){
	       $("#load-info-one").show();
	       $("#load-info-two").hide();
	       $("#load-info-three").hide();
	     }
	     
	     if(divVid == 'load-info-two'){
	       $("#load-info-two").show();
	       $("#load-info-one").hide();
	       $("#load-info-three").hide();
	     }
	     
	     if(divVid == 'load-info-three'){
	       $("#load-info-three").show();
	       $("#load-info-one").hide();
	       $("#load-info-two").hide();
	     }
	});
});	
</script>
</body>
</html>