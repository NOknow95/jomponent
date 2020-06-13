package com.wjw.jmd.handler;

import com.wjw.core.utils.other.collection.CollectionUtils;
import com.wjw.jmd.utils.ReflectUitls;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author NOknow
 * @version 1.0
 * @date 2020/05/09
 * @description
 */
public interface Entity {

  String entityKey();

  default Set<String> excludeFields() {
    return Collections.singleton("class");
  }

  /**
   * fieldKeyMap
   * @return Map&lt;Object, String&gt; , generateId: field value. value: entity generateId
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  default Map<Object, String> fieldKeyMap() throws InvocationTargetException, IllegalAccessException {
    Map<String, Object> fieldNamesAndValueMap = ReflectUitls.getPublicFieldNamesAndValueMap(this, excludeFields());
    if (CollectionUtils.isEmpty(fieldNamesAndValueMap)) {
      throw new IllegalStateException(String.format("there is no public field in this class:%s", this.getClass().getName()));
    }
    String key = entityKey();
    return fieldNamesAndValueMap.values().stream()
        .collect(Collectors.toMap(fieldValue -> fieldValue, value -> key));
  }


}
