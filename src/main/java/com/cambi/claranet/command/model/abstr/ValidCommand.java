package com.cambi.claranet.command.model.abstr;

public abstract class ValidCommand extends Command {

  public abstract String getPattern();

  public boolean matches(String input) {
    return input.matches(getPattern());
  }
}
