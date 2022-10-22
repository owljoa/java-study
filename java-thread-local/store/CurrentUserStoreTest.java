package store;

import repository.UserRepository;
import util.ThreadUtil;

public class CurrentUserStoreTest {

  private final CurrentUserStore currentUserStore;

  public CurrentUserStoreTest(UserRepository userRepository) {
    currentUserStore = new CurrentUserStore(userRepository);
  }

  public void test() {
    Runnable userA = () -> {
      currentUserStore.changeCurrentUserId(1);
      currentUserStore.getUserName();
    };
    Runnable userB = () -> {
      currentUserStore.changeCurrentUserId(2);
      currentUserStore.getUserName();
    };
    Runnable userC = () -> {
      currentUserStore.changeCurrentUserId(3);
      currentUserStore.getUserName();
    };

    ThreadUtil.run(userA, userB, userC);
  }

}
