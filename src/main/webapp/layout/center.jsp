<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
%>

<script type="text/javascript">
	var centerTabs;
	$(function() {
		centerTabs = $('#index_center_tabs').tabs({
			fit : true,
			border : false
		});
	});
	function addTab(obj) {
		if (centerTabs.tabs('exists', obj.title)) {
			centerTabs.tabs('select', obj.title);
		} else {
			$.messager.progress({
                text : '页面加载中....',
                interval : 100
            });
            window.setTimeout(function() {
                try {
                    $.messager.progress('close');
                } catch (e) {
                }
            }, 5000);
			centerTabs.tabs('add', {
				title : obj.title,
				content : '<iframe src="' + obj.href + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
				closable : true,
				tools : [ {
					iconCls : 'icon-mini-refresh',
					handler : function() {
						centerTabs.tabs('getTab', obj.title).panel('refresh');
						$.messager.progress({
			                text : '页面加载中....',
			                interval : 100
			            });
			            window.setTimeout(function() {
			                try {
			                    $.messager.progress('close');
			                } catch (e) {
			                }
			            }, 5000);
					}
				} ]
			});
		}
	}
</script>
<div id="index_center_tabs" style="overflow: hidden;">
	<div title="首页">
		<jsp:include page="../home.jsp"></jsp:include>
	</div>
</div>