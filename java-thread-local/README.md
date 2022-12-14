# ThreadLocal 사용 예시

## 1. 실행

- Main 클래스 내의 메인함수 실행하시면 결과를 콘솔에서 확인하실 수 있습니다.

## 2. 테스트 구성
- 테스트는 인스턴스변수, 쓰레드로컬변수, 쓰레드풀 환경에서의 쓰레드로컬변수 사용의 3가지 케이스로 구성되어있습니다
- 쓰레드별 테스트용 유저는 3명이고 각 유저 정보는 아래와 같습니다.(쓰레드풀 테스트는 상황 가정을 위해 3번 쓰레드가 없이 2개의 쓰레드로 진행됩니다)
  - 1번 쓰레드 - 1번 유저 - 이름: A
  - 2번 쓰레드 - 2번 유저 - 이름: B
  - 3번 쓰레드 - 3번 유저 - 이름: C
- 각 케이스별 테스트 로직은 아래와 같이 동일합니다. 
  1. 현재 유저아이디 출력
  2. 쓰레드별 지정된 현재 유저아이디를 인스턴스 변수 혹은 쓰레드로컬에 저장/수정
     - 테스트를 위해 100ms sleep
  3. 현재 유저아이디로 유저 정보 조회
     - 테스트를 위해 80ms sleep
  4. 조회한 유저 이름 출력
  5. 1~4의 과정을 3개의 쓰레드가 40ms의 시간차를 두고 수행

### 2.1. 인스턴스 변수 이용
- 클래스의 인스턴스 변수를 현재 쓰레드 작업을 요청한 유저 아이디 저장용으로 이용한 결과를 나타냅니다.
- 인스턴스 변수는 여러 쓰레드에서 동시 접근이 가능하기 때문에 유저아이디 변경과 유저 조회 시점에 따라 적합하지 않은 사용자아이디-사용자이름 쌍이 출력됨을 확인할 수 있습니다. 

### 2.2. ThreadLocal 이용
- ThreadLocal 변수를 현재 쓰레드 작업을 요청한 유저 아이디 저장용으로 이용한 결과를 나타냅니다.
- 인스턴스 변수 이용 케이스와는 다르게 각 쓰레드별로 사용자아이디와 사용자이름이 알맞게 출력됨을 확인할 수 있습니다.

### 2.3. ThreadPool 환경에서 ThreadLocal 이용
- 쓰레드풀(ThreadPool)은 쓰레드를 재활용합니다.
- ThreadLocal에 특정값을 저장하고 작업을 수행한 후 쓰레드풀에 쓰레드를 반납하기 이전에 ThreadLocal에 저장된 값을 제거하지 않으면, 반납 이후 해당 쓰레드를 다시 얻어올 때 저장된 값이 유지되어 있을 수 있습니다.
  - 이 부분은 간단히 확인하기위해 쓰레드풀의 기본 및 최대 쓰레드 갯수를 2개로 설정해두었습니다.
  - ThreadLocal에 저장된 값이 제거되지 않은 것은 ThreadPoolKnowingThreadLocal 클래스의 afterExecute 메소드 내용을 주석처리하시고 확인하실 수 있습니다.
    - 최대 쓰레드가 2개이기 때문에 3번째 쓰레드는 먼저 작업을 마친 쓰레드를 재사용하게 되어 "현재 사용자아이디"로 이전에 사용된 값이 출력됩니다.