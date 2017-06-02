<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body style="font-family: '微软雅黑'">
	<div id="top" style="height: 95%;color: green;position:absolute;top:0px;width: 100%;">
		<div id="tb" style="padding:5px;height:auto">
			<div>
				<form id="searchFrom" action="">
					<input type="text" name="filter_INI_id" value="${bossProviderCharge.id}" class="easyui-validatebox"
						data-options="width:60,prompt: 'id'" onchange="trimLeft(this)" />

					<input type="text" name="filter_EQI_orderId" value="${bossProviderCharge.orderId}" class="easyui-validatebox"
						data-options="width:80,prompt: '订单id'" onchange="trimLeft(this)" />

					<input type="text" name="filter_EQI_productId" value="${bossProviderCharge.productId}" class="easyui-validatebox"
						data-options="width:80,prompt: '供货商产品id'" onchange="trimLeft(this)" />

					<input type="text" name="filter_LIKES_phone" value="${bossProviderCharge.phone}" class="easyui-validatebox"
						data-options="width:120,prompt: '手机号'" onchange="trimLeft(this)" />
					<input type="text" name="filter_EQM_cost" class="easyui-validatebox" data-options="width:80,prompt: '成本'"
						onchange="trimLeft(this)" />

					<!-- 		供应商查询 start	 -->
					<input type="text" name="filter_EQI_providerId" value="${orderInfo.providerId}" class="easyui-validatebox"
						data-options="width:60,prompt: '供应商id'" />
					<input type="text" name="providerName" class="easyui-validatebox" data-options="width:150,prompt: '供应商名称'" />
					<!-- 				<span class="toolbar-item dialog-tool-separator"></span> 供应商：<select name="providerId" onchange="getSuccessIdBySelect()"> -->
					<!-- 					<option value="">请选择</option> -->
					<!-- 					<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap"> -->
					<!-- 						<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option> -->
					<!-- 					</c:forEach> -->
					<!-- 				</select> -->
					<input type="hidden" name="filter_INI_providerId" class="easyui-validatebox" data-options="width:100,prompt: '供应商id'" />
					<!---		供应商查询 end		 -->
					<!-- 		 产品查询	start		 -->
					<input type="text" name="filter_EQI_categoryId" class="easyui-validatebox" data-options="width:60,prompt: '产品id'" />
					<input type="text" name="categoryName" class="easyui-validatebox" data-options="width:150,prompt: '产品名称'" />
					<input type="hidden" name="filter_INI_categoryId" class="easyui-validatebox" data-options="width:150,prompt: '产品id'" />
					<!-- 		 产品查询	end		 -->
					<div style="height: 7px;"></div>
					<input type="text" name="filter_GED_callTime" class="easyui-my97" value="${reqMap.yestodayStart}"
						startDate='%y-%M-%d 00:00:00' datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '回调时间'" />
					--
					<input type="text" name="filter_LED_callTime" class="easyui-my97" value="${reqMap.todayEnd}" startDate='%y-%M-%d 23:59:59'
						datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '回调时间'" />
					<!-- -->
					<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_EQI_status">
						<option value="">供应商状态</option>
						<c:forEach items="${applicationScope.sysParam.orderStatusMap}" var="orderStatusMap">
							<option value="${orderStatusMap.key}">${orderStatusMap.value}</option>
						</c:forEach>
					</select>
					<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="orderInfo_status">
					<option value="">订单状态</option>
					<c:forEach items="${applicationScope.sysParam.orderStatusMap}" var="orderStatusMap">
						<option value="${orderStatusMap.key}">${orderStatusMap.value}</option>
					</c:forEach>
				</select>
					<!-- -->
					<span class="toolbar-item dialog-tool-separator"></span>
					<input type="text" name="filter_LIKES_orderCode" value="${bossProviderCharge.orderCode}" class="easyui-validatebox"
						data-options="width:120,prompt: '供货商流水号'" onchange="trimLeft(this)" />
					<input type="text" name="filter_LIKES_failReason" value="${bossProviderCharge.failReason}" class="easyui-validatebox"
						data-options="width:120,prompt: '失败原因'" onchange="trimLeft(this)" />
					<!-- 				<input type="text" name="filter_LIKES_poolNum" value="${bossProviderCharge.poolNum}" class="easyui-validatebox" -->
					<!-- 					data-options="width:120,prompt: ''" onchange="trimLeft(this)" /> -->

					<!-- 				<input type="text" name="filter_EQI_sizeValue" value="${bossProviderCharge.sizeValue}" class="easyui-validatebox" -->
					<!-- 					data-options="width:80,prompt: ''" onchange="trimLeft(this)" /> -->
					<div style="height: 7px;"></div>
					<a href="javascript(0)" class="easyui-linkbutton" onclick="cx();">查询</a> <span class="toolbar-item dialog-tool-separator"></span>
					<!-- -->
					<input type="reset" value="重置" onclick="resetValue();resetTime();" />
					<!-- 				<span class="toolbar-item dialog-tool-separator"></span> &nbsp; <a href="javascript(0)" class="easyui-linkbutton" -->
					<!-- 					onclick="alertWindowCommon(this,'${ctx}/boss/bossProviderCharge/list?page=Discount','${ctx}/boss/bossProviderCharge/alertWindow?method=discount',true)">修改折扣</a> -->
					<!-- -->
					<span class="toolbar-item dialog-tool-separator"></span> <a href="javascript(0)" class="easyui-linkbutton"
						onclick='sendPostByUrlManyCommon(this,"${ctx}/boss/bossProviderCharge/updateStatus",1,true)'>手工成功</a>
					<!-- -->
					<a href="javascript(0)" class="easyui-linkbutton"
						onclick='sendPostByUrlManyCommon(this,"${ctx}/boss/bossProviderCharge/updateStatus",0,true)'>手工失败</a>
					<!-- -->
				</form>
				<div style="height: 7px;"></div>
				<shiro:hasPermission name="boss:bossProviderCharge:add">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="addCommon('${ctx}/boss/bossProviderCharge/list?page=Add');">添加</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="boss:bossProviderCharge:delete">
					<span class="toolbar-item dialog-tool-separator"></span>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="del('bossProviderCharge/delete')">删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="boss:bossProviderCharge:update">
					<span class="toolbar-item dialog-tool-separator"></span>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
						onclick="updCommon('${ctx}/boss/bossProviderCharge/list?page=Update')">修改</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="boss:bossProviderCharge:exportExcel">
					<span class="toolbar-item dialog-tool-separator"></span>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true"
						onclick="exportExcelCommon('exportExcel')">导出Excel</a>
				</shiro:hasPermission>
			</div>

		</div>
		<table id="dg"></table>
		<div id="dlg"></div>
	</div>
	<!-- 使用 flex-box css3属性平分DIV -->
	<div id="bottom" style="height:5%;width:100%; position:absolute;bottom:0;">
		<div style="display:-moz-box;display:-webkit-box;display:box;width:100%;">
			<div>
				<button onclick="getCountInfo()" id="countInfo">统计信息</button>
			</div>
			<div id="bottomLeft" style="-moz-box-flex:1.0;-webkit-box-flex:1.0;box-flex:1.0;"></div>
			<shiro:hasPermission name="boss:orderInfo:showProfit">
				<div id="bottomRight" style="-moz-box-flex:1.0;-webkit-box-flex:1.0;box-flex:1.0;border-left:3px solid white"></div>
			</shiro:hasPermission>
		</div>
	</div>
	<script type="text/javascript">
		var dg;
		resetTime();
		$(function() {
			dg = $('#dg').datagrid(
					{
						method : 'POST',
						url : '${ctx}/boss/bossProviderCharge/bossProviderChargeList',
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
									{ field : 'id', title : 'id', sortable : true },
									{ field : 'orderId', title : '订单id', sortable : true },
									{ field : 'categoryId', title : '产品id', sortable : true },
									{ field : 'categoryName', title : '产品名称', formatter : function(value, row, index) {
										var returnVal = "";
										$.each(${applicationScope.sysParam.productCategoryInfoMap}, function(key, val) {
											if (key == row.categoryId) {
												returnVal = val.categoryName;
												return;
											}
										});
										return returnVal;
									} },
									{ field : 'providerId', title : '供应商id', sortable : true },
									{ field : 'successName', title : '供应商', formatter : function(value, row, index) {
										//获取供货商的列表
										var returnVal = "";
										$.each(${applicationScope.sysParam.providerInfoMap}, function(key, val) {
											if (key == row.providerId) {
												returnVal = val.providerName;
											}
										});
										return returnVal;
									} },
									{ field : 'productId', title : '供应商产品id', sortable : true },
									{
										field : 'providerCategoryId',
										title : '供货商产品名称',
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.providerProductInfoMap}, function(key,
													val) {
												if (key == row.productId) {
													returnVal = val.productName;
													return;
												}
											});
											return returnVal;
										} },

									{ field : 'phone', title : '手机号', sortable : true },
									{ field : 'cost', title : '成本', sortable : true },
									{ field : 'callTime', title : '提交时间', sortable : true,
										formatter : function(value, row, index) {
											return long2date(value);
										} },
									{ field : 'status', title : '供货商状态', sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.orderStatusMapJson}, function(key, val) {
												if (key == value) {
													returnVal = val;
													return;
												}
											});
											return returnVal;
										} },
									{ field : 'orderStatus', title : '订单状态', sortable : true,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.orderStatusMapJson}, function(key, val) {
												if (key == value) {
													returnVal = val;
													return;
												}
											});
											return returnVal;
										} }, { field : 'orderCode', title : '供货商流水号', sortable : true },
									{ field : 'failReason', title : '供货商失败原因', sortable : true },
									{ field : 'orderReason', title : '订单失败原因', sortable : true },
									{ field : 'poolNum', title : '流量池编号', sortable : true },
									{ field : 'sizeValue', title : '流量池大小', sortable : true },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});
		//重置时间
		function resetTime() {
			var filter_GED_callTime = $("input[name='filter_GED_callTime']");
			var date = new Date();
			date.setHours(-24, 0, 0);
			filter_GED_callTime.val(long2date(date));
			var filter_LED_callTime = $("input[name='filter_LED_callTime']");
			var date = new Date();
			date.setHours(24, 0, 0);
			filter_LED_callTime.val(long2date(date));
		}
		//创建查询对象并查询
		function cx() {
			clearRows();
			getCategoryId();
			var obj = $("#searchFrom").serializeObject();
			dg.datagrid('reload', obj);
		}
		function getCountInfo() {
			var countInfo = $("#countInfo");
			countInfo.css('display', "none");
			$("#bottomLeft").html("");
			$("#bottomRight").html("");
			var obj = $("#searchFrom").serializeObject();
			$.ajax({
				url : "${ctx}/boss/bossProviderCharge/getCountInfo",
				type : "POSt",
				data : obj,
				success : function(data) {
					countInfo.css('display', "");
					if (data != null) {
						if (data.failReason != null) {
							$("#bottomLeft").html(data.failReason);
						} else {
							var str1 = "总订单：" + data.totalNum + "笔<br/>失 &nbsp;&nbsp;败：" + data.failNum + "笔<br/>充值中："
									+ data.chargeNum + "笔<br>等待确认：" + data.waitNum + "笔<br/>手工处理：" + data.handleNum
									+ "笔";
							var str2 = "总销售：" + data.totalMoney.toFixed(2) + "元<br/>失 &nbsp;&nbsp;败："
									+ data.failMoney.toFixed(2) + "元<br/>充值中：" + data.chargeMoney.toFixed(2)
									+ "元<br/>等待确认：" + data.waitMoney.toFixed(2) + "元<br/>手工处理："
									+ data.handleMoney.toFixed(2) + "元";
							$("#bottomLeft").html(str1);
							$("#bottomRight").html(str2);
						}
					}
				}, error : function() {

				} });
		}
	</script>
</body>
</html>