package com.cambi.claranet.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class InvalidCommand extends Command {

  @Override
  public String getOutputFrom(String args) {
    return String.format("Command: %s is invalid!", args);
  }

  @Override
  public boolean matches(String input) {
    return false;
  }

  @Override
  public Pattern getPattern() {
    return null;
  }
}
