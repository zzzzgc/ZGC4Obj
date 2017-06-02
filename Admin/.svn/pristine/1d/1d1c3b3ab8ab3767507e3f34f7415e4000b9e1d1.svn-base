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
				<input type="text" name="filter_INI_id" class="easyui-validatebox" data-options="width:60,prompt: 'id'"
					onchange="trimLeft(this)" />
				<input type="text" name="filter_INI_customerId" class="easyui-validatebox" data-options="width:80,prompt: '采购商id'"
					onchange="trimLeft(this)" />
				<select name="filter_EQI_customerId">
					<option value="">采购商</option>
					<option value="0">全部</option>
					<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap">
						<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
					</c:forEach>
				</select>
				<!-- -->
				<select name="filter_EQS_operator">
					<option value="">运营商</option>
					<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
						<option value="${providerMap.value}">${providerMap.value}</option>
					</c:forEach>
				</select>
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
				<select name="filter_EQI_area">
					<option value="">使用区域</option>
					<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
						<option value="${areaMap.key}">${areaMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span>
				<input type="text" name="filter_EQI_providerId" class="easyui-validatebox" data-options="width:80,prompt: '供货商id'"
					onchange="trimLeft(this)" />
				<input type="text" name="filter_EQI_priority" class="easyui-validatebox" data-options="width:80,prompt: '优先级'"
					onchange="trimLeft(this)" />
				<select name="filter_EQI_status">
					<option value="">状态</option>
					<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap">
						<option value="${freezeMap.key}">${freezeMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span>

				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" onclick="cx()">查询</a> <span class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置" onclick="resetValue()" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp; <a href="javascript(0)" class="easyui-linkbutton"
					onclick='sendPostByUrlMany(this,"bossCustomerRoute/updateStatus",0)'>冻结</a> <a href="javascript(0)" class="easyui-linkbutton"
					onclick='sendPostByUrlMany(this,"bossCustomerRoute/updateStatus",1)'>开通</a>
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:bossCustomerRoute:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
					onclick="add('bossCustomerRoute/create');">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossCustomerRoute:delete">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
					onclick="del('bossCustomerRoute/delete')">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossCustomerRoute:update">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
					onclick="upd('bossCustomerRoute/update/')">修改</a>
			</shiro:hasPermission>
			<!-- 			<shiro:hasPermission name="boss:bossCustomerRoute:update"> -->
			<!-- 				<span class="toolbar-item dialog-tool-separator"></span> -->
			<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true" -->
			<!-- 					onclick="exportExcel('bossCustomerRoute/exportExcel')">导出Excel</a> -->
			<!-- 			</shiro:hasPermission> -->
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
						url : '${ctx}/boss/bossCustomerRoute/bossCustomerRouteList',
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
										field : 'customerInfo_customerName',
										title : '采购商名称',
										formatter : function(value, row, index) {
											var retrunVal = "";
											if (row.customerId == 0) {
												return "全部采购商";
											} else {
												$.each(${applicationScope.sysParam.customerInfoMap},
														function(key, val) {
															if (key == row.customerId) {
																retrunVal = val.customerName;
																return;
															}
														});
											}
											return retrunVal;
										} },
									{ field : 'operator', title : '运营商', width : 100, sortable : true },
									{ field : 'province', title : '省份', width : 100, sortable : true },
									{ field : 'city', title : '城市', width : 100, sortable : true,
										formatter : function(value, row, index) {
											var returnVal = value;
											if (value == "0") {
												returnVal = "全部";
											}
											return returnVal;
										} },
									{ field : 'productNum', title : '产品规格', width : 100,
										formatter : function(value, row, index) {
											var returnVal = "";
											if (value == "0") {
												returnVal = "全部";
											} else {
												$.each(${applicationScope.sysParam.specMapJson}, function(key, val) {
													if (val == value) {
														returnVal = val + "M";
														return;
													}
												});
											}
											return returnVal;
										} },
									{ field : 'area', title : '使用区域', width : 100,
										formatter : function(value, row, index) {
											if (value == 1) {
												return "全国";
											} else if (value == 0) {
												return "本省";
											} else {
												return "错误代码:" + value;
											}
										} },
									{ field : 'providerId', title : '供货商id', width : 100, sortable : true },
									{ field : 'providerInfo_providerName', title : '供应商名称',
										formatter : function(value, row, index) {
											var retrunVal = "";
											$.each(${applicationScope.sysParam.providerInfoMap}, function(key, val) {
												if (key == row.providerId) {
													retrunVal = val.providerName;
													return;
												}
											});
											return retrunVal;
										} },
									{ field : 'priority', title : '优先级', width : 100, sortable : true },
									{ field : 'status', title : '状态', width : 100,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.freezeMapJson}, function(key, val) {
												if (key == value) {
													returnVal = greenOrRed(key, val);
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