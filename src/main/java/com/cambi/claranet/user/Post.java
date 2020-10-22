package com.cambi.claranet.user;

import com.cambi.claranet.util.PostTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class Post {

  private String userName;
  private String message;
  private Date publishDate;

  @Override
  public String toString() {

    return userName
        + " -> "
        + String.format("%s %s", message, PostTimeFormatter.formatTimeDifference(this.publishDate));
  }
}
