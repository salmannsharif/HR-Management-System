package com.dev.controller;

import com.dev.exception.DepartmentNotFoundException;
import com.dev.exception.EmployeeAlreadyExistsException;
import com.dev.exception.EmployeeNotFoundException;
import com.dev.exception.RoleNotFoundException;
import com.dev.model.Employee;
import com.dev.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        try {
            Employee createdEmployee = employeeService.addEmployee(employee);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (EmployeeAlreadyExistsException | RoleNotFoundException | DepartmentNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT); // Conflict if employee already exists or required fields are missing
        }
    }

    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getEmployeeByName")
    public ResponseEntity<Employee> getEmployeeByName(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            Employee employee = employeeService.getEmployeeByName(firstName, lastName);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getEmployeeByEmail")
    public ResponseEntity<Employee> getEmployeeByEmail(@RequestParam String email) {
        try {
            Employee employee = employeeService.getEmployeeByEmail(email);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getEmployeeByPhone")
    public ResponseEntity<Employee> getEmployeeByPhone(@RequestParam String phoneNumber) {
        try {
            Employee employee = employeeService.getEmployeeByPhone(phoneNumber);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployee();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/createOrUpdateEmployee")
    public ResponseEntity<Employee> createOrUpdateEmployee(@RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.createOrUpdateEmployee(employee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (EmployeeNotFoundException | RoleNotFoundException | DepartmentNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deleteEmployeeById/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable long id) {
        try {
            employeeService.deleteEmployeeById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCountOfAllEmployees")
    public ResponseEntity<Long> getCountOfAllEmployees() {
        long count = employeeService.getCountOfAllEmployee();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/findEmployeeByDepartment/{departmentName}")
    public ResponseEntity<List<Employee>> findEmployeeByDepartment(@PathVariable String departmentName) {
        try {
            List<Employee> employees = employeeService.findEmployeeByDepartment(departmentName);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (DepartmentNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
