<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="../../include/taglib.jsp"%>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<title>OSS web直传</title>
	<link rel="stylesheet" type="text/css" href="style.css"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
</head>
<body>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="ctxStatic" value="${ctxStatic}" />
<!-- <h2>OSS web直传---直接在JS签名</h2>
<ol>
<li>基于plupload封装 </li>
<li>支持html5,flash,silverlight,html4 等协议上传</li>
<li>可以运行在PC浏览器，手机浏览器，微信</li>
<li>可以选择多文件上传</li>
<li>显示上传进度条</li>
<li>可以控制上传文件的大小</li>
<li>最关键的是，让你10分钟之内就能移植到你的系统，实现以上牛逼的功能！</li>
<li>注意：此方法是直接在前端签名，有accessid/accesskey泄漏的风险</li>
<li>注意一点，bucket必须设置了Cors(Post打勾）,不然没有办法上传</li>
<li>注意一点，把upload.js 里面的host/accessid/accesskey改成您上传所需要的信息即可</li>
</ol>
<br> -->

<h4>您所选择的文件列表：</h4>
<div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>

<br/>

<div id="container">
	<a id="selectfiles" href="javascript:void(0);" class='btn'>选择文件</a>
	<a id="postfiles" href="javascript:void(0);" class='btn'>开始上传</a>
	<input type="text" id="paths" >
	<img id="imgId" />
</div>

<form action="#" method="post" id="subForm">
<div style="margin-bottom: 20px;">
			<input id="sole" type="text" style="width:400px;" data-options="required:true">
		</div>
		<div style="margin-bottom: 20px;">
			<span style="color:red;">*</span>票据凭证：<button id="up">选择文件</button>
		</div>
		<div style="margin-bottom: 20px;" id="view">
		</div>
		<div style="text-align:center; padding:5px 0; margin-top: 20px;">
			<a id="submit" href="javascript:ck()" style="width:80px">上传</a>
			<a id="submit" href="javascript:del()" style="width:80px">删除</a>
			<a id="cancel" class="easyui-linkbutton" data-options="iconCls:'icon-no',text:'取消'" style="width:80px"></a>
		</div>
		</form>
<pre id="console"></pre>

<p>&nbsp;</p>

</body>
<script type="text/javascript" src="${ctxStatic}/oss/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="${ctxStatic}/oss/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="${ctxStatic}/oss/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="${ctxStatic}/oss/base64.js"></script>
<script type="text/javascript" src="${ctxStatic}/oss/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery-1.8.0.min.js"></script>
<script>

</script>
<script type="text/javascript" src="${ctxStatic}/oss/upload.js"></script>
</html>

