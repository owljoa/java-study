import model.User;
import repository.UserRepository;
import store.CurrentUserStoreTest;
import store.UserStoreUsingThreadLocalTest;
import util.ThreadUtil;

public class Main {

  public static void main(String[] args) {
    System.out.println("main start");

    // 1번 user 이름 A, 2번 user 이름 B, 3번 user 이름 C로 초기화
    UserRepository userRepository = new UserRepository();
    userRepository.save(new User(1L, "A"));
    userRepository.save(new User(2L, "B"));
    userRepository.save(new User(3L, "C"));

    System.out.println("=========== 멤버 변수 이용 ===========");
    CurrentUserStoreTest currentUserStoreTest = new CurrentUserStoreTest(userRepository);
    currentUserStoreTest.test();

    ThreadUtil.sleep(2000);

    System.out.println("=========== ThreadLocal 이용 ===========");
    UserStoreUsingThreadLocalTest userStoreUsingThreadLocalTest = new UserStoreUsingThreadLocalTest(userRepository);
    userStoreUsingThreadLocalTest.test();

    ThreadUtil.sleep(2000);

    System.out.println("=========== ThreadPool 환경에서 ThreadLocal 이용 ===========");
    userStoreUsingThreadLocalTest.testWithThreadPool();

    ThreadUtil.sleep(2000);
    System.out.println("=======================================");
    System.out.println("main exit");
  }


}
