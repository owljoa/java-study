package adapter;

import model.User;
import util.ThreadUtil;

public class UserApiAdapter {

  // 외부 서비스에 유저정보 요청
  public static User requestUser(long userId) {
    System.out.printf("[%s] 유저정보 받아오기 시작%n", Thread.currentThread().getName());
    ThreadUtil.sleep(1500);
    System.out.printf("[%s] 유저정보 받아오기 완료%n", Thread.currentThread().getName());
    return new User(userId, "Taylor");
  }

}
