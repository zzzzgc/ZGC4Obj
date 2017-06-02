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
		<form id="mainform" action="${ctx}/boss/failReason/update"
			method="post">
			<table class="formTable">
				<tr>
					<td>运营商：</td>
					<td><input type="hidden" name="id" value="${id }" />
					<select name="operator">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerMap}"
								var="providerMap">
								<option value="${providerMap.value}">${providerMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>错误原因关键字：</td>
					<td><input name="failReasonKey" type="text" value="${failReason.failReasonKey}"
						class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input name="remark" type="text" value="${failReason.remark}"
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
		$("select[name=operator] option").each(function(){ //遍历全部option
			if($(this).val()=="${failReason.operator}"){
				$(this).attr("selected",true);
				return ;
			}
	    }); 
	</script>
</body>
</html>