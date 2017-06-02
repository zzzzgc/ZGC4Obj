<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/bossScheduleChange/${action}" method="post">
			<table class="formTable">
				<tr>
					<td><input type="hidden" name="id" value="" /></td>
				</tr>
				<tr>
					<td>对应表：</td>
					<td><select name="tableType" onchange="getTable();getType();">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.tableTypeMap}" var="tableTypeMap">
								<option value="${tableTypeMap.key}">${tableTypeMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr id="customerInfo" style="display: none;">
					<!-- 					<td>采购商：</td> -->
					<!-- 					<td><select name="customerId" id="customerId" onchange="getTableIdByCustomerId();" onblur="getNames(this,'tableIds')"> -->
					<!-- 							<option value="">请选择</option> -->
					<!-- 							<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap"> -->
					<!-- 								<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option> -->
					<!-- 							</c:forEach> -->
					<!-- 					</select></td> -->
					<td>采购商:</td>
					<td><input name="customerName"
							type="text" value="" class="easyui-validatebox" data-options="width:200" /></td>
					<td><input name="customerId" id="customerId" type="hidden" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr id="providerInfo" style="display: none;">
					<td>供货商：</td>
					<td><input name="providerName"
							type="text" value="" class="easyui-validatebox" data-options="width:200" /></td>
					<td><input id="providerId" name="providerId" type="hidden" value="" class="easyui-validatebox" /></td>

					<!-- 					<td>供应商：</td> -->
					<!-- 					<td><select id="providerId" name="providerId" onchange="getTableIdByProviderId();" onblur="getNames(this,'tableIds')"> -->
					<!-- 							<option value="">请选择</option> -->
					<!-- 							<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap"> -->
					<!-- 								<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option> -->
					<!-- 							</c:forEach> -->
					<!-- 					</select></td> -->
				</tr>
				<tr id="productInfo" style="display: none;">
					<td>产品类别信息：</td>
					<td><select multiple="multiple" id="tableId" name="tableId" size="25" onblur="getNames(this,'tableIds')">
							<option value="">请选择</option>
					</select></td>
				</tr>

				<tr style="display: none;">
					<td>表id：</td>
					<td><input name="tableIds" type="text" value="" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>修改类型：</td>
					<td><select name="type" onchange="isShowChangeNum();">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.changeTypeMap}" var="changeTypeMap">
								<option value="${changeTypeMap.key}">${changeTypeMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td>是否生效：</td>
					<td><select name="status">
							<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr id="changeNum" style="display: none;">
					<td>折扣数：</td>
					<td><input name="changeNum" type="text" value="${bossScheduleChange.changeNum}" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>执行时间：</td>
					<td><input name="changeTime" type="text" value="${bossScheduleChange.changeTime}" class="easyui-my97"
							startDate='%y-%M-%d 00:00:00' datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '修改时间'" /></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input name="remark" type="text" value="${bossScheduleChange.remark}" class="easyui-validatebox" /></td>
				</tr>
				<tr style="display:none;">
					<td>添加时间：</td>
					<td><input name="addTime" type="text" value="${bossScheduleChange.addTime}" class="easyui-validatebox" /></td>
				</tr>

				<tr style="display:none;">
					<td>添加用户：</td>
					<td><input name="addUser" type="text" value="${bossScheduleChange.addUser}" class="easyui-validatebox" /></td>
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
				if (data != "success") {
					$($("#dlg .l-btn-text")[0]).css('display', "");
					$($("#dlg .l-btn-text")[1]).css('display', "");
				}
			} });
			showValueByKey("${bossScheduleChange.tableType}", "tableType");
			showValueByKey("${bossScheduleChange.type}", "type");
			showValueByKey("${bossScheduleChange.status}", "status");
			deleteMSCommon($("input[name=changeTime]").val(), "changeTime");
			deleteMSCommon($("input[name=addTime]").val(), "addTime");
		});

		function getTable() {
			var tableType = $("select[name=tableType] option:selected").val();
			if (tableType == 1) {
				$("#customerInfo").css("display", "");
				$("#providerInfo").css("display", "none");
				$("#productInfo").css("display", "none");
			} else if (tableType == 2) {
				$("#customerInfo").css("display", "none");
				$("#providerInfo").css("display", "");
				$("#productInfo").css("display", "none");
			} else if (tableType == 3) {
				$("#customerInfo").css("display", "");
				$("#providerInfo").css("display", "none");
				$("#productInfo").css("display", "");
			} else if (tableType == 4) {
				$("#customerInfo").css("display", "none");
				$("#providerInfo").css("display", "");
				$("#productInfo").css("display", "");
			}
		}
		function getType() {
			var $type = $("select[name=type]");
			$type.empty();
			var tableType = $("select[name=tableType] option:selected").val();
			$.each(${applicationScope.sysParam.changeTypeMapJson}, function(key, value) {
				if (((tableType == 3 || tableType == 4) || key != 2)) {
					$type.append("<option value='" +key+ "'>" + value + "</option>");
				}
			});
		}
		function isShowChangeNum() {
			var type = $("select[name=type] option:selected").val();
			if (type != 2) {
				$("#changeNum").css("display", "none");
			} else {
				$("#changeNum").css("display", "");
			}
		}
		function getTableIdByCustomerId() {
			var $category = $("#tableId");
			$category.empty();
			$.each(${applicationScope.sysParam.customerProductInfoMapJson}, function(key, value) {
				if (value.customerId == $("#customerId").val()) {
					$category.append("<option value='" +value.id+ "'>" + value.productName + "</option>");
				}
			});
		};

		function getTableIdByProviderId() {
			var $category = $("#tableId");
			$category.empty();
			$.ajax({
				url : "${ctx}/boss/providerProductInfo/getProviderProductsByProviderIds",
				type : "post",
				async : false,
				traditional : true,
				data : { "providerIds" : new Array($("#providerId").val()) },
				success : function(value) {
					if (value.length != 0) {
						for (var int = 0; int < value.length; int++) {
							$category.append("<option value='" +value[int].id+ "'>" + value[int].productName
									+ "</option>");
						}
					}
				} });

		};

		mofu_customerId();
		function mofu_customerId() {
			var customerNameArrays = new Array();
			var indexNum = 0;
			var customerIdArrays = new Array();
			$.each(${applicationScope.sysParam.customerInfoMap},
					function(key, val) {
						customerNameArrays[indexNum] = val.customerName != null ? val.customerName.replace("&mdash;",
								"-") : "";
						customerNameArrays[indexNum] = customerNameArrays[indexNum];
						customerIdArrays[indexNum] = val.id;
						indexNum++;
					});
			customerNameArrays[indexNum] = "全部";
			customerIdArrays[indexNum] = "0";
			var map = ${applicationScope.sysParam.customerInfoMap};
			$("input[name=customerName]").autocomplete({ minChars : 1, //
			maxHeight : 300, //
			lookup : customerNameArrays, //
			unhighlightColor : "red", onSelect : function(suggestion) {
				var index = customerNameArrays.indexOf(suggestion.value);
				$("input[name=customerId]").val(customerIdArrays[index]);
				onblur_changeCustomerId();
			} });
		}
		function onblur_changeCustomerId() {
			getTableIdByCustomerId();
			$("input[name=tableIds]").val($("input[name=customerId]").val());
		}

		mofu_providerId();
		function mofu_providerId() {
			var providerNameArrays = new Array();
			var indexNum = 0;
			var providerIdArrays = new Array();
			$.each(${applicationScope.sysParam.providerInfoMap},
					function(key, val) {
						providerNameArrays[indexNum] = val.providerName != null ? val.providerName.replace("&mdash;",
								"-") : "";
						providerIdArrays[indexNum] = val.id;
						indexNum++;
					});
			var map = ${applicationScope.sysParam.providerInfoMap};
			$("input[name=providerName]").autocomplete(
					{ minChars : 1, maxHeight : 300, lookup : providerNameArrays, onSelect : function(suggestion) {
						console.debug(suggestion.value);
						var index = providerNameArrays.indexOf(suggestion.value);
						$("input[name=providerId]").val(providerIdArrays[index]);
						onblur_changeProviderId();
					} });
		}
		function onblur_changeProviderId() {
			getTableIdByProviderId();
			$("input[name=tableIds]").val($("input[name=providerId]").val());
		}
	</script>
</body>
</html>