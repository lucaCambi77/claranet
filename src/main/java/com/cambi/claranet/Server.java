package com.cambi.claranet;

import com.cambi.claranet.repository.Repository;
import com.cambi.claranet.user.Post;
import com.cambi.claranet.user.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Server implements Runnable {

  private final ArrayList<ServerThread> threadList = new ArrayList<>();
  private final ExecutorService executorService = Executors.newCachedThreadPool();
  private ServerSocket serversocket;
  private final Map<String, User> userMap = new HashMap<>();
  private final Repository repository = new Repository();

  @Override
  public void run() {

    try {
      serversocket = new ServerSocket(5000);

      while (true) {
        Socket socket = serversocket.accept();
        ServerThread serverThread = new ServerThread(socket);
        threadList.add(serverThread);
        executorService.execute(serverThread);
      }
    } catch (Exception e) {
      System.out.println("Error occured in main: " + e.getStackTrace());
    }
  }

  public static void main(String[] args) {
    Server server = new Server();
    server.run();
  }

  public class ServerThread extends Thread {
    private final Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    public ServerThread(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {

      try {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

        output.println("Please provide a nickname : ");
        String nickName = input.readLine();

        // Create a user in the repository
        repository.createUser(nickName);
        broadcast(nickName + " joined!");

        String msg;

        while ((msg = input.readLine()) != null) {
          if (msg.matches("^[A-Za-z-0-9]+ -> (.+)$")) { // Post
            broadcast(msg);

            String[] userMessage = msg.split("->");

            String user = userMessage[0].trim();
            String message = userMessage[1].trim();

            repository.savePost(user, message);

          } else if (msg.matches("^[A-Za-z0-9]+$")) { // Read

            Optional<User> read = repository.getUser(msg.trim());

            if (read.isPresent()) {

              output.println(
                  read.map(
                      user ->
                          user.posts().stream()
                              .sorted((p1, p2) -> p2.publishDate().compareTo(p1.publishDate()))
                              .map(Object::toString)
                              .collect(Collectors.joining("\n"))));
            } else {
              output.println("User not found" + msg);
            }

          } else if (msg.matches("^[A-Za-z-0-9]+ follow [A-Za-z-0-9]+$")) { // Follow

            String[] splittedInput = msg.split("follow");

            String user = splittedInput[0].trim();
            String follow = splittedInput[1].trim();

            if (repository.getUser(user).isEmpty() || repository.getUser(follow).isEmpty()) {
              output.println("User not found" + msg);
            } else {
              User follower = repository.getUser(user).get();

              follower.addToFollowing(repository.getUser(follow).get());

              repository.updateUser(user, follower);
            }

          } else if (msg.matches("^[A-Za-z-0-9]+ wall$")) { // Wall

            Optional<User> aUser = repository.getUser(msg.split("wall")[0].trim());

            if (aUser.isPresent()) {
              User user = aUser.get();

              ArrayList<Post> posts = new ArrayList<>(user.posts());

              user.following()
                  .forEach(
                      u -> {
                        posts.addAll(u.posts());
                      });

              output.println(
                  posts.stream()
                      .sorted((p1, p2) -> p2.publishDate().compareTo(p1.publishDate()))
                      .map(Object::toString)
                      .collect(Collectors.joining("\n")));
            } else {
              output.println("User not found: " + msg.split("wall")[0]);
            }

          } else if (msg.matches("^exit$")) {
            shoutDown();
          } else {
            broadcast("Command not recognized: " + msg);
          }
        }

      } catch (Exception e) {
        System.out.println("Error occured: " + Arrays.toString(e.getStackTrace()));
      }
    }

    private void broadcast(String outputString) {
      for (ServerThread sT : threadList) {
        sT.output.println(outputString);
      }
    }
  }

  private void shoutDown() {
    try {
      serversocket.close();
    } catch (IOException e) {

    }
  }
}
