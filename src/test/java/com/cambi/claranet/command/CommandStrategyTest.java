package com.cambi.claranet.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.cambi.claranet.command.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommandStrategyTest {

  @InjectMocks CommandStrategy commandStrategy;
  @Mock ReadingCommand readingCommand;

  @Mock ExitCommand exitCommand;
  @Mock FollowCommand followCommand;
  @Mock UserPostCommand userPostCommand;
  @Mock WallCommand wallCommand;
  @Mock DefaultCommand defaultCommand;

  @Test
  public void should_find_command() {

    String input = "input";
    when(readingCommand.matches(input)).thenReturn(true);

    assertEquals(readingCommand, commandStrategy.getCommandFrom(input));
  }

  @Test
  public void should_not_find_command() {

    String input = "input";

    assertEquals(defaultCommand, commandStrategy.getCommandFrom(input));
  }
}
