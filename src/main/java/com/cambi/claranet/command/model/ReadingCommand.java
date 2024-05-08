package com.cambi.claranet.command.model;

import com.cambi.claranet.command.model.abstr.ValidCommand;
import com.cambi.claranet.repository.Repository;
import com.cambi.claranet.user.User;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadingCommand extends ValidCommand {

  final Repository repository;

  @Override
  public String execute(String input) {
    Optional<User> aUser = repository.getUser(input.trim());

    return aUser.map(user -> user.getPosts().stream()
            .sorted((p1, p2) -> p2.getPublishDate().compareTo(p1.getPublishDate()))
            .map(Object::toString)
            .collect(Collectors.joining("\n"))).orElse(null);
  }

  @Override
  public String getPattern() {
    return "^[A-Za-z0-9]+$";
  }
}
