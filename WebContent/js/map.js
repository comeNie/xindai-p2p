var map = new BMap.Map("container");
map.centerAndZoom(new BMap.Point(116.404, 39.915), 12);
map.enableScrollWheelZoom();

//向地图中添加缩放控件
var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
map.addControl(ctrl_nav);

//拼接infowindow内容字串
var html = [];
html.push('<span style="font-size:12px">属性信息: </span><br/>');
html.push('<table border="0" cellpadding="1" cellspacing="1" >');
html.push('  <tr>');
html.push('      <td  align="left" class="common">名称：</td>');
html.push('      <td colspan="2"><input type="text" maxlength="30" size="18"  id="txtName"></td>');
html.push('	     <td valign="top"><span class="star">*</span></td>');
html.push('  </tr>');
html.push('  <tr>');
html.push('      <td  align="left" class="common">电 话：</td>');
html.push('      <td colspan="2"><input type="text" maxlength="30" size="18"  id="txtTel"></td>');
html.push('	     <td valign="top"><span class="star">*</span></td>');
html.push('  </tr>');
html.push('  <tr>');
html.push('      <td  align="left" class="common">地 址：</td>');
html.push('      <td  colspan="2"><input type="text" maxlength="50" size="18"  id="txtAddr"></td>');
html.push('	     <td valign="top"><span class="star">*</span></td>');
html.push('  </tr>');
html.push('  <tr>');
html.push('      <td align="left" class="common">描 述：</td>');
html.push('      <td colspan="2"><textarea rows="2" cols="15"  id="areaDesc"></textarea></td>');
html.push('	     <td valign="top"></td>');
html.push('  </tr>');
html.push('  <tr>');
html.push('	     <td  align="center" colspan="3">');
html.push('          <input type="button" name="btnOK"  onclick="fnOK()" value="确定">&nbsp;&nbsp;');
html.push('		     <input type="button" name="btnClear" onclick="fnClear();" value="重填">');
html.push('	     </td>');
html.push('  </tr>');
html.push('</table>');	

var infoWin = new BMap.InfoWindow(html.join(""), {offset: new BMap.Size(0, -10)});
var curMkr = null; // 记录当前添加的Mkr
var icon = null;// 记录选择样式图标
var mkrTool = new BMapLib.MarkerTool(map, {autoClose: true,followText:"添加位置"});
mkrTool.addEventListener("markend", function(evt){ 
    var mkr = evt.marker;
    mkr.openInfoWindow(infoWin);
    mkr.addEventListener("click", function(evt){ 
        mkr.openInfoWindow(infoWin);
    });
    curMkr = mkr;
});
mkrTool.addEventListener("click", function(evt){ 
    var mkr = evt.marker;
    mkr.openInfoWindow(infoWin);
    curMkr = mkr;
});
//打开样式面板
function openStylePnl(){
    document.getElementById("divStyle").style.display = "block";
}

//选择样式
function selectStyle(index){
    mkrTool.open(); //打开工具 
    icon = BMapLib.MarkerTool.SYS_ICONS[index]; //设置工具样式，使用系统提供的样式BMapLib.MarkerTool.SYS_ICONS[0] -- BMapLib.MarkerTool.SYS_ICONS[23]
    mkrTool.setIcon(icon); 
    document.getElementById("divStyle").style.display = "none";    
}

//提交数据
function fnOK(){
    var name = encodeHTML(document.getElementById("txtName").value);
    var tel = encodeHTML(document.getElementById("txtTel").value);
    var addr = encodeHTML(document.getElementById("txtAddr").value);
    var desc = encodeHTML(document.getElementById("areaDesc").value);

    if(!tel || !addr){
        alert("星号字段必须填写");    
        return;
    }

    if(curMkr){
        //设置label
        var lbl = new BMap.Label(name, {offset: new BMap.Size(1, 1)});
        lbl.setStyle({border: "solid 1px gray"});
        curMkr.setLabel(lbl);
        
        //设置title
        var title = "电话: " + tel + "\n" + "地址: " +addr + "\n" + "描述: " + desc;
        curMkr.setTitle(title);        
    }
    if(infoWin.isOpen()){
        map.closeInfoWindow();
    }

    //在此用户可将数据提交到后台数据库中
}

