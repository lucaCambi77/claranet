package com.cambi.claranet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.cambi.claranet.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RepositoryTest {

  @InjectMocks Repository repository;

  @Test
  public void should_create_user() {
    repository.createUser("User");
    assertNotNull(repository.getUser("User").get());
  }

  @Test
  public void should_update_user() {
    repository.createUser("User");
    assertEquals(0, repository.getUser("User").get().getPosts().size());
    User newUser = repository.getUser("User").get();

    newUser.addPost("Hello world");
    newUser.addPost("Hello world2");

    assertEquals(2, repository.getUser("User").get().getPosts().size());
    assertEquals("User", repository.getUser("User").get().getName());

    repository.createUser("Followed");

    User followed = repository.getUser("Followed").get();
    followed.addPost("Hello world");
    followed.addPost("Hello world2");

    User updatedUser = repository.getUser("User").get();
    updatedUser.addToFollowing(repository.getUser("Followed").get());

    assertEquals(2, repository.getUser("User").get().getPosts().size());
    assertEquals(1, repository.getUser("User").get().getFollowing().size());
    assertEquals(
        2, repository.getUser("User").get().getFollowing().iterator().next().getPosts().size());
    assertEquals(
        "Followed", repository.getUser("User").get().getFollowing().iterator().next().getName());
  }
}
