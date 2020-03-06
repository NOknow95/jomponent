package com.wjw.core.sheet;

import java.io.OutputStream;
import java.util.regex.Pattern;

/**
 * @author NOknow
 * @version 1.0
 * @reateDate 2020/03/05
 * @description
 */
public class CsvSheetParser extends AbstractSheetParser implements ISheetParser {

  private static final Pattern PATTERN = Pattern.compile("^[0-9_A-Za-z\\-]+(.csv)$");

  @Override
  public boolean match(String fileName) {
    return PATTERN.matcher(fileName).find();
  }

  @Override
  public void export(OutputStream outputStream, String fileName) {

  }
}
