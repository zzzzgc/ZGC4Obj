<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body{text-align: center;}
        .just-top{
            margin-top: 25%;
        }

        .photo{
            width:220px;
        }

    </style>
    <title>绑定洋葱信息</title>
</head>
<body>
<div class="container">

    <div class="just-top">
        为了系统安全，请下载洋葱：<a href="https://www.yangcong.com/download#/public" target="_blank">点我跳转下载</a>
    </div>

    <div>
        下载安装完成后，请使用【洋葱】扫描以下二维码
        <div >
            <img id="code" class="photo" >
        </div>
    </div>

    <a class="btn btn-default" href="${ctx}/a/logout" role="button">操作完成，重新登录</a>
</div>





<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script>
    $(function(){
        $.getJSON('${ctx}/yc/bind/bind',{},function(data){
            if(data.state){
                $("#code").attr("src",data.url);
                console.log(data.url);
            }
        });
    });
</script>


</body>
</html>
