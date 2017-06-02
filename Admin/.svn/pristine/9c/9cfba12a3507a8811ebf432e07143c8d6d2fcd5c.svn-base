<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossInvoiceAudit/add" method="post">
			<table class="formTable"> 
				<tr>
					<td>订单id：</td>
					<td><input name="orderId" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>审核状态：</td>
					<td><select name="auditStatus">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.auditMap}" var="auditMap">
								<option value="${auditMap.key}">${auditMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr style="display:none;">
					<td>审核时间：</td>
					<td><input name="auditTime" type="text" value="${bossInvoiceAudit.auditTime}" class="easyui-validatebox"  /></td>
				</tr>	
				<tr>
					<td>审核人：</td>
					<td><input name="auditUser" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr style="display:none;">
					<td>备注：</td>
					<td><input name="remark" type="text" value="" class="easyui-validatebox"  /></td>
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