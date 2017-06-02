<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body style="font-family: '微软雅黑'">
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<form id="searchFrom" action="">
				<input type="text" name="filter_INI_id" value="${bossScheduleChange.id}" class="easyui-validatebox"
					data-options="width:60,prompt: 'id'" onchange="trimLeft(this)" />
				<select name="filter_EQI_tableType">
					<option value="">对应表</option>
					<c:forEach items="${applicationScope.sysParam.tableTypeMap}" var="tableTypeMap">
						<option value="${tableTypeMap.key}">${tableTypeMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span>
				<input type="text" name="filter_EQI_tableId" value="${bossScheduleChange.tableId}" class="easyui-validatebox"
					data-options="width:80,prompt: '表id'" onchange="trimLeft(this)" />
				<select name="filter_EQI_type">
					<option value="">修改类型</option>
					<c:forEach items="${applicationScope.sysParam.changeTypeMap}" var="changeTypeMap">
						<option value="${changeTypeMap.key}">${changeTypeMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_EQI_status">
					<option value="">是否生效</option>
					<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
						<option value="${isNoMap.key}">${isNoMap.value}</option>
					</c:forEach>
					<option value="2">已生效</option>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span>
				<input type="text" name="filter_GED_changeTime" class="easyui-my97" startDate='%y-%M-%d 00:00:00'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '修改时间'" />
				--
				<input type="text" name="filter_LED_changeTime" class="easyui-my97" startDate='%y-%M-%d 23:59:59'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '修改时间'" />
				<span class="toolbar-item dialog-tool-separator"></span>
				<input type="text" name="filter_EQM_changeNum" class="easyui-validatebox" data-options="width:80,prompt: '折扣数'"
					onchange="trimLeft(this)" />
				<input type="text" name="filter_LIKES_remark" class="easyui-validatebox" data-options="width:80,prompt: '备注'"
					onchange="trimLeft(this)" />
				<input type="text" name="filter_GED_addTime" class="easyui-my97" startDate='%y-%M-%d 00:00:00' datefmt="yyyy-MM-dd HH:mm:ss"
					data-options="width:150,prompt: '添加时间'" />
				--
				<input type="text" name="filter_LED_addTime" class="easyui-my97" startDate='%y-%M-%d 23:59:59' datefmt="yyyy-MM-dd HH:mm:ss"
					data-options="width:150,prompt: '添加时间'" />
				<span class="toolbar-item dialog-tool-separator"></span>

				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" onclick="cx()">查询</a> <span class="toolbar-item dialog-tool-separator"></span>
				<!-- -->
				<input type="reset" value="重置" onclick="resetValue()" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp; <a href="javascript(0)" class="easyui-linkbutton"
					onclick="alertWindowCommon(this,'${ctx}/boss/bossScheduleChange/list?page=Discount','${ctx}/boss/bossScheduleChange/alertWindow?method=discount',true)">修改折扣</a>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> <a href="javascript(0)" class="easyui-linkbutton"
					onclick='sendPostByUrlManyCommon(this,"${ctx}/boss/bossScheduleChange/updateStatus",1,true)'>生效</a>
				<!-- -->
				<a href="javascript(0)" class="easyui-linkbutton"
					onclick='sendPostByUrlManyCommon(this,"${ctx}/boss/bossScheduleChange/updateStatus",0,true)'>不生效</a>
				<!-- -->
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:bossScheduleChange:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
					onclick="addCommon('${ctx}/boss/bossScheduleChange/list?page=Add');">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossScheduleChange:delete">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
					onclick="del('bossScheduleChange/delete')">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossScheduleChange:update">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
					onclick="updCommon('${ctx}/boss/bossScheduleChange/list?page=Update')">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossScheduleChange:exportExcel">
				<!--			<span class="toolbar-item dialog-tool-separator"></span> -->
				<!--			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true" onclick="exportExcel('bossScheduleChange/exportExcel')">导出Excel</a> -->
			</shiro:hasPermission>
		</div>

	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
		var dg;
		$(function() {
			dg = $('#dg').datagrid(
					{
						method : 'POST',
						url : '${ctx}/boss/bossScheduleChange/bossScheduleChangeList',
						fit : true,
						fitColumns : true,//自动填充,如果字段过多就改为false
						queryParams : $("#searchFrom").serializeObject(),
						border : false,
						striped : true,
						idField : 'id',
						pagination : true,
						rownumbers : true,
						queryParams : {},
						pageNumber : 1,
						pageSize : 20,
						pageList : [
								10, 20, 30, 40, 50
						],
						singleSelect : false,
						columns : [
							[
									{ field : 'ck', checkbox : true },
									{ field : 'id', title : 'id', width : 80, sortable : true },
									{ field : 'tableType', title : '对应表', width : 100, sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.tableTypeMapJson}, function(key, val) {
												if (key == value) {
													returnVal = val;
													return;
												}
											});
											return returnVal;
										} },
									{ field : 'tableId', title : '表id', width : 100, sortable : true },
									{
										field : 'customerName',
										title : '采购商/供应商',
										width : 150,
										sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											if (row.tableType == 1) {
												$.each(${applicationScope.sysParam.customerInfoMap},
														function(key, val) {
															if (key == row.tableId) {
																returnVal = val.customerName;
																return;
															}
														});
											} else if (row.tableType == 2) {
												$.each(${applicationScope.sysParam.providerInfoMap},
														function(key, val) {
															if (key == row.tableId) {
																returnVal = val.providerName;
																return;
															}
														});
											} else if (row.tableType == 3) {
												$.each(${applicationScope.sysParam.customerProductInfoMapJson},
														function(key, val) {
															if (key == row.tableId) {
																var customerId = val.customerId;
																$.each(${applicationScope.sysParam.customerInfoMap},
																		function(key, val) {
																			if (key == customerId) {
																				returnVal = val.customerName;
																				return;
																			}
																		});
																return;
															}
														});
											} else if (row.tableType == 4) {
												$.each(${applicationScope.sysParam.providerProductInfoMap}, function(
														key, val) {
													if (key == row.tableId) {
														var providerId = val.providerId;
														$.each(${applicationScope.sysParam.providerInfoMap},
																function(key, val) {
																	if (key == providerId) {
																		returnVal = val.providerName;
																		return;
																	}
																});
														return;
													}
												});
											}
											return returnVal;
										} },
									{
										field : 'tableName',
										title : '采购商/供应商产品名称',
										width : 150,
										sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											if (row.tableType == 3) {
												$.each(${applicationScope.sysParam.customerProductInfoMapJson},
														function(key, val) {
															if (key == row.tableId) {
																returnVal = val.productName;
																return;
															}
														});
											} else if (row.tableType == 4) {
												$.each(${applicationScope.sysParam.providerProductInfoMap}, function(
														key, val) {
													if (key == row.tableId) {
														returnVal = val.productName;
														return;
													}
												});
											}
											return returnVal;
										} },
									{ field : 'type', title : '修改类型', width : 100, sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.changeTypeMapJson}, function(key, val) {
												if (key == value) {
													returnVal = val;
													return;
												}
											});
											return returnVal;
										} },
									{ field : 'status', title : '是否生效', width : 100, sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											if (value == 2) {
												returnVal = "<span style='color:blue'>已生效</span>";
											} else {
												$.each(${applicationScope.sysParam.isNoMapJson}, function(key, val) {
													if (key == value) {
														returnVal = greenOrRed(key, val);
														return;
													}
												});
											}
											return returnVal;
										} },
									{ field : 'changeTime', title : '执行时间', width : 130, sortable : true,
										formatter : function(value, row, index) {
											var returnVal = long2date(value);
											if (value != null) {
												var color = value > (new Date()) ? 1 : 0;
												returnVal = greenOrRed(color, long2date(value));
											}
											return returnVal;
										} },
									{ field : 'changeNum', title : '折扣数', width : 100, sortable : true },
									{ field : 'remark', title : '备注', width : 100, sortable : true },
									{ field : 'addTime', title : '添加时间', width : 130, sortable : true,
										formatter : function(value, row, index) {
											return long2date(value);
										} },
									{ field : 'addUser', title : '添加人', width : 130, sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.userMapJson}, function(key, val) {
												if (key == value) {
													returnVal = val.name;
													return;
												}
											});
											return returnVal;
										} },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});
	</script>
</body>
</html>