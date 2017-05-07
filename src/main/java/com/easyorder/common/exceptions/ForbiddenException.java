package com.easyorder.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 一般用于访问受权限保护的资源,当访问者无权限时拒绝该请求
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "没有权限") // 403
public class ForbiddenException extends RuntimeException {

  private static final long serialVersionUID = 6525461640799286507L;

  public ForbiddenException(String message) {
    super(message);
  }

  public ForbiddenException(String message, Throwable e) {
    super(message, e);
  }
}
