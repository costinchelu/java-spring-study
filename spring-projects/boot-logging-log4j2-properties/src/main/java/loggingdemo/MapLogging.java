package loggingdemo;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
@Log4j2
public class MapLogging implements CommandLineRunner {

    @Override
    public void run(String... args) {
        Map<String, String> hm1 = new HashMap<>();
        hm1.put("qa", "http://qa.com");
        hm1.put("uat", "http://uat.com");
        hm1.put("preprod", "http://preprod.com");
        hm1.put("prod", "http://prod.com");

        log.info("---------");
        log.info(hm1);

        log.info("---------");
        hm1.entrySet()
                .stream()
                .iterator()
                .forEachRemaining(entry -> log.info("K1ey: " + entry.getKey() + ". Value: " + entry.getValue()));

        log.info("---------");
        hm1.forEach((key1, value) -> log.info("Key: " + key1 + ". Value: " + value));

        log.info("---------");
        for (String key : hm1.keySet()) {
            log.info("Key: " + key + ". Value: " + hm1.get(key));
        }

        log.info("---------");
        Iterator<String> iterator = hm1.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            log.info("Key: " + key + ". Value: " + hm1.get(key));
        }


        // log4j-api
        // log4j-core

    }
}
