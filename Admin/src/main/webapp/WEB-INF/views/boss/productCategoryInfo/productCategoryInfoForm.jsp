<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/boss/productCategoryInfo/add" method="post">
	<table  class="formTable">
		<tr>
			<td>名称：</td>
			<td>
				<input type="hidden" name="area" value="1"/>
				<input type="hidden" name="province" value="1"/>
				<input type="hidden" name="operator" value="1"/>
				<input type="hidden" name="isSecondChannel" value="1"/>
				<input type="hidden" name="productType" value="1"/>
				<input type="hidden" name="productUnit" value="1"/>
				<input type="hidden" name="productNum" value="1"/>
				<input type="hidden" name="status" value="1"/>
				<input type="hidden" name="id" value="${productCategoryInfo.id }"/>
				<input id="categoryName" name="categoryName" type="text" value="${productCategoryInfo.categoryName }" class="easyui-validatebox" data-options="width: 180,required:'required',validType:'length[2,20]'"/>
			</td>
		</tr>
		<tr>
			<td>上级菜单：</td>
			<td><input id="pid" name="pid" value="${productCategoryInfo.pid}"/></td>
		</tr>
	</table>
	</form>
</div> 
<script type="text/javascript">
if($("#pid").prop("value")==""){
	var id = queryCondition.datagrid('getSelected')["id"];
    $("#pid").prop("value",id);
}
//父级权限
var action="${action}";
if(action=='create'){
	$('#pid').val(parentPermId);/*parentPermId 为undefined*/
	$('#state').val(0);/*设定默认值*/
	$('#type').val(0);
}else if(action=='update'){
	$('#pid').val(parentPermId);
}

//上级菜单
$('#pid').combotree({
	width:180,
	method:'GET',
    url: '${ctx}/boss/productCategoryInfo/getMenu',
    idField : 'id',
    textFiled : 'categoryName',
	parentField : 'pid',
	iconCls: 'icon',
    animate:true
});  

$('#mainform').form({    
    onSubmit: function(){    
    	var isValid = $(this).form('validate');    	
		return isValid;	// 返回false终止表单提交
    },    
    success:function(data){   
    	if(successTip(data,dg,d)){
    		queryCondition.treegrid('reload');
    	}
    }    
});   


</script>
</body>
</html>