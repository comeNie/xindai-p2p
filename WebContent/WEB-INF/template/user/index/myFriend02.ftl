<div class="repayment" id="myFriend_tab2" style="display:none">
                    <div class="searchbox">
                    	<span class="inputbox">
                            <input class="span2" placeholder="请输入查找" type="text" size="18" id="strangerName" name="strangerName">
                            <button class="ui-button ui-button-yellow ui-button-mid" id="search2Btn">查找</button>
                        </span>
                    </div>
                    <div class="clear"></div>
                    <div id="StrangerDiv">
                    
                    </div>
                    
                </div>
  <script type="text/javascript">
//<![CDATA[
	var currentindex = 1;
	


	$(document).ready(function(){
		var $submit = $("#submit");
		var $dealAmt = $("#dealAmt");
		var $usableBalAmt = $("#usableBalAmt");
		var $bankid = $("#bankid");
		var $submitForm = $("#submitForm");
		
		//ajaxQuery2(0, true);
		
		
		$('#search2Btn').click(function(){
			ajaxQuery2(0, true);
		});
		 
	});
	
	
	
	
		
	


 //]]>
</script>