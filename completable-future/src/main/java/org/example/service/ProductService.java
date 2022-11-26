package org.example.service;

import org.example.model.Product;
import org.example.util.LogUtil;
import org.example.util.ThreadUtil;

public class ProductService {

  public Product findById(long productId) {
    LogUtil.printWithThreadName("상품 조회 시작");
    ThreadUtil.sleep(300);
    LogUtil.printWithThreadName("상품 조회 완료");
    return new Product(1, 1000);
  }

}
