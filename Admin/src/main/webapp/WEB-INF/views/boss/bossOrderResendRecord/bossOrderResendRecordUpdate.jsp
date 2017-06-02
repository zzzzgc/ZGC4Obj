<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossOrderResendRecord/update" method="post">
			<table class="formTable">
				<tr>
					<td><input type="hidden" name="id" value="${bossOrderResendRecord.id}"/></td>
				</tr>
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_NEW" type="text" value="${bossOrderResendRecord.STATUS_NEW}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_CHAEGE" type="text" value="${bossOrderResendRecord.STATUS_CHAEGE}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_SUCCESS" type="text" value="${bossOrderResendRecord.STATUS_SUCCESS}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_FAIL" type="text" value="${bossOrderResendRecord.STATUS_FAIL}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_WAIT" type="text" value="${bossOrderResendRecord.STATUS_WAIT}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_HANDLE" type="text" value="${bossOrderResendRecord.STATUS_HANDLE}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="STATUS_AUDIT" type="text" value="${bossOrderResendRecord.STATUS_AUDIT}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="customerId" type="text" value="${bossOrderResendRecord.customerId}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="orderKey" type="text" value="${bossOrderResendRecord.orderKey}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="phone" type="text" value="${bossOrderResendRecord.phone}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="categoryId" type="text" value="${bossOrderResendRecord.categoryId}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr style="display:none;">
					<td>XXX：</td>
					<td><input name="submitTime" type="text" value="${bossOrderResendRecord.submitTime}" class="easyui-validatebox"  /></td>
				</tr>	
				
				<tr>
					<td>XXX：</td>
					<td><input name="status" type="text" value="${bossOrderResendRecord.status}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="failReason" type="text" value="${bossOrderResendRecord.failReason}" class="easyui-validatebox"  /></td>
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
					<td><input name="price" type="text" value="${bossOrderResendRecord.price}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="successId" type="text" value="${bossOrderResendRecord.successId}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="callbackStatus" type="text" value="${bossOrderResendRecord.callbackStatus}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="province" type="text" value="${bossOrderResendRecord.province}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="city" type="text" value="${bossOrderResendRecord.city}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="isSecondChannel" type="text" value="${bossOrderResendRecord.isSecondChannel}" class="easyui-validatebox"  /></td>
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
					<td><input name="beyoudOperation" type="text" value="${bossOrderResendRecord.beyoudOperation}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="providerKey" type="text" value="${bossOrderResendRecord.providerKey}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="chargeCount" type="text" value="${bossOrderResendRecord.chargeCount}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="priority" type="text" value="${bossOrderResendRecord.priority}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="cost" type="text" value="${bossOrderResendRecord.cost}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="isBlack" type="text" value="${bossOrderResendRecord.isBlack}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="providerCategoryId" type="text" value="${bossOrderResendRecord.providerCategoryId}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="handleStatus" type="text" value="${bossOrderResendRecord.handleStatus}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="weiXinPrice" type="text" value="${bossOrderResendRecord.weiXinPrice}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>XXX：</td>
					<td><input name="finalStatus" type="text" value="${bossOrderResendRecord.finalStatus}" class="easyui-validatebox"  /></td>
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
			deleteMS($("input[name=submitTime]").val(),"submitTime");
			deleteMS($("input[name=failTime]").val(),"failTime");
			deleteMS($("input[name=successTime]").val(),"successTime");
			deleteMS($("input[name=receiveTime]").val(),"receiveTime");
			deleteMS($("input[name=callbackTime]").val(),"callbackTime");
		});
	</script>
</body>
</html>