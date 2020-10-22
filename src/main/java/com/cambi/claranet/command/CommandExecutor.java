package com.cambi.claranet.command;

import com.cambi.claranet.command.model.abstr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandExecutor {

  final CommandStrategy commandStrategy;
  final CommandAgent commandAgent;

  public Command execute(String input) {
    Command command = commandStrategy.getCommandFrom(input);
    commandAgent.execute(command, input);
    return command;
  }
}
