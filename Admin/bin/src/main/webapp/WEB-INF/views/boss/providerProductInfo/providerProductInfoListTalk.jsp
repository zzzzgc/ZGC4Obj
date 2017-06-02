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
		<input type="hidden" id="providerIdFlag" name="providerIdFlag" value="${providerIdFlag}" class="easyui-validatebox"
			data-options="width:150,prompt: '判断有没有传providerId'" />
		<input type="hidden" id="categoryIdFlag" name="categoryIdFlag" value="${categoryIdFlag}" class="easyui-validatebox"
			data-options="width:150,prompt: '判断有没有传categoryId'" />
		<div>
			<form id="searchFrom" action="">
				<input type="hidden" name="filter_INI_categoryId" class="easyui-validatebox" data-options="width:150,prompt: '产品id'" />
				<input type="text" name="filter_EQS_productName" class="easyui-validatebox" data-options="width:150,prompt: '产品名称'" />
				<input type="hidden" name="standarPrice" class="easyui-validatebox" data-options="width:150,prompt: '标准价'" />
				<input type="text" name="filter_EQI_priority" class="easyui-validatebox" data-options="width:150,prompt: '通道优先级'" />
				供应商：<select id="filter_EQI_providerId" name="filter_EQI_providerId" onclick="testPro();">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap">
						<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> <br />
				<div style="height: 7px;"></div>
				省份: <select id="province" name="filter_EQS_province">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.productProvinceMap}" var="productProvinceMap">
						<option value="${productProvinceMap.value}">${productProvinceMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 状态: <select name="filter_EQI_status">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.productMap}" var="productMap">
						<option value="${productMap.key}">${productMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 二次通道: <select name="filter_EQI_isSecondChannel">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
						<option value="${isNoMap.key}">${isNoMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 运营商: <select name="filter_EQS_mobileOperator">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
						<option value="${providerMap.value}">${providerMap.value}</option>
					</c:forEach>
				</select> 使用区域：<select name="area">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
						<option value="${areaMap.key}">${areaMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span>
				<input type="text" name="productStandard" class="easyui-validatebox" data-options="width:60,prompt: '产品规格'" />
				<br />
				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a> <span
					class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置" />
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true"
				data-options="disabled:false" onclick="exportExcel()">导出Excel</a>
			</form>
		</div>

	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
		var dg;
		$(function() {
			var standardPrice = null;
			var discount = null;
			dg = $('#dg')
					.datagrid(
							{
								method : "POST",
								url : '${ctx}/boss/providerProductInfo/providerProductInfoListTalk',
								queryParams : { filter_EQI_providerId : $("#providerIdFlag").val(),
									filter_EQI_categoryId : $("#categoryIdFlag").val() },
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
								/* singleSelect:true, */
								columns : [
									[
											{ checkbox : true },
											{ field : 'id', title : 'id', hidden : true },
											// 		{field:'providerId',title:'供应商ID'},
											{ field : 'productName', title : '产品名称', width : 200 },
											{
												field : 'categoryName',
												title : '产品大类名称',
												width : 200,
												formatter : function(value, row, index) {
													//获取产品列表
													var $provider = ${applicationScope.sysParam.productCategoryInfoMap};
													var retrunVal = "";
													$.each($provider, function(key, val) {
														if (key == row.categoryId) {
															retrunVal = val.categoryName;
														}
													});
													return retrunVal;
												} },
												{
													field : 'productCategoryInfo_packSize',
													title : '规格',
													formatter : function(value, row, index) {
														//获取供货商的列表
														var $productStandarPriceMap = ${applicationScope.sysParam.productCategoryInfoMap};
														var retrunVal = "";
														$.each($productStandarPriceMap, function(key, val) {
															if (key == row.categoryId) {
																
																var danwei = "";
																if( val.productUnit==1){
																	danwei = "M";
																}else if(val.productUnit==2){
																	danwei = "G";
																}else if(val.productUnit ==3){
																	danwei="元";
																}
																retrunVal = val.productNum+danwei;
															}
														});
														return retrunVal;
													} },
											{ field : 'providerName', title : '供应商名称', width : 200,
												formatter : function(value, row, index) {
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
											{
												field : 'standardPrice',
												title : '标准价',
												width : 100,
												formatter : function(value, row, index) {
													standardPrice = null;
													discount = null;
													//获取供货商的列表
													var $productStandarPriceMap = ${applicationScope.sysParam.productCategoryInfoMap};
													var retrunVal = "";
													$.each($productStandarPriceMap, function(key, val) {
														if (key == row.categoryId) {
															retrunVal = val.standarPrice;
															standardPrice = val.standarPrice;
															return;
														}
													});
													if (retrunVal != "") {
														return parseFloat(retrunVal).toFixed(3);
													}
													return retrunVal;
												} },
											{
												field : 'discount',
												title : '折扣率',
												width : 100,
												formatter : function(value, row, index) {
													//获取供货商的列表
													var $productStandarPriceMap = ${applicationScope.sysParam.productCategoryInfoMap};
													var retrunVal = "";
													$.each($productStandarPriceMap, function(key, val) {
														if (key == row.categoryId) {
															retrunVal = val.standarPrice;
															discount = val.standarPrice;
															return;
														}
													});
													var price = row.costPrice / retrunVal;
													return price.toFixed(4);
												} },
											{ field : 'costPrice', title : '折扣价', width : 100,
												formatter : function(value, row, index) {
													if (value != undefined || value != "") {
														return value.toFixed(3);
													}
													return value;
												} },
											{ field : 'province', title : '省份', width : 100 },
											{
												field : 'productArea',
												title : '使用区域',
												width : 100,
												formatter : function(value, row, index) {
													//获取产品使用区域列表
													var $productAreaMap = ${applicationScope.sysParam.productCategoryInfoMap};
													var retrunVal = "";
													$.each($productAreaMap, function(key, val) {
														if (key == row.categoryId) {
															retrunVal = val.area;
														}
													});
													if (retrunVal == 0) {
														return "本省";
													} else if (retrunVal == 1) {
														return "全国";
													} else {
														return "错误使用区域代码:" + retrunVal + ",请联系技术人员";
													}
												} }, { field : 'mobileOperator', title : '运营商', width : 100 },

									]
								], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
								enableRowContextMenu : false, toolbar : '#tb' });
			var providerIdFlagVal = $("#providerIdFlag").val();
			if (providerIdFlagVal != "") {
				$("#filter_EQI_providerId").val(providerIdFlagVal);
				$("#providerIdFlag").val("");
			}
			var categoryIdFlagVal = $("#categoryIdFlag").val();
			if (categoryIdFlagVal != "") {
				$("#filter_EQI_providerId").val(categoryIdFlagVal);
				$("#categoryIdFlag").val("");
			}
		});
		function testPro() {

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
		//弹窗增加
		function add() {
			//供应商ID
			var providerId = $("#filter_EQI_providerId").val();

			d = $("#dlg").dialog(
					{ title : '添加配置', width : 380, height : 250,
						href : '${ctx}/boss/providerProductInfo/create?providerId=' + providerId, maximizable : true,
						modal : true, buttons : [
								{ text : '确认', handler : function() {
									$("#mainform").submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//删除
		function del() {
			var rows = dg.datagrid('getSelections');
			if (rows.length > 1) {
				alert("只能选择一行");
				return;
			}
			var row = dg.datagrid('getSelected');
			if (rowIsNull(row))
				return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
				if (data) {
					$.ajax({ type : 'get', url : "${ctx}/boss/providerProductInfo/delete/" + row.id,
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
			if (rows.length > 1) {
				alert("只能选择一行");
				return;
			}
			var row = dg.datagrid('getSelected');
			if (rowIsNull(row))
				return;
			d = $("#dlg").dialog(
					{ title : '修改用户', width : 380, height : 250,
						href : '${ctx}/boss/providerProductInfo/update/' + row.id, maximizable : true, modal : true,
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
			getCategoryId();
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
		//折扣
		function discount() {
			var rows = dg.datagrid('getSelections');
			if (rows.length > 0) {
				if (rowIsNull(rows))
					return;
				d = $("#dlg")
						.dialog(
								{
									title : '修改用户',
									width : 380,
									height : 250,
									href : '${ctx}/boss/providerProductInfo/discount',
									maximizable : true,
									modal : true,
									buttons : [
											{
												text : '修改',
												id : "btnUpdate",
												handler : function() {
													var rows = dg.datagrid('getSelections');
													var ids = [];
													var standardPrices = [];
													var discount = $("#discount1").val();
													if (parseFloat(discount) <= 2 && parseFloat(discount) > 0) {
														var title = '您的折扣为<span style="color:green;font-weight:bold">'
																+ discount + '</span>';
														if (parseFloat(discount) > 1) {
															title = '您的折扣为<span style="color:red;font-weight:bold;font-size:30px;">'
																	+ discount + '</span>';
														}
														parent.$.messager
																.confirm(
																		'提示',
																		title,
																		function(data) {
																			if (data) {
																				$
																						.each(
																								rows,
																								function(index, value) {
																									ids[index] = value['id'];
																									var categoryId = value.categoryId;
																									var standardPricess = eval("("
																											+ '${applicationScope.sysParam.productCategoryInfoMap}'
																											+ ")");
																									$
																											.each(
																													standardPricess,
																													function(
																															key,
																															val) {
																														if (key == categoryId) {
																															standardPrices[index] = val.standarPrice;
																														}
																													});
																								});
																				$
																						.ajax({
																							traditional : true,
																							type : "get",
																							url : "${ctx}/boss/providerProductInfo/updateDiscount",
																							data : {
																								"ids" : ids,
																								"standardPrices" : standardPrices,
																								"discount" : discount },
																							success : function(data) {
																								if (data == "success") {
																									d.panel('close');
																									cx();
																									$('#dg')
																											.datagrid(
																													'clearSelections');
																									parent.$.messager
																											.show({
																												title : "提示",
																												msg : "操作成功！",
																												position : "bottomRight" });
																								}
																							} });
																			}
																		});
													}
												} }, { text : '取消', handler : function() {
												d.panel('close');
											} }
									] });
			} else {
				$.messager.alert("请至少选择一项");
			}
		}
		function getCategoryId() {
			var area = $("select[name=area] option:selected").val();
			var productStandard = $("input[name=productStandard]").val();
			var standarPrice = $("input[name=standarPrice]").val();
			var filter_INI_categoryId = $("input[name=filter_INI_categoryId]");
			filter_INI_categoryId.val("");
			if (area.length == 0 && standarPrice.length == 0 && productStandard.length == 0) {
				return;
			}
			var num = null;
			var unit = null;
			if (productStandard.length != 0) {
				if (productStandard < 1000) {
					num = productStandard;
					unit = "1"; //单位为M
				} else {
					if (productStandard >= 1000) {
						num = productStandard / 1000;
						unit = "2"; //单位为G
					}
				}
			}
			$.each(${applicationScope.sysParam.productCategoryInfoMap}, function(key, val) {
				if (((val.productNum == num && val.productUnit == unit) || productStandard.length == 0)
						&& (standarPrice == val.standarPrice || standarPrice.length == 0)
						&& (area == val.area || area.length == 0)) {
					if (filter_INI_categoryId.val().length == 0) {
						filter_INI_categoryId.val(val.id);
					} else {
						filter_INI_categoryId.val(filter_INI_categoryId.val() + "," + val.id);
					}
				}
			});
			if (!(area.length == 0 && standarPrice.length == 0 && productStandard.length == 0)
					&& filter_INI_categoryId.val().length == 0) {
				//category不存在 但不能为空,如果为空这个条件会被过滤
				filter_INI_categoryId.val(666666);
			}
		}
		//批量更新状态
		function updateStatus(state) {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}

			var ids = new Array();
			for (var i = 0; i < rows.length; i++) {
				ids[i] = rows[i].id;
			}

			$.ajax({ url : "${ctx}/boss/providerProductInfo/updateStatus", type : "post", traditional : true,
				data : { "ids" : ids, "state" : state }, success : function(value) {
					dg.datagrid('reload');
				} });
		}
		//导出excel
		function exportExcel() {
			getCategoryId();
			var obj = $("#searchFrom").serializeObject();
			var params = "?";
			for ( var name in obj) {
				params += name + "=" + obj[name] + "&&";
			}
			params = params.substring(0, params.length - 2);
			parent.$.messager.show({ title : "提示", msg : "导出需要时间，请稍等片刻！", position : "bottomRight" });
			var url = "${ctx}/boss/providerProductInfo/exportExcelTalk" + params;
			window.location.href = url;
		}
	</script>
</body>
</html>