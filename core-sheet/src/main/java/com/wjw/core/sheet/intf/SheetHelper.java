package com.wjw.core.sheet.intf;

import com.wjw.core.sheet.component.CellData;
import com.wjw.core.sheet.component.SheetContext;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/03/05
 */
public interface SheetHelper {

  void export(String fileName, Map<String, List<CellData>> cellDataMap,
      OutputStream outputStream);

  void export2File(String filePath, Map<String, List<CellData>> cellDataMap);

  <T> void export(String fileName, List<SheetContext<T>> sheetContexts, OutputStream outputStream);
}
