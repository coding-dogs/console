package com.jeeplus.modules.oss.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.io.FileUtils;

/**
 * 云存储文件信息VO
 * 
 * @author DannyGe
 */
public class FileInfoVO implements Serializable {

  private static final long serialVersionUID = 4162828094316229062L;

  public FileInfoVO() {
    super();
  }

  public FileInfoVO(long size, Date lastModified, String remoteFileName) {
    super();
    this.size = size;
    this.lastModified = lastModified;
    this.remoteFileName = remoteFileName;
    this.dispSize = FileUtils.byteCountToDisplaySize(size);
  }

  private long size;// 文件大小
  private String dispSize;// 显示大小(自动计算单位)
  private Date lastModified;// 最后更新时间
  private String realFileName;// 上传时文件名（带路径）
  private String remoteFileName;// 远端文件名（带路径）

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
    this.dispSize = FileUtils.byteCountToDisplaySize(size);
  }



  public String getDispSize() {
    return dispSize;
  }



  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public String getRealFileName() {
    return realFileName;
  }

  public void setRealFileName(String realFileName) {
    this.realFileName = realFileName;
  }

  public String getRemoteFileName() {
    return remoteFileName;
  }

  public void setRemoteFileName(String remoteFileName) {
    this.remoteFileName = remoteFileName;
  }



}
