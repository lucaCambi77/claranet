package com.cambi.claranet.command.model;

import com.cambi.claranet.command.model.abstr.ValidCommand;
import com.cambi.claranet.repository.Repository;
import com.cambi.claranet.user.Post;
import com.cambi.claranet.user.User;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WallCommand extends ValidCommand {

  final Repository repository;

  @Override
  public String execute(String input) {
    Optional<User> aUser = repository.getUser(input.split("wall")[0].trim());

    if (!aUser.isPresent()) return null;

    User user = repository
            .getUser(input.split("wall")[0].trim()).get();

    ArrayList<Post> posts = new ArrayList<>(user.getPosts());

    user.getFollowing()
        .forEach(
            u -> {
              posts.addAll(u.getPosts());
            });

    return posts.stream()
        .sorted((p1, p2) -> p2.getPublishDate().compareTo(p1.getPublishDate()))
        .map(Object::toString)
        .collect(Collectors.joining("\n"));
  }

  @Override
  public String getPattern() {
    return "^[A-Za-z-0-9]+ wall$";
  }
}
