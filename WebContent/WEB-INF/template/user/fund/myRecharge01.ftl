
<div id="myRecharge_tab1" class='myRecharge'>
	<div class="repayment repayment_h">
		
		<div class="row"> 
<!-- 					<div class="item"> -->
<!--             	<span><input type="checkbox" bank="BOC"/>&nbsp;<a href="javascript:void(0);"><img src="${base}/platform/img/bank_015.gif" align="absmiddle"/></a></span> 	 -->
<!--             </div>  <#--中国银行 --> -->
<!-- 			<div class="item"> -->
<!--             	<span><input type="checkbox" bank="ABC"/>&nbsp;<a href="javascript:void(0);"><img src="${base}/platform/img/bank_011.gif" align="absmiddle"/></a></span> 	 -->
<!--         	</div>  <#--农 --> -->
<!--             <div class="item"> -->
<!--             	<span><input type="checkbox" bank="ICBC"/>&nbsp;<a href="javascript:void(0);"><img src="${base}/platform/img/bank_012.gif" align="absmiddle"/></a></span> 	 -->
<!--         	</div> <#--工 --> -->
<!--             <div class="item"> -->
<!--             	<span><input type="checkbox" bank="CCB"/>&nbsp;<a href="javascript:void(0);"><img src="${base}/platform/img/bank_013.gif" align="absmiddle"/></a></span> 	 -->
<!--          	</div>   <#--建 --> -->
<!--         	<div class="item"> -->
<!--         	<span><input type="checkbox" bank="BOCOM"/>&nbsp;<a href="javascript:void(0);"><img src="${base}/platform/img/bank_016.gif" align="absmiddle"/></a></span> 	 -->
<!--        </div>  <#--交通 --> -->
<!--         	<div class="item"> -->
<!--         		<span><input type="checkbox" bank="CMB"/>&nbsp;<a href="javascript:void(0);"><img src="${base}/platform/img/bank_014.gif" align="absmiddle"/></a></span> 	 -->
<!--       		</div>   <#--招  --> -->
<!--             <div class="item"> -->
<!--             	<span><input type="checkbox" bank="CITIC"/>&nbsp;<a href="javascript:void(0);"><img src="${base}/platform/img/bank_041.gif" align="absmiddle"/></a></span> 	 -->
<!--             </div> <#--中信 --> -->
<!--         	<div class="item">        	 -->
<!--             	<span><input type="checkbox" bank="CMBC"/>&nbsp;<a href="javascript:void(0);"><img src="${base}/platform/img/bank_017.gif" align="absmiddle"/></a></span> 	 -->
<!--         	</div>  <#--民生 --> -->
<!--         	<div class="item"> -->
<!--            		<span><input type="checkbox" bank="PAB"/>&nbsp;<a href="javascript:void(0);"><img src="${base}/platform/img/bank_018.gif" align="absmiddle"/></a></span> 	 -->
<!--             </div>  <#--平安 --> -->
            

         <div id="myDiv" style="display:none">           	
         	<div class="item" >
           		<span><input type="checkbox" bank="GDB"/>&nbsp;<a href="javascript:void(0);" ><img src="${base}/platform/img/bank_027.gif" align="absmiddle"/></a></span> 	
            </div> <#--广发  -->
            <div class="item" >
            	<span><input type="checkbox" bank="CIB"/>&nbsp;<a href="javascript:void(0);" ><img src="${base}/platform/img/bank_019.gif" align="absmiddle"/></a></span> 	
            </div> <#--兴业  -->
            <div class="item" >
            	<span><input type="checkbox" bank="PSBC"/>&nbsp;<a href="javascript:void(0);"  ><img src="${base}/platform/img/bank_020.gif" align="absmiddle"/></a></span> 	
        	</div><#--邮政  -->
            <div class="item" >
            	<span ><input type="checkbox" bank="BOBJ"/>&nbsp;<a href="javascript:void(0);"  ><img src="${base}/platform/img/bank_022.gif" align="absmiddle"/></a></span> 	
         	</div>  <#--北京银行-->
        	<div class="item" >
            	<span ><input type="checkbox" bank="CEB"/>&nbsp;<a href="javascript:void(0);"  ><img src="${base}/platform/img/bank_026.gif" align="absmiddle"/></a></span> 	
        	</div>  <#--光大  -->
            <div class="item" >
            	<span ><input type="checkbox" bank="HXBC"/>&nbsp;<a href="javascript:void(0);"  ><img src="${base}/platform/img/bank_031.gif" align="absmiddle"/></a></span> 	
         	</div> <#--华夏  -->
        	<div class="item" >
            	<span ><input type="checkbox" bank="BOS"/>&nbsp;<a href="javascript:void(0);"  ><img src="${base}/platform/img/bank_038.gif" align="absmiddle"/></a></span> 	
        	</div> <#--上海-->
<!--         	<div class="item"> -->
<!--             	<span><input type="checkbox" bank="TCCB"/>&nbsp;<a href="javascript:void(0);" ><img src="${base}/platform/img/bank_038.gif" align="absmiddle"/></a></span> 	 -->
<!--         	</div> <#--天津 --> -->
<!--         	<div class="item"> -->
<!--             	<span><input type="checkbox" bank="SPDB"/>&nbsp;<a href="javascript:void(0);" ><img src="${base}/platform/img/bank_038.gif" align="absmiddle"/></a></span> 	 -->
<!--         	</div> <#--上海浦东 --> -->
<!-- <div class="item"> -->
<!--             	<span><input type="checkbox" bank="NBCB"/>&nbsp;<a href="javascript:void(0);" ><img src="${base}/platform/img/bank_038.gif" align="absmiddle"/></a></span> 	 -->
<!--         	</div> <#--宁波 --> -->
       		
       		
         </div>

         

         
        <#--显示更多银行--> 
