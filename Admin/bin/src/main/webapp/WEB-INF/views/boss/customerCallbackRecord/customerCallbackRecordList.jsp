<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>

</head>
<body style="font-family: '微软雅黑'">
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<form id="searchFrom" action="">

				<input type="text" name="filter_EQI_orderId" class="easyui-validatebox" data-options="width:150,prompt: '订单id'" />
				<input type="text" name="filter_EQS_orderKey" class="easyui-validatebox" data-options="width:150,prompt: '采购商订单号'" />
				<span class="toolbar-item dialog-tool-separator"></span>      
				  状态：<select  name="filter_EQI_status">
       	        	<option value="">请选择</option>
       	        	<c:forEach items="${applicationScope.sysParam.successFailMap}" var="successFailMap" >
		 				<option value="${successFailMap.key}">${successFailMap.value}</option>
		 			</c:forEach>
		 			</select>
				<%-- <br/>
		<br/>
       	              产品规格:
       	          <select  name="filter_EQS_spec"> 
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.specMap}" var="specMap" >
		 				<option value="${specMap.key}">${specMap.value}M</option>
		 			</c:forEach>
	 			</select>
       	        <span class="toolbar-item dialog-tool-separator"></span>
       	              使用区域:
       	          <select  name="filter_EQI_area">
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap" >
		 				<option value="${areaMap.key}">${areaMap.value}</option>
		 			</c:forEach>
	 			</select>
       	        <span class="toolbar-item dialog-tool-separator"></span>
       	              是否有效:
       	          <select  name="filter_EQI_valid">
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.isNoMap}" var="isNoMap" >
		 				<option value="${isNoMap.key}">${isNoMap.value}</option>
		 			</c:forEach>
		 			</select> --%>

				<br /> 
				<div style="height: 7px;"></div>
				<a href="javascript(0)" class="easyui-linkbutton"
					plain="true" iconCls="icon-search" onclick="cx()">查询</a> 
					<span class="toolbar-item dialog-tool-separator"></span> 
					<input type="reset" value="重置" />
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:customerCallbackRecord:add">
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="boss:customerCallbackRecord:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="boss:customerCallbackRecord:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"  onclick="upd()">修改</a>
	        </shiro:hasPermission>
		</div>

	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({    
	method: "get", 
    url:'${ctx}/boss/customerCallbackRecord/customerCallbackRecordList', 
    fit : true,
	fitColumns : true,
	border : false,
	striped:true,
	idField : 'id',
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 20,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:true,
    columns:[[    
		{field:'id',title:'id',hidden:true},  
		{field:'orderId',title:'订单id'},
		{field:'orderKey',title:'采购商订单号'},
		{field:'callbackAddress',title:'回调地址'},
		{field:'status',title:'状态',
		formatter:function(value,row,index){
         		if(value==1){
         			return "成功";
         		}else if(value==2){
         			return "失败";
         		}
          	}
        },
		{field:'callbackData',title:'回调数据'},
		{field:'callbackTimes',title:'回调次数'},
		{field:'callbackTime',title:'回调时间'}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tb'
});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '添加配置',    
	    width: 380,    
	    height: 250,    
	    href:'${ctx}/tmall/tmallproduct/create',
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'确认',
			handler:function(){
				$("#mainform").submit(); 
			}
		},{
			text:'取消',
			handler:function(){
					d.panel('close');
				}
		}]
	});
}

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/tmall/tmallproduct/delete/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '添加配置',    
	    width: 380,    
	    height: 250,    
	    href:'${ctx}/boss/customerCallbackRecord/create',
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'确认',
			handler:function(){
				$("#mainform").submit(); 
			}
		},{
			text:'取消',
			handler:function(){
					d.panel('close');
				}
		}]
	});
}

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/boss/customerCallbackRecord/delete/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

//弹窗修改
function upd(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '修改用户',    
	    width: 380,    
	    height: 250,    
	    href:'${ctx}/boss/customerCallbackRecord/update/'+row.id,
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'修改',
			handler:function(){
				$('#mainform').submit(); 
			}
		},{
			text:'取消',
			handler:function(){
					d.panel('close');
				}
		}]
	});
}

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

//格式化 关于钱的字段，例如单价，一口价等
function formaterMoney(value){
	var val=parseInt(value);
	var vale=val/100;
	val=vale.toFixed(3);
	return val;
}
</script>
</body>
</html>