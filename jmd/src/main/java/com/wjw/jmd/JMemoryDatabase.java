package com.wjw.jmd;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/28
 */
public class JMemoryDatabase {

  private static JMemoryDatabase jMemoryDatabase;

  private JMemoryDatabase() {

  }

  public static JMemoryDatabase getInstance() {
    if (jMemoryDatabase == null) {
      synchronized (JMemoryDatabase.class) {
        if (jMemoryDatabase == null) {
          jMemoryDatabase = new JMemoryDatabase();
        }
      }
    }
    return jMemoryDatabase;
  }
}
