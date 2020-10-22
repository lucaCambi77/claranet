package com.cambi.claranet.command.model;

import com.cambi.claranet.command.model.abstr.ValidCommand;
import com.cambi.claranet.repository.Repository;
import com.cambi.claranet.user.User;
import com.cambi.claranet.util.PostTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserPostCommand extends ValidCommand {

  final Repository repository;

  @Override
  public String execute(String input) {

    String[] splittedInput = input.split("->");

    String user = splittedInput[0].trim();

    User userObject = repository.getUser(user).orElse(repository.createUser(user));

    userObject.addPost(splittedInput[1].trim());

    repository.updateUser(user, userObject);

    return null;
  }

  @Override
  public Pattern getPattern() {
    Pattern compile = Pattern.compile("^[A-Za-z-0-9]+ -> (.+)$");
    return compile;
  }
}
