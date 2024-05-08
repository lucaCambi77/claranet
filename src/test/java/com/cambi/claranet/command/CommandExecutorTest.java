package com.cambi.claranet.command;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cambi.claranet.command.model.ReadingCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommandExecutorTest {

  @InjectMocks CommandExecutor commandExecutor;
  @Mock CommandStrategy commandStrategy;
  @Mock CommandAgent commandAgent;
  @Mock ReadingCommand readingCommand;

  @Test
  public void should_execute_command() {

    String input = "input";

    when(commandStrategy.getCommandFrom(input)).thenReturn(readingCommand);

    commandExecutor.execute(input);

    verify(commandAgent).execute(readingCommand, input);
    verify(commandStrategy).getCommandFrom(input);
  }
}
