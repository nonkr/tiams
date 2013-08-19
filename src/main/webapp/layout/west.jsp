<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>

<script type="text/javascript">
	$("#index_west_tree").tree({
		url : '<%=basePath%>/menuAction!getAllTreeNode.action',
        parentField : 'pid',
		lines : true,
		animate : true,
		onClick: function(node) {
			if (node.attributes && node.attributes.url) {
				var url = '<%=basePath%>' + node.attributes.url;
                addTab({
                	title : node.text,
					closable : true,
					href : url
                });
            }
		}
	});
</script>

<div class="easyui-panel" data-options="title:'菜单',border:false,fit:true">
	<ul id="index_west_tree" class="easyui-tree">
		<%--<li><span>业务管理</span>
			<ul>
				<li data-options="attributes:{'target':'<%=basePath%>/modules/biz/biz_accept.jsp'}"><span>业务受理</span></li>
				<li data-options="attributes:{'target':'<%=basePath%>/modules/biz/biz_query.jsp'}"><span>业务查询</span></li>
			</ul>
		</li>
		<li><span>项目分解</span></li>
	--%></ul>
</div>

