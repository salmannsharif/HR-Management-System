package com.dev.controller;

import com.dev.model.Salary;
import com.dev.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    private final SalaryService salaryService;

    @Autowired
    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping("/add")
    public Salary addSalary(@RequestParam long id, @RequestParam double amount) {
        return salaryService.addSalary(id, amount);
    }

    @PostMapping("/bulkAdd")
    public List<Salary> bulkAddSalaries(@RequestParam long[] ids, @RequestParam double amount) {
        return salaryService.bulkAddSalaries(ids, amount);
    }
}
