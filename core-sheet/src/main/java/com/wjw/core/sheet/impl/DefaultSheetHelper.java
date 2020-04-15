package com.wjw.core.sheet.impl;

import com.wjw.core.sheet.component.CellData;
import com.wjw.core.sheet.component.SheetContext;
import com.wjw.core.sheet.intf.SheetHelper;
import com.wjw.core.sheet.intf.SheetParser;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
public class DefaultSheetHelper implements SheetHelper {

  private static final List<SheetParser> sheetParsers = Arrays
      .asList(new ExcelXlsxSheetParser(), new CsvSheetParser());

  @Override
  public void export(String fileName, Map<String, List<CellData>> cellDataMap,
      OutputStream outputStream) {
    sheetParsers.parallelStream().filter(parser -> parser.match(fileName))
        .forEach(parser -> parser.export(cellDataMap, outputStream));
  }

  @Override
  public void export2File(String filePath, Map<String, List<CellData>> cellDataMap) {
    sheetParsers.parallelStream().filter(parser -> parser.match(filePath))
        .forEach(parser -> parser.export2File(filePath, cellDataMap));
  }

  @Override
  public <T> void export(String fileName, List<SheetContext<T>> sheetContexts,
      OutputStream outputStream) {
    sheetParsers.parallelStream().filter(parser -> parser.match(fileName))
        .forEach(parser -> parser.export(fileName, sheetContexts, outputStream));
  }

}
