<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
    $(function() {
        $('#sys_role_datagrid').datagrid({
            url : '${pageContext.request.contextPath}/sys/roleAction!datagrid.action',
            fit : true,
            border : false,
            pagination : true,
            idField : 'id',
            fitColumns : true,
            striped : true,
            nowrap : false,
            sortName : 'name',
            sortOrder : 'asc',
            columns : [ [ {
                field : 'id',
                title : '编号',
                width : 150,
                checkbox : true
            }, {
                field : 'name',
                title : '角色名',
                width : 150,
                sortable : true
            }, {
                field : 'authIds',
                width : 150,
                hidden : true
            }, {
                field : 'authNames',
                title : '拥有权限',
                width : 150,
                formatter : function(value, row, index) {
                    return $nonkr.fs('<span title="{0}">{1}</span>', value, value);
                }
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

        $('#sys_role_authIds_combotree').combotree({
            url : '${pageContext.request.contextPath}/sys/authAction!authTreeRecursive.action',
            parentField : 'pid',
            lines : !$nonkr.isLessThanIe8(),
            multiple : true
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
        $('#sys_role_editForm input').val('');
        $('#sys_role_authIds_combotree').combotree('clear');
        $('#sys_role_editDialog').dialog('setButtonHandler', [ '保存', addSave ]);
        $('#sys_role_editDialog').dialog({
            title : '添加'
        });
        $('#sys_role_editDialog').dialog('open');
    }

    function deleteItem() {
        var rows = $('#sys_role_datagrid').datagrid('getChecked');
        if (rows.length > 0) {
            $.messager.confirm('确认', '您是否要删除当前勾选的记录？', function(r) {
                if (r) {
                    var ids = [];
                    $.each(rows, function(i, obj) {
                        ids.push("'" + obj.id + "'");
                    });
                    $.ajax({
                        url : '${pageContext.request.contextPath}/sys/roleAction!delete.action',
                        data : {
                            ids : ids.join(',')
                        },
                        dataType : 'json',
                        success : function(r) {
                            $('#sys_role_datagrid').datagrid('load');
                            $('#sys_role_datagrid').datagrid('clearChecked');
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
        var rows = $('#sys_role_datagrid').datagrid('getChecked');
        if (rows.length === 1) {
            $('#sys_role_editDialog').dialog('setButtonHandler', [ '保存', modifySave ]);
            $('#sys_role_editForm input[name=id]').val(rows[0].id);
            $('#sys_role_editForm input[name=name]').val(rows[0].name);
            
            $('#sys_role_authIds_combotree').combotree('setValues', $nonkr.getList(rows[0].authIds));
            
            
            $('#sys_role_editDialog').dialog({
                title : '修改'
            });
            $('#sys_role_editDialog').dialog('open');
        } else {
            $('#sys_role_datagrid').datagrid('clearChecked');
            $.messager.show({
                title : '提示',
                msg : '请勾选要修改的单条记录！'
            });
        }
    }

    function addSave() {
        $('#sys_role_editForm').form('submit', {
            url : '${pageContext.request.contextPath}/sys/roleAction!add.action',
            success : function(data) {
                var response = $.parseJSON(data);
                var obj = response.obj;
                if (response.success) {
                    $('#sys_role_datagrid').datagrid('insertRow', {
                        index : 0,
                        row : obj
                    });
                    $('#sys_role_editDialog').dialog('close');
                }
                $.messager.show({
                    title : '提示',
                    msg : response.msg
                });
            }
        });
    }

    function modifySave() {
        $('#sys_role_editForm').form('submit', {
            url : '${pageContext.request.contextPath}/sys/roleAction!modify.action',
            success : function(data) {
                var response = $.parseJSON(data);
                var obj = response.obj;
                if (response.success) {
                    $('#sys_role_datagrid').datagrid('updateRow', {
                        index : $('#sys_role_datagrid').datagrid('getRowIndex', obj.id),
                        row : obj
                    });
                    $('#sys_role_editDialog').dialog('close');
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
<table id="sys_role_datagrid"></table>

<div id="sys_role_editDialog" class="easyui-dialog" data-options="modal:true, closed:true, buttons : [ {
                text : '保存',
                iconCls : 'iconPluto-disk',
                plain : true,
                handler : addSave
            }, {
                text : '取消',
                iconCls : 'iconPluto-decline',
                plain : true,
                handler : function() {
                    $('#sys_role_editDialog').dialog('close');
                }
            } ]" style="width:400px;height:300px;">

    <form id="sys_role_editForm" method="post">
        <input type="hidden" name="id" />
        <div class="strong_form" style="margin-left: 0;">
            <p>
                <i>角色名:</i> <input name="name" type="text" class="easyui-validatebox" data-options="required:true" />
            </p>
            <p>
                <i>拥有权限:</i> <input id="sys_role_authIds_combotree" name="authIds" class="skipThese" style="width:200px;" />
            </p>
            <p>
                <i>备注:</i> <input name="remark" />
            </p>
        </div>
    </form>
</div>
</body>
</html>