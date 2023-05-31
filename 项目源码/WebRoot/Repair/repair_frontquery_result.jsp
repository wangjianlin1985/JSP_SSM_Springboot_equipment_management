<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Repair" %>
<%@ page import="com.chengxusheji.po.DeviceType" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Repair> repairList = (List<Repair>)request.getAttribute("repairList");
    //获取所有的deviceTypeObj信息
    List<DeviceType> deviceTypeList = (List<DeviceType>)request.getAttribute("deviceTypeList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    DeviceType deviceTypeObj = (DeviceType)request.getAttribute("deviceTypeObj");
    String deviceName = (String)request.getAttribute("deviceName"); //设备名称查询关键字
    String jzzt = (String)request.getAttribute("jzzt"); //校准状态查询关键字
    String ssdw = (String)request.getAttribute("ssdw"); //所属单位查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>校准检修查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#repairListPanel" aria-controls="repairListPanel" role="tab" data-toggle="tab">校准检修列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Repair/repair_frontAdd.jsp" style="display:none;">添加校准检修</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="repairListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>设备类型</td><td>设备名称</td><td>校验周期</td><td>使用日期</td><td>校准状态</td><td>校准日期</td><td>所属单位</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<repairList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Repair repair = repairList.get(i); //获取到校准检修对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=repair.getDeviceTypeObj().getTypeName() %></td>
 											<td><%=repair.getDeviceName() %></td>
 											<td><%=repair.getJyzq() %></td>
 											<td><%=repair.getUseDate() %></td>
 											<td><%=repair.getJzzt() %></td>
 											<td><%=repair.getJzrq() %></td>
 											<td><%=repair.getSsdw() %></td>
 											<td>
 												<a href="<%=basePath  %>Repair/<%=repair.getRepairId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="repairEdit('<%=repair.getRepairId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="repairDelete('<%=repair.getRepairId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

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
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>校准检修查询</h1>
		</div>
		<form name="repairQueryForm" id="repairQueryForm" action="<%=basePath %>Repair/frontlist" class="mar_t15">
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
				<label for="jzzt">校准状态:</label>
				<input type="text" id="jzzt" name="jzzt" value="<%=jzzt %>" class="form-control" placeholder="请输入校准状态">
			</div>






			<div class="form-group">
				<label for="ssdw">所属单位:</label>
				<input type="text" id="ssdw" name="ssdw" value="<%=ssdw %>" class="form-control" placeholder="请输入所属单位">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="repairEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;校准检修信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="repairEditForm" id="repairEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="repair_repairId_edit" class="col-md-3 text-right">记录id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="repair_repairId_edit" name="repair.repairId" class="form-control" placeholder="请输入记录id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="repair_deviceTypeObj_typeId_edit" class="col-md-3 text-right">设备类型:</label>
		  	 <div class="col-md-9">
			    <select id="repair_deviceTypeObj_typeId_edit" name="repair.deviceTypeObj.typeId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="repair_deviceName_edit" class="col-md-3 text-right">设备名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="repair_deviceName_edit" name="repair.deviceName" class="form-control" placeholder="请输入设备名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="repair_jyzq_edit" class="col-md-3 text-right">校验周期:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="repair_jyzq_edit" name="repair.jyzq" class="form-control" placeholder="请输入校验周期">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="repair_useDate_edit" class="col-md-3 text-right">使用日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date repair_useDate_edit col-md-12" data-link-field="repair_useDate_edit"  data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="repair_useDate_edit" name="repair.useDate" size="16" type="text" value="" placeholder="请选择使用日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="repair_jzzt_edit" class="col-md-3 text-right">校准状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="repair_jzzt_edit" name="repair.jzzt" class="form-control" placeholder="请输入校准状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="repair_jzrq_edit" class="col-md-3 text-right">校准日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date repair_jzrq_edit col-md-12" data-link-field="repair_jzrq_edit"  data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="repair_jzrq_edit" name="repair.jzrq" size="16" type="text" value="" placeholder="请选择校准日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="repair_ssdw_edit" class="col-md-3 text-right">所属单位:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="repair_ssdw_edit" name="repair.ssdw" class="form-control" placeholder="请输入所属单位">
			 </div>
		  </div>
		</form> 
	    <style>#repairEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxRepairModify();">提交</button>
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
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.repairQueryForm.currentPage.value = currentPage;
    document.repairQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.repairQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.repairQueryForm.currentPage.value = pageValue;
    documentrepairQueryForm.submit();
}

/*弹出修改校准检修界面并初始化数据*/
function repairEdit(repairId) {
	$.ajax({
		url :  basePath + "Repair/" + repairId + "/update",
		type : "get",
		dataType: "json",
		success : function (repair, response, status) {
			if (repair) {
				$("#repair_repairId_edit").val(repair.repairId);
				$.ajax({
					url: basePath + "DeviceType/listAll",
					type: "get",
					success: function(deviceTypes,response,status) { 
						$("#repair_deviceTypeObj_typeId_edit").empty();
						var html="";
		        		$(deviceTypes).each(function(i,deviceType){
		        			html += "<option value='" + deviceType.typeId + "'>" + deviceType.typeName + "</option>";
		        		});
		        		$("#repair_deviceTypeObj_typeId_edit").html(html);
		        		$("#repair_deviceTypeObj_typeId_edit").val(repair.deviceTypeObjPri);
					}
				});
				$("#repair_deviceName_edit").val(repair.deviceName);
				$("#repair_jyzq_edit").val(repair.jyzq);
				$("#repair_useDate_edit").val(repair.useDate);
				$("#repair_jzzt_edit").val(repair.jzzt);
				$("#repair_jzrq_edit").val(repair.jzrq);
				$("#repair_ssdw_edit").val(repair.ssdw);
				$('#repairEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除校准检修信息*/
function repairDelete(repairId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Repair/deletes",
			data : {
				repairIds : repairId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#repairQueryForm").submit();
					//location.href= basePath + "Repair/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交校准检修信息表单给服务器端修改*/
function ajaxRepairModify() {
	$.ajax({
		url :  basePath + "Repair/" + $("#repair_repairId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#repairEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#repairQueryForm").submit();
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
    $('.repair_useDate_edit').datetimepicker({
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
    /*校准日期组件*/
    $('.repair_jzrq_edit').datetimepicker({
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
})
</script>
</body>
</html>

