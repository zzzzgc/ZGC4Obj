<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/customerProductInfo/add" method="post">
			<table class="formTable">
				<tr>
					<td>采购商：</td>
					<td><select id="customerId2" name="customerId">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap">
								<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>运营商：</td>
					<td><input type="hidden" name="id" value="${customerProductInfo.id }" /> <select id="mobileOperator1"
						name="mobileOperator">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
								<option value="${providerMap.value}">${providerMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>省份：</td>
					<td><select id="province1" name="province">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.productProvinceMap}" var="productProvinceMap">
								<option value="${productProvinceMap.value}">${productProvinceMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>使用区域：</td>
					<td><select id="useArea1" name="useArea" onchange="chooseProduct()">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
								<option value="${areaMap.value}">${areaMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>产品类别信息：</td>
					<td><select multiple="multiple" id="categoryId1" name="categoryIds" size="25" onchange="syscToProductName();syscToSellPrice();">
							<option value="">请选择</option>
					</select></td>
				</tr>

				<!-- <tr>
			<td>产品名称：</td>
			<td>
			<input id="productName1" name="productName" type="text" value="" class="easyui-validatebox" required="required" />
			</td>
		</tr>
		<tr>
			<td>销售价：</td>
			<td>
			<input id="sellPrice1"  name="sellPrice" type="text" value="" class="easyui-validatebox" required="required"/>
			</td>
		</tr> -->
				<tr>
					<td>状态：</td>
					<td><select id="statusAdd1" name="status">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap">
								<option value="${freezeMap.key}">${freezeMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>


			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {

			$('#mainform').form({ onSubmit : function() {
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			}, success : function(data) {
				successTip(data, dg, d);
			} });
			
			showValueByKey("${customerId}", "customerId");
		});
		//同步到productName
		function syscToProductName() {
			$categoryText = $("#categoryId1 option:selected").text();
			$("#productName1").attr("value", $categoryText);
		}
		//同步到sellPrice
		function syscToSellPrice() {
			$categoryId = $("#categoryId1 option:selected").val();
			var standardPricess = eval("(" + '${applicationScope.sysParam.productCategoryInfoMap}' + ")");
			$.each(standardPricess, function(key, val) {
				if (key == $categoryId) {
					$("#sellPrice1").val(val.standarPrice);
				}
			});
		}
		
		function chooseProduct() {
			//获取采购商
			$customerId = "";
			if ($("#customerId1").val() == null || $("#customerId1").val() == undefined) {
				$customerId = $("#customerId2 option:selected").val();
			} else {
				$customerId = $("#customerId1").val();
			}

			//获取运营商
			$operator = $("#mobileOperator1 option:selected").val();

			//产品
			var $category = $("#categoryId1");

			//获取省份
			$province = $("#province1 option:selected").val();

			//获取使用区域
			$useArea = $("#useArea1 option:selected").val();

			$category.empty();

			if ($operator == "") {
				alert("请选择运营商");
				return;
			} else if ($province == "") {
				alert("请选择省份");
				return;
			} else if ($useArea == "") {
				alert("请选择使用区域");
				return;
			}
			$.ajax({
				url : "${ctx}/boss/providerProductInfo/getProductCategoryInfo",
				type : "post",
				data : { "operator" : $operator, "province" : $province, "useArea" : $useArea, "name" : "customer",
					"id" : $customerId },
				dataType : "json",
				success : function(categoryList) {
					if (categoryList != null && categoryList != undefined) {
						$.each(categoryList,
								function(i, category) {
									$category.append("<option value='" +category.id+ "'>" + category.categoryName
											+ "</option>");
								});
						syscToProductName();
						syscToSellPrice();
					}
				}, error : function() {

				} });
		}

		//判断状态下拉框的默认值
		var statusAdd1 = 0;
		$("#statusAdd1 option").each(function() { //遍历全部option
			var txt1 = $(this).val(); //获取option的内容
			if (txt1 == statusAdd1) {
				$(this).attr("selected", true);
			}
		});

		function check() {
			//获取运营商
			$operator = $("#mobileOperator1 option:selected").val();

			//获取省份
			$province = $("#province1 option:selected").val();

			//获取使用区域
			$useArea = $("#useArea1 option:selected").val();

			if ($operator == null) {
				alert("请选择运营商");
				return;
			} else if ($province == null) {
				alert("请选择省份");
				return;
			} else if ($useArea == null) {
				alert("请选择使用区域");
				return;
			}
		}
	</script>
</body>
</html>