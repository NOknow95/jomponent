package com.wjw.jmd.database;

import com.wjw.core.utils.other.collection.CollectionUtils;
import com.wjw.jmd.handler.Entity;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/28
 */
public class JMemoryDatabase implements DataBase, Cloneable, Serializable {

  private static transient JMemoryDatabase jMemoryDatabase;
  private static transient DatabaseMainMap databaseMainMap;

  private JMemoryDatabase() {
    databaseMainMap = new DatabaseMainMap();
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

  @Override
  public <T extends Entity> T get(String key, Class<T> clazz) {
    DataMapHolder dataMapHolder = databaseMainMap.get(clazz);
    return dataMapHolder == null ? null : dataMapHolder.get(key);
  }

  @Override
  public String put(Entity entity) {
    databaseMainMap.comput(entity.getClass(), (key, dataMapHolder) -> {
      if (dataMapHolder == null) {
        dataMapHolder = new DataMapHolder();
      }
      try {
        dataMapHolder.addOrUpdate(entity.entityKey(), entity);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return dataMapHolder;
    });
    return entity.entityKey();
  }

  @Override
  public int remove(String key, Class<?> clazz) {
    DataMapHolder dataMapHolder = databaseMainMap.get(clazz);
    dataMapHolder.remove(key);
    databaseMainMap.remove(clazz, key);
    return 0;
  }

  static class DatabaseMainMap {

    private static Map<String, DataMapHolder> dataMap;

    DatabaseMainMap() {
      dataMap = new ConcurrentHashMap<>();
    }

    public void put(Class<?> clazz, DataMapHolder dataMapHolder) {
      dataMap.put(clazz.getName(), dataMapHolder);
    }

    void comput(Class<?> clazz, BiFunction<? super String, ? super DataMapHolder, ? extends DataMapHolder> remappingFunction) {
      dataMap.compute(clazz.getName(), remappingFunction);
    }

    DataMapHolder get(Class<?> clazz) {
      return dataMap.get(clazz.getName());
    }

    void remove(Class<?> clazz, String key) {
      //TODO non-operate for now
    }
  }

  static class DataMapHolder {

    private Map<String, Object> entityMap;

    private Map<Object, Set<String>> fieldMap;

    DataMapHolder() {
      entityMap = new ConcurrentHashMap<>();
      fieldMap = new ConcurrentHashMap<>();
    }

    <T extends Entity> String addOrUpdate(String key, T entity) throws InvocationTargetException, IllegalAccessException {
      entityMap.put(key, entity);
      Map<Object, String> fieldKeyMap = entity.fieldKeyMap();
      fieldKeyMap.forEach((fieldValue, keyTemp) -> fieldMap.compute(fieldValue, (k, v) -> {
        if (v == null) {
          return new HashSet<>(fieldKeyMap.values());
        } else {
          v.addAll(fieldKeyMap.values());
          return v;
        }
      }));
      return key;
    }

    Object remove(String key) {
      Object remove = entityMap.remove(key);
      fieldMap.forEach((k, v) -> {
        if (!CollectionUtils.isEmpty(v)) {
          v.remove(key);
        }
      });
      return remove;
    }

    @SuppressWarnings("unchecked")
    <T extends Entity> T get(String key) {
      return (T) entityMap.get(key);
    }
  }
}
