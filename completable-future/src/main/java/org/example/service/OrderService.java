package org.example.service;

import java.util.concurrent.CompletableFuture;
import org.example.adapter.DeliveryApiAdapter;
import org.example.adapter.PaymentApiAdapter;
import org.example.constant.DeliveryStatus;
import org.example.constant.OrderStatus;
import org.example.constant.PaymentStatus;
import org.example.model.Order;
import org.example.model.Product;
import org.example.util.LogUtil;

public class OrderService {

  private final ProductService productService;

  public OrderService(ProductService productService) {
    this.productService = productService;
  }

  // 주문취소 트랜잭션 메소드
  public void cancel(long orderId) {
    // 주문 조회
    final Order order = findById(orderId);

    // 배송취소
    CompletableFuture<Long> canceledDeliveryId = cancelDeliveryAsync(order);
    // 결제취소
    CompletableFuture<Long> canceledPaymentId = cancelPaymentAsync(order);
    // 배송취소, 결제취소 결과 조합
    CompletableFuture<Boolean> preprocessToCancelOrder = canceledDeliveryId.thenCombine(
        canceledPaymentId,
        (cancelDeliverResult, cancelPaymentResult) -> {
          if (cancelDeliverResult == null || cancelPaymentResult == null) {
            return false;
          }
          return cancelDeliverResult == order.getDeliveryId() && cancelPaymentResult == order.getPaymentId();
        }
    );

    // 재고수량 복원
    final Product product = productService.findById(order.getProductId());
    product.increaseAmount(order.getAmount());

    // 주문상태 취소 처리
    order.cancelSuccess();

    // 배송취소, 결제취소 결과 대기
    if (!preprocessToCancelOrder.join()) {
      // 배송취소 혹은 결제취소 실패 시 트랜잭션 롤백
      throw new RuntimeException("주문취소 전처리 실패");
    }
    LogUtil.printWithThreadName("주문취소 처리 완료");
  }

  private CompletableFuture<Long> cancelDeliveryAsync(Order order) {
    return CompletableFuture.supplyAsync(() -> DeliveryApiAdapter.cancelDelivery(order.getDeliveryId()))
        .exceptionally(throwable -> {
          LogUtil.printWithThreadName("배송취소 실패 - " + throwable.getMessage());
          return null;
        }).thenApply(result -> {
          if (result != null && result == order.getDeliveryId()) {
            // 주문의 배소상태 "취소"로 변경
            order.cancelDeliverySuccess();
          }
          return result;
        });
  }

  private CompletableFuture<Long> cancelPaymentAsync(Order order) {
    return CompletableFuture.supplyAsync(() ->
            // 결제취소 처리 성공 케이스
//            PaymentApiAdapter.cancelPaymentSuccess(order.getPaymentId())
            // 결제취소 처리 실패 케이스
            PaymentApiAdapter.cancelPaymentFail(order.getPaymentId())
    ).exceptionally(throwable -> {
      LogUtil.printWithThreadName("결제취소 실패 - " + throwable.getCause());
      return null;
    }).thenApply(result -> {
      if (result != null && result == order.getPaymentId()) {
        // 주문의 결제상태 "취소"로 변경
        order.cancelPaymentSuccess();
      }
      return result;
    });
  }

  public Order findById(long orderId) {
    return new Order(1, OrderStatus.IN_PROGRESS,
        10, 100000, 1,
        1, PaymentStatus.COMPLETE,
        1, DeliveryStatus.READY);
  }

}
