package com.cambi.claranet.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class ModelMatcherTest {

  @InjectMocks private ExitCommand exitCommand;
  @InjectMocks private PostCommand postCommand;

  @Test
  public void checkExitCommand() {

    assertTrue(exitCommand.isExitCommand());
    assertTrue(exitCommand.matches("exit"));
    assertFalse(exitCommand.matches("Alice -> Hello World!"));

  }

  @Test
  public void checkPostCommand() {

    assertFalse(postCommand.isExitCommand());
    assertTrue(postCommand.matches("Alice -> Hello World!"));
    assertTrue(postCommand.matches("Alice09 -> Hello World!"));
    assertFalse(postCommand.matches(" -> Hello World!"));
    assertFalse(postCommand.matches("exit"));
  }
}
