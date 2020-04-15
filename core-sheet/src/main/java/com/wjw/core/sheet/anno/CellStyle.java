package com.wjw.core.sheet.anno;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.wjw.core.sheet.component.FontStyle.Underline;
import com.wjw.core.sheet.constant.FontConst;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/04/13
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface CellStyle {

  boolean bold() default false;

  boolean italic() default false;

  short fontHeight() default FontConst.INVALID_FONT_HEIGHT;

  IndexedColors fontColor() default IndexedColors.BLACK;

  IndexedColors bgColor() default IndexedColors.WHITE;

  Underline underline() default Underline.UNDERLINE_NONE;

  boolean rowBorder() default true;

  boolean columnBorder() default true;

  CellType dataType() default CellType.STRING;

  String fontColorStr() default "#FFFFFFFF";

  String bgColorStr() default "#00000000";
}
