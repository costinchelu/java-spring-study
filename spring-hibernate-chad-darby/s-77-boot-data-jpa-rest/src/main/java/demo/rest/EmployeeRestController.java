package demo.rest;

import demo.entity.Employee;
import demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;


    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee findEmployeeById(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);

        if (employee == null) {
            throw new RuntimeException("Employee id not found: " + employeeId);
        }

        return employee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        // in case we pass an id in JSON we will set it to 0 to force Hibernate to save a new item
        // instead of update
        employee.setId(0);
        employeeService.save(employee);
        return employee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return employee;
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployeeById(@PathVariable int employeeId) {
        Employee tempEmployee = employeeService.findById(employeeId);

        if (tempEmployee == null) {
            throw new RuntimeException("Employee with id " + employeeId + " is null");
        }

        employeeService.deleteById(employeeId);
        return "Employee with id " + employeeId + " was deleted.";
    }
}
