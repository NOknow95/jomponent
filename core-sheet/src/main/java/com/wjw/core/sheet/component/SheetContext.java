package com.wjw.core.sheet.component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Data;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
@Data
public class SheetContext<T> {

  private String sheetName;
  private SheetHeader sheetHeader;
  private List<T> formData;
  private int formDataStartRowIndexPaddingTop;
  private int formDataStartColumnIndexPaddingLeft;
  private SheetFooter SheetFooter;

  public SheetContext() {
    sheetHeader = new SheetHeader();
    formData = new LinkedList<>();
    formDataStartRowIndexPaddingTop = 0;
    formDataStartColumnIndexPaddingLeft = 0;
    SheetFooter = new SheetFooter();
  }

  @Data
  public static class SheetHeader {

    private List<SheetRowData> headerRowData;

    public SheetHeader() {
      this.headerRowData = new LinkedList<>();
    }

    public SheetHeader(List<SheetRowData> headerRowData) {
      this.headerRowData = headerRowData;
      if (this.headerRowData == null) {
        this.headerRowData = new LinkedList<>();
      }
    }
  }

  @Data
  public static class SheetFooter {

    private Integer startRowIndexPaddingTop;
    private Map<Integer, List<SheetColumnCell>> footerRowData;

    public SheetFooter() {
      this.startRowIndexPaddingTop = 0;
      this.footerRowData = new TreeMap<>();
    }

    public SheetFooter(Integer startRowIndexPaddingTop,
        Map<Integer, List<SheetColumnCell>> footerRowData) {
      this.startRowIndexPaddingTop = startRowIndexPaddingTop;
      this.footerRowData = footerRowData;
      if (this.startRowIndexPaddingTop < 0) {
        this.startRowIndexPaddingTop = 0;
      }
      if (this.footerRowData == null) {
        this.footerRowData = new TreeMap<>();
      }
    }
  }
}
