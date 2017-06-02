<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="searchFrom1" action="">
			是否需要验证码: <select name="param3">
				<option value="true">否</option>
				<option value="false">是</option>
			</select>
			<div style="height: 7px;"></div>
			<input type="text" name="param1" class="easyui-validatebox" data-options="width:80,prompt: '公司地址'" onchange="trimLeft(this)" />
			<div style="height: 7px;"></div>
			<input type="text" name="param2" class="easyui-validatebox" data-options="width:80,prompt: '数字'" onchange="trimLeft(this)" />
			<div style="height: 7px;"></div>
		</form>
		<button onclick="tjiao()">提交</button>
		<br />
	</div>
	<script type="text/javascript">
		$(function() {
		});

		function tjiao() {
			var obj = $("#searchFrom1").serializeObject();
			var params = "?";
			for ( var name in obj) {
				params += name + "=" + obj[name] + "&&";
			}

			// 				params = params.substring(0, params.length - 2);
			// 				var url = "${ctx}/tmall/tmallCommon/testCloseYc" + params;
			// 				window.location.href = url;
			var url = "${ctx}/boss/common/testCloseYc";
			console.debug(url);
			$.ajax({ url : url, type : "POST", data : obj, success : function(data) {
				$.messager.alert(data);
			}, error : function() {

			} });

		}
	</script>
</body>
</html>