<!DOCTYPE html>
<html>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta http-equiv="content-type" content="text/html" charset="utf-8">
<link rel="stylesheet" href="${base}/css/one.css">
  <link rel="stylesheet" href="${base}/css/index.css">
 
</head>
<body>
  <div class="pg-container  " style="margin-left:-10px;"><script>
seajs.use(['components/components'], function(Components) {
  new Components.Header().init();
});
(function(){function getQryStrRegExp(name){var reg=new RegExp("(^|\\?|&)"+name+"=([^&]*)(\\s|&|$)","i");if(reg.test(location.href)){return unescape(RegExp.$2.replace(/\+/g," "))}else{return null}}function setCookie(c_name,value,expiredays){var exdate=new Date();exdate.setDate(exdate.getDate()+expiredays);document.cookie=c_name+"="+escape(value)+((expiredays==null)?"":";expires="+exdate.toGMTString()+";path=/")}var promotion=getQryStrRegExp("utmSource");if(promotion){setCookie("promotion_source",promotion,365)}})();
</script>
    <div class="pg-container-content" >   
  <div id="pg-index" >
   
    <div class="container_12" style="margin-left:15%;margin-top:20px;">
      <ul class="intro mb fn-clear">
        <li class="grid_4">
          <a class="intro-link rrd-dimgray" href="guide/invest.html" target="_blank">
            <span class="intro-fig intro-fig-1" ></span>
    
            <span class="intro-title h3">注册认证</span>
<!--             <span class="intro-desc">成为理财人，通过主动投标、加入U计划或薪计划将资金进行投资，最高获得<em class="text-big color-blue-text">12-14%</em>的预期年化收益。</span> -->
          </a>
        </li>
        <li class="grid_4">
          <a class="intro-link rrd-dimgray" href="guide/borrow.html" target="_blank">
            <span class="intro-fig intro-fig-2"></span>
            <span class="intro-title h3">账户充值</span>
<!--             <span class="intro-desc">成为借款人，按照要求完善信用信息，获得信用认证后，通过发标进行借款，最快<em class="text-big color-blue-text">2.5小时</em>可获得所需资金。</span> -->
          </a>
        </li>
        <li class="grid_4">
          <a class="intro-link rrd-dimgray" href="guide/investSecurity.html" target="_blank">
            <span class="intro-fig intro-fig-3"></span>
            <span class="intro-title h3">投资理财</span>
<!--             <span class="intro-desc">所有投资标的100%适用本金保障计划，人人贷风险金账户已被<em class="text-big color-blue-text">民生银行托管</em>，有效保障理财人的本金安全。</span> -->
          </a>
        </li>
      </ul>
    <div class="index-uplan">
    </div>
    <div class="index-fixed"></div>
  </div>
</body>
</html>
