<!DOCTYPE html>
<html lang="en"  >
<head>
<meta charset="utf-8">
<title>世纪创投888-让资本启动未来-互联网金融理财服务首选平台</title>
<meta name="description" content="世纪创投888（www.sjct888.com）-让资本启动未来,互联网金融理财服务首选平台。为投资理财用户和借贷用户两端提供安全、透明、诚信、高效的互联网金融服务。 ">
<meta name="keywords" content="世纪创投/世纪创投888/深圳世纪创投/世纪创投888/世纪创投P2P/互联网金融/P2P/个人理财/网络借贷/投资理财/P2P理财/网络理财/网贷">
<meta name="author" content="">
<meta name="viewport"content="width=1349;"/> 
<meta name="baidu-site-verification" content="DeqU7TjXmj" />
<#include "/common/resource.ftl"> 

</head>
<#include "/common/header_a.ftl">
<script type="text/javascript">
var _py = _py || [];
_py.push(['a', 'NZs..2Gu6O7pkulCF4Vy5KuCC4X']);
_py.push(['domain','stats.ipinyou.com']);
_py.push(['e','']);
-function(d) {
	var s = d.createElement('script'),
	e = d.body.getElementsByTagName('script')[0]; e.parentNode.insertBefore(s, e),
	f = 'https:' == location.protocol;
	s.src = (f ? 'https' : 'http') + '://'+(f?'fm.ipinyou.com':'fm.p0y.cn')+'/j/adv.js';
}(document);
</script>

<div id="Slidebox"> 
  <!--flash begin-->
 	<div id="slideBox" class="slideBox">
        <div class="hd">
            <ul></ul>
        </div>
        <div class="bd">
            <ul>
                <#list (carouselList)! as list>
                    <li><a href="<@list.url?interpret />" target="_blank" style="background-image:url(${base}${list.logo})"></a></li>
                </#list>
          
            </ul>
        </div>
   </div>
  <!--flash end--> 
</div>
<div style="clear:both;">
</div>
<div class="wapper">
	<div class="main">
<link rel="stylesheet" href="css1/index1.css"/>
<script type="text/javascript" src="js1/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js1/index1.js"></script>
<body>

<script type="text/javascript" charset="utf-8">

