package com.wjw.jmd.key;

import com.wjw.jmd.handler.Entity;

/**
 * @author NOknow
 * @version 1.0
 * @date 2020/05/09
 */
public interface KeyGenerator<T extends Entity> {

  String generateId(Class<T> clazz);
}
