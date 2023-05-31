<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.SiteRun" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<SiteRun> siteRunList = (List<SiteRun>)request.getAttribute("siteRunList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String ssdw = (String)request.getAttribute("ssdw"); //所属单位查询关键字
    String zddh = (String)request.getAttribute("zddh"); //站点代号查询关键字
    String zdlb = (String)request.getAttribute("zdlb"); //站点类别查询关键字
    String zdmc = (String)request.getAttribute("zdmc"); //站点名称查询关键字
    String gzlx = (String)request.getAttribute("gzlx"); //故障类型查询关键字
    String gzsj = (String)request.getAttribute("gzsj"); //故障时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>运行情况查询</title>
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
			    	<li role="presentation" class="active"><a href="#siteRunListPanel" aria-controls="siteRunListPanel" role="tab" data-toggle="tab">运行情况列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>SiteRun/siteRun_frontAdd.jsp" style="display:none;">添加运行情况</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="siteRunListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>所属单位</td><td>站点代号</td><td>站点类别</td><td>站点名称</td><td>故障类型</td><td>故障时间</td><td>故障时长</td><td>恢复情况</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<siteRunList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		SiteRun siteRun = siteRunList.get(i); //获取到运行情况对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=siteRun.getSsdw() %></td>
 											<td><%=siteRun.getZddh() %></td>
 											<td><%=siteRun.getZdlb() %></td>
 											<td><%=siteRun.getZdmc() %></td>
 											<td><%=siteRun.getGzlx() %></td>
 											<td><%=siteRun.getGzsj() %></td>
 											<td><%=siteRun.getGzsc() %></td>
 											<td><%=siteRun.getHfqk() %></td>
 											<td>
 												<a href="<%=basePath  %>SiteRun/<%=siteRun.getSiteRunId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="siteRunEdit('<%=siteRun.getSiteRunId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="siteRunDelete('<%=siteRun.getSiteRunId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
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
    		<h1>运行情况查询</h1>
		</div>
		<form name="siteRunQueryForm" id="siteRunQueryForm" action="<%=basePath %>SiteRun/frontlist" class="mar_t15">
			<div class="form-group">
				<label for="ssdw">所属单位:</label>
				<input type="text" id="ssdw" name="ssdw" value="<%=ssdw %>" class="form-control" placeholder="请输入所属单位">
			</div>






			<div class="form-group">
				<label for="zddh">站点代号:</label>
				<input type="text" id="zddh" name="zddh" value="<%=zddh %>" class="form-control" placeholder="请输入站点代号">
			</div>






			<div class="form-group">
				<label for="zdlb">站点类别:</label>
				<input type="text" id="zdlb" name="zdlb" value="<%=zdlb %>" class="form-control" placeholder="请输入站点类别">
			</div>






			<div class="form-group">
				<label for="zdmc">站点名称:</label>
				<input type="text" id="zdmc" name="zdmc" value="<%=zdmc %>" class="form-control" placeholder="请输入站点名称">
			</div>






			<div class="form-group">
				<label for="gzlx">故障类型:</label>
				<input type="text" id="gzlx" name="gzlx" value="<%=gzlx %>" class="form-control" placeholder="请输入故障类型">
			</div>






			<div class="form-group">
				<label for="gzsj">故障时间:</label>
				<input type="text" id="gzsj" name="gzsj" class="form-control"  placeholder="请选择故障时间" value="<%=gzsj %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="siteRunEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;运行情况信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="siteRunEditForm" id="siteRunEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="siteRun_siteRunId_edit" class="col-md-3 text-right">记录id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="siteRun_siteRunId_edit" name="siteRun.siteRunId" class="form-control" placeholder="请输入记录id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="siteRun_ssdw_edit" class="col-md-3 text-right">所属单位:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="siteRun_ssdw_edit" name="siteRun.ssdw" class="form-control" placeholder="请输入所属单位">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="siteRun_zddh_edit" class="col-md-3 text-right">站点代号:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="siteRun_zddh_edit" name="siteRun.zddh" class="form-control" placeholder="请输入站点代号">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="siteRun_zdlb_edit" class="col-md-3 text-right">站点类别:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="siteRun_zdlb_edit" name="siteRun.zdlb" class="form-control" placeholder="请输入站点类别">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="siteRun_zdmc_edit" class="col-md-3 text-right">站点名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="siteRun_zdmc_edit" name="siteRun.zdmc" class="form-control" placeholder="请输入站点名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="siteRun_gzlx_edit" class="col-md-3 text-right">故障类型:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="siteRun_gzlx_edit" name="siteRun.gzlx" class="form-control" placeholder="请输入故障类型">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="siteRun_gzsj_edit" class="col-md-3 text-right">故障时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date siteRun_gzsj_edit col-md-12" data-link-field="siteRun_gzsj_edit">
                    <input class="form-control" id="siteRun_gzsj_edit" name="siteRun.gzsj" size="16" type="text" value="" placeholder="请选择故障时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="siteRun_gzsc_edit" class="col-md-3 text-right">故障时长:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="siteRun_gzsc_edit" name="siteRun.gzsc" class="form-control" placeholder="请输入故障时长">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="siteRun_hfqk_edit" class="col-md-3 text-right">恢复情况:</label>
		  	 <div class="col-md-9">
			    <textarea id="siteRun_hfqk_edit" name="siteRun.hfqk" rows="8" class="form-control" placeholder="请输入恢复情况"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#siteRunEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxSiteRunModify();">提交</button>
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
    document.siteRunQueryForm.currentPage.value = currentPage;
    document.siteRunQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.siteRunQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.siteRunQueryForm.currentPage.value = pageValue;
    documentsiteRunQueryForm.submit();
}

/*弹出修改运行情况界面并初始化数据*/
function siteRunEdit(siteRunId) {
	$.ajax({
		url :  basePath + "SiteRun/" + siteRunId + "/update",
		type : "get",
		dataType: "json",
		success : function (siteRun, response, status) {
			if (siteRun) {
				$("#siteRun_siteRunId_edit").val(siteRun.siteRunId);
				$("#siteRun_ssdw_edit").val(siteRun.ssdw);
				$("#siteRun_zddh_edit").val(siteRun.zddh);
				$("#siteRun_zdlb_edit").val(siteRun.zdlb);
				$("#siteRun_zdmc_edit").val(siteRun.zdmc);
				$("#siteRun_gzlx_edit").val(siteRun.gzlx);
				$("#siteRun_gzsj_edit").val(siteRun.gzsj);
				$("#siteRun_gzsc_edit").val(siteRun.gzsc);
				$("#siteRun_hfqk_edit").val(siteRun.hfqk);
				$('#siteRunEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除运行情况信息*/
function siteRunDelete(siteRunId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "SiteRun/deletes",
			data : {
				siteRunIds : siteRunId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#siteRunQueryForm").submit();
					//location.href= basePath + "SiteRun/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交运行情况信息表单给服务器端修改*/
function ajaxSiteRunModify() {
	$.ajax({
		url :  basePath + "SiteRun/" + $("#siteRun_siteRunId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#siteRunEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#siteRunQueryForm").submit();
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

    /*故障时间组件*/
    $('.siteRun_gzsj_edit').datetimepicker({
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

