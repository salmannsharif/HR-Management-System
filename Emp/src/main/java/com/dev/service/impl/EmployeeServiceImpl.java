package com.dev.service.impl;

import com.dev.exception.DepartmentNotFoundException;
import com.dev.exception.EmployeeAlreadyExistsException;
import com.dev.exception.EmployeeNotFoundException;
import com.dev.model.Department;
import com.dev.model.Employee;
import com.dev.repository.DepartmentRepository;
import com.dev.repository.EmployeeRepository;
import com.dev.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Employee addEmployee(Employee employee) {
        if (employeeRepository.findById(employee.getId()).isPresent()) {
            throw new EmployeeAlreadyExistsException("Employee with ID " + employee.getId() + " already exists");
        }

        if (employee.getDepartment() == null) {
            throw new DepartmentNotFoundException("Department must be provided for the employee");
        }

        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }


    @Override
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Could not find employee with id " + id));
    }

    @Override
    public Employee getEmployeeByName(String firstName, String lastName) {
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new EmployeeNotFoundException("Could not find employee with name " + firstName + "  " + lastName));
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Could not find employee with email " + email));
    }

    @Override
    public Employee getEmployeeByPhone(String phoneNumber) {
        return employeeRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EmployeeNotFoundException("Could not find employee with phone " + phoneNumber));
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee createOrUpdateEmployee(Employee employee) {
        Optional<Employee> user = employeeRepository.findById(employee.getId());
        if (user.isPresent()) {
            Employee newEmployee = user.get();
            newEmployee.setId(employee.getId());
            newEmployee.setFirstName(employee.getFirstName());
            newEmployee.setLastName(employee.getLastName());
            newEmployee.setEmail(employee.getEmail());
            newEmployee.setAddress(employee.getAddress());
            newEmployee.setPhoneNumber(employee.getPhoneNumber());
            newEmployee.setDepartment(employee.getDepartment());
            return employeeRepository.save(newEmployee);
        } else {
            return employeeRepository.save(employee);
        }
    }

    @Override
    public void deleteEmployeeById(long id) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new EmployeeNotFoundException("Employee with id " + id + " does not exist");
        }
    }

    @Override
    public long getCountOfAllEmployee() {
        return employeeRepository.count();
    }

    @Override
    public List<Employee> findEmployeeByDepartment(String departmentName) {
        Optional<Department> departmentByName = departmentRepository.findByName(departmentName);
        if (departmentByName.isPresent()) {
            Department department = departmentByName.get();
            return employeeRepository.findByDepartment(department);
        } else {
            throw new DepartmentNotFoundException("Department not found with name: " + departmentName);
        }
    }


}
