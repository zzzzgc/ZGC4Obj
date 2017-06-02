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
			        运营商：<select name="filter_EQS_operator" >
				 <option value="">请选择</option>
			 			<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap" >
			 				 <option value="${providerMap.value}">${providerMap.value}</option>
			 			</c:forEach>
		 		</select>
		 			<span class="toolbar-item dialog-tool-separator"></span>
		 		  状态：<select  name="filter_EQI_type">
       	        	<option value="">请选择</option>
       	        	<c:forEach items="${applicationScope.sysParam.operatorStatusMap}" var="operatorStatusMap" >
		 				<option value="${operatorStatusMap.key}">${operatorStatusMap.value}</option>
		 			</c:forEach>
		 			</select>
		 			<span class="toolbar-item dialog-tool-separator"></span>
		 			  省份：
       	          <select id="province" name="filter_EQS_province" onchange="chooseCity1()">
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.provinceMap}"
						var="provinceMap">
						<option value="${provinceMap.value}">${provinceMap.value}</option>
					</c:forEach>
				</select>
				<span class="toolbar-item dialog-tool-separator"></span>
				 城市：
       	         <select id="city" name="filter_EQS_city">
       	        	 <option value="">请选择</option>
       	         </select>
				<br />
				<div style="height: 7px;"></div>
				 <a href="javascript(0)" class="easyui-linkbutton"
					plain="true" iconCls="icon-search" onclick="cx()">查询</a>
					<span class="toolbar-item dialog-tool-separator"></span> 
					<input type="reset" value="重置" />
					 <span class="toolbar-item dialog-tool-separator"></span>
		        &nbsp;<input type="button" value="批量开通" onclick="updateStatus(1)"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        &nbsp;<input type="button" value="批量关闭" onclick="updateStatus(0)"/>
					 <span class="toolbar-item dialog-tool-separator"></span>
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  运营商：<select name="operatorChoose" >
				 <option value="">请选择</option>
			 			<c:forEach items="${applicationScope.sysParam.providerMap}" var="providerMap" >
			 				 <option value="${providerMap.value}">${providerMap.value}</option>
			 			</c:forEach>
		 		</select>
		 			<span class="toolbar-item dialog-tool-separator"></span>
				  省份：      
       	          <select name="provinceChoose">
       	        	<option value="">请选择</option>
		 			<c:forEach items="${applicationScope.sysParam.provinceMap}"
						var="provinceMap">
						<option value="${provinceMap.value}">${provinceMap.value}</option>
					</c:forEach>
				</select>
		       &nbsp;<input type="button" value="开通全省" onclick="updateStateByProvince(1)"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        &nbsp;<input type="button" value="关闭全省" onclick="updateStateByProvince(0)"/>
			</form>
			<div style="height: 7px;"></div>
			<shiro:hasPermission name="boss:operatorConfig:add">
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="boss:operatorConfig:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="boss:operatorConfig:update">
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
    url:'${ctx}/boss/operatorConfig/operatorConfigList', 
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
	singleSelect:false, 
    columns:[[    
		{field:'ck',checkbox:true}, 
		{field:'id',title:'id',hidden:true},  
		{field:'operator',title:'运营商',width:100},
		{field:'province',title:'省份',width:100},
		{field:'city',title:'城市',width:100},
		{field:'type',title:'状态',width:100,
			formatter:function(value,row,index){
				var strs=["关闭","开通"]; 
				for(var i=0;i<strs.length;i++){
					if(value==i){
						return strs[i];
					}
				}
				return value;
       	 	}
		}
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
	    href:'${ctx}/boss/operatorConfig/create',
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'确认',
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

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/boss/operatorConfig/delete/"+row.id,
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
	    href:'${ctx}/boss/operatorConfig/update/'+row.id,
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

//导出excel
function exportExcel(){
	parent.$.messager.show({ title : "提示",msg: "导出需要时间，请稍等片刻！", position: "bottomRight" });
	var url = "${ctx}/boss/operatorConfig/exportExcel";
	window.location.href = url;
}
//省市的二级联动
function chooseCity1(){
	var $province=$("#province option:selected").val();
	var $city=$("#city");
	//清空城市列表
	clearCity1();
	$.ajax({
		url:"${ctx}/boss/orderInfo/getCity",
		type:"post",
		data:{"province":$province},
		dataType:"json",
		success:function(cityList){
			if(cityList!=null && cityList!=undefined){
				$.each(cityList, function(i,city){
					$city.append("<option value='" +city+ "'>" + city +"</option>");
				});
			}
		},
		error:function(){
			
		}
	});
}

//清空城市
function clearCity1(){
	$("#city").empty();
	$("#city").append("<option value=''>请选择</option>");
	urll='${ctx}/boss/orderInfo/orderInfoList';
}

//批量更新状态
function updateStatus(state){
	//获取选中的所有行
	var rows=$("#dg").datagrid("getSelections");
	if(rows.length==0){
		alert("请选中至少一行");
		return false;
	}
	
	var ids=new Array();
	for(var i=0;i<rows.length;i++){
		ids[i]=rows[i].id;
	}
	
	$.ajax({
		url:"${ctx}/boss/operatorConfig/updateStatus",
		type:"post",
		traditional:true,
		data:{"ids":ids,"state":state},
		success:function(value){
			dg.datagrid('reload'); 
		}
	});
}
//批量更新状态
function updateStateByProvince(state){
	var province = $("select[name=provinceChoose]").val();
	var operatorChoose = $("select[name=operatorChoose]").val();
	var stateName = state==0?"关闭":"开通";
	if(province!=""&&operatorChoose!=""){
		$.messager.confirm('提示', "您确定要"+stateName+"<span style='color:green;font-weight:bold'>"+province+","+operatorChoose+"</span>?",function(data){
			if(data){
				$.ajax({
					url:"${ctx}/boss/operatorConfig/updateStateByProvince",
					type:"post",
					data:{"province":province,"state":state,"operator":operatorChoose},
					success:function(success){
						if(success){
							parent.$.messager.show({ title : "提示",msg: "更新成功！", position: "bottomRight" });
							dg.datagrid('reload'); 
						}else {
							alert("服务器出现问题，请与管理员联系");
						}
					}
				});
			}
		});
	}else {   
		alert("请选择省份和运营商");
	}
}
</script>
</body>
</html>