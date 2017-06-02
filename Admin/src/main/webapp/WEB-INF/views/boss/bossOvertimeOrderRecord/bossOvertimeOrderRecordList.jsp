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
				<input id="d1" type="text" name="filter_GED_addTime" class="easyui-my97" startDate='%y-%M-%d 00:00:00'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '接收订单起始时间'" />
				--
				<input id="d2" type="text" name="filter_LED_addTime" class="easyui-my97" startDate='%y-%M-%d 23:59:59'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '接收订单结束时间'" />
				<input type="text" name="filter_INI_id" value="" class="easyui-validatebox" data-options="width:60,prompt: 'id'"
					onchange="trimLeft(this)" />
				<input type="text" name="filter_INI_orderId" value="" class="easyui-validatebox" data-options="width:60,prompt: '订单id'"
					onchange="trimLeft(this)" />
				<input type="text" name="filter_LIKES_phone" value="" class="easyui-validatebox" data-options="width:120,prompt: '手机号'"
					onchange="trimLeft(this)" />
				<input type="text" name="filter_LIKES_addUser" value="" class="easyui-validatebox" data-options="width:120,prompt: '重发用户'"
					onchange="trimLeft(this)" />
				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" onclick="cx()">查询</a> <span class="toolbar-item dialog-tool-separator"></span>
				<!-- -->
				<input type="reset" value="重置" onclick="resetValue()" />
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:bossOvertimeOrderRecord:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
					onclick="addCommon('${ctx}/boss/bossOvertimeOrderRecord/list?page=Add');">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossOvertimeOrderRecord:delete">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
					onclick="del('bossOvertimeOrderRecord/delete')">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossOvertimeOrderRecord:update">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
					onclick="updCommon('${ctx}/boss/bossOvertimeOrderRecord/list?page=Update')">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossOvertimeOrderRecord:exportExcel">
				<!--			<span class="toolbar-item dialog-tool-separator"></span> -->
				<!--			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true" onclick="exportExcel('bossOvertimeOrderRecord/exportExcel')">导出Excel</a> -->
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
						url : '${ctx}/boss/bossOvertimeOrderRecord/bossOvertimeOrderRecordList',
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
									{ field : 'id', title : 'id', sortable : true },
									{ field : 'orderId', title : '订单id', sortable : true },
									{ field : 'addTime', title : '重发时间', sortable : true,
										formatter : function(value, row, index) {
											return long2date(value);
										} },
									{ field : 'addUser', title : '重发用户', width : 100, sortable : true },
									{ field : 'customerId', title : '采购商ID' },
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
									{ field : 'successId', title : '供应商ID' },
									{ field : 'successName', title : '供应商', formatter : function(value, row, index) {
										//获取供货商的列表
										var $provider = ${applicationScope.sysParam.providerInfoMap};
										var retrunVal = "";
										$.each($provider, function(key, val) {
											if (key == row.successId) {
												retrunVal = val.providerName;
											}
										});
										return retrunVal;
									} },
									{ field : 'beyoudOperation', title : '运营商' },
									{ field : 'phone', title : '充值号码' },
									{ field : 'province', title : '省份' },
									{ field : 'city', title : '城市' },
									{ field : 'categoryId', title : '采购商产品名称', formatter : function(value, row, index) {
										var $productCategory = ${applicationScope.sysParam.productCategoryInfoMap};
										var retrunVal = "";
										$.each($productCategory, function(key, val) {
											if (key == value) {
												retrunVal = val.categoryName;
												return;
											}
										});
										return retrunVal;
									} },
									{
										field : 'providerCategoryId',
										title : '供货商产品名称',
										formatter : function(value, row, index) {
											var retrunVal = "";
											$.each(${applicationScope.sysParam.productCategoryInfoMap}, function(key,
													val) {
												if (key == value) {
													retrunVal = val.categoryName;
													return;
												}
											});
											return retrunVal;
										} },
									{ field : 'productStandard', title : '产品规格',
										formatter : function(value, row, index) {
											var $productSize = ${applicationScope.sysParam.productCategoryInfoMap};
											var size = "";
											var unit = "";
											$.each($productSize, function(key, val) {
												if (row.categoryId == val.id) {
													size = val.productNum;
													if (val.productUnit == 1) {
														unit = "M";
													} else if (val.productUnit == 2) {
														unit = "G";
													} else if (val.productUnit == 3) {
														unit = "元";
													}
												}
											});
											return size + unit + "";
										} },
									{ field : 'price', title : '总价' },
									{ field : 'cost', title : '成本价' },
									{
										field : 'earn',
										title : '利润',
										formatter : function(value, row, index) {
											var money;
											if (row.status == 3) {
												money = (row.price - row.cost).toFixed(2);
												if (money < 0) {
													return '<span style="color:red;font-weight:bold;">' + money
															+ '</span>';
												} else {
													return '<span style="color:green;font-weight:bold;">' + money
															+ '</span>';
												}

											} else
												return "";
										} },
									{ field : 'price_before', title : '原售价', formatter : function(value, row, index) {
										var returnVal = "";
										if (row.weiXinPrice != null) {
											returnVal = row.weiXinPrice;
										} else if (row.tmallPrice != null) {
											returnVal = row.tmallPrice + "--" + row.tmallCost;
										}
										return returnVal;
									} },
									{ field : 'status', title : '订单状态', formatter : function(value, row, index) {
										if (value == 1) {
											return "新增";
										} else if (value == 2) {
											return "充值中";
										} else if (value == 3) {
											return "充值成功";
										} else if (value == 4) {
											return "充值失败";
										} else if (value == 5) {
											return "等待确认";
										} else if (value == 6) {
											return "需要手工处理";
										} else if (value == 7) {
											return "财务平账订单,客服不处理";
										}
									} },
									{ field : 'failReason', title : '失败原因' },
									{ field : 'receiveTime', title : '订单提交时间', formatter : function(value, row, index) {
										/* return jsonTimeStamp(value); */
										return timestampformat(value);
									} },
									{ field : 'endTime', title : '订单处理时间', formatter : function(value, row, index) {
										if (row.status == 3) {
											return jsonTimeStamp(row.successTime);
										} else if (row.status == 4) {
											return jsonTimeStamp(row.failTime);
										} else
											return "";
									} },
									{ field : 'useTime', title : '耗时(s)', formatter : function(value, row, index) {
										if (row.status == 3) {
											return (new Date(row.successTime) - new Date(row.receiveTime)) / 1000;
										} else if (row.status == 2) {
											return getDintanceBetweenTwoTime(row.receiveTime);
										} else if (row.status == 4) {
											return (new Date(row.failTime) - new Date(row.receiveTime)) / 1000;
										}
									} },

									{ field : 'callbackStatus', title : '回调状态',
										formatter : function(value, row, index) {
											if (value == 1) {
												return "未回调";
											} else if (value == 2) {
												return "已回调";
											} else if (value == 3) {
												return "已主动回调";
											}
										} }, { field : 'chargeCount', title : '充值次数' },
									{ field : 'orderKey', title : '采购商订单号' },
									{ field : 'providerKey', title : '供应商流水号' },
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
		function timestampformat(timestamp) {
			return (new Date(timestamp)).format("yyyy-MM-dd hh:mm:ss");
		}
		//获取当前的日期时间 格式“yyyy-MM-dd HH:MM:SS”
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