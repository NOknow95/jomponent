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
import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.xml.ws.Holder;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NOknow
 * @version 1.0
 * @date 2020/03/05
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
  public <T> void export(String fileName, List<SheetContext<T>> sheetContexts,
      OutputStream outputStream) {
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
      Holder<Integer> currentRowIndexHolder = new Holder<>(
          currentRowIndex + sheetContext.getFormDataStartRowIndexPaddingTop() + 1);
      parseFormData2CellData(sheetName, currentRowIndexHolder,
          sheetContext.getFormDataStartColumnIndexPaddingLeft(),
          sheetContext.getFormData(),
          resultCellDataMap);
      parseSheetFooter2CellData(sheetName, currentRowIndexHolder, sheetContext.getSheetFooter(),
          resultCellDataMap);
    }
    export(resultCellDataMap, outputStream);
  }

  private <T> void parseFormData2CellData(String sheetName,
      final Holder<Integer> currentRowIndex,
      final int formDataStartColumnIndexPaddingLeft,
      List<T> formData,
      Map<String, List<CellData>> resultCellDataMap) {
    if (CollectionUtils.isEmpty(formData) || formData.get(0) == null) {
      return;
    }
    T firstRowData = formData.get(0);
    List<CellData> cellDataList = new LinkedList<>();
    FormStyle formStyle = Optional
        .ofNullable(firstRowData.getClass().getAnnotation(FormStyle.class))
        .orElseThrow(() -> new BaseException(ErrorEnums.REQUIRED,
            "@" + FormStyle.class.getName() + " in class:" + firstRowData.getClass().getName()));
    CellStyle headerStyle = formStyle.headerStyle();
    addFormData(currentRowIndex, formDataStartColumnIndexPaddingLeft,
        firstRowData, cellDataList, headerStyle, false);

    CellStyle contentStyle = formStyle.contentStyle();
    for (T dataItem : formData) {
      addFormData(currentRowIndex, formDataStartColumnIndexPaddingLeft,
          dataItem, cellDataList, contentStyle, true);
    }
    appentListInMap(sheetName, resultCellDataMap, cellDataList);
  }

  private <T> void addFormData(Holder<Integer> currentRowIndex,
      int formDataStartColumnIndexPaddingLeft, T rowData, List<CellData> cellDataList,
      CellStyle formStyle, boolean isFormData) {
    List<CellData> cellDataListTmp = new ArrayList<>(cellDataList.size() + 10);
    Holder<String> cellVal = new Holder<>("");
    Optional.ofNullable(rowData.getClass().getDeclaredFields()).ifPresent(fields -> {
      try {
        for (Field field : fields) {
          if (field == null) {
            continue;
          }
          if (isFormData) {
            field.setAccessible(true);
            Object val = field.get(rowData);
            cellVal.value = val == null ? "" : String.valueOf(val);
            field.setAccessible(false);
          }
          Optional.ofNullable(field.getAnnotation(SheetField.class)).ifPresent(sheetField -> {
            if (sheetField.displayable()) {
              CellStyle cellStyle =
                  sheetField.overrideFormStyle() ? sheetField.cellStyle() : formStyle;
              String val = isFormData ? cellVal.value : sheetField.name();
              CellData cellData = new CellData(
                  currentRowIndex.value,
                  sheetField.ordinal(),
                  val, val, cellStyle.bold(), cellStyle.italic(),
                  cellStyle.fontHeight(), cellStyle.fontColor(),
                  cellStyle.bgColor(), cellStyle.underline(),
                  cellStyle.rowBorder(), cellStyle.columnBorder(),
                  cellStyle.dataType());
              cellDataListTmp.add(cellData);
            }
          });
        }
      } catch (IllegalAccessException e) {
        throw new BaseException(ErrorEnums.REFLECT_ERROR);
      }
    });
    currentRowIndex.value++;
    Holder<Integer> columnIndex = new Holder<>(0);
    cellDataListTmp.stream().sorted(Comparator.comparingInt(CellData::getColumnIndex))
        .forEach(data -> data
            .setColumnIndex(formDataStartColumnIndexPaddingLeft + (columnIndex.value++)));
    cellDataList.addAll(cellDataListTmp);
  }

  private void parseSheetFooter2CellData(String sheetName, Holder<Integer> currentRowIndexHolder,
      SheetFooter sheetFooter, Map<String, List<CellData>> resultCellDataMap) {
    if (sheetFooter == null || CollectionUtils.isEmpty(sheetFooter.getFooterRowData())) {
      return;
    }
    List<CellData> cellDataList = new LinkedList<>();
    sheetFooter.getFooterRowData().forEach((rowIndex, columnCells) -> {
      for (SheetColumnCell columnCell : Optional.ofNullable(columnCells)
          .orElse(Collections.emptyList())) {
        CellData cellData = new CellData(rowIndex - 1,
            columnCell.getColumnIndex(), columnCell.getValue(),
            columnCell.getValue(), columnCell.getBold(), columnCell.getItalic(),
            columnCell.getFontHeight(), columnCell.getFontColor(),
            columnCell.getBgColor(), columnCell.getUnderline(),
            columnCell.getRowBorder(), columnCell.getColumnBorder(),
            columnCell.getDataType());
        cellDataList.add(cellData);
      }
    });
    int paddingTop = Optional.ofNullable(sheetFooter.getStartRowIndexPaddingTop()).orElse(0);
    cellDataList.parallelStream().forEach(item -> item.setRowIndex(
        item.getRowIndex() + currentRowIndexHolder.value + paddingTop));

    IntSummaryStatistics statistics = new ArrayList<>(sheetFooter.getFooterRowData().keySet())
        .parallelStream()
        .collect(Collectors.summarizingInt(i -> i));
    int max = statistics.getMax();
    currentRowIndexHolder.value += max;

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
