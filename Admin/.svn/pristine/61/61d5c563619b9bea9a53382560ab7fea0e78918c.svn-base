<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body style="font-family: '微软雅黑'">
	<div id="top" style="height: 90%;color: green;position:absolute;top:0px;width: 100%;">
		<div id="tb" style="padding:5px;height:auto">
			<div>
				<form id="searchFrom" action="">

					<input id="d1" type="text" name="filter_GED_time" class="easyui-my97" startDate='%y-%M-%d 00:00:00'
						datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '开始时间'" />
					--
					<input id="d2" type="text" name="filter_LED_time" class="easyui-my97" startDate='%y-%M-%d 23:59:59'
						datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '结束时间'" />
					<span class="toolbar-item dialog-tool-separator"></span>
					<!-- 		采购商查询 start	 -->
					<input type="text" name="customerName" class="easyui-validatebox" data-options="width:150,prompt: '采购商名称'" />
					<span class="toolbar-item dialog-tool-separator"></span> 采购商：<select name="filter_EQI_customerId">
						<option value="">请选择</option>
						<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap">
							<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
						</c:forEach>
					</select> <span class="toolbar-item dialog-tool-separator"></span>
					<input type="hidden" name="filter_INI_customerId" class="easyui-validatebox" data-options="width:100,prompt: '采购商id'" />
					<!---		采购商查询 end		 -->
					<div style="height: 7px;"></div>
					<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a> <span
						class="toolbar-item dialog-tool-separator"></span>
					<input type="reset" value="重置" onclick="clearCity()" />
				</form>
				<div style="height: 7px;"></div>
			</div>

		</div>
		<table id="dg"></table>
		<div id="dlg"></div>
	</div>
	<div id="bottom" style="height:10%;width:100%; position:absolute;bottom:0;">
		<div id="bottomLeft"></div>
	</div>
	<script type="text/javascript">
		var dg;
		$(function() {
			dg = $('#dg').datagrid(
					{
						method : "get",
						url : '${ctx}/boss/customerDataAnalyze/customerDataAnalyzeList',
						fit : true,
						fitColumns : true,
						border : false,
						striped : true,
						idField : 'id',
						pagination : true,
						rowStyler : function(index, row) {
							if (row.totalMoney <= 0) {
								return 'background-color:pink';
							}
						},
						rownumbers : true,
						pageNumber : 1,
						sortName : 'customerId',
						onLoadSuccess : function(data) {
							if (data.distBalance != undefined) {
								distBalance
								$("#bottomLeft").html(bottem);
							}
						},
						pageSize : 20,
						pageList : [
								10, 20, 30, 40, 50
						],
						singleSelect : true,
						columns : [
							[

									{ field : 'id', title : 'id', sortable : true, width : 100, hidden : true },
									{ field : 'customerId', title : '采购商id', sortable : true, width : 100 },
									{ field : 'customerName', title : '采购商名称', formatter : function(value, row, index) {
										//获取采购商的列表
										var $provider = ${applicationScope.sysParam.customerInfoMap};
										var retrunVal = "";
										$.each($provider, function(key, val) {
											if (key == row.customerId) {
												retrunVal = val.customerName;
											}
										});
										if (retrunVal == "") {
											retrunVal = "采购商已被删除";
										}
										return retrunVal;
									} },
									{ field : 'totalMoney', title : '销售总额', sortable : true, width : 100 },
									{ field : 'successMoney', title : '成功销售额', sortable : true, width : 100 },
									{ field : 'successCost', title : '成本', sortable : true, width : 100 },
									{ field : 'earn', title : '利润', sortable : true, width : 100 },
									{ field : 'dianxinNum', title : '电信笔数', sortable : true, width : 100 },
									{ field : 'liantongNum', title : '联通笔数', sortable : true, width : 100 },
									{ field : 'yidongNum', title : '移动笔数', sortable : true, width : 100 },
									{ field : 'totalNum', title : '订单总笔数', sortable : true, width : 100 },
									{ field : 'successNum', title : '成功笔数', sortable : true, width : 100 },
									{ field : 'successRate', title : '成功率', width : 100,
										formatter : function(value, row, index) {
											return (Math.round(value * 10000) / 100).toFixed(2) + '%';
										} },
									{ field : 'maoEarn', title : '销售毛利率', width : 100,
										formatter : function(value, row, index) {
											return (Math.round(value * 10000) / 100).toFixed(2) + '%';
										} }, { field : 'useTime', title : '平均耗时(S)', width : 100 },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});

		//弹窗增加
		function add() {
			d = $("#dlg").dialog(
					{
						title : '添加配置',
						width : 380,
						height : 250,
						href : '${ctx}/boss/customerDataAnalyze/create',
						maximizable : true,
						modal : true,
						buttons : [
								{
									text : '确认',
									handler : function() {
										$.ajax({ data : $("#mainform").serializeObject(), type : 'post',
											url : "${ctx}/boss/customerDataAnalyze/add", success : function(data) {
												if (data == "false") {
													parent.$.messager.alert("保存失败,可能是地址出错");
												} else
													successTip(data, dg, d);
											} });
									} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//删除
		function del() {
			var rows = dg.datagrid('getSelections');
			if (rows.length != 1) {
				parent.$.messager.show({ title : "提示", msg : "请选择一项！", position : "bottomRight" });
				return;
			}
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
				if (data) {
					$.ajax({ type : 'get', url : "${ctx}/boss/customerDataAnalyze/delete/" + rows[0].id,
						success : function(data) {
							successTip(data, dg);
							dg.datagrid('uncheckAll');
						} });
				}
			});
		}

		//弹窗修改
		function upd() {
			var rows = dg.datagrid('getSelections');
			if (rows.length != 1) {
				parent.$.messager.show({ title : "提示", msg : "请选择一项！", position : "bottomRight" });
				return;
			}
			d = $("#dlg").dialog(
					{
						title : '修改用户',
						width : 380,
						height : 250,
						href : '${ctx}/boss/customerDataAnalyze/update/' + rows[0].id,
						maximizable : true,
						modal : true,
						buttons : [
								{
									text : '修改',
									handler : function() {
										$.ajax({ data : $("#mainform").serializeObject(), type : 'post',
											url : "${ctx}/boss/customerDataAnalyze/update", success : function(data) {
												if (data == "false") {
													parent.$.messager.alert("保存失败,可能是地址出错");
												} else
													successTip(data, dg, d);
											} });
									} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}
		//创建查询对象并查询
		function cx() {
			getCustomerId();
			var obj = $("#searchFrom").serializeObject();
			dg.datagrid('reload', obj);

		}

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
		function getCustomerIdBySupplier() {
			var filter_EQI_customerId = $("input[name=filter_EQI_customerId]");
			var cusSimpleName = $("input[name=cusSimpleName]").val();
			var $customerInfo = ${applicationScope.sysParam.customerInfoMap};
			filter_EQI_customerId.val("");
			var success = false;
			$.each($customerInfo, function(key, val) {
				if (val.cusSimpleName.toUpperCase() == cusSimpleName.toUpperCase()) {
					filter_EQI_customerId.val(val.id);
					success = true;
					return false;
				}
			});
			if (!success && cusSimpleName != "") {
				parent.$.messager.alert("不存在这样的采购商简称！");
			}
		}
	</script>
</body>
</html>