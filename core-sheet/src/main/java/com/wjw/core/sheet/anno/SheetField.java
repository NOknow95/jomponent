package com.wjw.core.sheet.anno;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.wjw.core.sheet.component.DataType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD})
@Retention(RUNTIME)
public @interface SheetField {

  String name();

  int ordinal();

  DataType dataType() default DataType.STRING;

  boolean displayable() default true;

  CellStyle cellStyle() default @CellStyle;

  boolean overrideFormStyle() default false;

}
