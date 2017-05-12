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
<#include "/common/header_c.ftl">
<div class="wapper">
	<div class="main">
        <div class="hborrowmoney_panel">
 <#--       	<div class="left">
            	<div class="menu">
                		<img src="${base}/platform/img/tempimg001${productNo}.png" />
                </div>
            </div> -->
          <form id="goToLoanCreditForm" class="form" action="${base}/loan/applyLoan.do" method="post" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
            <div class="right">
               <div class="bor-mon-box">
					<div class="row bor-mon-inp">
						<label>标的名称</label>

                        <div class="inputwrap"><input type="text" name="loanName"
                                                       data-rule="标的名称:required;length[3~50];"/></div>
					</div>
					
		<#--			<div class="row bor-mon-sel">
						<label>职业身份</label><input type="hidden" name="jobStatusCd" class="selectuloneinp" />

                        <div class="inputwrap"><input onfocus="this.blur()" type="text" id="selectuloneinp" data-rule="职业身份:required;"/>
                            <img class="selectbyimg" vid="selectulone" src="${base}/platform/img/selbtnyellow.png"/>
                        </div>
						<ul class="select-ul" id="selectulone" >
						    <@sysCodeUl codeType='jobStatusCd'/>
						</ul>
					</div>
					<div class="row bor-mon-sel">
						<label>还款方式</label><input type="hidden" name="repayTypeCd"  class="selectultwoinp"/>

                        <div class="inputwrap"><input onfocus="this.blur()" type="text" id="selectultwoinp" data-rule="还款方式:required;"/>
                            <img class="selectbyimg" vid="selectultwo" src="${base}/platform/img/selbtnyellow.png"/>
                        </div>
						<ul class="select-ul" id="selectultwo" >
						    <@sysCodeUl codeType="repayTypeCd"/>
						</ul>
						<div class="hbor-tip"></div>  
					</div>  -->
					
					<div class="row bor-mon-inp">
						<label>期望年利率(%)</label>

                        <div class="inputwrap">
                       <input type="text"  name="expectLoanRate" name="expectLoanRate" data-rule="期望年利率:required;myfloor;" data-rule-myfloor="[/^\d{1,2}([.]\d{1,2})?$/, '请输入整数或者两位小数']"/>
                        </div>
					</div>
					<div class="row bor-mon-inp">
						<label>贷款金额</label>
						<#--
                        <div class="inputwrap"><input type="text" placeholder="剩余额度:${supAmt}元" name="applyAmt"/></div>
                        -->
                         <div class="inputwrap"><input type="text"  name="applyAmt"/></div>
					</div> 
					  					
					<div class="row bor-mon-inp">
						<label>贷款期限(月)</label>

                        <div class="inputwrap"><input type="text" name="loanTerm" placeholder="剩余期限:${loanTerm}月"
                                                       id="selectulthreeinp" /></div>
						<input type="hidden" name="termUnitCd" class="span3" value="1" />
					</div>
 
					<div class="row bor-mon-textarea">
						<label>借款用途</label>

                        <div class="inputwrap"><textarea value="" name="loanPurpose"
                                                          data-rule="借款用途:required;"></textarea></div>
					    <input type="hidden" name="productId" class="span3" value="${productNo}" />
					</div>
					<div class="row bor-mon-inp">
						<label>验证码</label>

                        <div class="inputwrap"><input class="bm-inp-v" type="text" value="" name="j_captcha"
                                                       data-rule="验证码:required;length[4];remote[get:ajaxCaptcha.do]"/></div>
                            <img class="bm-img-v" id="captachaImg" src="${base}/captcha.jpg"/>
                        <a id="refreshBtn"><img  class="bm-img-c"  src="${base}/platform/img/refresh.png"/></a>
                        <span class="msg-box n-right" for="j_captcha"></span>
					</div>
					
					<div class="bor-mon-submit">
					   <a href="javascript:void(0)" onclick="$('#goToLoanCreditForm').submit();">
						 <img src="${base}/platform/img/hbormonbtn.png" />
					  </a>
					</div> 
       		   </div>
            </div>	
           </form>
 
			<div class="clear"></div>
			<#--<div class="bor-mon-help">
				<div class="mon-help-box help-box1">
					<h2>抵押借款</h2>
					<div>
						<span>申请条件</span>
						<ul>
							<li>22-55周岁的中国公民</li>
							<li>在现单位工作满3个月</li>
							<li>月收入2000以上</li>
						</ul>
					</div>
					<hr class="linedotted">
					<div>
						<span>必要申请资料</span>
						<ul>
							<li>身份证</li>
							<li>劳动合同或在职证明</li>
							<li>近3个月工作卡银行流水</li>
						</ul>
					</div>
					<img src="${base}/platform/img/knowmore01.png" />
 
				</div>
				<div class="mon-help-box help-box2">
					<h2>担保借款</h2>
					<div>
						<span>申请条件</span>
						<ul>
							<li>22-55周岁的中国公民</li>
							<li>在现单位工作满3个月</li>
							<li>月收入2000以上</li>
						</ul>
					</div>
					<hr class="linedotted">
					<div>
						<span>必要申请资料</span>
						<ul>
							<li>身份证</li>
							<li>劳动合同或在职证明</li>
							<li>近3个月工作卡银行流水</li>
						</ul>
					</div>
					<img src="${base}/platform/img/knowmore02.png" />
				</div>
				<div class="mon-help-box help-box3">
					<h2>信用借款</h2>
					<div>
						<span>申请条件</span>
						<ul>
							<li>22-55周岁的中国公民</li>
							<li>在现单位工作满3个月</li>
							<li>月收入2000以上</li>
						</ul>
					</div>
					<hr class="linedotted">
					<div>
						<span>必要申请资料</span>
						<ul>
							<li>身份证</li>
							<li>劳动合同或在职证明</li>
							<li>近3个月工作卡银行流水</li>
						</ul>
					</div>
					<img src="${base}/platform/img/knowmore03.png" />
				</div>
			
			</div>-->
			
        </div>
    </div>
