package org.example.model;

import org.example.constant.DeliveryStatus;
import org.example.constant.OrderStatus;
import org.example.constant.PaymentStatus;
import org.example.util.LogUtil;
import org.example.util.ThreadUtil;

public class Order {

  private long orderId;
  private OrderStatus orderStatus;
  private long amount;
  private long totalPrice;

  private long productId;

  private long paymentId;
  private PaymentStatus paymentStatus;

  private long deliveryId;
  private DeliveryStatus deliveryStatus;

  public Order(long orderId, OrderStatus orderStatus, long amount, long totalPrice, long productId, long paymentId, PaymentStatus paymentStatus, long deliveryId, DeliveryStatus deliveryStatus) {
    this.orderId = orderId;
    this.orderStatus = orderStatus;
    this.amount = amount;
    this.totalPrice = totalPrice;
    this.productId = productId;
    this.paymentId = paymentId;
    this.paymentStatus = paymentStatus;
    this.deliveryId = deliveryId;
    this.deliveryStatus = deliveryStatus;
  }

  public long getOrderId() {
    return orderId;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public long getAmount() {
    return amount;
  }

  public long getTotalPrice() {
    return totalPrice;
  }

  public long getProductId() {
    return productId;
  }

  public long getPaymentId() {
    return paymentId;
  }

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

  public long getDeliveryId() {
    return deliveryId;
  }

  public DeliveryStatus getDeliveryStatus() {
    return deliveryStatus;
  }

  public void cancelPaymentSuccess() {
    LogUtil.printWithThreadName("결제취소 후 주문의 결제상태 변경 시작");
    ThreadUtil.sleep(300);
    paymentStatus = PaymentStatus.CANCEL;
    LogUtil.printWithThreadName("결제취소 후 주문의 결제상태 변경 완료");
  }

  public void cancelSuccess() {
    LogUtil.printWithThreadName("주문상태 취소로 변경 시작");
    ThreadUtil.sleep(200);
    orderStatus = OrderStatus.CANCEL;
    LogUtil.printWithThreadName("주문상태 취소로 변경 완료");
  }

  public void cancelDeliverySuccess() {
    LogUtil.printWithThreadName("배송취소 후 주문의 배송상태 변경 시작");
    ThreadUtil.sleep(300);
    deliveryStatus = DeliveryStatus.CANCEL;
    LogUtil.printWithThreadName("배송취소 후 주문의 배송상태 변경 완료");
  }
}
