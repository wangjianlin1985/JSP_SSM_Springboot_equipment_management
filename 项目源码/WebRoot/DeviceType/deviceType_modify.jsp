<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/deviceType.css" />
<div id="deviceType_editDiv">
	<form id="deviceTypeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="deviceType_typeId_edit" name="deviceType.typeId" value="<%=request.getParameter("typeId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">类型名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="deviceType_typeName_edit" name="deviceType.typeName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="deviceTypeModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/DeviceType/js/deviceType_modify.js"></script> 
