# CompletableFuture 예제

비동기 로직 구현을 위해 사용하는 CompletableFuture 클래스에 대한 사용 예제입니다.

## 1. 실행

Main 함수를 실행하면 동작하고, 콘솔 로그로 내용을 확인합니다.

로그 패턴은 “[날짜 및 시간][작업 쓰레드 이름] 내용” 입니다.

## 2. 구성

주문취소 기능을 예시로 구현했습니다.
설명을 위해 작성했기 때문에 실제 주문취소 기능과는 많이 다를 수 있습니다.

배송, 결제 기능은 외부 API 기능이라고 가정했습니다.
각각의 기능에는 설명을 위해 해당 기능의 동작시간을 Thread sleep 메소드로 나타냈습니다.

주문취소 기능의 트랜잭션 처리가 되어있어 트랜잭션 내 예외발생 시 롤백을 가정했습니다.

## 3. 상세 시나리오

1. 특정 주문의 취소를 위해 주문취소 메소드 호출
2. 배송취소 요청
    - 배송취소 정상응답 수신 시 주문의 배송상태 "취소"로 변경
3. 결제취소 요청
    - 결제취소 정상응답 수신 시 주문의 결제상태 "취소"로 변경
    - 결제취소 실패 시
4. 상품 조회 및 재고수량 증가 처리
5. 주문상태 "취소"로 변경

## 4. 실행 결과

### 4.1. 성공

```
main start
[2022-11-26T15:24:00.274465][main] 상품 조회 시작
[2022-11-26T15:24:00.274479][ForkJoinPool.commonPool-worker-19] 배송취소 요청 발송
[2022-11-26T15:24:00.274476][ForkJoinPool.commonPool-worker-5] 결제취소 요청 발송
[2022-11-26T15:24:00.589865][main] 상품 조회 완료
[2022-11-26T15:24:00.594692][main] 재고수량 +1 시작
[2022-11-26T15:24:00.700534][main] 재고수량 +1 완료
[2022-11-26T15:24:00.701938][main] 주문상태 취소로 변경 시작
[2022-11-26T15:24:00.905890][main] 주문상태 취소로 변경 완료
[2022-11-26T15:24:01.789837][ForkJoinPool.commonPool-worker-19] 배송취소 응답 수신
[2022-11-26T15:24:01.795833][ForkJoinPool.commonPool-worker-19] 배송취소 후 주문의 배송상태 변경 시작
[2022-11-26T15:24:02.089840][ForkJoinPool.commonPool-worker-5] 결제취소 응답 수신
[2022-11-26T15:24:02.090971][ForkJoinPool.commonPool-worker-5] 결제취소 후 주문의 결제상태 변경 시작
[2022-11-26T15:24:02.098298][ForkJoinPool.commonPool-worker-19] 배송취소 후 주문의 배송상태 변경 완료
[2022-11-26T15:24:02.396201][ForkJoinPool.commonPool-worker-5] 결제취소 후 주문의 결제상태 변경 완료
[2022-11-26T15:24:02.396580][main] 주문취소 처리 완료
main finished
```

### 4.2. 실패

```
main start
[2022-11-26T15:43:13.851873][ForkJoinPool.commonPool-worker-5] 결제취소 요청 발송
[2022-11-26T15:43:13.851875][main] 상품 조회 시작
[2022-11-26T15:43:13.851874][ForkJoinPool.commonPool-worker-19] 배송취소 요청 발송
[2022-11-26T15:43:14.169488][main] 상품 조회 완료
[2022-11-26T15:43:14.171579][main] 재고수량 +1 시작
[2022-11-26T15:43:14.274219][main] 재고수량 +1 완료
[2022-11-26T15:43:14.274601][main] 주문상태 취소로 변경 시작
[2022-11-26T15:43:14.480530][main] 주문상태 취소로 변경 완료
[2022-11-26T15:43:14.888110][ForkJoinPool.commonPool-worker-5] 결제취소 실패 - java.lang.IllegalArgumentException: 결제 오류 발생 - 사유: 블라블라
[2022-11-26T15:43:15.371001][ForkJoinPool.commonPool-worker-19] 배송취소 응답 수신
[2022-11-26T15:43:15.372636][ForkJoinPool.commonPool-worker-19] 배송취소 후 주문의 배송상태 변경 시작
[2022-11-26T15:43:15.675965][ForkJoinPool.commonPool-worker-19] 배송취소 후 주문의 배송상태 변경 완료

...

2 actionable tasks: 2 executed
Exception in thread "main" java.lang.RuntimeException: 주문취소 전처리 실패
	at org.example.service.OrderService.cancel(OrderService.java:51)
	at org.example.Main.main(Main.java:13)
```