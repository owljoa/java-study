package org.example.adapter;

import org.example.util.LogUtil;
import org.example.util.ThreadUtil;

public class PaymentApiAdapter {

  public static long cancelPaymentFail(long paymentId) {
    LogUtil.printWithThreadName("결제취소 요청 발송");
    ThreadUtil.sleep(1000);
    throw new IllegalArgumentException("결제 오류 발생 - 사유: 블라블라");
  }

  public static long cancelPaymentSuccess(long paymentId) {
    LogUtil.printWithThreadName("결제취소 요청 발송");
    ThreadUtil.sleep(1800);
    LogUtil.printWithThreadName("결제취소 응답 수신");
    return paymentId;
  }

}
