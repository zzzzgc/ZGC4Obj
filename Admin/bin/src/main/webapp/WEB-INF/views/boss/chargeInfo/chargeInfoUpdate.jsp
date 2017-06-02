<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/boss/chargeInfo/update" method="post">
	<table  class="formTable">
		<tr>
			<td>订单号：</td>
			<td>
			<input type="hidden" name="id" value="${chargeInfo.id }" />
			<input name="orderId" type="text" value="${chargeInfo.orderId }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		
		<tr>
			<td>电话：</td>
			<td>
			<input name="phone" type="text" value="${chargeInfo.phone }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>产品编号：</td>
			<td>
			<input name="productId" type="text" value="${chargeInfo.productId }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>分类Id：</td>
			<td>
			<input name="categoryId" type="text" value="${chargeInfo.categoryId }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>充值状态：</td>
			<td>
       	          <select name="status">
		 			<c:forEach items="${applicationScope.sysParam.successFailMap}" var="successFailMap" >
		 				<option id="${successFailMap.key}" value="${successFailMap.key}">${successFailMap.value}</option>
		 			</c:forEach>
	 			</select>
			</td>
		</tr>
		<tr>
			<td>错误原因：</td>
			<td>
			<input name=failReason type="text" value="${chargeInfo.failReason }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>供应商Id：</td>
			<td>
			<input name="providerId" type="text" value="${chargeInfo.providerId }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		
		<tr>
			<td>回调时间：</td>
			<td>
			<input name="callTime" type="text" value="${chargeInfo.callTime }" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" startDate='%y-%M-%d 00:00:00' data-options="width:150,prompt: '回调时间'"/>
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
	

	//判断下拉框的默认值
	var status=${chargeInfo.status };
	$("select option").each(function(){ //遍历全部option
        var txt = $(this).val(); //获取option的内容
		if(txt==status){
			$(this).attr("selected",true);
		}
    });

});

</script>
</body>
</html>