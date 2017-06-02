<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossProviderCharge/${action}" method="post">
			<table class="formTable">
				<tr>
					<td><input type="hidden" name="id" value="${bossProviderCharge.id}"/></td>
				</tr>
				<tr>
					<td>订单id：</td>
					<td><input name="orderId" type="text" value="${bossProviderCharge.orderId}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>产品id：</td>
					<td><input name="productId" type="text" value="${bossProviderCharge.productId}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>供应商id：</td>
					<td><input name="providerId" type="text" value="${bossProviderCharge.providerId}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>手机号：</td>
					<td><input name="phone" type="text" value="${bossProviderCharge.phone}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>成本：</td>
					<td><input name="cost" type="text" value="${bossProviderCharge.cost}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>产品id：</td>
					<td><input name="categoryId" type="text" value="${bossProviderCharge.categoryId}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr style="display:none;">
					<td>回调时间：</td>
					<td><input name="callTime" type="text" value="${bossProviderCharge.callTime}" class="easyui-validatebox"  /></td>
				</tr>	
				
				<tr>
					<td>状态：</td>
					<td><select name="status">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.successFailMap}" var="successFailMap">
								<option value="${successFailMap.key}">${successFailMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				
				<tr>
					<td>供货商流水号：</td>
					<td><input name="orderCode" type="text" value="${bossProviderCharge.orderCode}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>失败原因：</td>
					<td><input name="failReason" type="text" value="${bossProviderCharge.failReason}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>：</td>
					<td><input name="poolNum" type="text" value="${bossProviderCharge.poolNum}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>：</td>
					<td><input name="sizeValue" type="text" value="${bossProviderCharge.sizeValue}" class="easyui-validatebox"  /></td>
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
			deleteMSCommon($("input[name=callTime]").val(),"callTime");
			showValueByKey("${bossProviderCharge.status}", "status");
		});
	</script>
</body>
</html>