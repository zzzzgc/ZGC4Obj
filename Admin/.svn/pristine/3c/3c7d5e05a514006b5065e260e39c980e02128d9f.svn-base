<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
				<!-- 		采购商查询 start	 -->
				<input type="text" name="filter_EQI_customerId" class="easyui-validatebox" data-options="width:60,prompt: '采购商id'" />

<!-- 				<input type="text" name="customerName" class="easyui-validatebox" data-options="width:150,prompt: '采购商名称'" /> -->
				<span class="toolbar-item dialog-tool-separator"></span> 采购商：<select name="customerId" onchange="getCustomerIdBySelect()">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.bossCustomerMap}" var="bossCustomerMap">
						<option value="${bossCustomerMap.key}">${bossCustomerMap.value.customerName}</option>
					</c:forEach>
				</select>
				<input type="hidden" name="filter_INI_customerId" class="easyui-validatebox" data-options="width:100,prompt: '采购商id'" />
				<!---		采购商查询 end		 -->
				<span class="toolbar-item dialog-tool-separator" />
				<!-- 		供应商查询 start	 -->
				<input type="text" name="filter_EQI_providerId" class="easyui-validatebox" data-options="width:60,prompt: '供应商id'" />
<!-- 				<input type="text" name="providerName" class="easyui-validatebox" data-options="width:150,prompt: '供应商名称'" /> -->
				<span class="toolbar-item dialog-tool-separator"></span>
				
				 供应商：<select name="providerId" onchange="getSuccessIdBySelect()">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.bossProviderMap}" var="bossProviderMap">
						<option value="${bossProviderMap.key}">${bossProviderMap.value.providerName}</option>
					</c:forEach>
				</select>
				<input type="hidden" name="filter_INI_providerId" class="easyui-validatebox" data-options="width:100,prompt: '供应商id'" />
				<!---		供应商查询 end		 -->
