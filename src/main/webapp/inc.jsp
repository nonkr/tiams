<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<link rel="Shortcut Icon" href="<%=basePath%>/images/favicon.png" type="image/x-icon">
<link rel="Bookmark" href="<%=basePath%>/images/favicon.png" type="image/x-icon">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="tiams">
<meta http-equiv="description" content="TIAMS">

<script type="text/javascript" src="<%=basePath%>/jslib/jquery-1.9.0.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/jslib/BrowserDetect.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/jslib/jquery.cookie.js" charset="utf-8"></script>
<link rel="stylesheet" href="<%=basePath%>/jslib/iconPluto/icon.css" type="text/css" />
<%
	String easyuiThemeName = "bootstrap";
	Cookie cookies[] = request.getCookies();
	if (cookies != null && cookies.length > 0) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("easyuiThemeName")) {
				easyuiThemeName = cookie.getValue();
				break;
			}
		}
	}
%>

<link id="easyuiTheme" rel="stylesheet" href="<%=basePath%>/jslib/jquery-easyui-1.3.2/themes/<%=easyuiThemeName%>/easyui.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/jslib/jquery-easyui-1.3.2/themes/icon.css" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/jslib/jquery-easyui-1.3.2/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/jslib/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<script type="text/javascript" src="<%=basePath%>/jslib/jquery.actual.js" charset="utf-8"></script>

<link rel="stylesheet" href="<%=basePath%>/jslib/uniform/themes/default/css/uniform.default.min.css" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/jslib/uniform/jquery.uniform.min.js" charset="utf-8"></script>

<script type="text/javascript" src="<%=basePath%>/jslib/nonkrj4easyui.js" charset="utf-8"></script>

<link rel="stylesheet" href="<%=basePath%>/css/main.css" type="text/css" />
