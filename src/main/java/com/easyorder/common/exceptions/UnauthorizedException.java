package com.easyorder.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 一般用于验证身份失败、登录时间过期时
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "权限不足") // 401
public class UnauthorizedException extends RuntimeException {

  private static final long serialVersionUID = 6525461640799286507L;

  public UnauthorizedException(String message) {
    super(message);
  }

  public UnauthorizedException(String message, Throwable e) {
    super(message, e);
  }
}
