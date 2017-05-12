<div class="new-info-box-two" id="load-info-five" style="display:none;">
				<div class="new-info-message">
					<div class="message-title">留言板</div>
					<div class="message-body"> <textarea id="content" name="content" class="form-control" rows="3"></textarea></div>
					<div class="message-btn"><a  onclick="sendMsg();" ><img src="${base}/platform/img/yellowbtn.png" /></a></div>
				</div>

				<div class="new-info-message">
					<div class="message-title">回复</div>
					<div class="message-body"><textarea id="replycontent" name="replycontent" class="form-control" rows="3"></textarea></div>
					<div class="message-btn"><a onclick="replyMsg(1);"><img src="${base}/platform/img/yellowbtn.png" /></a></div>
				</div>
</div>


<script type="text/javascript">
//<![CDATA[
		function sendMsg(){
			
			$.ajax({
				type:"POST",
				url:"ajaxAddMsg.do",
				dataType:"json",
				data:{
						loanId:$('#loanApproveId').val(),
					  	msgContent:$('#content').val()
					  },
				complete:function() {
				},
				success:function(data) {
					if(data.status=="1"){
						 showDialogNew(true,data.reMes,'S','提醒信息');
					}else{
						showDialogNew(true,data.reMes,'E','提醒信息');
					}
					
				}
				
			});
		}
		
		
		function replyMsg(linkId){
			$.ajax({
				type:"POST",
				url:"ajaxReplyMsg.do",
				dataType:"json",
				data:{
						linkId:linkId,
						loanId:$('#loanApproveId').val(),
					  	msgContent:$('#replycontent').val()
					  },
				complete:function() {
				},
				success:function(data) {
					if(data.status=="1"){
						showDialogNew(true,data.reMes,'S','提醒信息');
					}else{
						showDialogNew(true,data.reMes,'E','提醒信息');
					}
					
				}
				
			});
		}
 //]]>
</script> 