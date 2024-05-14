package com.cambi.claranet.user;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public record User(String name, List<Post> posts, HashSet<User> following) {

  public void addPost(String message) {
    posts.add(new Post(name, message, new Date()));
  }

  public void addToFollowing(User user) {
    if (!following.contains(user)) following.add(user);
  }
}
