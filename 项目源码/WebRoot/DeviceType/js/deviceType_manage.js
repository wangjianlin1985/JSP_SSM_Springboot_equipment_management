var deviceType_manage_tool = null; 
$(function () { 
	initDeviceTypeManageTool(); //建立DeviceType管理对象
	deviceType_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#deviceType_manage").datagrid({
		url : 'DeviceType/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "typeId",
		sortOrder : "desc",
		toolbar : "#deviceType_manage_tool",
		columns : [[
			{
				field : "typeId",
				title : "记录id",
				width : 70,
			},
			{
				field : "typeName",
				title : "类型名称",
				width : 140,
			},
		]],
	});

	$("#deviceTypeEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#deviceTypeEditForm").form("validate")) {
					//验证表单 
					if(!$("#deviceTypeEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#deviceTypeEditForm").form({
						    url:"DeviceType/" + $("#deviceType_typeId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#deviceTypeEditForm").form("validate"))  {
				                	$.messager.progress({
										text : "正在提交数据中...",
									});
				                	return true;
				                } else { 
				                    return false; 
				                }
						    },
						    success:function(data){
						    	$.messager.progress("close");
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#deviceTypeEditDiv").dialog("close");
			                        deviceType_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#deviceTypeEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#deviceTypeEditDiv").dialog("close");
				$("#deviceTypeEditForm").form("reset"); 
			},
		}],
	});
});

function initDeviceTypeManageTool() {
	deviceType_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#deviceType_manage").datagrid("reload");
		},
		redo : function () {
			$("#deviceType_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#deviceType_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#deviceTypeQueryForm").form({
			    url:"DeviceType/OutToExcel",
			});
			//提交表单
			$("#deviceTypeQueryForm").submit();
		},
		remove : function () {
			var rows = $("#deviceType_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var typeIds = [];
						for (var i = 0; i < rows.length; i ++) {
							typeIds.push(rows[i].typeId);
						}
						$.ajax({
							type : "POST",
							url : "DeviceType/deletes",
							data : {
								typeIds : typeIds.join(","),
							},
							beforeSend : function () {
								$("#deviceType_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#deviceType_manage").datagrid("loaded");
									$("#deviceType_manage").datagrid("load");
									$("#deviceType_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#deviceType_manage").datagrid("loaded");
									$("#deviceType_manage").datagrid("load");
									$("#deviceType_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#deviceType_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "DeviceType/" + rows[0].typeId +  "/update",
					type : "get",
					data : {
						//typeId : rows[0].typeId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (deviceType, response, status) {
						$.messager.progress("close");
						if (deviceType) { 
							$("#deviceTypeEditDiv").dialog("open");
							$("#deviceType_typeId_edit").val(deviceType.typeId);
							$("#deviceType_typeId_edit").validatebox({
								required : true,
								missingMessage : "请输入记录id",
								editable: false
							});
							$("#deviceType_typeName_edit").val(deviceType.typeName);
							$("#deviceType_typeName_edit").validatebox({
								required : true,
								missingMessage : "请输入类型名称",
							});
						} else {
							$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
