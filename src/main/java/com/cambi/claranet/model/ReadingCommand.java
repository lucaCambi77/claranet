package com.cambi.claranet.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ReadingCommand extends Command {

  @Override
  public String getOutputFrom(String args) {
    return null;
  }

  @Override
  public Pattern getPattern() {
    return Pattern.compile("^[A-Za-z0-9]+$");
  }
}
