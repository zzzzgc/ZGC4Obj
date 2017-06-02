<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/providerProductInfo/update" method="post">
			<table class="formTable">
				<tr>
					<td>供应商：</td>
					<td><input type="hidden" name="id" value="${id }" /> <select id="providerId1" name="providerId" style="width:200px;" onclick='$.messager.alert("不能修改!");'>
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap">
								<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>供应商编码：</td>
					<td><input name="productCode" type="text" value="${providerProductInfo.productCode }" class="easyui-validatebox"
							required="required" /></td>
				</tr>
				<tr>
					<td>产品名称：</td>
					<td><select id="categoryId1" name="categoryId">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.productCategoryInputMap}" var="productInputMap">
								<option value="${productInputMap.key}">${productInputMap.value.categoryName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>商品名称：</td>
					<td><input name="productName" type="text" value="${providerProductInfo.productName}" class="easyui-validatebox"
							required="required" /></td>
				</tr>
				<tr>
					<td>成本价：</td>
					<td><input id="costPrice1" name="costPrice" type="text" value="${providerProductInfo.costPrice }"
							class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>省份：</td>
					<td><select id="province1" name="province">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.productProvinceMap}" var="productProvinceMap">
								<option value="${productProvinceMap.value}">${productProvinceMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>通道优先级：</td>
					<td><input name="priority" type="text" value="${providerProductInfo.priority }" class="easyui-validatebox"
							required="required" /></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select id="status1" name="status">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.productMap}" var="productMap">
								<option value="${productMap.key}">${productMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>是否发送短信：</td>
					<td><select name="isSendMsg">
							<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>二次通道：</td>
					<td><select name="isSecondChannel">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>运营商：</td>
					<td><select id="mobileOperator1" name="mobileOperator">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
								<option value="${providerMap.value}">${providerMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input name="addTime" type="text" value="${providerProductInfo.addTime }" /> 
					<input name="remark" type="text"
							value="${providerProductInfo.remark }" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>有效期：</td>
					<td>
					<input name="validityTime" type="text"
							value="${providerProductInfo.validityTime }" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>禁止采购商：</td>
					<td><input name="forbidCustomer" type="text" value="${providerProductInfo.forbidCustomer}" class="easyui-validatebox" />
					</td>
				</tr>
				<!-- 不是卡券才显示 -->
				<tr>
					<td>允许采购商：</td>
					<td><input name="allowCustomer" type="text" value="${providerProductInfo.allowCustomer}" class="easyui-validatebox" /></td>
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
			showValueByKey("${providerProductInfo.status}", "status");
			showValueByKey("${providerProductInfo.mobileOperator}", "mobileOperator");
			showValueByKey("${providerProductInfo.categoryId}", "categoryId");
			showValueByKey("${providerProductInfo.providerId}", "providerId");
			showValueByKey("${providerProductInfo.province}", "province");
			showValueByKey("${providerProductInfo.isSecondChannel}", "isSecondChannel");
			showValueByKey("${providerProductInfo.isSendMsg}", "isSendMsg");
			deleteMSCommon($("input[name=addTime]").val(),"addTime");
		});

		//计算折扣率
		function calCulDisc() {
			var standardPrice = $("#standardPrice1").val();
			var costPrice = $("#costPrice1").val();
			$("#discount").val((costPrice / standardPrice).toFixed(2));
		}

		//通过折扣率计算成本价
		function calculateCostPrice() {
			if ($("#discount").val() == 1) {
				return false;
			}

			var discount = $("#discount").val();
			var costPrice = $("#standardPrice1").val();
			$("#costPrice1").val((discount * costPrice).toFixed(3));
		}
	</script>
</body>
</html>