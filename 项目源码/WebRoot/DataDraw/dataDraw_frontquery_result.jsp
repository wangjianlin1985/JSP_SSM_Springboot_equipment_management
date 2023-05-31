<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.DataDraw" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<DataDraw> dataDrawList = (List<DataDraw>)request.getAttribute("dataDrawList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String ssdw = (String)request.getAttribute("ssdw"); //所属单位查询关键字
    String drawClass = (String)request.getAttribute("drawClass"); //图纸类别查询关键字
    String drawName = (String)request.getAttribute("drawName"); //图纸名称查询关键字
    String drawDesc = (String)request.getAttribute("drawDesc"); //图纸描述查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>资料图纸查询</title>
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
			    	<li role="presentation" class="active"><a href="#dataDrawListPanel" aria-controls="dataDrawListPanel" role="tab" data-toggle="tab">资料图纸列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>DataDraw/dataDraw_frontAdd.jsp" style="display:none;">添加资料图纸</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="dataDrawListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>所属单位</td><td>图纸类别</td><td>图纸名称</td><td>图纸描述</td><td>图纸文件</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<dataDrawList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		DataDraw dataDraw = dataDrawList.get(i); //获取到资料图纸对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=dataDraw.getSsdw() %></td>
 											<td><%=dataDraw.getDrawClass() %></td>
 											<td><%=dataDraw.getDrawName() %></td>
 											<td><%=dataDraw.getDrawDesc() %></td>
 											<td><%=dataDraw.getDrawFile().equals("")?"暂无文件":"<a href='" + basePath + dataDraw.getDrawFile() + "' target='_blank'>" + dataDraw.getDrawFile() + "</a>"%>
 											<td>
 												<a href="<%=basePath  %>DataDraw/<%=dataDraw.getDrawId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="dataDrawEdit('<%=dataDraw.getDrawId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="dataDrawDelete('<%=dataDraw.getDrawId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
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
    		<h1>资料图纸查询</h1>
		</div>
		<form name="dataDrawQueryForm" id="dataDrawQueryForm" action="<%=basePath %>DataDraw/frontlist" class="mar_t15">
			<div class="form-group">
				<label for="ssdw">所属单位:</label>
				<input type="text" id="ssdw" name="ssdw" value="<%=ssdw %>" class="form-control" placeholder="请输入所属单位">
			</div>






			<div class="form-group">
				<label for="drawClass">图纸类别:</label>
				<input type="text" id="drawClass" name="drawClass" value="<%=drawClass %>" class="form-control" placeholder="请输入图纸类别">
			</div>






			<div class="form-group">
				<label for="drawName">图纸名称:</label>
				<input type="text" id="drawName" name="drawName" value="<%=drawName %>" class="form-control" placeholder="请输入图纸名称">
			</div>






			<div class="form-group">
				<label for="drawDesc">图纸描述:</label>
				<input type="text" id="drawDesc" name="drawDesc" value="<%=drawDesc %>" class="form-control" placeholder="请输入图纸描述">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="dataDrawEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;资料图纸信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="dataDrawEditForm" id="dataDrawEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="dataDraw_drawId_edit" class="col-md-3 text-right">记录id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="dataDraw_drawId_edit" name="dataDraw.drawId" class="form-control" placeholder="请输入记录id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="dataDraw_ssdw_edit" class="col-md-3 text-right">所属单位:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dataDraw_ssdw_edit" name="dataDraw.ssdw" class="form-control" placeholder="请输入所属单位">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dataDraw_drawClass_edit" class="col-md-3 text-right">图纸类别:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dataDraw_drawClass_edit" name="dataDraw.drawClass" class="form-control" placeholder="请输入图纸类别">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dataDraw_drawName_edit" class="col-md-3 text-right">图纸名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dataDraw_drawName_edit" name="dataDraw.drawName" class="form-control" placeholder="请输入图纸名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dataDraw_drawDesc_edit" class="col-md-3 text-right">图纸描述:</label>
		  	 <div class="col-md-9">
			    <textarea id="dataDraw_drawDesc_edit" name="dataDraw.drawDesc" rows="8" class="form-control" placeholder="请输入图纸描述"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dataDraw_drawFile_edit" class="col-md-3 text-right">图纸文件:</label>
		  	 <div class="col-md-9">
			    <a id="dataDraw_drawFileA" target="_blank"></a><br/>
			    <input type="hidden" id="dataDraw_drawFile" name="dataDraw.drawFile"/>
			    <input id="drawFileFile" name="drawFileFile" type="file" size="50" />
		  	 </div>
		  </div>
		</form> 
	    <style>#dataDrawEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxDataDrawModify();">提交</button>
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
    document.dataDrawQueryForm.currentPage.value = currentPage;
    document.dataDrawQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.dataDrawQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.dataDrawQueryForm.currentPage.value = pageValue;
    documentdataDrawQueryForm.submit();
}

/*弹出修改资料图纸界面并初始化数据*/
function dataDrawEdit(drawId) {
	$.ajax({
		url :  basePath + "DataDraw/" + drawId + "/update",
		type : "get",
		dataType: "json",
		success : function (dataDraw, response, status) {
			if (dataDraw) {
				$("#dataDraw_drawId_edit").val(dataDraw.drawId);
				$("#dataDraw_ssdw_edit").val(dataDraw.ssdw);
				$("#dataDraw_drawClass_edit").val(dataDraw.drawClass);
				$("#dataDraw_drawName_edit").val(dataDraw.drawName);
				$("#dataDraw_drawDesc_edit").val(dataDraw.drawDesc);
				$("#dataDraw_drawFile").val(dataDraw.drawFile);
				$("#dataDraw_drawFileA").text(dataDraw.drawFile);
				$("#dataDraw_drawFileA").attr("href", basePath +　dataDraw.drawFile);
				$('#dataDrawEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除资料图纸信息*/
function dataDrawDelete(drawId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "DataDraw/deletes",
			data : {
				drawIds : drawId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#dataDrawQueryForm").submit();
					//location.href= basePath + "DataDraw/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交资料图纸信息表单给服务器端修改*/
function ajaxDataDrawModify() {
	$.ajax({
		url :  basePath + "DataDraw/" + $("#dataDraw_drawId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#dataDrawEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#dataDrawQueryForm").submit();
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

})
</script>
</body>
</html>

