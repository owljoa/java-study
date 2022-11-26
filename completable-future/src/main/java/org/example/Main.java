package org.example;

import org.example.service.OrderService;
import org.example.service.ProductService;

public class Main {

  public static void main(String[] args) {
    System.out.println("main start");
    ProductService productService = new ProductService();
    OrderService orderService = new OrderService(productService);

    orderService.cancel(1);
    System.out.println("main finished");
  }
}