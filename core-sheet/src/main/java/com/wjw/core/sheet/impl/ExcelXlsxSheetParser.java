package com.wjw.core.sheet.impl;

import com.wjw.core.exception.BaseException;
import com.wjw.core.sheet.anno.CellStyle;
import com.wjw.core.sheet.anno.FormStyle;
import com.wjw.core.sheet.anno.SheetField;
import com.wjw.core.sheet.component.CellData;
import com.wjw.core.sheet.component.SheetColumnCell;
import com.wjw.core.sheet.component.SheetContext;
import com.wjw.core.sheet.component.SheetContext.SheetFooter;
import com.wjw.core.sheet.component.SheetContext.SheetHeader;
import com.wjw.core.sheet.component.SheetRowData;
import com.wjw.core.sheet.exception.ErrorEnums;
import com.wjw.core.sheet.intf.SheetParser;
import com.wjw.core.utils.other.collection.CollectionUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
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
  public <T> void export(List<SheetContext<T>> sheetContexts, OutputStream outputStream) {
    Map<String, List<CellData>> resultCellDataMap = new TreeMap<>();
    for (SheetContext<T> sheetContext : sheetContexts) {
      if (isInvalid(sheetContext)) {
        continue;
      }
      String sheetName = sheetContext.getSheetName();
      parseSheetHeader2CellData(sheetName, sheetContext.getSheetHeader(), resultCellDataMap);
      int currentRowIndex = Optional.ofNullable(sheetContext.getSheetHeader().getHeaderRowData())
          .orElse(Collections.emptyList()).parallelStream()
          .map(SheetRowData::getRowIndex)
          .max(Integer::compareTo).orElse(0);
      parseFormData2CellData(sheetName, currentRowIndex,
          sheetContext.getFormDataStartColumnIndexPaddingLeft(),
          sheetContext.getFormDataStartRowIndexPaddingTop(),
          sheetContext.getFormData(),
          resultCellDataMap);
      parseSheetFooter2CellData(sheetName, currentRowIndex, sheetContext.getSheetFooter(),
          resultCellDataMap);
    }
  }

  private <T> void parseFormData2CellData(String sheetName,
      final int currentRowIndex,
      final int formDataStartRowIndexPaddingTop,
      final int formDataStartColumnIndexPaddingLeft,
      List<T> formData,
      Map<String, List<CellData>> resultCellDataMap) {
    T firstRowData = formData.get(0);
    if (CollectionUtils.isEmpty(formData) || firstRowData == null) {
      return;
    }
    List<CellData> cellDataList = new LinkedList<>();
    try {
      FormStyle formStyle = Optional
          .ofNullable(firstRowData.getClass().getAnnotation(FormStyle.class))
          .orElse(FormStyle.class.newInstance());
      CellStyle headerStyle = formStyle.headerStyle();
      Optional.ofNullable(firstRowData.getClass().getDeclaredFields()).ifPresent(fields -> {
        Arrays.stream(fields).filter(Objects::nonNull)
            .map(field -> field.getAnnotation(SheetField.class))
            .filter(Objects::nonNull)
            .sorted(Comparator.comparingInt(SheetField::ordinal))
            .forEach(sheetField -> {
              CellStyle cellStyle =
                  sheetField.overrideFormStyle() ? sheetField.cellStyle() : headerStyle;
              CellData cellData = new CellData(currentRowIndex + formDataStartRowIndexPaddingTop,
                  formDataStartColumnIndexPaddingLeft + sheetField.ordinal(), sheetField.name(),
                  sheetField.name(), cellStyle.bold(), cellStyle.italic(),
                  cellStyle.fontHeight(), cellStyle.fontColor(),
                  cellStyle.bgColor(), cellStyle.underline(),
                  cellStyle.rowBorder(), cellStyle.columnBorder(),
                  cellStyle.dataType());
              cellDataList.add(cellData);
            });
      });

      for (T dataItem : formData) {
        Field[] fields = dataItem.getClass().getDeclaredFields();
        if (fields == null || fields.length <= 0) {
          continue;
        }
        System.out.println("");
      }
    } catch (InstantiationException | IllegalAccessException e) {
      logger.error("Reflect error.", e);
      throw new BaseException(ErrorEnums.REFLECT_ERROR);
    }
    appentListInMap(sheetName, resultCellDataMap, cellDataList);
  }

  private void parseSheetFooter2CellData(String sheetName, int currentRowIndex,
      SheetFooter sheetFooter, Map<String, List<CellData>> resultCellDataMap) {
    if (sheetFooter == null || CollectionUtils.isEmpty(sheetFooter.getFooterRowData())) {
      return;
    }
    List<CellData> cellDataList = new LinkedList<>();
    currentRowIndex += Optional.ofNullable(sheetFooter.getStartRowIndexPaddingTop()).orElse(0);
    int finalCurrentRowIndex = currentRowIndex;
    sheetFooter.getFooterRowData().forEach((rowIndex, columnCells) -> {
      for (SheetColumnCell columnCell : Optional.ofNullable(columnCells)
          .orElse(Collections.emptyList())) {
        CellData cellData = new CellData(rowIndex + finalCurrentRowIndex,
            columnCell.getColumnIndex(), columnCell.getValue(),
            columnCell.getValue(), columnCell.getBold(), columnCell.getItalic(),
            columnCell.getFontHeight(), columnCell.getFontColor(),
            columnCell.getBgColor(), columnCell.getUnderline(),
            columnCell.getRowBorder(), columnCell.getColumnBorder(),
            columnCell.getDataType());
        cellDataList.add(cellData);
      }
    });
    appentListInMap(sheetName, resultCellDataMap, cellDataList);
  }

  private void parseSheetHeader2CellData(String sheetName, SheetHeader sheetHeader,
      Map<String, List<CellData>> resultCellDataMap) {
    List<SheetRowData> headerRowData = sheetHeader.getHeaderRowData();
    parseRowData2CellData(sheetName, headerRowData, resultCellDataMap);
  }

  private void parseRowData2CellData(String sheetName, List<SheetRowData> rowDataList,
      Map<String, List<CellData>> cellDataMap) {
    List<CellData> cellDataList = new LinkedList<>();
    for (SheetRowData rowData : rowDataList) {
      for (SheetColumnCell columnCell : rowData.getSheetColumnCells()) {
        CellData cellData = new CellData(rowData.getRowIndex(),
            columnCell.getColumnIndex(), columnCell.getValue(),
            columnCell.getValue(), columnCell.getBold(), columnCell.getItalic(),
            columnCell.getFontHeight(), columnCell.getFontColor(),
            columnCell.getBgColor(), columnCell.getUnderline(),
            columnCell.getRowBorder(), columnCell.getColumnBorder(),
            columnCell.getDataType());
        cellDataList.add(cellData);
      }
    }
    appentListInMap(sheetName, cellDataMap, cellDataList);
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

  private <T> boolean isInvalid(SheetContext<T> sheetContext) {
    return sheetContext == null || ((sheetContext.getSheetHeader() == null || CollectionUtils
        .isEmpty(sheetContext.getSheetHeader().getHeaderRowData()))
        && CollectionUtils.isEmpty(sheetContext.getFormData())
        && (sheetContext.getSheetFooter() == null || CollectionUtils
        .isEmpty(sheetContext.getSheetFooter().getFooterRowData())));
  }

  private void appentListInMap(String sheetName, Map<String, List<CellData>> resultCellDataMap,
      List<CellData> cellDataList) {
    resultCellDataMap.compute(sheetName, (key, list) -> {
      if (list == null) {
        list = new ArrayList<>(cellDataList);
      } else {
        list.addAll(cellDataList);
      }
      return list;
    });
  }
}
