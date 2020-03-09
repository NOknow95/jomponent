package com.wjw.core.sheet.impl;

import com.wjw.core.sheet.exception.SheetParserException;
import com.wjw.core.sheet.intf.ISheetParser;
import com.wjw.core.sheet.intf.SheetParser;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author NOknow
 * @version 1.0
 * @reateDate 2020/03/05
 * @description
 */
public class DefaultSheetParserImpl implements ISheetParser {
  //header
  //content: content header, content
  //footer

  public DefaultSheetParserImpl(List<Object[]> lines) {
    this.lines = lines;
  }

  private List<Object[]> lines;
  private SheetParser parser;
  private static final List<Class<?>> parserClasses = Arrays.asList(CsvSheetParser.class,
      ExcelXlsxSheetParser.class);

  @Override
  public void export(OutputStream outputStream, String fileName) {
    try {
      for (Class<?> parserClass : parserClasses) {
        SheetParser sheetParser = (SheetParser) parserClass.newInstance();
        if (sheetParser.match(fileName)) {
          parser = sheetParser;
          parser.setLines(lines);
          parser.export(outputStream, fileName);
          return;
        }
      }
    } catch (Exception e) {
      throw new SheetParserException("create sheet parser bean error.", e);
    }
    throw new SheetParserException("no sheet parser matched.");
  }

  public List<Object[]> getLines() {
    return lines;
  }

  public void setLines(List<Object[]> lines) {
    this.lines = lines;
  }

  public SheetParser getParser() {
    return parser;
  }
}