$(document).ready(function() {
    //每页显示数据条数
    var itemPerPage=8;
	// 查询列表
	ajaxQuery(1,true);
	friendLink();

	function ajaxQuery(requestPage,isInit){
		var loanType = $("#loanTypeVal").val();
		var term  =  $("#termVal").val();
		var rate  =  $("#rateVal").val();
		//alert("rate=="+rate);
		var creditLevel  =  $("#creditLevelVal").val();
		
		
		$('tr[id^=ready]').hide();
		$("#nodata").hide();
		$("#loading").hide();
		$.ajax({
				type:"POST",
				url:"loan/ajaxQuery.do",
				dataType:"json",
				data:"loanType="+loanType+"&term="+term+"&rate="+rate+"&creditLevel="+creditLevel+"&requestPage="+requestPage+"&pageSize="+itemPerPage,
				complete:function(){
// 					alert('3333');
				},
				success:function(data) {
					$('tr[id^=ready]').remove();
					
					if(data.list == null){
					   $("#loading").hide();
					   $("#nodata").show();
					}
					
			        $.each(data.list, function(i, n){   
	                    var row = $("<ul></ul>");
			        	row.attr("id","ready"+i);//改变绑定好数据的行的id	
			        	 
	                    row.append($("<li> <li class=\"li1\">"+n.loanRate+"%</li>"));
	                    row.append($("<li class=\"li2\">年化收益</li>"));
	                   
	                    row.append($("<li class=\"li3\"><a href=\"${base}/loan/detail.do?loanApproveId="+n.loanApproveId+"\" title=\""+n.loanName+"\">"+n.loanName+"</a></li>"));
	                    row.append($("<li class=\"li\" style=\"width:100%;height:63px;padding: 3px 0 7px 0;\"><div style=\"margin-left:11px;margin-right:11px;line-height:20px;text-align:center;text-indent:0\"><font style=\"color:#989898;font-size:12px;font-family:'Microsoft YaHei'\">"+n.remark+"</font></div></li>"));
	                    row.append($("<li class=\"li\" style=\"margin-top: -10px;\"><span><i>"+n.tenderLimitAmt+"</i>&nbsp;&nbsp;元</span>单笔投资</li>"));
	                    row.append($("<li class=\"li\"><span><i>"+n.loanTerm+"</i>个月</span>投资期限</li>"));
	                    row.append($("<li class=\"li\"><span>"+n.completeness+"%</span>投资进度</li>"));
	                    row.append($("<li class=\"li5\"><span><i style=\"width:"+n.completenessRound+"%\"></i></span></li>"));
	                    row.append($("<li class=\"li4\">融资金额：<i>￥"+fmoney(n.loanAmt,2)+"</i></li>"));
// 	                    row.append($("<li class=\"li4\" style=\"margin-top:-80px\">此标由山西晋商世纪投资咨询有限公司发行并由太原自由空间饮食文化发</li>"));
	                    row.append($("<li><a href='${base}/loan/detail.do?loanApproveId=" + n.loanApproveId + "'><button>立即投标</button></a></li>"));
			            
	                    row.appendTo("#datas");//添加到模板的容器中
	                });
			    
	                // 分页
			          if(isInit){
		                	pageQuery(data.totalCount);
		                }
		                
		                $("#loading").hide();
		                $('tr[id^=ready]').show();
				         
					},
					error:function(text) {
						//alert('请求后台出错.');
					} 
				});
			}
	function friendLink(){
		$.ajax({
				type:"POST",
				url:"loan/friendLink.do",
				dataType:"json",
				complete:function(){
// 					alert('3333');
				},
				success:function(data) {
					 $("#link").empty();
					 $.each(data.list, function(i, n){   
		             // alert(n.name);
		              $('<a  class="pic" href="'+n.url+'" target="_blank"> '+n.name+'</a>').appendTo("#link")
		                });
					},
					error:function(text) {
						//alert('请求后台出错.');
					} 
				});
			}
	// 分页查询
	function pageQuery(dataSize){
	    var optInit = $.extend({
			items_per_page:itemPerPage,
			num_display_entries:10,
			current_page:0,
			num_edge_entries:2,
			prev_text:"上一页",
			next_text:"下一页",
			callback:pageselectCallback
		});
	    $("#Pagination").pagination(dataSize, optInit);
	}
	
	// 分页组件回调函数
	function pageselectCallback(page_index, j){
		if(!isFistLoad1){
			ajaxQuery(page_index+1,false);
		}
		isFistLoad1=false;
        return false;
    }
});
</script>

<div class="slidebox-01">
<div  >

	<ul class="slidepic-01"  >
	<div style="margin:0 auto;width:auto">
<!-- 		<a><li style=" background:url(images/1.jpg)center no-repeat;"></li></a> -->
				
		      
<!-- 		<a><li style="background:url(images1/2.jpg)center  no-repeat;"></li></a> -->
				
		      
		<a><li style="background:url(images1/3.jpg)center no-repeat;"></li></a>
				
		      
		<a><li style="background:url(images1/4.jpg)center  no-repeat;"></li></a>
				
		<a><li style="background:url(images1/6.jpg)center  no-repeat;"></li></a>
		
		<a><li style="background:url(images1/7.jpg)center  no-repeat;"></li></a>
		
<!--		<a><li style="background:url(images1/5.jpg)center no-repeat;"></li></a> -->
				
		    </div>  
		</ul>
		</div>
	<div class="slidebtn-01">
		<ul>
<!-- 		<li></li> -->
<!-- 		<li></li> -->
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		</ul>
	</div>
    <div class="center">
    	<div class="tanc">
            <div class="slidebtn-02" style="right:-40px"></div>
            <div class="slidebtn-03" style="right:-40px">
            	<p>最高年化收益率</p>
                <div style="min-width:1349px">
                	<span>1</span>
                	<span>6</span>
                    <i class="i">.</i>
                	<span>0</span>
                	<span>0</span>
                    <i>%</i>
                </div>
                <p>${leastRateYear*100}%-${mostRateYear*100}%年化收益；48倍活期存款收益</p>
                <p>已是会员？<a href="${base}/loginAction/login.do">立即登录</a><a href="${base}/register/index.do" class="button">立即注册</a></p>
            </div>
        </div>
    </div>
 

