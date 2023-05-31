<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/deviceType.css" />
<div id="deviceTypeAddDiv">
	<form id="deviceTypeAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">类型名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="deviceType_typeName" name="deviceType.typeName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="deviceTypeAddButton" class="easyui-linkbutton">添加</a>
			<a id="deviceTypeClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/DeviceType/js/deviceType_add.js"></script> 
