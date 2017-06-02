<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossScheduleChange/${action}" method="post">
			<table class="formTable">
				<tr>
					<td><input type="hidden" name="id" value="${bossScheduleChange.id}" /></td>
				</tr>
				<tr>
					<td>对应表：</td>
					<td><select name="tableType">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.tableTypeMap}" var="tableTypeMap">
								<option value="${tableTypeMap.key}">${tableTypeMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td>表id：</td>
					<td><input name="tableId" type="text" value="${bossScheduleChange.tableId}" class="easyui-validatebox" /></td>
				</tr>

				<tr>
					<td>修改类型：</td>
					<td><select name="type">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.changeTypeMap}" var="changeTypeMap">
								<option value="${changeTypeMap.key}">${changeTypeMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td>是否生效：</td>
					<td><select name="status">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr >
					<td>执行时间：</td>
					<td><input name="changeTime" type="text" value="${bossScheduleChange.changeTime}" class="easyui-validatebox" /></td>
				</tr>

				<tr>
					<td>折扣数：</td>
					<td><input name="changeNum" type="text" value="${bossScheduleChange.changeNum}" class="easyui-validatebox" /></td>
				</tr>

				<tr>
					<td>备注：</td>
					<td><input name="remark" type="text" value="${bossScheduleChange.remark}" class="easyui-validatebox" /></td>
				</tr>

				<tr style="display:none;">
					<td>添加时间：</td>
					<td><input name="addTime" type="text" value="${bossScheduleChange.addTime}" class="easyui-validatebox" /></td>
				</tr>

				<tr style="display:none;">
					<td>添加用户：</td>
					<td><input name="addUser" type="text" value="${bossScheduleChange.addUser}" class="easyui-validatebox" /></td>
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
				if (data != "success") {
					$($("#dlg .l-btn-text")[0]).css('display', "");
					$($("#dlg .l-btn-text")[1]).css('display', "");
				}
			} });
			showValueByKey("${bossScheduleChange.tableType}", "tableType");
			showValueByKey("${bossScheduleChange.type}", "type");
			showValueByKey("${bossScheduleChange.status}", "status");
			deleteMSCommon($("input[name=changeTime]").val(), "changeTime");
			deleteMSCommon($("input[name=addTime]").val(), "addTime");
		});
	</script>
</body>
</html>