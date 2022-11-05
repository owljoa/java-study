package service;

import model.User;
import util.ThreadUtil;

public class SomeService {

  public void somethingWithoutUser() {
    System.out.printf("[%s] 유저정보나 결제정보와 관련 없는 작업 시작%n", Thread.currentThread().getName());
    ThreadUtil.sleep(1000);
    System.out.printf("[%s] 유저정보나 결제정보와 관련 없는 작업 완료%n", Thread.currentThread().getName());
  }

  public void somethingWithUser(User user) {
    System.out.printf("[%s] 유저정보를 이용한 작업 시작%n", Thread.currentThread().getName());
    ThreadUtil.sleep(500);
    System.out.printf("[%s] 유저정보를 이용한 작업 완료%n", Thread.currentThread().getName());
  }

}
