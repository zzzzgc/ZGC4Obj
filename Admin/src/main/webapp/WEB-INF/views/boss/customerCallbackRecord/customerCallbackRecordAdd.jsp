<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/boss/customerCallbackRecord/add" method="post">
	<table  class="formTable">
		<tr>
			<td>订单id：</td>
			<td>
			<input type="hidden" name="id" value="${customerCallbackRecord.id }" />
			<input name="orderId" type="text" value="${customerCallbackRecord.orderId }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>采购商订单号：</td>
			<td>
			<input name="orderKey" type="text" value="${customerCallbackRecord.orderKey }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>回调地址：</td>
			<td>
			<input name="callbackAddress" type="text" value="${customerCallbackRecord.callbackAddress }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>状态：</td>
			<td>
			<select  name="status">
       	        	<option value="">请选择</option>
       	        	<c:forEach items="${applicationScope.sysParam.successFailMap}" var="successFailMap" >
		 				<option value="${successFailMap.key}">${successFailMap.value}</option>
		 			</c:forEach>
		 	</select>
			</td>
		</tr>
		<tr>
			<td>回调数据：</td>
			<td>
			<input name="callbackData" type="text" value="${customerCallbackRecord.callbackData }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>回调数据：</td>
			<td>
			<input id="d1" type="text" name="filter_GED_submitTime"  class="easyui-my97" startDate='%y-%M-%d 00:00:00' datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '提交到供货商起始时间'" />--
			<input name="callbackData" type="text" value="${customerCallbackRecord.callbackData }" class="easyui-validatebox" required="required"/>
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