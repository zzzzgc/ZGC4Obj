<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossOrderResendRecord/add" method="post">
			<table class="formTable"> 
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_NEW" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_CHAEGE" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_SUCCESS" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_FAIL" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_WAIT" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_HANDLE" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_AUDIT" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="customerId" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="orderKey" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="phone" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="categoryId" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr style="display:none;">
					<td>XXX：</td>
					<td><input name="submitTime" type="text" value="${bossOrderResendRecord.submitTime}" class="easyui-validatebox"  /></td>
				</tr>	
				<tr>
					<td>XXX：</td>
					<td><input name="status" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="failReason" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr style="display:none;">
					<td>XXX：</td>
					<td><input name="failTime" type="text" value="${bossOrderResendRecord.failTime}" class="easyui-validatebox"  /></td>
				</tr>	
				<tr style="display:none;">
					<td>XXX：</td>
					<td><input name="successTime" type="text" value="${bossOrderResendRecord.successTime}" class="easyui-validatebox"  /></td>
				</tr>	
				<tr>
					<td>XXX：</td>
					<td><input name="price" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="successId" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="callbackStatus" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="province" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="city" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="isSecondChannel" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr style="display:none;">
					<td>XXX：</td>
					<td><input name="receiveTime" type="text" value="${bossOrderResendRecord.receiveTime}" class="easyui-validatebox"  /></td>
				</tr>	
				<tr style="display:none;">
					<td>XXX：</td>
					<td><input name="callbackTime" type="text" value="${bossOrderResendRecord.callbackTime}" class="easyui-validatebox"  /></td>
				</tr>	
				<tr>
					<td>XXX：</td>
					<td><input name="beyoudOperation" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="providerKey" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="chargeCount" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="priority" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="cost" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="isBlack" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="providerCategoryId" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="handleStatus" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="weiXinPrice" type="text" value="" class="easyui-validatebox"  /></td>
				</tr>			
				<tr>
					<td>XXX：</td>
					<td><input name="finalStatus" type="text" value="" class="easyui-validatebox"  /></td>
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