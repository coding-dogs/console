package com.easyorder.common.exceptions;

import com.easyorder.common.utils.StringUtils;

/**
 * @Project : framework-core
 * @Program Name : com.ljt.framework.core.exception.Exceptions.java
 * @Description : 异常工具类
 * @Author : wangchao
 * @Creation Date : 2016年9月23日 下午12:44:19
 * @ModificationHistory Who When What ---------- ------------- -----------------------------------
 *                      wangchao 2016年9月23日 create
 */
public abstract class Exceptions {

  protected final static InternalServerException INTERNAL_SERVER_EXCEPTION =
      new InternalServerException();

  // /**
  // * @Description : 创建内部服务异常
  // * @return : InternalServerException
  // * @Creation Date : 2016年9月23日 下午12:52:57
  // * @Author : wangchao
  // */
  // public static InternalServerException internalServerException(){
  // return INTERNAL_SERVER_EXCEPTION;
  // }

  /**
   * @Description : 创建内部服务异常
   * @return : InternalServerException
   * @Creation Date : 2016年9月23日 下午12:52:57
   * @Author : wangchao
   */
  public static InternalServerException internalServerException(String message) {
    return new InternalServerException(message);
  }

  /**
   * @Description : 创建内部服务异常
   * @return : InternalServerException
   * @Creation Date : 2016年9月23日 下午12:52:57
   * @Author : wangchao
   */
  public static InternalServerException internalServerException(String... message) {
    return new InternalServerException(StringUtils.append(message));
  }

  /**
   * @Description : 创建内部服务异常
   * @return : InternalServerException
   * @Creation Date : 2016年9月23日 下午12:52:57
   * @Author : wangchao
   */
  public static InternalServerException internalServerException(Throwable e) {
    return new InternalServerException(e);
  }

  /**
   * @Description : 创建内部服务异常
   * @return : InternalServerException
   * @Creation Date : 2016年9月23日 下午12:52:57
   * @Author : wangchao
   */
  public static InternalServerException internalServerException(Throwable e, String message) {
    return new InternalServerException(message, e);
  }

  public static InternalServerException internalServerException(Throwable e, String... messages) {
    return new InternalServerException(StringUtils.append(messages), e);
  }

  /**
   * @Description : 构建BadRequestException异常
   * @return : BadRequestException
   * @Creation Date : 2016年9月23日 下午12:55:36
   * @Author : wangchao
   */
  public static BadRequestException badRequestException(String message) {
    return new BadRequestException(message);
  }

  /**
   * @Description : 构建BadRequestException异常
   * @return : BadRequestException
   * @Creation Date : 2016年9月23日 下午12:55:36
   * @Author : wangchao
   */
  public static BadRequestException badRequestException(String... message) {
    return new BadRequestException(StringUtils.append(message));
  }

}
