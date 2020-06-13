package com.wjw.jmd.test;

import com.wjw.jmd.database.DataBase;
import com.wjw.jmd.database.JMemoryDatabase;
import com.wjw.jmd.handler.Entity;
import com.wjw.jmd.key.KeyUtils;

/**
 * @author NOknow
 * @version 1.0
 * @date 2020/05/11
 */
public class Test {

  public static void main(String[] args) {
    DataBase dataBase = JMemoryDatabase.getInstance();
    long t1 = System.currentTimeMillis();
    for (int i = 0; i < 1000 * 10000; i++) {
      dataBase.put(new Hello(KeyUtils.id(Hello.class)));
    }
    long t = System.currentTimeMillis() - t1;
    System.out.println(String.format("costs %sms", t));
  }

  public static class Hello implements Entity {

    private String id;

    public Hello(String id) {
      this.id = id;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    @Override
    public String entityKey() {
      return this.id;
    }

    @Override
    public String toString() {
      return "Hello{" +
          "id='" + id + '\'' +
          '}';
    }
  }

}
