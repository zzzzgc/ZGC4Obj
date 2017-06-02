<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/boss/orderInfo/batchUpdateHFOrder" method="post">
			<table class="formTable">
				<tr>
					<td>修改话费订单的Exl表格:</td>
					<td>
					<!-- 文件提交 -->
					<input type="file" class="easyui-filebox" id="upload_file" style="width:100%"> <span id="status"></span><a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="uploadFile()">上传</a>
					<!-- 充值地址记录 -->
					<input type="hidden" id="address" name="urll"/>
					</td>
				</tr>
				<pre>
					<b>格式:</b>
					<b>手机(11位)|金额(元)|状态(2 充值中,3 充值成功,4 充值失败)</b>
					<b>132XXXXXXXX|100|2</b>
					<b>133XXXXXXXX|200|3</b>
					<b>134XXXXXXXX|50|4</b>
					<b>135XXXXXXXX|20|1</b>
					<br />
					<b>错误格式:</b>
					<b>136XXXXXXXXXX|300|1 (电话号码有误)</b>
					<b>136XXXXXXXX|300|0  (状态有误)</b>
					<b>有错误的订单不修改...</b>
				</pre>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			
			$('#mainform').form({
				onSubmit : function() {
					var isValid = $(this).form('validate');
					if(isValid){
						alert("操作成功");
					}
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {
					//alert(data);
					successTip("success", dg, d);
				}
			});
			
		});

		
		function uploadFile(){
			var fileName=$("#upload_file").val();
	 		var num=fileName.lastIndexOf(".");
	 		fileName=fileName.substring(num);
	 		if(fileName!=".xlsx"){
	 			alert("只能选择.xls或.xlsx文件");
	 			return;
	 		}
	 
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
					
					$("#address").val(responseText);<!--保存地址-->
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