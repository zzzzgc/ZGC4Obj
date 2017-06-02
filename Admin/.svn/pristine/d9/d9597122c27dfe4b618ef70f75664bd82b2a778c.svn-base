<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossOvertimeOrderRecord/${action}" method="post">
			<table class="formTable">
				<tr>
					<td><input type="hidden" name="id" value="${bossOvertimeOrderRecord.id}"/></td>
				</tr>
				<tr>
					<td>：</td>
					<td><input name="orderId" type="text" value="${bossOvertimeOrderRecord.orderId}" class="easyui-validatebox"  /></td>
				</tr>		
				
				
				<tr style="display:none;">
					<td>：</td>
					<td><input name="addTime" type="text" value="${bossOvertimeOrderRecord.addTime}" class="easyui-validatebox" /></td>
				</tr>	
				
				<tr style="display:none;">
					<td>：</td>
					<td><input name="addUser" type="text" value="${bossOvertimeOrderRecord.addUser}" class="easyui-validatebox" /></td>
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
				if(data!="success"){
				$($("#dlg .l-btn-text")[0]).css('display', "");
				$($("#dlg .l-btn-text")[1]).css('display', "");
				}
			} });
			deleteMSCommon($("input[name=addTime]").val(),"addTime");
		});
	</script>
</body>
</html>