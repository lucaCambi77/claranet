package com.cambi.claranet.model;

import java.util.regex.Pattern;

public abstract class Command {

  public abstract String getOutputFrom(String args);

  public boolean matches(String input) {
    return getPattern().matcher(input).matches();
  }

  abstract Pattern getPattern();

  public boolean isExitCommand() {
    return false;
  }
}
