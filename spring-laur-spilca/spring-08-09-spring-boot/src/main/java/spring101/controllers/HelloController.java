package spring101.controllers;

import org.springframework.web.bind.annotation.*;
import spring101.dto.Person;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;


// RestController = Controller + ResponseBody
@RestController
public class HelloController {

    @RequestMapping(method = RequestMethod.GET, path = "/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return "Hello " + name;
    }

    @GetMapping("/hello2/{name}")
    public String hello2(@PathVariable("name") String name) {
        return "Hello " + name;
    }

    @PostMapping(path = "/goodbye")
    public String goodbye(@RequestBody Person person) {
        return "Goodbye, " + person.getName() + "!";
    }

    @GetMapping("/get-person")
    public Person getPerson() {
        Person p = new Person();
        p.setName("John");
        return p;
    }

    @GetMapping("/get-all-persons")
    public List<Person> getAllPersons() {
        Person[] persons = new Person[3];
        for (int i = 0; i < persons.length; i++) {
            Person p = new Person();
            p.setName("Person " + (i + 1));
            persons[i] = p;
        }
        return Arrays.asList(persons);
    }

    @GetMapping(path = "/status-test")
    public void statusTest(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
