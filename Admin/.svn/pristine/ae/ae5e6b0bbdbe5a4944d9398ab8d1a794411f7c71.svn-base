<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/providerInfo/add" method="post">
			<table class="formTable">
				<tr>
					<td>供应商名称：</td>
					<td><input type="hidden" name="sellsMoney" value="0" /> <input name="providerName" type="text" value=""
							class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>供应商简称：</td>
					<td><input id="supplier1" name="supplier" type="text" value="" class="easyui-validatebox" required="required"
							onblur="checkSimpleName()" /></td>
				</tr>
				<tr>
					<td>供应商联系人：</td>
					<td><input name="providerContact" type="text" value="" class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>供应商联系号码：</td>
					<td><input name="providerNumber" type="text" value="" class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>供应商地址：</td>
					<td><input name="providerAddress" type="text" value="" class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>折扣率：</td>
					<td><input name="discount" type="text" value="1.0" class="easyui-validatebox" required="required" /></td>
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
					<td>运营商：</td>
					<td><select name="operator">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
								<option value="${providerMap.value}">${providerMap.value}</option>
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
				<tr style="display:none">
					<td>警报阀值：</td>
					<td><input name="alarmBalance" type="text" value="0" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>禁止采购商：</td>
					<td><input name="forbinCustomer" type="text" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>允许采购商：</td>
					<td><input name="allowCustomer" type="text" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input id="addTime" name="addTime" type="hidden" value="" /> <input name="remark" type="text"
							value="${providerInfo.remark }" class="easyui-validatebox" /></td>
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

			var time = new Date();

			$("#addTime").val(time);

			//设置供应商默认状态为冻结
			$("#status1 option[value='0']").attr("selected", true);

			<c:if test="${applicationScope.sysParam.commonMap.isNotNeedYanzheng}">
			$("input[name=providerContact]").val("123456");
			$("input[name=providerNumber]").val("123456");
			$("input[name=providerAddress]").val("123456");
			$("input[name=discount]").val("1");
			$("input[name=alarmBalance]").val("-500");
			$("input[name=allowCustomer]").val("600001");
			showValueByKey("上海", "province");
			showValueByKey("0", "isCallback");
			showValueByKey("1", "status");
			</c:if>

		});

		//检查采购商简称
		function checkSimpleName() {
			var simpleName = $("#supplier1").val();
			$.ajax({ type : 'get', url : "${ctx}/boss/providerInfo/checkSimpleName?simpleName=" + simpleName,
				dataType : "json", success : function(data) {
					if (data.msg != "success") {
						alert(data.msg);
					}
				}, error : function(data) {
					alert("系统错误");
				} });
		}
	</script>
</body>
</html>