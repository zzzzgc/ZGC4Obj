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
				<!-- <input type="text" name="filter_EQI_id" class="easyui-validatebox" data-options="width:150,prompt: '编号'" /> -->

				<input type="text" name="providerSupplier" class="easyui-validatebox" onblur="getProviderIdBySupplier();"
					data-options="width:150,prompt: '供应商简称'" />
				<!-- 				<input type="text" name="filter_GEI_notHandleNum" class="easyui-validatebox" data-options="width:80,prompt: '未处理数量'" /> -->
				<!-- 				-- -->
				<!-- 				<input type="text" name="filter_LEI_notHandleNum" class="easyui-validatebox" data-options="width:80,prompt: '未处理数量'" /> -->
				<!-- 				<input type="text" name="filter_GEL_timeOut" class="easyui-validatebox" data-options="width:80,prompt: '超过耗时'" /> -->
				<!-- 				-- -->
				<!-- 				<input type="text" name="filter_LEL_timeOut" class="easyui-validatebox" data-options="width:80,prompt: '超过耗时'" /> -->
				<span class="toolbar-item dialog-tool-separator"></span> 省份：<select name="filter_EQS_province">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.productProvinceMap}" var="productProvinceMap">
						<option value="${productProvinceMap.value}">${productProvinceMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 运营商：<select name="filter_EQS_operator">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
						<option value="${providerMap.value}">${providerMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 级别：<select name="filter_EQI_grade">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.gradeMap}" var="gradeMap">
						<option value="${gradeMap.key}">${gradeMap.value}</option>
					</c:forEach>
				</select>
				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a> <span
					class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置" onclick="clearCity()" />
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:alarmConfig:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:alarmConfig:delete">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false"
					onclick="del()">删除</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:alarmConfig:update">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"
					onclick="upd()">修改</a>
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
						method : "get",
						url : '${ctx}/boss/alarmConfig/alarmConfigList',
						fit : true,
						fitColumns : true,
						border : false,
						striped : true,
						idField : 'id',
						pagination : true,
						rownumbers : true,
						pageNumber : 1,
						pageSize : 20,
						pageList : [
								10, 20, 30, 40, 50
						],
						singleSelect : false,
						columns : [
							[
									{ field : 'ck', checkbox : true },
									{ field : 'id', title : 'id', width : 100, hidden : true },
									{ field : 'providerId', title : '供应商名称', formatter : function(value, row, index) {
										//获取供货商的列表
										var $provider = ${applicationScope.sysParam.providerInfoMap};
										var retrunVal = "";
										$.each($provider, function(key, val) {
											if (key == row.providerId) {
												retrunVal = val.providerName;
											}
										});
										return retrunVal;
									} },
									{ field : 'operator', title : '运营商', width : 250 },
									{ field : 'province', title : '省份', width : 250 },
									{ field : 'timeOut', title : '超过耗时(s)', width : 250 },
									{ field : 'NumOfNotHandle', title : '未处理订单数', width : 250,
										formatter : function(value, row, index) {
											if (row.maxNumOfNotHandle != null) {
												return row.minNumOfNotHandle + " - " + row.maxNumOfNotHandle;
											} else {
												return " ≥ " + row.minNumOfNotHandle;
											}
										} },
									{ field : 'grade', title : '报警等级', width : 250,
										formatter : function(value, row, index) {
											if (value == 0) {
												return "一般";
											} else if (value == 1) {
												return "严重";
											} else if (value == 2) {
												return "特别严重";
											} else
												return value;
										} },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});
		function getProviderIdBySupplier() {
			var filter_EQI_providerId = $("input[name=filter_EQI_providerId]");
			var providerSupplier = $("input[name=providerSupplier]").val();
			var $providerInfo = ${applicationScope.sysParam.providerInfoMap};
			filter_EQI_providerId.val("");
			var success = false;
			$.each($providerInfo, function(key, val) {
				if (val.supplier.toUpperCase() == providerSupplier.toUpperCase()) {
					filter_EQI_providerId.val(val.id);
					success = true;
					return false;
				}
			});
			if (!success && providerSupplier != "") {
				parent.$.messager.alert("不存在这样的供应商简称！");
			}
		}
		//弹窗增加
		function add() {

			d = $("#dlg").dialog(
					{ title : '添加配置', width : 380, height : 250, href : '${ctx}/boss/alarmConfig/create',
						maximizable : true, modal : true, buttons : [
								{ text : '确认', handler : function() {
									$('#mainform').submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//删除
		function del() {
			var rows = $('#dg').datagrid('getSelections');
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			var ids = new Array();
			for (var i = 0; i < rows.length; i++) {
				ids[i] = rows[i].id;
			}
			$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
				if (data) {
					$.ajax({ type : 'post', traditional : true, data : { "ids" : ids },
						url : "${ctx}/boss/alarmConfig/delete", success : function(data) {
							dg.datagrid('clearSelections');
							successTip(data, dg);
						} });
				}
			});
		}

		//弹窗修改
		function upd() {
			var row = dg.datagrid('getSelected');
			if (rowIsNull(row))
				return;
			d = $("#dlg").dialog(
					{ title : '修改用户', width : 380, height : 250, href : '${ctx}/boss/alarmConfig/update/' + row.id,
						maximizable : true, modal : true, buttons : [
								{ text : '修改', handler : function() {
									$('#mainform').submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//创建查询对象并查询
		function cx() {
			var obj = $("#searchFrom").serializeObject();
			dg.datagrid('reload', obj);

		}

		//格式化 关于钱的字段，例如单价，一口价等
		function formaterMoney(value) {
			var val = parseInt(value);
			var vale = val / 100;
			val = vale.toFixed(3);
			return val;
		}
	</script>
</body>
</html>