package com.wjw.core.sheet.component;

import com.wjw.core.sheet.component.FontStyle.Underline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CellData {

  private int rowIndex;
  private int columnIndex;
  private String value;
  private String valueIfNull = "";
  private Boolean bold;
  private Boolean italic;
  private Short fontHeight;
  private IndexedColors fontColor;
  private IndexedColors bgColor;
  private Underline underline;
  private Boolean rowBorder;
  private Boolean columnBorder;
  private CellType dataType = CellType.STRING;

  public CellData(int rowIndex, int columnIndex, String value) {
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
    this.value = value;
  }
}
