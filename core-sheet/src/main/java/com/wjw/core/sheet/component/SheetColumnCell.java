package com.wjw.core.sheet.component;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
@Data
@AllArgsConstructor
public class SheetColumnCell {

  private Integer columnIndex;
  private String value;
  private Boolean bold;
  private Short color;
  private DataType dataType;
}
