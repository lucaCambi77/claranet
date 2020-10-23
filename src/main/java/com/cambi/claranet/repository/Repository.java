package com.cambi.claranet.repository;

import com.cambi.claranet.user.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Repository {
  private Map<String, User> userMap = new HashMap<>();

  public Optional<User> getUser(String userName) {
    return null == userMap.get(userName) ? Optional.empty() : Optional.of(userMap.get(userName));
  }

  public User createUser(String userName) {
    userMap.putIfAbsent(
        userName,
        User.builder().name(userName).following(new HashSet<>()).posts(new ArrayList<>()).build());

    return getUser(userName).get();
  }

  public User updateUser(String userName, User user) {
    if (null == user || null == userMap.get(userName)) return null;

    return userMap.put(userName, user);
  }
}
