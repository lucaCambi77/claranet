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

    boolean isExitCommand = false;

    while (!isExitCommand) {
      System.out.println(PROMPT);
      String input = commandScanner.getNextLine();
      Command command = commandExecutor.execute(input);
      isExitCommand = command.isExitCommand();
    }
  }
}
