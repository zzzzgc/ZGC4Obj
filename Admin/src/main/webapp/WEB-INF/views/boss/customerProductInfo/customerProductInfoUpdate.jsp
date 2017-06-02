<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/boss/customerProductInfo/update" method="post">
	<table  class="formTable">
		<tr>
			<td>采购商：</td>
			<td>
			<input  type="hidden" name="id" value="${id }" />
			 <select id="customerId1" name="customerId" style="width:200px;">
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap" >
		 				<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
		 			</c:forEach>
	 		</select>
			</td>
		</tr>
		<tr>
			<td>运营商：</td>
			<td>
			<select id="mobileOperator1"  name="mobileOperator" onchange="chooseProduct()">
       	        	<option value="">请选择</option>
       	        	<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap" >
		 				<option value="${providerMap.value}">${providerMap.value}</option>
		 			</c:forEach>
		 	</select>
			</td>
		</tr>
		<tr>
			<td>产品类别信息：</td>
			<td>
			<select id="categoryId1" name="categoryId">
       	        	<option value="">请选择</option>
	 		</select>
			</td>
		</tr>
		<tr style="display: none">
			<td>产品名称：</td>
			<td>
			<input name="productName" type="text" value="${customerProductInfo.productName }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>销售价：</td>
			<td>
			<input name="sellPrice" type="text" value="${customerProductInfo.sellPrice }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>状态：</td>
			<td>
			<select id="status1"  name="status">
       	        	<option value="">请选择</option>
       	        	<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap" >
		 				<option value="${freezeMap.key}">${freezeMap.value}</option>
		 			</c:forEach>
		 			</select>
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
	    	dg.datagrid("clearSelections");
	    	successTip(data,dg,d);
	    }    
	}); 
	
	//判断状态下拉框的默认值
	var status1="${customerProductInfo.status }";
	$("#status1 option").each(function(){ //遍历全部option
	    var txt1 = $(this).val(); //获取option的内容
		if(txt1==status1){
			$(this).attr("selected",true);
		}
	});
	//判断运营商下拉框的默认值
	var status2="${customerProductInfo.mobileOperator }";
	$("#mobileOperator1 option").each(function(){ //遍历全部option
	    var txt2 = $(this).val(); //获取option的内容
		if(txt2==status2){
			$(this).attr("selected",true);
		}
	});
	//判断采购商下拉框的默认值
	var status3="${customerProductInfo.customerId }";
	$("#customerId1 option").each(function(){ //遍历全部option
	    var txt3 = $(this).val(); //获取option的内容
		if(txt3==status3){
			$(this).attr("selected",true);
		}
	});
	//判断产品类别信息下拉框的默认值
	chooseProduct();
	
});
function chooseProduct(){
	//获取运营商
	$operator=$("#mobileOperator1 option:selected").val();
	//产品
	var $category=$("#categoryId1");
	$category.empty();
	$.ajax({
		url:"${ctx}/boss/providerProductInfo/getProductCategoryInfoPro",
		type:"post",
		data:{"operator":$operator},
		dataType:"json",
		success:function(categoryList){
			if(categoryList!=null && categoryList!=undefined){
				$.each(categoryList, function(i,category){
					$category.append("<option value='" +category.id+ "'>" + category.categoryName +"</option>");
				});
				
					var status4="${customerProductInfo.categoryId}";
					console.log(status4);
					if(status4!=null){
						$("#categoryId1 option").each(function(){ //遍历全部option
						    var txt4 = $(this).val(); //获取option的内容
							if(txt4==status4){
								$(this).attr("selected",true);
							}
						});
					}
				
			}
		},
		error:function(){
			
		}
	});
	
	
}
</script>
</body>
</html>