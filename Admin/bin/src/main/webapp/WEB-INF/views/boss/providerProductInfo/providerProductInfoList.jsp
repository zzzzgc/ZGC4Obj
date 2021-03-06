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
				<input type="hidden" name="standarPrice" class="easyui-validatebox" data-options="width:150,prompt: '标准价'" />
				<input type="hidden" name="filter_EQI_priority" class="easyui-validatebox" data-options="width:150,prompt: '通道优先级'" />
				<!-- 		供应商查询 start	 -->
				<input type="text" name="filter_EQI_providerId" value="${providerId}" class="easyui-validatebox"
					data-options="width:60,prompt: '供应商id'" />
				<input type="text" name="providerName" class="easyui-validatebox" data-options="width:150,prompt: '供应商名称'" />
				<span class="toolbar-item dialog-tool-separator"></span> 供应商：<select name="providerId" onchange="getProviderIdBySelect()">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap">
						<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
					</c:forEach>
				</select>
				<input type="hidden" name="filter_INI_providerId" class="easyui-validatebox" data-options="width:100,prompt: '供应商id'" />
				<!---		供应商查询 end		 -->
				<br />
				<div style="height: 7px;"></div>

				状态: <select name="filter_EQI_status">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.productMap}" var="productMap">
						<option value="${productMap.key}">${productMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 二次通道: <select name="filter_EQI_isSecondChannel">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
						<option value="${isNoMap.key}">${isNoMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span>
				<!-- 		 产品查询	start		 -->
				<input type="text" name="filter_EQI_categoryId" value="${categoryId}" class="easyui-validatebox"
					data-options="width:60,prompt: '产品id'" />
				<input type="text" name="categoryName" class="easyui-validatebox" data-options="width:150,prompt: '产品名称'" />
				<input type="hidden" name="filter_INI_categoryId" class="easyui-validatebox" data-options="width:150,prompt: '产品id'" />
				<span class="toolbar-item dialog-tool-separator"></span> 省份：<select name="province">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.productProvinceMap}" var="productProvinceMap">
						<option value="${productProvinceMap.key}">${productProvinceMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 运营商：<select name="operator">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
						<option value="${providerMap.key}">${providerMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span>
				<input type="text" name="productStandard" class="easyui-validatebox" data-options="width:60,prompt: '产品规格'" />
				<span class="toolbar-item dialog-tool-separator"></span> 使用区域：<select name="area">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
						<option value="${areaMap.key}">${areaMap.value}</option>
					</c:forEach>
				</select>
				<!-- 		 产品查询	end		 -->
				<span class="toolbar-item dialog-tool-separator"></span> <br />
				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a> <span
					class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="button" value="更改折扣" onclick="discount()" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="reset" value="批量开通" onclick="updateStatus(this,1)" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="reset" value="批量冻结" onclick="updateStatus(this,2)" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="reset" value="开通发送短信" onclick="sendPostByUrlMany(this,'providerProductInfo/updateIsSendMsg',1)" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="reset" value="关闭发送短信" onclick="sendPostByUrlMany(this,'providerProductInfo/updateIsSendMsg',0)" />
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:providerProductInfo:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:providerProductInfo:delete">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false"
					onclick="del()">删除</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:providerProductInfo:update">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"
					onclick="upd()">修改</a>
			</shiro:hasPermission>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true"
				data-options="disabled:false" onclick="exportExcel()">导出Excel</a> <a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addBatch();">批量添加</a> <span class="toolbar-item dialog-tool-separator"></span>

		</div>

	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
		var dg;
		$(function() {
			dg = $('#dg')
					.datagrid(
							{
								method: 'POST',
								url : '${ctx}/boss/providerProductInfo/providerProductInfoList',
								queryParams : { filter_EQI_providerId : $("input[name=filter_EQI_providerId]").val(),
									filter_EQI_categoryId : $("input[name=filter_EQI_categoryId]").val() },
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
											{ field : 'id', title : 'id' },
											{ field : 'providerId', title : '供应商ID' },
											{ field : 'providerName', title : '供应商名称',
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
											{ field : 'categoryId', title : '产品类别id' },
											{
												field : 'categoryName',
												title : '产品大类名称',
												formatter : function(value, row, index) {
													//获取产品列表
													var $provider = ${applicationScope.sysParam.productCategoryInfoMap};
													var retrunVal = "";
													$.each($provider, function(key, val) {
														if (key == row.categoryId) {
															retrunVal = val.categoryName;
														}
													});

													var str = "";
													if (retrunVal.indexOf("黑龙江") != -1
															|| retrunVal.indexOf("内蒙古") != -1) {
														str = retrunVal.substring(0, 5)
																+ retrunVal.substring(retrunVal.length - 3,
																		retrunVal.length);
													} else {
														str = retrunVal.substring(0, 4)
																+ retrunVal.substring(retrunVal.length - 3,
																		retrunVal.length);
													}
													return str;
												} },
											{ field : 'productName', title : '产品名称' },
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
											{ field : 'costPrice', title : '成本价',
												formatter : function(value, row, index) {
													if (value != undefined || value != "") {
														return value.toFixed(5);
													}
													return value;
												} },
											{
												field : 'standardPrice',
												title : '标准价',
												formatter : function(value, row, index) {
													//获取供货商的列表
													var $productStandarPriceMap = ${applicationScope.sysParam.productCategoryInfoMap};
													var retrunVal = "";
													$.each($productStandarPriceMap, function(key, val) {
														if (key == row.categoryId) {
															retrunVal = val.standarPrice;
														}
													});
													if (retrunVal != "") {
														return parseFloat(retrunVal).toFixed(5);
													}
													return retrunVal;
												} },
											{
												field : 'discount',
												title : '折扣率',
												formatter : function(value, row, index) {
													//获取供货商的列表
													var $productStandarPriceMap = ${applicationScope.sysParam.productCategoryInfoMap};
													var retrunVal = "";
													$.each($productStandarPriceMap, function(key, val) {
														if (key == row.categoryId) {
															retrunVal = val.standarPrice;
														}
													});
													var price = row.costPrice / retrunVal;
													return price.toFixed(5);
												} },
											{ field : 'province', title : '省份' },
											{
												field : 'productArea',
												title : '使用区域',
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
												} },
											{
												field : 'isSecondChannel',
												title : '二次通道',
												formatter : function(value, row, index) {
													var retrunVal = "";
													$.each(${applicationScope.sysParam.isNoMapJson},
															function(key, val) {
																if (key == value) {
																	retrunVal = val;
																	return;
																}
															});
													return retrunVal;
												} },
											{
												field : 'isSendMsg',
												title : '是否发送短信',
												formatter : function(value, row, index) {
													var retrunVal = "";
													$.each(${applicationScope.sysParam.isNoMapJson},
															function(key, val) {
																if (key == value) {
																	retrunVal = greenOrRed(key, val);
																	return;
																}
															});
													return retrunVal;
												} },
											{ field : 'mobileOperator', title : '运营商' },
											{
												field : 'status',
												title : '状态',
												formatter : function(value, row, index) {
													if (value == 1) {
														return '<span style="color:green;font-weight:bold;">' + "使用中"
																+ '</span>';
													} else if (value == 2) {
														return '<span style="color:red;font-weight:bold;">' + "已下架"
																+ '</span>';
													}
												} },
											{ field : 'addTime', title : '添加时间',
												formatter : function(value, row, index) {
													if (value != null) {
														var time = jsonTimeStamp(value);
														return time;
													}
													return "";
												} }, { field : 'priority', title : '通道优先级' },
											{ field : 'productCode', title : '供货商产品编号' },
											{ field : 'remark', title : '备注' },
											{ field : 'forbidCustomer', title : '禁止采购商' },
											{ field : 'allowCustomer', title : '允许采购商' },
									]
								], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
								enableRowContextMenu : false, toolbar : '#tb' });
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
			var providerId = $("input[name=filter_EQI_providerId]").val();
			if (providerId == undefined) {
				providerId = "";
			}

			d = $("#dlg").dialog(
					{ title : '添加配置', width : 400, height : 500,
						href : '${ctx}/boss/providerProductInfo/create?providerId=' + providerId, maximizable : true,
						modal : true, buttons : [
								{ text : '确认', handler : function() {
									$("#mainform").submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}
		//弹窗增加
		function addBatch() {
			//供应商ID
			var providerId = $("input[name=filter_EQI_providerId]").val();
			if (providerId == undefined) {
				providerId = "";
			}
			d = $("#dlg").dialog(
					{ title : '添加配置', width : 400, height : 800,
						href : '${ctx}/boss/providerProductInfo/createBatch?providerId=' + providerId,
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
		//批量更新状态
		function updateStatus(html,state) {
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
			var title = $(html).val();
			var color = (state == 0 || state == 2) ? "red" : "green";
			parent.$.messager.confirm('提示', '已选<span style="color:' + color + ';font-weight:bold;">' + rows.length
					+ '</span>条记录,您确定要<span style="color:' + color + ';font-weight:bold;">' + title + '</span>吗?', function(data) {
				if (data) {
					$.ajax({ url : "${ctx}/boss/providerProductInfo/updateStatus", type : "post", traditional : true,
						data : { "ids" : ids, "state" : state }, success : function(value) {
							dg.datagrid('reload');
						}

					});
				}
			});
		}
		//导出excel
		function exportExcel() {
			getCategoryId();
			getProviderId();
			var obj = $("#searchFrom").serializeObject();
			var params = "?";
			for ( var name in obj) {
				params += name + "=" + obj[name] + "&&";
			}
			params = params.substring(0, params.length - 2);
			parent.$.messager.show({ title : "提示", msg : "导出需要时间，请稍等片刻！", position : "bottomRight" });
			var url = "${ctx}/boss/providerProductInfo/exportExcel" + params;
			window.location.href = url;
		}
	</script>
</body>
</html>