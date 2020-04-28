package com.wjw.core.sheet.impl;

import com.wjw.core.sheet.bean.StudentInfo;
import com.wjw.core.sheet.component.CellData;
import com.wjw.core.sheet.component.SheetColumnCell;
import com.wjw.core.sheet.component.SheetContext;
import com.wjw.core.sheet.component.SheetContext.SheetFooter;
import com.wjw.core.sheet.component.SheetContext.SheetHeader;
import com.wjw.core.sheet.component.SheetRowData;
import com.wjw.core.sheet.constans.TestGlobalConstans;
import com.wjw.core.sheet.intf.SheetHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DefaultSheetHelperTest {

  private SheetHelper sheetHelper;

  @BeforeEach
  void setUp() {
    sheetHelper = new DefaultSheetHelper();
  }

  @ParameterizedTest
  @ValueSource(strings = {"export2File.xlsx", "export2File.csv"})
  void export(String fileName) throws FileNotFoundException {
    Map<String, List<CellData>> cellMap = new TreeMap<>();
    cellMap.put("sheet 1",
        Arrays.asList(CellData.builder().rowIndex(0).columnIndex(0).value("aaa")
                .fontColor(IndexedColors.BLUE).build(),
            new CellData(0, 1, "bbb")));
    sheetHelper.export(fileName, cellMap,
        new FileOutputStream(new File(TestGlobalConstans.ROOT_OUTPUT_DIR + fileName)));
  }

  @ParameterizedTest
  @ValueSource(strings = {"export2File.xlsx", "export2File.csv"})
  void export2File(String fileName) {
    Map<String, List<CellData>> cellMap = new TreeMap<>();
    cellMap.put("sheet 1",
        Arrays.asList(CellData.builder().rowIndex(0).columnIndex(0).value("aaa")
                .fontColor(IndexedColors.BLUE).build(),
            new CellData(0, 1, "bbb")));
    sheetHelper.export2File(TestGlobalConstans.ROOT_OUTPUT_DIR + fileName, cellMap);
  }

  @ParameterizedTest
  @ValueSource(strings = {"export1.xlsx"})
  void export1(String fileName) throws FileNotFoundException {
    List<SheetContext<StudentInfo>> sheetContexts = new LinkedList<>();
    SheetContext<StudentInfo> sheetContext = new SheetContext<>("sheet1");
    SheetHeader sheetHeader = new SheetHeader();
    sheetHeader.setHeaderRowData(Arrays.asList(
        SheetRowData.builder().rowIndex(0).sheetColumnCells(Arrays.asList(
            new SheetColumnCell(0, "time", true),
            new SheetColumnCell(1, "2020-04-15")
        )).build(),
        SheetRowData.builder().rowIndex(1).sheetColumnCells(Arrays.asList(
            new SheetColumnCell(0, "address", true),
            new SheetColumnCell(1, "Fu Zhou")
        )).build()
    ));
    sheetContext.setSheetHeader(sheetHeader);
    sheetContext.setFormData(Arrays.asList(
        new StudentInfo("xiaoming", 15, "Fuzhou", "1@qq.com", "1"),
        new StudentInfo("xiaowen", 16, "Xiamen", "2@qq.com", "1"),
        new StudentInfo("xiaohua", 17, "Guangzhou", "3@qq.com", "1"),
        new StudentInfo("xiaolei", 18, "Shenzhen", "4@qq.com", "1")
    ));
    sheetContext.setFormDataStartColumnIndexPaddingLeft(1);
    sheetContext.setFormDataStartRowIndexPaddingTop(3);
    Map<Integer, List<SheetColumnCell>> footerMap = new TreeMap<>();
    footerMap.put(1, Arrays.asList(
        new SheetColumnCell(0, "11111"),
        new SheetColumnCell(1, "22222")
    ));
    footerMap.put(2, Arrays.asList(
        new SheetColumnCell(0, "3333"),
        new SheetColumnCell(1, "4444")
    ));
    SheetFooter footer = new SheetFooter(1, footerMap);
    sheetContext.setSheetFooter(footer);
    sheetContexts.add(sheetContext);
    sheetHelper.export(fileName, sheetContexts,
        new FileOutputStream(new File(TestGlobalConstans.ROOT_OUTPUT_DIR + fileName)));
  }
}