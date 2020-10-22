package com.cambi.claranet.repository;

import com.cambi.claranet.user.Post;
import com.cambi.claranet.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(JUnitPlatform.class)
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

    repository.updateUser(
        "User",
        User.builder()
            .name("User")
            .posts(
                Arrays.asList(
                    Post.builder().userName("User").publishDate(new Date()).build(),
                    Post.builder().userName("User").publishDate(new Date()).build()))
            .build());

    assertEquals(2, repository.getUser("User").get().getPosts().size());

    User updatedUser = repository.getUser("User").get();

    updatedUser.setFollowing(
        new HashSet(
            Arrays.asList(
                User.builder()
                    .name("User2")
                    .posts(
                        Arrays.asList(
                            Post.builder().userName("User").publishDate(new Date()).build(),
                            Post.builder().userName("User").publishDate(new Date()).build()))
                    .build())));

    assertEquals(2, repository.getUser("User").get().getPosts().size());
    assertEquals(1, repository.getUser("User").get().getFollowing().size());
    assertEquals(
        2, repository.getUser("User").get().getFollowing().iterator().next().getPosts().size());
  }
}
