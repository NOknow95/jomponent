package com.wjw.core.sheet.component;

import com.wjw.core.sheet.component.FontStyle.Underline;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;

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
  private String valueIfNull;
  private Boolean bold;
  private Boolean italic;
  private Short fontHeight;
  private IndexedColors fontColor;
  private IndexedColors bgColor;
  private Underline underline;
  private Boolean rowBorder;
  private Boolean columnBorder;
  private CellType dataType;

  public SheetColumnCell() {
    this.valueIfNull = "";
    this.dataType = CellType.STRING;
  }
}
