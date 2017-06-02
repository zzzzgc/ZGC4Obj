<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossProviderCharge/updateStatus" method="post">
			<table class="formTable">
				<tr style="display: none;">
					<td><input type="text" name="status" value="${status}" /></td>
				</tr>
				<tr>
					<td>供应商：</td>
					<td><select name="ids">
							<option value="">请选择</option>
							<c:forEach items="${list}" var="list">
								<option value="${list.id}">id:${list.id} 订单id:${list.orderId} 供货商名称:${list.providerName} 供货商id:${list.providerId}</option>
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
				$($("#dlg .l-btn-text")[0]).css('display', "");
				$($("#dlg .l-btn-text")[1]).css('display', "");
				successTip(data, dg, d);
			} });
		});
	</script>
</body>
</html>