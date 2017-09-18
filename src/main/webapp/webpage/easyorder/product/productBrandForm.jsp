<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>商品品牌</title>
<meta name="decorator" content="default" />
<link rel="stylesheet" href="${ctxStatic}/easy-uploader/easy-uploader.css">
<script type="text/javascript">
	var validateForm;
	function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if (validateForm.form()) {
			$("#inputForm").submit();
			return true;
		}

		return false;
	}
	$(document).ready(
			function() {
				validateForm = $("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});

			});
</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="productBrand"
		action="${ctx}/productManager/productBrand/save" method="post"
		class="form-horizontal">
		<input type="hidden" id="id" name="id" value="${productBrand.id}" />
		<sys:message content="${message}" />
		<table
			class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><em
							class="required-tag">* </em>品牌名称:</label></td>
					<td class="width-35"><form:input path="name"
							htmlEscape="false" maxlength="50" class="form-control required" /></td>
					<td class="width-15" class="active"><label class="pull-right"><em
							class="required-tag">* </em>品牌简称:</label></td>
					<td class="width-35"><form:input path="simpleName"
							htmlEscape="false" maxlength="50" class="form-control required" /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><em
							class="required-tag">* </em>排序值:</label></td>
					<td class="width-35"><form:input path="sort"
							class="form-control required" /></td>
					<td class="width-15 active"><label class="pull-right">备注:</label></td>
					<td class="width-35"><form:textarea path="remarks"
							htmlEscape="false" rows="3" maxlength="300" class="form-control " /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">品牌LOGO:</label></td>
					<td class="width-35">
						<input type="hidden" name="logoUrl" id="logoUrl" value="${productBrand.logoUrl}"/>
						<div id="easy-uploader"></div>
					</td>
				</tr>
			</tbody>
		</table>
	</form:form>
	
	<script type="text/javascript" src="${ctxStatic}/easy-uploader/easy-uploader.js"></script>
	<script type="text/javascript">
	var pictureDatas = [{
		url: "${productBrand.logoUrl}"
	}];
	var mode = ("${productBrand.action}" == "view") ? "readonly" : "normal";
	// 上传插件参照此处使用
	$("#easy-uploader").easyUploader({
		// 回显图片数据
		datas: pictureDatas,
		uploadUrl : ctx + "/upload/common/file?type=brand",
		success: function(uploader, data) {			// 此函数必须返回图片URL，以便于预览
			var fileUrl;
			if(SUCCESS_CODE === data.code) {
				uploader.find('.hidden-value').val(data.result);
				fileUrl = data.result;
				$('#logoUrl').val(fileUrl);
			} else {
				top.layer.alert(data.msg);
			}
			return fileUrl;
		},
		error: function() {
			top.layer.alert('上传失败,请联系管理员');
		},
		targetName: 'pictures',							// 后台接收文件对象属性的属性名
		fileTypes: 'image/jpg,image/jpeg,image/png',	// 限制文件格式(相应格式后缀名)，多个以','分割
		maxSize: 5 * 1024,								// 限制单文件大小					
		maxLength: 1,									// 限制文件个数
		uploaderError : function(detailMsg, simpleMsg) {// 上传过程出错处理函数，detailMsg为英文错误提示，simpleMsg为简体中文错误提示
			top.layer.alert(simpleMsg);
		},
		uploaderBtnClass: 'btn btn-success',			// 上传按钮样式
		mode: mode,
		closeHandler : function(uploader) {				// 点击图片右上角的移除按钮时的处理函数
			var $img = uploader.find('.file-preview').find('img');
			if($img.length > 0) {
				$('#logoUrl').val('');
			}
		}
	});
	</script>
</body>
</html>