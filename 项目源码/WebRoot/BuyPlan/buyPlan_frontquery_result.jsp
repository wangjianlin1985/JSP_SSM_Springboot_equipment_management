<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.BuyPlan" %>
<%@ page import="com.chengxusheji.po.DeviceType" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<BuyPlan> buyPlanList = (List<BuyPlan>)request.getAttribute("buyPlanList");
    //获取所有的deviceTypeObj信息
    List<DeviceType> deviceTypeList = (List<DeviceType>)request.getAttribute("deviceTypeList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    DeviceType deviceTypeObj = (DeviceType)request.getAttribute("deviceTypeObj");
    String deviceName = (String)request.getAttribute("deviceName"); //设备名称查询关键字
    String sydw = (String)request.getAttribute("sydw"); //使用单位查询关键字
    String jhsj = (String)request.getAttribute("jhsj"); //计划时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>购置计划查询</title>
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
			    	<li role="presentation" class="active"><a href="#buyPlanListPanel" aria-controls="buyPlanListPanel" role="tab" data-toggle="tab">购置计划列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>BuyPlan/buyPlan_frontAdd.jsp" style="display:none;">添加购置计划</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="buyPlanListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>设备类型</td><td>设备名称</td><td>数量</td><td>金额</td><td>规格型号</td><td>用途</td><td>使用单位</td><td>计划时间</td><td>是否购置</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<buyPlanList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		BuyPlan buyPlan = buyPlanList.get(i); //获取到购置计划对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=buyPlan.getDeviceTypeObj().getTypeName() %></td>
 											<td><%=buyPlan.getDeviceName() %></td>
 											<td><%=buyPlan.getBuyNum() %></td>
 											<td><%=buyPlan.getBuyMoney() %></td>
 											<td><%=buyPlan.getGuige() %></td>
 											<td><%=buyPlan.getYongtu() %></td>
 											<td><%=buyPlan.getSydw() %></td>
 											<td><%=buyPlan.getJhsj() %></td>
 											<td><%=buyPlan.getSfgz() %></td>
 											<td>
 												<a href="<%=basePath  %>BuyPlan/<%=buyPlan.getPlanId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="buyPlanEdit('<%=buyPlan.getPlanId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="buyPlanDelete('<%=buyPlan.getPlanId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
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
    		<h1>购置计划查询</h1>
		</div>
		<form name="buyPlanQueryForm" id="buyPlanQueryForm" action="<%=basePath %>BuyPlan/frontlist" class="mar_t15">
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
				<label for="sydw">使用单位:</label>
				<input type="text" id="sydw" name="sydw" value="<%=sydw %>" class="form-control" placeholder="请输入使用单位">
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
<div id="buyPlanEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;购置计划信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="buyPlanEditForm" id="buyPlanEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="buyPlan_planId_edit" class="col-md-3 text-right">记录id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="buyPlan_planId_edit" name="buyPlan.planId" class="form-control" placeholder="请输入记录id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="buyPlan_deviceTypeObj_typeId_edit" class="col-md-3 text-right">设备类型:</label>
		  	 <div class="col-md-9">
			    <select id="buyPlan_deviceTypeObj_typeId_edit" name="buyPlan.deviceTypeObj.typeId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="buyPlan_deviceName_edit" class="col-md-3 text-right">设备名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="buyPlan_deviceName_edit" name="buyPlan.deviceName" class="form-control" placeholder="请输入设备名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="buyPlan_buyNum_edit" class="col-md-3 text-right">数量:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="buyPlan_buyNum_edit" name="buyPlan.buyNum" class="form-control" placeholder="请输入数量">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="buyPlan_buyMoney_edit" class="col-md-3 text-right">金额:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="buyPlan_buyMoney_edit" name="buyPlan.buyMoney" class="form-control" placeholder="请输入金额">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="buyPlan_guige_edit" class="col-md-3 text-right">规格型号:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="buyPlan_guige_edit" name="buyPlan.guige" class="form-control" placeholder="请输入规格型号">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="buyPlan_yongtu_edit" class="col-md-3 text-right">用途:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="buyPlan_yongtu_edit" name="buyPlan.yongtu" class="form-control" placeholder="请输入用途">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="buyPlan_sydw_edit" class="col-md-3 text-right">使用单位:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="buyPlan_sydw_edit" name="buyPlan.sydw" class="form-control" placeholder="请输入使用单位">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="buyPlan_jhsj_edit" class="col-md-3 text-right">计划时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date buyPlan_jhsj_edit col-md-12" data-link-field="buyPlan_jhsj_edit">
                    <input class="form-control" id="buyPlan_jhsj_edit" name="buyPlan.jhsj" size="16" type="text" value="" placeholder="请选择计划时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="buyPlan_sfgz_edit" class="col-md-3 text-right">是否购置:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="buyPlan_sfgz_edit" name="buyPlan.sfgz" class="form-control" placeholder="请输入是否购置">
			 </div>
		  </div>
		</form> 
	    <style>#buyPlanEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxBuyPlanModify();">提交</button>
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
    document.buyPlanQueryForm.currentPage.value = currentPage;
    document.buyPlanQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.buyPlanQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.buyPlanQueryForm.currentPage.value = pageValue;
    documentbuyPlanQueryForm.submit();
}

/*弹出修改购置计划界面并初始化数据*/
function buyPlanEdit(planId) {
	$.ajax({
		url :  basePath + "BuyPlan/" + planId + "/update",
		type : "get",
		dataType: "json",
		success : function (buyPlan, response, status) {
			if (buyPlan) {
				$("#buyPlan_planId_edit").val(buyPlan.planId);
				$.ajax({
					url: basePath + "DeviceType/listAll",
					type: "get",
					success: function(deviceTypes,response,status) { 
						$("#buyPlan_deviceTypeObj_typeId_edit").empty();
						var html="";
		        		$(deviceTypes).each(function(i,deviceType){
		        			html += "<option value='" + deviceType.typeId + "'>" + deviceType.typeName + "</option>";
		        		});
		        		$("#buyPlan_deviceTypeObj_typeId_edit").html(html);
		        		$("#buyPlan_deviceTypeObj_typeId_edit").val(buyPlan.deviceTypeObjPri);
					}
				});
				$("#buyPlan_deviceName_edit").val(buyPlan.deviceName);
				$("#buyPlan_buyNum_edit").val(buyPlan.buyNum);
				$("#buyPlan_buyMoney_edit").val(buyPlan.buyMoney);
				$("#buyPlan_guige_edit").val(buyPlan.guige);
				$("#buyPlan_yongtu_edit").val(buyPlan.yongtu);
				$("#buyPlan_sydw_edit").val(buyPlan.sydw);
				$("#buyPlan_jhsj_edit").val(buyPlan.jhsj);
				$("#buyPlan_sfgz_edit").val(buyPlan.sfgz);
				$('#buyPlanEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除购置计划信息*/
function buyPlanDelete(planId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "BuyPlan/deletes",
			data : {
				planIds : planId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#buyPlanQueryForm").submit();
					//location.href= basePath + "BuyPlan/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交购置计划信息表单给服务器端修改*/
function ajaxBuyPlanModify() {
	$.ajax({
		url :  basePath + "BuyPlan/" + $("#buyPlan_planId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#buyPlanEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#buyPlanQueryForm").submit();
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

    /*计划时间组件*/
    $('.buyPlan_jhsj_edit').datetimepicker({
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

