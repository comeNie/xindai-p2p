function showSelect(hld,id){
var ele = document.getElementById(id);
ele.style.display = 'block';
var timer = null;
ele.onmouseover = function(){
if(timer){
clearTimeout(timer);
}
ele.style.display = 'block';
}
ele.onmouseout = function(){
timer = setTimeout(function(){ele.style.display = 'none'},200);
}
hld.onmouseover = function(){
if(timer){
clearTimeout(timer);
}
ele.style.display = 'block';
}
hld.onmouseout = function(){
timer = setTimeout(function(){ele.style.display = 'none'},200);
}
}
//±Í«©«–ªª
function setTab(name,cursel,n){
 for(i=1;i<=n;i++){
  var menu=document.getElementById(name+i);
  var con=document.getElementById("con_"+name+"_"+i);
  menu.className=i==cursel?"on":"";
  con.style.display=i==cursel?"block":"none";
 }
}
