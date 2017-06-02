<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/orderInfo/handleResend" method="post">
			<table class="formTable">
				<tr style="display: none;">
					<td>ids:</td>
					<td><input type="text" name="ids" value="${ids}" /></td>
				</tr>
				<tr>
					<td>供应商：</td>
					<td>
					 <select name="providerProductId">
							<option value="">请选择</option>
							<c:forEach items="${providerProductList}" var="providerProductList" >
		 				<option value="${providerProductList.providerProductInfoId}">${providerProductList.costPrice} ${providerProductList.productName}  ${providerProductList.providerName}</option>
		 			</c:forEach>
					</select>
					</td>
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