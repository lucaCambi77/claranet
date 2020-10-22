package com.cambi.claranet.command;

import com.cambi.claranet.command.model.*;
import com.cambi.claranet.command.model.abstr.Command;
import com.cambi.claranet.command.model.abstr.ValidCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashSet;

@Component
public class CommandStrategy {

  private final LinkedHashSet<ValidCommand> commands = new LinkedHashSet<>();
  private final InvalidCommand invalidCommand;

  @Autowired
  public CommandStrategy(
      ExitCommand exitCommand,
      FollowCommand followCommand,
      UserPostCommand userPostCommand,
      WallCommand wallCommand,
      ReadingCommand readingCommand,
      InvalidCommand invalidCommand) {
    commands.addAll(
        Arrays.asList(exitCommand, followCommand, userPostCommand, wallCommand, readingCommand));
    this.invalidCommand = invalidCommand;
  }

  public Command getCommandFrom(String input) {

    for (ValidCommand command : commands) if (command.matches(input)) return command;

    return invalidCommand;
  }
}
