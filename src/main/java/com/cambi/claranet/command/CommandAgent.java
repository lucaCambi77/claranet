package com.cambi.claranet.command;

import com.cambi.claranet.model.Command;
import org.springframework.stereotype.Component;

@Component
public class CommandAgent {

  public void execute(Command command, String input) {
    String output = command.getOutputFrom(input);
    System.out.println(output);
  }
}
