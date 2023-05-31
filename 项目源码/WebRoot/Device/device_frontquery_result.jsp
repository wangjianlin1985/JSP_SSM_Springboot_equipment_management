<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Device" %>
<%@ page import="com.chengxusheji.po.DeviceType" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Device> deviceList = (List<Device>)request.getAttribute("deviceList");
    //获取所有的deviceTypeObj信息
    List<DeviceType> deviceTypeList = (List<DeviceType>)request.getAttribute("deviceTypeList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    DeviceType deviceTypeObj = (DeviceType)request.getAttribute("deviceTypeObj");
    String deviceName = (String)request.getAttribute("deviceName"); //设备名称查询关键字
    String deviceState = (String)request.getAttribute("deviceState"); //设备状态查询关键字
    String useDate = (String)request.getAttribute("useDate"); //使用日期查询关键字
    String ssdw = (String)request.getAttribute("ssdw"); //所属单位查询关键字
    String jhsj = (String)request.getAttribute("jhsj"); //计划时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>仪器设备查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>Device/frontlist">仪器设备信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Device/device_frontAdd.jsp" style="display:none;">添加仪器设备</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<deviceList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Device device = deviceList.get(i); //获取到仪器设备对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Device/<%=device.getDeviceId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=device.getDevicePhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		设备类型:<%=device.getDeviceTypeObj().getTypeName() %>
			     	</div>
			     	<div class="field">
	            		设备名称:<%=device.getDeviceName() %>
			     	</div>
			     	<div class="field">
	            		规格型号:<%=device.getGuige() %>
			     	</div>
			     	<div class="field">
	            		设备状态:<%=device.getDeviceState() %>
			     	</div>
			     	<div class="field">
	            		使用日期:<%=device.getUseDate() %>
			     	</div>
			     	<div class="field">
	            		检修周期:<%=device.getJxzq() %>
			     	</div>
			     	<div class="field">
	            		所属单位:<%=device.getSsdw() %>
			     	</div>
			     	<div class="field">
	            		计划时间:<%=device.getJhsj() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Device/<%=device.getDeviceId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="deviceEdit('<%=device.getDeviceId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="deviceDelete('<%=device.getDeviceId() %>');" style="display:none;">删除</a>
			     </div>
			</div>
			<%  } %>

			<div class="row">
				<div class="col-md-12">
					<nav class="pull-left">
						<ul class="pagination">
							<li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
							<%
								int startPage = currentPage - 5;
								int endPage = currentPage + 5;
								if(startPage < 1) startPage=1;
								if(endPage > totalPage) endPage = totalPage;
								for(int i=startPage;i<=endPage;i++) {
							%>
							<li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
							<%  } %> 
							<li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						</ul>
					</nav>
					<div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>仪器设备查询</h1>
		</div>
		<form name="deviceQueryForm" id="deviceQueryForm" action="<%=basePath %>Device/frontlist" class="mar_t15">
            <div class="form-group">
            	<label for="deviceTypeObj_typeId">设备类型：</label>
                <select id="deviceTypeObj_typeId" name="deviceTypeObj.typeId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(DeviceType deviceTypeTemp:deviceTypeList) {
	 					String selected = "";
 					if(deviceTypeObj!=null && deviceTypeObj.getTypeId()!=null && deviceTypeObj.getTypeId().intValue()==deviceTypeTemp.getTypeId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=deviceTypeTemp.getTypeId() %>" <%=selected %>><%=deviceTypeTemp.getTypeName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="deviceName">设备名称:</label>
				<input type="text" id="deviceName" name="deviceName" value="<%=deviceName %>" class="form-control" placeholder="请输入设备名称">
			</div>
			<div class="form-group">
				<label for="deviceState">设备状态:</label>
				<input type="text" id="deviceState" name="deviceState" value="<%=deviceState %>" class="form-control" placeholder="请输入设备状态">
			</div>
			<div class="form-group">
				<label for="useDate">使用日期:</label>
				<input type="text" id="useDate" name="useDate" class="form-control"  placeholder="请选择使用日期" value="<%=useDate %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="ssdw">所属单位:</label>
				<input type="text" id="ssdw" name="ssdw" value="<%=ssdw %>" class="form-control" placeholder="请输入所属单位">
			</div>
			<div class="form-group">
				<label for="jhsj">计划时间:</label>
				<input type="text" id="jhsj" name="jhsj" class="form-control"  placeholder="请选择计划时间" value="<%=jhsj %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="deviceEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;仪器设备信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="deviceEditForm" id="deviceEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="device_deviceId_edit" class="col-md-3 text-right">记录id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="device_deviceId_edit" name="device.deviceId" class="form-control" placeholder="请输入记录id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="device_deviceTypeObj_typeId_edit" class="col-md-3 text-right">设备类型:</label>
		  	 <div class="col-md-9">
			    <select id="device_deviceTypeObj_typeId_edit" name="device.deviceTypeObj.typeId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="device_deviceName_edit" class="col-md-3 text-right">设备名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="device_deviceName_edit" name="device.deviceName" class="form-control" placeholder="请输入设备名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="device_devicePhoto_edit" class="col-md-3 text-right">设备图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="device_devicePhotoImg" border="0px"/><br/>
			    <input type="hidden" id="device_devicePhoto" name="device.devicePhoto"/>
			    <input id="devicePhotoFile" name="devicePhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="device_guige_edit" class="col-md-3 text-right">规格型号:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="device_guige_edit" name="device.guige" class="form-control" placeholder="请输入规格型号">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="device_deviceState_edit" class="col-md-3 text-right">设备状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="device_deviceState_edit" name="device.deviceState" class="form-control" placeholder="请输入设备状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="device_useDate_edit" class="col-md-3 text-right">使用日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date device_useDate_edit col-md-12" data-link-field="device_useDate_edit" data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="device_useDate_edit" name="device.useDate" size="16" type="text" value="" placeholder="请选择使用日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="device_jxzq_edit" class="col-md-3 text-right">检修周期:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="device_jxzq_edit" name="device.jxzq" class="form-control" placeholder="请输入检修周期">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="device_ssdw_edit" class="col-md-3 text-right">所属单位:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="device_ssdw_edit" name="device.ssdw" class="form-control" placeholder="请输入所属单位">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="device_jhsj_edit" class="col-md-3 text-right">计划时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date device_jhsj_edit col-md-12" data-link-field="device_jhsj_edit">
                    <input class="form-control" id="device_jhsj_edit" name="device.jhsj" size="16" type="text" value="" placeholder="请选择计划时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="device_deviceDesc_edit" class="col-md-3 text-right">设备描述:</label>
		  	 <div class="col-md-9">
			 	<textarea name="device.deviceDesc" id="device_deviceDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#deviceEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxDeviceModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var device_deviceDesc_edit = UE.getEditor('device_deviceDesc_edit'); //设备描述编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.deviceQueryForm.currentPage.value = currentPage;
    document.deviceQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.deviceQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.deviceQueryForm.currentPage.value = pageValue;
    documentdeviceQueryForm.submit();
}

