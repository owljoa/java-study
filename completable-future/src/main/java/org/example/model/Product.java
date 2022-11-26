package org.example.model;

import org.example.util.LogUtil;
import org.example.util.ThreadUtil;

public class Product {

  private long productId;
  private long amount;

  public Product(long productId, long amount) {
    this.productId = productId;
    this.amount = amount;
  }

  public long getProductId() {
    return productId;
  }

  public long getAmount() {
    return amount;
  }

  public void increaseAmount(long amount) {
    LogUtil.printWithThreadName("재고수량 +1 시작");
    ThreadUtil.sleep(100);
    this.amount += amount;
    LogUtil.printWithThreadName("재고수량 +1 완료");
  }
}
