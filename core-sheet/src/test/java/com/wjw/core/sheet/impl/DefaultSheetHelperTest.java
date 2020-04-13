package com.wjw.core.sheet.impl;

import com.wjw.core.sheet.component.CellData;
import com.wjw.core.sheet.constans.TestGlobalConstans;
import com.wjw.core.sheet.intf.SheetHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
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
}