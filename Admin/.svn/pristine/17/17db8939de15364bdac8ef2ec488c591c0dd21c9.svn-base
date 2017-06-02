n<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/numberSegment/add"
			method="post">
			<table class="formTable">
				<tr>
					<td>电话号码：</td>
					<td><input name="number" type="text" value=""
						class="easyui-validatebox" required="required" /></td>
				</tr>

				<tr>
					<td>省份：</td>
					<td><select id="provincee" name="province"
						onchange="chooseCity()">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.provinceMap}"
								var="provinceMap">
								<option value="${provinceMap.value}">${provinceMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>城市：</td>
					<td><select id="cityy" name="city">
							<option value="">请选择</option>
					</select></td>
				</tr>
				<tr>
					<td>运营商：</td>
					<td><select name="operator">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerMap}"
								var="providerMap">
								<option value="${providerMap.value}">${providerMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#mainform').form({
				onSubmit : function() {
					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {
					successTip(data, dg, d);
				}
			});
		});
		//省市的二级联动
		function chooseCity() {
			var $province = $("#provincee option:selected").val();
			var $city = $("#cityy");
			//清空城市列表
			clearCity();

			$.ajax({
				url : "${ctx}/boss/numberSegment/getCity",
				type : "post",
				data : {
					"province" : $province
				},
				dataType : "json",
				success : function(cityList) {
					if (cityList != null && cityList != undefined) {
						$.each(cityList, function(i, city) {
							$city.append("<option value='" +city+ "'>" + city
									+ "</option>");
						});
					}
				},
				error : function() {

				}
			});
		}

		//清空城市
		function clearCity() {
			$("#cityy").empty();
			$("#cityy").append("<option value=''>请选择</option>");
			urll = '${ctx}/tmall/tmallorder/orderList';
		}
	</script>
</body>
</html>