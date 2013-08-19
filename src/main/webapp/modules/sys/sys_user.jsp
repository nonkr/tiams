<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$('#sys_user_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/sys/userAction!datagrid.action',
			fit : true,
			border : false,
			pagination : true,
			idField : 'id',
			fitColumns : true,
			striped : true,
			sortName : 'name',
			sortOrder : 'asc',
			singleSelect : true,
			checkOnSelect : false,
			selectOnCheck : false,
			//rownumbers : true,
			//pageSize : 5,
			//pageList : [ 5, 10, 20, 30, 40, 50 ],
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			}, {
				field : 'name',
				title : '用户名',
				width : 150,
				sortable : true
			}, {
				field : 'pwd',
				title : '密码',
				width : 150,
				hidden : true
			/*
			formatter : function(value, row, index) {
				return '<span title="' + value +'">' + value + '</span>';
			}
			 */
			}, {
				field : 'role',
				title : '所属角色',
				width : 150,
				sortable : true
			}, {
				field : 'createdatetime',
				title : '创建时间',
				width : 150,
				sortable : true
			}, {
				field : 'modifydatetime',
				title : '最后修改时间',
				width : 150,
				sortable : true
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
			} ],
			onCheck : function() {
				$('#sys_user_datagrid').datagrid('clearSelections');
			},
			onCheckAll : function() {
				$('#sys_user_datagrid').datagrid('clearSelections');
			},
			onSelect : function() {
				$('#sys_user_datagrid').datagrid('clearChecked');
			},
			onSelectAll : function() {
				$('#sys_user_datagrid').datagrid('clearChecked');
			}
		});

		//表单美化
		var $uniformed = $(".strong_form").find("input, textarea, select, button").not(".skipThese, .combo-text");
		$uniformed.uniform({
			fileButtonHtml : "选择文件",
			fileDefaultHtml : "未选择"
		});
		$(".uniformed").uniform();

	});

	function searchItem() {
		$('#sys_user_datagrid').datagrid('load', $('#sys_user_searchForm').serializeObject());
	}

	function clearSearchOptions() {
		$('#sys_user_searchForm input').val('');
		$('#sys_user_datagrid').datagrid('load', {});
	}

	function addItem() {
		$('#sys_user_editForm input').val('');
		$('#sys_user_editDialog').dialog('setButtonHandler', [ '保存', addSave ]);
		$('#sys_user_editDialog').dialog({
			title : '添加'
		});
		$('#sys_user_editDialog').dialog('open');
	}

	function deleteItem() {
		var rows = $('#sys_user_datagrid').datagrid('getChecked');
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要删除当前勾选的记录？', function(r) {
				if (r) {
					var ids = [];
					$.each(rows, function(i, obj) {
						ids.push("'" + obj.id + "'");
					});
					$.ajax({
						url : '${pageContext.request.contextPath}/sys/userAction!delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(r) {
							$('#sys_user_datagrid').datagrid('load');
							$('#sys_user_datagrid').datagrid('clearChecked');
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					});
				}
			});
		} else {
			$.messager.show({
				title : '提示',
				msg : '请勾选要删除的记录！'
			});
		}
	}

	function modifyItem() {
		var rows = $('#sys_user_datagrid').datagrid('getChecked');
		if (rows.length === 1) {
			$('#sys_user_editDialog').dialog('setButtonHandler', [ '保存', modifySave ]);
			$('#sys_user_editForm input[name=id]').val(rows[0].id);
			$('#sys_user_editForm input[name=name]').val(rows[0].name);
			$('#sys_user_editForm input[name=pwd]').val(rows[0].pwd);
			$('#sys_user_editForm input[name=pwd2]').val(rows[0].pwd);
			$('#sys_user_editDialog').dialog({
				title : '修改'
			});
			$('#sys_user_editDialog').dialog('open');
		} else {
			$('#sys_user_datagrid').datagrid('clearChecked');
			$.messager.show({
				title : '提示',
				msg : '请勾选要修改的单条记录！'
			});
		}
	}

	function addSave() {
		$('#sys_user_editForm').form('submit', {
			url : '${pageContext.request.contextPath}/sys/userAction!add.action',
			onSubmit : function() {
				console.log("addSave");
			},
			success : function(data) {
				var response = $.parseJSON(data);
				var obj = response.obj;
				if (response.success) {
					$('#sys_user_datagrid').datagrid('insertRow', {
						index : 0,
						row : obj
					});
					$('#sys_user_editDialog').dialog('close');
				}
				$.messager.show({
					title : '提示',
					msg : response.msg
				});
			}
		});
	}

	function modifySave() {
		$('#sys_user_editForm').form('submit', {
			url : '${pageContext.request.contextPath}/sys/userAction!modify.action',
			onSubmit : function() {
				console.log("modifySave");
			},
			success : function(data) {
				var response = $.parseJSON(data);
				var obj = response.obj;
				if (response.success) {
					$('#sys_user_datagrid').datagrid('updateRow', {
						index : $('#sys_user_datagrid').datagrid('getRowIndex', obj.id),
						row : obj
					});
					$('#sys_user_editDialog').dialog('close');
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
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north', title:'查询条件',border:false" style="height:100px;">
		<form id="sys_user_searchForm">
			检索用户名称(可模糊查询)：<input name="name" /> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchItem();">查询</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'iconPluto-decline'" onclick="clearSearchOptions();">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="sys_user_datagrid"></table>
	</div>
</div>

<div id="sys_user_editDialog" class="easyui-dialog" data-options="modal:true, closed:true, buttons : [ {
                text : '保存',
                iconCls : 'iconPluto-disk',
                plain : true,
                handler : addSave
            }, {
                text : '取消',
                iconCls : 'iconPluto-decline',
                plain : true,
                handler : function() {
                    $('#sys_user_editDialog').dialog('close');
                }
            } ]" style="width:400px;height:300px;">

	<form id="sys_user_editForm" method="post">
		<input type="hidden" name="action" /> <input type="hidden" name="id" />
		<div class="strong_form" style="margin-left: 0;">
			<p>
				<i>用户名:</i> <input name="name" type="text" class="easyui-validatebox" data-options="required:true" /> <b>*</b>
			</p>
			<p>
				<i>密码:</i> <input name="pwd" type="password" class="easyui-validatebox" data-options="required:true" /> <b>*</b>
			</p>
			<p>
				<i>密码确认:</i> <input name="pwd2" type="password" class="easyui-validatebox" data-options="required:true" validType="eqPassword['#sys_user_editForm input[name=pwd]']" /> <b>*</b>
			</p>
		</div>
	</form>
	<!-- 
	<table>
		<tr>
			<th>用户名:</th>
			<td><input id="name" name="name" type="text" class="easyui-validatebox" data-options="required:true" />
			</td>
		</tr>
		<tr>
			<th>密码:</th>
			<td><input id="pwd" name="pwd" type="password" class="easyui-validatebox" data-options="required:true" />
			</td>
		</tr>
		<tr>
			<th>密码确认:</th>
			<td><input id="pwd2" name="pwd2" type="password" class="easyui-validatebox" data-options="required:true" />
			</td>
		</tr>
	</table>-->
</div>
</body>
</html>