<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/providerProductInfo/add" method="post">
			<table class="formTable">
				<tr>
					<td>供应商：</td>
					<td><select id="providerId1" name="providerId" style="width:200px;" style="width:200px;">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap">
								<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>是否发送短信：</td>
					<td><select name="isSendMsg">
							<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>运营商：</td>
					<td><select id="mobileOperator1"
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
					<td><select id="categoryId1" name="categoryId" onclick="check()" onchange="getProductName()">
							<option value="">请选择</option>
					</select></td>
				</tr>
				<tr>
					<td>商品名称：</td>
					<td><input id="productName1" name="productName" type="text" value="" class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>产品编码：</td>
					<td><input name="productCode" type="text" value="" class="easyui-validatebox" required="required"
							onclick="getStandarPrice()" /> <input id="standardPrice1" name="standardPrice" type="hidden" value="0"
							class="easyui-validatebox" required="required" /> <input id="discount" type="hidden" value="1" class="easyui-validatebox" />
						<input id="costPrice1" name="costPrice" type="hidden" class="easyui-validatebox" required="required" /></td>
				</tr>

				<tr>
					<td>通道优先级：</td>
					<td><input name="priority" type="text" value="1" class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select id="status1" name="status">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.productMap}" var="productMap">
								<option value="${productMap.key}">${productMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>二次通道：</td>
					<td><select name="isSecondChannel">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input id="addTime" name="addTime" type="hidden" value="" /> <input name="remark" type="text" value=""
							class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>有效期：</td>
					<td><input name="validityTime" type="text" value="当月有效，月底清零"
							class="easyui-validatebox" /></td>
				</tr>

				<tr>
					<td>禁止采购商：</td>
					<td><input name="forbidCustomer" type="text" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>允许采购商：</td>
					<td><input name="allowCustomer" type="text" value="" class="easyui-validatebox" /></td>
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
			showValueByKey("${providerId}", "providerId");
			var time = new Date();
			$("#addTime").val(time);

			//设置供应商产品默认状态为冻结
			$("#status1 option[value='2']").attr("selected", true);

			
			<c:if test="${applicationScope.sysParam.commonMap.isNotNeedYanzheng}">
			showValueByKey("1", "status");
			</c:if>
		});

		function chooseProduct() {
			//获取供应商
			$providerId = "";
			if ($("#providerId2").val() == null || $("#providerId2").val() == undefined) {
				$providerId = $("#providerId1 option:selected").val();
			} else {
				$providerId = $("#providerId2").val();
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

			check();
			$.ajax({
				url : "${ctx}/boss/providerProductInfo/getProductCategoryInfo",
				type : "post",
				data : { "operator" : $operator, "province" : $province, "useArea" : $useArea, "name" : "provider",
					"id" : $providerId },
				dataType : "json",
				success : function(categoryList) {
					if (categoryList != null && categoryList != undefined) {
						$.each(categoryList,
								function(i, category) {
									$category.append("<option value='" +category.id+ "'>" + category.categoryName
											+ "</option>");
								});

					}
					getProductName();
				}, error : function() {

				} });

		}
		//设置标准价的格式
		function setPriceFormat() {
			var standardPrice = $("#standardPrice1").val();
			$("#standardPrice1").val(parseInt(standardPrice).toFixed(3));
			var costPrice = $("#costPrice1").val();
			$("#costPrice1").val(parseInt(costPrice).toFixed(3));
		}

		//获取分类名字
		function getProductName() {
			//产品
			var $categoryVal = $("#categoryId1 option:selected").text();
			$("#productName1").val($categoryVal);
		}

		//设置默认成本价以及折扣率
		function sureCostAndDisc() {
			//标准价
			var costPrice = $("#standardPrice1").val();
			$("#costPrice1").val(costPrice);
			$("#discount").val("1");
			setPriceFormat();
		}

		//通过折扣率计算成本价
		function calculateCostPrice() {
			if ($("#discount").val() == 1) {
				return false;
			}

			var discount = $("#discount").val();
			var costPrice = $("#standardPrice1").val();
			$("#costPrice1").val((discount * costPrice).toFixed(3));
		}

		function check() {
			//获取运营商
			$operator = $("#mobileOperator1 option:selected").val();

			//获取省份
			$province = $("#province1 option:selected").val();

			//获取使用区域
			$useArea = $("#useArea1 option:selected").val();

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
		}

		//计算产品销售价
		function getStandarPrice() {
			var priceMap = ${applicationScope.sysParam.productCategoryInfoMap};
			$.each(priceMap, function(key, value) {
				//获取产品ID
				var $category1 = $("#categoryId1 option:selected").val();
				if (key == $category1) {
					$("#standardPrice1").val(value.standarPrice);
				}
			});
			//获取标准价
			var $standarPrice = $("#standardPrice1").val();
			var price = 0.0;

			//获取供应商折扣
			//获取产品ID
			var $dis = $("#providerId2");
			var proId = "";
			if ($dis == null || $dis == undefined) {
				proId = $("#providerId1 option:selected").val();
			} else {
				proId = $("#providerId2").val();
			}
			console.log("供应商id" + proId);

			var $pro = ${applicationScope.sysParam.providerInfoMap};
			$.each($pro, function(key, value) {
				if (key == proId) {
					price = $standarPrice * value.discount;
					console.log("产品标准价:" + $standarPrice + " ,折扣:" + discount + " ,折后:" + price);
				}

			});

			//设置成本价为标准价
			$("#costPrice1").val(price);
			
		}
	</script>
</body>
</html>