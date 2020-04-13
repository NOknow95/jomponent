package com.wjw.core.exception;

import java.text.MessageFormat;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
public class BaseException extends RuntimeException {

  private String errorCode;
  private String errorMessage;

  public BaseException(String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public BaseException(IErrorCode errorCode, Object... arguments) {
    this.errorCode = errorCode.getErrorCode();
    this.errorMessage = MessageFormat.format(errorCode.getErrorMessage(), arguments);
  }

  public BaseException(String msg, Throwable e) {
    super(msg, e);
  }

  public BaseException(String message) {
    super(message);
  }

  public String getErrorCode() {
    return this.errorCode;
  }

  public String getErrorMessage() {
    return this.errorMessage;
  }

  public String getMessage() {
    return super.getMessage() + ", BaseException errorCode:" + this.errorCode + ", errorMessage:"
        + this.errorMessage;
  }
}
