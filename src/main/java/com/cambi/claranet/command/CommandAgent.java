package com.cambi.claranet.command;

import com.cambi.claranet.command.model.abstr.Command;
import org.springframework.stereotype.Component;

@Component
public class CommandAgent {

  public void execute(Command command, String input) {
    String output = command.execute(input);
    if (null != output) System.out.println(output);
  }
}
