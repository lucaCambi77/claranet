package com.cambi.claranet.model;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ExitCommand extends Command {

  @Override
  public String getOutputFrom(String args) {
    return args + " ... Bye Bye!";
  }

  @Override
  public Pattern getPattern() {
    return Pattern.compile("^exit$");
  }

  @Override
  public boolean isExitCommand() {
    return true;
  }
}
