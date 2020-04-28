package com.wjw.core.sheet.impl;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.wjw.core.exception.BaseException;
import com.wjw.core.sheet.component.CellData;
import com.wjw.core.sheet.component.SheetContext;
import com.wjw.core.sheet.exception.ErrorEnums;
import com.wjw.core.sheet.intf.SheetParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * refer to <a href='https://www.jianshu.com/p/6414185b2f01'>how to use</a>
 *
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
public class CsvSheetParser extends SheetParser {

  private static final Logger logger = LoggerFactory.getLogger(CsvSheetParser.class);

  @Override
  public boolean match(String fileNameOrPath) {
    return fileNameOrPath != null && fileNameOrPath.toLowerCase().endsWith(".csv");
  }

  @Override
  public void export(Map<String, List<CellData>> cellDataMap, OutputStream outputStream) {
    Writer writer = new OutputStreamWriter(outputStream);
    try (CSVWriter csvWriter = (CSVWriter) new CSVWriterBuilder(writer)
        .withSeparator(',').build()) {
      cellDataMap.values().parallelStream().forEach(cellData -> {
        String[] nextLine = cellData.parallelStream()
            .map(data -> Optional.ofNullable(data.getValue()).orElse(data.getValueIfNull()))
            .toArray(String[]::new);
        csvWriter.writeNext(nextLine);
      });
    } catch (IOException e) {
      logger.error("export csv error.", e);
      throw new BaseException(ErrorEnums.EXPORT_CSV_ERROR);
    }
  }

  @Override
  public <T> void export(String fileName, List<SheetContext<T>> sheetContexts,
      OutputStream outputStream) {

  }

  @Override
  public void export2File(String filePath, Map<String, List<CellData>> cellDataMap) {
    try {
      export(cellDataMap, new FileOutputStream(new File(filePath)));
    } catch (FileNotFoundException e) {
      logger.error("file not found", e);
      throw new BaseException(ErrorEnums.FILE_NOT_FOUND, filePath);
    }
  }
}
