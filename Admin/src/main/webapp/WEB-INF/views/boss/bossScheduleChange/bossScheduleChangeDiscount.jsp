<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossScheduleChange/${action}" method="post">
			<table class="formTable">
				<tr id="changeNum">
					<td>折扣数：</td>
					<td><input name="changeNum" type="text" value="" class="easyui-validatebox" /></td>
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
			showValueByKey("${bossScheduleChange.tableType}", "tableType");
			showValueByKey("${bossScheduleChange.type}", "type");
			showValueByKey("${bossScheduleChange.status}", "status");
		});

	</script>
</body>
</html>