package com.cambi.claranet.command.model;

import com.cambi.claranet.command.model.abstr.ValidCommand;
import com.cambi.claranet.repository.Repository;
import com.cambi.claranet.user.Post;
import com.cambi.claranet.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReadingCommand extends ValidCommand {

  final Repository repository;

  @Override
  public String execute(String input) {
    User user = repository.getUser(input).get();

    List<Post> posts = user.getPosts();

    return posts.stream()
        .sorted(Comparator.comparing(Post::getPublishDate))
        .map(Object::toString)
        .collect(Collectors.joining("\n"));
  }

  @Override
  public Pattern getPattern() {
    return Pattern.compile("^[A-Za-z0-9]+$");
  }
}
