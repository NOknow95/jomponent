package com.wjw.jmd.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author NOknow
 * @version 1.0
 * @date 2020/05/11
 */
public class ReflectUitls {

  public static Map<String, Object> getPublicFieldNamesAndValueMap(Object object, Set<String> excludeFields)
      throws InvocationTargetException, IllegalAccessException {
    Method[] methods = object.getClass().getMethods();
    Map<String, Object> map = new HashMap<>();
    for (Method method : methods) {
      // 检测方法是否以 get 开头，且方法没有参数，且方法访问级别为 public
      if (method.getName().startsWith("get")
          && method.getParameterTypes().length == 0
          && Modifier.isPublic(method.getModifiers())) {
        // 获取属性名，比如 getName 方法对应属性名 name
        String property = method.getName().length() > 3 ?
            method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4) : "";
        if (excludeFields != null && excludeFields.contains(property)) {
          continue;
        }
        Object value = method.invoke(object);
        map.put(property, value);
      }
    }
    return map;
  }
}
