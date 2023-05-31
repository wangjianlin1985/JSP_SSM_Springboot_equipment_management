<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.SiteBase" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    SiteBase siteBase = (SiteBase)request.getAttribute("siteBase");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>查看站点基本信息详情</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<jsp:include page="../header.jsp"></jsp:include>
<div class="container">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li><a href="<%=basePath %>SiteBase/frontlist">站点基本信息信息</a></li>
  		<li class="active">详情查看</li>
	</ul>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">记录id:</div>
		<div class="col-md-10 col-xs-6"><%=siteBase.getSiteBaseId()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">所属单位:</div>
		<div class="col-md-10 col-xs-6"><%=siteBase.getSsdw()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">归属地:</div>
		<div class="col-md-10 col-xs-6"><%=siteBase.getGsd()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">站点代号:</div>
		<div class="col-md-10 col-xs-6"><%=siteBase.getZddh()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">站点名称:</div>
		<div class="col-md-10 col-xs-6"><%=siteBase.getZdmc()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">站点类别:</div>
		<div class="col-md-10 col-xs-6"><%=siteBase.getZdlb()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">通讯方式:</div>
		<div class="col-md-10 col-xs-6"><%=siteBase.getTxfs()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">安装地点:</div>
		<div class="col-md-10 col-xs-6"><%=siteBase.getZzdd()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">经度:</div>
		<div class="col-md-10 col-xs-6"><%=siteBase.getLongitude()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">纬度:</div>
		<div class="col-md-10 col-xs-6"><%=siteBase.getLatitude()%></div>
	</div>
	
	<div class="row bottom15">
		<div class="col-md-2 col-xs-4 text-right bold">地图显示:</div>
		<div class="col-md-10 col-xs-12">
			<div id="allmap"></div> 
		</div>
	</div> 
	<style>
		#allmap {width: 100%;height: 500px;overflow: scroll;margin:0;}
	</style>
	
	<div class="row bottom15">
		<div class="col-md-2 col-xs-4"></div>
		<div class="col-md-6 col-xs-6">
			<button onclick="history.back();" class="btn btn-primary">返回</button>
		</div>
	</div>
</div> 
<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=uOqdzZgQFY83xmQ4bqIQlixR"></script>

<script>
var basePath = "<%=basePath%>";

BMap.Point.prototype.toString = function(){
	 return "经度=" + this.lng + ",纬度=" + this.lat;
}

function InitMap() { 
  // 百度地图API功能
	var map = new BMap.Map("allmap"); 
	var point = new BMap.Point(<%=siteBase.getLongitude()%>,<%=siteBase.getLatitude() %>);   //苏州大学的经纬度点
	map.centerAndZoom(point, 18);
	map.enableScrollWheelZoom();//启动鼠标滚轮缩放地图
	map.enableKeyboard();//启动键盘操作地图
	
	
	//创建小图标
	var myIcon = new BMap.Icon(basePath + "images/icon.jpg", new BMap.Size(40,40),{anchor:new BMap.Size(20,20),imageOffset:new BMap.Size(0,0)});
	
	var markerOptions = {
		//enableMassClear : false
		enableDragging: true,
		//enableClicking: true,
		//raiseOnDrag: true,
		//draggingCursor: "wait",
		//rotation: 30,
		title: "这是一个标注",
		//icon: myIcon
		
	}; 
	var point2 = new BMap.Point(<%=siteBase.getLongitude()%>,<%=siteBase.getLatitude() %>);
	var marker = new BMap.Marker(point2,markerOptions);
	
	marker.addEventListener("click",function(event) {
		//alert("图像标注被点击了！"); 
		console.log(event.type);
		console.log(event.target.point);		
	});
	
	var menuItem1 = new BMap.MenuItem("菜单1",function() {
		//alert("你点击了菜单1");
		//console.log(marker.getPosition());
		alert(marker.getPosition());
	});
	
	var menuItem2 = new BMap.MenuItem("菜单2",function() {
		alert("你点击了菜单2");
	});

	var contextMenu = new BMap.ContextMenu();
	contextMenu.addItem(menuItem1);
	contextMenu.addItem(menuItem2);
	marker.addContextMenu(contextMenu); 
	
	
	var opts = {    
		width : 250,     // 信息窗口宽度    
		height: 50,     // 信息窗口高度    
		title : "Hello",  // 信息窗口标题  
		enableAutoPan:true,
		//enableMessage: false
		 message:"手机信息：你好"
	}    
	var infoWindow = new BMap.InfoWindow("你好，欢迎来到<%=siteBase.getZdmc() %>！", opts);  // 创建信息窗口对象 
	
	infoWindow.setContent("<%=siteBase.getZdmc() %>欢迎你哦！");
	infoWindow.setTitle("<%=siteBase.getZdmc() %>");

	  
	map.addOverlay(marker);
	marker.openInfoWindow(infoWindow);
	  
}


$(function(){
        /*小屏幕导航点击关闭菜单*/
        $('.navbar-collapse a').click(function(){
            $('.navbar-collapse').collapse('hide');
        });
        new WOW().init();

        InitMap();
 })
 </script> 
</body>
</html>

