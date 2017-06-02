<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<style type="text/css">
.jump_img {
	position: absolute;
	z-index: 99999;
	width: 50%;
	height: 50%;
	top: 50%;
	left: 50%;
	margin: -25% 0 0 -25%;
}
</style>
</head>
<body style="font-family: '微软雅黑'">
	<div style="position:relaitve;width: 100%">
		<div style="position:absolute; z-index:5;margin-top: 35%;margin-left: 10%">
			<input type="text" name="phone" data-options="prompt: '采购商id'" style="width:100%;height: 50%;" />
			<a href="javascript(0)" class="easyui-linkbutton" onclick="getPicture()">抢流量</a>
		</div>
		<div style="position:absolute; z-index:3">
			<img style="width:100%;" src="${ctx}/static/images/bossActivityGdws11/fuli.jpg" />
		</div>
		<div style="position:absolute; z-index:6">
			<img style="width:60%;" src="${ctx}/static/images/bossActivityGdws11/zhongjiang.png" />
		</div>
	</div>
	<!-- 		 -->
	<!-- 		<img src="${ctx}/static/images/bossActivityGdws11/fuli.jpg" class="jump_img"> -->
	<table id="dg"></table>
	<div id="dlg"></div>
	<script type="text/javascript">
		$(function() {
		})
		function getPicture() {//'${ctx}/dist/distProduct/getPicture?src='
			var url = "${ctx}/boss/common/list?page=commonBigPicture";
// 			$("#dlg").css('display','none');
			d = $("#dlg").dialog(
					{ title : '提示', width : 600, height : 600, href : url, maximizable : true,
						modal : true,
						collapsible:false,minimizable:false,maximizable:false,
						buttons : [
							{ text : '确认', handler : function() {
								d.panel('close');
							} }
						// 							, { text : '取消', handler : function() {
						// 								d.panel('close');
						// 							} }
						] });
		}
		function getFlow() {
		}
	</script>
</body>
</html>