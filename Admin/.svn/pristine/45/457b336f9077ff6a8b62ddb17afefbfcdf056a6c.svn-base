<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ include file="/WEB-INF/views/common/base/common.jsp"%>
<!-- easyui皮肤 -->
<link
	href="${ctx}/static/plugins/easyui/jquery-easyui-theme/<c:out value="${cookie.themeName.value}" default="default"/>/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/plugins/easyui/jquery-easyui-theme/icon.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/plugins/easyui/icons/icon-all.css" rel="stylesheet" type="text/css" />
<!-- ztree样式 -->
<link href="${ctx}/static/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/plugins/easyui/jquery/jquery-1.11.1.min.js"></script>

<script src="${ctx}/static/plugins/easyui/jquery-easyui-1.3.6/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>

<!-- jquery扩展 -->
<script src="${ctx}/static/plugins/easyui/release/jquery.jdirk.min.js"></script>
<!-- 模糊搜索 -->
<script src="${ctx}/static/plugins/jquery/jquery.autocomplete.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/jquery/jquery.mockjax.js" type="text/javascript"></script>
<!-- easyui扩展 -->
<link href="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.progressbar.js"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.slider.js"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.linkbutton.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.validatebox.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combo.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combobox.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.menu.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.searchbox.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.panel.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.window.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.dialog.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.layout.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.tree.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.datagrid.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.treegrid.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combogrid.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combotree.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.tabs.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.theme.js" type="text/javascript"></script>

<!--<script src="${ctx}/static/plugins/easyui/release/jeasyui.extensions.all.min.js"></script>-->

<script src="${ctx}/static/plugins/easyui/icons/jeasyui.icons.all.js" type="text/javascript"></script>
<!--<script src="${ctx}/static/plugins/easyui/release/jeasyui.icons.all.min.js"></script>-->

<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.icons.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.gridselector.js" type="text/javascript"></script>

<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.toolbar.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.comboicons.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.comboselector.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.portal.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.my97.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.ty.js"></script>

<!-- ztree扩展 -->
<script src="${ctx}/static/plugins/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/static/plugins/ztree/js/jquery.ztree.exhide-3.5.min.js"></script>
<!-- common.js -->
<!-- <script src="${ctx}/WEB-INF/views/common/base/common.js" type="text/javascript"></script> -->
<link rel="stylesheet" href="${ctx }/static/plugins/easyui/common/other.css"></link>


