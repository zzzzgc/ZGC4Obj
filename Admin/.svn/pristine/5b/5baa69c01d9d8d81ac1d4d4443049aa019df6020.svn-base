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
				<input type="text" name="filter_INI_id" value="${bossOrderPhoneAndSizeRecord.id}" class="easyui-validatebox"
					data-options="width:60,prompt: 'id'" onchange="trimLeft(this)" />
				<input type="text" name="filter_LIKES_phone" value="${bossOrderPhoneAndSizeRecord.id}" class="easyui-validatebox"
					data-options="width:60,prompt: '手机号'" onchange="trimLeft(this)" />
				<input type="text" name="filter_INI_url" value="${bossOrderPhoneAndSizeRecord.id}" class="easyui-validatebox"
					data-options="width:60,prompt: '地址'" onchange="trimLeft(this)" />


				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" onclick="cx()">查询</a> <span class="toolbar-item dialog-tool-separator"></span>
				<!-- -->
				<input type="reset" value="重置" onclick="resetValue()" />
				<!-- 				<span class="toolbar-item dialog-tool-separator"></span> &nbsp; <a href="javascript(0)" class="easyui-linkbutton" -->
				<!-- 					onclick="alertWindowCommon(this,'${ctx}/boss/bossOrderPhoneAndSizeRecord/list?page=Discount','${ctx}/boss/bossOrderPhoneAndSizeRecord/alertWindow?method=discount',true)">修改折扣</a> -->

				<span class="toolbar-item dialog-tool-separator"></span> <a href="javascript(0)" class="easyui-linkbutton"
					onclick='handle(this,"${ctx}/boss/bossOrderPhoneAndSizeRecord/updateStatus",1,true)'>手工成功</a>
				<!-- -->
				<a href="javascript(0)" class="easyui-linkbutton"
					onclick='handle(this,"${ctx}/boss/bossOrderPhoneAndSizeRecord/updateStatus",2,true)'>手工失败</a>
				<!-- -->
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:bossOrderPhoneAndSizeRecord:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
					onclick="addCommon('${ctx}/boss/bossOrderPhoneAndSizeRecord/list?page=Add');">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossOrderPhoneAndSizeRecord:delete">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
					onclick="del('bossOrderPhoneAndSizeRecord/delete')">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossOrderPhoneAndSizeRecord:update">
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
					onclick="updCommon('${ctx}/boss/bossOrderPhoneAndSizeRecord/list?page=Update')">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:bossOrderPhoneAndSizeRecord:exportExcel">
				<!--			<span class="toolbar-item dialog-tool-separator"></span> -->
				<!--			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel" plain="true" onclick="exportExcel('bossOrderPhoneAndSizeRecord/exportExcel')">导出Excel</a> -->
			</shiro:hasPermission>


		</div>

	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
		var dg;
		$(function() {
			dg = $('#dg').datagrid(
					{
						method : 'POST',
						url : '${ctx}/boss/bossOrderPhoneAndSizeRecord/bossOrderPhoneAndSizeRecordList',
						fit : true,
						fitColumns : true,//自动填充,如果字段过多就改为false
						queryParams : $("#searchFrom").serializeObject(),
						border : false,
						striped : true,
						idField : 'id',
						pagination : true,
						rownumbers : true,
						queryParams : {},
						pageNumber : 1,
						pageSize : 20,
						pageList : [
								10, 20, 30, 40, 50
						],
						singleSelect : false,
						columns : [
							[
									{ field : 'ck', checkbox : true },
									{ field : 'id', title : 'id', width : 80, sortable : true },
									{ field : 'orderId', title : '订单id', width : 100, sortable : true },
									{ field : 'phone', title : '手机号', width : 100, sortable : true },
									{ field : 'size', title : '规格', width : 100, sortable : true },
									{ field : 'status', title : '状态', formatter : function(value, row, index) {
										var returnVal = "";

										if (value == 0) {
											returnVal = '未处理';
										} else if (value == 1) {
											returnVal = '<span style="color:green;font-weight:bold">成功</span>';
										} else if (value == 2) {
											returnVal = '<span style="color:fail;font-weight:bold">失败</span>';
										} else if (value == 3) {
											returnVal = '<span style="color:blue;font-weight:bold">处理中</span>';
										} else if(value==4){
											returnVal = '<span style="color:pink;font-weight:bold">作废</span>';
										}

										return returnVal;
									} },
									// 											{ field : 'url', title : '地址', width : 100, sortable : true },
									{
										field : 'itemid',
										title : '操作',
										formatter : function(value, row, index) {
											var returnVal = "";
											if (row.url != null && row.url != "") {
												returnVal = '<a href="' + row.url
														+ '" target="_blank" onclick="changeStatus(' + index
														+ ')">支付</a>';
												// 												rreturnVal = '<a href="#" iconCls="icon-add" onclick="jumpToMainTabs('
												// 														+ index + ')">' + '支付' + '</a>';
											}
											return returnVal;
										} },
									{ field : 'addTime', title : '添加时间', width : 130, sortable : true,
										formatter : function(value, row, index) {
											return long2date(value);
										} },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});
		function changeStatus(index) {
			$('#dg').datagrid('clearSelections');
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			var ids = new Array();
			ids[0] = row.id;
			if (row.status == 0) {
				$.ajax({ url : "${ctx}/boss/bossOrderPhoneAndSizeRecord/updateStatus", type : "post",
					traditional : true, data : { "ids" : ids, "status" : 3 }, success : function(value) {
						if (value == "success") {
							dg.datagrid('reload');
						} else
							$.messager.alert(value);
					} });
			} else {
			}
		}
		function addTab(title, url) {
			if (mainTabs.tabs('exists', title)) {
				mainTabs.tabs('close', title);
			}
			var content = '<iframe scrolling="auto" frameborder="0"  src="' + url
					+ '" style="width:100%;height:100%;"></iframe>';
			mainTabs.tabs('add', { title : title, content : content, closable : true });

		}
		function handle(html, url, status, isNeed, obj) {

			obj = JSON.stringify(obj);
			// 		console.debug(obj);
			var rows = $("#dg").datagrid("getSelections");
			var yixuan = "";
			if (isNeed == null && rows.length != 1) {
				$.messager.alert("请只选择一行");
				return false;
			}
			var color = (status == 0 || status == 2) ? "red" : "green";
			if (isNeed == true || isNeed == undefined) {
				if (rows.length == 0) {
					$.messager.alert("请选中至少一行");
					return false;
				}
				yixuan = '已选<span style="color:' + color + ';font-weight:bold;">' + rows.length + '</span>条记录,';
			}

			var ids = new Array();
			for (var i = 0; i < rows.length; i++) {
				ids[i] = rows[i].id;
				if (rows[i].status != 3) {
					$.messager.alert("只能选择处理中的订单");
					return;
				}
			}
			var isSuccess = status == 1 ? true : false;
			handleOrderInfo(isSuccess);
			var title = $(html).text();
			parent.$.messager.confirm('提示', yixuan + '您确定要<span style="color:' + color + ';font-weight:bold;">' + title
					+ '</span>吗?', function(data) {
				if (data) {
					$.ajax({ url : url, type : "post", traditional : true,
						data : { "ids" : ids, "status" : status, "obj" : obj }, success : function(value) {
							if (value == "success") {
								dg.datagrid('reload');
							} else
								$.messager.alert(value);
						} });
				}
			});
		}

		function handleOrderInfo(isSuccess) {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			// 			var t= (isSuccess?"手工成功":"手工失败");
			// 			var title = '已选<span style="color:'
			// 					+ (isSuccess ? 'green' : 'red')
			// 					+ ';font-weight:bold;">'
			// 					+ rows.length
			// 					+ '</span>条记录,您确定要<span style="color:green;font-weight:bold"> + t +</span>?';
			// 			parent.$.messager.confirm('提示', title, function(data) {
			// 				if (data) {
			var ids = new Array();
			for (var i = 0; i < rows.length; i++) {
				ids[i] = rows[i].orderId;
			}
			var action = (isSuccess ? 'toSuccess' : 'toFail');
			$.ajax({ url : "${ctx}/boss/orderInfo/handleOrder", type : "post", traditional : true,
				data : { ids : ids, action : action }, success : function(data) {
				} });
			// 				}
			// 			});
		}
	</script>
</body>
</html>