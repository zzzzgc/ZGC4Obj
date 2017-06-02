<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/boss/providerProductInfo/add" method="post">
	<table  class="formTable">
		<tr>
			<td>折扣：</td>
			<td>
			<input id="discount1" name="discount" type="text"  class="easyui-validatebox" required="required" onkeyup="NumberCheck();" />
			<span id="span1" style="color: red;"></span> 
			</td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">
/* $().ready(function() {
	$("#mainform").validate({
	        rules: {
	  		range:[0,1]
	   	 },
		    messages: {
		    email: {
		    	range: "请输入{0}到{1}的数"
	 	  }
	    }
	})
}) */
function NumberCheck() {
	 var btn = $("#btnUpdate");
	  var num=$("#discount1").val();
	  var re=/^[0-9]+.?[0-9]*$/;
	  if(!re.exec(num)){ 
		  $("#span1").text("格式不符合");
		  if(!btn.hasClass("l-btn-disabled")){
			  btn.addClass("l-btn-disabled"); 
		  }
	  }else{
	 	  if(num!=null||num!=""){
		 	  if(!(parseFloat(num)>0&&parseFloat(num)<=2)){
			 	 $("#span1").text("请输入大于0小于等于2的数");		
			 	 if(!btn.hasClass("l-btn-disabled")){
					  btn.addClass("l-btn-disabled"); 
				  }
			  	 } else {
			  		$("#span1").text("");	
			  		if(btn.hasClass("l-btn-disabled"))
			  	    	btn.removeClass("l-btn-disabled");
			  	 }
		  }
	  }
}
$(function(){
	$('#mainform').form({    
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){   
	    	successTip(data,dg,d);
	    }    
	}); 
});
</script>
</body>
</html>