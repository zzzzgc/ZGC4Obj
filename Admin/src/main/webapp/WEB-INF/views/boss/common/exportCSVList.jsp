<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body style="font-family: '微软雅黑'">
	<div id="tb" style="padding:20px;height:auto">
		<div>
			<form id="searchFrom1" action="">
				年份: <select name="year">
					<option value="2016">2016</option>
					<option value="2017">2017</option>
				</select> 月份: <select name="month">
					<option value="">请选择</option>
					<option value="1">1月</option>
					<option value="2">2月</option>
					<option value="3">3月</option>
					<option value="4">4月</option>
					<option value="5">5月</option>
					<option value="6">6月</option>
					<option value="7">7月</option>
					<option value="8">8月</option>
					<option value="9">9月</option>
					<option value="10">10月</option>
					<option value="11">11月</option>
					<option value="12">12月</option>
				</select> 
				</form>
				<br />
			<button onclick="exportCSV('month','orderInfo');">下载月订单列表</button>
			<button onclick="exportCSV('month','customerMoneyRecord');">下载月财务列表</button>
				<br />
				<br />
				<br />
				<br />
				<form id="searchFrom2" action="">
				<input type="text" name="startTime" class="easyui-my97" startDate='%y-%M-%d' datefmt="yyyy-MM-dd"
					data-options="width:150,prompt: '开始时间'" />
				--
				<input type="text" name="endTime" class="easyui-my97" startDate='%y-%M-%d' datefmt="yyyy-MM-dd"
					data-options="width:150,prompt: '结束时间'" />
				提示:时间间隔不能超过一个月
					</form>
				<br />
			<button onclick="exportCSV('day','orderInfo')">下载日订单列表</button>
			<button onclick="exportCSV('day','customerMoneyRecord')">下载日财务列表</button>
			<span style="background: red" id="failReason">${failReason}</span>
		</div>

	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
		$(function() {
			var date = new Date();
			var month = date.getMonth();
			if(month==0){
				month = 12;
			}
			$("select[name=month] option").each(function(){ //遍历全部option
				if($(this).val()==month){
					$(this).attr("selected",true);
				}
		    });
			
		});
		function exportCSV(monthOrDay, className) {
			$("#failReason").html("");
			var obj = null;
			if(monthOrDay=="month"){
				obj = $("#searchFrom1").serializeObject();
			}else if(monthOrDay =="day"){
				obj = $("#searchFrom2").serializeObject();
			}
			var params = "?";
			for ( var name in obj) {
				params += name + "=" + obj[name] + "&&";
			}
			params = params.substring(0, params.length - 2);
// 			parent.$.messager.show({ title : "提示", msg : "下载需要时间，请稍等片刻！", position : "bottomRight" });
			var url = "${ctx}/boss/common/exportCSV" + params + "&&" + "name=" + monthOrDay + "&className=" + className;
			console.debug(url);
			window.location.href = url;
		}
	</script>
</body>
</html>