package com.wjw.core.sheet.component;

import java.util.List;
import lombok.Data;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
@Data
public class SheetContext<T> {

  private String sheetName;
  private List<SheetRowData> rowData;
  private List<T> formData;
  private Integer formDataStartRowIndex;
  private Integer formDataStartColumnIndex;
}