//输入校验
function encodeHTML(a){
	return a.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;");
}

//重填数据
function fnClear(){
    document.getElementById("txtName").value = "";
    document.getElementById("txtTel").value = "";
    document.getElementById("txtAddr").value = "";
    document.getElementById("areaDesc").value = "";
}

//创建CityList对象，并放在citylist_container节点内
var myCl = new BMapLib.CityList({container : "citylist_container", map : map});

// 给城市点击时，添加相关操作
myCl.addEventListener("cityclick", function(e) {
	// 修改当前城市显示
	document.getElementById("curCity").innerHTML = e.name;

	// 点击后隐藏城市列表
	document.getElementById("cityList").style.display = "none";
});

// 给“更换城市”链接添加点击操作
document.getElementById("curCityText").onclick = function() {
	var cl = document.getElementById("cityList");
	if (cl.style.display == "none") {
		cl.style.display = "";
	} else {
		cl.style.display = "none";
	}	
};

// 给城市列表上的关闭按钮添加点击操作
document.getElementById("popup_close").onclick = function() {
	var cl = document.getElementById("cityList");
	if (cl.style.display == "none") {
		cl.style.display = "";
	} else {
		cl.style.display = "none";
	}	
};

var styleOptions = {
	strokeColor : "red", // 边线颜色。
	fillColor : "red", // 填充颜色。当参数为空时，圆形将没有填充效果。
	strokeWeight : 3, // 边线的宽度，以像素为单位。
	strokeOpacity : 0.8, // 边线透明度，取值范围0 - 1。
	fillOpacity : 0.6, // 填充的透明度，取值范围0 - 1。
	strokeStyle : 'solid' // 边线的样式，solid或dashed。
};

// 实例化鼠标绘制工具
var drawingManager = new BMapLib.DrawingManager(map, {
    isOpen: true, //是否开启绘制模式
    enableDrawingTool: true, //是否显示工具栏
    drawingToolOptions: {
        anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
        offset: new BMap.Size(5, 5), //偏离值
        scale: 0.8 //工具栏缩放比例
    },
    circleOptions: styleOptions, //圆的样式
    polylineOptions: styleOptions, //线的样式
    polygonOptions: styleOptions, //多边形的样式
    rectangleOptions: styleOptions //矩形的样式
});
var overlaycomplete = function(e){
    //overlays.push(e.overlay);
    var result = "";
    result = "<p>";
    result += e.drawingMode + ":";
    if (e.drawingMode == BMAP_DRAWING_MARKER) {
    	e.overlay.enableDragging();
    	e.overlay.setIcon(icon); 
    	e.overlay.addEventListener("click", function(evt){ 
    		e.overlay.openInfoWindow(infoWin);
        });
        result += ' 坐标：' + e.overlay.getPosition().lng + ',' + e.overlay.getPosition().lat;
        if ($('isInfowindow').checked) {
            searchInfoWindow.open(e.overlay);
        }
    }
    if (e.drawingMode == BMAP_DRAWING_CIRCLE) {
        result += ' 半径：' + e.overlay.getRadius();
        result += ' 中心点：' + e.overlay.getCenter().lng + "," + e.overlay.getCenter().lat;
    }
    if (e.drawingMode == BMAP_DRAWING_POLYLINE || e.drawingMode == BMAP_DRAWING_POLYGON || e.drawingMode == BMAP_DRAWING_RECTANGLE) {
        result += ' 所画的点个数：' + e.overlay.getPath().length;
    }
    result += "</p>";
};
//添加鼠标绘制工具监听事件，用于获取绘制结果
drawingManager.addEventListener('overlaycomplete', overlaycomplete);
