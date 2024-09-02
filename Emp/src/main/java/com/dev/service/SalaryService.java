package com.dev.service;


import com.dev.model.Salary;

import java.util.List;

public interface SalaryService {

    Salary addSalary(long id, double amount);

    List<Salary> bulkAddSalaries(long[] ids, double amount);

}
