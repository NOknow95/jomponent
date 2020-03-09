package com.wjw.core.sheet.impl;

import com.wjw.core.sheet.constans.GlobalConts;
import com.wjw.core.sheet.intf.ISheetParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DefaultSheetParserImplTest {

  @Test
  void test() throws FileNotFoundException {
    Object[] objects = {"name", 15, "fj"};
    List<Object[]> lines = new ArrayList<>();
    lines.add(objects);
    ISheetParser sheetParser = new DefaultSheetParserImpl(lines);
    String fileName = "test.xlsx";
//    String fileName = "test.csv";
    sheetParser.export(
        new FileOutputStream(new File(GlobalConts.ROOT_OUTPUT_DIR + fileName)), fileName);
  }
}