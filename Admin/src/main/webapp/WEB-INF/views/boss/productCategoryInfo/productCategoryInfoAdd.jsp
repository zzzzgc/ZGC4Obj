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
			<td>产品名称：</td>
			<td>
			<input name="categoryName" type="text" value="" class="easyui-validatebox" onkeydown="changeCategoryName();" onchange="changeCategoryName();" required="required"/>
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
			<td> 运营商：</td>
			<td>
			 <select  name="operator">
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap" >
		 				<option value="${providerMap.value}">${providerMap.value}</option>
		 			</c:forEach>
	 			</select>
			</td>
		</tr>
		<tr>
			<td>产品规格：</td>
			<td>
			<input name="productNum" type="text" value="" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>产品单位：</td>
			<td>
			 <select id="productUnit1" name="productUnit">
			 <option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.numberMap}" var="numberMap" >
		 				<option value="${numberMap.key}">${numberMap.value}</option>
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
		<tr>
			<td>产品类型：</td>
			<td>
			<select id="productType1" name="productType">
		 			<c:forEach items="${applicationScope.sysParam.productTypeMap}" var="productTypeMap" >
		 				 <option value="${productTypeMap.key}">${productTypeMap.value}</option>
		 			</c:forEach>
	 			</select>
			</td>
		</tr>
		
		<tr>
			<td>标准价：</td>
			<td>
				<input name="standarPrice" type="text" value="" class="easyui-validatebox" required="required"/>
			</td>
		</tr>
		<tr>
			<td>上架状态：</td>
			<td>
			  <select id="status1" name="status">
			  		<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.productMap}" var="productMap" >
		 				<option value="${productMap.key}">${productMap.value}</option>
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
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap" >
		 				<option value="${isNoMap.key}">${isNoMap.value}</option>
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
	    	$($("#dlg .l-btn-text")[0]).css('display', "");
			$($("#dlg .l-btn-text")[1]).css('display', "");
	    	successTip(data,dg,d);
	    }    
	}); 
	
	//设置产品默认状态为冻结
	$("#status1 option[value='2']").attr("selected", true);
	//设置二次通天道状态为不开启
	$("#isSecondChannel1 option[value='0']").attr("selected", true);
	//设置使用区域为全国
	$("#area1 option[value='1']").attr("selected", true);
	//设置省份为全国
	$("#province1 option[value='全国']").attr("selected", true);
	//设置产品单位为M
	$("#productUnit1 option[value='1']").attr("selected", true);
	//设置产品类型为流量包
	$("#productType1 option[value='1']").attr("selected", true);
	//设置产品类型为流量包
	$("select[name=isLimit] option[value='1']").attr("selected", true);
});
function changeCategoryName(){
	var categoryName = $("input[name=categoryName]").val();
	if(categoryName.length<6){
		return ;
	}
	head = categoryName.substring(0, categoryName.length-5);
	end = categoryName.substring(categoryName.length-5, categoryName.length);
	console.debug(head+","+end);
	var productNum = /\d+/g.exec(categoryName);
	var productUnit = categoryName.match(/[A-Za-z]/g);
	$("input[name=productNum]").val(productNum);
	$.each(${applicationScope.sysParam.productProvinceJsonMap},function(key,val){
		if(head.indexOf(val)>-1){
			$("select[name=province] option").each(function(){ //遍历全部option
				if($(this).val()==val){
					$(this).attr("selected",true);
					return;
				}
		    }); 
		}
	});
	$.each(${applicationScope.sysParam.providerJsonMap},function(key,val){
		if(head.indexOf(val)>-1){
			$("select[name=operator] option").each(function(){ //遍历全部option
				if($(this).val()==val){
					$(this).attr("selected",true);
					return;
				}
		    }); 
		}
	});
	$.each(${applicationScope.sysParam.numberJsonMap},function(key,val){
			$("select[name=productType] option").each(function(){ //遍历全部option
				if($(this).text()==productUnit){
					$(this).attr("selected",true);
					return;
				}
		    }); 
	});
	$.each(${applicationScope.sysParam.areaJsonMap},function(key,val){
		if(end.indexOf(val)>-1){
			$("select[name=area] option").each(function(){ //遍历全部option
				if($(this).text()==val){
					$(this).attr("selected",true);
					return;
				}
		    }); 
		}
	});
	/* $.each(${applicationScope.sysParam.productTypeJsonMap},function(key,val){
		if(end.indexOf(val)>-1){
			$("select[name=productType] option").each(function(){ //遍历全部option
				if($(this).text()==val){
					$(this).attr("selected",true);
				return;
				}
		    }); 
		}
	}); */
}
</script>
</body>
</html>