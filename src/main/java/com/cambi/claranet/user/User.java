package com.cambi.claranet.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Builder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(of = "name")
public class User {
  private String name;
  private List<Post> posts = new ArrayList<>();
  private Set<User> following = new HashSet<>();

  public void addPost(String message) {
    posts.add(Post.builder().message(message).publishDate(new Date()).build());
  }

  public void addToFollowing(User user) {
    if (!following.contains(user)) following.add(user);
  }
}
