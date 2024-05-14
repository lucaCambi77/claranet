package com.cambi.claranet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

  BufferedReader input;
  PrintWriter output;

  public static void main(String[] args) {
    Client client = new Client();
    client.run();
  }

  class InputHandler implements Runnable {

    @Override
    public void run() {

      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

      while (true) {
        String input;
        try {
          input = reader.readLine();
          output.println(input);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  @Override
  public void run() {
    try (Socket socket = new Socket("localhost", 5000)) {
      input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      output = new PrintWriter(socket.getOutputStream(), true);

      InputHandler runnable = new InputHandler();
      Thread thread = new Thread(runnable);
      thread.start();

      String message;
      while ((message = input.readLine()) != null) {
        System.out.println(message);
      }
    } catch (Exception e) {
      System.out.println("Exception occured in client main: " + e.getStackTrace());
    }
  }
}
