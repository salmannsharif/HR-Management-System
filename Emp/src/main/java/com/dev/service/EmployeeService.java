package com.dev.service;

import com.dev.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    Employee getEmployeeById(long id);

    Employee getEmployeeByName(String firstName, String lastName);

    Employee getEmployeeByEmail(String email);

    Employee getEmployeeByPhone(String phoneNumber);

    List<Employee> getAllEmployee();

    Employee createOrUpdateEmployee(Employee employee);

    void deleteEmployeeById(long id);

    long getCountOfAllEmployee();

    List<Employee> findEmployeeByDepartment(String departmentName);

}
