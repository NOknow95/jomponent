package com.wjw.jmd.key;

import com.wjw.jmd.handler.Entity;
import java.util.UUID;

/**
 * @author NOknow
 * @version 1.0
 * @date 2020/05/11
 */
public class DefaultKeyGenerator<T extends Entity> implements KeyGenerator<T> {

  @Override
  public String generateId(Class<T> clazz) {
    return KeyUtils.id(clazz);
  }
}
