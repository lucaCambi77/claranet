package com.cambi.claranet.model;

import static org.junit.jupiter.api.Assertions.*;

import com.cambi.claranet.command.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ModelMatcherTest {

  @InjectMocks private ExitCommand exitCommand;
  @InjectMocks private UserPostCommand postCommand;
  @InjectMocks private DefaultCommand invalidCommand;
  @InjectMocks private WallCommand wallCommand;
  @InjectMocks private FollowCommand followCommand;

  @Test
  public void checkInvalidCommand() {

    assertNull(invalidCommand.execute("some bad command"));
  }

  @Test
  public void checkExitCommand() {

    assertTrue(exitCommand.isExitCommand());
    assertTrue(exitCommand.matches("exit"));
    assertFalse(exitCommand.matches("Alice -> Hello World!"));
    assertFalse(exitCommand.matches("Alice follow Julia"));
    assertFalse(exitCommand.matches("Alice wall"));
  }

  @Test
  public void checkPostCommand() {

    Assertions.assertFalse(postCommand.isExitCommand());
    Assertions.assertTrue(postCommand.matches("Alice -> Hello World!"));
    Assertions.assertTrue(postCommand.matches("Alice09 -> Hello World!"));
    Assertions.assertFalse(postCommand.matches("Alice09 -> "));
    Assertions.assertFalse(postCommand.matches("Alice09"));
    Assertions.assertFalse(postCommand.matches("Alice09 wall"));
    Assertions.assertFalse(postCommand.matches("Alice09 follow Julia"));
    Assertions.assertFalse(postCommand.matches(" -> Hello World!"));
    Assertions.assertFalse(postCommand.matches("exit"));
  }

  @Test
  public void checkWallCommand() {

    Assertions.assertFalse(wallCommand.isExitCommand());
    Assertions.assertTrue(wallCommand.matches("Alice wall"));
    Assertions.assertFalse(wallCommand.matches("Alice09 -> Hello World!"));
    Assertions.assertFalse(wallCommand.matches("Alice09 follow Julia"));
    Assertions.assertFalse(wallCommand.matches("exit"));
  }

  @Test
  public void checkFollowCommand() {

    Assertions.assertFalse(followCommand.isExitCommand());
    Assertions.assertTrue(followCommand.matches("Alice follow Julia"));
    Assertions.assertFalse(followCommand.matches("Alice09 -> Hello World!"));
    Assertions.assertFalse(followCommand.matches("Alice09 wall"));
    Assertions.assertFalse(followCommand.matches("exit"));
  }
}
