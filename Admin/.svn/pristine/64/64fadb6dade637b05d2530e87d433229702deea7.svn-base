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
			<input type="hidden" name="id" value="${id }" />
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
			<select id="status1" name="status">
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
	
	//判断状态下拉框的默认值
	var status1="${customerCallbackRecord.status }";
	$("#status1 option").each(function(){ //遍历全部option
        var txt1 = $(this).val(); //获取option的内容
		if(txt1==status1){
			$(this).attr("selected",true);
		}
    });
});

</script>
</body>
</html>