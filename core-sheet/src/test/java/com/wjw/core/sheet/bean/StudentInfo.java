package com.wjw.core.sheet.bean;

import com.wjw.core.sheet.anno.FormStyle;
import com.wjw.core.sheet.anno.SheetField;
import lombok.Data;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2020/03/06
 */
@Data
@FormStyle()
public class StudentInfo {

  @SheetField(name = "name", ordinal = 1)
  private String name;
  @SheetField(name = "age", ordinal = 2)
  private Integer age;
  @SheetField(name = "address", ordinal = 3)
  private String address;
  @SheetField(name = "address", ordinal = 4, displayable = false)
  private String email;

  private String password;

}
