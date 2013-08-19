<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var $treegrid;
	$(function() {
		$treegrid = $('#sys_auth_treegrid').treegrid({
			url : '${pageContext.request.contextPath}/sys/authAction!treegrid.action',
			fit : true,
			border : false,
			idField : 'id',
			parentField : 'pid',
			treeField : 'name',
			fitColumns : true,
			striped : true,
			nowrap : false,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'name',
				title : '权限名',
				width : 150,
				formatter : function(value) {
					if (value) {
						return $nonkr.fs('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				field : 'url',
				title : '权限地址',
				width : 150
			}, {
				field : 'pid',
				title : '上级权限',
				width : 150,
				hidden : true
			}, {
				field : 'remark',
				title : '备注',
				width : 150
			} ] ],
			toolbar : [ {
				text : '添加',
				iconCls : 'iconPluto-add',
				handler : addItem
			}, '-', {
				text : '删除',
				iconCls : 'iconPluto-delete',
				handler : deleteItem
			}, '-', {
				text : '修改',
				iconCls : 'iconPluto-pencil',
				handler : modifyItem
			} ]
		});

		//表单美化
		var $uniformed = $(".strong_form").find("input, textarea, select, button").not(".skipThese, .combo-text");
		$uniformed.uniform({
			fileButtonHtml : "选择文件",
			fileDefaultHtml : "未选择"
		});
		$(".uniformed").uniform();
	});

	function addItem() {
		$('#sys_auth_editForm input').val('');
		$('#sys_authIds_combotree').combotree({
			url : '${pageContext.request.contextPath}/sys/authAction!authTreeRecursive.action',
			lines : !$nonkr.isLessThanIe8()
		});

		$('#sys_auth_editDialog').dialog('setButtonHandler', [ '保存', addSave ]);
		$('#sys_auth_editDialog').dialog({
			title : '添加'
		});
		$('#sys_auth_editDialog').dialog('open');
	}

	function deleteItem() {
		var data = $treegrid.treegrid('getSelected');
		if(data === null) {
			$.messager.show({
                title : '提示',
                msg : '请选择要删除的记录！'
            });
		} else {
			$.messager.confirm('确认', '您是否要删除当前选择的记录？', function(r) {
                if (r) {
                    $.ajax({
                        url : '${pageContext.request.contextPath}/sys/authAction!delete.action',
                        data : {
                            id : data.id
                        },
                        dataType : 'json',
                        success : function(r) {
                            $treegrid.treegrid('reload');
                            $treegrid.treegrid('clearSelections');
                            $.messager.show({
                                title : '提示',
                                msg : r.msg
                            });
                        }
                    });
                }
            });
		}
	}

	function modifyItem() {
		var data = $treegrid.treegrid('getSelected');
		if(data === null) {
			$treegrid.treegrid('clearSelections');
            $.messager.show({
                title : '提示',
                msg : '请选择要修改的记录！'
            });
		} else {
			$('#sys_auth_editDialog').dialog('setButtonHandler', [ '保存', modifySave ]);
            $('#sys_auth_editForm input[name=id]').val(data.id);
            $('#sys_auth_editForm input[name=name]').val(data.name);
            $('#sys_auth_editForm input[name=url]').val(data.url);
            $('#sys_auth_editForm input[name=seq]').val(data.seq);
            $('#sys_auth_editForm input[name=remark]').val(data.remark);

            $('#sys_authIds_combotree').combotree({
                url : '${pageContext.request.contextPath}/sys/authAction!authTreeRecursive.action',
                lines : !$nonkr.isLessThanIe8()
            });
            $('#sys_authIds_combotree').combotree('setValues', $nonkr.getList(data.pid));

            $('#sys_auth_editDialog').dialog({
                title : '修改'
            });
            $('#sys_auth_editDialog').dialog('open');
		}
	}

	function addSave() {
		$('#sys_auth_editForm').form('submit', {
			url : '${pageContext.request.contextPath}/sys/authAction!add.action',
			success : function(data) {
				var response = $.parseJSON(data);
				var obj = response.obj;
				if (response.success) {
					$treegrid.treegrid('reload');
					$('#sys_auth_editDialog').dialog('close');
				}
				$.messager.show({
					title : '提示',
					msg : response.msg
				});
			}
		});
	}

	function modifySave() {
		$('#sys_auth_editForm').form('submit', {
			url : '${pageContext.request.contextPath}/sys/authAction!modify.action',
			success : function(data) {
				var response = $.parseJSON(data);
				var obj = response.obj;
				if (response.success) {
					$treegrid.treegrid('reload');
					$('#sys_auth_editDialog').dialog('close');
				}
				$.messager.show({
					title : '提示',
					msg : response.msg
				});
			}
		});
	}
</script>
</head>

<body>
	<table id="sys_auth_treegrid"></table>

	<div id="sys_auth_editDialog" class="easyui-dialog" data-options="modal:true, closed:true, buttons : [ {
                text : '保存',
                iconCls : 'iconPluto-disk',
                plain : true,
                handler : addSave
            }, {
                text : '取消',
                iconCls : 'iconPluto-decline',
                plain : true,
                handler : function() {
                    $('#sys_auth_editDialog').dialog('close');
                }
            } ]" style="width:430px;height:350px;">

		<form id="sys_auth_editForm" method="post">
			<input type="hidden" name="id" />
			<div class="strong_form" style="margin-left: 0;">
				<p>
					<i>权限名:</i> <input name="name" class="easyui-validatebox" data-options="required:true" />
				</p>
				<p>
					<i>权限地址:</i> <input name="url" style="width:210px;" />
				</p>
				<p>
					<i>上级权限:</i> <input id="sys_authIds_combotree" name="pid" class="skipThese" style="width:200px;" />
				</p>
				<p>
					<i>排序:</i> <input name="seq" style="width:50px;" />
				</p>
				<p>
					<i>备注:</i> <input name="remark" style="width:200px;" />
				</p>
			</div>
		</form>
	</div>
</body>
</html>