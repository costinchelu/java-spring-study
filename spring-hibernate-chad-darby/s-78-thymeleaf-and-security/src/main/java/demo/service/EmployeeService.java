package demo.service;

import demo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();
    Employee findById(int employeeId);
    void save(Employee employee);
    void deleteById(int employeeId);
    List<Employee> searchBy(String theName);
}
