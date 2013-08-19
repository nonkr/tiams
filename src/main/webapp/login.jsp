<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>
<!DOCTYPE HTML>
<html dir="ltr" lang="zh-CN">
<head>
<title>刀具检验与管理系统</title>
<link rel="Shortcut Icon" href="<%=basePath%>/images/favicon.png" type="image/x-icon">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="tiams">
<meta http-equiv="description" content="TIAMS">
<link href="css/login.css" rel="stylesheet" type="text/css" />

<jsp:include page="inc.jsp"></jsp:include>

<script type="text/javascript" src="<%=basePath%>/js/login.js" charset="utf-8"></script>
<script type="text/javascript">
	$(function() {
		airBalloon('div.air-balloon');

		$('#userlogin_form').form({
			url : '${pageContext.request.contextPath}/sys/userAction!login.action',
			success : function(r) {
				var sessionInfo_loginName = '${sessionInfo.loginName}';
				var obj = jQuery.parseJSON(r);
				if (obj.success) {
					location.href = '${pageContext.request.contextPath}';
				} else {
					$.messager.show({
						title : '提示',
						msg : obj.msg
					});
					$('#userlogin_form input[name=name]').focus();
				}
			}
		});
		$('#userlogin_form input').bind('keyup', function(event) {/* 增加回车提交功能 */
			if (event.keyCode == '13') {
				$('#userlogin_form').submit();
			}
		});

		window.setTimeout(function() {
			$('#userlogin_form input[name=name]').focus();
		}, 0);
	});
</script>

</head>
<body>
	<div class="login">
		<div class="menus">
			<div class="public">
				<!-- <a href="#http://julying.com" target="_blank">使用帮助</a>  -->
				<a href="mailto:nonkr@hotmail.com?subject=Some advices about TIAMS" target="_blank">联系开发者</a>
			</div>
		</div>
		<div class="box png">
			<form id="userlogin_form" action="<%=basePath%>/admin/login.action" method="post">
				<div class="header">
					<h2 class="logo png">
						<a href="#" target="_blank"></a>
					</h2>
				</div>
				<ul>
					<li><label>用户名</label><input name="name" class="login_text" />
					</li>
					<li><label>密 码</label><input name="pwd" type="password" class="login_text" />
					</li>
					<li class="submits"><input class="submit" type="submit" value="登录" />
					</li>
				</ul>
				<div class="copyright">
					&copy; 2012 - 2013
					<!-- 
					 | <a href="http://julying.com" target="_blank" title="Julying CMS">Julying.</a> | 
					<a title="nonkr的腾讯微博" href="http://t.qq.com/BillieSoong" target="_blank" class="weibo tencent">nonkr的腾讯微博</a>
					 -->
				</div>
			</form>
		</div>
		<div class="air-balloon ab-1 png"></div>
		<div class="air-balloon ab-2 png"></div>
		<div class="footer"></div>
	</div>

	<!--[if lt IE 8]>
	<script src="<%=basePath%>/jslib/PIE.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
	    if (window.PIE && ( $.browser.version >= 6 && $.browser.version < 10 )){
	        $('input.text,input.submit').each(function(){
	            PIE.attach(this);
	        });
	    }
	});
	</script>
	<![endif]-->

	<!--[if IE 6]>
	<script src="<%=basePath%>/jslib/DD_belatedPNG.js" type="text/javascript"></script>
	<script>DD_belatedPNG.fix('.png')</script>
	<![endif]-->

</body>
</html>