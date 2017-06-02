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
				<input type="text" name="filter_GED_addTime" class="easyui-my97" value="${reqMap.start}" startDate='%y-%M-%d 00:00:00'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '${reqMap.start}'" />
				--
				<input type="text" name="filter_LED_addTime" class="easyui-my97" value="${reqMap.end}" startDate='%y-%M-%d 23:59:59'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '${reqMap.end}'" />
				<input type="text" name="filter_INI_id" value="${bossGdDataAnalyze.id}" class="easyui-validatebox"
					data-options="width:60,prompt: 'id'" onchange="trimLeft(this)" />
				<input type="text" name="filter_LIKES_city" value="${bossGdDataAnalyze.city}" class="easyui-validatebox"
					data-options="width:120,prompt: '城市'" onchange="trimLeft(this)" />

				<input type="text" name="filter_EQI_totalNum" value="" class="easyui-validatebox" data-options="width:80,prompt: '订单笔数'"
					onchange="trimLeft(this)" />
				<input type="text" name="filter_EQM_totalCost" class="easyui-validatebox" data-options="width:80,prompt: '订单成本'"
					onchange="trimLeft(this)" />

<!-- 				<input type="text" name="filter_EQI_successNum" value="" class="easyui-validatebox" data-options="width:80,prompt: '成功笔数'" -->
<!-- 					onchange="trimLeft(this)" /> -->
<!-- 				<input type="text" name="filter_EQM_successCost" class="easyui-validatebox" data-options="width:80,prompt: '成功成本'" -->
<!-- 					onchange="trimLeft(this)" /> -->
<!-- 				<input type="text" name="filter_EQM_dayTotalNumRate" class="easyui-validatebox" data-options="width:80,prompt: '订单笔数总'" -->
<!-- 					onchange="trimLeft(this)" /> -->
<!-- 				<input type="text" name="filter_EQM_daySuccessNum" class="easyui-validatebox" data-options="width:80,prompt: '成功笔数总'" -->
<!-- 					onchange="trimLeft(this)" /> -->
<!-- 				<input type="text" name="filter_EQM_dayTotalCost" class="easyui-validatebox" data-options="width:80,prompt: '订单金额总'" -->
<!-- 					onchange="trimLeft(this)" /> -->
<!-- 				<input type="text" name="filter_EQM_daySuccessCost" class="easyui-validatebox" data-options="width:80,prompt: '成功金额总'" -->
<!-- 					onchange="trimLeft(this)" /> -->
						<!-- -->
<!-- 					<select name="filter_INS_city"> -->
<!-- 					<option value="">城市</option> -->
<!-- 					<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap"> -->
<!-- 						<option value="${areaMap.key}">${areaMap.value}</option> -->
<!-- 					</c:forEach> -->
<!-- 				</select> -->
				<!-- -->
<!-- 				<span class="toolbar-item dialog-tool-separator"></span> -->
					
				<select name="filter_EQI_area">
					<option value="">使用区域</option>
					<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
						<option value="${areaMap.key}">${areaMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span>

				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" onclick="cx()">查询</a> <span class="toolbar-item dialog-tool-separator"></span>
				<!-- -->
				<input type="reset" value="重置" onclick="resetTime();" />
		
				<!-- 				<span class="toolbar-item dialog-tool-separator"></span> &nbsp; <a href="javascript(0)" class="easyui-linkbutton" -->
				<!-- 					onclick="alertWindowCommon(this,'${ctx}/boss/bossGdDataAnalyze/list?page=Discount','${ctx}/boss/bossGdDataAnalyze/alertWindow?method=discount',true)">修改折扣</a> -->
				<!-- -->
				<!-- 				<span class="toolbar-item dialog-tool-separator"></span> <a href="javascript(0)" class="easyui-linkbutton" -->
				<!-- 					onclick='sendPostByUrlManyCommon(this,"${ctx}/boss/bossGdDataAnalyze/updateStatus",1,true)'>开通</a> -->
				<!-- -->
				<!-- 				<a href="javascript(0)" class="easyui-linkbutton" -->
				<!-- 					onclick='sendPostByUrlManyCommon(this,"${ctx}/boss/bossGdDataAnalyze/updateStatus",0,true)'>冻结</a> -->
				<!-- -->
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:bossGdDataAnalyze:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
					onclick="addCommon('${ctx}/boss/bossGdDataAnalyze/list?page=Add');">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossGdDataAnalyze:delete">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
					onclick="del('bossGdDataAnalyze/delete')">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossGdDataAnalyze:update">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
					onclick="updCommon('${ctx}/boss/bossGdDataAnalyze/list?page=Update')">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossGdDataAnalyze:exportExcel">
				<!--			<span class="toolbar-item dialog-tool-separator"></span> -->
				<!--			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true" onclick="exportExcel('bossGdDataAnalyze/exportExcel')">导出Excel</a> -->
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
						// 						url : '${ctx}/boss/bossGdDataAnalyze/getTotalList',
						url : '${ctx}/boss/bossGdDataAnalyze/bossGdDataAnalyzeList',
						fit : true,
						fitColumns : true,//自动填充,如果字段过多就改为false
						queryParams : $("#searchFrom").serializeObject(),
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
									{ field : 'id', title : 'id', width : 80, sortable : true },
									{ field : 'city', title : '城市', width : 100, sortable : true },
									{ field : 'totalNum', title : '订单笔数', width : 100, sortable : true },
									{ field : 'totalCost', title : '订单成本', width : 100, sortable : true },
									{ field : 'successNum', title : '成功笔数', width : 100, sortable : true },
									{ field : 'successCost', title : '成功成本', width : 100, sortable : true },
									{ field : 'dayTotalNum', title : '当天订单笔数', width : 100, sortable : true },
									{ field : 'daySuccessNum', title : '当天成功笔数', width : 100, sortable : true },
									{ field : 'dayTotalCost', title : '当天订单金额', width : 100, sortable : true },
									{ field : 'daySuccessCost', title : '当天成功金额', width : 100, sortable : true },
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
										} },
									{ field : 'addTime', title : '添加时间', width : 130, sortable : true,
										formatter : function(value, row, index) {
											return long2date(value);
										} },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});
		function resetTime() {
			var filter_GED_addTime = $("input[name='filter_GED_addTime']");
			var date = str2date("${reqMap.start}");
			filter_GED_addTime.val(date.format("yyyy-MM-dd HH:mm:ss"));
			var filter_LED_addTime = $("input[name='filter_LED_addTime']");
			var date = str2date("${reqMap.end}");
			filter_LED_addTime.val(date.format("yyyy-MM-dd HH:mm:ss"));

		}

		function getNowFormatDate(date) {
			var seperator1 = "-";
			var seperator2 = ":";
			var month = date.getMonth() + 1;
			var strDate = date.getDate();
			if (month >= 1 && month <= 9) {
				month = "0" + month;
			}
			if (strDate >= 0 && strDate <= 9) {
				strDate = "0" + strDate;
			}
			var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " " + date.getHours()
					+ seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
			return currentdate;
		}
	</script>
</body>
</html>