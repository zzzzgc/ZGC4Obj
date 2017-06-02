<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
</head>
<body class="easyui-layout" style="font-family: '微软雅黑'">
	<div data-options="region:'center',split:true,border:false,title:'列表'">
		<div id="tb" style="padding:5px;height:auto">
			<div>
				<form id="searchFrom" action="">

					<select name="filter_INI_status">
						<option value="">上架状态</option>
						<option value="1">使用中</option>
						<option value="2">已下架</option>
					</select> <select name="filter_INI_productType">
						<option value="">产品类型</option>
						<option value="1">流量包</option>
						<option value="2">话费</option>
					</select> <select name="filter_INI_productUnit">
						<option value="">规格</option>
						<option value="1">M</option>
						<option value="2">G</option>
						<option value="3">元</option>
					</select> <select name="filter_INI_area">
						<option value="">使用区域</option>
						<option value="0">本省</option>
						<option value="1">全国</option>
					</select> <select name="filter_INI_isLimit">
						<option value="">是否限价</option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
					<input type="text" name="filter_INI_id" class="easyui-validatebox" data-options="width:100,prompt: 'id'" />
					<input type="text" name="filter_LIKES_categoryName" class="easyui-validatebox" data-options="width:100,prompt: '分类名称'" />
					<input type="text" name="filter_INI_productNum" class="easyui-validatebox" data-options="width:100,prompt: '产品规格'" />
					<input type="text" name="filter_LIKES_operator" class="easyui-validatebox" data-options="width:100,prompt: '运营商'" />
					<input type="text" name="filter_LIKES_province" class="easyui-validatebox" data-options="width:100,prompt: '省份'" />
					<div style="height: 7px;"></div>
					<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a> <span
						class="toolbar-item dialog-tool-separator"></span>
					<input type="reset" value="重置" onclick="clearCity()" />
				</form>

				<div style="height: 7px;"></div>
				<shiro:hasPermission name="boss:productCategoryInfo:add">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
					<span class="toolbar-item dialog-tool-separator"></span>
				</shiro:hasPermission>
				<shiro:hasPermission name="boss:productCategoryInfo:delete">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false"
						onclick="del()">删除</a>
					<span class="toolbar-item dialog-tool-separator"></span>
				</shiro:hasPermission>
				<shiro:hasPermission name="boss:productCategoryInfo:update">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"
						onclick="upd()">修改</a>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="dg"></table>
	</div>
	<div id="dlg"></div>
	<script type="text/javascript">
		var dg; //角色datagrid
		var d; //弹窗
		var queryCondition; //省份地区运营商 查询条件
		$(function() {
			dg = $('#dg').datagrid(
					{
						method : "get",
						url : '${ctx}/boss/productCategoryInfo/productCategoryInfoList',
						fit : true,
						fitColumns : true,
						border : false,
						idField : 'id',
						pagination : true,
						rownumbers : true,
						pageNumber : 1,
						pageSize : 30,
						pageList : [
								10, 20, 30, 40, 50
						],
						/* singleSelect:true, */
						striped : true,
						columns : [
							[
									{ checkbox : true },
									{ field : 'id', title : 'id' },
									{ field : 'categoryName', title : '分类名称', width : 130 },
									/* {field:'status',title:'上架状态',width:100,
										formatter:function(value,row,index){
									 		if(value==1){
									 			return "使用中";
									 		}else if(value==2){
									 			return "已下架";
									 		}
									  	}
									}, */
									{ field : 'productNum', title : '产品规格', width : 100 },
									{ field : 'productUnit', title : '产品单位', width : 100,
										formatter : function(value, row, index) {
											if (value == 1) {
												return "M";
											} else if (value == 2) {
												return "G";
											} else if (value == 3) {
												return "元";
											}
										} },
									{ field : 'operator', title : '运营商', width : 100 },
									{ field : 'standarPrice', title : '标准价', width : 100,
										formatter : function(value, row, index) {
											if (value != null) {
												return value.toFixed(3);
											}
										} },
									{ field : 'province', title : '所属省份', width : 100 },
									{ field : 'area', title : '使用地区', width : 100,
										formatter : function(value, row, index) {
											if (value == 1) {
												return "全国";
											} else if (value == 0) {
												return "本省";
											} else {
												return "错误代码:" + value;
											}
										} },
									{ field : 'productType', title : '产品类型', width : 100,
										formatter : function(value, row, index) {
											if (value == 1) {
												return "流量包";
											} else if (value == 2) {
												return "话费";
											}
										} },
									{ field : 'isLimit', title : '是否限价', width : 100,
										formatter : function(value, row, index) {
											var returnVal = "";
											$.each(${applicationScope.sysParam.isNoMapJson}, function(key, val) {
												if (key == value) {
													returnVal = greenOrRed(key, val);
													return;
												}
											});
											return returnVal;
										} },
									{
										field : 'itemid2',
										title : '操作',
										width : 100,
										formatter : function(value, row, index) {
											var title = "'采购商产品信息'";
											var url = "'boss/customerProductInfo/list?categoryId='";
											return '<a href="#" iconCls="icon-add" onclick="jumpToMainTabs(' + index
													+ ',' + title + ',' + url + ')">采购商产品信息</a>';
										} },
									{
										field : 'itemid',
										title : '操作',
										width : 100,
										formatter : function(value, row, index) {
											var title = "'供应商产品信息'";
											var url = "'boss/providerProductInfo/list?categoryId='";
											return '<a href="#" iconCls="icon-add" onclick="jumpToMainTabs(' + index
													+ ',' + title + ',' + url + ')">供应商产品信息</a>';
										} },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });

		});
		/**
		 * 格式化状态
		 */
		function formatState(value, row) {
			switch (value) {
			case 1:
				return '正常';
			case 0:
				return '冻结';
			}
		}
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
						href : '${ctx}/boss/productCategoryInfo/create',
						maximizable : true,
						modal : true,
						buttons : [
								{
									text : '确认',
									handler : function() {
										$('#mainform').form(
												'submit',
												{ url : '${ctx}/boss/productCategoryInfo/add',
													success : function(data) {
														dg.datagrid('reload');
														queryCondition.treegrid('reload');
														successTip(data, dg);
														d.panel('close');
													} });
									} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//删除
		function del() {
			var rows = dg.datagrid('getSelections');
			if (rows.length == 0 || rows.length > 1) {
				parent.$.messager.show({ title : "提示", msg : "请只选择一项！", position : "bottomRight" });
				return;
			}
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
				if (data) {
					$.ajax({ type : 'get', url : "${ctx}/boss/productCategoryInfo/delete/" + rows[0].id,
						success : function(data) {
							dg.datagrid("clearSelections");
							queryCondition.treegrid('reload');
							successTip(data, dg);
						} });
				}
			});
		}

		//弹窗修改
		function upd() {
			console.debug("aaaaaa");
			var rows = dg.datagrid('getSelections');
			if (rows.length == 0 || rows.length > 1) {
				parent.$.messager.show({ title : "提示", msg : "请只选择一项！", position : "bottomRight" });
				return;
			}
			d = $("#dlg").dialog(
					{ title : '修改用户', width : 380, height : 250,
						href : '${ctx}/boss/productCategoryInfo/update/' + rows[0].id, maximizable : true,
						modal : true, buttons : [
								{ text : '修改', handler : function() {
									$('#mainform').submit();
									successTip(data, dg);
									queryCondition.treegrid('reload');
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}
		//弹窗增加
		function add1() {
			d = $("#dlg").dialog(
					{ title : '添加配置', width : 380, height : 250,
						href : '${ctx}/boss/productCategoryInfo/create?flag=1', maximizable : true, modal : true,
						buttons : [
								{ text : '确认', handler : function() {
									$("#mainform").submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}

		//删除
		function del1() {
			var row = queryCondition.datagrid('getSelected');
			var child = queryCondition.treegrid('getChildren', $(row).attr("id"));
			if (child.length != 0) {
				$.messager.alert("只能删除子节点!");
			} else {
				if (rowIsNull(row))
					return;
				parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
					if (data) {
						$.ajax({ type : 'get', url : "${ctx}/boss/productCategoryInfo/delete/" + row.id,
							success : function(data) {
								successTip(data, dg);
								queryCondition.treegrid('reload');
							} });
					}
				});
			}
		}

		//弹窗修改
		function upd1() {
			var row = queryCondition.datagrid('getSelected');
			if (rowIsNull(row))
				return;
			d = $("#dlg").dialog(
					{ title : '修改用户', width : 380, height : 250,
						href : '${ctx}/boss/productCategoryInfo/update1/' + row.id, maximizable : true, modal : true,
						buttons : [
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
			dg.datagrid({ queryParams : obj, url : '${ctx}/boss/productCategoryInfo/productCategoryInfoList' });
		}

		//格式化 关于钱的字段，例如单价，一口价等
		function formaterMoney(value) {
			var val = parseInt(value);
			var vale = val / 100;
			val = vale.toFixed(3);
			return val;
		}
		//批量更新状态
		function updateStatus(state) {
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

			$.ajax({ url : "${ctx}/boss/productCategoryInfo/updateStatus", type : "post", traditional : true,
				data : { "ids" : ids, "state" : state }, success : function(value) {
					dg.datagrid('reload');
				} });
		}
		//跳到供应商页面
		function jumpToMainTabs(index, title, url) {
			$('#dg').datagrid('clearSelections');
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			mainTabs = parent.$("#mainTabs");
			addTab(title, url + row.id + "");
		}
		function addTab(title, url) {
			if (mainTabs.tabs('exists', title)) {
				mainTabs.tabs('close', title);
			}
			var content = '<iframe scrolling="auto" frameborder="0"  src="' + url
					+ '" style="width:100%;height:100%;"></iframe>';
			mainTabs.tabs('add', { title : title, content : content, closable : true });

		}
	</script>
</body>
</html>