/*弹出修改仪器设备界面并初始化数据*/
function deviceEdit(deviceId) {
	$.ajax({
		url :  basePath + "Device/" + deviceId + "/update",
		type : "get",
		dataType: "json",
		success : function (device, response, status) {
			if (device) {
				$("#device_deviceId_edit").val(device.deviceId);
				$.ajax({
					url: basePath + "DeviceType/listAll",
					type: "get",
					success: function(deviceTypes,response,status) { 
						$("#device_deviceTypeObj_typeId_edit").empty();
						var html="";
		        		$(deviceTypes).each(function(i,deviceType){
		        			html += "<option value='" + deviceType.typeId + "'>" + deviceType.typeName + "</option>";
		        		});
		        		$("#device_deviceTypeObj_typeId_edit").html(html);
		        		$("#device_deviceTypeObj_typeId_edit").val(device.deviceTypeObjPri);
					}
				});
				$("#device_deviceName_edit").val(device.deviceName);
				$("#device_devicePhoto").val(device.devicePhoto);
				$("#device_devicePhotoImg").attr("src", basePath +　device.devicePhoto);
				$("#device_guige_edit").val(device.guige);
				$("#device_deviceState_edit").val(device.deviceState);
				$("#device_useDate_edit").val(device.useDate);
				$("#device_jxzq_edit").val(device.jxzq);
				$("#device_ssdw_edit").val(device.ssdw);
				$("#device_jhsj_edit").val(device.jhsj);
				device_deviceDesc_edit.setContent(device.deviceDesc, false);
				$('#deviceEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除仪器设备信息*/
function deviceDelete(deviceId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Device/deletes",
			data : {
				deviceIds : deviceId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#deviceQueryForm").submit();
					//location.href= basePath + "Device/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交仪器设备信息表单给服务器端修改*/
function ajaxDeviceModify() {
	$.ajax({
		url :  basePath + "Device/" + $("#device_deviceId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#deviceEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#deviceQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*使用日期组件*/
    $('.device_useDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    /*计划时间组件*/
    $('.device_jhsj_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

