<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>世纪创投</title>
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport"content="width=1349;"/> 
<#include "/common/resource.ftl">

</head>
<body>
<div style="min-width:1349px">
<#include "/common/header_b.ftl">
<div class="wapper">
	<div class="main">
        <div class="conduct">
            <div class="new-nav-two">
                <div class="nav-two-box">
                    <a href="${base}/loan/list.do">我要投资</a>&gt;<a href="javascript:void(0);">散标投资列表</a>
                </div>
            </div>
        	<div class="conduct">
        	<div class="search_box">
            	<div class="search_t">
                	<p><span class="l">筛选投资项目</span><span class="r">新手指引</span></p>
                </div>
                <div class="search_b">
                    <div class="col-left">
                        <p>
                            <span class="t">标的类型：</span>
                            <span class="c" id="loanType">
                                <span class="select-all current" value="-1"><a href="javascript:void(0);">不限</a></span>
                                <@sysCodeList codeType='tenderTypeCd'/>
                                <input id="loanTypeVal" value="-1" style="display: none;"/>
                            </span>
                        </p>
                        <p >
                            <span class="t">借款期限：</span>
                            <span class="c" id="term">
                                <span class="select-all current" value="-1"><a href="javascript:void(0);">不限</a></span>
                                <span value="0"><a href="javascript:void(0);">1-3个月</a></span>
                                <span value="1"><a href="javascript:void(0);">3-6个月</a></span>
                                <span value="2"><a href="javascript:void(0);">6-12个月</a></span>
                                <span value="3"><a href="javascript:void(0);">12个月以上</a></span>
                                <input id="termVal" value="-1" style="display: none;"/>
                            </span>
                        </p>
                        <p>
                            <span class="t">年化利率：</span>
                            <span class="c" id="rate">
                                <span class="select-all current" value="-1"><a href="javascript:void(0);">不限</a></span>
                                <span value="0"><a href="javascript:void(0);">12%以下</a></span>
                                <span value="1"><a href="javascript:void(0);">12%-14%</a></span>
                                <span value="2"><a href="javascript:void(0);">14%-17%</a></span>
                                <span value="3"><a href="javascript:void(0);">17%以上</a></span>
                                <input id="rateVal" value="-1" style="display: none;"/>
                            </span>
                        </p>
                        <p>
<!--                             <span class="t">信用等级：</span> -->
<!--                             <span class="c" id="creditLevel"> -->
<!--                                 <span class="select-all current" value="-1"><a href="javascript:void(0);">不限</a></span> -->
<!--                                 <@sysCodeList codeType='creditRate'/> -->
<!--                                 <span value="-1"><a href="javascript:void(0);">E</a></span> -->
                                <input id="creditLevelVal" value="-1" style="display: none;"/>
                                      
<!--                             </span> -->
                        </p>	
					</div>
					<div class="col-right">
					    <p><a href="${base}/helpManager/wyjk.do" class="right">什么是信用等级？</a></p>
					    <p><a href="${base}/helpManager/wyjk.do" class="right">什么是抵押标？</a></p>
					    <p><a href="${base}/helpManager/wyjk.do" class="right">什么是信用标？</a></p>
					    <p><a href="${base}/helpManager/wyjk.do" class="right">什么是担保标？</a></p>
					</div>
                </div>
            </div>
        </div>
      
        <div class="investments">
        	<h2>投资列表<span><!--<button class="button_con">理财计算器</button>--></span>
        	<!--
        	<span class="input-search"><input type="text" size="18" class="span2"></span>
            -->
            </h2>
            <table class="table">
                <thead>
                  <tr>
                    <th >标题</th>
<!--                     <th style="width: 10%;text-align:center;">信用等级</th> -->
                    <th style="width: 10%;text-align:center;">年利率</th>
                    <th style="width: 10%;text-align:center;">金额</th>
                    <th style="width: 7%;text-align:center;">期限</th>
                    <th style="width: 43px;text-align:center;">进度</th>
                    <th style="width: 18%;text-align:center;">还款方式</th>
                    <th style="width: 80px;text-align:center;">操作</th>
                  </tr>
                </thead>
                <tbody id="datas">
                </tbody>
              </table>
              <div id="loading" class="load" style="display:none">
        	 	 <p>正在加载数据....</p>
        	  </div>
        	  <div id="nodata" class="load" style="display:none">
        	 	 <p>暂无数据</p>
        	  </div>
        <div id="Pagination" class="pagination pagination-right">
         </div>
        </div>
    </div>
