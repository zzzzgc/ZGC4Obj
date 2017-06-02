<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/boss/providerMoneyRecord/add" method="post">
	<table  class="formTable">
		<tr>
			<td>采购商id：</td>
			<td>
			<input type="hidden" name="id" value="${providerMoneyRecord.id }" />
			<input name="providerId" type="text" value="${providerMoneyRecord.providerId }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>资金余额：</td>
			<td>
			<input name="fundBalance" type="text" value="${providerMoneyRecord.fundBalance }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>收支类型：</td>
			<td>
			<select  name="costType">
       	        	<option value="">请选择</option>
	 				<c:forEach items="${applicationScope.sysParam.costTypeMap}" var="costTypeMap" >
		 				<option value="${costTypeMap.key}">${costTypeMap.value}</option>
		 			</c:forEach>
		 			</select>
			</td>
		</tr>
		<tr>
			<td>备注：</td>
			<td>
			<input name="remark" type="text" value="${providerMoneyRecord.remark }" class="easyui-validatebox" />
			</td>
		</tr>
		<tr>
			<td>发生金额：</td>
			<td>
			<input name="cost" type="text" value="${providerMoneyRecord.cost }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
	
	</table>
	</form>
</div>
<script type="text/javascript">
$(function(){
	$('#mainform').form({    
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){   
	    	successTip(data,dg,d);
	    }    
	}); 
});

</script>
</body>
</html>