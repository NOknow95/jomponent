package com.wjw.core.sheet.intf;

import com.wjw.core.exception.BaseException;
import com.wjw.core.sheet.component.CellData;
import com.wjw.core.sheet.component.SheetContext;
import com.wjw.core.sheet.constant.FontConst;
import com.wjw.core.sheet.exception.ErrorEnums;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.BorderStyle;
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
              Optional.ofNullable(item.getFontHeight()).ifPresent(fontHeight -> {
                if (!Objects.equals(fontHeight, FontConst.INVALID_FONT_HEIGHT)) {
                  font.setFontHeight(fontHeight);
                }
              });
              Optional.ofNullable(item.getFontColor())
                  .ifPresent(color -> font.setColor(color.index));
              Optional.ofNullable(item.getUnderline())
                  .ifPresent(underline -> font.setUnderline(underline.getCode()));
              cellStyle.setFont(font);
              Optional.ofNullable(item.getBgColor())
                  .ifPresent(color -> cellStyle.setFillBackgroundColor(color.index));
              Optional.ofNullable(item.getRowBorder()).ifPresent(rowBorder -> {
                if (rowBorder) {
                  cellStyle.setBorderBottom(BorderStyle.THIN);
                  cellStyle.setBorderTop(BorderStyle.THIN);
                }
              });
              Optional.ofNullable(item.getColumnBorder()).ifPresent(cBorder -> {
                if (cBorder) {
                  cellStyle.setBorderLeft(BorderStyle.THIN);
                  cellStyle.setBorderRight(BorderStyle.THIN);
                }
              });
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

  public abstract <T> void export(String fileName, List<SheetContext<T>> sheetContexts,
      OutputStream outputStream);

  public abstract void export2File(String filePath, Map<String, List<CellData>> cellDataMap);

}