</div>
</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
    //每页显示数据条数
    var itemPerPage=8;
	// 查询列表
	ajaxQuery(1,true);
	
	$("#loanType span").click(function () {
		$(this).addClass("current").siblings().removeClass("current");
		//alert($(this).attr("value"));
		$("#loanTypeVal").attr("value",$(this).attr("value"));
		
	});
	
	$("#term span").click(function () {
		$(this).addClass("current").siblings().removeClass("current");
		$("#termVal").attr("value",$(this).attr("value"));
	});
	
	$("#rate span").click(function () {
		$(this).addClass("current").siblings().removeClass("current");
		//alert($(this).attr("value"));
		$("#rateVal").attr("value",$(this).attr("value"));
	});
	
// 	$("#creditLevel span").click(function () {
// 		$(this).addClass("current").siblings().removeClass("current");
// 		$("#creditLevelVal").attr("value",$(this).attr("value"));
// 	});
	
	$(".search_b span").live("click", function () {
		ajaxQuery(1,true);
	});
	
	function ajaxQuery(requestPage,isInit){
		var loanType = $("#loanTypeVal").val();
		var term  =  $("#termVal").val();
		var rate  =  $("#rateVal").val();
		//alert("rate=="+rate);
		var creditLevel  =  $("#creditLevelVal").val();
		
		
		$('tr[id^=ready]').hide();
		$("#nodata").hide();
		$("#loading").show();
		$.ajax({
				type:"POST",
				url:"ajaxQuery.do",
				dataType:"json",
				data:"loanType="+loanType+"&term="+term+"&rate="+rate+"&creditLevel="+creditLevel+"&requestPage="+requestPage+"&pageSize="+itemPerPage,
				complete:function(){
				},
				success:function(data) {
					$('tr[id^=ready]').remove();
					
					if(data.list == null){
					   $("#loading").hide();
					   $("#nodata").show();
					}
			        $.each(data.list, function(i, n){
	                    var row = $("<tr></tr>");
			        	row.attr("id","ready"+i);//改变绑定好数据的行的id
	                    row.append($("<td><img src=\"${base}/images/interest"+n.loanTypeCd+".gif\"/>&nbsp;<a href=\"${base}/loan/detail.do?loanApproveId="+n.loanApproveId+"\" title=\""+n.loanName+"\">"+n.loanName+"</a></td>"));
// 			        	row.append($("<td style=\"text-align:center;\"><img src=\"${base}/platform/img/xydj"+n.creditRate+".png\"/></td>"));
			        	row.append($("<td style=\"text-align:center;\">"+n.loanRate+"%</td>"));
			        	row.append($("<td style=\"text-align:center;\">"+fmoney(n.loanAmt,2)+"</td>"));
			        	row.append($("<td style=\"text-align:center;\">"+n.loanTerm+"月</td>"));
			        	row.append($("<td style=\"text-align:center;\"><div class=\"progress-circle\" style=\"background-position:-"+ (n.completenessRound*54) +"px 0px;\">"+n.completeness+"%</div></td>"));
			        	row.append($("<td style=\"text-align:center;\">"+n.repayTypeCdStr+"</td>"));
			            
			            var status_cd = n.approveStatusCd;
			            var loanStatus = n.loanStatusCd
			            if (status_cd == 0){
			        	   row.append($("<td style=\"text-align:center;\"><a class=\"Operation ui-button ui-button-blue ui-button-mid\" href=\"${base}/loan/detail.do?loanApproveId="+n.loanApproveId +" \">投标</a></td>"));
			        	}else if(status_cd == 1){
			        	  row.append($("<td><span class=\"ui-buttonto ui-button-yellow ui-button-mid\">已满标</span></td>")); 
			        	}else if(status_cd == 2){
			        	   row.append($("<td><span class=\"ui-buttonto ui-button-gray ui-button-mid\">已撤标</span></td>"));
			        	}else if(status_cd == 3){
			        		if(loanStatus == 1){
			        			 row.append($("<td><span class=\"ui-buttonto ui-button-gray ui-button-mid\">已结清</span></td>")); 
			        		}else{
			        			  row.append($("<td><span class=\"ui-buttonto ui-button-gray ui-button-mid\">已放款</span></td>")); 
			        		}
			        	
			        	}
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
</body>
</html>