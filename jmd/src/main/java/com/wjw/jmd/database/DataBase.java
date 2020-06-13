package com.wjw.jmd.database;

import com.wjw.jmd.handler.Entity;

/**
 * @author NOknow
 * @version 1.0
 * @date 2020/05/11
 */
public interface DataBase {

  <T extends Entity> T get(String key, Class<T> clazz);

  String put(Entity entity);

  int remove(String key, Class<?> clazz);

}
