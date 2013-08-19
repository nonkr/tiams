<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<link rel="stylesheet" href="<%=basePath%>/jslib/smartwizard/styles/smart_wizard.css" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/jslib/smartwizard/jquery.smartWizard-2.0.min.js"></script>
<style>
.strong_form {
	margin-left: 300px;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#wizard').smartWizard({
			labelNext : '下一步',
			labelPrevious : '上一步',
			labelFinish : '完成',
			onLeaveStep : onLeaveStep,
			onShowStep : onShowStep,
			onFinish : onFinishCallback
		});

		var $uniformed = $(".strong_form").find("input, textarea, select, button").not(".skipThese, .combo-text");
		$uniformed.uniform({
			fileButtonHtml : "选择文件",
			fileDefaultHtml : "未选择"
		});

		$(".uniformed").uniform();

		//            $("#raw_material_info").mouseup(function (event) {
		//                //鼠标左键
		//                if (event.which == 1) {
		//                    console.log($(this).height());
		//                }
		//            });

	});

	function onFinishCallback() {
		//    $('#wizard').smartWizard('showMessage','Finish Clicked');
		$("#biz_accept_form").submit();
	}

	function onLeaveStep(obj) {
		var step_num = parseInt(obj.attr("rel"), 10);
		refresh_height(step_num + 1);
		return true;
	}

	function refresh_height(step_num) {
		var step_content_height = 0;
		if ($("#step-" + step_num).children(".strong_form").size() != 0) {
			step_content_height = $("#step-" + step_num + " .strong_form").actual("height");
		} else {
			$("#step-" + step_num).children("div").each(function(i, val) {
				step_content_height += $(val).height();
			});
		}
		if (step_content_height != null && step_content_height > 240) {
			$(".swMain .stepContainer #step-" + step_num + ".content").height(step_content_height + 70);
			$(".swMain .stepContainer").height(step_content_height + 82);
		} else {
			$(".swMain .stepContainer #step-" + step_num + ".content").height(300);
			$(".swMain .stepContainer").height(312);
		}
	}

	function onShowStep(obj) {
		var step_num = parseInt(obj.attr("rel"), 10);

		switch (step_num) {
		case 3:
		case 5:
			var biz_type = $("#ywlx").val();
			if (biz_type == "a") { //仅显示原材料相关选项
				$(".biz_type_a").show();
				$(".biz_type_b").hide();
			} else if (biz_type == "b") { //仅显示刀具相关选项
				$(".biz_type_a").hide();
				$(".biz_type_b").show();
			}
			refresh_height(3);
			break;
		case 6:
			change_basis_display();
			break;
		case 8:
			if ($("#agree").prop("checked")) {
				$(".buttonFinish").removeClass("buttonDisabled");
			} else {
				$(".buttonFinish").addClass("buttonDisabled");
			}
			break;
		}
		return true;
	}

	function change_basis_display() {
		var biz_type = $("#ywlx").val();
		if (biz_type == "a") { //仅显示原材料相关选项
			$(".biz_type_a").show();
			$(".biz_type_b").hide();

			$("#yj_ycl_cfjy").closest("p").toggle($("#xm_ycl_cfjy").prop("checked"));
			$("#yj_ycl_jxjy").closest("p").toggle($("#xm_ycl_jxjy").prop("checked"));
			$("#yj_ycl_ydjy").closest("p").toggle($("#xm_ycl_ydjy").prop("checked"));
		} else if (biz_type == "b") { //仅显示刀具相关选项
			$(".biz_type_a").hide();
			$(".biz_type_b").show();

			$("#yj_rcl_jldjy").closest("p").toggle($("#xm_rcl_jldjy").prop("checked"));
			$("#yj_rcl_grcdjy").closest("p").toggle($("#xm_rcl_grcdjy").prop("checked"));
			$("#yj_rcl_hhcdjy").closest("p").toggle($("#xm_rcl_hhcdjy").prop("checked"));
			$("#yj_rcl_ydjy").closest("p").toggle($("#xm_rcl_ydjy").prop("checked"));
		}
	}

	function toggleFinishButton() {
		$(".buttonFinish").toggleClass("buttonDisabled");
	}
</script>
</head>

