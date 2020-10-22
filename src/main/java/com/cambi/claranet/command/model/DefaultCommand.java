package com.cambi.claranet.command.model;

import com.cambi.claranet.command.model.abstr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultCommand extends Command {

  private String defaultCommand = null;

  @Override
  public String execute(String args) {
    return defaultCommand;
  }
}
