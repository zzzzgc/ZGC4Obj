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
				<input type="text" name="filter_INI_id" value="${bossProviderSame.id}" class="easyui-validatebox"
					data-options="width:60,prompt: 'id'" onchange="trimLeft(this)" />
				<input type="text" name="filter_LIKES_supplier" value="${bossProviderSame.id}" class="easyui-validatebox"
					data-options="width:60,prompt: '简称'" onchange="trimLeft(this)" />
				<input type="text" name="filter_LIKES_providerIds" value="${bossProviderSame.providerIds}" class="easyui-validatebox"
					data-options="width:120,prompt: '供应商id'" onchange="trimLeft(this)" />
				<input type="text" name="filter_EQM_alarmBalance" class="easyui-validatebox" data-options="width:80,prompt: '报警金额'"
					onchange="trimLeft(this)" />
				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" onclick="cx()">查询</a> <span class="toolbar-item dialog-tool-separator"></span>
				<!-- -->
				<input type="reset" value="重置" onclick="resetValue()" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp; <a href="javascript(0)" class="easyui-linkbutton"
					onclick="alertWindowCommon(this,'${ctx}/boss/bossProviderSame/list?page=ChangeAlarmBalance','${ctx}/boss/bossProviderSame/alertWindow?method=changeAlarmBalance',true)">修改预警金额</a>
				<!-- -->
				<!-- 				<span class="toolbar-item dialog-tool-separator"></span> <a href="javascript(0)" class="easyui-linkbutton" -->
				<!-- 					onclick='sendPostByUrlManyCommon(this,"${ctx}/boss/bossProviderSame/updateStatus",1,true)'>开通</a> -->
				<!-- -->
				<!-- 				<a href="javascript(0)" class="easyui-linkbutton" -->
				<!-- 					onclick='sendPostByUrlManyCommon(this,"${ctx}/boss/bossProviderSame/updateStatus",0,true)'>冻结</a> -->
				<!-- -->
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:bossProviderSame:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
					onclick="addCommon('${ctx}/boss/bossProviderSame/list?page=Add');">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossProviderSame:delete">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
					onclick="del('bossProviderSame/delete')">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossProviderSame:update">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
					onclick="updCommon('${ctx}/boss/bossProviderSame/list?page=Update')">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossProviderSame:exportExcel">
				<!--			<span class="toolbar-item dialog-tool-separator"></span> -->
				<!--			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true" onclick="exportExcel('bossProviderSame/exportExcel')">导出Excel</a> -->
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
						url : '${ctx}/boss/bossProviderSame/bossProviderSameList',
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
									{ field : 'providerIds', title : '供应商id', width : 100, sortable : true },
									{
										field : 'providerIds_name',
										title : '供应商名称',
										width : 500,
										formatter : function(value, row, index) {
											var returnVal = "";
											var proIds = row.providerIds.split(",");
											for (var int = 0; int < proIds.length; int++) {
												$.each(${applicationScope.sysParam.providerInfoMap},
														function(key, val) {
															if (key == proIds[int]) {
																if (returnVal.length == 0) {
																	returnVal += val.providerName;
																} else {
																	returnVal += '<span style="color:red">' + "|"
																			+ '</span>' + val.providerName;
																}
															}
														});
											}
											return returnVal;
										} },
									{ field : 'supplier', title : '简称', width : 100, sortable : true },
									{ field : 'alarmBalance', title : '报警金额', width : 100, sortable : true },
									{ field : 'balance', title : '余额', width : 100, sortable : true,
										formatter : function(value, row, index) {
											var color = row.alarmBalance < row.balance ? 1 : 0;
											var returnVal = greenOrRed(color, value);
											return returnVal;
										} },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});

	</script>
</body>
</html>