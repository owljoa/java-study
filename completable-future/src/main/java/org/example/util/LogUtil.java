package org.example.util;

import java.time.LocalDateTime;

public class LogUtil {

  public static void printWithThreadName(String message) {
    System.out.printf("[%s][%s] %s%n", LocalDateTime.now(), Thread.currentThread().getName(), message);
  }
}
