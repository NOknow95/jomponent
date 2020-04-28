package com.wjw.core.sheet.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author NOknow
 * @version 1.0
 * @reateDate 2020/03/05
 * @description
 */
public class TestRegex {

  @Test
  void test() {
    Pattern compile = Pattern.compile("^[0-9_A-Za-z\\-]+(.csv)$");
    Matcher matcher = compile.matcher("test_1-2.csv");
    Assertions.assertTrue(matcher.find());
    Matcher matcher1 = compile.matcher("test._1-2.csv");
    Assertions.assertFalse(matcher1.find());
  }
}
