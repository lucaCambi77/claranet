package com.cambi.claranet.user;

import com.cambi.claranet.util.PostTimeFormatter;
import java.util.Date;

public record Post(String userName, String message, Date publishDate) {

  @Override
  public String toString() {
    return userName
        + " -> "
        + String.format("%s %s", message, PostTimeFormatter.formatTimeDifference(this.publishDate));
  }
}
