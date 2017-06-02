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
				 <input type="text" name="filter_EQS_phone" class="easyui-validatebox" data-options="width:150,prompt: '手机'" /> 
				 <input type="text" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt: '姓名'" /> 
				 <input type="text" name="filter_LIKES_weixinId" class="easyui-validatebox" data-options="width:150,prompt: '企业微信帐号'" /> 
				<span class="toolbar-item dialog-tool-separator"></span> 
				状态：<select name="filter_EQI_status">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap">
						<option value="${freezeMap.key}">${freezeMap.value}</option>
					</c:forEach>
				</select> 
				角色：<select name="filter_EQI_role">
					<option value="">请选择</option>
					<c:forEach items="${applicationScope.sysParam.roleMap}" var="roleMap">
						<option value="${roleMap.key}">${roleMap.value}</option>
					</c:forEach>
				</select> 

<!-- 				<input id="d3" type="text" name="filter_GED_time" class="easyui-my97" startDate='%y-%M-%d 00:00:00' -->
<!-- 						datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '开始时间'" /> -->
<!-- 					-- -->
<!-- 					<input id="d4" type="text" name="filter_LED_time" class="easyui-my97" startDate='%y-%M-%d 23:59:59' -->
<!-- 						datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '结束时间'" /> -->

 			<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton" onclick="cx()">查询</a>
				 <span
					class="toolbar-item dialog-tool-separator"></span>
				<input type="reset" value="重置" onclick="clearCity()" />
				<span class="toolbar-item dialog-tool-separator"></span>
		        &nbsp;<input type="reset" value="批量开通" onclick="updateStatus(this,1)"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        &nbsp;<input type="reset" value="批量冻结" onclick="updateStatus(this,0)"/>
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:alarmLinkman:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:alarmLinkman:delete">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false"
					onclick="del()">删除</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="boss:alarmLinkman:update">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"
					onclick="upd()">修改</a>
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
						method : "get",
						url : '${ctx}/boss/alarmLinkman/alarmLinkmanList',
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
//									{ field : 'id', title : 'id', width : 100, hidden : true },
									{ field : 'ck', checkbox : true },
									{ field : 'name', title : '姓名', width : 100,sortable:true},		
									{ field : 'phone', title : '手机号', width : 100,sortable:true},		
									{ field : 'email', title : '邮箱', width : 100,sortable:true},		
									{ field : 'weixinId', title : '企业微信帐号', width : 100,sortable:true},		
									{ field : 'status', title : '状态', width : 100, formatter : function(value, row, index) {
										var returnVal = "";
										$.each(${applicationScope.sysParam.freezeMapJson}, function(key, val) {
											if (key == value) {
												returnVal = greenOrRed(key,val);
												return ;
											}
										});
										return returnVal;
									} },
									{ field : 'role', title : '角色', width : 100, formatter : function(value, row, index) {
										var returnVal = "";
										$.each(${applicationScope.sysParam.roleMapJson}, function(key, val) {
											if (key == value) {
												returnVal = val;
												return ;
											}
										});
										return returnVal;
									} },
									{ field : 'type', title : '类型', width : 100, formatter : function(value, row, index) {
										var returnVal = "";
										if(value==null||value.length==0){
											return "";
										}
										var arrays = value.split(",");
										var arrays2 =new Array("","天猫","微信","微信渠道","boss");
										for(var i=0;i<arrays.length;i++){
											if(returnVal==""){
												returnVal = arrays2[arrays[i]];
											}else {
												returnVal = returnVal+","+arrays2[arrays[i]];
											}
										}
										return returnVal;
									} },
							]
						], enableHeaderClickMenu : false, enableHeaderContextMenu : false,
						enableRowContextMenu : false, toolbar : '#tb' });
		});
		//弹窗增加
		function add() {
			d = $("#dlg").dialog(
					{ title : '添加配置', width : 380, height : 250, href : '${ctx}/boss/alarmLinkman/create',
						maximizable : true, modal : true, buttons : [
								{ text : '确认', handler : function() {
									$('#mainform').submit();
								} }, { text : '取消', handler : function() {
									d.panel('close');
								} }
						] });
		}
		//删除
		function del() {
			var rows = $('#dg').datagrid('getSelections');
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			var ids = new Array();
			for (var i = 0; i < rows.length; i++) {
				ids[i] = rows[i].id;
			}
			$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
				if (data) {
					$.ajax({ type : 'post', traditional : true, data : { "ids" : ids },
						url : "${ctx}/boss/alarmLinkman/delete", success : function(data) {
							dg.datagrid('clearSelections');
							successTip(data, dg);
						} });
				}
			});
		}
		//弹窗修改
		function upd() {
			var row = dg.datagrid('getSelected');
			if (rowIsNull(row))
				return;
			d = $("#dlg").dialog(
					{ title : '修改用户', width : 380, height : 250, href : '${ctx}/boss/alarmLinkman/update/' + row.id,
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
		//时间戳转化为日期格式yyyy-MM-dd HH:mm:ss
		function timeStamp2date(milliseconds) {
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
		//批量更新状态
		function updateStatus(html,state) {
			//获取选中的所有行
			var rows = $("#dg").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选中至少一行");
				return false;
			}
			var title = $(html).val();
			
			var ids = new Array();
			for (var i = 0; i < rows.length; i++) {
				ids[i] = rows[i].id;
			}
			var color = state==1?"green":"red";
			parent.$.messager.confirm('提示', '您确定要<span style="color:' + color + ';font-weight:bold;">' + title + '</span>？', function(data) {
				if (data) {
					$.ajax({ url : "${ctx}/boss/alarmLinkman/updateStatus", type : "post", traditional : true,
						data : { "ids" : ids, "state" : state }, success : function(value) {
							dg.datagrid('reload');
						} });
				}
			});
		}
	</script>
</body>
</html>