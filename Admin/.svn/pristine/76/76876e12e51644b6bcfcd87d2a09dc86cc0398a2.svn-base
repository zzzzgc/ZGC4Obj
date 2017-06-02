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
 
       	        <input type="text" name="filter_EQI_customerId" class="easyui-validatebox" data-options="width:150,prompt: '客户ID'"/>
       	        <span class="toolbar-item dialog-tool-separator"></span>
       	        <input type="text" name="filter_EQS_orderKey" class="easyui-validatebox" data-options="width:150,prompt: '订单号'"/>
       	        <input type="text" name="filter_EQI_productId" class="easyui-validatebox" data-options="width:150,prompt: '产品编号'"/>
       	        <br/>
       	       <div style="height: 7px;"></div>
       	       
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		         <span class="toolbar-item dialog-tool-separator"></span>
		        <input type="reset" value="重置"/>
			</form>
			<div style="height: 7px;"></div>			
	      <%--  	<shiro:hasPermission name="tmall:product:add">
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="tmall:product:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="tmall:product:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"  onclick="upd()">修改</a>
	        </shiro:hasPermission> --%>
        </div> 
        
</div>
<table id="dg"></table> 
<div id="dlg"></div>  
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({    
	method: "get", 
    url:'${ctx}/boss/customerOrderUniue/customerList', 
    fit : true,
	fitColumns : true,
	border : false,
	striped:true,
	idField : 'customerId',
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 20,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:true,
    columns:[[    
		/* {field:'id',title:'id',hidden:true},   */
		{field:'customerId',title:'客户ID',width:100},
		{field:'orderKey',title:'订单号',width:100},
		{field:'addTime',title:'添加时间',width:100,
        	formatter:function(value,row,index){
        		if(value!=null){
        			var time=jsonTimeStamp(value);
               		return time;
        		}
           		return "";
           	 }}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tb'
});
});
//时间戳转化为日期格式yyyy-MM-dd HH:mm:ss
function jsonTimeStamp(milliseconds) {
    if (milliseconds != "" && milliseconds != null
            && milliseconds != "null") {
        var datetime = new Date();
        datetime.setTime(milliseconds);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0"
                + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate()
                : datetime.getDate();
        var hour = datetime.getHours() < 10 ? "0" + datetime.getHours()
                : datetime.getHours();
        var minute = datetime.getMinutes() < 10 ? "0"
                + datetime.getMinutes() : datetime.getMinutes();
        var second = datetime.getSeconds() < 10 ? "0"
                + datetime.getSeconds() : datetime.getSeconds();
        return year + "-" + month + "-" + date + " " + hour + ":" + minute
                + ":" + second;
    } else {
        return "";
    }
 
}
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

//弹窗修改
function upd(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '修改用户',    
	    width: 380,    
	    height: 250,    
	    href:'${ctx}/tmall/tmallproduct/update/'+row.id,
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