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

  public SheetColumnCell(Integer columnIndex, String value) {
    this.columnIndex = columnIndex;
    this.value = value;
  }

  public SheetColumnCell(Integer columnIndex, String value, String valueIfNull) {
    this.columnIndex = columnIndex;
    this.value = value;
    this.valueIfNull = valueIfNull;
  }

  public SheetColumnCell(Integer columnIndex, String value, Boolean bold) {
    this.columnIndex = columnIndex;
    this.value = value;
    this.bold = bold;
  }

  public SheetColumnCell(Integer columnIndex, String value, String valueIfNull,
      Boolean bold, Boolean rowBorder, Boolean columnBorder,
      CellType dataType) {
    this.columnIndex = columnIndex;
    this.value = value;
    this.valueIfNull = valueIfNull;
    this.bold = bold;
    this.rowBorder = rowBorder;
    this.columnBorder = columnBorder;
    this.dataType = dataType;
  }

  public SheetColumnCell(Integer columnIndex, String value, Boolean bold, Boolean italic,
      Short fontHeight, IndexedColors fontColor, IndexedColors bgColor,
      Underline underline, Boolean rowBorder, Boolean columnBorder,
      CellType dataType) {
    this.columnIndex = columnIndex;
    this.value = value;
    this.bold = bold;
    this.italic = italic;
    this.fontHeight = fontHeight;
    this.fontColor = fontColor;
    this.bgColor = bgColor;
    this.underline = underline;
    this.rowBorder = rowBorder;
    this.columnBorder = columnBorder;
    this.dataType = dataType;
  }
}
