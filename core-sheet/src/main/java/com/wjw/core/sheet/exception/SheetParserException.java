package com.wjw.core.sheet.exception;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/03/06
 */
public class SheetParserException extends RuntimeException {

  public SheetParserException() {
    super();
  }

  public SheetParserException(String message) {
    super(message);
  }

  public SheetParserException(String message, Throwable cause) {
    super(message, cause);
  }

  public SheetParserException(Throwable cause) {
    super(cause);
  }
}
