package store;

import config.ThreadPoolKnowingThreadLocal;
import repository.UserRepository;
import util.ThreadUtil;

public class UserStoreUsingThreadLocalTest {

  private final UserStoreUsingThreadLocal userStore;

  public UserStoreUsingThreadLocalTest(UserRepository userRepository) {
    userStore = new UserStoreUsingThreadLocal(userRepository);
  }

  public void test() {
    Runnable userA = () -> {
      userStore.changeCurrentUserId(1);
      userStore.getUserName();
    };
    Runnable userB = () -> {
      userStore.changeCurrentUserId(2);
      userStore.getUserName();
    };
    Runnable userC = () -> {
      userStore.changeCurrentUserId(3);
      userStore.getUserName();
    };

    ThreadUtil.run(userA, userB, userC);
  }


  public void testWithThreadPool() {
    ThreadPoolKnowingThreadLocal threadPoolKnowingThreadLocal = new ThreadPoolKnowingThreadLocal();

    Runnable userA = () -> {
      userStore.changeCurrentUserId(1);
      userStore.getUserName();
    };
    Runnable userB = () -> {
      userStore.changeCurrentUserId(2);
      userStore.getUserName();
    };
    Runnable userC = () -> {
      userStore.changeCurrentUserId(3);
      userStore.getUserName();
    };

    threadPoolKnowingThreadLocal.execute(userA);
    threadPoolKnowingThreadLocal.execute(userB);
    threadPoolKnowingThreadLocal.execute(userC);

    threadPoolKnowingThreadLocal.shutdown();
  }
}
