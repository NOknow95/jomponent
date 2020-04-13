package com.wjw.core.sheet.component;

import org.apache.poi.ss.usermodel.CellType;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
public enum DataType {
  STRING(CellType.STRING),
  FORMULA(CellType.FORMULA),
  ;
  private CellType cellType;

  DataType(CellType cellType) {
    this.cellType = cellType;
  }

  public CellType getCellType() {
    return cellType;
  }
}
