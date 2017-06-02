<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossOperatorCloseConfig/${action}" method="post">
			<table class="formTable">
				<tr>
					<td><input type="hidden" name="id" value="${bossOperatorCloseConfig.id}" /></td>
				</tr>
				<tr>
					<td>采购商：</td>
					<td><select name="customerId">
							<option value="">请选择</option>
							<option value="0">全部</option>
							<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap">
								<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td>省份：</td>
					<td><select name="province">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.provinceMap}" var="provinceMap">
								<option value="${provinceMap.value}">${provinceMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td>城市：</td>
					<td><input name="city" type="text" value="${bossOperatorCloseConfig.city}" class="easyui-validatebox" /></td>
				</tr>

				<tr>
					<td>流量规格：</td>
					<td><select name="size">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.specMap}" var="specMap">
								<option value="${specMap.value}">${specMap.value}</option>
							</c:forEach>
					</select></td>
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

				<tr>
					<td>失败状态：</td>
					<td><select name="failStatus">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.failStatusMap}" var="failStatusMap">
								<option value="${failStatusMap.key}">${failStatusMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				
				<tr>
					<td>漫游类型：</td>
					<td><select name="area">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
								<option value="${areaMap.key}">${areaMap.value}</option>
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
			showValueByKey("${bossOperatorCloseConfig.customerId}", "customerId");
			showValueByKey("${bossOperatorCloseConfig.province}", "province");
			showValueByKey("${bossOperatorCloseConfig.size}", "size");
			showValueByKey("${bossOperatorCloseConfig.status}", "status");
			showValueByKey("${bossOperatorCloseConfig.failStatus}", "failStatus");
		});
	</script>
</body>
</html>