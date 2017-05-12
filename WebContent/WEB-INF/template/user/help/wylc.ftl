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
    <div class="aboutus" style="background-color:#f2f2f2;padding-top:5px;">
        <div class="con" style="border:0px;">
            <div class="left_menu" style="margin-left:6%;margin-right:20px;width:140px;background-color:#f2f2f2;">
                <ul id="all">
                    <li lid="one"><a href="${base}/helpManager/help.do">名词解释</a></li>
                    <li lid="two"><a href="${base}/helpManager/ptjs.do" target="_self">平台介绍</a></li>
                    <li lid="three"><a href="${base}/helpManager/wyjk.do" target="_self">我要借款</a></li>
                    <li lid="four" class="current"><a href="${base}/helpManager/wylc.do" target="_self">我要理财</a><img style="display:none;" src="${base}/platform/img/aboutusbk.png"/></li>
                    <li lid="five"><a href="${base}/helpManager/lxhfy.do" target="_self">利息和费用</a></li>
                    <li lid="six"><a href="${base}/helpManager/zxzh.do" target="_self">世纪创投账户</a></li>
                </ul>
            </div>
            <div class="right_con" id="four" style="width:78%;float:right;margin-top:10px; height:auto;">
                <div class="text">
                    <h2 class="about-title">我要理财</h2>
                    <div id="contn">
                        <div class="contnall">
                            <span>1.投标前需要注意哪些事项？</span>
                            <div class="contninfo"><p>&nbsp;&nbsp;（1）投标前认真阅读该笔借款的详细信息（包括借款金额，利息率，借款期限、借款者信用等级等），以确定您所要投的标符合您的风险承受能力和所要求的投资回报率。</p>
                                <p>&nbsp;&nbsp;（2）投标前应知道若您所投标的借款人发生违约，违约损失由投资该标的所有理财人共同承担。</p>
                                <p>&nbsp;&nbsp;（3）投标前请核实自己将要理财的金额，确认无误后再进行操作。</p></div>
                        </div>

                        <div class="contnall">
                            <span>2.如何分散投标？</span>
                            <div class="contninfo"><p>&nbsp;&nbsp;（1）尽量进行分散投资，这样可降低单一借款人违约对投资收益的影响。例如您可以把5000元借给10个借款人。</p>
                               <p>&nbsp;&nbsp;（2）在同等收益的情况下投标给信用等级较高的借款人。</p></div>
                        </div>

                        <div class="contnall">
                            <span>3.投标后是否可以取消？</span>
                            <div class="contninfo">您在投标后不允许取消。若此标满标并放款后，您账户上的投标金额自动将转入该标借款人账户。若此标流标，则您账户上的投标金额自动转为用户可用金额。</div>
                        </div>
                        <div class="contnall">
                            <span>4.如何收取还款？</span>
                            <div class="contninfo">借款人在规定的还款时间内将钱充值到与世纪创投合作的第三方支付平台上，世纪创投系统会发送邮件通知所有理财人借款人已成功还款。理财人可按个人需求选择提现或继续投资。</div>
                        </div>
                        <div class="contnall">
                            <span>5.借款人提前还款怎么办？</span>
                            <div class="contninfo">借款人可以提前偿还借款，如果借款人提前偿还全部借款，借款人还得支付剩余的利息。</div>
                        </div>
                        <div class="contnall">
                            <span>6.借款人逾期还款怎么办？</span>
                            <div class="contninfo">
                            <p>&nbsp;&nbsp;（1）如借入方未按本协议约定支付利息的，借入方应当按照累计预期利息、逾期天数向贷出方支付违约金，计算公式如下：违约金=（（本金*年利率）/365）*3*逾期天数</p>
                               <p>&nbsp;&nbsp;（2）如借款到期后，借入方未按本协议约定偿还本金，则借入方应当向贷出方支付借款本金10%的违约金。</p>
                                <p>&nbsp;&nbsp;（3）如管理方代为支付借款本息，管理方有权按照前述约定向借入方主张违约责任，且违约金由管理方所有。</p></div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
<#include "/common/footer.ftl">
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#contn").find("span").click(function(){
		
		if($(this).nextAll().is(":visible")==false){//表示下面所有的span都不可见的话
			$(this).attr("style","color:#00a8e8;"); //点击的这个就赋予颜色
			$(this).nextAll().show();               //同时让其显示     
		}else{
			$(this).attr("style","");              //样式就为空
			$(this).nextAll().hide();              //让其隐藏
		}
		
	});

	//$(".left_menu").find("li").click(function(){
	//$(".left_menu").find("li").removeClass("current");
	//$(this).addClass("current");
	//});
});
</script>
</body>
</html>