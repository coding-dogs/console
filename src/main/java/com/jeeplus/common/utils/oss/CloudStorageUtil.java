package com.jeeplus.common.utils.oss;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.SimplifiedObjectMeta;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.modules.oss.entity.FileInfoVO;



/**
 * 阿里云存储工具类
 * 
 * @author DannyGe
 */
public class CloudStorageUtil implements Closeable {
  private static final Logger logger = LoggerFactory.getLogger(CloudStorageUtil.class);
  // 云存储中路径分隔符（与本地操作系统无关）
  public static final String OSS_FILE_DELLIMITER = "/";

  private OSSClient ossClient = null;
  private String endpoint = null;
  private String accessKeyId = null;
  
  public OSSClient getClient() {
	  return ossClient;
  }

  /**
   * @param endpoint 云节点地址：北京oss-cn-beijing.aliyuncs.com
   * @param accessKeyId
   * @param accessKeySecret
   * @updateb: lichaoyue
   * @param endpoint 内网的云节点地址：vpc100-oss-cn-qingdao.aliyuncs.com
   */
  public CloudStorageUtil(String endpoint, String accessKeyId, String accessKeySecret) {
    this.endpoint = endpoint;
    this.accessKeyId = accessKeyId;
    ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
  }


  private static void assertParameterNotNull(Object param, String paramName) {
    if (param == null) {
      throw new NullPointerException(paramName + " is null!");
    }
  }

  /**
   * 从云存储中下载单个文件 （一般不需要从后台下载一个文件返回给客户，应该通过前端直接返回给客户）
   * 
   * @param bucketName
   * @param remoteFilePath 文件在云存储中的位置(包括文件夹)
   * @param localFilePath 要下载到本地的文件路径（绝对路径、包括文件名）,路径不存在时自动创建
   * @throws IOException
   */
  public void download(String bucketName, String remoteFilePath, File localFilePath)
      throws IOException {
    assertParameterNotNull(localFilePath, "localFilePath");


    if (!localFilePath.getParentFile().exists()) {
      localFilePath.getParentFile().mkdirs();
    }

    if (localFilePath.exists()) {
      logger.warn(localFilePath.getPath() + " already exists!");
    }
    logger.info("start download file[" + localFilePath.getPath() + "] from " + remoteFilePath + "@"
        + bucketName);
    long startTime = System.currentTimeMillis();
    ObjectMetadata meta =
        ossClient.getObject(new GetObjectRequest(bucketName, remoteFilePath), localFilePath);

    double usedTime = (System.currentTimeMillis() - startTime) / 1000;

    logger.info("[" + localFilePath.getPath() + "](" + FileUtils.getSize(meta.getContentLength())
        + ") download end used " + usedTime + "s");

  }

  /**
   * 从云存储中下载某个目录 例如： downloadFolder("ljt-sit-oss", "doc", new File("/Users/DannyGe/tmp/oss"), map);
   * 
   * @param bucketName
   * @param remoteFolderPath 云存储中文件夹路径
   * @param localDirPath 要下载到本地的路径(为了防止覆盖必须为空白目录，如果目录不存在，则自动创建一个)
   * @param fileNamesMap 文件名映射，key为doc_file.file_name,value为doc_file.UPLOAD_FILE_NAME
   * @throws IOException
   */
  public void downloadFolder(String bucketName, String remoteFolderPath, File localDirPath,
      Map<String, String> fileNamesMap) throws IOException {
    assertParameterNotNull(localDirPath, "localDirPath");

    long startTime = System.currentTimeMillis();
    logger.info("start download folder [" + localDirPath.getPath() + "] from " + remoteFolderPath
        + "@" + bucketName);

    checkLocalDir(localDirPath);
    // 分页获取文件列表
    final int maxKeys = 30;
    String nextMarker = null;
    ObjectListing objectListing;
    do {
      objectListing = ossClient.listObjects(new ListObjectsRequest(bucketName)// .withDelimiter(OSS_FILE_DELLIMITER)
          .withPrefix(remoteFolderPath).withMarker(nextMarker).withMaxKeys(maxKeys));

      List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
      for (OSSObjectSummary s : sums) {
        if (s.getSize() > 0) {
          String fileName = getRealFileName(s.getKey(), fileNamesMap);

          download(s.getBucketName(), s.getKey(),
              new File(localDirPath.getPath() + File.separator + fileName));
        } else {
          logger.info(s.getKey() + " is dir or empty!");
        }
      }

      nextMarker = objectListing.getNextMarker();
    } while (objectListing.isTruncated());


    double usedTime = (System.currentTimeMillis() - startTime) / 1000;
    logger.info("[" + localDirPath.getPath() + "] downloadFolder end used " + usedTime + "s");

  }

