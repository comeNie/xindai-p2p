

<style>
.selected{
color:white;  background:#ffb23f;  border-radius: 5px;
}
</style>
<body>
<script>
var errMes = '${errMes}';
var sucMes = '${sucMes}';
var isFistLoad1 = true;
var isFistLoad2 = true;
var isFistLoad3 = true;
var isFistLoad4 = true;
if(errMes!=''){
  showDialogNew(true,errMes,'E','提醒信息');
}

if(sucMes!=''){
  showDialogNew(true,sucMes,'S','提醒信息');
}
</script>
<script type="text/javascript">
$(document).ready(function(){
	startRecRequest();	
	setInterval(startRecRequest,30000);

	var h = $(document).height();
	$(".overlay").css({"height": h });
		
	$('#mylend').hover(function() {
		$('#mylend_second').show();	
	}, function() {
		$('#mylend_second').hide();		
	});
	
    $('#myLoan').hover(function() {
		$('#myLoan_second').show();	
	}, function() {
		$('#myLoan_second').hide();		
	});
	
	
	$('#loginBtn').click(function(){
	    if($("#loginDiv").is(":visible")){  
	    	$('#loginDiv').hide();
	    }else{
	    	$('#loginDiv').show();
		}
	});
	
 
	
//	$('#loginForm').keydown(function(e){
//	 var e = e || event,
// 	keycode = e.which || e.keyCode;
//	if (keycode==13){
//		ajaxLogin();
//	  }
//	});

});



function startRecRequest(){
		$.ajax({
				type:"POST",
				url:"${base}/userMessage/ajaxQueryMsgCnt.do",
				dataType:"json",
				
				complete:function(){
				},
				success:function(data) {
				    if(data.status == 1){
				    	$('#recNum').html("("+data.cnt+")");
				    }
				},
				error:function(text) {
					$('#recNum').html("(0)");
				} 
			});
}


</script>
</body>