<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/boss/balanceRecord/add/audit" method="post">
	<table  class="formTable">
		<tr class="hidden">
			<td>
				<input type="hidden" name="customerId" value="${customerInfo.id}"/>
			</td>
		</tr>
		<tr>
			<td>采购商名称：</td>
			<td>
				<input name="customerName" value="${customerInfo.customerName}"  readonly="readonly" data-options="width: 180"/>
			</td>
		</tr>
		<tr>
			<td>注资类型：</td>
			<td>
			<input id="type1" name="type" type="text"  data-options="required:'required',panelHeight:'auto'"/>
			</td>
		</tr>
		<tr>
			<td>注资金额：</td>
			<td>
				<input id="balance"  name="balance" type="text" class="easyui-validatebox"  data-options="validType:'requireRadio[\'input[name=balance]\', \'扣款输入负数，注资输入正数 \']'"/>
			</td>
		</tr>
		
		<tr>
			<td>注资凭证：</td>
			<td>
				<input type="file" class="easyui-filebox" id="upload_file" style="width:100%"> <span id="status"></span><a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="uploadFile()">上传</a>
				<input type="hidden" id="tickUrl" name="tickUrl" data-options="width: 180"/>
				<input type="hidden" name="addUser" value="${userName}" data-options="width: 180"/>
			 </td>
		</tr>
		<tr>
			<td>备注：</td>
			<td><textarea rows="3" cols="41" name="remark"></textarea></td>
		</tr>
	</table>
	</form>
</div> 
<script type="text/javascript">

$(function(){
	$('#type1').combobox({   
		url:'${ctx}/static/system/balance/balanceType.json',   
	    valueField:'id',   
	    textField:'text',
	});
});


	$.extend($.fn.validatebox.defaults.rules, {
	    requireRadio: {
	        validator: function(value, param){  
	        	var type = $("#type1").combobox('getValue');
	            var balance = $("#balance").val();	            
	            var res = (type==5 && balance<0) || (type==1 && balance>0);
	            return res;
	        },  
	        message: '{1}'
	    }  
	});


$('#mainform').form({    
    onSubmit: function(){   
    	var isValid = $(this).form('validate'); 
		return isValid;	// 返回false终止表单提交
    },    
    success:function(data){   
    	if(successTip(data,dg,d))
    		dg.datagrid('reload');
    }    
});   


function uploadFile(){
	var files = $("#upload_file")[0].files[0];
	if(files != undefined){
		var url="${ctx}/file_upload.do";
		ajaxSubmit(files,url);
	}else{
		successTip("请选 择文件",dg,d);
	}
}


function ajaxSubmit(files,url) {
	var xhr = new XMLHttpRequest();
	xhr.open('POST', url, true);
	var formData = new FormData();				
	formData.append('file', files);//$("#gift_detail_icon")[0].files[0]);//文件名
	xhr.onreadystatechange = function(response) {
		if (xhr.readyState == 4 && xhr.status == 200 && xhr.responseText != "") {						
			//successTip("success",dg,d);
			var responseText = xhr.responseText;
			$("#status").html("上传成功");
			console.log(responseText);
			$("#tickUrl").val(responseText);
			//getRewardInfoDisplay(responseText);
		} else if (xhr.readyState == 4 && xhr.status != 200 && xhr.responseText) {						
			successTip("上传失败",dg,d);
		}
	};
	xhr.send(formData);
}

</script>
</body>
</html>