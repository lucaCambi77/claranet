package com.cambi.claranet.command;

import com.cambi.claranet.command.model.*;
import com.cambi.claranet.command.model.abstr.Command;
import com.cambi.claranet.command.model.abstr.ValidCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class CommandStrategy {

  private final Set<ValidCommand> commands = new HashSet<>();
  private final InvalidCommand invalidCommand;

  @Autowired
  public CommandStrategy(
      FollowCommand followCommand,
      UserPostCommand userPostCommand,
      WallCommand wallCommand,
      ExitCommand exitCommand,
      ReadingCommand readingCommand,
      InvalidCommand invalidCommand) {
    commands.addAll(
        Arrays.asList(followCommand, userPostCommand, exitCommand, wallCommand, readingCommand));
    this.invalidCommand = invalidCommand;
  }

  public Command getCommandFrom(String input) {

    for (ValidCommand command : commands) if (command.matches(input)) return command;

    return invalidCommand;
  }
}
