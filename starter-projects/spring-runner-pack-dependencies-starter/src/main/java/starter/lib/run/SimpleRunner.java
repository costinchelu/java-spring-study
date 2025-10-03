package starter.lib.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Slf4j
public class SimpleRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Enter your name, or just press return to exit.");
                String response = scanner.nextLine();

                if (!response.isEmpty()) {
                    System.out.println("Hello " + response + "!");

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }

                } else {
                    System.out.println("Goodbye");
                    break;
                }
            }
        }
    }
}

