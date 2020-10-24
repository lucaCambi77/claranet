package com.cambi.claranet.command.model;

import com.cambi.claranet.command.model.abstr.ValidCommand;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand extends ValidCommand {

  @Override
  public String execute(String args) {
    return args + " ... Bye Bye!";
  }

  @Override
  public String getPattern() {
    return "^exit$";
  }

  @Override
  public boolean isExitCommand() {
    return true;
  }
}