</div>
		


<div id="hid_cs_pannel" style="z-index: 65000; top: 90%;  right:-8px;overflow: hidden; width: 100px; position: fixed;">
<!-- <script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwMTk1NV8zMDQyNTRfNDAwMDE3NDY4OF8"></script> -->
<script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwMTk1NV8zMDgzMDFfNDAwMDE3NDY4OF8"></script>
</div>

        <div class="clear">
        	<input id="loanTypeVal" value="-1" style="display: none;"/>
        	<input id="termVal" value="-1" style="display: none;"/>
        	<input id="rateVal" value="-1" style="display: none;"/>
        	<input id="creditLevelVal" value="-1" style="display: none;"/>
        </div>
<html>
<head>
<link rel="stylesheet" href="css1/index.css"/>

<link href="css1/css.css" rel="stylesheet" type="text/css" />
</head>

<div >
<div style="text-align:center;margin-top:30px;">

<!--<p><img src="${base}/images/Invest.png"><p/>-->
<img style="margin:-7px;" src="${base}/images/lclc.png">
<a href="${base}/register/index.do"><img style="margin:-5px;" src="${base}/images/zc.png"></a>
<a href="${base}/userSecurity/index.do?myVid=userinfohead1"><img style="margin:-7px;" src="${base}/images/smrz.png"></a>
<a href="${base}/userFund/myRecharge.do"><img style="margin:-7px;"  src="${base}/images/zhcz.png"></a>
<a href="${base}/loan/list.do"><img style="margin:-5px;" src="${base}/images/tzlc.png"></a>


</div><br>
<!-- 	<table  style="width:1080px;height:50px;border:solid 1px #ffb23f ;margin: 0 auto;"> -->
		
<!-- 		<tr> -->
<!-- 			<td  style="text-align:left;text-indent:20px;font-size:20px"><font style="color:#666666">关于世纪创投</font></th> -->
<!-- 		</tr> -->
<!-- 	</table><br> -->
	<table  style="width:1080px;height:50px;border:solid 1px #ffb23f;margin: 0 auto;">
		
		<tr>
			<td style="text-align:left;font-size:18px;text-indent:23px;padding:8px;"><font style="color:#666666">关于世纪创投：</font></td>
		</tr>
		<tr>
			<td style="text-align:left;font-size:18px;line-height: 30px;padding-left:32px;padding-right:15px"><font style="color:#666666">世纪创投888（www.sjct888.com）-让资本启动未来,互联网金融理财服务首选平台。为投资理财用户和借贷用户两端提供规范、透明、诚信、高效的互联网金融服务。</font></th>
		</tr>
	</table>
<div class="center"  style="margin:0 auto; ">   



    <div class="table4 "  style="margin-left:-4%">
    	<ul>
            <li class="li1"><span class="li">投资理财列表</span>（世纪创投正在努力审核项目，预计项目发布时间 11:00 , 15:00 ）</li>
        </ul>
        <a href="${base}/loan/list.do" style="margin-left:40px">查看更多投资信息</a>
    </div>
    <div class="table5_div"style="text-align:center;width:110%">
                <div class="table5" id="datas" style="margin-left:-3.5%;width:110%">
               </div>
     </div>
     <div id="loading" class="load" style="display:none">
 	 <p>正在加载数据....</p>
  	</div>
  	<div id="nodata" class="load" style="display:none">
 	 <p>暂无数据</p>
  	</div>
   
   <div style="width:100%;">

<#include "/common/new.ftl">
   
</div>
</div>
<html>
<head>
<!-- <link rel="stylesheet" href="css1/style.css"/> -->
<style>

