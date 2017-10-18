var MarginLeft = 30;   //浮动层离浏览器右侧的距离
var MarginTop = 50;     //浮动层离浏览器顶部的距离 
var Width = 200;        //浮动层宽度
var Heigth= 130;        //浮动层高度
var field_name = 'pur'; 

//设置浮动层宽、高
function Set()
{

	$('#'+field_name).css({'opacity':'0.8'});  
    $('#'+field_name).width(Width);
    $('#'+field_name).height(Heigth);
}

//实时设置浮动层的位置
function Move()
{
	$('#'+field_name).css('top',document.documentElement.scrollTop + MarginTop);    
    setTimeout("Move();",100); 
  
}

Set();
Move();