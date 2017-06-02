<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossProviderSame/${action}" method="post">
			<table class="formTable">
				<tr>
					<td><input type="hidden" name="id" value="" /></td>
				</tr>
				<tr>
					<td>供应商id：</td>
<!-- 					<td><select name="providerId" multiple="multiple" size="10" onblur="getNames(this,'providerIds')"> -->
<!-- 							<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap"> -->
<!-- 								<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option> -->
<!-- 							</c:forEach> -->
<!-- 					</select></td> -->
					<td><input name="providerIds" type="text" value="" class="easyui-validatebox" /></td>
				</tr>

				<tr>
					<td>报警金额：</td>
					<td><input name="alarmBalance" type="text" value="${bossProviderSame.alarmBalance}" class="easyui-validatebox" /></td>
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
				if (data != "success") {
					$($("#dlg .l-btn-text")[0]).css('display', "");
					$($("#dlg .l-btn-text")[1]).css('display', "");
				}
			} });
		});
	</script>
</body>
</html>