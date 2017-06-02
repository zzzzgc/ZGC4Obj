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
		<form id="mainform" action="${ctx}/boss/orderInfo/update" method="post">
			<table class="formTable">
				<tr>
					<td>采购商id：</td>
					<td><input type="hidden" name="id" value="${id }" />
						<input name="customerId" type="text"
						value="${orderInfo.customerId }" class="easyui-validatebox"
						required="required" /></td>
				</tr>
				<tr>
					<td>采购商订单号：</td>
					<td><input name="orderKey" type="text"
						value="${orderInfo.orderKey }" class="easyui-validatebox"
						required="required" /></td>
				</tr>
				<tr>
					<td>要充值的手机号码：</td>
					<td><input name="phone" type="text"
						value="${orderInfo.phone }" class="easyui-validatebox"
						required="required" /></td>
				</tr>
				<tr>
					<td>要充值的产品id：</td>
					<td><input name="categoryId" type="text"
						value="${orderInfo.categoryId }" class="easyui-validatebox"
						required="required" /></td>
				</tr>
				<tr>
					<td>提交时间到供货商的时间：</td>
					<td><input name="submitTime" type="text"
						value="${orderInfo.submitTime }" class="easyui-my97"
						datefmt="yyyy-MM-dd HH:mm:ss" startDate='%y-%M-%d 00:00:00'
						data-options="width:150,prompt: '呼叫时间'" /></td>
				</tr>
				<tr>
					<td>成功时间：</td>
					<td><input name="successTime" type="text"
						value="${orderInfo.successTime }" class="easyui-my97"
						datefmt="yyyy-MM-dd HH:mm:ss" startDate='%y-%M-%d 00:00:00'
						data-options="width:150,prompt: '呼叫时间'" /></td>
				</tr>
				<tr>
					<td>失败时间：</td>
					<td><input name="failTime" type="text"
						value="${orderInfo.failTime }" class="easyui-my97"
						datefmt="yyyy-MM-dd HH:mm:ss" startDate='%y-%M-%d 00:00:00'
						data-options="width:150,prompt: '呼叫时间'" /></td>
				</tr>
				<tr>
					<td>订单状态：</td>
					<td><select id="status1" name="status">
							<c:forEach items="${applicationScope.sysParam.orderStatusMap}"
								var="orderStatusMap">
								<option value="${orderStatusMap.key}">${orderStatusMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				
				
				<tr>
					<td>失败原因：</td>
					<td><input name="failReason" type="text"
						value="${orderInfo.failReason }" class="easyui-validatebox"
						required="required" /></td>
				</tr>
				<tr>
					<td>单价：</td>
					<td><input name="price" type="text"
						value="${orderInfo.price }" class="easyui-validatebox"
						required="required" /></td>
				</tr>
				<tr>
					<td>充值成功的供应商id：</td>
					<td><input name="successId" type="text"
						value="${orderInfo.successId }" class="easyui-validatebox"
						required="required" /></td>
				</tr>
				<tr>
					<td>充值次数：</td>
					<td><input name="chargeCount" type="text"
						value="${orderInfo.chargeCount }"
						class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>订单优先值：</td>
					<td><input name="priority" type="text"
						value="${orderInfo.priority }"
						class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>是否已回调或者查询状态：</td>
					<td><select id="callbackStatus1" name="callbackStatus">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.callbackStatusMap}"
								var="callbackStatusMap">
								<option value="${callbackStatusMap.key}">${callbackStatusMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>省份：</td>
					<td><select id="provincee" name="province" onchange="chooseCity()">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.provinceMap}"
								var="provinceMap">
								<option value="${provinceMap.value}">${provinceMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>城市：</td>
					<td>
					<select id="cityy" name="city">
		       	        	 <option value="">请选择</option>
		       	         </select>
					</td>
				</tr>
				<tr>
					<td>是否启用二次通道：</td>
					<td><select id="isSecondChannel1" name="isSecondChannel">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.isNoMap}"
								var="isNoMap">
								<option value="${isNoMap.key}">${isNoMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>号码所属运营商：</td>
					<td><select id="beyoudOperation1" name="beyoudOperation">
							<option value="">请选择</option>
							<c:forEach items="${applicationScope.sysParam.providerMap}"
								var="providerMap">
								<option value="${providerMap.value}">${providerMap.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>供应商流水号：</td>
					<td><input name="providerKey" type="text"
						value="${orderInfo.providerKey }" class="easyui-validatebox"
						required="required" /></td>
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
			successTip(data, dg, d);
		}
	});
	//判断省下拉框的默认值
	var status1="${orderInfo.province }";
	$("#provincee option").each(function(){ //遍历全部option
        var txt1 = $(this).val(); //获取option的内容
		if(txt1==status1){
			$(this).attr("selected",true);
			chooseCity();
		}
    });
	
	var status2="${orderInfo.callbackStatus }";
	$("#callbackStatus1 option").each(function(){ //遍历全部option
        var txt2 = $(this).val(); //获取option的内容
		if(txt2==status2){
			$(this).attr("selected",true);
		}
    });
	
	var status3="${orderInfo.status }";
	$("#status1 option").each(function(){ //遍历全部option
        var txt3 = $(this).val(); //获取option的内容
		if(txt3==status3){
			$(this).attr("selected",true);
		}
    });
	var status4="${orderInfo.beyoudOperation }";
	$("#beyoudOperation1 option").each(function(){ //遍历全部option
        var txt4 = $(this).val(); //获取option的内容
		if(txt4==status4){
			$(this).attr("selected",true);
		}
    });
	var status5="${orderInfo.isSecondChannel }";
	$("#isSecondChannel1 option").each(function(){ //遍历全部option
        var txt5 = $(this).val(); //获取option的内容
		if(txt5==status5){
			$(this).attr("selected",true);
		}
    });
});
function checkCity(){
	//判断市的下拉框默认值
	var cityStatus="${orderInfo.city}";
	$("#cityy option").each(function(){ //遍历全部option
        var text = $(this).val(); //获取option的内容
		if(text==cityStatus){
			$(this).attr("selected",true);
		}
    });
}

//省市的二级联动
function chooseCity(){
	var $province=$("#provincee option:selected").val();
	var $city=$("#cityy");
	//清空城市列表
	clearCity();
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
			checkCity();
		},
		error:function(){
			
		}
	});

}

//清空城市
function clearCity(){
	$("#cityy").empty();
	$("#cityy").append("<option value=''>请选择</option>");
	urll='${ctx}/boss/orderInfo/list';
}
	</script>
</body>
</html>