<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossOrderPhoneAndSizeRecord/${action}" method="post">
			<table class="formTable">
				<tr>
					<td><input type="hidden" name="id" value=""/></td>
				</tr>
				<tr>
					<td>订单id：</td>
					<td><input name="orderId" type="text" value="${bossOrderPhoneAndSizeRecord.orderId}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>手机号：</td>
					<td><input name="phone" type="text" value="${bossOrderPhoneAndSizeRecord.phone}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>规格：</td>
					<td><input name="size" type="text" value="${bossOrderPhoneAndSizeRecord.size}" class="easyui-validatebox"  /></td>
				</tr>		
				
				
				<tr style="display:none;">
					<td>添加时间：</td>
					<td><input name="addTime" type="text" value="${bossOrderPhoneAndSizeRecord.addTime}" class="easyui-validatebox" /></td>
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
			deleteMSCommon($("input[name=addTime]").val(),"addTime");
		});
	</script>
</body>
</html>