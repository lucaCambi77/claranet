package com.cambi.claranet;

import com.cambi.claranet.command.CommandExecutor;
import com.cambi.claranet.command.CommandScanner;
import com.cambi.claranet.command.model.abstr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ClaranetApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(ClaranetApplication.class, args);
  }

  final CommandExecutor commandExecutor;
  final CommandScanner commandScanner;
  private static String PROMPT = "| ";

  @Override
  public void run(String... args) {
    initialBanner();

    boolean isExitCommand = false;

    while (!isExitCommand) {
      System.out.print(PROMPT);
      String input = commandScanner.getNextLine();
      Command command = commandExecutor.execute(input);
      isExitCommand = command.isExitCommand();
    }
  }

  private void initialBanner() {
    System.out.println(" ");
    System.out.println(" ");
    System.out.println("Welcome to Claranet Social Network demo!");
    System.out.println(" ");
    System.out.println("MyUserName -> Hello World!");
    System.out.println( "... add a post to your time line");
    System.out.println("MyUserName follow AnotherUser");
    System.out.println("... to follow another user");
    System.out.println("MyUserName");
    System.out.println("... to see your posts");
    System.out.println("MyUserName wall");
    System.out.println("... to see yours and your following users posts ");
    System.out.println(" ");
    System.out.println("For the purpose of this demo only alphanumeric users names are allowed");
    System.out.println(" ");
    System.out.println("Type exit to end your session");
    System.out.println(" ");
    System.out.println("Enjoy!");
    System.out.println(" ");
  }
}
