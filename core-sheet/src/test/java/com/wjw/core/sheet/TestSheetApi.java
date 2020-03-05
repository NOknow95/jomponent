package com.wjw.core.sheet;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestSheetApi {

  private static final String ROOT_DIR = "output/sheet/";

  @Test
  void testWriteArrays() throws IOException {
    CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(ROOT_DIR + "test.csv"))
        .withSeparator(',').build();
    String[] entries = "111#2#3".split("#");
    writer.writeNext(entries, false);
    writer.writeNext(entries, false);
    writer.close();
    Assertions.assertTrue(true);
  }
}