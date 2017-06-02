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
				<input id="d1" type="text" name="filter_GED_addTime"  class="easyui-my97" startDate='%y-%M-%d 00:00:00' datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '注资开始时间'" />--
	 			<input id="d2" type="text" name="filter_LED_addTime"  class="easyui-my97" startDate='%y-%M-%d 23:59:59' datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '注资结束时间'"/>
				<input type="text" name="filter_GED_auditTime"  class="easyui-my97" startDate='%y-%M-%d 00:00:00' datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '审核开始时间'" />--
	 			<input  type="text" name="filter_LED_auditTime"  class="easyui-my97" startDate='%y-%M-%d 23:59:59' datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '审核结束时间'"/>
					<!-- 		供应商查询 start	 -->
					<input type="text" name="filter_EQI_providerId" class="easyui-validatebox" data-options="width:60,prompt: '供应商id'" />
					<input type="text" name="providerName" class="easyui-validatebox" data-options="width:150,prompt: '供应商名称'"  />
					<span class="toolbar-item dialog-tool-separator"></span> 供应商：<select name="providerId" onchange="getProviderIdBySelect()">
						<option value="">请选择</option>
						<c:forEach items="${applicationScope.sysParam.providerInputMap}" var="providerInputMap">
							<option value="${providerInputMap.key}">${providerInputMap.value.providerName}</option>
						</c:forEach>
					</select>
					<input type="hidden" name="filter_INI_id" class="easyui-validatebox" data-options="width:100,prompt: 'id'" />
					<input type="hidden" name="filter_INI_providerId" class="easyui-validatebox" data-options="width:100,prompt: '供应商id'" />
					<!---		供应商查询 end		 -->
       	        <span class="toolbar-item dialog-tool-separator"></span> 
				 审核状态：<select name="filter_INI_status" >
						<option value="">请选择</option>
						<option value="0">未审核</option>
						<option value="1">审核通过</option>
						<option value="2">审核不通过</option>
					</select>

				<br /><div style="height: 7px;"></div>
				 <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
					<span class="toolbar-item dialog-tool-separator"></span> 
					<shiro:hasPermission name="boss:providerBalanceRecord:audit">
					 <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="sh()">审核</a>
					 </shiro:hasPermission>
					<span class="toolbar-item dialog-tool-separator"></span> 
					<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="add()">添加注资</a>
					<span class="toolbar-item dialog-tool-separator"></span> 
					<input type="reset" value="重置" onclick="resetTime()"/>
			</form>
			<div style="height: 7px;"></div>

		</div>

	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
var dg;
var d;
$(function(){   
	var providerName=${applicationScope.sysParam.providerInfoMap};
	dg=$('#dg').datagrid({    
	method: "get", 
    url:'${ctx}/boss/providerBalanceRecord/balanceRecordList', 
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
		{field:'id',title:'id'},  
		{field:'providerId',title:'供应商id',width:100},
		{field:'providerName',title:'供应商',sortable:false,width:100,
			formatter:function(value,row,index){
				var value = row.providerId;  
				var retrunVal="";
				$.each(providerName,function(key,val){
					if(key==value){
						retrunVal= val.providerName; 
					}
				});
				return retrunVal;
          	}
		},
		{field:'balance',title:'注资金额',width:100},
		{field:'status',title:'审核状态',width:100,
			formatter:function(value,row,index){
         		if(value==0){
         			return "未审核";
         		}else if(value==1){
         			return "<span class='green'>审核通过</span>";
         		}else if(value==2){
         			return "<span class='red'>审核不通过</span>";
         		}
          	}	
		},
		{field:'type',title:'收支类型',width:100,
		formatter:function(value,row,index){
         		if(value==1){
         			return "人工注资";
         		}else if(value==2){
         			return "系统注资";
         		}else if(value==3){
         			return "消费";
         		}else if(value==4){
         			return "失败返还";
         		}else if(value==5){
         			return "人工扣款";
         		}
          	}
        },
        {field:'remark',title:'备注',width:100},
		{field:'addUser',title:'注资人',width:100},
		{field:'addTime',title:'注资时间',width:100},
		{field:'auditUser',title:'审核人',width:100},
		{field:'auditTime',title:'审核时间',width:100}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tb'
});	
});


//弹窗审核
function sh(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '审核注资',    
	    width: 600,    
	    height: 600,    
	    href:'${ctx}/boss/providerBalanceRecord/audit/'+row.id,
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'审核',
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

//弹窗添加注资
function add(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '增加注资',    
	    width: 380,    
	    height: 300,    
	    href:'${ctx}/boss/providerBalanceRecord/addProvider/'+row.providerId,
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'添加',
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
	getProviderId();
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

function resetTime(){
	$("input[name='filter_GED_addTime']").val("");
	$("input[name='filter_LED_addTime']").val("");
}
</script>
</body>
</html>