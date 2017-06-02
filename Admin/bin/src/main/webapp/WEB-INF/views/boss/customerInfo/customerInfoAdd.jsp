<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/common/base/easyui.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
</head>
<body>
<%
	List<Integer> list=new ArrayList<Integer>();
	list.add(2);
	list.add(3);
	request.setAttribute("rlist",list);
%>
	<div>
		<form id="mainform" action="${ctx}/boss/customerInfo/add" method="post">
			<table class="formTable">
				<tr>
					<td>采购商名称：</td>
					<td><input type="hidden" name="id" value="${customerInfo.id }" />
						<input name="customerName" type="text"
						value="${customerInfo.customerName }" class="easyui-validatebox"
						required="required" />
						<input type="hidden" name="status" value="0" required="required"/>
						</td>
				</tr>
				<tr>
					<td>采购商简称：</td>
					<td><input id="cusSimpleName1" name="cusSimpleName" type="text"
						value="${customerInfo.cusSimpleName }"
						class="easyui-validatebox" required="required" onblur="checkSimpleName()"/></td>
				</tr>
				<tr>
					<td>联系人：</td>
					<td><input name="customerContact" type="text"
						value="${customerInfo.customerContact }"
						class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>联系号码：</td>
					<td><input name="customerNumber" type="text"
						value="${customerInfo.customerNumber }"
						class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<td>登录名：</td>
					<td><input name="loginName" type="text"
						value="${customerInfo.loginName}"
						class="easyui-validatebox" required="required" /></td>
				</tr>
				<tr style="display: none">
					<td>资金余额：</td>
					<td><input name="balance" type="text"
						value="0"
						class="easyui-validatebox" readOnly="true"/></td>
				</tr>
				<%-- <tr>
					<td>状态：</td>
					<td><select id="statusAdd1" name="status">
							<c:forEach items="${applicationScope.sysParam.freezeMap}"
								var="freezeMap">
								<option value="${freezeMap.key}">${freezeMap.value}</option>
							</c:forEach>
					</select></td>
				</tr> --%>
				<shiro:hasPermission name="boss:customerInfo:showIsBackCost" >
				<tr>
					<td>是否回调成本：</td>
					<td><select id="isBackCost1" name="isBackCost">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.isNoMap}"
								var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				</shiro:hasPermission>
				<shiro:lacksPermission name="boss:customerInfo:showIsBackCost" >
				<tr style="display: none">
					<td>是否回调成本：</td>
					<td><input name="isBackCost" type="text"
						value="0"
						class="easyui-validatebox" required="required" /></td>
				</tr>
				</shiro:lacksPermission>
				<tr>
					<td>邮箱：</td>
					<td><input name="email" type="text"
						value="${customerInfo.email }"
						class="easyui-validatebox" required="required"/></td>
				</tr>
				<tr>
					<td>发票类型：</td>
					<td><select name="tickType">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.tickTypeMap}"
								var="tickTypeMap">
								<option value="${tickTypeMap.key}">${tickTypeMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>是否向下兼容产品：</td>
					<td><select name="isAutoConfig">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.isNoMap}"
								var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>兼容等级：</td>
					<td><select name="configLevel">
							<option value="">请选择</option>
							<c:forEach items="${rlist}" var="num">
								<option value="${num}">${num}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>是否手工回调：</td>
					<td><select name="isManualCallback">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.isNoMap}"
								var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr style="display: none">
					<td>是否触发报警：</td>
					<td><select name="isAlarm">
							<c:forEach items="${applicationScope.sysParam.isNoMap}"
								var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>回调地址：</td>
					<td><input name="callbackAddress" type="text"
						value="${customerInfo.callbackAddress }"
						class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>允许ip：</td>
					<td><input name="allowIp" type="text"
						value="${customerInfo.allowIp }"
						class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>订单优先级：</td>
					<td><input name="priority" type="text"
						value="${customerInfo.priority }"
						class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td><input name="remark" type="text"
						value="${customerInfo.remark }"
						class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>余额预警额度：</td>
					<td><input name="alarmBalance" type="text"
						value="200"
						class="easyui-validatebox" required="required" /></td>
				</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
		$(function() {
			$('#mainform').form({
				onSubmit : function() {
					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {
					if(successTip(data, dg, d)){
						
					}
				}
			});
		});
		
		//判断状态下拉框的默认值
		var statusAdd1=0;
		$("#statusAdd1 option").each(function(){ //遍历全部option
	        var txt1 = $(this).val(); //获取option的内容
			if(txt1==statusAdd1){
				$(this).attr("selected",true);
			}
	    });
		showValueByKey(1,"isAlarm");
		//检查采购商简称
		function checkSimpleName(){
			var simpleName=$("#cusSimpleName1").val();
			$.ajax({
				type:'get',
				url:"${ctx}/boss/customerInfo/checkSimpleName?simpleName="+simpleName,
				dataType:"json",
				success: function(data){
					if(data.msg!="success"){
						alert(data.msg);
					}
				},
				error:function(data){
					alert("系统错误");
				}
			});
		}
	</script>
</body>
</html>