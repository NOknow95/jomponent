package com.wjw.core.sheet.impl;

import com.wjw.core.exception.BaseException;
import com.wjw.core.sheet.component.CellData;
import com.wjw.core.sheet.exception.ErrorEnums;
import com.wjw.core.sheet.intf.SheetParser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NOknow
 * @version 1.0
 * @reateDate 2020/03/05
 * @description
 */
public class ExcelXlsxSheetParser extends SheetParser {

  private static final Logger logger = LoggerFactory.getLogger(ExcelXlsxSheetParser.class);

  @Override
  public boolean match(String fileNameOrPath) {
    return fileNameOrPath != null && fileNameOrPath.toLowerCase().endsWith(".xlsx");
  }

  @Override
  public void export(Map<String, List<CellData>> cellDataMap,
      OutputStream outputStream) {
    Workbook workbook = new XSSFWorkbook();
    super.export(workbook, cellDataMap, outputStream);
  }

  @Override
  public void export2File(String filePath, Map<String, List<CellData>> cellDataMap) {
    File file = new File(filePath);
    if (!file.getParentFile().exists()) {
      boolean mkdirs = file.getParentFile().mkdirs();
      logger.info("mkdirs = {}", mkdirs);
    }
    try (OutputStream fos = new FileOutputStream(file)) {
      export(cellDataMap, fos);
    } catch (IOException e) {
      logger.error("export_to_file_error", e);
      throw new BaseException(ErrorEnums.EXPORT_TO_FILE_ERROR, filePath);
    }
  }
}
