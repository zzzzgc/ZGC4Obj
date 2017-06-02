<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/alarmLinkman/add" method="post">
			<table class="formTable">
				<tr>
					<td>姓名：</td>
					<td><input name="name" type="text" value="" class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>手机：</td>
					<td><input name="phone" type="text" value="" class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>邮箱：</td>
					<td><input name="email" type="text" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>企业微信帐号：</td>
					<td><input name="weixinId" type="text" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select name="status">
							<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap">
								<option value="${freezeMap.key}">${freezeMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>角色：</td>
					<td><select name="role">
							<c:forEach items="${applicationScope.sysParam.roleMap}" var="roleMap">
								<option value="${roleMap.key}">${roleMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>类型:<input type="hidden" name="type" value="" /></td>
					<td><input type="checkbox" class="type1" onclick="getType()" />天猫 <input type="checkbox" class="type1"
							onclick="getType()" />微信 <input type="checkbox" class="type1" onclick="getType()" />微信渠道 <input type="checkbox"
							class="type1" onclick="getType()" />boss</td>
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
		function getType() {
			var type = $("input[name=type]");
			type.val("");
			var value = "";
			if (type.val())
				;
			var arrs = new Array();
			for (var i = 0; i < 4; i++) {
				arrs[i] = $(".type1")[i].checked;
			}
			for (var i = 0; i < 4; i++) {
				if (arrs[i]) {
					if (value == "") {
						value = i + 1;
					} else
						value = value + "," + (i + 1);
				}
			}
			type.val(value);
		};
	</script>
</body>
</html>