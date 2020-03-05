package com.jw.core.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.Locale;

/**
 * @author wang.jianwen
 * @version 1.0
 * @CreateDate 2020/03/03
 * @Desc
 */
public class DateUtils {
  private DateUtils() {
  }

  public static void main(String[] args){
    ZonedDateTime zdt = ZonedDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm ZZZZ");
    System.out.println(formatter.format(zdt));

    DateTimeFormatter zhFormatter = DateTimeFormatter.ofPattern("yyyy MMM dd EE HH:mm", Locale.CHINA);
    System.out.println(zhFormatter.format(zdt));

    DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("E, MMMM/dd/yyyy HH:mm", Locale.US);
    System.out.println(usFormatter.format(zdt));
  }
}
