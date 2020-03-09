package com.wjw.core.sheet;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.wjw.core.sheet.constans.GlobalConts;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

class TestSheetApi {

  private static final Logger logger = LoggerFactory.getLogger(TestSheetApi.class);

  @BeforeEach
  void setUp() {
    File rootDir = new File(GlobalConts.ROOT_OUTPUT_DIR);
    if (!rootDir.exists()) {
      boolean mkdirs = rootDir.mkdirs();
      logger.info(() -> "mkdirs = " + mkdirs);
    }
  }

  @Test
  void writeArrays() throws IOException {
    CSVWriter writer = (CSVWriter) new CSVWriterBuilder(
        new FileWriter(GlobalConts.ROOT_OUTPUT_DIR + "writeArrays.csv"))
        .withSeparator(',').build();
    String[] entries = "111#2#3".split("#");
    writer.writeNext(entries, false);
    writer.writeNext(entries, false);
    writer.close();
  }

  @Test
  void writeListArrays() throws IOException {
    CSVWriter writer = (CSVWriter) new CSVWriterBuilder(
        new FileWriter(GlobalConts.ROOT_OUTPUT_DIR + "writeListArrays.csv"))
        .withSeparator(',').build();
    List<String[]> list = new ArrayList<String[]>() {{
      add(new String[]{"no", "name", "age"});
      add(new String[]{"1", "xiaoming", "20"});
      add(new String[]{"2", "lihua ", "19"});
    }};
    writer.writeAll(list);
    writer.close();
  }

  @Test
  void writeToBytes() throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    CSVWriter writer = (CSVWriter) new CSVWriterBuilder(
        new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8))
        .withSeparator(',').build();
    List<String[]> list = new ArrayList<String[]>() {{
      add(new String[]{"no", "name", "age"});
      add(new String[]{"1", "xiaoming", "20"});
      add(new String[]{"2", "lihua ", "19"});
    }};
    writer.writeAll(list);
    writer.close();
    byte[] bytes = byteArrayOutputStream.toByteArray();
    Files.write(Paths.get(GlobalConts.ROOT_OUTPUT_DIR, "writeToBytes.csv"), bytes);
  }
}