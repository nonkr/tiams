<%@ page language="java" pageEncoding="UTF-8"%>

<style>
.north_logo_icon {
    position: absolute;
    left: 15px;
    top: 0;
    width: 80px;
    height: 60px;
    background:url(images/518e1fc5be412L7pXg1cpQK.png) no-repeat;
}
.north_logo {
    position: absolute;
    left: 100px;
    top: 28px;
    background:url(images/daoju-logo-bai30.png) no-repeat;
    width:285px;
    height:40px;
}
</style>

<script type="text/javascript" charset="utf-8">
function logout() {
    $.post('${pageContext.request.contextPath}/sys/userAction!logout.action', function() {
        location.replace('${pageContext.request.contextPath}');
    });
}
</script>

<div class="north_logo_icon"></div>
<div class="north_logo"></div>
<div style="position: absolute; right: 0px; bottom: 0px; ">
    <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#index_center_skinMenu',iconCls:'iconPluto-color-swatch'" style="color:#FFF">更换皮肤</a>
    <!-- <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-help">控制面板</a> -->
    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'iconPluto-door-out'" style="color:#FFF" onclick="logout();">注销</a>
</div>
<div id="index_center_skinMenu" style="width: 120px; display: none;">
    <div onclick="$nonkr.changeTheme('default');">default</div>
    <div onclick="$nonkr.changeTheme('bootstrap');">bootstrap</div>
    <div onclick="$nonkr.changeTheme('cupertino');">cupertino</div>
    <div onclick="$nonkr.changeTheme('gray');">gray</div>
    <div onclick="$nonkr.changeTheme('pepper-grinder');">pepper-grinder</div>
    <div onclick="$nonkr.changeTheme('sunny');">sunny</div>
    <div onclick="$nonkr.changeTheme('black');">black</div>
    <div onclick="$nonkr.changeTheme('dark-hive');">dark-hive</div>
    <div onclick="$nonkr.changeTheme('metro');">metro</div>
    <div onclick="$nonkr.changeTheme('metro-blue');">metro-blue</div>
    <div onclick="$nonkr.changeTheme('metro-gray');">metro-gray</div>
    <div onclick="$nonkr.changeTheme('metro-green');">metro-green</div>
    <div onclick="$nonkr.changeTheme('metro-orange');">metro-orange</div>
    <div onclick="$nonkr.changeTheme('metro-red');">metro-red</div>
</div>