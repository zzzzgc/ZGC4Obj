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
				<input id="d1" type="text" name="filter_GED_recordTime" class="easyui-my97" startDate='%y-%M-%d 00:00:00'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '起始时间'" />
				--
				<input id="d2" type="text" name="filter_LED_recordTime" class="easyui-my97" startDate='%y-%M-%d 23:59:59'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '结束时间'" />
				<input type="text" name="filter_EQI_id" class="easyui-validatebox" data-options="width:60,prompt: 'id'" />
				<!-- 		供应商查询 start	 -->
				<input type="text" name="filter_EQI_providerId" class="easyui-validatebox" data-options="width:60,prompt: '供应商id'" />
				<input type="text" name="providerName" class="easyui-validatebox" data-options="width:150,prompt: '供应商名称'" />
				<span class="toolbar-item dialog-tool-separator"></span> 供应商：<select name="providerId" onchange="getProviderIdBySelect()">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap">
						<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
					</c:forEach>
				</select>
				<input type="hidden" name="filter_INI_providerId" class="easyui-validatebox" data-options="width:100,prompt: '供应商id'" />
				<input type="text" name="filter_LIKES_remark" class="easyui-validatebox" data-options="width:100,prompt: '备注'" />
				<!---		供应商查询 end		 -->
				<input type="text" name="filter_LEM_fundBalance" class="easyui-validatebox" data-options="width:150,prompt: '资金余额'" />
				<input type="text" name="filter_LEM_cost" class="easyui-validatebox" data-options="width:150,prompt: '发生金额'" />
				<span class="toolbar-item dialog-tool-separator"></span> 收支类型：<select name="filter_EQI_costType">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.costTypeMap}" var="costTypeMap">
						<option value="${costTypeMap.key}">${costTypeMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span>
				<br />
				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a> <span
					class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置" onclick="resetTime()" />
			</form>
			<div style="height: 7px;"></div>

			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true"
				data-options="disabled:false" onclick="exportExcel()">导出Excel</a>

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
						url : '${ctx}/boss/providerMoneyRecord/providerMoneyRecordList',
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
						singleSelect : true,
						columns : [
							[
									{ field : 'providerId', title : '供应商Id', width : 100,
										formatter : function(value, row, index) {
											var value = row.providerId;
											return value;
										} },
									{ field : 'providerName', title : '供应商名称', width : 100,
										formatter : function(value, row, index) {
											var value = row.providerId;
											var $provider = ${applicationScope.sysParam.providerInfoMap};
											var retrunVal = "";
											$.each($provider, function(key, val) {
												if (key == value) {
													retrunVal = val.providerName;
												}
											});
											return retrunVal;
										} },
									{ field : 'fundBalance', title : '资金余额', width : 100 },
									{ field : 'costType', title : '收支类型', width : 100,
										formatter : function(value, row, index) {
											if (value == 1) {
												return "人工注资";
											} else if (value == 2) {
												return "系统注资";
											} else if (value == 3) {
												return "消费";
											} else if (value == 4) {
												return "失败返还";
											} else if (value == 5) {
												return "人工扣款";
											}
										} },
									{ field : 'remark', title : '备注', width : 100 },
									{ field : 'cost', title : '发生金额', width : 100 },
									{ field : 'recordTime', title : '时间', width : 100,
										formatter : function(value, row, index) {
											if (value != null) {
												var time = jsonTimeStamp(value);
												return time;
											}
											return "";
										} },
									{ field : 'id', title : 'id', width : 100 }
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});
		//时间戳转化为日期格式yyyy-MM-dd HH:mm:ss
		function jsonTimeStamp(milliseconds) {
			if (milliseconds != "" && milliseconds != null && milliseconds != "null") {
				var datetime = new Date();
				datetime.setTime(milliseconds);
				var year = datetime.getFullYear();
				var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
				var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
				var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
				var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
				var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
				return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
			} else {
				return "";
			}

		}

		//弹窗增加
		function add() {
			d = $("#dlg").dialog(
					{ title : '添加配置', width : 380, height : 250, href : '${ctx}/boss/providerMoneyRecord/create',
						maximizable : true, modal : true, buttons : [
								{ text : '确认', handler : function() {
									$("#mainform").submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//删除
		function del() {
			var row = dg.datagrid('getSelected');
			if (rowIsNull(row))
				return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
				if (data) {
					$.ajax({ type : 'get', url : "${ctx}/boss/providerMoneyRecord/delete/" + row.id,
						success : function(data) {
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
					{ title : '修改用户', width : 380, height : 250,
						href : '${ctx}/boss/providerMoneyRecord/update/' + row.id, maximizable : true, modal : true,
						buttons : [
								{ text : '修改', handler : function() {
									$('#mainform').submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//创建查询对象并查询
		function cx() {
			getProviderId();
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

		//导出excel
		function exportExcel() {
			
			$.messager.confirm('提示', '确定要导出excel？', function(data) {
				if (data) {
					var obj = $("#searchFrom").serializeObject();
					var params = "?";
					for ( var name in obj) {
						params += name + "=" + obj[name] + "&&";
					}
					params = params.substring(0, params.length - 2);
					parent.$.messager.show({ title : "提示", msg : "导出需要时间，请稍等片刻！", position : "bottomRight" });
					var url = "${ctx}/boss/providerMoneyRecord/exportExcel" + params;
					window.location.href = url;
				}
			});
			
			
// 			parent.$.messager.show({ title : "提示", msg : "导出需要时间，请稍等片刻！", position : "bottomRight" });
// 			var url = "${ctx}/boss/providerMoneyRecord/exportExcel";
// 			window.location.href = url;
		}

		function resetTime() {
			$("input[name='filter_GED_recordTime']").val("");
			$("input[name='filter_LED_recordTime']").val("");
		}
	</script>
</body>
</html>