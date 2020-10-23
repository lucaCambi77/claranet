package com.cambi.claranet.command;

import com.cambi.claranet.command.model.UserPostCommand;
import com.cambi.claranet.command.model.WallCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CommandPrintTest {

  @InjectMocks CommandAgent commandAgent;
  @Mock WallCommand wallCommand;
  @Mock UserPostCommand userPostCommand;

  final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  final PrintStream originalOut = System.out;
  final PrintStream originalErr = System.err;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  public void should_print_output() {
    Mockito.lenient().when(wallCommand.execute("input")).thenReturn("Something to display");

    commandAgent.execute(wallCommand, "input");
    assertEquals("Something to display\n", outContent.toString());
  }

  @Test
  public void should_not_print_output() {
    Mockito.lenient().when(userPostCommand.execute("input")).thenReturn(null);

    commandAgent.execute(wallCommand, "input");
    assertEquals("", outContent.toString());
  }
}
