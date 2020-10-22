package com.cambi.claranet.model;

import com.cambi.claranet.model.abstr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvalidCommand extends Command {

  private String invalidCommand = "%s is an invalid command!";

  @Override
  public String getOutputFrom(String args) {
    return String.format(invalidCommand, args);
  }
}
