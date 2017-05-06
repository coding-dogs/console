package com.jeeplus.modules.sys.web;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.oss.CloudStorageUtil;
import com.jeeplus.common.utils.oss.DownloadUtils;

/**
 * 
 * @Project       : console
 * @Program Name  : com.jeeplus.modules.sys.web.OssController.java
 * @Description   : 阿里云操作类
 * @Author        : bianyan
 * @Creation Date : 2017年1月18日 下午2:46:08 
 * @ModificationHistory  
 * Who          When             What 
 * ----------   -------------    -----------------------------------
 * bianyan     2017年1月18日        create
 */
@RestController
@RequestMapping(value = "${adminPath}/sts")
public class OssController {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * 根据上传路径，获取直接上传云服务参数
   * @param dir
   * @return
   * @update 2016-09-03 11:46:20 bingo刑天 去除參數獲取方式
   * @update 2017年1月17日16:27:58 bianyan 
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public Map<String, String> getUploadPara(String dir) {
    int timeOut = 60 * 30;
    try (CloudStorageUtil util = DownloadUtils.getOssConnection();) {
      return util.getUploadParas(Global.getConfig("oss.access.bucket"), dir, timeOut);
    } catch (UnsupportedEncodingException e) {
      
    }
    return null;
  }
  
  /**
   * 
   *  @Description	  : 云端图片下载展示 
   *  @return         : Map<String,Object>
   *  @Creation Date  : 2017年1月18日 下午3:59:19 
   *  @Author         : bianyan
   */
  @RequestMapping(value ="/file" , method =RequestMethod.POST)
  public Map<String, Object> showUploadPara(String path){
    CloudStorageUtil cloudStorageUtil = null;
    Map<String,Object> resuMap = null;
    try {
      // 获取阿里云连接信息
      cloudStorageUtil = DownloadUtils.getIntranetOssConnection();
      // 获取容器名称
      String bucketName = Global.getConfig("oss.access.bucket");
      // 获取下载路径
      URL url = cloudStorageUtil.getDownLoadURL(bucketName, path);
      // 转码操作
      String urls = java.net.URLEncoder.encode(String.valueOf(url), "utf-8");
      resuMap = new HashMap<>();
      // 解码操作
      resuMap.put("path",URLDecoder.decode(urls));
      return resuMap;
    }catch(UnsupportedEncodingException e){
      logger.error(" method showUploadPara transcoding is error");
    } catch (Exception e) {
      logger.error("method showUploadPara is error", e);
    }finally{
      if (cloudStorageUtil != null) {
      // 关闭连接
      cloudStorageUtil.close();
      }
    }
    return null;
  }
  
  /**
   * 
   *  @Description	  : 云端图片删除 
   *  @return         : Map<String,Object>
   *  @Creation Date  : 2017年1月18日 下午5:53:47 
   *  @Author         : bianyan
   */
  @RequestMapping(value ="/file" , method =RequestMethod.DELETE)
  public Map<String, Object> delUploadPara(String path){
    CloudStorageUtil cloudStorageUtil = null;
    Map<String,Object> resuMap = null;
    try {
      // 获取阿里云连接信息
      cloudStorageUtil = DownloadUtils.getIntranetOssConnection();
      // 获取容器名称
      String bucketName = Global.getConfig("oss.access.bucket");
      // 执行云端删除方法
      cloudStorageUtil.delDir(bucketName, path);
      // 执行Service方法  TODO  
      return resuMap;
    } catch (Exception e) {
      logger.error("method delUploadPara is error", e);
    }finally{
      // 关闭连接
      cloudStorageUtil.close();
    }
    return null;
  }
  
  
  
}
