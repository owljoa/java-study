import adapter.UserApiAdapter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import model.User;
import service.SomeService;
import util.ThreadUtil;

public class Main {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    System.out.println("main start");
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    SomeService someService = new SomeService();

    // 외부 서비스에 유저정보 요청
    Future<User> user = executorService.submit(() -> UserApiAdapter.requestUser(1));

    // 유저정보 없이 할 수 있는 작업
    someService.somethingWithoutUser();

    // 유저정보를 얻어올 때 까지 대기
    while (!user.isDone()) {
      System.out.printf("[%s] 외부 서비스에서 유저정보 받아오는 중%n", Thread.currentThread().getName());
      ThreadUtil.sleep(300);
    }

    // 유저정보를 이용한 작업
    someService.somethingWithUser(user.get());

    System.out.println("main finished");
    executorService.shutdown();
  }

}
