var ctx = $("#ctx").val();
//oss相关
var accessid='';
var host='http://ljt-sit-oss.oss-cn-beijing.aliyuncs.com';
//加密
var signature = '';
var policy = '';
//TODO 票据凭证在OSS中的存储路径
/**
* 机构充值文件存放规则：
* 租户ID/年(yy)/月(mm)/文件类型(recharges)/ 
*/
var myDate = new Date();
var year=myDate.getFullYear(); 
var month=myDate.getMonth()+1;
//暂定租户ID为1便于测试，后面修改
var dir ='1'+'/'+year+'/'+month+'/';
var currFileName = '';
var objectName = '';



//获取随机字符串
function randomString(len) {
	len = len || 32;
	var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
	var maxPos = chars.length;
	var pwd = '';
	for (i = 0; i < len; i++) {
		pwd += chars.charAt(Math.floor(Math.random() * maxPos));
	}
	return pwd;
}

//获取文件后缀名
function getSuffix(filename) {
    var pos = filename.lastIndexOf('.');
    var suffix = '';
    if (pos != -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}

//返回计算后的文件名
function calculateObjectName(filename){
	var suffix = getSuffix(filename);
	currFileName = randomString(10) + suffix;
	objectName = dir + currFileName;
}

/**
* 请求Token
*/
$.ajax({
	   type: "GET",
	   url: ctx+"/sts/?dir="+dir,
	   success: function(msg){
		   accessid = msg.accessid;
		   host = msg.host;
		   signature = msg.signature;
		   policy = msg.policy;
	   }
	});


//上传主键相关信息
var upload = {};
upload.variable={
	fileName :''
}

upload.view={
	previewImage:function(file,callback){//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
		//if(!file || !/image\//.test(file.type)) return; //确保文件是图片
		if(file.type=='image/gif'){//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
			var fr = new mOxie.FileReader();
			fr.onload = function(){
				callback(fr.result);
				fr.destroy();
				fr = null;
			}
			fr.readAsDataURL(file.getSource());
		}else{
			var preloader = new mOxie.Image();
			preloader.onload = function() {
				preloader.downsize( 60, 60 );//先压缩一下要预览的图片,宽300，高300
				var imgsrc = preloader.type=='image/jpeg' ? preloader.getAsDataURL('image/jpeg',80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
				callback && callback(imgsrc); //callback传入的参数为预览图片的url
				preloader.destroy();
				preloader = null;
			};
			preloader.load( file.getSource() );
		}	
	}
}
upload.action={
	//设置上传时时的参数，由于plupload
	setUploadParam:function(up, filename){
		//如果文件名不为空
	    if (filename != '') {
	        //计算文件名称
	        calculateObjectName(filename);
	    }
	    new_multipart_params = {
	        'key' : objectName,
	        'policy': policy,
	        'OSSAccessKeyId': accessid, 
	        'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
	        'signature': signature
	    };

	    up.setOption({
	        'url': host,
	        'multipart_params': new_multipart_params
	    });
	}
}

var uploaders = new plupload.Uploader({ //实例化一个plupload上传对象
	browse_button : 'up',
	multi_selection:false,
	filters: {
		 mime_types : [ //只允许上传图片和zip文件
            { title : "Image files", extensions : "jpg,gif,png" }, 
            { title : "Zip files", extensions : "zip" }
          ],
		  prevent_duplicates : false //不允许选取重复文件
		},
	url : host,
	flash_swf_url : ctx+'/static/oss/plupload-2.1.2/js/Moxie.swf',
	silverlight_xap_url :ctx+'/static/oss/plupload-2.1.2/js/Moxie.xap',
	init: {
		
		//当Init事件发生后触发
		PostInit: function(uploader) {
			
		},
		
		//当文件添加到上传队列后触发
		FilesAdded: function(uploader,files) {
			var html = '';
			fileName = files[0].name;
			//如果文件类型为压缩文件，使用预定义的压缩文件图标
			if(files[0].type=='application/x-zip-compressed'){
				html='<img id="'+files[0].id+'" alt="'+files[0].name+'" src="/admin/static/images/zip.png" width="60px" height="60px" class="mask">';
				$(html).appendTo('#view');
			}else{
				upload.view.previewImage(files[0],function(imgsrc){
					html ='<img id="'+files[0].id+'" alt="'+files[0].name+'" src="'+imgsrc+'" width="60px" height="60px" class="mask">';
					$(html).appendTo('#view');
				});
			}
		},

		//当队列中的某一个文件正要开始上传前触发
		BeforeUpload: function(uploader,file) {
			upload.action.setUploadParam(uploader, file.name);
        },

        //会在文件上传过程中不断触发，可以用此事件来显示上传进度
		UploadProgress: function(uploader,file) {
			
		},

		//当队列中的某一个文件上传完成后触发
		FileUploaded: function(uploader,file,responseObject) {
			if(responseObject.status==200){
				//如果上传队列中剩余数量为0，说明全部上传完成
				if(uploaders.total.queued==0){
					upload.variable.fileName+=currFileName;
				}else{
					upload.variable.fileName+=currFileName+',';
				}
			}
		},
		//当发生错误时触发
		Error: function(uploader,errObject) {
		}
	}		
});

uploaders.init(); //初始化

//removeFile(file)	从上传队列中移除文件，参数file为plupload文件对象或先前指定的文件名称
$('.mask').live('click',function(){
	var id=$(this).attr("id");
	uploaders.removeFile(id);
	$(this).remove();
});

function ck(){
	if(uploaders.total.queued>0){
		uploaders.start(); //开始上传
	}
}

function del(){
	var path = "1/2017/1/KTDifH5NJt.png";
	$.ajax({
		   type: "DELETE",
		   url: ctx+"/sts/file?path="+path,
		   success: function(msg){
			   $("#paths").val(msg.path);
			   $("#imgId").attr('src',msg.path);
		   }
		});
}
