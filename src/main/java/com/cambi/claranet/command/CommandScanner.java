package com.cambi.claranet.command;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class CommandScanner {
  Scanner scanner = new Scanner(System.in);

  public String getNextLine() {
   return scanner.nextLine();
  }
}
