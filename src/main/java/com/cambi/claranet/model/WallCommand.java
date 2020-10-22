package com.cambi.claranet.model;

import com.cambi.claranet.model.abstr.ValidCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class WallCommand extends ValidCommand {

  @Override
  public String getOutputFrom(String args) {
    return null;
  }

  @Override
  public Pattern getPattern() {
    return Pattern.compile("^[A-Za-z-0-9]+ wall$");
  }
}
