n<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/flowPoolInfo/add" method="post">
			<table class="formTable">
				<tr>
					<td>供应商：</td>
					<td><select name="providerId" style="width:150px">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap">
								<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>流量池编号：</td>
					<td><input name="poolNum" type="text" value="" class="easyui-validatebox" /></td>
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
				<!-- 	private int totalNum;//总量 -->
				<!-- 	private int reserveNum;//预留量 -->
				<!-- 	private int alarmNum;//预警量 -->
				<tr>
					<td>总量：</td>
					<td><input name="totalNum" type="text" value="" class="easyui-validatebox" required="required"/></td>
				</tr>
				<tr>
					<td>预留量 ：</td>
					<td><input name="reserveNum" type="text" value="" class="easyui-validatebox" required="required"/></td>
				</tr>
				<tr>
					<td>预警量：</td>
					<td><input name="poolRemain" type="text" value="" class="easyui-validatebox" required="required"/></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input name="remark" type="text" value="" class="easyui-validatebox" /></td>
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
		});
	</script>
</body>
</html>