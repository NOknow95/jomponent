package com.wjw.core.sheet.intf;

import java.io.OutputStream;

/**
 * refer to <a href='https://www.jianshu.com/p/6414185b2f01'>web page</a>
 *
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/03/05
 */
public interface ISheetParser {

  void export(OutputStream outputStream, String fileName);
}