</div>	



<#include "/common/footer.ftl">
<script src="/platform/js/placeholders.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
   $(".bor-mon-inp").find("input").attr("style","color:#cccccc;");
$(".bor-mon-inp").find("input").attr("flagv","1");
$(".bor-mon-inp").find("input").focus(function(){$(this).attr("style","color:#66bde6;");if($(this).attr("flagv")=="1"){$(this).val("");$(this).attr("flagv","0")}});
//$(".bor-mon-box").find("input").blur(function(){$(this).attr("style","color:#eaeaea;");});
$(".bor-mon-textarea").find("textarea").attr("style","color:#cccccc;");
$(".bor-mon-textarea").find("textarea").attr("flagv","1");
$(".bor-mon-textarea").find("textarea").focus(function(){$(this).attr("style","color:#66bde6;");if($(this).attr("flagv")=="1"){$(this).val("");$(this).attr("flagv","0")}});

$('#goToLoanCreditForm').validator({
			rules:{
				  multNumber:function(element,param,field){
						return multNum(element.value,50) || '请输入50的倍数';
			 }
		  },
    fields: {
    	// 20150716 yjj 不做额度校验
		// applyAmt:'贷款金额:required;integer[+]; range[0~${supAmt}];multNumber;',
		loanTerm:'贷款期限:required;integer[+];range[0~${loanTerm}];'
    }
});

$(".selectbyimg").click(function(){
	var tempIdStr=$(this).attr("vid");
	var tempObj=$("#"+tempIdStr);

    var selectinp = $(this).parent().find("input:text");
    var offset = selectinp.offset();
    $("#"+$(this).attr("vid")).css({
        top : offset.top + selectinp.outerHeight(),
        left : offset.left,
        width : selectinp.outerWidth()-2
    });
    $("#"+$(this).attr("vid")).find("li").click(function(){
        $("#"+tempIdStr+"inp").val($(this).html());
        $("."+tempIdStr+"inp").val($(this).attr("sval"));
        $(tempObj).hide();
    });
	$("#"+$(this).attr("vid")).show();
	
	});

   $("#captachaImg").click(function(){
   	    // alert("535555");
   		refreshCaptcha();
   });
   
   $("#refreshBtn").click(function(){
   		 //alert("535555");
   		refreshCaptcha();
   });
   
   function refreshCaptcha(){
        var randomVal = Math.random();
       // alert(randomVal);
   		$("#captachaImg").attr("src",'${base}/captcha.jpg?'+randomVal);    //其实这里头是一个方法,后面的randomVal是一个参数
   }

});
</script>
</body>
</html>