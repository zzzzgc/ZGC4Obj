<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/providerInfo/update" method="post">
			<table class="formTable">
				<tr>
					<td>供应商名称：</td>
					<td><input type="hidden" name="id" value="${providerInfo.id }" /> <input type="hidden" name="sellsMoney"
							value="${providerInfo.sellsMoney}" /> <input name="providerName" type="text" value="${providerInfo.providerName}"
							class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>供应商简称：</td>
					<td><input name="supplier" type="text" value="${providerInfo.supplier }" class="easyui-validatebox" required="required" />
					</td>
				</tr>
				<tr>
					<td>供应商联系号码：</td>
					<td><input name="providerNumber" type="text" value="${providerInfo.providerNumber}" class="easyui-validatebox"
							required="required" /></td>
				</tr>
				<tr>
					<td>供应商联系人：</td>
					<td><input name="providerContact" type="text" value="${providerInfo.providerContact}" class="easyui-validatebox"
							required="required" /></td>
				</tr>
				<tr>
					<td>供应商地址：</td>
					<td><input name="providerAddress" type="text" value="${providerInfo.providerAddress }" class="easyui-validatebox"
							required="required" /></td>
				</tr>

				<tr>
					<td>运营商：</td>
					<td><select name="operator">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
								<option value="${providerMap.value}">${providerMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>


				<tr>
					<td>省份：</td>
					<td><select id="provincee" name="province">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.provinceMap}" var="provinceMap">
								<option value="${provinceMap.value}">${provinceMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td>是否会回调：</td>
					<td><select id="isCallback1" name="isCallback">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.yesMap}" var="yesMap">
								<option value="${yesMap.key}">${yesMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>是否允许负利润：</td>
					<td><select  name="allowLossMoney">
							<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option> 
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select id="status1" name="status">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap">
								<option value="${freezeMap.key}">${freezeMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>折扣率：</td>
					<td><input name="discount" type="text" value="${providerInfo.discount }" class="easyui-validatebox" required="required" />
					</td>
				</tr>
				<tr style="display:none;">
					<td>可用余额：</td>
					<td><input name="balance" type="text" value="${providerInfo.balance }" readonly="readonly" class="easyui-validatebox"
							required="required" /></td>
				</tr>
				<tr style="display: none">
					<td>警报阀值：</td>
					<td><input name="alarmBalance" type="text" value="${providerInfo.alarmBalance }" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>禁止采购商：</td>
					<td><input name="forbinCustomer" type="text" value="${providerInfo.forbinCustomer }" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>允许采购商：</td>
					<td><input name="allowCustomer" type="text" value="${providerInfo.allowCustomer }" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input id="addTime" name="addTime" type="hidden" value="${providerInfo.addTime }" /> <input name="remark"
							type="text" value="${providerInfo.remark }" class="easyui-validatebox" /></td>
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
				dg.datagrid("clearSelections");
				successTip(data, dg, d);
			} });

			//判断下拉框的默认值
			showValueByKey("${providerInfo.province }", "province");
			showValueByKey("${providerInfo.isCallback }", "isCallback");
			showValueByKey("${providerInfo.status }", "status");
			showValueByKey("${providerInfo.operator }", "operator");
			showValueByKey("${providerInfo.allowLossMoney }", "allowLossMoney");

		});
	</script>
</body>
</html>