  /**
   * 批量下载文件（非整个目录的情况）
   * 
   * @param bucketName
   * @param remoteFilePaths 要下载的云存储中文件夹路径列表
   * @param localDirPath 要下载到本地的路径(为了防止覆盖必须为空白目录，如果目录不存在，则自动创建一个)
   * @param fileNamesMap 文件名映射，key为doc_file.file_name,value为doc_file.UPLOAD_FILE_NAME
   * @throws IOException
   */
  public void downloadBatch(String bucketName, String[] remoteFilePaths, File localDirPath,
      Map<String, String> fileNamesMap) throws IOException {
    assertParameterNotNull(remoteFilePaths, "remoteFilePaths");
    assertParameterNotNull(localDirPath, "localDirPath");
    // 只下载一个文件应该从前端直接下载
    if (remoteFilePaths.length == 1) {
      logger.warn("call downloadBatch with only 1 file[" + remoteFilePaths[0] + "]");
    }
    checkLocalDir(localDirPath);


    long startTime = System.currentTimeMillis();
    logger.info("start downloadBatch[" + localDirPath.getPath() + "(" + remoteFilePaths.length
        + ")] from " + bucketName);
    for (int i = 0; i < remoteFilePaths.length; i++) {
      String realFileName = getRealFileName(remoteFilePaths[i], fileNamesMap);
      File localFilePath = new File(localDirPath.getPath() + File.separator + realFileName);
      download(bucketName, remoteFilePaths[i], localFilePath);
    }
    double usedTime = (System.currentTimeMillis() - startTime) / 1000;
    logger.info("[" + localDirPath.getPath() + "] download end used " + usedTime + "s");

  }

  /*
   * 检查本地文件夹是否符合要求
   */
  private void checkLocalDir(File localDirPath) throws IOException {
    // 不存在，新建目录
    if (!localDirPath.exists()) {
      localDirPath.mkdirs();
    }

    // 检查本地路径是否为文件夹
    if (!localDirPath.isDirectory()) {
      throw new IOException(localDirPath.getPath() + " is not dir!");
    }
    // 检查本地路径是否为空
    File[] childs = localDirPath.listFiles();
    if (childs != null && childs.length > 0) {
      for (int i = 0; i < childs.length; i++) {
        if (!childs[i].isHidden()) {
          throw new IOException(localDirPath.getPath() + " is not empty dir!");
        }
      }
    }
  }

  /**
   * 批量获取云端文件信息（大小、最后修改时间）
   * 
   * @param bucketName
   * @param remoteFilePaths 要获取的文件路径
   * @return key:文件路径；value:文件信息
   */
  public Map<String, FileInfoVO> batchGetFileSize(String bucketName, Set<String> remoteFilePaths) {
    if (remoteFilePaths == null || remoteFilePaths.isEmpty()) {
      return new HashMap<>(0);
    }

    long startTime = System.currentTimeMillis();
    logger.info("start batchGetFileSize from {},keys={}", bucketName,
        Arrays.toString(remoteFilePaths.toArray(new String[0])));
    Map<String, FileInfoVO> result = new HashMap<>(remoteFilePaths.size());
    Iterator<String> it = remoteFilePaths.iterator();
    while (it.hasNext()) {
      String remoteFilePath = it.next();
      SimplifiedObjectMeta meta = ossClient.getSimplifiedObjectMeta(bucketName, remoteFilePath);

      FileInfoVO info = new FileInfoVO(meta.getSize(), meta.getLastModified(), remoteFilePath);
      result.put(remoteFilePath, info);
    }

    double usedTime = (System.currentTimeMillis() - startTime) / 1000;
    logger.info("batchGetFileSize end used " + usedTime + "s");
    return result;
  }


  /*
   * 将文件名从ID中替换为上传时的文件名
   */
  private String getRealFileName(String remoteFilePath, Map<String, String> fileNamesMap) {
    // remoteFileName是包含文件夹路径的，所以需要去掉
    String remoteFileName;
    int index = remoteFilePath.lastIndexOf(OSS_FILE_DELLIMITER);
    if (index > 0) {
      remoteFileName = remoteFilePath.substring(index + 1);
    } else {
      remoteFileName = remoteFilePath;
    }

    String fileName = fileNamesMap.get(remoteFileName);
  /*  if (!StringUtils.hasText(fileName)) {
      logger.warn(remoteFileName + " has no fileNameMaping,use key instead!");
      return remoteFileName;
    }*/
    return fileName;
  }


