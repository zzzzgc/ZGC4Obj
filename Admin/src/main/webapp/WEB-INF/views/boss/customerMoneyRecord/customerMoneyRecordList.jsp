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
				<!-- 		采购商查询 start	 -->
					<input type="text" name="filter_EQI_customerId" class="easyui-validatebox" data-options="width:60,prompt: '采购商id'" />
					
					<input type="text" name="customerName" class="easyui-validatebox" data-options="width:150,prompt: '采购商名称'" />
					<span class="toolbar-item dialog-tool-separator"></span> 采购商：<select name="customerId" onchange="getCustomerIdBySelect()">
						<option value="">请选择</option>
						<c:forEach items="${applicationScope.sysParam.customerInputMap}" var="customerInputMap">
							<option value="${customerInputMap.key}">${customerInputMap.value.customerName}</option>
						</c:forEach>
					</select>
					<input type="hidden" name="filter_INI_customerId" class="easyui-validatebox" data-options="width:100,prompt: '采购商id'" />
					<!---		采购商查询 end		 -->
				<input type="text" name="filter_LEM_fundBalance" class="easyui-validatebox" data-options="width:150,prompt: '资金余额'" />
				<input type="text" name="filter_LEM_cost" class="easyui-validatebox" data-options="width:150,prompt: '发生金额'" />
				<span class="toolbar-item dialog-tool-separator"></span> 
			        收支类型：<select  name="filter_EQI_costType">
       	        	<option value="">请选择</option>
	 				<c:forEach items="${applicationScope.sysParam.costTypeMap}" var="costTypeMap" >
		 				<option value="${costTypeMap.key}">${costTypeMap.value}</option>
		 			</c:forEach>
		 			</select>
		 			<span class="toolbar-item dialog-tool-separator"></span> 
				<input id="d1" type="text" name="filter_GED_recordTime"  class="easyui-my97" startDate='%y-%M-%d 00:00:00' datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '起始时间'" />--
	 			<input id="d2" type="text" name="filter_LED_recordTime"  class="easyui-my97" startDate='%y-%M-%d 23:59:59' datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '结束时间'"/>
				<br />
				<div style="height: 7px;"></div>
				 <a href="javascript(0)" class="easyui-linkbutton"
					plain="true" iconCls="icon-search" onclick="cx()">查询</a>
					<span class="toolbar-item dialog-tool-separator"></span> 
					<input type="reset" value="重置" onclick="resetTime()"/>
			</form>
			<div style="height: 7px;"></div>
	<%-- 		<shiro:hasPermission name="boss:customerMoneyRecord:add">
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="boss:customerMoneyRecord:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="boss:customerMoneyRecord:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-options="disabled:false"  onclick="upd()">修改</a>
	        </shiro:hasPermission>  --%>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-excel"  plain="true" data-options="disabled:false" onclick="exportExcel()">导出Excel</a>
	        
		</div>

	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({    
	method: "get", 
    url:'${ctx}/boss/customerMoneyRecord/customerMoneyRecordList', 
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
		{field:'customerId',title:'采购商Id',width:100,
			formatter:function(value,row,index){
				var value = row.customerId;
				return value;
       	 	}
		},
		{field:'customerName',title:'采购商名称',width:100,
			formatter:function(value,row,index){
				var value = row.customerId;
				var $provider=${applicationScope.sysParam.customerInfoMap};
				var retrunVal="";
				$.each($provider,function(key,val){
					if(key==value){
						retrunVal= val.customerName; 
					}
				});
				return retrunVal;
       	 	}
		},
		{field:'fundBalance',title:'资金余额',width:100},
		{field:'orderKey',title:'流水号',sortable:true,width:100},
		{field:'phone',title:'充值号码',sortable:true,width:100},
		{field:'costType',title:'收支类型',width:100,
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
         		}else if(value==6){
         			return "授信";
         		}
         		
         	
          	}
        },
		{field:'remark',title:'备注',width:100},
		{field:'price',title:'单价',sortable:true,width:100},
		{field:'cost',title:'发生金额',width:100},
		{field:'recordTime',title:'时间',width:100,
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
	    href:'${ctx}/boss/customerMoneyRecord/create',
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
				url:"${ctx}/boss/customerMoneyRecord/delete/"+row.id,
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
	    href:'${ctx}/boss/customerMoneyRecord/update/'+row.id,
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
	getCustomerId();
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

//导出excel
function exportExcel(){
	var obj=$("#searchFrom").serializeObject();
	var params = "?";
	for(var name in obj){         
		params+=name+"="+obj[name]+"&&";    
    }    
	params = params.substring(0, params.length-2);
	parent.$.messager.show({ title : "提示",msg: "导出需要时间，请稍等片刻！", position: "bottomRight" });
	var url = "${ctx}/boss/customerMoneyRecord/exportExcel"+params;
	window.location.href = url;
}

function resetTime(){
	$("input[name='filter_GED_recordTime']").val("");
	$("input[name='filter_LED_recordTime']").val("");
}
</script>
</body>
</html>