.bottom{float:left;width:100%;min-height:270px; background:#E7E7E7;margin-top:20px;clear: both;}
.bottom .bottom-top{border-bottom:1px solid #e5e5e5;height:201px;position: relative;}
.bottom .bottom-top .left{float:left;width:380px;border-right:1px solid #e5e5e5; position: absolute; left:408px;top: 5px;}
.bottom .bottom-top .left ul{float:left;width:33%;height:161px;background:#E7E7E7;margin-top:40px;}
/* .bottom .bottom-top .left ul li{float:left;width:100%;height:20px;font-size:12px;text-align:left;} */
.bottom .bottom-top .left ul .li{float:left;width:100%;height:25px;font-weight:bold;margin-left: -12px;}


.bottom .bottom-top .bottom-center{float:left;width:340px;height:201px;border-right:1px solid #e5e5e5;}
.bottom .bottom-top .bottom-center ul{float:right;width:262px;height:161px;margin-top:40px;text-align:left;}
.bottom .bottom-top .bottom-center ul .li{float:left;width:262px;font-size:12px;font-weight:bold;color:#4a4a4a;background:url(../images1/ioco.png) no-repeat;line-height: 29px;height: 29px;text-indent: 35px;}
.bottom .bottom-top .bottom-center ul .li1{float:left;width:262px;font-size:26px;font-weight:bold;color:#4a4a4a;text-indent: 10px;line-height:35px;}
.bottom .bottom-top .bottom-center ul .li2{float:left;width:262px;font-size:12px;color:#4a4a4a;text-indent: 10px;}
.bottom .bottom-top .bottom-center .s1{background-position:0px -30px;}
.bottom .bottom-top .bottom-center .s1:hover{background-position:0px -62px;}
.bottom .bottom-top .bottom-center .s2{background-position:-47px 0px;}
.bottom .bottom-top .bottom-center .s2:hover{background-position:-47px -32px;}
.bottom .bottom-top .bottom-center .s3{background-position:-94px 0px;}
.bottom .bottom-top .bottom-center .s3:hover{background-position:-94px -32px;}
.bottom .bottom-top .bottom-center .s4{background-position:-141px 0px;}
.bottom .bottom-top .bottom-center .s4:hover{background-position:-141px -32px;}


.bottom .bottom-top .bottom-right{float:left;width:350px;padding:40px 0px 0px 6px;height:161px;position: absolute; left: 650px;top: 2px;}
.bottom .bottom-top .bottom-right .ul{float:left;width:215px;}
  .bottom .bottom-top .bottom-right .ul li{float:left;width:215px;height:30px;background:#fff;margin-bottom:13px;}  
   .bottom .bottom-top .bottom-right .ul span{float:left;width:215px;height:30px;line-height:30px;text-indent:22px;border:1px solid #e5e5e5;background:url(../images1/ioco.png) 1px -64px no-repeat;}  
 .bottom .bottom-top .bottom-right .ul1{float:right;width:117px;color:#4a4a4a;margin-top: -5px;}  
 .bottom p{float:left;width:90%;min-height:35px;line-height:35px;background:#E7E7E7;text-align:left;margin-left:2%} 
  .bottom p i{float:left;color:#ccc;} 
 .bottom p a{float:left;margin:0px 5px;} 
 .bottom p span{float:left;} 
 .bottom .footer-ft{float:left;width:100%;color:#646464;  margin-top:5px;}  
  .bottom .footer-ft a{border:0px;}  


</style>
</head>

<body>
<div>
<div class= "bottom" style="margin:1 auto">
	<div class="center" style="margin:0 auto">
    	<div class="bottom-top" style="margin-left:-5%">
            <div class="bottom-center"style="margin-left:60px;">
            
            <div >
        	<ul style="  font-family: \5FAE\8F6F\96C5\9ED1,arial,sans-serif; color: #333;font-size: 14px;">
            	<li  style="color:#333333;font-size:28px;width:400px;padding:10px;margin-left:-120px;margin-top: -25px;font-family:arial,sans-serif;"><img style="margin-left:50px;" src="${base}/images/slogo.png"><strong style="margin-right:80px;margin-top: 32px;float: right;">全国热线 :</strong></li>
                <li style="font-weight: bold;color: #666;font-size: 35px;line-height: 23px;margin-left:-57px;">400-7566-678</li>
                <li style="margin-top:20px;font-size:15px;margin-left:-60px"> 服务时间：09:00 - 21:00 (周一至周日) </li>
            </ul>
        </div>
            </div>
            <div class="left" style="text-align:center;">
            	
                  <ul>
                    <li class="li"><img src="${base}/images/footer1.png"/>关于我们</li>
                    <li> <a href="${base}/about/aboutUs.do"><font style="font-family:'宋体'；">公司简介</font></a></li>
                    <li><a href="${base}/about/aboutUsManager.do"><font style="font-family:'宋体'；">管理团队</font></a></li>
<!--                     <li> <a href="getMessageBytypeIdce30.html?typeId=4"><font style="font-family:'宋体'；">专家顾问</font></a></li> -->
                    <li> <a href="${base}/about/aboutUsCareers.do"><font style="font-family:'宋体'；">招贤纳士</font></a></li>
                    <li> <a href="${base}/about/aboutUsContact.do"><font style="font-family:'宋体'；">联系我们</font></a></li>
                    
                </ul>
                <ul>
                    <li class="li"><img src="${base}/images/footer2.png"/>官方新闻</li>
                    <li> <a href="${base}/about/aboutUsInfo.do"><font style="font-family:'宋体'；">社会责任</font></a></li>
                    <li> <a href="${base}/about/aboutUsPartner.do"><font style="font-family:'宋体'；">合作伙伴</font></a></li>
                    <li>  <a href="${base}/about/aboutUsNews.do"><font style="font-family:'宋体'；">官方公告</font></a></li>
                    <li style=""> <a href="${base}/about/sjctVip.do"><font style="font-family:'宋体'；">VIP公告</font></a></li>
<!--                     <li> <a href="getMessageBytypeIdce30.html?typeId=4"><font style="font-family:'宋体'；">媒体报道</font></a></li> -->
                </ul>
                <ul>
                    <li class="li"><img src="${base}/images/footer3.png"/>帮助我们</li>
                    <li><a href="${base}/about/aboutUsNetBuild.do"><font style="font-family:'宋体'；">新手入门</font></a></li>
 <li><a href="${base}/helpManager/help.do"> <font style="font-family:'宋体'；">帮助中心</font></a></li>
<!--                     <li><a href="financetool.html"><font style="font-family:'宋体'；">收益计算器</font></a></li> -->
                    <li> <a href="${base}/about/aboutUsAnnouncement.do"><font style="font-family:'宋体'；">联系客服</font></a></li>
                </ul>
            </div>
            <div class="bottom-right" style="margin-left:23%;width:100px">
                <ul class="ul1" >
                	<li style="position:relative"><img src="images1/erwei.jpg"/></li>
                    
<!--                     <li style="position:absolute"><img style="margin-left: 120%;margin-top:-120%" src="images/downerwei.png"/></li> -->
                    <li style="width:150px;position:absolute;top:165px;margin-left: -1%;">扫描二维码关注微信</li>
<!--                     <li style="margin-left:140%;width:150px;position:absolute;margin-top:13%" >世纪通APP下载</li> -->
                </ul>
            </div>
            
        </div>
        <div style="position: relative;">
        <p class="friend" style="position: absolute;left: 57px;font-size:18px;top:-8px;padding:10px;"><span>友情链接:</span>
            <span id="link" style="font-family:'幼圆';position:absolute;left:100px;">
        
 			</span>
			</p>
		
        <div class="footer-ft" style="position: absolute;top:28px;text-align:center;">
        	© 2015 世纪创投 All Rights Reserved&nbsp;&nbsp;|&nbsp;&nbsp;深圳市世纪创投互联网金融服务有限公司 &nbsp;&nbsp;|&nbsp;&nbsp;<a href="http://www.miitbeian.gov.cn/" target="_black" style="font-size:14px">备案号：粤ICP备15097089号 </a>
			&nbsp;<a target="_black" style="font-size:14px"></a>
        </div>
        </div>
    </div>
</div>    
</div> 
</body>
</body>
</html>
</html>
</html>