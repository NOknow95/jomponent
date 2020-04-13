package com.wjw.core.sheet.exception;

import com.wjw.core.exception.IErrorCode;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
public enum ErrorEnums implements IErrorCode {
  NOT_FOUND("COMMON-00001", "The {0} is not found."),
  FILE_NOT_FOUND("COMMON-00002", "The file({0}) is not found."),

  EXPORT_ERROR("EXPORT-00001", "Export error."),
  EXPORT_TO_FILE_ERROR("EXPORT-00002", "Export to file[{0}] error."),
  EXPORT_CSV_ERROR("EXPORT-00003", "Export csv error."),
  ;

  ErrorEnums(String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  private String errorCode;
  private String errorMessage;

  @Override
  public String getErrorCode() {
    return this.errorCode;
  }

  @Override
  public String getErrorMessage() {
    return this.errorMessage;
  }
}
