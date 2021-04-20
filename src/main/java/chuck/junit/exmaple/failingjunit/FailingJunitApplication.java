package chuck.junit.exmaple.failingjunit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FailingJunitApplication {

  public static void main(String[] args) {
    SpringApplication.run(FailingJunitApplication.class, args);
  }

}
