package com.jeeplus.common.utils.oss;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.jeeplus.common.config.Global;
/**
 * 
 * @Project       : console
 * @Program Name  : com.jeeplus.common.utils.oss.DownloadUtils.java
 * @Description   : 阿里云工具类
 * @Author        : bianyan
 * @Creation Date : 2017年1月17日 下午2:33:36 
 * @ModificationHistory  
 * Who          When             What 
 * ----------   -------------    -----------------------------------
 * bianyan     2017年1月17日        create
 */
public class DownloadUtils {

  public static ResponseEntity<byte[]> getResponseEntity(String fileName, String zipPath)
      throws IOException {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDispositionFormData("attachment", fileName);
    return new ResponseEntity<>(FileUtils.readFileToByteArray(new File(zipPath)), headers,
        HttpStatus.CREATED);
  }

  /**
   * 
   *  @Description	  : 获取阿里云上传工具类 
   *  @return         : CloudStorageUtil
   *  @Creation Date  : 2017年1月17日14:35:42
   *  @Author         : bianyan
   */
  public static CloudStorageUtil getOssConnection() {
    String keyId = Global.getConfig("oss.access.key");
    String keySecret = Global.getConfig("oss.access.secret");
    String servicePath = Global.getConfig("oss.access.servicePath");
    return new CloudStorageUtil(servicePath, keyId, keySecret);
  }
  
  /**
   * 
   *  @Description	: 获取图片缩略图的下载类
   *  @return
   *  @return         : CloudStorageUtil
   *  @Creation Date  : 2017年1月17日14:35:49
   *  @Author         : bianyan
   */
  public static CloudStorageUtil getPictureOssConnections() {
    String keyId = Global.getConfig("oss.access.key");
    String keySecret = Global.getConfig("oss.access.secret");
    String servicePath = Global.getConfig("oss.access.servicePicturePath");
    return new CloudStorageUtil(servicePath, keyId, keySecret);
  }
  
  /**
   * 
   *  @Description	: 获取阿里云内网的上传得工具类
   *  @return  
   *  @return         : CloudStorageUtil
   *  @Creation Date  : 2017年1月17日14:36:01
   *  @Author         : bianyan
   */
  public static CloudStorageUtil getIntranetOssConnection() {
    String keyId = Global.getConfig("oss.access.key");
    String keySecret = Global.getConfig("oss.access.secret");
    //将链换成内网的路径
    String servicePath = Global.getConfig("oss.access.servicePath");
    return new CloudStorageUtil(servicePath, keyId, keySecret);
  }

}
