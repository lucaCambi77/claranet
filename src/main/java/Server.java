import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Server implements Runnable {

  private Socket client;
  private final ArrayList<ServerThread> threadList = new ArrayList<>();
  private final ExecutorService executorService = Executors.newCachedThreadPool();
  private ServerSocket serversocket;
  private final Repository repository = new Repository();
  private boolean running = true;

  @Override
  public void run() {

    try {
      serversocket = new ServerSocket(5000);

      while (running) {
        client = serversocket.accept();
        ServerThread serverThread = new ServerThread(client);
        threadList.add(serverThread);
        executorService.execute(serverThread);
      }
    } catch (Exception e) {
      shoutDown();
    }
  }

  private void shoutDown() {
    try {
      running = false;
      serversocket.close();
      threadList.forEach(ServerThread::shoutDown);
    } catch (IOException e) {

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
            postAction(msg);
          } else if (msg.matches("^[A-Za-z0-9]+$")) { // Read
            readAction(msg);
          } else if (msg.matches("^[A-Za-z-0-9]+ follow [A-Za-z-0-9]+$")) { // Follow
            followAction(msg);
          } else if (msg.matches("^[A-Za-z-0-9]+ wall$")) { // Wall
            wallAction(msg);
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

    private void shoutDown() {
      try {
        input.close();
        output.close();
        client.close();
      } catch (IOException e) {

      }
    }

    private void wallAction(String msg) {
      Optional<User> aUser = repository.getUser(msg.split("wall")[0].trim());

      if (aUser.isPresent()) {
        User user = aUser.get();

        ArrayList<Post> posts = new ArrayList<>(user.posts());

        user.following().forEach(u -> posts.addAll(u.posts()));

        output.println(
            posts.stream()
                .sorted((p1, p2) -> p2.publishDate().compareTo(p1.publishDate()))
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
      } else {
        output.println("User not found: " + msg.split("wall")[0]);
      }
    }

    private void followAction(String msg) {
      String[] userFollow = msg.split("follow");

      String user = userFollow[0].trim();
      String follow = userFollow[1].trim();

      if (repository.getUser(user).isEmpty() || repository.getUser(follow).isEmpty()) {
        output.println("User not found" + msg);
      } else {
        User follower = repository.getUser(user).get();

        follower.addToFollowing(repository.getUser(follow).get());

        repository.updateUser(user, follower);
      }
    }

    private void readAction(String msg) {
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
    }

    private void postAction(String msg) {
      broadcast(msg);

      String[] userMessage = msg.split("->");

      String user = userMessage[0].trim();
      String message = userMessage[1].trim();

      repository.savePost(user, message);
    }

    private void broadcast(String outputString) {
      for (ServerThread sT : threadList) {
        sT.output.println(outputString);
      }
    }
  }

  public record User(String name, List<Post> posts, Set<User> following) {

    public void addPost(String message) {
      posts.add(new Post(name, message, new Date()));
    }

    public void addToFollowing(User user) {
      following.add(user);
    }
  }

  public record Post(String userName, String message, Date publishDate) {

    @Override
    public String toString() {
      return userName
          + " -> "
          + String.format(
              "%s %s", message, PostTimeFormatter.formatTimeDifference(this.publishDate));
    }
  }

  public static class Repository {
    private final Map<String, User> userMap = new HashMap<>();

    public Optional<User> getUser(String userName) {
      return null == userMap.get(userName) ? Optional.empty() : Optional.of(userMap.get(userName));
    }

    public User createUser(String userName) {
      userMap.putIfAbsent(userName, new User(userName, new ArrayList<>(), new HashSet<>()));

      return getUser(userName).get();
    }

    public User updateUser(String userName, User user) {
      if (null == user || null == userMap.get(userName)) return null;

      return userMap.put(userName, user);
    }

    public String savePost(String user, String message) {

      User userObject = getUser(user).orElseGet(() -> createUser(user));

      userObject.addPost(message);

      updateUser(user, userObject);

      return message;
    }
  }

  public static class PostTimeFormatter {
    private static final long ONE_MINUTE = TimeUnit.MINUTES.toMillis(1);
    private static final long ONE_HOUR = TimeUnit.HOURS.toMillis(1);
    private static final long ONE_DAY = TimeUnit.DAYS.toMillis(1);

    public static String formatTimeDifference(Date creationDate) {
      long timeDifference = System.currentTimeMillis() - creationDate.getTime();
      long timeDiff;
      String timeUnit;
      if (timeDifference < ONE_MINUTE) {
        timeDiff = TimeUnit.MILLISECONDS.toSeconds(timeDifference);
        timeUnit = "second";
      } else if (timeDifference < ONE_HOUR) {
        timeDiff = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
        timeUnit = "minute";
      } else if (timeDifference < ONE_DAY) {
        timeDiff = TimeUnit.MILLISECONDS.toHours(timeDifference);
        timeUnit = "hour";
      } else {
        timeDiff = TimeUnit.MILLISECONDS.toDays(timeDifference);
        timeUnit = "day";
      }

      if (timeDiff > 1) {
        timeUnit = timeUnit + "s";
      }
      return String.format("(%d %s ago)", timeDiff, timeUnit);
    }
  }
}
