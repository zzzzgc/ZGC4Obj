<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />



<script>
	function clearRows(){
		$('#dg').datagrid('clearSelections');
	}
	function uploadFileCommon(url, fileNamesStr) {
		var fileName = $("#upload_file").val();
		var num = fileName.lastIndexOf(".");
		fileName = fileName.substring(num);
		if (fileNamesStr != null && fileNamesStr.indexOf(fileName) == -1) {
			$.messager.alert("只能选择" + fileNamesStr + "格式文件");
			return;
		}
		var files = $("#upload_file")[0].files[0];
		if (files != undefined) {
			// 			var url = url;// "http://localhost:8080/XinXingDistribution/call.do?cmd=cmdFileUpload";
			ajaxSubmit(files, url);
		} else {
			successTip("请选择文件", dg, d);
		}
	}
	function ajaxSubmit(files, url) {
		var xhr = new XMLHttpRequest();
		xhr.open('POST', url, true);

		var formData = new FormData();
		formData.append('file', files);//$("#gift_detail_icon")[0].files[0]);//文件名
		xhr.onreadystatechange = function(response) {
			if (xhr.readyState == 4 && xhr.status == 200 && xhr.responseText != "") {
				var responseText = xhr.responseText;
				$("#state").html("上传成功");
				console.log(responseText);
				var i = responseText.lastIndexOf("/");
				var imgScr = responseText.substring(i + 1);
				console.log("imgScr:" + imgScr);
				var visit_url = '${img_visit_url}';
				$("#address").val(visit_url + imgScr);
// 				$("#img").attr("src", visit_url + imgScr);
			} else if (xhr.readyState == 4 && xhr.status != 200 && xhr.responseText) {
				successTip("上传失败", dg, d);
			}
		};
		xhr.send(formData);
	}

	//显示绿色或红色
	function greenOrRed(value, showValue, more) {
		var color = value == 1 ? "green" : "red";
		returnVal = '<span style="color:'+color+';font-weight:bold;">' + showValue + '</span>';
		if (more) {
			if (value != 1 && value != 2) {
				returnVal = showValue;
			}
		}
		return returnVal;
	}
	//更新的时候是去掉时间最后的点(毫秒),不然会报错
	function deleteMSCommon(value, name) {
		var value = $("input[name=" + name + "]").val();
		if (value != "") {
			$("input[name=" + name + "]").val(value.substring(0, value.length - 2));
		}
	}
	//批量更新
	function sendPostByUrlManyCommon(html, url, status, isNeed, obj) {
		obj = JSON.stringify(obj);
		// 		console.debug(obj);
		var rows = $("#dg").datagrid("getSelections");
		var yixuan = "";
		if (isNeed == null && rows.length != 1) {
			$.messager.alert("请只选择一行");
			return false;
		}
		var color = (status == 0 || status == 2) ? "red" : "green";
		if (isNeed == true || isNeed == undefined) {
			if (rows.length == 0) {
				$.messager.alert("请选中至少一行");
				return false;
			}
			yixuan = '已选<span style="color:' + color + ';font-weight:bold;">' + rows.length + '</span>条记录,';
		}
		var ids = new Array();
		for (var i = 0; i < rows.length; i++) {
			ids[i] = rows[i].id;
		}
		var title = $(html).text();
		parent.$.messager.confirm('提示', yixuan + '您确定要<span style="color:' + color + ';font-weight:bold;">' + title
				+ '</span>吗?', function(data) {
			if (data) {
				$.ajax({ url : url, type : "post", traditional : true,
					data : { "ids" : ids, "status" : status, "obj" : obj }, success : function(value) {
						if (value == "success") {
							dg.datagrid('reload');
						} else
							$.messager.alert(value);
					} });
			}
		});
	}

	//导出excel
	function exportExcel(url) {
		var abc = url;
		$.messager.confirm('提示', '确定要导出excel？', function(data) {
			if (data) {
				var obj = $("#searchFrom").serializeObject();
				// 				var params = "&";
				var params = (abc.indexOf("?") != -1) ? "&" : "?";
				for ( var name in obj) {
					params += name + "=" + obj[name] + "&";
				}
				params = params.substring(0, params.length - 1);
				parent.$.messager.show({ title : "提示", msg : "导出需要时间，请稍等片刻！", position : "bottomRight" });
				var url = abc + params;
				window.location.href = url;
			}
		});
	}
	//导出excel
	function exportExcelCommon(url) {
		var baseUrl = url;
		$.messager.confirm('提示', '确定要导出excel？', function(data) {
			if (data) {
				var obj = $("#searchFrom").serializeObject();
				postDataByForm(baseUrl, obj);
			}
		});
	}
	function postDataByForm(url, obj) {
		var str = "";
		for ( var name in obj) {
			str += '<input type="text" name="'+name+'" value="'+obj[name]+'" />';
		}
		$('<form method="post" action="' + url + '">' + str + '</form>').appendTo('body').submit().remove();
	}
	function getNames(name, moreName) {
		var batchs = (moreName == undefined) ? "batchs" : moreName;
		var returnVal = "";
		$(name).each(function() { //遍历全部option
			returnVal += "," + $(this).val();
		});
		if (returnVal != "") {
			returnVal = returnVal.substring(1, returnVal.length);
			$("input[name=" + batchs + "]").val(returnVal);
		}
	}
	function addCommon(url, width, height) {
		if (width == null) {
			width = 380;
		}
		if (height == null) {
			height = 500;
		}
		d = $("#dlg").dialog(
				{ title : '添加配置', width : width, height : height, href : url, maximizable : true, modal : true,
					buttons : [
							{ text : '确认', handler : function() {
								$($("#dlg .l-btn-text")[0]).css('display', "none");
								$($("#dlg .l-btn-text")[1]).css('display', "none");
								$('#mainform').submit();
							} }, { text : '取消', handler : function() {
								d.panel('close');
							} }
					] });
	}
	//弹窗修改
	function updCommon(url, width, height) {
		var rows = $("#dg").datagrid("getSelections");
		if (width == null) {
			width = 380;
		}
		if (height == null) {
			height = 500;
		}
		var errorMsg = null;
		errorMsg = getUpdErrorMsgCommon(rows);
		if (errorMsg != null) {
			$.messager.alert(errorMsg);
			return;
		}
		d = $("#dlg").dialog(
				{ title : '修改用户', width : width, height : height, href : url + "&id=" + rows[0].id, maximizable : true,
					modal : true, buttons : [
							{ text : '修改', handler : function() {
								$('#mainform').submit();
							} }, { text : '取消', handler : function() {
								d.panel('close');
							} }
					] });
	}

	function getUpdErrorMsgCommon(rows) {
	}
	function getErrorMsgCommon(rows) {
	}
	function getMapCommon(title, rows) {
		var map = $("#mainform").serializeObject();
		if (rows != null) {
			var ids = new Array();
			for (var i = 0; i < rows.length; i++) {
				ids[i] = rows[i].id;
			}
			map.ids = ids;
		}
		map.title = '您确定要<span style="color:green;font-weight:bold;">' + title + '</span>吗?';
		return map;
	}
	//折扣
	function alertWindowCommon(html, url1, url2, isNeedRows) {
		var rows = dg.datagrid('getSelections');
		if (isNeedRows == null && rows.length != 1) {
			$.messager.alert("请只选择一项");
			return;
		}
		if (isNeedRows) {
			if (rows.length == 0) {
				$.messager.alert("请至少选择一项");
				return;
			}
		}
		var errorMsg = null;
		errorMsg = getErrorMsgCommon(rows, errorMsg);
		if (errorMsg != null) {
			$.messager.alert(errorMsg);
			return;
		}
		d = $("#dlg").dialog(
				{
					title : '修改折扣',
					width : 500,
					height : 500,
					href : url1,
					maximizable : true,
					modal : true,
					buttons : [
							{
								text : '修改',
								id : "btnUpdate",
								handler : function() {
									var rows = dg.datagrid('getSelections');
									var map = getMapCommon($(html).text(), rows);
									if (map.errorMsg != null) {
										$.messager.alert(map.errorMsg);
										return;
									}
									parent.$.messager.confirm('提示', map.title, function(data) {
										if (data) {
											$.ajax({ traditional : true, type : "get", url : url2, data : map,
												success : function(data) {
													if (data == "success") {
														d.panel('close');
														cx();
														$.messager.alert("操作成功!");
													} else {
														$.messager.alert(data);
													}
												} });
										}
									});
								} }, { text : '取消', handler : function() {
								d.panel('close');
							} }
					] });
	}
</script>
