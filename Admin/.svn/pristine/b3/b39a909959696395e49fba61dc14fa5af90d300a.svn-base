<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/boss/customerMoneyRecord/add" method="post">
	<table  class="formTable">
		<tr>
			<td>采购商：</td>
			<td>
			 <select id="customerId1" name="customerId" >
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap" >
		 				<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
		 			</c:forEach>
	 		</select>
			</td>
		</tr>
		<tr>
			<td>资金余额：</td>
			<td>
			<input name="fundBalance" type="text" value="${customerMoneyRecord.fundBalance }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>收支类型：</td>
			<td>
			<select id="costType1" name="costType">
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
			<input name="remark" type="text" value="${customerMoneyRecord.remark }" class="easyui-validatebox" />
			</td>
		</tr>
		<tr>
			<td>发生金额：</td>
			<td>
			<input name="cost" type="text" value="${customerMoneyRecord.cost }" class="easyui-validatebox" required="required"/>
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
	//判断收支类型下拉框的默认值
	var status1="${customerMoneyRecord.costType }";
	$("#costType1 option").each(function(){ //遍历全部option
        var txt1 = $(this).val(); //获取option的内容
		if(txt1==status1){
			$(this).attr("selected",true);
		}
    });
	
	//判断采购商下拉框的默认值
	var status3="${customerMoneyRecord.customerId }";
	$("#customerId1 option").each(function(){ //遍历全部option
	    var txt3 = $(this).val(); //获取option的内容
		if(txt3==status3){
			$(this).attr("selected",true);
		}
	});
});

</script>
</body>
</html>