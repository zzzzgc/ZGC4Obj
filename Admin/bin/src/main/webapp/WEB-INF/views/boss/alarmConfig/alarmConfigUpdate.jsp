<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/alarmConfig/update" method="post">
			<table class="formTable">
				<tr>
					<td>供应商：</td>
					<td><input type="hidden" name="id" value="${id }" /> <select name="providerId" style="width:200px;">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap">
								<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
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
					<td><select name="province">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.provinceMap}" var="provinceMap">
								<option value="${provinceMap.value}">${provinceMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>超时耗时：</td>
					<td><input name="timeOut" type="text" value="${alarmConfig.timeOut}" class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>未处理订单数：</td>
					<td><input name="minNumOfNotHandle" type="text" style="width:50px;" value="${alarmConfig.minNumOfNotHandle}" class="easyui-validatebox" required="required"/>-- <input
							name="maxNumOfNotHandle" type="text" style="width:50px;" value="${alarmConfig.maxNumOfNotHandle}" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>级别：</td>
					<td><select name="grade">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.gradeMap}" var="gradeMap">
								<option value="${gradeMap.key}">${gradeMap.value}</option>
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

			showValueByKey("${alarmConfig.providerId}", "providerId");
			showValueByKey("${alarmConfig.operator}", "operator");
			showValueByKey("${alarmConfig.province}", "province");
			showValueByKey("${alarmConfig.grade}", "grade");
		});
	</script>
</body>
</html>