<!--            <div class="item" > -->
<!--                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" id="otherBanks" style="color:red">更多银行&#187;</a> -->
<!--        </div>  -->
<!--          <div class="item" id="hideOthers"  style="display:none;margin-left:15%;width:100px"> -->
<!--                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);"  style="color:red">&#171;收起</a> -->
<!--        </div>  -->

     
        <div class="clear"></div>
        <div class="recharge" style="margin-left:0%">
            <form id="paymentForm" class="form-horizontal" action="payment.do" target="_blank" method="post" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
                <input type="hidden" id="bank" name="bank"/>
                <fieldset>
       <div style="margin-left:50px;">   <h2>填写充值金额：</h2></div>
                    <div class="control-group">
                        <label class="control-label">账户余额：</label>
                        <div class="controls">
                            <span><#if userIndexVO.accountBalAmt??>${userIndexVO.accountBalAmt}<#else>0</#if>(元)</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"><em>*</em>充值金额：</label>
                        <div class="controls">
                              <input type="text" class="span3" name="amount" id="amount" onblur="">&nbsp;<span>元</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" >充值费用：</label>
                        <div class="controls"><input type="hidden" class="span3" name="recFee" id="recFee" value="${fee}" onblur="">
                            <span id="fee">${fee}元</span><span><img src="${base}/platform/img/i.gif" align="absbottom"/></span>
                        </div>
                    </div>
                    <div class="control-group"> <p style="margin-left:-20%;margin-top:-1%;color:#FA9600"> （温馨提示：充值未投资金额直接提现将收取${exceedRate}%提现费用）</p></div>
                    <div class="btnbar" style="margin-left:5%"><button class="btn btn-primary" id="submit"  onclick="show_a();">充值</button></div>
                </fieldset>
            </form>
        </div>
	</div>
</div>

<script type="text/javascript">
//<![CDATA[
var $repaymentItem= $(".repayment .item input");
	var $amount= $("#amount");
	var $fee= $("#fee");
	var $paymentForm= $("#paymentForm");
	var $submit= $("#submit");
	var $bank= $("#bank");
	var str;
	//银行选择
	$repaymentItem.click(function(){
		var $this = $(this);
		$this.closest(".repayment").find(":checkbox").attr("checked",false);
		$this.attr("checked",true);
		$bank.val($this.attr("bank"));
	});
	
	
	//银行的显示与隐藏
	  
	$("#otherBanks").click(function(){
	  if($("#myDiv").css("display")=="none"){
	     $("#myDiv").show();
	     $("#hideOthers").show();
	     $("#otherBanks").hide();
	  }
	  else{
	     $("#myDiv").hide();
	     $("#hideOthers").hide();
	      
	 }
	});
	
	$("#hideOthers").click(function(){
		  if($("#myDiv").css("display")=="none"){
		     $("#myDiv").show();
		     $("#hideOthers").show();
		     $("#otherBanks").hide();
		  }
		  else{
		     $("#myDiv").hide();
		     $("#hideOthers").hide();
		     $("#otherBanks").show();
		 }
		});
	

	//金额输入框
		$amount.on("keyup",function(){
			
			if(/^([1-9][\d]{0,7}|0)([\.]{0,1}[\d]{0,2})?$/.test($(this).val())){
			}else{
			  $amount.val("");
			}
			
			if($amount.val() == ""){
			    var rFee = $("#recFee").val();
			}else{
				var rFee = (parseFloat($amount.val()) * parseFloat($("#recFee").val())).toFixed(2);
			}
			
			$fee.text(rFee+"元");     
		});	
	
	
	//充值提交
	$submit.on("click",function(){
// 		if($bank.val()==""){
// 			showDialogNew(true,"请先选择银行！",'E','提醒信息');
// 			return false;
// 		}
		if($amount.val()==""){
			showDialogNew(true,"请输入正确的金额！",'E','提醒信息');
			return false;
		}
		if($bank.val()=="NOCARD"){
		    $bank.val("");
		}
		$paymentForm.submit();
	});
	
// 	   function show_a(){
		
// 			dialog({
// 				 content:"<label class='control-label' style='font-size:22px;margin-top:2%'>请您在新打开的网上支付页面完成付款</label>"
// 				 +"<br><p>付款完成前请不要关闭此窗口</p><p>完成付款后请根据您的情况点击下面的按钮：</p>"
// 				 +"<p><button class='btn btn-primary'>已完成付款</button>"
// 				 +" <button class='btn btn-primary'>付款遇到问题</button></p>",
		         
// 		         function(){
// 		            var password = $('#password').val();
// 		            if(password == ""){
// 		              showDialogNew(true,"交易密码不能为空",'E','提醒信息');
// 		              return false;
// 		            }
// 		            $('#tradePwd').attr("value",password);
// 		            showAjaxAjaxLoading();
// 					$('#submitForm').submit();
// 		         }
// 			 }).showModal();
// 	   }	

 //]]>
</script>
