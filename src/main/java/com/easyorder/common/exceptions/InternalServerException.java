package com.easyorder.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 服务器发生错误，用户将无法判断发出的请求是否成功。
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "服务器错误") // 500
public class InternalServerException extends RuntimeException {

  private static final long serialVersionUID = 6525461640799286507L;

  public static final String MESSAGE = "内部服务异常！";

  public InternalServerException() {
    super(MESSAGE);
  }

  public InternalServerException(String message) {
    super(message);
  }

  public InternalServerException(String message, Throwable e) {
    super(message, e);
  }

  public InternalServerException(Throwable e) {
    super(MESSAGE, e);
  }

}
