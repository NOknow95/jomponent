package com.wjw.core.sheet.component;

import org.apache.poi.ss.usermodel.Font;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
public class FontStyle {


  public enum Underline {
    UNDERLINE_NONE(Font.U_NONE),
    UNDERLINE_SINGLE(Font.U_SINGLE),
    UNDERLINE_DOUBLE(Font.U_DOUBLE),
    UNDERLINE_SINGLE_ACCOUNTING(Font.U_SINGLE_ACCOUNTING),
    UNDERLINE_DOUBLE_ACCOUNTING(Font.U_DOUBLE_ACCOUNTING);

    private byte code;

    Underline(byte code) {
      this.code = code;
    }

    public byte getCode() {
      return code;
    }
  }
}
