				<div class="repayment" id="myFriend_tab1">
                    <div class="searchbox">
                    	<span class="inputbox">
                            <input class="span2"  placeholder="请输入查找" type="text" size="18" id="friendName" name="friendName">
                            <button class="ui-button ui-button-yellow ui-button-mid" id="searchBtn">查找</button>
                        </span>
                    </div>
                    <div class="clear"></div>
                    <div id="friendDiv">
                    
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
		
		//ajaxQuery1(0, true);
		
		
		$('#searchBtn').click(function(){
			ajaxQuery1(0, true);
		});
		 
	});
	
	
	
	
		
		


 //]]>
</script>