<script>
	//全局的AJAX访问，处理AJAX清求时SESSION超时
	$.ajaxSetup({ contentType : "application/x-www-form-urlencoded;charset=utf-8",
		complete : function(XMLHttpRequest, textStatus) {
			//通过XMLHttpRequest取得响应头，sessionstatus           
			var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
			if (sessionstatus == "timeout") {
				//跳转的登录页面
				//window.location.replace('');
				//partenjump
				window.parent.location.href = '${ctx}/a/login';
			}
		} });
	//省市的二级联动
	function chooseCity() {
		var $province = $("select[name=province] option:selected").val() == undefined ? $(
				"select[name=filter_EQS_province] option:selected").val() : $("select[name=province] option:selected")
				.val();
		var $city = $("select[name=city]").val() == undefined ? $("select[name=filter_EQS_city]")
				: $("select[name=city]");
		if ($("#city").length == 1)
			$city = $("#city");
		//清空城市列表
		clearCity();
		$.ajax({ url : "${ctx}/boss/orderInfo/getCity", type : "post", data : { "province" : $province },
			dataType : "json", success : function(cityList) {
				if (cityList != null && cityList != undefined) {
					$.each(cityList, function(i, city) {
						$city.append("<option value='" +city+ "'>" + city + "</option>");
					});
				}
			}, error : function() {

			} });
	}
	//获得日期通过 2016-12-12 00:00:00
	function str2date(str){
		str = str.replace(/-/g,"/");
		var date = new Date(str);
		return date;
	}
	//清空城市
	function clearCity() {
		var $city = $("select[name=city]").val() == undefined ? $("select[name=filter_EQS_city]")
				: $("select[name=city]");
		if ($("#city").length == 1)
			$city = $("#city");
		$city.empty();
		$city.append("<option value=''>请选择</option>");
		urll = '${ctx}/boss/orderInfo/list';
	}
	//弹窗增加
	function add(url, width, height) {
		if (width == null) {
			width = 380;
		}
		if (height == null) {
			height = 500;
		}
		d = $("#dlg").dialog(
				{ title : '添加配置', width : width, height : height, href : '${ctx}/boss/' + url, maximizable : true,
					modal : true, buttons : [
							{ text : '确认', handler : function() {
								$($("#dlg .l-btn-text")[0]).css('display', "none");
								$($("#dlg .l-btn-text")[1]).css('display', "none");
								$('#mainform').submit();
							} }, { text : '取消', handler : function() {
								d.panel('close');
							} }
					] });
	}
	//删除
	function del(url) {
		var rows = $('#dg').datagrid('getSelections');
		if (rows.length == 0) {
			parent.$.messager.alert("请选中至少一行");
			return false;
		}
		var ids = new Array();
		for (var i = 0; i < rows.length; i++) {
			ids[i] = rows[i].id;
		}
		$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
			if (data) {
				$.ajax({ type : 'post', traditional : true, data : { "ids" : ids }, url : '${ctx}/boss/' + url,
					success : function(data) {
						dg.datagrid('clearSelections');
						successTip(data, dg);
					} });
			}
		});
	}
	//弹窗修改
	function upd(url, width, height) {
		var row = dg.datagrid('getSelected');
		if (rowIsNull(row))
			return;
		if (width == null) {
			width = 380;
		}
		if (height == null) {
			height = 500;
		}
		d = $("#dlg").dialog(
				{ title : '修改用户', width : width, height : height, href : '${ctx}/boss/' + url + row.id,
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
		var obj = $("#searchFrom").serializeObject();
		dg.datagrid('reload', obj);
	}

	function getErrorMsg(rows, errorMsg) {
	}
	//批量更新
	function sendPostByUrlMany(html, url, status) {
		//获取选中的所有行
		var rows = $("#dg").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert("请选中至少一行");
			return false;
		}
		var errorMsg = null;
		errorMsg = getErrorMsg(rows, errorMsg);
		if (errorMsg != null) {
			$.messager.alert(errorMsg);
			return;
		}
		var ids = new Array();
		for (var i = 0; i < rows.length; i++) {
			ids[i] = rows[i].id;
		}
		var title = $(html).val() == "" ? $(html).text() : $(html).val();
		var color = (status == 0 || status == 2) ? "red" : "green";
		parent.$.messager.confirm('提示', '已选<span style="color:' + color + ';font-weight:bold;">' + rows.length
				+ '</span>条记录,您确定要<span style="color:' + color + ';font-weight:bold;">' + title + '</span>吗?',
				function(data) {
					if (data) {
						$.ajax({ url : "${ctx}/boss/" + url, type : "post", traditional : true,
							data : { "ids" : ids, "status" : status }, success : function(value) {
								if (value == "success") {
									dg.datagrid("clearSelections");
									dg.datagrid('reload');
								} else
									$.messager.alert(value);
							} });
					}
				});
	}
	function getErrorMsg(rows, errorMsg) {
	}
	function getMap(title, rows) {
		var map = new Object();
		map.xxx = $("#xxx option:selected").val();
		map.discount = $("#discount").val();
		map.title = '您确定要<span style="color:green;font-weight:bold;">' + title + '</span>吗?';
		return;
	}
	//折扣
	function changeDiscount(url1, url2) {
		var rows = dg.datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("请至少选择一项");
			return;
		}
		var errorMsg = null;
		errorMsg = getErrorMsg(rows, errorMsg);
		if (errorMsg != null) {
			$.messager.alert(errorMsg);
			return;
		}
		d = $("#dlg").dialog(
				{
					title : '修改折扣',
					width : 380,
					height : 250,
					href : url1,
					maximizable : true,
					modal : true,
					buttons : [
							{
								text : '修改',
								id : "btnUpdate",
								handler : function() {
									var rows = dg.datagrid('getSelections');
									var map = getMap(rows);
									if (map.errorMsg != null) {
										$.messager.alert(map.errorMsg);
										return;
									}
									parent.$.messager.confirm('提示', map.title, function(data) {
										if (data) {
											$.ajax({ traditional : true, type : "get", url : url2, data : map.object,
												success : function(data) {
													if (data == "success") {
														d.panel('close');
														cx();
														$.messager.alert("操作成功!");
													} else {
														$.messager.alert(data);
													}
												} });
										}
									});
								} }, { text : '取消', handler : function() {
								d.panel('close');
							} }
					] });
	}

	function resetValue() {
		if ($("input[name=filter_GED_addTime]").val() != undefined) {
			$("input[name=filter_GED_addTime]").val("");
			$("input[name=filter_LED_addTime]").val("");
		}
		if ($("input[name=filter_GED_recordTime]").val() != undefined) {
			$("input[name=filter_GED_recordTime]").val("");
			$("input[name=filter_LED_recordTime]").val("");
		}
		if ($("input[name=filter_GED_updateTime]").val() != undefined) {
			$("input[name=filter_GED_updateTime]").val("");
			$("input[name=filter_LED_updateTime]").val("");
		}
		if ($("input[name=filter_GED_resultTime]").val() != undefined) {
			$("input[name=filter_GED_resultTime]").val("");
			$("input[name=filter_LED_resultTime]").val("");
		}
		if ($("input[name=filter_GED_orderTime]").val() != undefined) {
			$("input[name=filter_GED_orderTime]").val("");
			$("input[name=filter_LED_orderTime]").val("");
		}
		if ($("input[name=filter_GED_submitTime]").val() != undefined) {
			$("input[name=filter_GED_submitTime]").val("");
			$("input[name=filter_LED_submitTime]").val("");
		}
		if ($("input[name=filter_GED_auditTime]").val() != undefined) {
			$("input[name=filter_GED_auditTime]").val("");
			$("input[name=filter_LED_auditTime]").val("");
		}
		if ($("input[name=filter_GED_customerProductActiveTime]").val() != undefined) {
			$("input[name=filter_GED_customerProductActiveTime]").val("");
			$("input[name=filter_LED_customerProductActiveTime]").val("");
		}
		if ($("input[name=filter_GED_customerProductInActiveTime]").val() != undefined) {
			$("input[name=filter_GED_customerProductInActiveTime]").val("");
			$("input[name=filter_LED_customerProductInActiveTime]").val("");
		}
	}

	function showValueByKey(value, name) {
		$("select[name=" + name + "] option").each(function() { //遍历全部option
			if ($(this).val() == value) {
				$(this).attr("selected", true);
			}
		});
	}
	//将long转date
	function long2date(timestamp) {
		if (timestamp == null)
			return "";
		return (new Date(timestamp)).format("yyyy-MM-dd HH:mm:ss");
	}
	//将long转date
	function long2yyyyMMdd(timestamp) {
		if (timestamp == null)
			return "";
		return (new Date(timestamp)).format("yyyy-MM-dd");
	}
	function select(name) {
		return $("select[name=" + name + "] option:selected");
	}
	function inputVal(name) {
		$("input[name=" + name + "]").val();
	}
	function getCustomerIdBySelect() {
		var customerId = $("select[name=customerId] option:selected").val();
		$("input[name=filter_EQI_customerId]").val(customerId);
	}
	//input onchange时去掉左边的空格
	function trimLeft(input) {
		var s = $(input).val();
		var name = $(input).attr("name");
		if (s == null) {
			return "";
		}
		var whitespace = new String(" \t\n\r");
		var str = new String(s);
		if (whitespace.indexOf(str.charAt(0)) != -1) {
			var j = 0, i = str.length;
			while (j < i && whitespace.indexOf(str.charAt(j)) != -1) {
				j++;
			}
			str = str.substring(j, i);
		}
		var tiaojian = "input[name=" + name + "]";
		$(tiaojian).val(str);
	}
	//字符串去掉左边的空格
	function trimLeftValue(s) {
		var whitespace = new String(" \t\n\r");
		var str = new String(s);
		if (whitespace.indexOf(str.charAt(0)) != -1) {
			var j = 0, i = str.length;
			while (j < i && whitespace.indexOf(str.charAt(j)) != -1) {
				j++;
			}
			str = str.substring(j, i);
		}
		return str;
	}
	function getCustomerId() {
		var filter_INI_customerId = $("input[name=filter_INI_customerId]");
		var customerName = trimLeftValue($("input[name=customerName]").val());
		filter_INI_customerId.val("");
		if (customerName.length == 0) {
			return;
		}
		$.each(${applicationScope.sysParam.customerInfoMap}, function(key, val) {
			if (val.customerName.indexOf(customerName) != -1 || customerName.length == 0) {
				if (filter_INI_customerId.val().length == 0) {
					filter_INI_customerId.val(val.id);
				} else {
					filter_INI_customerId.val(filter_INI_customerId.val() + "," + val.id);
				}
			}
		});
		if (!(customerName.length == 0) && filter_INI_customerId.val().length == 0) {
			filter_INI_customerId.val(666666);
		}
	}
	function getProviderIdBySelect() {
		var providerId = $("select[name=providerId] option:selected").val();
		$("input[name=filter_EQI_providerId]").val(providerId);
	}
	function getProviderId() {
		var filter_INI_providerId = $("input[name=filter_INI_providerId]");
		var providerName = trimLeftValue($("input[name=providerName]").val());
		filter_INI_providerId.val("");
		if (providerName.length == 0) {
			return;
		}
		$.each(${applicationScope.sysParam.providerInfoMap}, function(key, val) {
			if (val.providerName.indexOf(providerName) != -1 || providerName.length == 0) {
				if (filter_INI_providerId.val().length == 0) {
					filter_INI_providerId.val(val.id);
				} else {
					filter_INI_providerId.val(filter_INI_providerId.val() + "," + val.id);
				}
			}
		});
		if (!(providerName.length == 0) && filter_INI_providerId.val().length == 0) {
			filter_INI_providerId.val(666666);
		}
	}
	// 	successId就是providerId 就是orderInfo表字段名字不一样
	function getSuccessIdBySelect() {
		var successId = $("select[name=successId] option:selected").val();
		$("input[name=filter_EQI_successId]").val(successId);
	}
	function getSuccessId() {
		var filter_INI_successId = $("input[name=filter_INI_successId]");
		var providerName = trimLeftValue($("input[name=providerName]").val());
		filter_INI_successId.val("");
		if (providerName.length == 0) {
			return;
		}
		$.each(${applicationScope.sysParam.providerInfoMap}, function(key, val) {
			if (val.providerName.indexOf(providerName) != -1 || providerName.length == 0) {
				if (filter_INI_successId.val().length == 0) {
					filter_INI_successId.val(val.id);
				} else {
					filter_INI_successId.val(filter_INI_successId.val() + "," + val.id);
				}
			}
		});
		if (!(providerName.length == 0) && filter_INI_successId.val().length == 0) {
			filter_INI_successId.val(666666);
		}
	}
	function getCategoryId() {
		
		var area = $("select[name=area] option:selected").val() == undefined ? "" : $(
				"select[name=area] option:selected").val();
		var isLimit = $("select[name=productCategoryInfo_isLimit] option:selected").val() == undefined ? "" : $(
				"select[name=productCategoryInfo_isLimit] option:selected").val();
		var productStandard = $("input[name=productStandard]").val() == undefined ? "" : $(
				"input[name=productStandard]").val();
		var categoryName = trimLeftValue($("input[name=categoryName]").val());
		var operatorValue = $("select[name=operator] option:selected").val() == undefined ? "" : $(
				"select[name=operator] option:selected").val();
		var provinceValue = $("select[name=province] option:selected").val() == undefined ? "" : $(
				"select[name=province] option:selected").val();
		var operator = $("select[name=operator] option:selected").html();
		var province = $("select[name=province] option:selected").html();
		var filter_INI_categoryId = $("input[name=filter_INI_categoryId]");
		filter_INI_categoryId.val("");
		if (categoryName.length == 0 && area.length == 0 && isLimit.length==0&& productStandard.length == 0 && operatorValue.length == 0
				&& provinceValue.length == 0) {
			return;
		}
		num = parseInt(productStandard.substring(0, productStandard.length - 1));
		unit = (productStandard.substring(productStandard.length - 1, productStandard.length)).toUpperCase();
		if (unit == "M") {
			unit = 1;
		} else if (unit = "G") {
			unit = 2;
		} else if (unit = "元") {
			unit = 3;
		}
		$.each(${applicationScope.sysParam.productCategoryInfoMap}, function(key, val) {
			if (((val.productNum == num && val.productUnit == unit) || productStandard.length == 0)
					&& (area == val.area || area.length == 0)
					&& (isLimit == val.isLimit || isLimit.length == 0)
					&& (val.categoryName.indexOf(categoryName) != -1 || categoryName.length == 0)
					&& (operator == val.operator || operatorValue.length == 0)
					&& (province == val.province || provinceValue.length == 0)) {
				if (filter_INI_categoryId.val().length == 0) {
					filter_INI_categoryId.val(val.id);
				} else {
					filter_INI_categoryId.val(filter_INI_categoryId.val() + "," + val.id);
				}
			}
		});
		if (!(area.length == 0 && isLimit.length == 0 &&categoryName.length == 0 && productStandard.length == 0 && operatorValue.length == 0 && provinceValue.length == 0)
				&& filter_INI_categoryId.val().length == 0) {
			filter_INI_categoryId.val(666666);
		}
	}
</script>
