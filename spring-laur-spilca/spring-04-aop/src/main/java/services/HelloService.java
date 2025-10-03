package services;


import org.springframework.stereotype.Service;


@Service
public class HelloService {

    public void hello(String name) {
        System.out.println("Hello, " + name + "!");
    }

    public void boo() {
        System.out.println("This will throw an exception");
        throw new RuntimeException("Booo!");
    }

    public String hello2(String name) {
        String message = "Hello " + name + "!";
        System.out.println(message);
        return message;
    }
}
