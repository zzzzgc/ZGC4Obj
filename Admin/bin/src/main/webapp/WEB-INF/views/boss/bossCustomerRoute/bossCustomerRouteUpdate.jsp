<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossCustomerRoute/update" method="post">
			<table class="formTable">
				<tr>
					<td><input type="hidden" name="id" value="${bossCustomerRoute.id}" /></td>
				</tr>
				<tr>
					<td>采购商：</td>
					<td><select name="customerId" style="width:200px;">
							<option value="0">全部</option>
							<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap">
								<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
							</c:forEach>
					</select></td>
				</tr>

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
					<td><select name="province" onchange="chooseCity()">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.provinceMap}" var="provinceMap">
								<option value="${provinceMap.value}">${provinceMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>城市：</td>
					<td><input type="text" name="city" value="${bossCustomerRoute.city}" /></td>
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
					<td>产品规格：</td>
					<td><select name="productNum">
							<option value="0">全部</option>
							<c:forEach items="${applicationScope.sysParam.specMap}" var="specMap">
								<option value="${specMap.value}">${specMap.value}M</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td>供货商：</td>
					<td><select name="providerId" style="width:200px;">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap">
								<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td>优先级：</td>
					<td><input name="priority" type="text" value="${bossCustomerRoute.priority}" class="easyui-validatebox" /></td>
				</tr>

				<tr>
					<td>状态：</td>
					<td><select name="status">
							<option value="">请选择</option>
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
			showValueByKey("${bossCustomerRoute.operator}", "operator");
			showValueByKey("${bossCustomerRoute.productNum}", "productNum");
			showValueByKey("${bossCustomerRoute.status}", "status");
			showValueByKey("${bossCustomerRoute.customerId}", "customerId");
			showValueByKey("${bossCustomerRoute.providerId}", "providerId");
			showValueByKey("${bossCustomerRoute.province}", "province");
			showValueByKey("${bossCustomerRoute.area}", "area");
			chooseCity();
			$("select[name=city] option:selected").val("${bossCustomerRoute.city}");
		});
	</script>
</body>
</html>