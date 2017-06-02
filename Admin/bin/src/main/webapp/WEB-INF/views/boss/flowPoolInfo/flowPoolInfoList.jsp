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
        		 <input type="text" name="filter_EQS_poolNum" class="easyui-validatebox" data-options="width:150,prompt: '流量池编号'" /> 
        		 <input type="text" name="filter_EQI_poolRemain" class="easyui-validatebox" data-options="width:150,prompt: '流量池剩余量'" /> 
		 		<span class="toolbar-item dialog-tool-separator"></span>
        		供应商：<select id="filter_EQI_providerId" name="filter_EQI_providerId" >
       	        	<option value="">请选择</option>
       	        	<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap" >
		 				<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
		 			</c:forEach>
		 			</select>
		 			   状态:
       	          <select  name="filter_EQI_status">
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.freezeMap}" var="freezeMap" >
		 				<option value="${freezeMap.key}">${freezeMap.value}</option>
		 			</c:forEach>
	 			</select>
       	        <span class="toolbar-item dialog-tool-separator"></span>
	        	<div style="height: 7px;"></div>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <input type="reset" value="重置" onclick="clearCity()"/>
			</form>
			<div style="height: 7px;"></div>			
	       	<shiro:hasPermission name="boss:flowPoolInfo:add">
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="boss:flowPoolInfo:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="boss:flowPoolInfo:update">
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
    url:'${ctx}/boss/flowPoolInfo/flowPoolInfoList', 
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
	singleSelect:false,
    columns:[[    
		{field:'id',title:'id',width:100,hidden:true},   
		{field:'providerId',title:'供应商名称',width:100,
			formatter:function(value,row,index){
				//获取供货商的列表
				var $provider=${applicationScope.sysParam.providerInfoMap};
				var providerName = "";
				$.each($provider,function(key,val){
					if(key==row.providerId){
						providerName = val.providerName;
						return ; 
					}
				});
			    return	providerName;
       	 	}
		},
		{field:'poolNum',title:'流量池编号',width:100},
		{field:'poolRemain',title:'流量池剩余量',width:100},
		{field:'status',title:'状态',width:100,
			formatter:function(value,row,index){
				if(value==0){
					return "冻结";
				}else if(value==1){
					return "正常";
				}
				return value;
			}
		},
		{field:'lastUpdateTime',title:'最后更新时间',width:100},
		{field:'remark',title:'备注',width:100}
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
	    href:'${ctx}/boss/flowPoolInfo/create',
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'确认',
			handler:function(){
				$.ajax({
					data:$("#mainform").serializeObject(),
					type:'post',
					url:"${ctx}/boss/flowPoolInfo/add",
					success: function(data){
						successTip(data,dg,d);
					}
				});
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
	var rows = dg.datagrid('getSelections');
	if(rows.length!=1){
		parent.$.messager.show({ title : "提示",msg: "请选择一项！", position: "bottomRight" });
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/boss/flowPoolInfo/delete/"+rows[0].id,
				success: function(data){
					successTip(data,dg);
					dg.datagrid('uncheckAll');
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
	    href:'${ctx}/boss/flowPoolInfo/update/'+row.id,
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'修改',
			handler:function(){
				$.ajax({
					data:$("#mainform").serializeObject(),
					type:'post',
					url:"${ctx}/boss/flowPoolInfo/update",
					success: function(data){
						successTip(data,dg,d);
					}
				});
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



</script>
</body>
</html>