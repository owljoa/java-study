package store;

import model.User;
import repository.UserRepository;
import util.ThreadUtil;

public class CurrentUserStore {

  private final UserRepository userRepository;

  private Long userId;

  public CurrentUserStore(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public String getUserName() {
    // 현재 요청 userId로 유저 정보 조회
    User user = userRepository.findUserById(this.userId);

    System.out.printf("[Thread-%s][조회결과] 사용자아이디:%d, 조회결과 사용자 이름: %s%n", Thread.currentThread().getName(), this.userId, user.getName());
    return user.getName();
  }

  public void changeCurrentUserId(long userId) {
    System.out.printf("[Thread-%s][수정이전] 사용자아이디:%d%n", Thread.currentThread().getName(), this.userId);
    ThreadUtil.sleep(100);
    this.userId = userId;
    System.out.printf("[Thread-%s][수정결과] 사용자아이디:%d%n", Thread.currentThread().getName(), this.userId);
  }
}
