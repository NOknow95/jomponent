package com.wjw.core.sheet.other;

import com.wjw.core.sheet.anno.FormStyle;
import com.wjw.core.sheet.anno.SheetField;
import com.wjw.core.sheet.bean.StudentInfo;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/14
 */
public class TestReflection {

  @Test
  void test1() throws IllegalAccessException, InstantiationException {
    FormStyle formStyle = StudentInfo.class.getAnnotation(FormStyle.class);
    if (formStyle == null) {
      formStyle = FormStyle.class.newInstance();
    }
    System.out.println(formStyle);

    Field[] fields = StudentInfo.class.getDeclaredFields();
    for (Field field : fields) {
      System.out.println(field.getName());
      SheetField annotation = field.getAnnotation(SheetField.class);
      if (annotation != null) {
        System.out.println(annotation.toString());
      }
    }
  }
}
