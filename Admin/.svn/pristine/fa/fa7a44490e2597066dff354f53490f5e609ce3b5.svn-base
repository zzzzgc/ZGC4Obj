<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/boss/productCategoryInfo/update" method="post">
	<table  class="formTable">
		<tr>
			<td>产品名称：</td>
			<td>
			<input type="hidden" name="id" value="${productCategoryInfo.id }" />
			<input name="categoryName" type="text" value="${productCategoryInfo.categoryName }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>上架状态：</td>
			<td>
			  <select id="status1" name="status">
		 			<c:forEach items="${applicationScope.sysParam.productMap}" var="productMap" >
		 				<option value="${productMap.key}">${productMap.value}</option>
		 			</c:forEach>
	 			</select>
			</td>
		</tr>
		<tr>
			<td>产品类型：</td>
			<td>
			<select id="productType1" name="productType">
			 <option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.productTypeMap}" var="productTypeMap" >
		 				 <option value="${productTypeMap.key}">${productTypeMap.value}</option>
		 			</c:forEach>
	 			</select>
			</td>
		</tr>
		<tr>
			<td>标准价：</td>
			<td>
				<input name="standarPrice" type="text" value="${productCategoryInfo.standarPrice }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>产品规格：</td>
			<td>
			<input name="productNum" type="text" value="${productCategoryInfo.productNum }" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>产品单位：</td>
			<td>
			 <select id="productUnit1" name="productUnit">
		 			<c:forEach items="${applicationScope.sysParam.numberMap}" var="numberMap" >
		 				<option value="${numberMap.key}">${numberMap.value}</option>
		 			</c:forEach>
	 			</select>
			</td>
		</tr>
		<tr>
			<td>是否限价：</td>
			<td>
			  <select name="isLimit">
			  		<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap" >
		 				<option value="${isNoMap.key}">${isNoMap.value}</option>
		 			</c:forEach>
	 			</select>
			</td>
		</tr>
		<tr>
			<td>是否开启二次通道：</td>
			<td>
			 <select id="isSecondChannel1" name="isSecondChannel">
		 			<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap" >
		 				<option value="${isNoMap.key}">${isNoMap.value}</option>
		 			</c:forEach>
	 			</select>
			</td>
		</tr>
		<tr>
			<td> 运营商：</td>
			<td>
			 <select id="operator1" name="operator">
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap" >
		 				<option value="${providerMap.value}">${providerMap.value}</option>
		 			</c:forEach>
	 			</select>
			</td>
		</tr>
		<tr>
			<td> 所属省份：</td>
			<td>
			 <select id="province1" name="province">
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.productProvinceMap}" var="productProvinceMap" >
		 				<option value="${productProvinceMap.value}">${productProvinceMap.value}</option>
		 			</c:forEach>
	 			</select>
			</td>
		</tr>
		<tr>
			<td>使用区域：</td>
			<td>
			 <select id="area1" name="area">
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap" >
		 				<option value="${areaMap.key}">${areaMap.value}</option>
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
	    	successTip(data,dg,d);
	    }    
	}); 
	
	//判断下拉框的默认值
	var status1=${productCategoryInfo.status };
	$("#status1 option").each(function(){ //遍历全部option
        var txt1 = $(this).val(); //获取option的内容
		if(txt1==status1){
			$(this).attr("selected",true);
		}
    });
	
	var status2=${productCategoryInfo.productUnit };
	$("#productUnit1 option").each(function(){ //遍历全部option
        var txt2 = $(this).val(); //获取option的内容
		if(txt2==status2){
			$(this).attr("selected",true);
		}
    });
	
	var status3=${productCategoryInfo.isSecondChannel};
	$("#isSecondChannel1 option").each(function(){ //遍历全部option
        var txt3 = $(this).val(); //获取option的内容
		if(txt3==status3){
			$(this).attr("selected",true);
		}
    });
	
	var status4="${productCategoryInfo.operator}";
	$("#operator1 option").each(function(){ //遍历全部option
        var txt4 = $(this).val(); //获取option的内容
		if(txt4==status4){
			$(this).attr("selected",true);
		}
    });
	var status5="${productCategoryInfo.province}";
	$("#province1 option").each(function(){ //遍历全部option
        var txt5 = $(this).val(); //获取option的内容
		if(txt5==status5){
			$(this).attr("selected",true);
		}
    });
	var status6=${productCategoryInfo.area};
	$("#area1 option").each(function(){ //遍历全部option
        var txt6 = $(this).val(); //获取option的内容
		if(txt6==status6){
			$(this).attr("selected",true);
		}
    });
	var status7=${productCategoryInfo.productType};
	$("#productType1 option").each(function(){ //遍历全部option
        var txt6 = $(this).val(); //获取option的内容
		if(txt6==status7){
			$(this).attr("selected",true);
		}
    });
	showValueByKey("${productCategoryInfo.isLimit}", "isLimit");
	
});

</script>
</body>
</html>