package com.cambi.claranet.repository;

import com.cambi.claranet.user.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Repository {
  private Map<String, User> userMap = new HashMap<>();

  public Optional<User> getUser(String userName) {
    return Optional.ofNullable(userMap.get(userName));
  }

  public User createUser(String userName) {
    return userMap.putIfAbsent(
        userName,
        User.builder().name(userName).following(new HashSet<>()).posts(new ArrayList<>()).build());
  }

  public User updateUser(String userName, User user) {
    return userMap.put(userName, user);
  }
}
