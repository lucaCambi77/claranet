package com.cambi.claranet.application;

import com.cambi.claranet.ClaranetApplication;
import com.cambi.claranet.model.ExitCommand;
import com.cambi.claranet.model.ReadingCommand;
import com.cambi.claranet.command.CommandExecutor;
import com.cambi.claranet.command.CommandScanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class ClaranetApplicationTest {

  @InjectMocks ClaranetApplication claranetApplication;

  @Mock ReadingCommand readCommand;

  @Mock ExitCommand exitCommand;

  @Mock CommandScanner commandScanner;

  @Mock CommandExecutor commandExecutor;

  @BeforeEach
  public void setUp() {
    Mockito.lenient().when(exitCommand.isExitCommand()).thenCallRealMethod();
  }

  @Test
  public void should_get_command_and_terminate_program() {
    String read = "read";
    String exit = "exit";

    when(commandExecutor.execute(anyString())).thenReturn(readCommand, exitCommand);
    when(commandScanner.getNextLine()).thenReturn(read, exit);

    claranetApplication.run();

    verify(commandExecutor, times(1)).execute(read);
    verify(commandExecutor, times(1)).execute(exit);
    verify(commandScanner, times(2)).getNextLine();
  }
}
