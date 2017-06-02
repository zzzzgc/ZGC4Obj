<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/boss/balanceRecord/${action}" method="post">
	<table  class="formTable">
		<tr>
			<td>审核id：</td>
			<td>
				<input type="text" name="id" value="${balance.id }"  readonly="readonly"/>
				<input type="hidden" name="auditUser" value="${userName }"  readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td>采购商：</td>
			<td>
				<input id="customerName" name="customerName" value="${balance.customerId}" data-options="width: 180;"  readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td>注资类型：</td>
			<td>
				<input id="balanceType" name="type" value="${balance.type}" readonly="readonly" />
			</td>
		</tr>
		<tr>
			<td>注资金额：</td>
			<td>
				<input id="type" name="balance" value="${balance.balance}" data-options="width: 180;"  readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td>注资凭证：</td>
			<td><img style="width:400px;"src="${ctx}/${balance.tickUrl}"/></td>
		</tr>
		<tr>
			<td>是否通过：</td>
			<td>
				<input type="radio" name="status" value="1" class="easyui-validatebox" data-options="validType:'requireRadio[\'input[name=status]\', \'通过  或者 不通过 \']'"><span id="pass">通过</span> <input type="radio" name="status" value="2"><span id="noPass">不通过</span>
			</td>
		</tr>
		<tr>
			<td>备注：</td>
			<td><textarea rows="3" cols="41" name="remark">${balance.remark}</textarea></td>
		</tr>
	</table>
	</form>
</div> 
<script type="text/javascript">


$(function(){
	var val = $("#customerName").val();
	 $("#customerName").val(getCustomerName(val));
		$('#balanceType').combobox({   
			url:'${ctx}/static/system/balance/balanceType.json',   
		    valueField:'id',   
		    textField:'text',
		    disabled:true
		});
});
$("#pass").click(function(){
	$("input[name='status']").eq(0).prop('checked', true);
	$("input[name='status']").eq(1).prop('checked', false);
});
$("#noPass").click(function(){
	$("input[name='status']").eq(0).prop('checked', false);;
	$("input[name='status']").eq(1).prop('checked', true);
});



$('#mainform').form({    
    onSubmit: function(){    
    	var isValid = $(this).form('validate');   
    	console.log(isValid);
		return isValid;	// 返回false终止表单提交
    },    
   success:function(data){   
    	if(successTip(data,dg,d))
    		dg.datagrid('reload');
    }
});   

$.extend($.fn.validatebox.defaults.rules, {
    requireRadio: {  
        validator: function(value, param){  
            var input = $(param[0]);
            input.off('.requireRadio').on('click.requireRadio',function(){
                $(this).focus();
            });
            return $(param[0] + ':checked').val() != undefined;
        },  
        message: '请选择 {1}.'  
    }  
});

function getCustomerName(value){
	var customerName=${applicationScope.sysParam.customerInfoMap};
	var retrunVal="";
	$.each(customerName,function(key,val){
		if(key==value){
			retrunVal= val.customerName; 
		}
	});
	return retrunVal;
}

</script>
</body>
</html>