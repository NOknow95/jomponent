package com.wjw.jmd.test;

import com.wjw.jmd.handler.Entity;

/**
 * @author NOknow
 * @version 1.0
 * @date 2020/05/11
 */
public class Student implements Entity {

  private String id;
  private String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String entityKey() {
    return this.id;
  }
}
