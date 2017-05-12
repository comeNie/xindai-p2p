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
<div style="min-width:1349px">
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
        <div class="user_panel">
        	<#include "/common/left.ftl">
            <div class="right">
             <table class="table_W table_B">
                    <thead>
                      <tr>
                        <th width="33%">债权已赚金额</th>
                        <th width="34%">债权帐户资产</th>
                        <th width="33%">回收中的债权数量</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td align="left" width="33%"><em>0.00元</em></td>
                        <td align="left" width="34%">0.00元</td>
                        <td align="left" width="33%">0个</td>
                      </tr>
                      <tr>
                        <td colspan="3" class="left">利息收益0.00元</td>
                      </tr>
                      <tr>
                        <td colspan="3" class="left">债权转让盈亏0.00元</td>
                      </tr>
                    </tbody>
              </table>
               <div class="tab_table_N creditor">
                <div class="nav">
                    <ul>
                        <li  class="current"><a href="javascript:void(0);">回收中的债权</a></li>
                        <li><a href="javascript:void(0);">已结清的债权</a></li>
                        <li><a href="javascript:void(0);">投标中的债权</a></li>
                        <li><a href="javascript:void(0);">已转出的债权</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div class="repayment">
                    <table class="table table-hover">
                    <thead>
                      <tr>
                        <th>债权ID</th>
                        <th>原始投资金额</th>
                        <th>年利率</th>
                        <th>待收本金</th>
                        <th>月收本息</th>
                        <th>期数</th>
                        <th>下个月还款日状态</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    	<#list (myDebtList)! as list>
							<tr>
		                   		 <td><a href="javascript:void(0);">${list.creditorId}</td>
		                   		 <td>${list.crAmt}</td>
			                     <td>${list.loanRate}%</td>
			                     <td>${list.crAmt}元</td>
			                     <td>${list.crAmt}元</td>
			                     <td></td>
			                     <td></td>
			                     <td><a href="javascript:void(0);">债权转让</a></td>
							</tr>
						</#list>
                      <tr>
                        <td colspan="7" style="text-align:center; padding-top:100px; background:none;">没有回收的债权</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
       		 </div>
            </div>	
        </div>
        <div class="clear"></div>        
    </div>
</div>	
<#include "/common/footer.ftl">
</div>
<!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->

    <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
    <!--[if lte IE 6]>
    <script type="text/javascript" src="js/bootstrap-ie.js"></script>
    <![endif]-->
<script type="text/javascript">
//<![CDATA[
$(document).ready(function(){					   
$(".item1").hover(function(){$("#tit_fc1").slideDown("normal");	}, function() {$("#tit_fc1").slideUp("fast");});				
$(".item2").hover(function(){$("#tit_fc2").slideDown("normal");	}, function() {$("#tit_fc2").slideUp("fast");});
$(".item3").hover(function(){$("#tit_fc3").slideDown("normal");	}, function() {$("#tit_fc3").slideUp("fast");});
$(".item4").hover(function(){$("#tit_fc4").slideDown("normal");	}, function() {$("#tit_fc4").slideUp("fast");});
});			   
var currentindex=1;
$("#flashBg").css("background-color",$("#flash1").attr("name"));
function changeflash(i) {	
currentindex=i;
for (j=1;j<=5;j++){//此处的5代表你想要添加的幻灯片的数量与下面的5相呼应
	if (j==i) 
	{$("#flash"+j).fadeIn("normal");
	$("#flash"+j).css("display","block");
	$("#f"+j).removeClass();
	$("#f"+j).addClass("dq");
	$("#flashBg").css("background-color",$("#flash"+j).attr("name"));
	}
	else
	{$("#flash"+j).css("display","none");
	$("#f"+j).removeClass();
	$("#f"+j).addClass("no");}
}}
function startAm(){
timerID = setInterval("timer_tick()",8000);//8000代表间隔时间设置
}
function stopAm(){
clearInterval(timerID);
}
function timer_tick() {
    currentindex=currentindex>=5?1:currentindex+1;//此处的5代表幻灯片循环遍历的次数
	changeflash(currentindex);}
$(document).ready(function(){
$(".flash_bar div").mouseover(function(){stopAm();}).mouseout(function(){startAm();});
startAm();
});
 //]]>
</script>
</body>
</html>