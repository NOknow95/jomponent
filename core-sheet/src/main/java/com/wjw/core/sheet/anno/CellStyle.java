package com.wjw.core.sheet.anno;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface CellStyle {

  int fontSize() default 18;

  IndexedColors fontIndexedColor() default IndexedColors.BLACK;

  IndexedColors bgIndexedColor() default IndexedColors.WHITE;

  String fontColor() default "#FFFFFFFF";

  String bgColor() default "#00000000";
}
