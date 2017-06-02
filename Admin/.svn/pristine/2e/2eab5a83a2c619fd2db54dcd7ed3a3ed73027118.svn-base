<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body style="font-family: '微软雅黑'">
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<form id="searchFrom" action="">
				<input id="d1" type="text" name="filter_GED_receiveTime" class="easyui-my97" startDate='%y-%M-%d 00:00:00'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '起始时间'" />
				--
				<input id="d2" type="text" name="filter_LED_receiveTime" class="easyui-my97" startDate='%y-%M-%d 23:59:59'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '结束时间'" />
				<input type="text" name="filter_INS_phone" class="easyui-validatebox" data-options="width:120,prompt: '手机号码'"
					onchange="trimLeft(this)" />
				<input type="text" name="GE_chargeNum" class="easyui-validatebox" data-options="width:120,prompt: '充值次数'"
					onchange="trimLeft(this)" />
				-
				<input type="text" name="LE_chargeNum" class="easyui-validatebox" data-options="width:120,prompt: '充值次数'"
					onchange="trimLeft(this)" />
				运营商：<select name="filter_EQS_beyoudOperation">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap">
						<option value="${providerMap.value}">${providerMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span>
				<!-- -->
				省份： <select id="province" name="filter_EQS_province" onchange="chooseCity1()">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.provinceMap}" var="provinceMap">
						<option value="${provinceMap.value}">${provinceMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span>
				<!-- -->
				城市： <select id="city" name="filter_EQS_city">
					<option value="">请选择</option>
				</select><span class="toolbar-item dialog-tool-separator"></span>
				<!-- -->
				状态：<select name="filter_EQI_status">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.orderStatusMap}" var="orderStatusMap">
						<option value="${orderStatusMap.key}">${orderStatusMap.value}</option>
					</c:forEach>
				</select>
				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a> <span
					class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置" onclick="resetTime()" />
			</form>
			<div style="height: 7px;"></div>

		</div>

	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
		var dg;
		$(function() {
			resetTime();
			dg = $('#dg').datagrid(
					{
						method : "get",
						url : '${ctx}/boss/common/phoneCountList',
						queryParams : { filter_GED_receiveTime : $("input[name='filter_GED_receiveTime']").val(),
							filter_LED_receiveTime : $("input[name='filter_LED_receiveTime']").val(), },
						fit : true,
						fitColumns : true,
						border : false,
						striped : true,
						idField : 'id',
						pagination : true,
						rownumbers : true,
						pageNumber : 1,
						pageSize : 20,
						pageList : [
								10, 20, 30, 40, 50
						],
						singleSelect : true,
						columns : [
							[
									{ field : 'day', title : '日期', width : 100 },
									{ field : 'phone', title : '手机号码', width : 100 },
									{ field : 'operator', title : '运营商', width : 100 },
									{ field : 'province', title : '省份', width : 100 },
									{ field : 'city', title : '城市', width : 100 },
									{ field : 'count', title : '充值次数', width : 100 },
									{
										field : 'status',
										title : '次数',
										width : 100,
										formatter : function(value, row, index) {
											var array = value.split(",");
											var successNum = 0;
											var failNum = 0;
											var returnVal = "";
											var other = "";
											for (var i = 0; i < array.length; i++) {
												if (array[i] == "3") {
													successNum++;
												} else if (array[i] == "4") {
													failNum++;
												} else {
													other += "---1"
															+ (array[i] == 1 ? "新增"
																	: (array[i] == 2 ? "充值中"
																			: (array[i] == 5 ? "等待确认"
																					: (array[i] == 6 ? "需要手工处理"
																							: (array[i] == 6 ? "平账审核"
																									: "ssss")))));
												}
											}
											if (successNum != 0) {
												returnVal += "成功:" + successNum;
											}
											if (failNum != 0) {
												if (returnVal.length > 0) {
													returnVal += ",";
												}
												returnVal += "失败:" + failNum;
											}
											return returnVal + other;
										} },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});
		//时间戳转化为日期格式yyyy-MM-dd HH:mm:ss
		function jsonTimeStamp(milliseconds) {
			if (milliseconds != "" && milliseconds != null && milliseconds != "null") {
				var datetime = new Date();
				datetime.setTime(milliseconds);
				var year = datetime.getFullYear();
				var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
				var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
				var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
				var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
				var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
				return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
			} else {
				return "";
			}

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
		} //省市的二级联动
		function chooseCity1() {
			var $province = $("#province option:selected").val();
			var $city = $("#city");
			//清空城市列表
			clearCity1();
			$.ajax({ url : "${ctx}/boss/orderInfo/getCity", type : "post", data : { "province" : $province },
				dataType : "json", success : function(cityList) {
					if (cityList != null && cityList != undefined) {
						$.each(cityList, function(i, city) {
							$city.append("<option value='" +city+ "市'>" + city + "</option>");
						});
					}
				}, error : function() {

				} });
		}

		//清空城市
		function clearCity1() {
			$("#city").empty();
			$("#city").append("<option value=''>请选择</option>");
			urll = '${ctx}/boss/orderInfo/orderInfoList';
		}
		//重置时间
		function resetTime() {
			var filter_GED_receiveTime = $("input[name='filter_GED_receiveTime']");
			var date = new Date();
			date.setHours(-24 * 1, 0, 0);
			filter_GED_receiveTime.val(getNowFormatDate(date));
			var filter_LED_receiveTime = $("input[name='filter_LED_receiveTime']");
			var date = new Date();
			date.setHours(0, 0, 0);
			date.setHours(24, 0, 0);
			filter_LED_receiveTime.val(getNowFormatDate(date));
		}
	</script>
</body>
</html>