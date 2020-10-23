package com.cambi.claranet.command.model;

import com.cambi.claranet.command.model.abstr.ValidCommand;
import com.cambi.claranet.repository.Repository;
import com.cambi.claranet.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class FollowCommand extends ValidCommand {

  final Repository repository;

  @Override
  public String execute(String input) {
    String[] splittedInput = input.split("follow");

    String user = splittedInput[0].trim();

    if (!repository.getUser(user).isPresent()
        || !repository.getUser(splittedInput[1].trim()).isPresent()) return null;

    User follower = repository.getUser(user).get();

    follower.addToFollowing(repository.getUser(splittedInput[1].trim()).get());

    repository.updateUser(user, follower);

    return null;
  }

  @Override
  public Pattern getPattern() {
    return Pattern.compile("^[A-Za-z-0-9]+ follow [A-Za-z-0-9]+$");
  }
}
