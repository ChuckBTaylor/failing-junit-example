package chuck.junit.exmaple.failingjunit.utils;

import java.util.function.Supplier;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncUtils {

  @Async
  public void fireAndForget(final Supplier<Void> supplier) {
    supplier.get();
  }

}
