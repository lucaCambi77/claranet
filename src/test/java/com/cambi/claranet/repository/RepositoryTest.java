package com.cambi.claranet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Server;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RepositoryTest {

  Server.Repository repository = new Server.Repository();

  @Test
  public void should_create_user() {
    repository.createUser("User");
    assertTrue(repository.getUser("User").isPresent());
  }

  @Test
  public void should_update_user() {
    repository.createUser("User");

    assertTrue(repository.getUser("User").isPresent());

    assertEquals(0, repository.getUser("User").get().posts().size());
    Server.User newUser = repository.getUser("User").get();

    newUser.addPost("Hello world");
    newUser.addPost("Hello world2");

    assertEquals(2, repository.getUser("User").get().posts().size());
    assertEquals("User", repository.getUser("User").get().name());

    repository.createUser("Followed");

    assertTrue(repository.getUser("Followed").isPresent());

    Server.User followed = repository.getUser("Followed").get();
    followed.addPost("Hello world");
    followed.addPost("Hello world2");

    Server.User updatedUser = repository.getUser("User").get();
    updatedUser.addToFollowing(repository.getUser("Followed").get());

    assertEquals(2, repository.getUser("User").get().posts().size());
    assertEquals(1, repository.getUser("User").get().following().size());
    assertEquals(2, repository.getUser("User").get().following().iterator().next().posts().size());
    assertEquals("Followed", repository.getUser("User").get().following().iterator().next().name());
  }
}
