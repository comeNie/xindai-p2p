<div id="maskId" data-widget-cid="widget-0" class="ui-mask" style="position: fixed; top: 0px; left: 0px; width: 100%; height: 100%; z-index: 99998; opacity: 0.85; filter:alpha(85); background-color: rgb(255, 255, 255);display:none;"></div>
<div id="dialogId" class="ui-dialog" tabindex="-1" data-widget-cid="widget-3" style="width: 500px; z-index: 99999; position: absolute; left: 423.5px; top: 111.5px;display:none; ">
	<div data-role="close" title="关闭本框" class="ui-dialog-close" style="display: block;" onclick="showDialog(false,'');">×</div>
	<div data-role="content" class="ui-dialog-content" style="background: none repeat scroll 0% 0% rgb(255, 255, 255); height: 100%;">
		<div class="ui-message-content">
			  <div class="fn-clear">
				<div class="ui-message-icon fn-left" >
				  <input type="hidden" id="reMessage" name="reMessage" value="<#if reMessage??>${reMessage}<#else></#if>"/>
				  <i title="错误" class="iconfont fn-left error"></i>
				</div>
				<div class="ui-message-text fn-left" id="dialog_content" ></div>
			  </div>
			  <div class="ui-message-operation text-center mt20">
				<a class="ui-button ui-button-blue ui-button-mid ui-message-close-button" onclick="showDialog(false,'');">关闭</a>
			</div>
		</div>
	</div>
</div>
