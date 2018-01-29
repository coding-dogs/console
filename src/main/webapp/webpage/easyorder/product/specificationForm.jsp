<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>商品规格管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	var validateForm;
	function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if (validateForm.form()) {
			$('#children').val(JSON.stringify(wrapJson()));
			$("#inputForm").submit();
			return true;
		}

		return false;
	}
	var readonly = "${specification.id}";
	$(document).ready(function() {
				var validateConfig = {
					submitHandler : function(form) {
						$('#children').val(JSON.stringify(wrapJson()));
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
				};
				if(!readonly) {
					validateConfig.rules = {
						no: {
							required: true,
							remote: {
								url: '${ctx}/productManager/specification/validate',
								type: 'GET',
								data: {
									no : function() {
										return $('#no').val();
									}
								}
							}
						}
					};
					validateConfig.messages = {
						no: {
							required: "请输入规格编号",
							remote: "此规格编号已存在"
						}
					};
				}
				validateForm = $("#inputForm").validate(validateConfig);

			});
</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="specification"
		action="${ctx}/productManager/specification/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="children"/>
		<sys:message content="${message}" />
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
				<tr>
					<td colspan="4" align="left">多规格类型</td>
				</tr>
				<tr>
					<td  class="width-15 active">
						<label class="pull-right"><font color="red">*</font>规格名称:</label>
					</td>
		         	<td class="width-35" >
			         	<form:input path="name" htmlEscaoe="false" placeholder="请输入规格名称" maxlength="50" class="form-control required"/>
		         	</td>
		         	<td  class="width-15 active"><label class="pull-right"><font color="red">*</font>规格编号</label></td>
		         	<td  class="width-35" >
		         		<form:input path="no" readonly="${not empty specification.id}" htmlEscaoe="false" placeholder="请输入规格编号" maxlength="300" class="form-control required"/>
		         	</td>
		     	</tr>
			</tbody>
		</table>
		<table id="subSpecTable" class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
				<tr>
					<td colspan="4" align="left">规格值</td>
				</tr>
				<tr>
					<th>序号</th>
					<th>子规格名称</th>
					<th>子规格编号</th>
					<th>操作</th>
		     	</tr>
			</tbody>
		</table>
		<a href="javascript:void(0);" id="addSubSpec">添加子规格</a>
		
	</form:form>
	
	<script type="text/javascript" src="${ctxStatic}/easyorder/js/common/own-validation.js"></script>
	<script type="text/javascript">
		var subIndex = 0;
		function createRow(subName, subNo) {
			var subTable = $("#subSpecTable").find('tbody');
			var $tr = $('<tr class="sub-spec-data">');
			var $seqTd = $('<td>');
			var $subNameTd = $('<td>');
			var $subNoTd = $('<td>');
			var $operaTd = $('<td>');
			
			subTable.append($tr);
			$seqTd.appendTo($tr);
			$subNameTd.appendTo($tr);
			$subNoTd.appendTo($tr);
			$operaTd.appendTo($tr);
			
			subIndex++;
			var $seqSpan = $('<span class="sub-seq">');
			$seqSpan.appendTo($seqTd);
			$seqSpan.html(subIndex);
			
			var $subNameInput = $('<input type="text" name="subName'+ subIndex +'" class="form-control sub-spec-name required">');
			var $subNoInput = $('<input type="text" name="subNo'+ subIndex +'" class="form-control sub-spec-no required specificationNo">');
			$subNameInput.appendTo($subNameTd);
			$subNoInput.appendTo($subNoTd);
			if(subName) {
				$subNameInput.val(subName);
			}
			if(subNo) {
				$subNoInput.val(subNo);
			} else {
				$subNoInput.val(subIndex);
			}
			
			var $del = $('<a>');
			$del.text('删除');
			$del.attr('href', 'javascript:void(0);');
			$del.appendTo($operaTd);
			
			$del.on('click', function(e) {
				var _this = $(this);
				var $tr = _this.parents('tr');
				if($tr.length != 0) {
					subIndex--;
					var trIndex = $tr.index();
					var $sibTrs = $tr.siblings();
					if($sibTrs.length > 0) {
						$.each($sibTrs, function(index, sibTr){
							var $sibTr = $(sibTr);
							if($sibTr.index() <= trIndex) {
								return true;
							}
							var $subSeq = $sibTr.find('td span.sub-seq');
							if($subSeq.length > 0) {
								var seq = $subSeq.html();
								var newSeq = parseInt(seq) - 1;
								$subSeq.html(newSeq);
							}
						});
					}
					$tr.remove();
				}
			});
		}
		
		var specId = '${specification.id}';
		if(specId) {
			// 查询规格下的子规格项目
			$.ajax({
				url: '${ctx}/productManager/specification/items',
				type: "GET",
				data: {specificationId: specId},
				dataType: 'JSON',
				success: function(data) {
					if(data.code == SUCCESS_CODE) {
						var specItem = data.result;
						if(specItem && specItem.length != 0) {
							$.each(specItem, function(index, si) {
								createRow(si.name, si.no);
							});
						}
					} else {
						top.layer.alert(data.msg);
					}
				},
				error: function() {
				}
				
			});
		} else {
			createRow();
		}
		
		$('#addSubSpec').on('click', function(e) {
			createRow();
		});
		
		
		function wrapJson() {
			var children = [];
			var $trs = $('#subSpecTable').find('.sub-spec-data');
			if($trs.length == 0) {
				return children;
			}
			$.each($trs, function(index, tr) {
				var $tr = $(tr);
				var $subName = $tr.find('td .sub-spec-name');
				var $subNo = $tr.find('td .sub-spec-no');
				
				var subData = {};
				subData.name = $subName.val();
				subData.no = $subNo.val();
				
				children.push(subData);
			});
			return children;
		}
	</script>
</body>
</html>