<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<!-- jchart图标扩展 -->
<script src="${ctx}/static/plugins/jchart/ichart.1.2.min.js"></script>
</head>
<body style="font-family: '微软雅黑'">
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<form id="searchFrom" action="">
				<input name="filter_INS_city" type="text" value="" class="easyui-validatebox" />
				<select name="city" multiple="multiple" size="5" onblur="getNames(this,'filter_INS_city')">
					<c:forEach items="${applicationScope.sysParam.gdCityMap}" var="gdCityMap">
						<option value="${gdCityMap.value}">${gdCityMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="type">
					<option value="0">成功数</option>
					<option value="1">成功率</option>
					<option value="2" selected="selected">总数</option>
				</select>
				<!-- -->
				<span class="toolbar-item dialog-tool-separator"></span> <select name="filter_INI_area">
					<c:forEach items="${applicationScope.sysParam.areaMap}" var="areaMap">
						<option value="${areaMap.key}">${areaMap.value}</option>
					</c:forEach>
				</select>
				<!-- -->

				<a href="javascript(0)" class="easyui-linkbutton" onclick="cx()">查询</a> <span class="toolbar-item dialog-tool-separator"></span>


				<input type="text" name="filter_GED_addTime" class="easyui-my97" startDate='%y-%M-%d 00:00:00' datefmt="yyyy-MM-dd HH:mm:ss"
					data-options="width:150,prompt: '添加时间'" />
				--
				<input type="text" name="filter_LED_addTime" class="easyui-my97" startDate='%y-%M-%d 23:59:59' datefmt="yyyy-MM-dd HH:mm:ss"
					data-options="width:150,prompt: '添加时间'" />
			</form>
		</div>
		<div id="canvasDiv"></div>

	</div>
	<script type="text/javascript">
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
		// 		cx();
		function cx() {
			$.ajax({ type : 'post', traditional : true, data : $("#searchFrom").serializeObject(),
				url : '${ctx}/boss/bossGdDataAnalyze/getPicMap', success : function(data) {
					console.debug(data);
					if (data != null && data.failReason == null) {
						createPic(data.values, data.dates, data.title);
					} else {
						if (data == null) {
							$.messager.alert("数据异常,请联系技术人员");
						} else {
							$.messager.alert(data.failReason);
						}
					}

				} });
		}
		function createPic(values, labels, title) {
			var data = values;
			var labels = labels;
			var chart = new iChart.Area2D({
				render : 'canvasDiv',
				data : data,
				title : title,
				width : 1500,
				height : 800,
				tip : { enable : true, shadow : true, move_duration : 400,
					border : { enable : true, radius : 5, width : 2, color : '#3f8695' }, listeners : {
					//tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
					parseText : function(tip, name, value, text, i) {
						return name + ":" + value + "";
					} } }, sub_option : { label : false, point_size : 10 },
				crosshair : { enable : true, line_color : '#62bce9'//十字线的颜色
				}, legend : { enable : true, row : 1,//设置在一行上显示，与column配合使用
				column : 'max', valign : 'top', sign : 'bar', background_color : null,//设置透明背景
				offsetx : -80,//设置x轴偏移，满足位置需要
				border : true },

				coordinate : { height : '90%', background_color : '#edf8fa' }, sub_option : { hollow_inside : false,//设置一个点的亮色在外环的效果
				point_size : 10 }, labels : labels });
			chart.draw();

		};
	</script>
</body>
</html>