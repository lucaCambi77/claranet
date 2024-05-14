package com.cambi.claranet.repository;

import com.cambi.claranet.user.User;
import java.util.*;

public class Repository {
  private final Map<String, User> userMap = new HashMap<>();

  public Optional<User> getUser(String userName) {
    return null == userMap.get(userName) ? Optional.empty() : Optional.of(userMap.get(userName));
  }

  public User createUser(String userName) {
    userMap.putIfAbsent(userName, new User(userName, new ArrayList<>(), new HashSet<>()));

    return getUser(userName).get();
  }

  public User updateUser(String userName, User user) {
    if (null == user || null == userMap.get(userName)) return null;

    return userMap.put(userName, user);
  }

  public String savePost(String user, String message) {

    User userObject = getUser(user).orElseGet(() -> createUser(user));

    userObject.addPost(message);

    updateUser(user, userObject);

    return message;
  }
}
