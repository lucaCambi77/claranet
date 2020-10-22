package com.cambi.claranet.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "name")
public class User {
  private String name;
  private List<Post> posts;
  private Set<User> following;

  public void addPost(String message) {
    posts.add(Post.builder().userName(name).message(message).publishDate(new Date()).build());
  }

  public void addToFollowing(User user) {
    if (!following.contains(user)) following.add(user);
  }
}
