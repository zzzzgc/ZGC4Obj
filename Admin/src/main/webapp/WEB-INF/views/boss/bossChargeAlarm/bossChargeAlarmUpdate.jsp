<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossChargeAlarm/${action}" method="post">
			<table class="formTable">
				<tr>
					<td><input type="hidden" name="id" value="${bossChargeAlarm.id}" /></td>
				</tr>
				<tr>
					<td>运营商：</td>
					<td><select name="operator" onchange="getProductSelected()">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.operatorMap}" var="operatorMap">
								<option value="${operatorMap.value}">${operatorMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>省份：</td>
					<td><select name="province" onchange="getProductSelected()">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.provinceMap}" var="provinceMap">
								<option value="${provinceMap.value}">${provinceMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>使用区域：</td>
					<td><select name="area" onchange="getProductSelected()">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
								<option value="${areaMap.key}">${areaMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>产品名称：</td>
					<td><select name="productId" id="productId">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.productCategoryInputMap}" var="productCategoryInputMap">
								<option value="${productCategoryInputMap.key}">${productCategoryInputMap.value.categoryName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>充值中笔数：</td>
					<td><input name="chargeNum" type="text" value="${bossChargeAlarm.chargeNum}" class="easyui-validatebox" /></td>
				</tr>

				<tr>
					<td>耗时(分)：</td>
					<td><input name="useTime" type="text" value="${bossChargeAlarm.useTime}" class="easyui-validatebox" /></td>
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
			showValueByKey("${bossChargeAlarm.productId}", "productId");
			showValueByKey("${bossChargeAlarm.operator}", "operator");
			showValueByKey("${bossChargeAlarm.province}", "province");
			showValueByKey("${bossChargeAlarm.area}", "area");
		});
		function getProductSelected() {
			var operator = $("select[name=operator] option:selected").val();
			var province = $("select[name=" + "province" + "] option:selected").val();
			var area = $("select[name=" + "area" + "] option:selected").val();
			var $productId = $("#productId");
			if (operator != "" && province != "" && area != "") {
				$productId.empty();
				$productId.append("<option value=''>请选择</option>");
				$.each(${applicationScope.sysParam.productCategoryInfoMap}, function(key, val) {
					if (val.area == area && val.province == province && val.operator == operator) {
						$productId.append("<option value='" +val.id+ "'>" + val.categoryName + "</option>");
						return;
					}
				});
			}

		}
	</script>
</body>
</html>