  /** 关闭链接 */
  @Override
  public void close() {
    if (ossClient != null)
      ossClient.shutdown();
  }

  /**
   * 获取文件上传参数
   * 
   * @param bucketName
   * @param dir 要上传的路径
   * @param timeOut 超时时间（以秒为单位）
   * @return
   * @throws UnsupportedEncodingException
   */
  public Map<String, String> getUploadParas(String bucketName, String dir, int timeOut)
      throws UnsupportedEncodingException {

    PolicyConditions policyConds = new PolicyConditions();
    policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
    policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

    Date expiration = new Date(System.currentTimeMillis() + timeOut * 1000);
    String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
    byte[] binaryData = postPolicy.getBytes("utf-8");
    String encodedPolicy = BinaryUtil.toBase64String(binaryData);
    String postSignature = ossClient.calculatePostSignature(postPolicy);

    String host = "https://" + bucketName + "." + endpoint;

    Map<String, String> respMap = new LinkedHashMap<>();
    respMap.put("accessid", accessKeyId);
    respMap.put("policy", encodedPolicy);
    respMap.put("signature", postSignature);
    respMap.put("dir", dir);
    respMap.put("host", host);
    respMap.put("expire", String.valueOf(expiration.getTime() / 1000));

    URL url =
        ossClient.generatePresignedUrl(bucketName, this.accessKeyId, expiration, HttpMethod.PUT);
    respMap.put("url", url.toString());
    return respMap;
  }



  /**
   * @Description :生成目录
   * @param bucketName
   * @param fileName
   * @return : boolean
   * @Creation Date : 2016年8月29日 下午1:27:41
   * @Author : bingo刑天
   */
  public void mkdir(String bucketName, String fileName) {
    ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(new byte[0]));
  }

  /**
   * 删除目录(同时删除目录下内容)
   * 
   * @param bucketName
   * @param dirName
   */
  public void delDir(String bucketName, String dirName) {

    List<String> files = new ArrayList<>();
    final int maxKeys = 30;
    String nextMarker = null;
    ObjectListing objectListing;
    do {
      objectListing = ossClient.listObjects(new ListObjectsRequest(bucketName)// .withDelimiter(OSS_FILE_DELLIMITER)
          .withPrefix(dirName).withMarker(nextMarker).withMaxKeys(maxKeys));

      List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
      for (OSSObjectSummary s : sums) {
        files.add(s.getKey());
      }
      nextMarker = objectListing.getNextMarker();
    } while (objectListing.isTruncated());

    delFiles(bucketName, files);
  }

  /**
   * 删除多个文件
   * 
   * @param bucketName
   * @param files
   */
  public void delFiles(String bucketName, List<String> files) {
    ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(files));
  }

  /**
   * 删除单个文件
   * 
   * @param bucketName
   * @param file
   */
  public void delFile(String bucketName, String file) {
    ossClient.deleteObject(bucketName, file);
  }


  /**
   * 获取下载路径
   * 
   * @param bucketName
   * @param key
   * @return
   */
  public URL getDownLoadURL(String bucketName, String key) {
    Date expiration = new Date(new Date().getTime() + 3600 * 1000);
    GeneratePresignedUrlRequest request =
        new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
    // 设置过期时间
    request.setExpiration(expiration);
    // 生成URL签名(HTTP GET请求)
    return ossClient.generatePresignedUrl(request);

  }

  /**
   * 获取缩略图下载路径，默认按90%质量，自动适应方向（如果存在exif信息，则先自动进行旋转，再进行缩略），不转换格式 主要按宽度进行缩放，然后按高度进行居中剪裁
   * 注意：gif会转换成非动态图片，只保留第一帧
   * 
   * @param bucketName
   * @param key
   * @param maxWidth
   * @param maxHeight
   * @return
   */
  public URL getImageAutoZoom(String bucketName, String key, int maxWidth, int maxHeight) {
    return getImageAutoZoom(bucketName, key, maxWidth, maxHeight, 90);
  }

  public URL getImageAutoZoom(String bucketName, String key, int maxWidth, int maxHeight,
      int quality) {
    // 按宽度缩放，保持源格式，目标缩略图大于原图不处理
    String newkey = key + "@" + maxWidth + "w_2o_1l.src";
    newkey = newkey + "|0x" + maxHeight + "-5rc_" + quality + "Q";
    return getDownLoadURL(bucketName, newkey);
  }
}
