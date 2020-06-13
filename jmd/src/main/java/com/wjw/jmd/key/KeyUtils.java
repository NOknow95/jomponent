package com.wjw.jmd.key;

import com.wjw.jmd.handler.Entity;
import java.util.UUID;

/**
 * @author NOknow
 * @version 1.0
 * @date 2020/05/12
 */
public class KeyUtils {
  public static final String separator = "#_#";

  private static final KeyGenerator<Entity> KEY_GENERATOR = new DefaultKeyGenerator<>();
  private KeyUtils(){}

  public static String id(Class<?> clazz){
    return clazz.getName() + separator + UUID.randomUUID().toString();
  }
}
