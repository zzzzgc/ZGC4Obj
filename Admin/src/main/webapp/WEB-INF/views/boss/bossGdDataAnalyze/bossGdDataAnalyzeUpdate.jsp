<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossGdDataAnalyze/${action}" method="post">
			<table class="formTable">
				<tr>
					<td><input type="hidden" name="id" value="${bossGdDataAnalyze.id}"/></td>
				</tr>
				<tr>
					<td>城市：</td>
					<td><input name="city" type="text" value="${bossGdDataAnalyze.city}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>订单笔数：</td>
					<td><input name="totalNum" type="text" value="${bossGdDataAnalyze.totalNum}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>订单成本：</td>
					<td><input name="totalCost" type="text" value="${bossGdDataAnalyze.totalCost}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>成功笔数：</td>
					<td><input name="successNum" type="text" value="${bossGdDataAnalyze.successNum}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>成功成本：</td>
					<td><input name="successCost" type="text" value="${bossGdDataAnalyze.successCost}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>订单笔数占比：</td>
					<td><input name="totalNumRate" type="text" value="${bossGdDataAnalyze.totalNumRate}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>成功笔数占比：</td>
					<td><input name="successNumRate" type="text" value="${bossGdDataAnalyze.successNumRate}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>订单金额占比：</td>
					<td><input name="totalCostRate" type="text" value="${bossGdDataAnalyze.totalCostRate}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>成功金额占比：</td>
					<td><input name="successCostRate" type="text" value="${bossGdDataAnalyze.successCostRate}" class="easyui-validatebox"  /></td>
				</tr>		
				
				<tr>
					<td>使用区域：</td>
					<td><select name="area">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
								<option value="${areaMap.key}">${areaMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				
				
				<tr style="display:none;">
					<td>备注：</td>
					<td><input name="remark" type="text" value="${bossGdDataAnalyze.remark}" class="easyui-validatebox" /></td>
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
			showValueByKey("${bossGdDataAnalyze.area}", "area");
		});
	</script>
</body>
</html>