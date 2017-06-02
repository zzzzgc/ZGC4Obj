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
			<input type="hidden" id="flag" name="flag" value="${flag}" class="easyui-validatebox"
				data-options="width:150,prompt: '判断有没有传id'" />
			<form id="searchFrom" action="">
				<input type="text" name="filter_EQI_id" class="easyui-validatebox" data-options="width:150,prompt: '供应商Id'" />
				<input type="text" name="filter_LIKES_providerName" class="easyui-validatebox" data-options="width:150,prompt: '供应商名称'" />
				<span class="toolbar-item dialog-tool-separator"></span> 运营商: <select name="filter_EQS_operator">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerStr">
						<option value="${providerStr.value}">${providerStr.value}</option>
					</c:forEach>
				</select> <span class="toolbar-item dialog-tool-separator"></span> 状态: <select name="filter_EQI_status">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap">
						<option value="${freezeMap.key}">${freezeMap.value}</option>
					</c:forEach>
				</select> <br />
				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a> <span
					class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="reset" value="重置" onclick="clearCity1()" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="reset" value="批量开通" onclick="updateStatus(this,1)" />
				<span class="toolbar-item dialog-tool-separator"></span> &nbsp;
				<input type="reset" value="批量冻结" onclick="updateStatus(this,0)" />
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:providerInfo:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:providerInfo:delete">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false"
					onclick="del()">删除</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:providerInfo:update">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"
					onclick="upd()">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:providerInfo:addBalance">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"
					onclick="addBalance()">注资</a>
			</shiro:hasPermission>
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
						url : '${ctx}/boss/providerInfo/providerInfoList',
						fit : true,
						fitColumns : true,
						border : false,
						striped : true,
						idField : 'id',
						queryParams : { filter_EQI_id : $("#flag").val() },
						pagination : true,
						rownumbers : true,
						pageNumber : 1,
						pageSize : 20,
						pageList : [
								10, 20, 30, 40, 50
						],
						/* singleSelect:true, */
						columns : [
							[
									{ checkbox : true },
									{ field : 'id', title : 'id' },
									{ field : 'operator', title : '运营商' },
									{ field : 'province', title : '省份' },
									{ field : 'providerName', title : '供应商名称' },
									{ field : 'supplier', title : '供应商简称' },
									{ field : 'providerNumber', title : '供应商联系号码' },
									{ field : 'providerContact', title : '供应商联系人' },
									{ field : 'providerAddress', title : '供应商联系地址' },
									{ field : 'discount', title : '折扣率' },
									{ field : 'balance', title : '剩余可用余额', formatter : function(value, row, index) {
										if (value == null) {
											return '<span style="color:red;font-weight:bold;">资金余额不正常</span>';
										} else if (value >= 0) {
											return '<span style="color:green;font-weight:bold;">' + value + '</span>';
										} else if (value < 0) {
											return '<span style="color:red;font-weight:bold;">' + value + '</span>';
										}
									} },
									{ field : 'alarmBalance', title : '警报阀值' },
									/* {field:'city',title:'城市'}, */
									{ field : 'isCallback', title : '是否回调', formatter : function(value, row, index) {
										if (value == 1) {
											return "会回调";
										} else if (value == 0) {
											return "不会回调";
										}
									} },
									{ field : 'status', title : '状态', formatter : function(value, row, index) {
										if (value == 1) {
											return '<span style="color:green;font-weight:bold;">' + "正常" + '</span>';
										} else if (value == 0) {
											return '<span style="color:red;font-weight:bold;">' + "冻结" + '</span>';
										}
									} },
									{ field : 'addTime', title : '添加时间', formatter : function(value, row, index) {
										if (value != null) {
											var time = jsonTimeStamp(value);
											return time;
										}
										return "";
									} },
									{
										field : 'itemid',
										title : '操作',
										formatter : function(value, row, index) {
											return '<a href="#" iconCls="icon-add" onclick="jumpToMainTabs(' + index
													+ ')">产品信息</a>';
										} }, { field : 'forbinCustomer', title : '禁止采购商' },
									{ field : 'allowCustomer', title : '只允许的采购商' }, { field : 'remark', title : '备注' }
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
					{ title : '添加配置', width : 380, height : 250, href : '${ctx}/boss/providerInfo/create',
						maximizable : true, modal : true, buttons : [
								{ text : '确认', handler : function() {
									$("#mainform").submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//删除
		function del() {
			var row = dg.datagrid('getSelected');
			if (rowIsNull(row))
				return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
				if (data) {
					$.ajax({ type : 'get', url : "${ctx}/boss/providerInfo/delete/" + row.id, success : function(data) {
						successTip(data, dg);
						dg.datagrid('uncheckAll');
					} });
				}
			});
		}

		//弹窗修改
		function upd() {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			if (rows.length > 1) {
				alert("只能选择一行进行修改");
				return false;
			}

			var row = dg.datagrid('getSelected');
			if (rowIsNull(row))
				return;
			d = $("#dlg").dialog(
					{ title : '修改用户', width : 380, height : 250, href : '${ctx}/boss/providerInfo/update/' + row.id,
						maximizable : true, modal : true, buttons : [
								{ text : '修改', handler : function() {
									$('#mainform').submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//创建查询对象并查询
		function cx() {
			var obj = $("#searchFrom").serializeObject();
			dg.datagrid('reload', obj);
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
							
							
							$.ajax({ url : "${ctx}/boss/providerInfo/updateStatus", type : "post", traditional : true,
								data : { "ids" : ids, "state" : state }, success : function(value) {
									dg.datagrid("clearSelections");
									dg.datagrid('reload');
								} });
							
							
						}
					});
		}

		//弹窗添加注资
		function addBalance() {
			var row = dg.datagrid('getSelected');
			if (rowIsNull(row))
				return;
			d = $("#dlg").dialog(
					{ title : '增加注资', width : 380, height : 300,
						href : '${ctx}/boss/providerBalanceRecord/addProvider/' + row.id, maximizable : true,
						modal : true, buttons : [
								{ text : '添加', handler : function() {
									$('#mainform').submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		function jumpToMainTabs(index) {
			$('#dg').datagrid('clearSelections');
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			mainTabs = parent.$("#mainTabs");
			addTab('供货商产品信息', 'boss/providerProductInfo/list?providerId=' + row.id);
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
			var url = "${ctx}/boss/providerInfo/exportExcel";
			window.location.href = url;
		}
	</script>
</body>
</html>