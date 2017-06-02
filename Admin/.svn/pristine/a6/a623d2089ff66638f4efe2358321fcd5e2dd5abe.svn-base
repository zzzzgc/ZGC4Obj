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
		<form id="mainform" action="${ctx}/boss/bossConfig/add" method="post">
			<table class="formTable">
				<tr>
					<td>配置名称：</td>
					<td><input name="name" type="text" value=""
						class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>配置内容：</td>
					<td><input name="content" type="text" value=""
						class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>配置描述：</td>
					<td><input name="description" type="text" value=""
						class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input name="remark" type="text" value=""
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
	</script>
</body>
</html>