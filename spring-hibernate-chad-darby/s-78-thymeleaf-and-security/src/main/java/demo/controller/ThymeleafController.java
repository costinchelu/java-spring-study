package demo.controller;

import demo.entity.Employee;
import demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class ThymeleafController {

    // for in-memory version
    private List<Employee> employees;

    // for from-database version
    private EmployeeService employeeService;


    @Autowired
    public ThymeleafController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // simple
    @GetMapping("/test/hello")
    public String hello(Model model) {
        model.addAttribute("currentDate", new java.util.Date());
        return "helloworld";
    }

    // load values in memory for /test/employees endpoint
    @PostConstruct
    private void loadData() {
        Employee emp1 = new Employee(1, "Todd", "Anderson", "todda@mail.com");
        Employee emp2 = new Employee(2, "John", "Smith", "johns@mail.com");
        Employee emp3 = new Employee(3, "Mat", "Ratchet", "matr@mail.com");
        Employee emp4 = new Employee(4, "Mary", "Rob", "maryr@mail.com");

        this.employees = new ArrayList<>();
        this.employees.add(emp1);
        this.employees.add(emp2);
        this.employees.add(emp3);
        this.employees.add(emp4);
    }

    // in memory version
    @GetMapping("/test/employees")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employees);
        return "employees/list-employees";
    }

    // in db version
    @GetMapping("/employees")
    public String selectAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list-employees";
    }

    @GetMapping("/employees/showFormForAdd")
    public String showFormForAdd(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employees/employee-form";
    }

    @PostMapping("/employees/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/employees/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int employeeId, Model model) {
        Employee employee = employeeService.findById(employeeId);
        model.addAttribute(employee);
        return "employees/employee-form";
    }

    @GetMapping("/employees/delete")
    public String deleteEmployee(@RequestParam("employeeId") int employeeId) {
        employeeService.deleteById(employeeId);
        return "redirect:/employees";
    }

    @GetMapping("/employees/search")
    public String delete(@RequestParam("employeeName") String theName,
                         Model model) {

        // delete the employee
        List<Employee> employees = employeeService.searchBy(theName);

        // add to the spring model
        model.addAttribute("employees", employees);

        // send to /employees/list
        return "/employees/list-employees";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/employees";
    }
}
