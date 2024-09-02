package com.dev.service;

import com.dev.model.Department;

import java.util.List;

public interface DepartmentService {

    Department addDepartment(Department department);

    Department getDepartmentById(Long id);

    Department getDepartmentByName(String name);

    List<Department> getAllDepartments();

    void removeDepartmentById(long id);

}
