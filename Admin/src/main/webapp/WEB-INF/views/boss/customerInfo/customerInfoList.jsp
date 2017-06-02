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
				<input type="text" name="filter_EQI_id" class="easyui-validatebox" data-options="width:150,prompt: '采购商id'" />
				<input type="text" name="filter_LIKES_customerName" class="easyui-validatebox" data-options="width:150,prompt: '采购商名称'" />
				<input type="text" name="filter_EQI_priority" class="easyui-validatebox" data-options="width:150,prompt: '订单优先级'" />
				<span class="toolbar-item dialog-tool-separator"></span> 状态：<select name="filter_EQI_status">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap">
						<option value="${freezeMap.key}">${freezeMap.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 发票类型:<select name="filter_EQI_tickType">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.tickTypeMap}" var="tickTypeMap">
						<option value="${tickTypeMap.key}">${tickTypeMap.value}</option>
					</c:forEach>
				</select> <br />
				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a> <span
					class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置" />
				&nbsp; <span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="button" value="批量开通" onclick="updateStatus(this,1)" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="button" value="批量冻结" onclick="updateStatus(this,0)" />
			<shiro:hasPermission name="boss:customerInfo:send">
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="button" value="发送采购商账号密码" onclick="sendCusEmail(this)" />
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:customerInfo:changePwd">
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="button" value="修改采购商密码" onclick="updatePaw(this)" />
			</shiro:hasPermission>
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:customerInfo:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:customerInfo:delete">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false"
					onclick="del()">删除</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:customerInfo:update">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"
					onclick="upd()">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:customerInfo:addBalance">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"
					onclick="addBalance()">注资</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:customerInfo:showPwd">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="" plain="true" data-options="disabled:false"
					onclick="getPasAndKey()">密码和密钥</a>
			</shiro:hasPermission>
			<!-- 				 -->
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true"
				data-options="disabled:false" onclick="exportExcel()">导出Excel</a>
		</div>

	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
		var dg;
		$(function() {
			dg = $('#dg').datagrid(
					{
						method : "get",
						url : '${ctx}/boss/customerInfo/customerInfoList',
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
						singleSelect : false,
						columns : [
							[
									/*采购商名称、采购商简称、采购商联系人、采购商电话、邮箱、采购商联系地址、资金余额、状态、发票类型、允许IP、订单优先级、添加时间、采购商回调地址、操作、是否向下兼容、是否手工回调、
										标识[MerChant]、开发者密匙、登录名（开发者ID）、预警金额、备注。*/

									{ field : 'ck', checkbox : true },
									{ field : 'id', title : 'id' },
									{ field : 'customerName', title : '采购商名称' },
									{ field : 'cusSimpleName', title : '采购商简称' },
									{ field : 'customerContact', title : '采购商联系人' },
									{ field : 'customerNumber', title : '采购商电话' },
									{ field : 'email', title : '邮箱' },
									{ field : 'customerAddress', title : '采购商联系地址' },
									{ field : 'balance', title : '资金余额', formatter : function(value, row, index) {
										if (value == null) {
											return '<span style="color:red;font-weight:bold;">资金余额不正常</span>';
										} else if (value >= 0) {
											return '<span style="color:green;font-weight:bold;">' + value + '</span>';
										} else if (value < 0) {
											return '<span style="color:red;font-weight:bold;">' + value + '</span>';
										}
									} },
									{ field : 'status', title : '状态', formatter : function(value, row, index) {
										if (value == 1) {
											return '<span style="color:green;font-weight:bold;">' + "正常" + '</span>';
										} else if (value == 0) {
											return '<span style="color:red;font-weight:bold;">' + "冻结" + '</span>';
										}
									} },
									{ field : 'tickType', title : '发票类型', formatter : function(value, row, index) {
										if (value == 1) {
											return "增值税发票";
										} else if (value == 2) {
											return "普通发票";
										}
									} },{ field : 'isSendMsg', title : '是否发送短信',
										formatter : function(value, row, index) {
											if (value == 0) {
												return '<span style="color:red;font-weight:bold;">' + "否" + '</span>';
											} else if (value == 1) {
												return '<span style="color:green;font-weight:bold;">' + "是" + '</span>';
											}
										} },
									{ field : 'allowIp', title : '允许ip' },
									{ field : 'priority', title : '订单优先级' },
									{ field : 'addTime', title : '添加时间', formatter : function(value, row, index) {
										if (value != null) {
											var time = jsonTimeStamp(value);
											return time;
										}
										return "";
									} },
									{ field : 'callbackAddress', title : '采购商回调地址' },
									{
										field : 'itemid',
										title : '操作',
										formatter : function(value, row, index) {
											return '<a href="#" iconCls="icon-add" onclick="jumpToMainTabs(' + index
													+ ')">产品信息</a>';
										} },
									{ field : 'isAutoConfig', title : '是否向下兼容',
										formatter : function(value, row, index) {
											if (value == 0) {
												return "否";
											} else if (value == 1) {
												return "是";
											}
										} },
									{ field : 'configLevel', title : '兼容等级' },
									{ field : 'isManualCallback', title : '是否手工回调',
										formatter : function(value, row, index) {
											if (value == 0) {
												return "否";
											} else if (value == 1) {
												return "是";
											}
										} },
									{ field : 'id1', title : '标识[MerChant]', sortable : false,
										formatter : function(value, row, index) {
											var value = row.id;
											return value;
										} },
									// 										{ field : 'devKey', title : '开发者密钥', sortable : false },
									{ field : 'loginName', title : '登录名(开发者ID)', sortable : false },
									// 									{ field : 'loginPaw', title : '密码', sortable : false },
									{ field : 'alarmBalance', title : '预警金额', sortable : false },
									{ field : 'remark', title : '备注' }
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
		//弹窗增加
		function add() {
			d = $("#dlg").dialog(
					{
						title : '添加配置',
						width : 380,
						height : 250,
						href : '${ctx}/boss/customerInfo/create',
						maximizable : true,
						modal : true,
						buttons : [
								{
									text : '确认',
									handler : function() {
										$('#mainform').form(
												'submit',
												{ url : '${ctx}/boss/customerInfo/add',
													data : $("#searchFrom").serializeObject(),
													success : function(data) {
														d.panel('close');
														successTip("success", dg);
														$.messager.alert(data);
													} });
									} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//删除
		function del() {
			var rows = dg.datagrid('getSelections');
			if (rows.length != 1) {
				parent.$.messager.show({ title : "提示", msg : "请选择一项！", position : "bottomRight" });
				return;
			}
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
				if (data) {
					$.ajax({ type : 'get', url : "${ctx}/boss/customerInfo/delete/" + rows[0].id,
						success : function(data) {
							successTip(data, dg);
							dg.datagrid('uncheckAll');
						} });
				}
			});
		}

		function getPasAndKey() {
			var rows = dg.datagrid('getSelections');
			if (rows.length != 1) {
				alert("请选择一个采购商");
				return;
			}
			var str = "用户"+rows[0].loginName+"密码" + rows[0].loginPaw + "密钥" + rows[0].devKey;
			$.messager.alert(str);
		}

		//弹窗修改
		function upd() {
			var rows = dg.datagrid('getSelections');
			if (rows.length != 1) {
				alert("请选择一项进行修改");
				return;
			}
			d = $("#dlg").dialog(
					{ title : '修改用户', width : 380, height : 250,
						href : '${ctx}/boss/customerInfo/update/' + rows[0].id, maximizable : true, modal : true,
						buttons : [
								{ text : '修改', handler : function() {
									$("#mainform").submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//弹窗添加注资
		function addBalance() {
			var row = dg.datagrid('getSelected');
			if (rowIsNull(row))
				return;
			d = $("#dlg").dialog(
					{ title : '增加注资', width : 380, height : 300, href : '${ctx}/boss/balanceRecord/add/' + row.id,
						maximizable : true, modal : true, buttons : [
								{ text : '添加', handler : function() {
									$('#mainform').submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//创建查询对象并查询
		function cx() {
			clearRows();
			var obj = $("#searchFrom").serializeObject();
			dg.datagrid('reload', obj);
		}

		//格式化 关于钱的字段，例如单价，一口价等
		function formaterMoney(value) {
			var val = parseInt(value);
			var vale = val / 100;
			val = vale.toFixed(3);
			return val;
		}

		//批量更新状态
		function updateStatus(html, state) {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}

			var ids = new Array();
			for (var i = 0; i < rows.length; i++) {
				ids[i] = rows[i].id;
			}
			var title = $(html).val();
			var color = (state == 0 || state == 2) ? "red" : "green";
			parent.$.messager.confirm('提示', '已选<span style="color:' + color + ';font-weight:bold;">' + rows.length
					+ '</span>条记录,您确定要<span style="color:' + color + ';font-weight:bold;">' + title + '</span>吗?',
					function(data) {
						if (data) {
							$.ajax({ url : "${ctx}/boss/customerInfo/updateStatus", type : "post", traditional : true,
								data : { "ids" : ids, "state" : state }, success : function(value) {
									dg.datagrid('reload');
								} });
						}
					});
		}

		function jumpToMainTabs(index) {
			$('#dg').datagrid('clearSelections');
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			mainTabs = parent.$("#mainTabs");
			addTab('采购商产品信息', 'boss/customerProductInfo/list?customerId=' + row.id);
		}
		function addTab(title, url) {
			if (mainTabs.tabs('exists', title)) {
				mainTabs.tabs('close', title);
			}
			var content = '<iframe scrolling="auto" frameborder="0"  src="' + url
					+ '" style="width:100%;height:100%;"></iframe>';
			mainTabs.tabs('add', { title : title, content : content, closable : true });

		}

		//导出excel
		function exportExcel() {
			parent.$.messager.show({ title : "提示", msg : "导出需要时间，请稍等片刻！", position : "bottomRight" });
			var url = "${ctx}/boss/customerInfo/exportExcel";
			window.location.href = url;
		}
		//发送采购商邮件
		function sendCusEmail() {
			var rows = dg.datagrid('getSelections');
			if (rows.length > 1) {
				alert("只能选择一位采购商进行发送");
				return;
			}

			var row = dg.datagrid('getSelected');
			if (row == null || row == undefined) {
				alert("请选择一个采购商");
				return;
			}
			var id = row.id;
			parent.$.messager.confirm('提示', '确定要发送帐号密码？', function(data) {
				if (data) {
					$.ajax({ url : "${ctx}/boss/customerInfo/sendEmail", data : { "customerId" : id }, type : "get",
						success : function(value) {
							alert("已放送成功");
						}, error : function(value) {
							alert("系统错误，请找技术人员");
						} });
				}
			});

		}

		//修改采购商密码
		function updatePaw() {
			var rows = dg.datagrid('getSelections');
			if (rows.length > 1) {
				alert("只能选择一位采购商进行发送");
				return;
			} else if (rows.length < 1) {
				alert("请选择一位采购商");
				return;
			}

			var row = dg.datagrid('getSelected');
			parent.$.messager.confirm('提示', '确定要修改采购商密码？', function(data) {
				if (data) {
					$.ajax({ url : "${ctx}/boss/customerInfo/updatePaw",
						data : { "customerInfo" : JSON.stringify(row) }, type : "post", success : function(value) {
							alert("新的采购商密码为:" + value);
						}, error : function(value) {
							alert("系统错误，请找技术人员");
						} });
				}
			});

		}
	</script>
</body>
</html>