package util;

public class ThreadUtil {

  public static void sleep(int milliSeconds) {
    try {
      Thread.sleep(milliSeconds);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
