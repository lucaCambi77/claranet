package com.cambi.claranet.command.model;

import com.cambi.claranet.command.model.abstr.ValidCommand;
import com.cambi.claranet.repository.Repository;
import com.cambi.claranet.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPostCommand extends ValidCommand {

  final Repository repository;

  @Override
  public String execute(String input) {

    String[] splittedInput = input.split("->");

    String user = splittedInput[0].trim();

    User userObject = repository.getUser(user).orElseGet(() -> repository.createUser(user));

    userObject.addPost(splittedInput[1].trim());

    repository.updateUser(user, userObject);

    return null;
  }

  @Override
  public String getPattern() {
    return "^[A-Za-z-0-9]+ -> (.+)$";
  }
}
