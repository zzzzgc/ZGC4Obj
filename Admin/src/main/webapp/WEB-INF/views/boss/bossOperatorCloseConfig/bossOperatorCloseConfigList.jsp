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
				<input type="text" name="filter_INI_id" value="${bossOperatorCloseConfig.id}" class="easyui-validatebox"
					data-options="width:60,prompt: 'id'" onchange="trimLeft(this)" />
				<!-- 		采购商查询 start	 -->
				<input type="text" name="filter_EQI_customerId" class="easyui-validatebox" data-options="width:60,prompt: '采购商id'" />

				<input type="text" name="customerName" class="easyui-validatebox" data-options="width:150,prompt: '采购商名称'" />
				<span class="toolbar-item dialog-tool-separator"></span> 采购商：<select name="customerId" onchange="getCustomerIdBySelect()">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap">
						<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
					</c:forEach>
				</select>
				<input type="hidden" name="filter_INI_customerId" class="
						easyui-validatebox" data-options="width:100,prompt: '采购商id'" />
				<!---		采购商查询 end		 -->
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> 省份： <select name="filter_EQS_province" onchange="chooseCity()">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.provinceMap}" var="provinceMap">
						<option value="${provinceMap.value}">${provinceMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 城市： <select name="filter_EQS_city">
					<option value="">请选择</option>
				</select> <select name="filter_EQI_productNum">
					<option value="">产品规格</option>
					<option value="0">全部</option>
					<c:forEach items="${applicationScope.sysParam.specMap}" var="specMap">
						<option value="${specMap.value}">${specMap.value}M</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_EQI_size">
					<option value="">流量规格</option>
					<c:forEach items="${applicationScope.sysParam.specMap}" var="specMap">
						<option value="${specMap.key}">${specMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_EQI_status">
					<option value="">状态</option>
					<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap">
						<option value="${freezeMap.key}">${freezeMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_EQI_failStatus">
					<option value="">失败状态</option>
					<c:forEach items="${applicationScope.sysParam.failStatusMap}" var="failStatusMap">
						<option value="${failStatusMap.key}">${failStatusMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_EQI_failStatus">
					<option value="">漫游类型</option>
					<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
						<option value="${areaMap.key}">${areaMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span>

				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" onclick="cx()">查询</a> <span class="toolbar-item dialog-tool-separator"></span>
				<!-- -->
				<input type="reset" value="重置" onclick="resetValue()" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp; <a href="javascript(0)" class="easyui-linkbutton"
					onclick='sendPostByUrlMany(this,"bossOperatorCloseConfig/updateStatus",0)'>冻结</a>
				<!-- -->
				<a href="javascript(0)" class="easyui-linkbutton" onclick='sendPostByUrlMany(this,"bossOperatorCloseConfig/updateStatus",1)'>开通</a>
				<!-- -->
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:bossOperatorCloseConfig:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
					onclick="addCommon('${ctx}/boss/bossOperatorCloseConfig/list?page=Add');">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossOperatorCloseConfig:delete">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
					onclick="del('bossOperatorCloseConfig/delete')">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossOperatorCloseConfig:update">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
					onclick="updCommon('${ctx}/boss/bossOperatorCloseConfig/list?page=Update')">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossOperatorCloseConfig:exportExcel">
				<!--			<span class="toolbar-item dialog-tool-separator"></span> -->
				<!--			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true" onclick="exportExcel('bossOperatorCloseConfig/exportExcel')">导出Excel</a> -->
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
						url : '${ctx}/boss/bossOperatorCloseConfig/bossOperatorCloseConfigList',
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
									{ field : 'customerId', title : '采购商id', width : 100, sortable : true },
									{
										field : 'customerName',
										title : '采购商名称',
										width : 100,
										sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.customerInfoMapJson},
													function(key, val) {
														if (key == row.customerId) {
															returnVal = val.customerName;
															return;
														}
													});
											return returnVal;
										} },
									{ field : 'province', title : '省份', width : 100, sortable : true },
									{ field : 'city', title : '城市', width : 100, sortable : true },
									{ field : 'size', title : '流量规格', width : 100, sortable : true,
										formatter : function(value, row, index) {
											return value+"M";
										} },
									{ field : 'status', title : '状态', width : 100, sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.freezeMapJson}, function(key, val) {
												if (key == value) {
													returnVal = greenOrRed(key,val);
													return;
												}
											});
											return returnVal;
										} },
									{ field : 'failStatus', title : '失败状态', width : 100, sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.failStatusMapJson}, function(key, val) {
												if (key == value) {
													returnVal = val;
													return;
												}
											});
											return returnVal;
										} },
									{ field : 'area', title : '漫游类型', width : 100, sortable : true ,
										formatter : function(value, row, index) {
										var returnVal = "";
										$.each(${applicationScope.sysParam.areaJsonMap}, function(key, val) {
											if (key == value) {
												returnVal = val;
												return;
											}
										});
										return returnVal;
									}},
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});
	</script>
</body>
</html>