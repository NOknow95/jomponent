package com.wjw.core.sheet.intf;

import java.io.OutputStream;
import java.util.List;

/**
 * @author NOknow
 * @version 1.0
 * @reateDate 2020/03/05
 * @description
 */
public abstract class SheetParser {

  List<Object[]> lines;

  public abstract boolean match(String fileName);

  public List<Object[]> getLines() {
    return lines;
  }

  public void setLines(List<Object[]> lines) {
    this.lines = lines;
  }

  public abstract void export(OutputStream outputStream, String fileName);
}
