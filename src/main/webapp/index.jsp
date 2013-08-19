<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
<head>
<title>刀具检验与管理系统</title>
<jsp:include page="inc.jsp"></jsp:include>

<style>
</style>
</head>
<body id="index_layout" class="easyui-layout">
	<div id="index_layout_north" data-options="region:'north', border:false, href:'layout/north.jsp'" style="height:60px;background: url('images/topback.gif');overflow: visible;"></div>

	<div id="index_layout_west" data-options="region:'west', href:'layout/west.jsp'" style="width:200px;"></div>

	<div id="index_layout_center" data-options="region:'center', href:'layout/center.jsp'" style="overflow:hidden;"></div>
	
	<jsp:include page="isIe.jsp"></jsp:include>
</body>
</html>
