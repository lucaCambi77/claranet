package com.cambi.claranet.command;

import com.cambi.claranet.model.ExitCommand;
import com.cambi.claranet.model.InvalidCommand;
import com.cambi.claranet.model.ReadingCommand;
import com.cambi.claranet.model.abstr.Command;
import com.cambi.claranet.model.abstr.ValidCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class CommandStrategy {

  private Set<ValidCommand> commands = new HashSet<>();
  private InvalidCommand invalidCommand;

  @Autowired
  public CommandStrategy(
      ExitCommand exitCommand, ReadingCommand readingCommand, InvalidCommand invalidCommand) {
    commands.addAll(Arrays.asList(exitCommand, readingCommand));
    this.invalidCommand = invalidCommand;
  }

  public Command getCommandFrom(String input) {

    for (ValidCommand command : commands) if (command.matches(input)) return command;

    return invalidCommand;
  }
}