<body>
	<form id="biz_accept_form" action="/admin/biz!accept" method="post">
		<div id="wizard" class="swMain">
			<ul>
				<li><a href="#step-1"> <label class="stepNumber">1</label> <span class="stepDesc"> Step 1<br /> <small>业务受理</small> </span> </a>
				</li>
				<li><a href="#step-2"> <label class="stepNumber">2</label> <span class="stepDesc"> Step 2<br /> <small>客户信息</small> </span> </a>
				</li>
				<li><a href="#step-3"> <label class="stepNumber">3</label> <span class="stepDesc"> Step 3<br /> <small>样品信息</small> </span> </a>
				</li>
				<li><a href="#step-4"> <label class="stepNumber">4</label> <span class="stepDesc"> Step 4<br /> <small>样品核查</small> </span> </a>
				</li>
				<li><a href="#step-5"> <label class="stepNumber">5</label> <span class="stepDesc"> Step 5<br /> <small>检验项目</small> </span> </a>
				</li>
				<li><a href="#step-6"> <label class="stepNumber">6</label> <span class="stepDesc"> Step 6<br /> <small>检验依据</small> </span> </a>
				</li>
				<li><a href="#step-7"> <label class="stepNumber">7</label> <span class="stepDesc"> Step 7<br /> <small>费用核算</small> </span> </a>
				</li>
				<li><a href="#step-8"> <label class="stepNumber">8</label> <span class="stepDesc"> Step 8<br /> <small>服务承诺</small> </span> </a>
				</li>
			</ul>


			<div id="step-1">
				<h2 class="StepTitle">业务受理</h2>

				<div class="strong_form">
					<p>
						<i>业务类型:</i> <select id="ywlx" name="ywlx">
							<option value="a">刀具原材料检验</option>
							<option value="b">刀具成品检验</option>
						</select>
					</p>
					<p>
						<i>受理时间:</i> <input id="slsj" name="slsj" class="easyui-datebox" />
					</p>
				</div>
			</div>
			<div id="step-2">
				<h2 class="StepTitle">客户信息</h2>

				<div class="strong_form">
					<p>
						<i>委托单位:</i> <input id="wtdw" name="wtdw" type="text" class="easyui-validatebox" data-options="required:true" /> <b>*</b>
					</p>

					<p>
						<i>联系地址:</i> <input id="lxdz" name="lxdz" type="text" />

					<p>
						<i>联系人:</i> <input id="lxr" name="lxr" type="text" />
					</p>

					<p>
						<i>手机号码:</i> <input id="sjhm" name="sjhm" type="text" />
					</p>

					<p>
						<i>联系电话:</i> <input id="lxdh" name="lxdh" type="text" />
					</p>

					<p>
						<i>E-Mail:</i> <input id="email" name="email" type="text" />
					</p>
				</div>
			</div>
			<div id="step-3">
				<h2 class="StepTitle">样品信息</h2>

				<div class="strong_form">
					<p>
						<i>工件名称:</i> <input id="gjmc" name="gjmc" type="text" class="easyui-validatebox" data-options="required:true" /> <b>*</b>
					</p>
					<p>
						<i>原材料:</i> <input id="ycl" name="ycl" type="text" class="easyui-validatebox" data-options="required:true" /> <b>*</b>
					</p>

					<p>
						<i>原材料厂家:</i> <input id="yclcj" name="yclcj" type="text" />
					</p>

					<p class="biz_type_a">
						<i>原材料批次:</i> <input id="yclpc" name="yclpc" type="text" />
					</p>

					<p>
						<i>有效尺寸:</i> <input id="yxcc" name="yxcc" type="text" />
					</p>
					<p>
						<i>工艺类别:</i> <input id="gylb" name="gylb" type="text" />
					</p>
					<p class="biz_type_b">
						<i>热处理厂家:</i> <input id="rclcj" name="rclcj" type="text" class="easyui-validatebox" data-options="required:true" /> <b>*</b>
					</p>

					<p class="biz_type_b">
						<i>热处理设备:</i> <input id="rclsb" name="rclsb" type="text" class="easyui-validatebox" data-options="required:true" /> <b>*</b>
					</p>

					<p class="biz_type_b">
						<i>热处理淬火温度:</i> <input id="rclzhwd" name="rclzhwd" type="text" />
					</p>

					<p class="biz_type_b">
						<i>热处理回火温度:</i> <input id="rclhhwd" name="rclhhwd" type="text" />
					</p>

					<p class="biz_type_b">
						<i>热处理几何参数:</i> <input id="rcljhcs" name="rcljhcs" type="file" />
					</p>
					
					<p>
                        <i>其他描述:</i>
                        <textarea id="qtms" name="qtms" rows="4" cols="50"></textarea>
                    </p>
                    
				</div>
			</div>
			<div id="step-4">
				<h2 class="StepTitle">样品核查</h2>

				<div class="strong_form">
					<p>
						<i>对样品核查结果的简单描述:</i>
						<textarea id="yphcms" name="yphcms" rows="15" cols="80"></textarea>
					</p>
				</div>
			</div>
			<div id="step-5">
				<h2 class="StepTitle">检验项目</h2>

				<div class="strong_form">
					<p class="biz_type_a">
						<i>成分检验(原材料):</i> <input type="checkbox" id="xm_ycl_cfjy" name="xm_ycl_cfjy" />
					</p>

					<p class="biz_type_a">
						<i>金相检验(原材料):</i> <input type="checkbox" id="xm_ycl_jxjy" name="xm_ycl_jxjy" />
					</p>

					<p class="biz_type_a">
						<i>硬度检验(原材料):</i> <input type="checkbox" id="xm_ycl_ydjy" name="xm_ycl_ydjy" />
					</p>

					<p class="biz_type_b">
						<i>晶粒度检验(热处理后的金相):</i> <input type="checkbox" id="xm_rcl_jldjy" name="xm_rcl_jldjy" />
					</p>

					<p class="biz_type_b">
						<i>过热程度检验(热处理后的金相):</i> <input type="checkbox" id="xm_rcl_grcdjy" name="xm_rcl_grcdjy" />
					</p>

					<p class="biz_type_b">
						<i>回火程度检验(热处理后的金相):</i> <input type="checkbox" id="xm_rcl_hhcdjy" name="xm_rcl_hhcdjy" />
					</p>

					<p class="biz_type_b">
						<i>硬度检验(热处理后):</i> <input type="checkbox" id="xm_rcl_ydjy" name="xm_rcl_ydjy" />
					</p>
				</div>
			</div>
			<div id="step-6">
				<h2 class="StepTitle">检验依据</h2>

				<div class="strong_form">
					<p class="biz_type_a">
						<i>成分检验(原材料):</i> <select id="yj_ycl_cfjy" name="yj_ycl_cfjy">
							<option value="1">联盟标准</option>
							<option value="2">国际标准</option>
							<option value="3">国家标准</option>
							<option value="4">行业标准</option>
						</select>
					</p>

					<p class="biz_type_a">
						<i>金相检验(原材料):</i> <select id="yj_ycl_jxjy" name="yj_ycl_jxjy">
							<option value="1">联盟标准</option>
							<option value="2">国际标准</option>
							<option value="3">国家标准</option>
							<option value="4">行业标准</option>
						</select>
					</p>

					<p class="biz_type_a">
						<i>硬度检验(原材料):</i> <select id="yj_ycl_ydjy" name="yj_ycl_ydjy">
							<option value="1">联盟标准</option>
							<option value="2">国际标准</option>
							<option value="3">国家标准</option>
							<option value="4">行业标准</option>
						</select>
					</p>

					<p class="biz_type_b">
						<i>晶粒度检验(热处理后的金相):</i> <select id="yj_rcl_jldjy" name="yj_rcl_jldjy">
							<option value="1">联盟标准</option>
							<option value="2">国际标准</option>
							<option value="3">国家标准</option>
							<option value="4">行业标准</option>
						</select>
					</p>

					<p class="biz_type_b">
						<i>过热程度检验(热处理后的金相):</i> <select id="yj_rcl_grcdjy" name="yj_rcl_grcdjy">
							<option value="1">联盟标准</option>
							<option value="2">国际标准</option>
							<option value="3">国家标准</option>
							<option value="4">行业标准</option>
						</select>
					</p>

					<p class="biz_type_b">
						<i>回火程度检验(热处理后的金相):</i> <select id="yj_rcl_hhcdjy" name="yj_rcl_hhcdjy">
							<option value="1">联盟标准</option>
							<option value="2">国际标准</option>
							<option value="3">国家标准</option>
							<option value="4">行业标准</option>
						</select>
					</p>

					<p class="biz_type_b">
						<i>硬度检验(热处理后):</i> <select id="yj_rcl_ydjy" name="yj_rcl_ydjy">
							<option value="1">联盟标准</option>
							<option value="2">国际标准</option>
							<option value="3">国家标准</option>
							<option value="4">行业标准</option>
						</select>
					</p>
				</div>
			</div>
			<div id="step-7">
				<h2 class="StepTitle">费用核算</h2>

				<div class="strong_form">
					<p>
						<i>费用总合:</i> <input type="text" id="fyzh" name="fyzh">
					</p>
				</div>
			</div>
			<div id="step-8">
				<h2 class="StepTitle">服务承诺</h2>
				<jsp:include page="biz_commitment.jsp"></jsp:include>
				<div style="float: right;margin-right: 30%;margin-top:10px;height: 50px;">
					<span> <label style="font-size: 25px;vertical-align: -5px;">同意&nbsp;</label> <input type="checkbox" id="agree" class="uniformed" onclick="toggleFinishButton();" /> </span>
				</div>
			</div>
		</div>
	</form>
</body>
</html>