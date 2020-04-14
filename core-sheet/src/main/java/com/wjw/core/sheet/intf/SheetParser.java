package com.wjw.core.sheet.intf;

import com.wjw.core.exception.BaseException;
import com.wjw.core.sheet.component.CellData;
import com.wjw.core.sheet.component.SheetContext;
import com.wjw.core.sheet.exception.ErrorEnums;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NOknow
 * @version 1.0
 * @reateDate 2020/03/05
 * @description
 */
public abstract class SheetParser {

  private static final Logger logger = LoggerFactory.getLogger(SheetParser.class);

  public abstract boolean match(String fileNameOrPath);

  protected void export(Workbook workbook, Map<String, List<CellData>> cellDataMap,
      OutputStream outputStream) {
    cellDataMap.forEach((sheetName, cellData) -> {
      Sheet sheet = workbook.createSheet(sheetName);
      cellData.stream()
          .collect(Collectors.groupingBy(CellData::getRowIndex))
          .forEach((rowIndex, cellItems) -> {
            Row row = sheet.createRow(rowIndex);
            for (CellData item : cellItems) {
              Cell cell = row.createCell(item.getColumnIndex());
              Optional.ofNullable(item.getDataType()).ifPresent(cell::setCellType);
              cell.setCellValue(
                  Optional.ofNullable(item.getValue()).orElse(item.getValueIfNull()));
              CellStyle cellStyle = workbook.createCellStyle();
              Font font = workbook.createFont();
              Optional.ofNullable(item.getBold()).ifPresent(font::setBold);
              Optional.ofNullable(item.getItalic()).ifPresent(font::setItalic);
              Optional.ofNullable(item.getFontColor())
                  .ifPresent(color -> font.setColor(color.index));
              Optional.ofNullable(item.getUnderline())
                  .ifPresent(underline -> font.setUnderline(underline.getCode()));
              cellStyle.setFont(font);
              Optional.ofNullable(item.getBgColor())
                  .ifPresent(color -> cellStyle.setFillBackgroundColor(color.index));
              cell.setCellStyle(cellStyle);
            }
          });
    });
    try {
      workbook.write(outputStream);
      workbook.close();
    } catch (IOException e) {
      logger.error("export error.", e);
      throw new BaseException(ErrorEnums.EXPORT_ERROR);
    }
  }

  public abstract void export(Map<String, List<CellData>> cellDataMap, OutputStream outputStream);

  public abstract <T> void export(List<SheetContext<T>> sheetContexts, OutputStream outputStream);

  public abstract void export2File(String filePath, Map<String, List<CellData>> cellDataMap);

}
