package com.cambi.claranet.command.model;

import com.cambi.claranet.command.model.abstr.ValidCommand;
import com.cambi.claranet.repository.Repository;
import com.cambi.claranet.user.Post;
import com.cambi.claranet.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReadingCommand extends ValidCommand {

  final Repository repository;

  @Override
  public String execute(String input) {
    Optional<User> aUser = repository.getUser(input.trim());

    if (!aUser.isPresent()) return null;

    User user = aUser.get();

    List<Post> posts = user.getPosts();

    return posts.stream()
        .sorted((p1, p2) -> p2.getPublishDate().compareTo(p1.getPublishDate()))
        .map(Object::toString)
        .collect(Collectors.joining("\n"));
  }

  @Override
  public String getPattern() {
    return "^[A-Za-z0-9]+$";
  }
}
