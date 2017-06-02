<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body style="font-family: '微软雅黑'">
	<!-- 	<div id="top" style="height: 95%;color: green;position:absolute;top:0px;width: 100%;"> -->
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<form id="searchFrom" action="">
				<input type="hidden" name="filter_EQI_status" class="easyui-validatebox" value="6" data-options="width:150,prompt: '订单状态'" />
				<!-- 		采购商查询 start	 -->
				<input type="text" name="filter_EQI_customerId" class="easyui-validatebox" data-options="width:60,prompt: '采购商id'" />
				<input type="text" name="customerName" class="easyui-validatebox" data-options="width:150,prompt: '采购商名称'" />
				<span class="toolbar-item dialog-tool-separator"></span> 采购商：<select name="customerId" onchange="getCustomerIdBySelect()">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap">
						<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
					</c:forEach>
				</select>
				<input type="hidden" name="filter_INI_customerId" class="easyui-validatebox" data-options="width:100,prompt: '采购商id'" />
				<!---		采购商查询 end		 -->
				<span class="toolbar-item dialog-tool-separator" />
				<!-- 		供应商查询 start	 -->
				<input type="text" name="filter_EQI_successId" class="easyui-validatebox" data-options="width:60,prompt: '供应商id'" />
				<input type="text" name="providerName" class="easyui-validatebox" data-options="width:150,prompt: '供应商名称'" />
				<span class="toolbar-item dialog-tool-separator"></span> 供应商：<select name="successId" onchange="getSuccessIdBySelect()">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap">
						<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
					</c:forEach>
				</select>
				<input type="hidden" name="filter_INI_successId" class="easyui-validatebox" data-options="width:100,prompt: '供应商id'" />
				<!---		供应商查询 end		 -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_EQI_handleStatus" value="${handleStatus}">
					<option value="">手工处理状态</option>
					<c:forEach items="${applicationScope.sysParam.handleStatusMap}" var="handleStatusMap">
						<option value="${handleStatusMap.key}">${handleStatusMap.value}</option>
					</c:forEach>
				</select>
				<!---		 -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_EQI_callbackStatus">
					<option value="">回调状态</option>
					<c:forEach items="${applicationScope.sysParam.callbackStatusMap}" var="callbackStatusMap">
						<option value="${callbackStatusMap.key}">${callbackStatusMap.value}</option>
					</c:forEach>
				</select>
				<!---话费/流量 -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_EQI_bizType">
					<option value="">业务类型</option>
					<c:forEach items="${applicationScope.sysParam.productTypeMap}" var="productTypeMap">
						<option value="${productTypeMap.key}">${productTypeMap.value}</option>
					</c:forEach>
				</select>
				<!---		 -->
				<!-- 				<span class="toolbar-item dialog-tool-separator"></span> 二次通道：<select name="filter_EQI_isSecondChannel"> -->
				<!-- 					<option value="">请选择</option> -->
				<!-- 					<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap"> -->
				<!-- 						<option value="${isNoMap.key}">${isNoMap.value}</option> -->
				<!-- 					</c:forEach> -->
				<!-- 				</select> -->
				<div style="height: 7px;"></div>
				<input id="d1" type="text" name="filter_GED_receiveTime" value="${reqMap.todayStart}" class="easyui-my97" startDate='%y-%M-%d 00:00:00'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '${reqMap.todayStart}'" />
				--
				<input id="d2" type="text" name="filter_LED_receiveTime" value="${reqMap.todayEnd}" class="easyui-my97" startDate='%y-%M-%d 23:59:59'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '${reqMap.todayEnd}'" />
				<input id="d3" type="text" name="filter_GED_successTime" class="easyui-my97" startDate='%y-%M-%d 00:00:00'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '订单处理时间'" />
				--
				<input id="d4" type="text" name="filter_LED_successTime" class="easyui-my97" startDate='%y-%M-%d 23:59:59'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '订单处理时间'" />
				<!-- 		 产品查询	start		 -->
				<input type="text" name="filter_EQI_categoryId" class="easyui-validatebox" data-options="width:60,prompt: '产品id'" />
				<input type="text" name="categoryName" class="easyui-validatebox" data-options="width:150,prompt: '产品名称'" />
				<input type="hidden" name="filter_INI_categoryId" class="easyui-validatebox" data-options="width:150,prompt: '产品id'" />
				<span class="toolbar-item dialog-tool-separator"></span> 省份：<select name="province">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.productProvinceMap}" var="productProvinceMap">
						<option value="${productProvinceMap.key}">${productProvinceMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 运营商：<select name="filter_EQS_beyoudOperation">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
						<option value="${providerMap.value}">${providerMap.value}</option>
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
				<div style="height: 7px;"></div>
				<input type="text" name="filter_INI_id" class="easyui-validatebox" data-options="width:80,prompt: '订单号'" />
				<input type="text" name="filter_EQS_orderKey" class="easyui-validatebox" data-options="width:80,prompt: '采购商订单号'" />
				<input type="text" name="filter_EQS_providerKey" class="easyui-validatebox" data-options="width:80,prompt: '供应商流水号'" />
				<input type="text" name="filter_EQS_phone" class="easyui-validatebox" data-options="width:80,prompt: '手机号码'" />
				<span class="toolbar-item dialog-tool-separator"></span> 省份： <select id="province" name="filter_EQS_province"
					onchange="chooseCity1()">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.provinceMap}" var="provinceMap">
						<option value="${provinceMap.value}">${provinceMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 城市： <select id="city" name="filter_EQS_city">
					<option value="">请选择</option>
				</select>
				<div style="height: 7px;"></div>
				<shiro:hasPermission name="boss:orderInfo:search">
					<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
				</shiro:hasPermission>
				<span class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置" onclick="clearCity1();resetTime();" />
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true"
					data-options="disabled:false" onclick="exportExcel()">导出Excel</a>
				<!-- 	 -->
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:false" onclick="getOrderStatus(this)">查询广东移动订单</a>
				<!-- 	 -->
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:false" onclick="getOrderStatusAll(this)">查询所有订单</a>
				<!-- 	 -->
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:false" onclick="handle2Fail()">手工失败</a>
				<!-- 	 -->
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:false" onclick="handle2Success()">手工成功</a>
				<!-- 	 -->
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:false" onclick="handle2Back()">手工回调</a>
				<!-- 	 -->
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:false" onclick="handle2SendNotAuto()">重发(选供应商)</a>
				<!-- 	 -->
<!-- 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:false" onclick="handle2SendAuto()">重发(自动匹配)</a>
				<!-- 	 -->
				<a href="javascript:;" class="easyui-linkbutton" data-options="disabled:false" onclick="handle2SendAll();">重发(所有订单)</a>
				<!-- 	 -->
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:false" onclick="changeOrder()">改变订单号</a>
				<!-- 	 -->
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:false" onclick="selectHF()">导出话费订单(手工充值导出)</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:false" onclick="updateHFStatus()">导入话费订单(带状态订正手工订单)</a>
				<!-- 	 -->
				<div style="height: 7px;"></div>
				<!-- <input type="button" value="刷新提供商、采购商数据" onclick="flush(1)"/>
					 <input type="button" value="刷新未充值订单" onclick="flush(2)"/>
					 <input type="button" value="刷新号码段数据" onclick="flush(3)"/>
					 <input type="button" value="刷新提供商数据" onclick="flush(4)"/>
					 <input type="button" value="刷新类别数据" onclick="flush(5)"/>
					 <input type="button" value="刷新采购商数据" onclick="flush(6)"/>
					 <input type="button" value="刷新订单状态(true)" onclick="flush(7)"/>
					 <input type="button" value="刷新订单状态(false)" onclick="flush(8)"/>
					 <input type="button" value="获取还未发送订单列表大小" onclick="flush(9)"/>
					 <input type="button" value="手工处理订单" onclick="callback()"/> -->
			</form>
			<shiro:hasPermission name="boss:orderInfo:delete">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false"
					onclick="del()">删除</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:orderInfo:update">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"
					onclick="upd()">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:orderInfo:showPriceAndDis">
				<input type="hidden" id = "takeMsg" name="takeMsg" value="1" />
			</shiro:hasPermission>
		</div>

	</div>
	<table id="dg" style="width: 100%"></table>
	<div id="dlg"></div>
	<!-- 	</div> -->

	<!-- 使用 flex-box css3属性平分DIV -->
	<!-- 	<div id="bottom" style="height:5%;width:100%; position:absolute;bottom:0;"> -->
	<!-- 		<div style="display:-moz-box;display:-webkit-box;display:box;width:100%;"></div> -->
	<!-- 	</div> -->
	<script type="text/javascript">
		showValueByKey("${handleStatus}", "filter_EQI_handleStatus");
		var dg;
		var takeMsg = $('#takeMsg').val();
		$(function() {
			resetTime();
			var mark = true;
		    //$.each( takeMsg, function(n,value){ 
			    if('1' == takeMsg){
			        mark = false;
			    }
    	    // });
			dg = $('#dg').datagrid(
					{
						method : "POST",
						url : '${ctx}/boss/orderInfo/orderInfoList',
						fit : true,
						autoRowHeight : false,
						fitColumns : true,
						queryParams : $("#searchFrom").serializeObject(),
						border : false,
						striped : true,
						onLoadSuccess : function() {
							/* getBalckList(); */
						},
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
									{ field : 'beyoudOperation', title : '运营商' },
									{ field : 'orderKey', title : '采购商订单号' },
									{ field : 'id', title : '订单号' },
									{ field : 'phone', title : '充值号码' },
									{ field : 'province', title : '省份' },
									{ field : 'city', title : '城市' },
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
									{ field : 'price', title : '总价'},
									{ field : 'cost', title : '成本价',hidden:mark },
									{ field : 'price_before', title : '原售价', hidden:mark,formatter : function(value, row, index) {
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
										}
									} },
									{
										field : 'handleStatus',
										title : '手工处理状态',
										formatter : function(value, row, index) {
											//获取产品的列表
											var retrunVal = value + "";
											$.each(${applicationScope.sysParam.handleStatusMapJson},
													function(key, val) {
														if (key == value) {
															retrunVal = val;
															return;
														}
													});
											return retrunVal;
										} },
									{ field : 'receiveTime', title : '订单提交时间', formatter : function(value, row, index) {
										return jsonTimeStamp(value);
									} },
									{ field : 'order_useTime', title : '耗时' , formatter : function(value, row, index) {
										var a = ${reqMap.handleOrderUseTime};
										var useTime = ( (new Date()-new Date(row.receiveTime))/1000/60).toFixed(0);
										var hour = (useTime/60).toFixed(0);
										if(hour>useTime/60){//不要四舍五入
											  hour = hour-1;
										}
										var min = useTime%60;
										var returnVal = hour+"时"+min+"分";
										if(hour>=a){
											returnVal = greenOrRed(0,returnVal);
										}
										return returnVal;
									} },
									{ field : 'chargeCount', title : '充值次数' },
									{ field : 'failReason', title : '失败原因',formatter :function(value,row,index){
										if(value != null){
											//去掉前后空格
											return value.replace("/(^\s*)|(\s*$)/g","");
										}
										return value;} 
									},
									{ field : 'productCategory_categoryName', title : '产品名称',
										formatter : function(value, row, index) {
											//获取产品的列表
											var $productCategory = ${applicationScope.sysParam.productCategoryInfoMap};
											var retrunVal = "";
											$.each($productCategory, function(key, val) {
												if (key == row.categoryId) {
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
													} else if (val.productUnit = 2) {
														unit = "G";
													} else if (val.productUnit = 3) {
														unit = "元";
													}
												}
											});
											return size + unit + "";
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
										} }, { field : 'categoryId', title : '产品id' },
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
		function getProviderIdBySupplier() {
			var filter_EQI_providerId = $("input[name=filter_EQI_successId]");
			var providerSupplier = $("input[name=providerSupplier]").val();
			var $providerInfo = ${applicationScope.sysParam.providerInfoMap};
			filter_EQI_providerId.val("");
			var success = false;
			$.each($providerInfo, function(key, val) {
				if (val.supplier.toUpperCase() == providerSupplier.toUpperCase()) {
					filter_EQI_providerId.val(val.id);
					success = true;
					return false;
				}
			});
			if (!success && providerSupplier != "") {
				parent.$.messager.alert("不存在这样的供应商简称！");
			}
		}
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

		//删除
		function del() {
			var row = dg.datagrid('getSelected');
			if (rowIsNull(row))
				return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
				if (data) {
					$.ajax({ type : 'get', url : "${ctx}/boss/orderInfo/delete/" + row.id, success : function(data) {
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
					{ title : '修改用户', width : 380, height : 250, href : '${ctx}/boss/orderInfo/update/' + row.id,
						maximizable : true, modal : true, buttons : [
								{ text : '修改', handler : function() {
									$('#mainform').submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//创建查询对象并查询
		function cx() {
			clearRows();
			getCategoryId();
			getCustomerId();
			getSuccessId();
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
		function jumpToMainTabs(index) {
			$('#dg').datagrid('clearSelections');
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			mainTabs = parent.$("#mainTabs");
			addTab('供货商信息', 'boss/providerInfo/list?id=' + row.successId);
		}
		function addTab(title, url) {
			if (mainTabs.tabs('exists', title)) {
				mainTabs.tabs('close', title);
			}
			var content = '<iframe scrolling="auto" frameborder="0"  src="' + url
					+ '" style="width:100%;height:100%;"></iframe>';
			mainTabs.tabs('add', { title : title, content : content, closable : true });

		}
		//省市的二级联动
		function chooseCity1() {
			var $province = $("#province option:selected").val();
			var $city = $("#city");
			//清空城市列表
			clearCity1();
			$.ajax({ url : "${ctx}/boss/orderInfo/getCity", type : "post", data : { "province" : $province },
				dataType : "json", success : function(cityList) {
					if (cityList != null && cityList != undefined) {
						$.each(cityList, function(i, city) {
							$city.append("<option value='" +city+ "市'>" + city + "</option>");
						});
					}
				}, error : function() {

				} });
		}

		//清空城市
		function clearCity1() {
			$("#city").empty();
			$("#city").append("<option value=''>请选择</option>");
			urll = '${ctx}/boss/orderInfo/orderInfoList';
		}
		//重置时间
		function resetTime() {
			var date = new Date();
			date.setHours(00, 00, 00);
			var filter_GED_receiveTime = $("input[name='filter_GED_receiveTime']");
			filter_GED_receiveTime.val(getNowFormatDate(date));
			/*
			 var date1 = new Date();
			date1.setUTCDate(1);
			filter_GED_receiveTime.val(getNowFormatDate(date1));   
			 */
			var date = new Date();
			date.setHours(23, 59, 59);
			var filter_LED_receiveTime = $("input[name='filter_LED_receiveTime']");

			filter_LED_receiveTime.val(getNowFormatDate(date));
		}
		//刷新缓存
		function flush(type) {
			$.ajax({ url : "${ctx}/boss/orderInfo/flush", type : "post", data : { "type" : type },
				success : function(data) {
					successTip(data, dg);
				}, error : function() {
					successTip("刷新失败", dg);
				}

			});
		}

		//导出excel
		function exportExcel() {
			var obj = $("#searchFrom").serializeObject();
			var params = "?";
			for ( var name in obj) {
				params += name + "=" + obj[name] + "&&";
			}
			params = params.substring(0, params.length - 2);
			parent.$.messager.show({ title : "提示", msg : "导出需要时间，请稍等片刻！", position : "bottomRight" });
			var url = "${ctx}/boss/orderInfo/exportExcel" + params;
			window.location.href = url;
		}
		
		//话费订单获取
		function selectHF(){
			var obj = $("#searchFrom").serializeObject();
			var params = "?";
			for ( var name in obj) {
				params += name + "=" + obj[name] + "&&";
			}
			params = params.substring(0, params.length - 2);
			parent.$.messager.show({ title : "提示", msg : "导出需要时间，请稍等片刻！", position : "bottomRight" });
			var url = "${ctx}/boss/orderInfo/selectHFExcel" + params;
			window.location.href = url;
		}
		
		//话费订单状态批量修改
		function updateHFStatus(){
			d = $("#dlg").dialog(
					{ title : '修改订单状态', width : 380, height : 250, href : "${ctx}/boss/orderInfo/updateHFOrder", maximizable : true,
						modal : true, buttons : [
								{ text : '确认', handler : function() {
									$("#mainform").submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//手工回调订单
		function callback() {

			var select = $('#dg').datagrid('getSelected');
			if (select == null) {
				alert("请选择一行数据");
				return;
			}

			if (select.status != 6) {
				alert("请选择需要手工回调的订单进行回调");
				return;
			}

			$.ajax({ url : "${ctx}/boss/orderInfo/callback", type : "post", data : { "id" : select.id },
				success : function(data) {
					successTip(data, dg);
				}, error : function() {
					successTip("发送失败", dg);
				}

			});
		}

		//获取黑名单列表
		function getBalckList() {
			$.ajax({ url : "${ctx}/boss/orderInfo/getBlackList", type : "get", success : function(data) {
				if (data.black > 0 || data.exception > 0) {
					var msg = "今天一共有" + data.black + "个黑名单用户，" + data.exception + "个异常用户，请及时处理";
					$.ajax({ url : "${ctx}/boss/orderInfo/addOperationRecord", type : "get", success : function(data) {
						/* successTip(data,dg); */
					}, error : function() {
						successTip("发送失败", dg);
					}
					});
					successTip(msg, dg);
				}
			}, error : function() {
				successTip("发送失败", dg);
			}
			});
		}

		function getOrderStatus(value) {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			var title = '已选<span style="color:' + 'green' + ';font-weight:bold;">' + rows.length
					+ '</span>条记录,您确定要<span style="color:red;font-weight:bold">' + $(value).text() + '</span>?';
			parent.$.messager.confirm('提示', title, function(data) {
				if (data) {
					var ids = new Array();
					for (var i = 0; i < rows.length; i++) {
						ids[i] = rows[i].id;
					}

					$.ajax({ url : "${ctx}/boss/orderInfo/handleOrder", type : "post", traditional : true,
						data : { ids : ids, action : 'getOrderStatus' }, success : function(data) {
							$('#dg').datagrid('clearSelections');
							successTip(data, dg);
							var obj = $("#searchFrom").serializeObject();
							dg.datagrid('reload', obj);
						} });

				}
			});

		}
		function getOrderStatusAll(value) {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			var title = '已选<span style="color:' + 'green' + ';font-weight:bold;">' + rows.length
					+ '</span>条记录,您确定要<span style="color:red;font-weight:bold">' + $(value).text() + '</span>?';
			parent.$.messager.confirm('提示', title, function(data) {
				if (data) {
					var ids = new Array();
					for (var i = 0; i < rows.length; i++) {
						ids[i] = rows[i].id;
					}

					$.ajax({ url : "${ctx}/boss/orderInfo/handleOrder", type : "post", traditional : true,
						data : { ids : ids, action : 'getOrderStatusAll' }, success : function(data) {
							$('#dg').datagrid('clearSelections');
							successTip(data, dg);
							var obj = $("#searchFrom").serializeObject();
							dg.datagrid('reload', obj);
						} });

				}
			});

		}
		function handle2Fail() {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			var title = '已选<span style="color:' + 'green' + ';font-weight:bold;">' + rows.length
					+ '</span>条记录,您确定要<span style="color:red;font-weight:bold">手工失败</span>?';
			parent.$.messager.confirm('提示', title, function(data) {
				if (data) {
					var ids = new Array();
					for (var i = 0; i < rows.length; i++) {
						ids[i] = rows[i].id;
					}

					$.ajax({ url : "${ctx}/boss/orderInfo/handleOrder", type : "post", traditional : true,
						data : { ids : ids, action : 'toFail' }, success : function(data) {
							$('#dg').datagrid('clearSelections');
							successTip(data, dg);
							var obj = $("#searchFrom").serializeObject();
							dg.datagrid('reload', obj);
						} });

				}
			});

		}

		function handle2Success() {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			var title = '已选<span style="color:' + 'green' + ';font-weight:bold;">' + rows.length
					+ '</span>条记录,您确定要<span style="color:green;font-weight:bold">手工成功</span>?';
			parent.$.messager.confirm('提示', title, function(data) {
				if (data) {

					var ids = new Array();
					for (var i = 0; i < rows.length; i++) {
						ids[i] = rows[i].id;
					}
					$.ajax({ url : "${ctx}/boss/orderInfo/handleOrder", type : "post", traditional : true,
						data : { ids : ids, action : 'toSuccess' }, success : function(data) {
							$('#dg').datagrid('clearSelections');
							successTip(data, dg);
							var obj = $("#searchFrom").serializeObject();
							dg.datagrid('reload', obj);
						} });
				}
			});

		}

		function handle2Back() {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			var title = '已选<span style="color:' + 'green' + ';font-weight:bold;">' + rows.length
					+ '</span>条记录,您确定要<span style="color:green;font-weight:bold">手工回调</span>?';
			parent.$.messager.confirm('提示', title, function(data) {
				if (data) {
					var ids = new Array();
					for (var i = 0; i < rows.length; i++) {
						ids[i] = rows[i].id;
					}

					$.ajax({ url : "${ctx}/boss/orderInfo/handleOrder", type : "post", traditional : true,
						data : { ids : ids, action : 'toCallback' }, success : function(data) {
							$('#dg').datagrid('clearSelections');
							successTip(data, dg);
							var obj = $("#searchFrom").serializeObject();
							dg.datagrid('reload', obj);
						} });
				}
			});

		}

		function handle2SendNotAuto() {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");

			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			var ids = new Array();
			var categoryId = rows[0].categoryId;
			var errorMsg = null;
			var params = "?categoryId=" + categoryId + "&&";
			for (var i = 0; i < rows.length; i++) {
				if (i > 0 && categoryId != rows[i].categoryId) {
					errorMsg = "产品名称必须相同";
					break;
				}
				ids[i] = rows[i].id;
				params += "ids" + "=" + rows[i].id + "&&";
			}
			if (errorMsg != null) {
				$.messager.alert(errorMsg);
				return;
			}
			// 省份 运营商   规格 区域  全国移动500M全国包
			params = params.substring(0, params.length - 2);
			d = $("#dlg").dialog(
					{ title : '添加配置', width : 380, height : 250,
						href : '${ctx}/boss/orderInfo/handleResendUrl' + params, traditional : true,
						maximizable : true, modal : true, buttons : [
								{ text : '确认', handler : function() {
									$($("#dlg .l-btn-text")[0]).css('display', "none");
									$($("#dlg .l-btn-text")[1]).css('display', "none");
									$("#mainform").submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		function handle2SendAuto() {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");

			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			var color = "green";
			var title = '已选<span style="color:' + 'green' + ';font-weight:bold;">' + rows.length
					+ '</span>条记录,您确定要<span style="color:green;font-weight:bold">手工重发</span>?';
			parent.$.messager.confirm('提示', title, function(data) {
				if (data) {
					var ids = new Array();
					for (var i = 0; i < rows.length; i++) {
						ids[i] = rows[i].id;
					}
					$.ajax({ url : "${ctx}/boss/orderInfo/handleOrder", type : "post", traditional : true,
						data : { ids : ids, action : 'toResend' }, success : function(data) {
							$('#dg').datagrid('clearSelections');
							successTip(data, dg);
							var obj = $("#searchFrom").serializeObject();
							dg.datagrid('reload', obj);
						} });
				}
			});

		}
		function handle2SendAll() {
			var title = '您确定要<span style="color:red;font-weight:bold">手工重发所有订单</span>?',
			$EQI_status = $('input[name=filter_EQI_status]').val(),
			$EQI_status = $EQI_status ? $EQI_status:'none',
			$EQI_customerId = $('input[name=filter_EQI_customerId]').val(),
			$EQI_customerId = $EQI_customerId ? $EQI_customerId:'none',
			$INI_customerId = $('input[name=filter_INI_customerId]').val(),
			$INI_customerId = $INI_customerId ? $INI_customerId:'none',
			$EQI_successId = $('input[name=filter_EQI_successId]').val(),
			$EQI_successId = $EQI_successId ? $EQI_successId:'none',
			$INI_successId = $('input[name=filter_INI_successId]').val(),
			$INI_successId = $INI_successId ? $INI_successId:'none',
			$EQI_handleStatus = $('select[name=filter_EQI_handleStatus]').val(),
			$EQI_handleStatus = $EQI_handleStatus ? $EQI_handleStatus:'none',
			$EQI_callbackStatus = $('select[name=filter_EQI_callbackStatus]').val(),
			$EQI_callbackStatus = $EQI_callbackStatus ? $EQI_callbackStatus:'none',
			$GED_receiveTime = $('input[name=filter_GED_receiveTime]').val(),
			$GED_receiveTime = $GED_receiveTime ? $GED_receiveTime:'none',
			$LED_receiveTime = $('input[name=filter_LED_receiveTime]').val(),
			$LED_receiveTime = $LED_receiveTime ? $LED_receiveTime:'none',
			$GED_successTime = $('input[name=filter_GED_successTime]').val(),
			$GED_successTime = $GED_successTime ? $GED_successTime:'none',
			$LED_successTime = $('input[name=filter_LED_successTime]').val(),
			$LED_successTime = $LED_successTime ? $LED_successTime:'none',
			$EQI_categoryId = $('input[name=filter_EQI_categoryId]').val(),
			$EQI_categoryId = $EQI_categoryId ? $EQI_categoryId:'none',
			$INI_categoryId = $('input[name=filter_INI_categoryId]').val(),
			$INI_categoryId = $INI_categoryId ? $INI_categoryId:'none',
			$EQS_beyoudOperation = $('select[name=filter_EQS_beyoudOperation]').val(),
			$EQS_beyoudOperation = $EQS_beyoudOperation ? $EQS_beyoudOperation:'none',
			$INI_id = $('input[name=filter_INI_id]').val(),
			$INI_id = $INI_id ? $INI_id:'none',
			$EQS_orderKey = $('input[name=filter_EQS_orderKey]').val(),
			$EQS_orderKey = $EQS_orderKey ? $EQS_orderKey:'none',
			$EQS_providerKey = $('input[name=filter_EQS_providerKey]').val(),
			$EQS_providerKey = $EQS_providerKey ? $EQS_providerKey:'none',
			$EQS_phone = $('input[name=filter_EQS_phone]').val(),
			$EQS_phone = $EQS_phone ? $EQS_phone:'none',
			$EQS_province = $('select[name=filter_EQS_province]').val(),
			$EQS_province = $EQS_province ? $EQS_province:'none',
			$EQS_city = $('select[name=filter_EQS_city]').val(),
			$EQS_city = $EQS_city ? $EQS_city:'none',
			$filter = 'EQI_status::'+$EQI_status+',EQI_customerId::'+$EQI_customerId+',INI_customerId::'+$INI_customerId+',EQI_successId::'+$EQI_successId+',INI_successId::'+$INI_successId+',EQI_handleStatus::'+$EQI_handleStatus
			+',EQI_callbackStatus::'+$EQI_callbackStatus+',GED_receiveTime::'+$GED_receiveTime+',LED_receiveTime::'+$LED_receiveTime+',GED_successTime::'+$GED_successTime+',LED_successTime::'+$LED_successTime+',EQI_categoryId::'+$EQI_categoryId
			+',INI_categoryId::'+$INI_categoryId+',EQS_beyoudOperation::'+$EQS_beyoudOperation+',INI_id::'+$INI_id+',EQS_orderKey::'+$EQS_orderKey+',EQS_providerKey::'+$EQS_providerKey+',EQS_phone::'+$EQS_phone+',EQS_province::'+$EQS_province
			+',EQS_city::'+$EQS_city;
			parent.$.messager.confirm('警告', title, function(data) {
				if (data) {
					$.ajax({ url : "${ctx}/boss/orderInfo/handleOrder", type : "post", traditional : true,cache:false,
						data : { ids : [00], action : 'reSendAll', filter:$filter }, success : function(data) {
							$('#dg').datagrid('clearSelections');
							successTip(data, dg);
							dg.datagrid('reload', obj);
						} });
				}
			});

		}

		function changeOrder() {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");

			if (rows.length != 1) {
				alert("请选择一行");
				return false;
			}
			var ids = new Array();
			for (var i = 0; i < rows.length; i++) {
				ids[i] = rows[i].id;
			}
			$.ajax({ url : "${ctx}/boss/orderInfo/changeOrder", type : "post", traditional : true,
				data : { ids : ids }, success : function(data) {
					
					$('#dg').datagrid('clearSelections');
					successTip(data, dg);
				} });

		}
	</script>
</body>
</html>