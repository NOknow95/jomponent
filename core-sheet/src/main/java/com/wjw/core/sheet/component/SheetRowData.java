package com.wjw.core.sheet.component;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
@Data
@Builder
public class SheetRowData {

  private int rowIndex;
  private List<SheetColumnCell> sheetColumnCells;
}
