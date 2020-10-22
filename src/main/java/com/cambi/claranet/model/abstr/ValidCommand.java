package com.cambi.claranet.model.abstr;

import java.util.regex.Pattern;

public abstract class ValidCommand extends Command {

  public boolean matches(String input) {
    return getPattern().matcher(input).matches();
  }

  public abstract Pattern getPattern();
}
