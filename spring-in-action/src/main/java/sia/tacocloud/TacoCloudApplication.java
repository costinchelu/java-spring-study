package sia.tacocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sia.tacocloud.se_test.TestSE;

@SpringBootApplication
public class TacoCloudApplication {

   public static void main(String[] args) {
      SpringApplication.run(TacoCloudApplication.class, args);
      TestSE.test();
   }
}
