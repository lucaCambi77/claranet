package com.cambi.claranet.command.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class ModelMatcherTest {

  @InjectMocks private ExitCommand exitCommand;
  @InjectMocks private PostCommand postCommand;
  @InjectMocks private InvalidCommand invalidCommand;
  @InjectMocks private WallCommand wallCommand;
  @InjectMocks private FollowCommand followCommand;

  @Test
  public void checkInvalidCommand() {

    ReflectionTestUtils.setField(invalidCommand, "invalidCommand", "Very bad command : %s");
    assertEquals("Very bad command : some bad command", invalidCommand.getOutputFrom("some bad command"));
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

    assertFalse(postCommand.isExitCommand());
    assertTrue(postCommand.matches("Alice -> Hello World!"));
    assertTrue(postCommand.matches("Alice09 -> Hello World!"));
    assertFalse(postCommand.matches("Alice09 -> "));
    assertFalse(postCommand.matches("Alice09"));
    assertFalse(postCommand.matches("Alice09 wall"));
    assertFalse(postCommand.matches("Alice09 follow Julia"));
    assertFalse(postCommand.matches(" -> Hello World!"));
    assertFalse(postCommand.matches("exit"));
  }

  @Test
  public void checkWallCommand() {

    assertFalse(wallCommand.isExitCommand());
    assertTrue(wallCommand.matches("Alice wall"));
    assertFalse(wallCommand.matches("Alice09 -> Hello World!"));
    assertFalse(wallCommand.matches("Alice09 follow Julia"));
    assertFalse(wallCommand.matches("exit"));
  }

  @Test
  public void checkFollowCommand() {

    assertFalse(followCommand.isExitCommand());
    assertTrue(followCommand.matches("Alice follow Julia"));
    assertFalse(followCommand.matches("Alice09 -> Hello World!"));
    assertFalse(followCommand.matches("Alice09 wall"));
    assertFalse(followCommand.matches("exit"));
  }
}
