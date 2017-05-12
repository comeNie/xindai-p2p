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
<#include "/common/header.ftl">
<form class="form-horizontal" method="post" id="submitForm" action="${base}/userMessage/noteSetSubmit.do" autocomplete="off"  data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
<div class="wapper">
	<div class="main">
        <div class="user_panel">
        	<#include "/common/left.ftl">
            <div class="right">
               <div class="tab_table_N creditor auto_css">
                <div class="nav">
                    <ul id="messageManageNav">
                        <li  class="current" id="tab1" ><a <a href="javascript:void(0);">通知设置</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div id="msgDiv"></div>
                <div class="repayment repayment_auto">
                     <table class="table table-hover table-h">
                        <thead>
                          <tr>
                            <th></th>
                            <th>系统消息</th>
                            <th>邮件提醒</th>
                            <th>短信提醒</th>
                          </tr>
                        </thead>
                        <tbody>
                         <!--
                          <tr>
                             <td>基本设置</td>
                             <td><input  type="checkbox" id="_1_all" checked/></td>
                             <td><input  type="checkbox" id="_2_all" checked/></td>
                             <td><input  type="checkbox" id="_3_all" checked/></td>
                          </tr>
                          -->
                          <tr>
                             <td>注册成功</td>
                             <td><input class="noteSetCk" type="checkbox" id="A1_1" name="A1_1"  class="_1_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A1_2" name="A1_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A1_3" name="A1_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>登录提醒短信</td>
                             <td><input class="noteSetCk" type="checkbox" id="A2_1" name="A2_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A2_2" name="A2_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A2_3" name="A2_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>修改密码</td>
                             <td><input class="noteSetCk" type="checkbox" id="A3_1" name="A3_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A3_2" name="A3_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A3_3" name="A3_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>修改提现密码</td>
                             <td><input class="noteSetCk" type="checkbox" id="A4_1" name="A4_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A4_2" name="A4_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A4_3" name="A4_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>重置安全问题</td>
                             <td><input class="noteSetCk" type="checkbox" id="A5_1" name="A5_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A5_2" name="A5_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A5_3" name="A5_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>修改手机号</td>
                             <td><input class="noteSetCk" type="checkbox" id="A6_1" name="A6_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A6_2" name="A6_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A6_3" name="A6_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>修改邮箱地址</td>
                             <td><input class="noteSetCk" type="checkbox" id="A7_1" name="A7_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A7_2" name="A7_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A7_3" name="A7_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>修改银行账号</td>
                             <td><input class="noteSetCk" type="checkbox" id="A8_1" name="A8_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A8_2" name="A8_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A8_3" name="A8_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>升级会员</td>
                             <td><input class="noteSetCk" type="checkbox" id="A9_1" name="A9_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A9_2" name="A9_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A9_3" name="A9_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>会员延期</td>
                             <td><input class="noteSetCk" type="checkbox" id="A10_1" name="A10_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A10_2" name="A10_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A10_3" name="A10_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>线上充值完成</td>
                             <td><input class="noteSetCk" type="checkbox" id="A11_1" name="A11_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A11_2" name="A11_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A11_3" name="A11_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>资金提现</td>
                             <td><input class="noteSetCk" type="checkbox" id="A12_1" name="A12_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A12_2" name="A12_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A12_3" name="A12_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>收到现金</td>
                             <td><input class="noteSetCk" type="checkbox" id="A13_1" name="A13_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A13_2" name="A13_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A13_3" name="A13_3"  class="_3_cls" checked/></td>
                          </tr>
                          <tr>
                             <td>月度对账单</td>
                             <td><input class="noteSetCk" type="checkbox" id="A14_1" name="A14_1"  class="_1_cls"  checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A14_2" name="A14_2"  class="_2_cls" checked/></td>
                             <td><input class="noteSetCk" type="checkbox" id="A14_3" name="A14_3"  class="_3_cls" checked/></td>
                          </tr>
                          <!--
                          <tr>
                          	<td colspan="4">
                          	<button class="ui-button ui-button-yellow ui-button-mid" type="submit">保持设置</button>
                          	</td>
                          </tr>
                          -->
                        </tbody>
                      </table>
                </div>	
       		 </div>
            </div>	
        </div>
        <div class="clear"></div>        
    </div>
</div>	
</form>
<#include "/common/footer.ftl">
<script type="text/javascript">
//<![CDATA[
$(document).ready(function(){
	loadData();
	/*
	$("#_1_all").click(function(){
		if($("#_1_all").attr("checked")){
			$('._1_cls').attr("checked",true);
		}else{
			$('._1_cls').attr("checked",false);
		}
	});
	
	$("#_2_all").click(function(){
		if($("#_2_all").attr("checked")){
			$('._2_cls').attr("checked",true);
		}else{
			$('._2_cls').attr("checked",false);
		}
	});
	
	$("#_3_all").click(function(){
		if($("#_3_all").attr("checked")){
			$('._3_cls').attr("checked",true);
		}else{
			$('._3_cls').attr("checked",false);
		}
	});
	*/
	$(".noteSetCk").click(function(){
		var ckVal; 
		if($(this).attr("checked")){
			ckVal = "on";
		}else{
			ckVal = "";
		}
		var noteSetKey = $(this).attr("id");
		$('.loadingWord').hide();
		//showAjaxAjaxLoading();
		$.ajax({
				type:"POST",
				url:"ajaxNoteSet.do",
				dataType:"json",
				data:"noteSetKey=" + noteSetKey + "&ckVal=" + ckVal,
				complete:function() {
				},
				success:function(data) {
					//hidenAjaxAjaxLoading();
				},
				error:function(text) {
					//hidenAjaxAjaxLoading();
				} 

			});
		
	});
	
	
});

function loadData(){
	$("input[name='checkbox']").removeAttr("checked"); 
	        var _1_cnt = 0;
	        var _2_cnt = 0;
	        var _3_cnt = 0;
			$.ajax({
				type:"POST",
				url:"ajaxQueryNoteSet.do",
				dataType:"json",
				complete:function() {
				},
				success:function(data) {
					//alert(data.status);
					if(data.status=="1"){
						for(i=1;i<=14;i++){
							for(j=1;j<=3;j++){
								try{
									var val = "-1";
									eval("val = data.A" + i + "_" + j + ";");
									//alert(val);
									if( val=="1" ){
										$("#A" + i + "_" + j).attr("checked",true);
										eval("_" + j + "_cnt++");
									}else{
										$("#A" + i + "_" + j).attr("checked",false); 	
									}
								}catch(e){}
							}
						}
						
						if( _1_cnt<14 ){
							$("#_1_all").attr("checked",false); 
						}
						if( _2_cnt<14 ){
							$("#_2_all").attr("checked",false); 
						}
						if( _3_cnt<14 ){
							$("#_3_all").attr("checked",false); 
						}
					}else{
						alert("not ok");
					}
					
					
					
				}
				
			});
}


 //]]>
</script>
</body>
</html>