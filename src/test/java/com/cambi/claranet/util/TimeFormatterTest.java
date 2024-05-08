package com.cambi.claranet.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import org.junit.jupiter.api.Test;

public class TimeFormatterTest {

  @Test
  public void formatTimeDifferenceTest() {
    Date date = new Date();
    String response = PostTimeFormatter.formatTimeDifference(date);
    assertEquals("(0 second ago)", response);
  }

  @Test
  public void formatTimeDifferenceTestWithMinutes() {
    Date date = new Date(System.currentTimeMillis() - 10 * 60 * 1000L);
    String response = PostTimeFormatter.formatTimeDifference(date);
    assertEquals("(10 minutes ago)", response);
  }

  @Test
  public void formatTimeDifferenceTestWithHours() {
    Date date = new Date(System.currentTimeMillis() - 1 * 60 * 60 * 1000L);
    String response = PostTimeFormatter.formatTimeDifference(date);
    assertEquals("(1 hour ago)", response);
  }

  @Test
  public void formatTimeDifferenceTestWithDays() {
    Date date = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L);
    String response = PostTimeFormatter.formatTimeDifference(date);
    assertEquals("(1 day ago)", response);
  }
}
