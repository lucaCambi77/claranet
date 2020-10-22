package com.cambi.claranet.command.model.abstr;

public abstract class Command {

  public abstract String execute(String args);

  public boolean isExitCommand() {
    return false;
  }
}
