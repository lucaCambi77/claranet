package com.cambi.claranet.command;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandScanner {
  Scanner scanner = new Scanner(System.in);

  public String getNextLine() {
   return scanner.nextLine();
  }
}
