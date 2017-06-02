n<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/flowPoolInfo/update"
			method="post">
			<table class="formTable">
				<tr>
					<td>供应商：</td>
					<td><input type="hidden" name="id" value="${flowPoolInfo.id }" />
					<select name="providerId">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerInputMap}"
								var="providerInputMap">
								<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td>
					<select name="status">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.freezeMap}"
								var="freezeMap">
								<option value="${freezeMap.key}">${freezeMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>流量池编号：</td>
					<td><input name="poolNum" type="text" value="${flowPoolInfo.poolNum}"
						class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>流量池剩余量：</td>
					<td><input name="poolRemain" type="text" value="${flowPoolInfo.poolRemain}"
						class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input name="remark" type="text" value="${flowPoolInfo.remark}"
						class="easyui-validatebox" /></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#mainform').form({
				onSubmit : function() {
					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {
					successTip(data, dg, d);
				}
			});
		});
		showValueByKey("${flowPoolInfo.providerId}","providerId");
		showValueByKey("${flowPoolInfo.status}","status");
	</script>
</body>
</html>