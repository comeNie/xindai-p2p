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
             
               <div class="tab_table_N creditor auto_css">
                <div class="nav">
                    <ul>
                        <li  class="current"><a href="javascript:void(0);">我的推荐</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div class="repayment">
                    <div class="form-inline">
                        <label class="control-label">您的邀请链接：</label>
                        <input style="width:400px;" type="text" value="http://${url_pre}${base}/register/index.do?userCode=${userCode}"  readonly="true" />
                        <button id="CopyBtn" class="btn btn-primary" data-clipboard-text="http://${url_pre}${base}/register/index.do?userCode=${userCode}">复制地址</button>
                    </div>
                </div>
       		 </div>
       		<p><h1 style=font-weight:bold;">注:复制邀请链接，发给您的朋友们，您的朋友只要通过该链接注册世纪创投账户，您即可得相应的奖励!</h1></p>
       		 <h1 style=font-weight:bold;">活动时间：8月1日~8月30日</h1>
       		 <div class="p-jk">
       		  <div class="p-jk-box p-jk-box-blue">
                   <div class="p-jk-hd">普通推广员</div>
                   <div class="p-jk-bd">
                       <ul style="margin:15px 15px 0; padding-bottom:15px; font-size:15px; height: 100px; border-bottom:1px dashed #eee;">
                           <li class="tit" style="font-weight:bold;">如何成为普通推广员？</li>
                           <li>1、注册成为世纪创投用户。</li>
                           <li>2、进行身份证实名认证。</li>
                           <li>3、完成银行卡绑定。</li>
                       </ul>
                       <ul style="margin:15px 15px 0; padding-bottom:15px; font-size:15px; height: 60px;">
                           <li class="tit" style="font-weight:bold;">奖励规则</li>
                           <li>每推荐一个用户绑定银行卡即可获得：20元现金+20个世纪创投币</li>
                       </ul>
                   </div>
               </div>
               
       		  <div class="p-jk-box p-jk-box-aqua">
                   <div class="p-jk-hd">超级推广员</div>
                   <div class="p-jk-bd">
                      <ul style="margin:15px 15px 0; padding-bottom:15px; font-size:15px; height: 100px; border-bottom:1px dashed #eee;">
                           <li class="tit" style="font-weight:bold;">如何成为超级推广员？</li>
                           <li>1、成为普通推广员。</li>
                           <li>2、推荐20个人完成银行卡的绑定。</li>
                       </ul>
                       <ul style="margin:15px 15px 0; padding-bottom:15px; font-size:15px; height: 60px;"">
                           <li class="tit" style="font-weight:bold;">奖励规则</li>
                           <li>每推荐一个用户绑定银行卡即可获得：40元现金+40个世纪创投币</li>
                       </ul>
                   </div>
               </div>
               
              <#--<div style="border-top: 1px solid #E6E6E6; width: 300px; text-align: left; margin-bottom: 10px;">
               </div>-->
            </div> 
            <div style="border: 1px solid #E6E6E6; width: 640px; margin-bottom: 10px;">
                <h1 style="font-weight:bold;">注意事项：</h1>
	            <h1 style="line-height: 25px;">1、每个自然月最多推荐100个用户，超出部分不计算为推荐人。</h1> 
	            <h1 style="line-height: 25px;">2、世纪创投不带缴个人所得税，请获得奖励的推广员自行缴纳个人所得税。</h1> 
	            <h1 style="line-height: 25px;">3、如推广员采用某些特殊技术手段，扰乱正常的推荐传播，一经核实，世纪创投保留拒绝支付的权利。</h1>
	            <h1 style="line-height: 25px;">4、厦门世纪创投投资管理有限公司保留对本活动的最终解释权。</h1>
            </div>
            
            
            
            </div>	
        </div>
        <div class="clear"></div>        
    </div>
</div>	
<#include "/common/footer.ftl">
</div>
    <script src="/platform/js/zeroclipboard/ZeroClipboard.min.js"></script>
    <script>
        var client = new ZeroClipboard( document.getElementById("CopyBtn") );
        client.on( "ready", function( readyEvent ) {
            client.on( "aftercopy", function( event ) {
                  showDialogNew(true,"复制成功！",'S','提醒信息');
            });
        });
    </script>
<script type="text/javascript">
//<![CDATA[
	var itemPerPage = 10;

	


function copyToClipboard(txt) {   
     if(window.clipboardData) { 
     	
             window.clipboardData.clearData();   
             window.clipboardData.setData("Text", txt); 
             alert("复制成功！")   
     } else if(navigator.userAgent.indexOf("Opera") != -1) {   
          window.location = txt;   
          alert("复制成功！") 
     } else if (window.netscape) {   
          try { 
          	     
               window.netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");   
          
          var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);   
          if (!clip)   
               return;   
          var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);   
          if (!trans)   
               return;   
          trans.addDataFlavor('text/unicode');   
          var str = new Object();   
          var len = new Object();   
          var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);   
          var copytext = txt;   
          str.data = copytext;   
          trans.setTransferData("text/unicode",str,copytext.length*2);   
          var clipid = Components.interfaces.nsIClipboard;   
          if (!clip)   
               return false;   
          clip.setData(trans,null,clipid.kGlobalClipboard);   
          alert("复制成功！") 
          } catch (e) {   
               alert("复制失败,请手动进行复制");   
          }     
     } else{
     	alert("复制失败,请手动进行复制"); 
     }   
}  
 //]]>
</script>
</body>
</html>