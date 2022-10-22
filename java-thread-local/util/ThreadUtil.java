package util;

public class ThreadUtil {

  public static void run(Runnable... runnables) {
    for (int threadNumber = 0; threadNumber < runnables.length; threadNumber++) {
      Runnable runnable = runnables[threadNumber];
      Thread thread = new Thread(runnable);
      thread.setName(String.valueOf(threadNumber + 1));
      thread.start();
      sleep(threadNumber * 40);
    }
  }

  public static void sleep(int milliSec) {
    try {
      Thread.sleep(milliSec);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
