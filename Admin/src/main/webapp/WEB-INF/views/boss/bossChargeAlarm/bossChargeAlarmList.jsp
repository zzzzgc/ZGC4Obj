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
				<input type="text" name="filter_INI_id" value="${bossChargeAlarm.id}" class="easyui-validatebox"
					data-options="width:60,prompt: 'id'" onchange="trimLeft(this)" />
				<!-- 		产品查询 start	 -->
				<input type="text" name="filter_EQI_productId" value="${bossChargeAlarm.productId}" class="easyui-validatebox"
					data-options="width:60,prompt: '产品id'" />
				<input type="text" name="categoryName" class="easyui-validatebox" data-options="width:120,prompt: '产品名称'"
					onblur="getProductId()" />
				<span class="toolbar-item dialog-tool-separator"></span> <select name="productId" onchange="getProductIdBySelect()">
					<option value="">产品</option>
					<c:forEach items="${applicationScope.sysParam.productCategoryInputMap}" var="productCategoryInputMap">
						<option value="${productCategoryInputMap.key}">${productCategoryInputMap.value.categoryName}</option>
					</c:forEach>
				</select>
				<!-- -->
				<input type="hidden" name="filter_INI_productId" class="easyui-validatebox" data-options="width:100,prompt: '产品id'" />
				<span class="toolbar-item dialog-tool-separator"></span>
				<!---		产品查询 end		 -->
				<select name="filter_EQS_operator">
					<option value="">运营商</option>
					<c:forEach items="${applicationScope.sysParam.operatorMap}" var="operatorMap">
						<option value="${operatorMap.value}">${operatorMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_EQS_province">
					<option value="">省份</option>
					<c:forEach items="${applicationScope.sysParam.provinceMap}" var="provinceMap">
						<option value="${provinceMap.value}">${provinceMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_EQI_area">
					<option value="">使用区域</option>
					<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
						<option value="${areaMap.key}">${areaMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span>
				<input type="text" name="filter_EQI_chargeNum" value="${bossChargeAlarm.chargeNum}" class="easyui-validatebox"
					data-options="width:80,prompt: '充值中笔数'" onchange="trimLeft(this)" />
				<input type="text" name="filter_EQI_useTime" value="${bossChargeAlarm.useTime}" class="easyui-validatebox"
					data-options="width:80,prompt: '耗时(分)'" onchange="trimLeft(this)" />

				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" onclick="cx()">查询</a> <span class="toolbar-item dialog-tool-separator"></span>
				<!-- -->
				<input type="reset" value="重置" onclick="resetValue()" />
				<!-- 				<span class="toolbar-item dialog-tool-separator"></span> &nbsp; <a href="javascript(0)" class="easyui-linkbutton" -->
				<!-- 					onclick='sendPostByUrlMany(this,"bossChargeAlarm/updateStatus",0)'>冻结</a> -->
				<!-- -->
				<!-- 				<a href="javascript(0)" class="easyui-linkbutton" onclick='sendPostByUrlMany(this,"bossChargeAlarm/updateStatus",1)'>开通</a> -->
				<!-- -->
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:bossChargeAlarm:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
					onclick="addCommon('${ctx}/boss/bossChargeAlarm/list?page=Add');">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossChargeAlarm:delete">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
					onclick="del('bossChargeAlarm/delete')">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossChargeAlarm:update">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
					onclick="updCommon('${ctx}/boss/bossChargeAlarm/list?page=Update')">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossChargeAlarm:exportExcel">
				<!--			<span class="toolbar-item dialog-tool-separator"></span> -->
				<!--			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true" onclick="exportExcel('bossChargeAlarm/exportExcel')">导出Excel</a> -->
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
						url : '${ctx}/boss/bossChargeAlarm/bossChargeAlarmList',
						fit : true,
						fitColumns : true,//自动填充,如果字段过多就改为false
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
									{ field : 'productId', title : '产品id', width : 100, sortable : true },
									{
										field : 'categoryName',
										title : '产品名称',
										width : 100,
										sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.productCategoryInfoMap}, function(key,
													val) {
												if (key == row.productId) {
													returnVal = val.categoryName;
													return;
												}
											});
											return returnVal;
										} },
									{ field : 'operator', title : '运营商', width : 80, sortable : true },
									{ field : 'province', title : '省份', width : 80, sortable : true },
									{ field : 'area', title : '使用区域', width : 100, sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.areaMapJson}, function(key, val) {
												if (key == value) {
													returnVal = val;
													return;
												}
											});
											return returnVal;
										} }, { field : 'chargeNum', title : '充值中笔数', width : 100, sortable : true },
									{ field : 'useTime', title : '耗时(分)', width : 100, sortable : true },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});
	</script>
</body>
</html>