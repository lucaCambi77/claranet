package com.cambi.claranet.model.abstr;

public abstract class Command {

  public abstract String getOutputFrom(String args);

  public boolean isExitCommand() {
    return false;
  }
}
