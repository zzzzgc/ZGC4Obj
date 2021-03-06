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
				<input type="text" name="filter_INI_id" value="" class="easyui-validatebox" data-options="width:60,prompt: 'id'" />
				<!-- 		采购商查询 start	 -->
				<input type="text" name="filter_EQI_customerId" value="${customerId}" class="easyui-validatebox" data-options="width:60,prompt: '采购商id'" />
				<input type="text" name="customerName" class="easyui-validatebox" data-options="width:150,prompt: '采购商名称'" />
				<span class="toolbar-item dialog-tool-separator"></span> 
				采购商：<select name="customerId" onchange="getCustomerIdBySelect()">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap">
						<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
					</c:forEach>
				</select> 
				<input type="hidden" name="filter_INI_customerId" class="easyui-validatebox" data-options="width:100,prompt: '采购商id'" />
				<!---		采购商查询 end		 -->
				 <span class="toolbar-item dialog-tool-separator"></span> 状态：<select name="filter_EQI_status">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap">
						<option value="${freezeMap.key}">${freezeMap.value}</option>
					</c:forEach>
				</select>
				<!-- 		 产品查询	start		 -->
				<br />
				<input type="text" name="filter_EQI_categoryId" value="${categoryId}" class="easyui-validatebox" data-options="width:60,prompt: '产品id'" />
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
				<span class="toolbar-item dialog-tool-separator"></span> 是否限价：<select name="productCategoryInfo_isLimit">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
						<option value="${isNoMap.key}">${isNoMap.value}</option>
					</c:forEach>
				</select>
				<br />
				<!-- 		 产品查询	end		 -->
				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a> <span
					class="toolbar-item dialog-tool-separator"></span>
				<input id="reset" type="reset" value="重置" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				
				<shiro:hasPermission name="boss:customerProductInfo:changeDiscount"> 
					<c:if test="${customerId!=600073}">
					<input type="button" value="更改折扣" onclick="discount()" />
					</c:if>
					<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				</shiro:hasPermission> 
					
				<input type="reset" value="批量开通" onclick="updateStatus(1)" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="reset" value="批量冻结" onclick="updateStatus(0)" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="button" value="批量删除" onclick="delAll()" />
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:customerProductInfo:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:customerProductInfo:delete">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false"
					onclick="del()">删除</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:customerProductInfo:update">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"
					onclick="upd()">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:customerProductInfo:showPriceAndDis">
				<input type="hidden" id = "takeMsg" name="takeMsg" value="1" />
			</shiro:hasPermission>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true"
				data-options="disabled:false" onclick="exportExcel()">导出Excel</a>

		</div>

	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
		var dg;
		var takeMsg = $('#takeMsg').val();
		$(function() {
			var mark = true;
			//$.each( takeMsg, function(n,value){ 
				if('1' == takeMsg){
					 mark = false;
				 }    
	    	 // });

			dg = $('#dg')
					.datagrid(
							{
								method : "POST",
								url : '${ctx}/boss/customerProductInfo/customerProductInfoList',
								/*  async:false, */
								queryParams : { filter_EQI_customerId : $("input[name=filter_EQI_customerId]").val(),
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
								singleSelect : false,
								columns : [
									[
											{ checkbox : true },
											{ field : 'customerId', title : '采购商id', width : 100 },
											{ field : 'customerName', title : '采购商名称', width : 300,
												formatter : function(value, row, index) {
													var customerId = row.customerId;
													//获取采购商的列表
													var $provider = ${applicationScope.sysParam.customerInfoMap};
													var retrunVal = "";
													$.each($provider, function(key, val) {
														if (key == customerId) {
															retrunVal = val.customerName;
														}
													});
													return retrunVal;
												} },
											{ field : 'categoryId', title : '产品id', width : 200 },
											{ field : 'productName', title : '产品名', width : 200 },
											{
												field : 'categoryName',
												title : '产品类别名称',
												width : 200,
												formatter : function(value, row, index) {
													//获取供货商的列表
													var $provider = ${applicationScope.sysParam.productCategoryInfoMap};
													var retrunVal = "";
													$.each($provider, function(key, val) {
														if (key == row.categoryId) {
															retrunVal = val.categoryName;
														}
													});
													return retrunVal;
												} },
											{ field : 'mobileOperator', title : '运营商', width : 100 },
											{
												field : 'province',
												title : '省份',
												width : 100,
												formatter : function(value, row, index) {
													//获取省份列表
													var $productProvincesMap = ${applicationScope.sysParam.productCategoryInfoMap};
													var retrunVal = "";
													$.each($productProvincesMap, function(key, val) {
														if (key == row.categoryId) {
															retrunVal = val.province;
														}
													});
													return retrunVal;
												} },
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
												} },
											{
												field : 'standardPrice',
												title : '标准价',
												width : 100,
												formatter : function(value, row, index) {
													var value = row.categoryId;
													var standardPrices = eval("("
															+ '${applicationScope.sysParam.productCategoryInfoMap}'
															+ ")");
													var retrunVal = "";
													$.each(standardPrices, function(key, val) {
														if (key == value) {
															retrunVal = val.standarPrice;
														}
													});
													return parseFloat(retrunVal).toFixed(3);
												} },
											{ field : 'sellPrice', title : '销售价', width : 100,hidden:mark,
												formatter : function(value, row, index) {
													return value.toFixed(3);
												} },
											{
												field : 'discount',
												title : '折扣率',
												hidden:mark,
												width : 100,
												formatter : function(value, row, index) {
													var value = row.categoryId;
													var standardPrices = ${applicationScope.sysParam.productCategoryInfoMap};
													var standardPrice = 1;
													$.each(standardPrices, function(key, val) {
														if (key == value) {
															standardPrice = val.standarPrice;
														}
													});

													var price = row.sellPrice / standardPrice;
													return price.toFixed(4);
												} },
											{
												field : 'status',
												title : '状态',
												width : 100,
												formatter : function(value, row, index) {
													if (value == 0) {
														return '<span style="color:red;font-weight:bold;">' + "冻结"
																+ '</span>';
													} else if (value == 1) {
														return '<span style="color:green;font-weight:bold;">' + "正常"
																+ '</span>';
													}
												} }, { field : 'categoryId', title : '分类id', width : 100 },
											{ field : 'id', title : '采购商对接产品id', width : 100 }
									]
								], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
								enableRowContextMenu : false, toolbar : '#tb' });
		});

		//弹窗增加
		function add() {
			//获取弹窗前的高度
			var parentHeight = document.body.clientHeight;
			//采购商ID
			var customerId = $("input[name=filter_EQI_customerId]").val();
			if(customerId==undefined){
				customerId="";
			}
			d = $("#dlg").dialog(
					{ title : '添加配置', width : 400,height : parentHeight,
						href : '${ctx}/boss/customerProductInfo/create?customerId=' + customerId, maximizable : true,
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
			if (rows.length != 1) {
				alert("只能选择一项");
				return;
			}
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
				if (data) {
					$.ajax({ type : 'get', url : "${ctx}/boss/customerProductInfo/delete/" + rows[0].id,
						success : function(data) {
							if (data == "success") {
								dg.datagrid("clearSelections");
								cx();
								$('#dg').datagrid('clearSelections');
								parent.$.messager.show({ title : "提示", msg : "操作成功！", position : "bottomRight" });
							}
						} });
				}
			});
		}

		//弹窗修改
		function upd() {
			var rows = dg.datagrid('getSelections');
			if (rows.length != 1) {
				alert("只能选择一项");
				return;
			}
			d = $("#dlg").dialog(
					{ title : '修改用户', width : 380, height : 250,
						href : '${ctx}/boss/customerProductInfo/update/' + rows[0].id, maximizable : true,
						modal : true, buttons : [
								{ text : '修改', handler : function() {
									$('#mainform').submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
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
																							url : "${ctx}/boss/customerProductInfo/updateDiscount",
																							data : {
																								"ids" : ids,
																								"standardPrices" : standardPrices,
																								"discount" : discount },
																							success : function(data) {
																								if (data == "success") {
																									d.panel('close');
																									cx();
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

		//创建查询对象并查询
		function cx() {
			dg.datagrid('clearSelections');
			getCategoryId();
			getCustomerId();
			var obj = $("#searchFrom").serializeObject();
			dg.datagrid('reload', obj);
			dg.datagrid('clearSelections');
		}

		//格式化 关于钱的字段，例如单价，一口价等
		function formaterMoney(value) { 
			var val = parseInt(value);
			var vale = val / 100;
			val = vale.toFixed(3);
			return val;
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
			$.ajax({ url : "${ctx}/boss/customerProductInfo/updateStatus", type : "post", traditional : true,
				data : { "ids" : ids, "state" : state }, success : function(value) {
					dg.datagrid('reload');
				} });
		}
		//导出excel
		function exportExcel() {
			getCategoryId();
			getCustomerId();
			var obj = $("#searchFrom").serializeObject();
			var params = "?";
			for ( var name in obj) {
				params += name + "=" + obj[name] + "&&";
			}
			params = params.substring(0, params.length - 2);
			 parent.$.messager.show({ title : "提示",msg: "导出需要时间，请稍等片刻！", position: "bottomRight" });
			var url = "${ctx}/boss/customerProductInfo/exportExcel"+params;
			window.location.href = url;
		}
		//批量删除
		function delAll() {
			var rows = dg.datagrid('getSelections');
			if (rows.length < 1) {
				alert("请至少选择一项");
				return;
			}
			var ids = new Array();
			for (var i = 0; i < rows.length; i++) {
				ids[i] = rows[i].id;
			}
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
				if (data) {
					$.ajax({ type : 'post', traditional : true, data : { "ids" : ids },
						url : "${ctx}/boss/customerProductInfo/delAll", success : function(data) {
							successTip(data, dg);
							dg.datagrid('uncheckAll');
						} });
				}
			});
		}
	</script>
</body>
</html>