<!-- 				<input type="text" name="filter_LIKES_failReason" class="easyui-validatebox" data-options="width:60,prompt: '失败原因'" /> -->
<!-- 				<div style="height: 7px;"></div> -->
				<input id="d3" type="text" name="filter_GED_successTime" class="easyui-my97" startDate='%y-%M-%d 00:00:00'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '订单处理时间'" />
				--
				<input id="d4" type="text" name="filter_LED_successTime" class="easyui-my97" startDate='%y-%M-%d 23:59:59'
					datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '订单处理时间'" />
				<!-- 		 产品查询	start		 -->
				<div style="height: 7px;"></div>
				<shiro:hasPermission name="boss:orderInfo:search">
					<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
					<span class="toolbar-item dialog-tool-separator"></span>
				</shiro:hasPermission>
				<input type="reset" value="重置" onclick="clearCity1();resetTime();clearHiddenValue();" />
			</form>
		</div>

	</div>
	<table id="dg" style="width: 100%"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
		var dg;
		$(function() {
			resetTime();
			dg = $('#dg').datagrid(
					{
						method : "POST",
						url : '${ctx}/boss/common/cxOldAdminOrderMoneyList?tableName='+'${map.tableName}',
						fit : true,
						autoRowHeight : false,
						fitColumns : true,
						queryParams : {
// 							filter_GED_receiveTime : $("input[name='filter_GED_receiveTime']").val(),
							filter_LED_receiveTime : $("input[name='filter_LED_receiveTime']").val(), },
						border : false,
						striped : true,
						onLoadSuccess : function() {
							getBalckList();
						},
						idField : 'id',
						pagination : true,
						rownumbers : true,
						pageNumber : 1,
						pageSize : 20,
						pageList : [
								10, 20, 30, 40, 50, 100, 200
						],

						singleSelect : true,
						columns : [
							[
									{ field : 'customerId', title : '采购商ID' },
									{ field : 'customerName', title : '采购商名称', formatter : function(value, row, index) {
										//获取采购商的列表
										var $provider = ${applicationScope.sysParam.bossCustomerMapJson};
										var retrunVal = "";
										$.each($provider, function(key, val) {
											if (key == row.customerId) {
												retrunVal = val.customerName;
											}
										});
										if (retrunVal == "") {
											retrunVal = "采购商已被删除";
										}
										return retrunVal;
									} },
									{ field : 'providerId', title : '供应商ID' },
									{ field : 'providerName', title : '供应商', formatter : function(value, row, index) {
										//获取供货商的列表
										var $provider = ${applicationScope.sysParam.bossProviderMapJson};
										var retrunVal = "";
										$.each($provider, function(key, val) {
											if (key == row.providerId) {
												retrunVal = val.providerName;
											}
										});
										if (retrunVal == "") {
											retrunVal = "供应商已被删除";
										}
										return retrunVal;
									} },
									{ field : 'operator', title : '运营商' },
									{ field : 'phone', title : '充值号码' },
									{ field : 'province', title : '省份' },
									{ field : 'city', title : '城市' },
									{ field : 'categoryId', title : '采购商产品名称', formatter : function(value, row, index) {
										var $productCategory = ${applicationScope.sysParam.productCategoryInfoMap};
										var retrunVal = "";
										$.each($productCategory, function(key, val) {
											if (key == value) {
												retrunVal = val.categoryName;
												return;
											}
										});
										return retrunVal;
									} },
									{
										field : 'providerCategoryId',
										title : '供货商产品名称',
										formatter : function(value, row, index) {
											var retrunVal = "";
											$.each(${applicationScope.sysParam.productCategoryInfoMap}, function(key,
													val) {
												if (key == value) {
													retrunVal = val.categoryName;
													return;
												}
											});
											return retrunVal;
										} },
									{ field : 'productStandard', title : '产品规格',
										formatter : function(value, row, index) {
											var $productSize = ${applicationScope.sysParam.productCategoryInfoMap};
											var size = "";
											var unit = "";
											$.each($productSize, function(key, val) {
												if (row.categoryId == val.id) {
													size = val.productNum;
													if (val.productUnit == 1) {
														unit = "M";
													} else if (val.productUnit == 2) {
														unit = "G";
													} else if (val.productUnit == 3) {
														unit = "元";
													}
												}
											});
											return size + unit + "";
										} },
									{ field : 'price', title : '总价' },
									{ field : 'cost', title : '成本价' },
									{
										field : 'earn',
										title : '利润',
										formatter : function(value, row, index) {
											var money;
											if (row.status == 3) {
												money = (row.price - row.cost).toFixed(2);
												if (money < 0) {
													return '<span style="color:red;font-weight:bold;">' + money
															+ '</span>';
												} else {
													return '<span style="color:green;font-weight:bold;">' + money
															+ '</span>';
												}

											} else
												return "";
										} },
									{ field : 'weiXinPrice', title : '微信售价' },
									{ field : 'status', title : '订单状态', formatter : function(value, row, index) {
										if (value == 1) {
											return "新增";
										} else if (value == 2) {
											return "充值中";
										} else if (value == 3) {
											return "充值成功";
										} else if (value == 4) {
											return "充值失败";
										} else if (value == 5) {
											return "等待确认";
										} else if (value == 6) {
											return "需要手工处理";
										} else if (value == 7) {
											return "财务平账订单,客服不处理";
										}
									} },
									{ field : 'failReason', title : '失败原因' },
									{ field : 'receiveTime', title : '订单提交时间', formatter : function(value, row, index) {
										/* return jsonTimeStamp(value); */
										return timestampformat(value);
									} },
									{ field : 'endTime', title : '订单处理时间', formatter : function(value, row, index) {
										if (row.status == 3) {
											return jsonTimeStamp(row.successTime);
										} else if (row.status == 4) {
											return jsonTimeStamp(row.failTime);
										} else
											return "";
									} },
									{ field : 'useTime', title : '耗时(s)', formatter : function(value, row, index) {
										if (row.status == 3) {
											return (new Date(row.successTime) - new Date(row.receiveTime)) / 1000;
										} else if (row.status == 2) {
											return getDintanceBetweenTwoTime(row.receiveTime);
										} else if (row.status == 4) {
											return (new Date(row.failTime) - new Date(row.receiveTime)) / 1000;
										}
									} },

									{ field : 'callbackStatus', title : '回调状态',
										formatter : function(value, row, index) {
											if (value == 1) {
												return "未回调";
											} else if (value == 2) {
												return "已回调";
											} else if (value == 3) {
												return "已主动回调";
											}
										} }, { field : 'chargeCount', title : '充值次数' },
									{ field : 'orderKey', title : '采购商订单号' },
									{ field : 'providerKey', title : '供应商流水号' }, { field : 'id', title : '订单号' }
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
		}
		function getCustomerIdBySupplier() {
			var filter_EQI_customerId = $("input[name=filter_EQI_customerId]");
			var cusSimpleName = $("input[name=cusSimpleName]").val();
			var $customerInfo = ${applicationScope.sysParam.customerInfoMap};
			filter_EQI_customerId.val("");
			var success = false;
			$.each($customerInfo, function(key, val) {
				if (val.cusSimpleName.toUpperCase() == cusSimpleName.toUpperCase()) {
					filter_EQI_customerId.val(val.id);
					success = true;
					return false;
				}
			});
			if (!success && cusSimpleName != "") {
				parent.$.messager.alert("不存在这样的采购商简称！");
			}
		}
		function getSuccessIdBySupplier() {
			var filter_EQI_providerId = $("input[name=filter_EQI_providerId]");
			var providerSupplier = $("input[name=providerSupplier]").val();
			var $providerInfo = ${applicationScope.sysParam.providerInfoMap};
			filter_EQI_providerId.val("");
			var success = false;
			$.each($providerInfo, function(key, val) {
				if (val.supplier.toUpperCase() == providerSupplier.toUpperCase()) {
					filter_EQI_providerId.val(val.id);
					success = true;
					return false;
				}
			});
			if (!success && providerSupplier != "") {
				parent.$.messager.alert("不存在这样的供应商简称！");
			}
		}
		//弹窗增加
		function add() {
			d = $("#dlg").dialog(
					{ title : '添加配置', width : 380, height : 250, href : '${ctx}/boss/orderInfo/create',
						maximizable : true, modal : true, buttons : [
								{ text : '确认', handler : function() {
									$("#mainform").submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//创建查询对象并查询
		function cx() {
// 			getCategoryId();
// 			getCustomerId();
// 			getSuccessId();
// 			resetReceiveTimeIfEmpty();
			var obj = $("#searchFrom").serializeObject();
			dg.datagrid('reload', obj);
			$("#bottomLeft").html("");
			$("#bottomRight").html("");
		}

		//格式化 关于钱的字段，例如单价，一口价等
		function formaterMoney(value) {
			var val = parseInt(value);
			var vale = val / 100;
			val = vale.toFixed(3);
			return val;
		}
		function jumpToMainTabs(index) {
			$('#dg').datagrid('clearSelections');
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			mainTabs = parent.$("#mainTabs");
			addTab('供货商信息', 'boss/providerInfo/list?id=' + row.providerId);
		}
		function addTab(title, url) {
			if (mainTabs.tabs('exists', title)) {
				mainTabs.tabs('close', title);
			}
			var content = '<iframe scrolling="auto" frameborder="0"  src="' + url
					+ '" style="width:100%;height:100%;"></iframe>';
			mainTabs.tabs('add', { title : title, content : content, closable : true });

		}
		function getCountInfo() {
			$("#bottomLeft").html("");
			$("#bottomRight").html("");
			getCategoryId();
			getCustomerId();
			getSuccessId();
			var obj = $("#searchFrom").serializeObject();
			$.ajax({
				url : "${ctx}/boss/orderInfo/getCountInfo",
				type : "get",
				data : obj,
				success : function(data) {
					if (data != null) {
						/* var str1 = "总订单数量："+data.totalNum+"笔，成功"+data.successNum+"笔[平均耗时"+data.successTime.toFixed(2)+"秒]，失败"+data.failNum+"笔[平均耗时"+(data.failTime).toFixed(2)+"秒]<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;充值中"+data.chargeNum+"笔，等待确认"+data.waitNum+"笔，需要手工处理"+data.handleNum+"笔";
						var str2 = "总销售金额："+data.totalMoney.toFixed(2)+"元，成功"+data.successMoney.toFixed(2)+"元，失败"+data.failMoney.toFixed(2)+"元，盈利"+data.earnMoney.toFixed(2)+"元<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;充值中"+data.chargeMoney.toFixed(2)+"元，等待确认"+data.waitMoney.toFixed(2)+"元，需要手工处理"+data.handleMoney.toFixed(2)+"元"; */
						var str1 = "总订单：" + data.totalNum + "笔<br/>成 &nbsp;&nbsp;功：" + data.successNum + "笔[平均耗时"
								+ (data.successTime != null ? data.successTime.toFixed(2) : "")
								+ "秒]<br>失 &nbsp;&nbsp;败：" + data.failNum + "笔[平均耗时"
								+ (data.failTime != null ? data.failTime.toFixed(2) : "") + "秒]<br/>充值中："
								+ data.chargeNum + "笔<br>等待确认：" + data.waitNum + "笔<br/>手工处理：" + data.handleNum + "笔";
						var str2 = "总销售：" + data.totalMoney.toFixed(2) + "元<br/>成 &nbsp;&nbsp;功："
								+ data.successMoney.toFixed(2) + "元<br/>失 &nbsp;&nbsp;败：" + data.failMoney.toFixed(2)
								+ "元<br/>充值中：" + data.chargeMoney.toFixed(2) + "元<br/>等待确认："
								+ data.waitMoney.toFixed(2) + "元<br/>手工处理：" + data.handleMoney.toFixed(2)
								+ "元<br/>成功成本：" + data.successCost.toFixed(2) + "元<br/>盈 &nbsp;&nbsp;利："
								+ data.earnMoney.toFixed(2) + "元";
						$("#bottomLeft").html(str1);
						$("#bottomRight").html(str2);
					}
				}, error : function() {

				} });
		}
		//省市的二级联动
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
// 		重置时间
		function resetTime() {
// 			var date = new Date();
// 			date.setHours(0, 0, 0);
// 			var filter_GED_receiveTime = $("input[name='filter_GED_receiveTime']");
// 			var filter_LED_receiveTime = $("input[name='filter_LED_receiveTime']");
// 			filter_GED_receiveTime.val(getNowFormatDate(date));
// 			date.setHours(24, 0, 0);
// 			filter_LED_receiveTime.val(getNowFormatDate(date));
		}
		//如果receiveTime为空则默认为今天的0时0分0秒
		function resetReceiveTimeIfEmpty() {
			if ($("input[name='filter_GED_receiveTime']").val() != "") {
				return;
			}
			var date = new Date();
			date.setHours(0, 0, 0);
			var filter_GED_receiveTime = $("input[name='filter_GED_receiveTime']");
			filter_GED_receiveTime.val(getNowFormatDate(date));
			date.setHours(24, 0, 0);
		}
		//充值清空id的值 
		function clearHiddenValue() {
			$("input[name=filter_EQI_customerId]").val("");
			$("input[name=filter_EQI_providerId]").val("");
			$("input[name=filter_INI_categoryId]").val("");
		}
		//刷新缓存
		function flush(type) {
			$.ajax({ url : "${ctx}/boss/orderInfo/flush", type : "post", data : { "type" : type },
				success : function(data) {
					successTip(data, dg);
				}, error : function() {
					successTip("刷新失败", dg);
				}

			});
		}

		//导出excel
		function exportExcel() {
			$.messager.confirm('提示', '确定要导出excel？', function(data) {
				if (data) {
					getCategoryId();
					getCustomerId();
					getSuccessId();
					var obj = $("#searchFrom").serializeObject();
					var params = "?";
					for ( var name in obj) {
						params += name + "=" + obj[name] + "&&";
					}
					params = params.substring(0, params.length - 2);
					parent.$.messager.show({ title : "提示", msg : "导出需要时间，请稍等片刻！", position : "bottomRight" });
					var url = "${ctx}/boss/orderInfo/exportExcel" + params;
					window.location.href = url;
				}
			});
		}

		//手工回调订单
		function callback(value) {

			var ids = $('#dg').datagrid('getSelections');

			if (ids.length < 1) {
				alert("请至少选择一行数据!");
				return;
			}
			var title = $(value).val();
			var color = "green";
			parent.$.messager.confirm('提示', '您确定要<span style="color:green;font-weight:bold;">' + title + '</span>吗?',
					function(data) {
						if (data) {
							var list = new Array();
							var j = 0;
							for (var i = 0; i < ids.length; i++) {
								if (ids[i].status == 3 || ids[i].status == 4) {
									alert("充值成功或失败的订单不能改变状态，订单号:" + ids[i].id);
								} else {
									list[j++] = ids[i].id;
								}

							}
							console.log("list.size:" + list.length);

							$.ajax({ url : "${ctx}/boss/orderInfo/updateOrderStatus", type : "post",
								traditional : true, data : { "ids" : list }, success : function(data) {
									successTip(data, dg);
								}, error : function() {
									successTip("发送失败", dg);
								}

							});
						}
					});
		}

		//获取黑名单列表
		function getBalckList() {
			$.ajax({ url : "${ctx}/boss/orderInfo/getBlackList", type : "get", success : function(data) {

				if (data.state == "true") {
					if (data.black > 0 || data.exception > 0) {
						var msg = "今天一共有" + data.black + "个黑名单用户，" + data.exception + "个异常用户，请及时处理";
						successTip(msg, dg);
					}
				}
			}, error : function() {
				successTip("发送失败", dg);
			}

			});
		}

		//获取当前时间与之前的时间之差
		function getDintanceBetweenTwoTime(time) {
			var date = new Date();
			var newTime = date.getTime();
			return parseInt((newTime - time) / 1000);
		}

		Date.prototype.format = function(format) {
			var o = {
			// month
			"M+" : this.getMonth() + 1,
			// day
			"d+" : this.getDate(),
			// hour
			"h+" : this.getHours(),
			// minute
			"m+" : this.getMinutes(),
			// second
			"s+" : this.getSeconds(),
			// quarter
			"q+" : Math.floor((this.getMonth() + 3) / 3),
			// millisecond
			"S" : this.getMilliseconds() };
			if (/(y+)/.test(format) || /(Y+)/.test(format)) {
				format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			}
			for ( var k in o) {
				if (new RegExp("(" + k + ")").test(format)) {
					format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k])
							.substr(("" + o[k]).length));
				}
			}
			return format;
		};

		function timestampformat(timestamp) {
			return (new Date(timestamp)).format("yyyy-MM-dd hh:mm:ss");
		}

		function getSuccessIdBySelect() {
			var providerId = $("select[name=providerId] option:selected").val();
			$("input[name=filter_EQI_providerId]").val(providerId);
		}
		function getSuccessId() {
			var filter_INI_providerId = $("input[name=filter_INI_providerId]");
			var providerName = $("input[name=providerName]").val();
			filter_INI_providerId.val("");
			if (providerName.length == 0) {
				return;
			}
			$.each(${applicationScope.sysParam.providerInfoMap}, function(key, val) {
				if (val.providerName.indexOf(providerName) != -1 || providerName.length == 0) {
					if (filter_INI_providerId.val().length == 0) {
						filter_INI_providerId.val(val.id);
					} else {
						filter_INI_providerId.val(filter_INI_providerId.val() + "," + val.id);
					}
				}
			});
			if (!(providerName.length == 0) && filter_INI_providerId.val().length == 0) {
				filter_INI_providerId.val(666666);
			}
		}
	</script>

</body>
</html>