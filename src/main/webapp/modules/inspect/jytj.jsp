<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
    $(function() {
    	$('#jytj_rwbh').combobox({
            url:'combobox_data.json',
            valueField:'id',
            textField:'text',
            panelHeight:'auto'
        });
    	
    	
        //表单美化
        var $uniformed = $(".strong_form").find("input, textarea, select, button").not(".skipThese, .combo-text");
        $uniformed.uniform({
            fileButtonHtml : "选择文件",
            fileDefaultHtml : "未选择"
        });
        $(".uniformed").uniform();
    });

</script>
<style type="text/css">
.jytj_container {
    display: block;
    position: absolute;
    float: left;
    margin: 0;
    padding: 5px;
    border: 1px solid #CCC;
    font: normal 12px Verdana, Arial, Helvetica, sans-serif;
    color: #5A5655;
    background-color: #F8F8F8;
    height: 300px;
    text-align: left;
    overflow: visible;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    width: 968px;
    clear: both;
}
</style>
</head>
<body>
<div class="jytj_container">
<div class="strong_form">
    <table style="margin:auto;">
        <tr>
            <td>
		        <i>任务编号:</i>
                <select id="jytj_rwbh" name="jyRwbh" class="easyui-combobox" style="width:120px;" data-options="required:true">
		        </select>
            </td>
        </tr>
    </table>
</div>
</div>
</body>
</html>