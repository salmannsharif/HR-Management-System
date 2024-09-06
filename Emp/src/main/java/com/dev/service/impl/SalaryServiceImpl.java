package com.dev.service.impl;

import com.dev.exception.EmployeeNotFoundException;
import com.dev.model.Employee;
import com.dev.model.Salary;
import com.dev.repository.EmployeeRepository;
import com.dev.repository.SalaryRepository;
import com.dev.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final EmployeeRepository employeeRepository;

    private final SalaryRepository salaryRepository;

    @Autowired
    public SalaryServiceImpl(EmployeeRepository employeeRepository, SalaryRepository salaryRepository) {
        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
    }

    @Override
    public Salary addSalary(long id, double amount) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        Salary salary = new Salary();
        salary.setAmount(amount);
        salary.setEmployee(employee);

        return salaryRepository.save(salary);
    }

    @Override
    public List<Salary> bulkAddSalaries(long[] ids, double amount) {
        List<Long> idList = Arrays.stream(ids).boxed().toList();
        List<Employee> employees = employeeRepository.findAllById(idList);

        List<Salary> salaries = employees.stream().map(employee -> {
            Salary salary = new Salary();
            salary.setAmount(amount);
            salary.setEmployee(employee);
            return salary;
        }).toList();

        return salaryRepository.saveAll(salaries);
    }
}
