<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossCustomerRoute/add" method="post">
			<table class="formTable">
				<!-- 				<tr> -->
				<!-- 					<td>采购商：</td> -->
				<!-- 					<td><select name="customerId" style="width:200px;" multiple="multiple" size="20" onblur="getNames(this,'customerIds')"> -->
				<!-- 							<option value="0">全部</option> -->
				<!-- 							<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap"> -->
				<!-- 								<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option> -->
				<!-- 							</c:forEach> -->
				<!-- 					</select></td> -->
				<!-- 					<td><input name="customerIds" type="hidden" value="" class="easyui-validatebox" /></td> -->
				<!-- 				</tr> -->
				<tr>
					<td>采购商:</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input name="customerName"
							type="text" value="" class="easyui-validatebox" data-options="width:200" /></td>
					<td><input name="customerIds" type="hidden" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>供货商：</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input name="providerName"
							type="text" value="" class="easyui-validatebox" data-options="width:200" /></td>
					<td><input name="providerId" type="hidden" value="" class="easyui-validatebox" /></td>
				</tr>
				<!-- 				<tr> -->
				<!-- 					<td>供货商：</td> -->
				<!-- 					<td><select name="providerId" style="width:200px;"> -->
				<!-- 							<option value="">请选择</option> -->
				<!-- 							<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap"> -->
				<!-- 								<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option> -->
				<!-- 							</c:forEach> -->
				<!-- 					</select></td> -->
				<!-- 				</tr> -->
				<tr>
					<td>运营商：</td>
					<td><select name="operator">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
								<option value="${providerMap.value}">${providerMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>省份：</td>
					<td><select name="province" multiple="multiple" size="10" onchange="chooseCity()" onblur="getNames(this,'provinces')">
							<option value="0">全部</option>
							<c:forEach items="${applicationScope.sysParam.provinceMap}" var="provinceMap">
								<option value="${provinceMap.value}">${provinceMap.value}</option>
							</c:forEach>
					</select></td>
					<td><input name="provinces" type="hidden" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>城市：</td>
					<td><select id="city" name="city" multiple="multiple" size="20" onblur="getNames(this,'citys')">
							<option value="0">全部</option>
					</select></td>
					<td><input name="citys" type="hidden" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>使用区域：</td>
					<td><select name="area">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
								<option value="${areaMap.key}">${areaMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>是否限价：</td>
					<td><select name="isLimit">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>产品规格：</td>
					<td><select name="productNum" multiple="multiple" size="20" onblur="getNames(this,'productNums')">
							<option value="0">全部</option>
							<c:forEach items="${applicationScope.sysParam.specMap}" var="specMap">
								<option value="${specMap.value}">${specMap.value}M</option>
							</c:forEach>
					</select></td>
					<td><input name="productNums" type="hidden" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>优先级：</td>
					<td><input name="priority" type="text" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select name="status">
							<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap">
								<option value="${freezeMap.key}">${freezeMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#mainform').form({ onSubmit : function() {
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			}, success : function(data) {
				successTip(data, dg, d);
			} });
			showValueByKey("${bossCustomerRoute.isLimit}", "1");
		});

		//清空城市
		function clearCity() {
			var $city = $("select[name=city]").val() == undefined ? $("select[name=filter_EQS_city]")
					: $("select[name=city]");
			if ($("#city").length == 1)
				$city = $("#city");
			$city.empty();
			$city.html("");
			$city.append("<option value='0'>全部</option>");
			urll = '${ctx}/boss/orderInfo/list';
		}

		var customerNameArrays = new Array();
		var indexNum = 0;
		var customerIdArrays = new Array();
		$.each(${applicationScope.sysParam.customerInfoMap}, function(key, val) {
			customerNameArrays[indexNum] = val.customerName != null ? val.customerName.replace("&mdash;", "-") : "";
			customerNameArrays[indexNum] = customerNameArrays[indexNum];
			customerIdArrays[indexNum] = val.id;
			indexNum++;
		});
		customerNameArrays[indexNum] = "全部";
		customerIdArrays[indexNum] = "0";
		var map = ${applicationScope.sysParam.customerInfoMap};
		$("input[name=customerName]").autocomplete({ minChars : 1, //
		maxHeight : 300, //
		lookup : customerNameArrays, //
		unhighlightColor : "red", onSelect : function(suggestion) {
			var index = customerNameArrays.indexOf(suggestion.value);
			$("input[name=customerIds]").val(customerIdArrays[index]);
		} });

		var providerNameArrays = new Array();
		var indexNum = 0;
		var providerIdArrays = new Array();
		$.each(${applicationScope.sysParam.providerInfoMap}, function(key, val) {
			providerNameArrays[indexNum] = val.providerName != null ? val.providerName.replace("&mdash;", "-") : "";
			providerIdArrays[indexNum] = val.id;
			indexNum++;
		});
		var map = ${applicationScope.sysParam.providerInfoMap};
		$("input[name=providerName]").autocomplete(
				{ minChars : 1, maxHeight : 300, lookup : providerNameArrays, onSelect : function(suggestion) {
					console.debug(suggestion.value);
					var index = providerNameArrays.indexOf(suggestion.value);
					console.debug(index);
					console.debug(providerIdArrays[index]);
					$("input[name=providerId]").val(providerIdArrays[index]);
				} });
	</script>
</body>
</html>