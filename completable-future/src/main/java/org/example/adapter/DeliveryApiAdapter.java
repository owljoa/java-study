package org.example.adapter;

import org.example.util.LogUtil;
import org.example.util.ThreadUtil;

public class DeliveryApiAdapter {

  public static long cancelDelivery(long deliveryId) {
    LogUtil.printWithThreadName("배송취소 요청 발송");
    ThreadUtil.sleep(1500);
    LogUtil.printWithThreadName("배송취소 응답 수신");
    return deliveryId;
  }

}
