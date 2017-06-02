<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />



<script>
//显示绿色或红色
function greenOrRed(value, showValue, more) {
	var color = value == 1 ? "green" : "red";
	returnVal = '<span style="color:'+color+';font-weight:bold;">' + showValue + '</span>';
	if (more) {
		if (value != 1 && value != 2) {
			returnVal = showValue;
		}
	}
	return returnVal;
}

//导出excel
function exportExcel(url) {
	var abc = url;
	$.messager.confirm('提示', '确定要导出excel？', function(data) {
		if (data) {
			var obj = $("#searchFrom").serializeObject();
			console.debug(abc);
// 			var params = "&";
			var params = (url.indexOf("?") != -1)?"&":"?";
			for ( var name in obj) {
				params += name + "=" + obj[name] + "&";
			}
			params = params.substring(0, params.length - 1);
			parent.$.messager.show({ title : "提示", msg : "导出需要时间，请稍等片刻！", position : "bottomRight" });
			var url = abc + params;
			window.location.href = url;
		}
	});
}
// <td><select id="packageName" name="packageName" multiple="multiple" size="20" onblur="getNames(this)">
// <option value=""> </option> 
// </select></td>
// <td><input name="batchs" type="text" value="" class="easyui-validatebox" /></td>
//一次性添加多个--
function getNames(name, moreName) {
	var batchs = (moreName == undefined) ? "batchs" : moreName;
	var returnVal = "";
	$(name).each(function() { //遍历全部option
		returnVal += "," + $(this).val();
	});
	if (returnVal != "") {
		returnVal = returnVal.substring(1, returnVal.length);
		$("input[name=" + batchs + "]").val(returnVal);
	}
}
</script>
