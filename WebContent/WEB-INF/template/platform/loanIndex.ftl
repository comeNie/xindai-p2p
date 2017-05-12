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
<#include "/common/header.ftl">
<div class="wapper">
	<div class="main">
        <div class="product_index">
        	<div class="product">
            	<div class="left">
                    <div class="hd">
                        <div class="img">
                            <a href="javascript:void(0);"><img src="${base}/platform/img/win_01.png"/></a>
                        </div>
                        <div class="info">
                            <h2>投资散标</h2>

                            <p>平台提供抵押标、担保标、信用标等多类产品，用户根据审核后的信息，自选合适的借款标的，构建符合个人意愿的投资组合。</p>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <div class="con">
                        <p>标的年利率区间12%-24%</p>
                        <p>投资标的适用本息保障计划</p>
                        <p>期限灵活，1-36个月</p>
                        <p>50元起投，可小额多笔投资，分散风险</p>
                       <#-- <p>债权持有90天以上，可进行转让</p>-->
                        <#--<p><span>理财收益计算</span></p>-->
                    </div>
                    <div class="button_div">
                      <a class="ui-button ui-button-blue ui-button-mid" href="${base}/loan/list.do">投标</a>
                    </div>
                </div>
                <div class="right">
                    <div class="hd">
                        <div class="img">
                            <a href="javascript:void(0);"><img src="${base}/platform/img/win_02.png"/></a>
                        </div>
                        <div class="info">
                            <h2>购买债权</h2>

                            <p>平台提供债权转让功能，用户可自行转让资产组合中符合条件的债权，也可购买其他用户转让的债权，从而获得接手奖金和借款标的后续收益。</p>
                        </div>
                    </div>
                    
                    <div class="clear"></div>
                    <div class="con">
                        <p>提供转让功能，增加资金流动性</p>
                        <p>债权转让次数不限</p>
                        <p>购买债权，可获得接手奖金</p>
                        <p>无投资门槛</p>
                       <#-- <p>债权持有90天以上，可进行转让</p>-->
                        <#--<p><span>理财收益计算</span></p>-->
                    </div>
                    <div class="button_div">
                      <a class="ui-button ui-button-yellow ui-button-mid" href="${base}/transfer/list.do">购买</a>
                    </div>
                </div>
                    
        </div>
    </div>
</div>
<#include "/common/footer.ftl">
</body>
</html>