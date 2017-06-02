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
					<!---		供应商查询 end		 -->
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
			dg = $('#dg')
					.datagrid(
							{
								method : "get",
								url : '${ctx}/boss/providerDataAnalyze/providerDataAnalyzeList',
								fit : true,
								fitColumns : true,
								border : false,
								striped : true,
								idField : 'id',
								rowStyler : function(index, row) {
									if (row.totalMoney <= 0) {
										return 'background-color:pink';
									}
								},
								onLoadSuccess : function(data) {
									if (data.isSuccess) {
										if (data.tongji != undefined) {
											var bottem = '<table border="1"  align="center" cellpadding="10"  style="font-size:14px;width:74%;table-layout:fixed;"><tbody ><tr ><td >成本总额合计</td><td>成功成本额合计</td><td>电信笔数合计</td><td>联通笔数合计</td><td>移动笔数合计</td><td>订单总笔数合计</td><td>成功笔数合计</td><td>成功率</td><td>平均耗时(s)</td></tr><tr><td>'
													+ data.tongji.totalMoney
													+ '</td><td>'
													+ data.tongji.successMoney
													+ '</td><td>'
													+ data.tongji.dianxinNum
													+ '</td><td>'
													+ data.tongji.liantongNum
													+ '</td><td>'
													+ data.tongji.yidongNum
													+ '</td><td>'
													+ data.tongji.totalNum
													+ '</td><td>'
													+ data.tongji.successNum
													+ '</td><td>'
													+ (Math.round(data.tongji.successRate * 10000) / 100).toFixed(2) + '%'
													+ '</td><td>'
													+ data.tongji.useTime + '</td></tr></tbody>';
											$("#bottomLeft").html(bottem);
										}
									} else {
										$.messager.alert("请先配置天猫超时时间tmallOutTime");
									}
								},
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
											{ field : 'providerId', title : '供应商id', sortable : true, width : 100 },
											{ field : 'successName', title : '供应商名称', formatter : function(value, row, index) {
												var $provider = ${applicationScope.sysParam.providerInfoMap};
												var retrunVal = "";
												$.each($provider, function(key, val) {
													if (key == row.providerId) {
														retrunVal = val.providerName;
													}
												});
												return retrunVal;
											} },
											{ field : 'totalMoney', title : '成本总额', sortable : true, width : 100 },
											{ field : 'successMoney', title : '成功成本额', sortable : true, width : 100 },
											{ field : 'dianxinNum', title : '电信笔数', sortable : true, width : 100 },
											{ field : 'liantongNum', title : '联通笔数', sortable : true, width : 100 },
											{ field : 'yidongNum', title : '移动笔数', sortable : true, width : 100 },
											{ field : 'totalNum', title : '订单总笔数', sortable : true, width : 100 },
											{ field : 'successNum', title : '成功笔数', sortable : true, width : 100 },
											{ field : 'successRate', title : '成功率', width : 100,
												formatter : function(value, row, index) {
													return (Math.round(value * 10000) / 100).toFixed(2) + '%';
												} },
											{ field : 'useTime', title : '平均耗时(s)',width : 100 },
									]
								], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
								enableRowContextMenu : false, toolbar : '#tb' });
		});
		//创建查询对象并查询
		function cx() {
			getProviderId();
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
	</script>
</body>
</html>