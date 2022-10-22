package store;

import model.User;
import repository.UserRepository;
import util.ThreadUtil;

public class UserStoreUsingThreadLocal {

  private final UserRepository userRepository;

  private static final ThreadLocal<Long> userIdInThreadLocal = ThreadLocal.withInitial(() -> -1L);

  public UserStoreUsingThreadLocal(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public String getUserName() {
    // userId로 유저 정보 조회
    User user = userRepository.findUserById(userIdInThreadLocal.get());
    System.out.printf("[THREAD-%s][조회결과] 사용자아이디:%d, 조회결과 사용자 이름: %s%n", Thread.currentThread().getName(), userIdInThreadLocal.get(), user.getName());
    return user.getName();
  }

  public void changeCurrentUserId(long userId) {
    System.out.printf("[THREAD-%s][수정이전] 사용자아이디:%d%n", Thread.currentThread().getName(), userIdInThreadLocal.get());
    ThreadUtil.sleep(100);
    userIdInThreadLocal.set(userId);
    System.out.printf("[THREAD-%s][수정결과] 사용자아이디:%d%n", Thread.currentThread().getName(), userIdInThreadLocal.get());
  }

  public static void cleanThreadLocal() {
    System.out.printf("[cleanThreadLocal][Before] 사용자아이디: %d%n", userIdInThreadLocal.get());
    userIdInThreadLocal.remove();
    System.out.printf("[cleanThreadLocal][After] 사용자아이디: %d%n", userIdInThreadLocal.get());
  }
}
