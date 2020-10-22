package com.cambi.claranet.command;

import com.cambi.claranet.command.model.FollowCommand;
import com.cambi.claranet.command.model.ReadingCommand;
import com.cambi.claranet.command.model.UserPostCommand;
import com.cambi.claranet.command.model.WallCommand;
import com.cambi.claranet.repository.Repository;
import com.cambi.claranet.user.Post;
import com.cambi.claranet.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ModelCommandTest {

  @InjectMocks private UserPostCommand userPostCommand;
  @InjectMocks private ReadingCommand readingCommand;
  @InjectMocks private FollowCommand followCommand;
  @InjectMocks private WallCommand wallCommand;

  @Mock Repository repository;
  @Mock User user;
  @Mock User following;

  @BeforeEach
  public void setUp() {
    Mockito.lenient().doCallRealMethod().when(user).setName(anyString());
    Mockito.lenient().doCallRealMethod().when(user).setPosts(any());
    Mockito.lenient().doCallRealMethod().when(user).setFollowing(anySet());
    Mockito.lenient().doCallRealMethod().when(user).getFollowing();
    Mockito.lenient().doCallRealMethod().when(user).getPosts();
  }

  @Test
  public void should_add_user_and_post() {

    Mockito.lenient().when(repository.getUser("User")).thenReturn(Optional.empty());
    Mockito.lenient().when(repository.createUser("User")).thenReturn(user);

    assertNull(userPostCommand.execute("User -> Hello World!"));

    verify(repository).getUser("User");
    verify(repository).createUser("User");
    verify(repository).updateUser("User", user);
    verify(user).addPost("Hello World!");
  }

  @Test
  public void should_read_user_posts() {
    user.setName("User");
    user.setPosts(
        new ArrayList<>(
            Arrays.asList(
                Post.builder().userName("User").publishDate(new Date()).build(),
                Post.builder().userName("User").publishDate(new Date()).build())));

    Mockito.lenient().when(repository.getUser("User")).thenReturn(Optional.of(user));
    String output = readingCommand.execute("User");

    String[] feed = output.split("\n");
    assertEquals(2, feed.length);
  }

  @Test
  public void should_follow_user() {

    Mockito.lenient().when(user.getFollowing()).thenReturn(new HashSet<>(Collections.singletonList(following)));

    Mockito.lenient().when(repository.getUser("User")).thenReturn(Optional.of(user));
    Mockito.lenient().when(repository.getUser("User2")).thenReturn(Optional.of(following));

    assertNull(followCommand.execute("User follow User2"));

    verify(repository).getUser("User");
    verify(repository).getUser("User2");
    verify(user).addToFollowing(following);
    verify(repository).updateUser("User", user);
  }

  @Test
  public void should_create_wall() {
    user.setName("User");
    user.setPosts(
        Arrays.asList(
            Post.builder().userName("User").publishDate(new Date()).build(),
            Post.builder().userName("User").publishDate(new Date()).build()));

    User followed =
        User.builder()
            .name("Followed")
            .posts(
                Arrays.asList(
                    Post.builder().userName("User").publishDate(new Date()).build(),
                    Post.builder().userName("User").publishDate(new Date()).build()))
            .build();

    user.setFollowing(new HashSet<>(Collections.singletonList(followed)));

    Mockito.lenient().when(repository.getUser("User")).thenReturn(Optional.of(user));
    String output = wallCommand.execute("User");

    String[] feed = output.split("\n");
    assertEquals(4, feed.length);
  }
}
