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
				<input type="text" name="filter_EQI_id" class="easyui-validatebox" data-options="width:60,prompt: 'id'" />
				<input type="text" name="filter_EQI_orderId" class="easyui-validatebox" data-options="width:80,prompt: '订单id'" />
				审核状态：<select name="filter_EQI_auditStatus">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.auditMap}" var="auditMap">
						<option value="${auditMap.key}">${auditMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span>
				<input type="text" name="filter_GED_auditTime" class="easyui-my97" startDate='%y-%M-%d 00:00:00' datefmt="yyyy-MM-dd HH:mm:ss"
					data-options="width:150,prompt: '审核时间'" />
				--
				<input type="text" name="filter_LED_auditTime" class="easyui-my97" startDate='%y-%M-%d 23:59:59' datefmt="yyyy-MM-dd HH:mm:ss"
					data-options="width:150,prompt: '审核时间'" />
				<span class="toolbar-item dialog-tool-separator"></span>
				<input type="text" name="filter_EQI_auditUser" class="easyui-validatebox" data-options="width:80,prompt: '审核人'" />
				<input type="text" name="filter_GED_addTime" class="easyui-my97" startDate='%y-%M-%d 00:00:00' datefmt="yyyy-MM-dd HH:mm:ss"
					data-options="width:150,prompt: '添加时间'" />
				--
				<input type="text" name="filter_LED_addTime" class="easyui-my97" startDate='%y-%M-%d 23:59:59' datefmt="yyyy-MM-dd HH:mm:ss"
					data-options="width:150,prompt: '添加时间'" />
				<span class="toolbar-item dialog-tool-separator"></span>

				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" onclick="cx()">查询</a> <span class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置" onclick="resetValue()" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp; 
				<shiro:hasPermission name="boss:invoiceAudit:audit">
				<a href="javascript(0)" class="easyui-linkbutton"
					onclick='sendPostByUrlMany(this,"bossInvoiceAudit/updateStatus",2)'>不通过</a> <a href="javascript(0)" class="easyui-linkbutton"
					onclick='sendPostByUrlMany(this,"bossInvoiceAudit/updateStatus",1)'>通过</a>
				</shiro:hasPermission>
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:orderInfo:add">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
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
						url : '${ctx}/boss/bossInvoiceAudit/bossInvoiceAuditList',
						fit : true,
						fitColumns : false,//自动填充,如果字段过多就改为false
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
									{ field : 'id', title : 'id', width : 80,sortable:true},
									{ field : 'orderId', title : '订单id', width : 100,sortable:true},		
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
												return ;
											}
										});
										return retrunVal;
									} },
									{ field : 'providerCategoryId', title : '供货商产品名称', formatter : function(value, row, index) {
										var retrunVal = "";
										$.each(${applicationScope.sysParam.productCategoryInfoMap}, function(key, val) {
											if (key == value) {
												retrunVal = val.categoryName;
												return ;
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
										}
									} },
									{ field : 'receiveTime', title : '订单提交时间', formatter : function(value, row, index) {
										return long2date(value);
									} },
									{ field : 'endTime', title : '订单处理时间', formatter : function(value, row, index) {
										if (row.status == 3) {
											return long2date(row.successTime);
										} else if (row.status == 4) {
											return long2date(row.failTime);
										} else
											return "";
									} },
									
									
									{ field : 'auditStatus', title : '审核状态', width : 100, formatter : function(value, row, index) {
										var returnVal = "";
										$.each(${applicationScope.sysParam.auditMapJson}, function(key, val) {
											if (key == value) {
												returnVal = greenOrRed(key,val,true);
												return ;
											}
										});
										return returnVal;
									} },
									{ field : 'auditTime', title : '审核时间', width : 130, formatter : function(value, row, index) {
										return long2date(value);
									} },
									{ field : 'auditUser', title : '审核人', width : 100, formatter : function(value, row, index) {
										var returnVal = "";
										$.each(${applicationScope.sysParam.userMapJson}, function(key, val) {
											if (key == value) {
												returnVal = val.name;
												return ;
											}
										});
										return returnVal;
									} },
									{ field : 'addTime', title : '添加时间', width : 130, formatter : function(value, row, index) {
										return long2date(value);
									} },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});
		//弹窗增加
		function add() {
			d = $("#dlg").dialog(
					{ title : '添加配置', width : 380, height : 250, href : '${ctx}/boss/orderInfo/create',
						maximizable : true, modal : true, buttons : [
								{ text : '确认', handler : function() {
									$("#mainform").submit();
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
		function getErrorMsg(rows, errorMsg){
			for(var i = 0;i<rows.length;i++){
				if(rows[i].auditStatus!=0){
					return "只能审核未审核的状态";
				}
			}
		}
	</script>
</body>
</html>