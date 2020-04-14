package com.wjw.core.sheet.anno;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface FormStyle {

  CellStyle headerStyle() default @CellStyle(bold = true);

  CellStyle contentStyle() default @CellStyle;
}
