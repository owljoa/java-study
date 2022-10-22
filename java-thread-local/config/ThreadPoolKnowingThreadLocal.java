package config;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import store.UserStoreUsingThreadLocal;

public class ThreadPoolKnowingThreadLocal extends ThreadPoolExecutor {

  public ThreadPoolKnowingThreadLocal() {
    super(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
  }

  @Override
  protected void afterExecute(Runnable r, Throwable t) {
    // 쓰레드풀에 할당된 작업 수행 후 실행될 후처리작업
    // 여기서 ThreadLocal을 지워줘야 쓰레드 재활용 시 다른 작업에 영향을 미치지 않음
    UserStoreUsingThreadLocal.cleanThreadLocal();
  }
}
