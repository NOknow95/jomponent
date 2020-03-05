package com.wjw.core.utils.other.collection;

import java.util.Collection;
import java.util.Map;

/**
 * @author wang.jianwen
 * @version 1.0
 * @CreateDate 2020/03/05
 * @Desc
 */
public class CollectionUtils {

  public static boolean isEmpty(Map map) {
    return map == null || map.isEmpty();
  }
  public static boolean isEmpty(Collection collection) {
    return collection == null || collection.isEmpty();
  }
}
