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
		<div style="height: 7px;"></div>
		<div style="height: 7px;"></div>
		<div style="height: 7px;"></div>
		<div>
			<button onclick="flushStatus('stop_send_mobile',this);">关闭移动发送订单</button>
			<span class="toolbar-item dialog-tool-separator"></span>
			<button onclick="flushStatus('stop_send_unicom',this)">关闭联通发送订单</button>
			<span class="toolbar-item dialog-tool-separator"></span>
			<button onclick="flushStatus('stop_send_telecom',this)">关闭电信发送订单</button>
			<span class="toolbar-item dialog-tool-separator"></span>
			<div style="height: 7px;"></div>
			<div style="height: 7px;"></div>
			<button onclick="flushStatus('start_send_mobile',this)">开通移动发送订单</button>
			<span class="toolbar-item dialog-tool-separator"></span>
			<button onclick="flushStatus('start_send_unicom',this)">开通联通发送订单</button>
			<span class="toolbar-item dialog-tool-separator"></span>
			<button onclick="flushStatus('start_send_telecom',this)">开通电信发送订单</button>
			<span class="toolbar-item dialog-tool-separator"></span>
			<div style="height: 7px;"></div>
			<div style="height: 7px;"></div>
			<button onclick="getStatus('get_queue_state')">获取队列状态</button>
		</div>

	</div>
	<script type="text/javascript">
		$(function() {
		});
		//格式化 关于钱的字段，例如单价，一口价等
		function flushStatus(name,value) {
			var title = $(value).html();
			parent.$.messager.confirm('提示', '您确定要<span style="color:green;font-weight:bold;">'+title+'</span>吗?', function(data) {
				if (data) {
					$.ajax({
						type : 'get',
						url : "${ctx}/boss/flushStatus/flushStatus?name="
								+ name,
						success : function(data) {
							if (data) {
								$.messager.alert("操作成功！");
							} else
								$.messager.alert("操作失败,请与管理员联系！");
						}
					});
				}
			});

		}
		
		//格式化 关于钱的字段，例如单价，一口价等
		function getStatus(name) {
			$.ajax({
				type : 'get',
				url : "${ctx}/boss/flushStatus/flushStatus?name="
						+ name,
				success : function(data) {
					if (data) {
						$.messager.alert("操作成功！");
					} else
						$.messager.alert("操作失败,请与管理员联系！");
				}
			});
		}
	</script>
</body>
</html>