package com.wjw.core.sheet.impl;

import com.wjw.core.sheet.intf.ISheetParser;
import com.wjw.core.sheet.intf.SheetParser;
import java.io.OutputStream;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NOknow
 * @version 1.0
 * @reateDate 2020/03/05
 * @description
 */
public class ExcelXlsxSheetParser extends SheetParser implements ISheetParser {

  private static final Logger logger = LoggerFactory.getLogger(ExcelXlsxSheetParser.class);

  private static final Pattern PATTERN = Pattern.compile("^[0-9_A-Za-z\\-]+(.xlsx)$");

  @Override
  public boolean match(String fileName) {
    return PATTERN.matcher(fileName).find();
  }

  @Override
  public void export(OutputStream outputStream, String fileName) {
    logger.info("ExcelXlsxSheetParser--export");
  }
}
