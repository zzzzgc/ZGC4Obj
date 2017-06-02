<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/orderInfo/add" method="post">
			<table class="formTable">
				<tr>
					<td>采购商：</td>
					<td><input type="hidden" name="id" value="${orderInfo.id }" /> <select name="customerId" style="width: 150px;">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap">
								<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>供应商：</td>
					<td><input type="hidden" name="id" value="${orderInfo.id }" /> <select name="successId" style="width: 150px;">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap">
								<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>运营商：</td>
					<td><select id="mobileOperator1" name="beyoudOperation" onchange="chooseProduct()">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
								<option value="${providerMap.value}">${providerMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>产品名称：</td>
					<td><select id="categoryId1" name="categoryId">
							<option value="">请选择</option>
					</select></td>
				</tr>
				<tr>
					<td>要充值的手机号码：</td>
					<td><input name="phone" type="text" value="${orderInfo.phone }" class="easyui-validatebox" /></td>
				</tr>
<!-- 				列表增加一个“添加”、“修改”按键，用户可添加/修改采购商名称（下拉框）、 -->
<!-- 				供应商（下拉框）、运营商（下拉框）、充值号码、采购商产品名称（下拉框）、 -->
<!-- 				供货商产品名称（下拉框）、总价、成本价、订单状态、订单提交时间。 -->
				<tr style="display: none">
					<td>订单状态：</td>
					<td><select name="status">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.orderStatusMap}" var="orderStatusMap">
								<option value="${orderStatusMap.key}">${orderStatusMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>销售价：</td>
					<td><input name="price" type="text"  class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>成本价：</td>
					<td><input name="cost" type="text" class="easyui-validatebox" /></td>
				</tr>
				<tr style="display: none">
					<td>提交时间到供货商的时间：</td>
					<td><input name="submitTime" type="text"
						value="${orderInfo.callbackTime }" class="easyui-my97"
						datefmt="yyyy-MM-dd HH:mm:ss" startDate='%y-%M-%d 00:00:00'
						data-options="width:150,prompt: '呼叫时间'" /></td>
				</tr>
				<tr style="display: none">
					<td>失败时间：</td>
					<td><input name="failTime" type="text"
						value="${orderInfo.failTime }" class="easyui-my97"
						datefmt="yyyy-MM-dd HH:mm:ss" startDate='%y-%M-%d 00:00:00'
						data-options="width:150,prompt: '呼叫时间'" /></td>
				</tr>
				<tr>
					<td>成功时间：</td>
					<td><input name="successTime" type="text"
						value="${orderInfo.successTime }" class="easyui-my97"
						datefmt="yyyy-MM-dd HH:mm:ss" startDate='%y-%M-%d 00:00:00'
						data-options="width:150,prompt: '呼叫时间'" /></td>
				</tr>
				<tr style="display: none">
					<td>接收时间：</td>
					<td><input name="receiveTime" type="text"
						value="${orderInfo.receiveTime }" class="easyui-my97"
						datefmt="yyyy-MM-dd HH:mm:ss" startDate='%y-%M-%d 00:00:00'
						data-options="width:150,prompt: '呼叫时间'" /></td>
				</tr>
				<tr style="display: none">
					<td>回调时间：</td>
					<td><input name="callbackTime" type="text"
						value="${orderInfo.callbackTime }" class="easyui-my97"
						datefmt="yyyy-MM-dd HH:mm:ss" startDate='%y-%M-%d 00:00:00'
						data-options="width:150,prompt: '呼叫时间'" /></td>
				</tr>
				<tr>
					<td>采购商订单号：</td>
					<td><input name="orderKey" type="text" value="${orderInfo.orderKey }" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>失败原因：</td>
					<td><input name="failReason" type="text" value="${orderInfo.failReason }" class="easyui-validatebox" /></td>
				</tr>
				

				<tr style="display: none">
					<td>充值次数：</td>
					<td><input name="chargeCount" type="text" value="0" class="easyui-validatebox" /></td>
				</tr>
				<tr style="display: none">
					<td>订单优先值：</td>
					<td><input name="priority" type="text" value="0" class="easyui-validatebox" /></td>
				</tr>
				<tr style="display: none">
					<td>是否已回调或者查询状态：</td>
					<td><select name="callbackStatus">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.callbackStatusMap}" var="callbackStatusMap">
								<option value="${callbackStatusMap.key}">${callbackStatusMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>省份：</td>
					<td><select id="provincee" name="province" onchange="chooseCity()">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.provinceMap}" var="provinceMap">
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
				<tr style="display: none">
					<td>是否启用二次通道：</td>
					<td><select name="isSecondChannel">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr style="display: none">
					<td>供应商流水号：</td>
					<td><input name="providerKey" type="text" value="${orderInfo.providerKey }" class="easyui-validatebox" /></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			getCurrentTime();
			$('#mainform').form({ onSubmit : function() {
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			}, success : function(data) {
				successTip(data, dg, d);
			} });
		});
		function checkCity() {
			//判断市的下拉框默认值
			var cityStatus = "${orderInfo.city}";
			$("#cityy option").each(function() { //遍历全部option
				var text = $(this).val(); //获取option的内容
				if (text == cityStatus) {
					$(this).attr("selected", true);
				}
			});
		}

		function getCurrentTime() {
			var date = new Date();
			$("input[name=callbackTime]").val(getNowFormatDate(date));
			$("input[name=successTime]").val(getNowFormatDate(date));
			$("input[name=submitTime]").val(getNowFormatDate(date));
			$("input[name=receiveTime]").val(getNowFormatDate(date));
			$("input[name=failTime]").val(getNowFormatDate(date));
		}
		//获取当前的日期时间 格式“yyyy-MM-dd HH:MM:SS”
		function getNowFormatDate(date) {
			var seperator1 = "-";
			var seperator2 = ":";
			var month = date.getMonth() + 1;
			var strDate = date.getDate();
			if (month >= 1 && month <= 9) {
				month = "0" + month;
			}
			if (strDate >= 0 && strDate <= 9) {
				strDate = "0" + strDate;
			}
			var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " " + date.getHours()
					+ seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
			return currentdate;
		}

		//省市的二级联动
		function chooseCity() {
			var $province = $("#provincee option:selected").val();
			var $city = $("#cityy");
			//清空城市列表
			clearCity();

			$.ajax({ url : "${ctx}/boss/orderInfo/getCity", type : "post", data : { "province" : $province },
				dataType : "json", success : function(cityList) {
					if (cityList != null && cityList != undefined) {
						$.each(cityList, function(i, city) {
							$city.append("<option value='" +city+ "'>" + city + "</option>");
						});
					}
					checkCity();
				}, error : function() {

				} });

		}

		//清空城市
		function clearCity() {
			$("#cityy").empty();
			$("#cityy").append("<option value=''>请选择</option>");
			urll = '${ctx}/boss/orderInfo/list';
		}

		function chooseProduct() {
			//获取运营商
			$operator = $("#mobileOperator1 option:selected").val();

			//产品
			var $category = $("#categoryId1");

			$category.empty();

			$.ajax({
				url : "${ctx}/boss/providerProductInfo/getProductCategoryInfoPro",
				type : "post",
				data : { "operator" : $operator },
				dataType : "json",
				success : function(categoryList) {
					if (categoryList != null && categoryList != undefined) {
						$.each(categoryList,
								function(i, category) {
									$category.append("<option value='" +category.id+ "'>" + category.categoryName
											+ "</option>");
								});
					}
				}, error : function() {

				} });

		}
	</script>
</body>
</html>