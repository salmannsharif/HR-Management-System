package com.dev.service.impl;

import com.dev.exception.DepartmentAlreadyExist;
import com.dev.exception.DepartmentNotFoundException;
import com.dev.model.Department;
import com.dev.repository.DepartmentRepository;
import com.dev.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department addDepartment(Department department) {
        Optional<Department> existByName = departmentRepository.findByName(department.getName());
        if (existByName.isPresent()) {
            throw new DepartmentAlreadyExist("Department name already exists");
        } else {
            return departmentRepository.save(department);
        }
    }

    @Override
    public Department getDepartmentById(Long id) {

        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            return department.get();
        } else {
            throw new DepartmentNotFoundException("Department not found with id: " + id);
        }
    }

    @Override
    public Department getDepartmentByName(String name) {
        Optional<Department> department = departmentRepository.findByName(name);
        if (department.isPresent()) {
            return department.get();
        } else {
            throw new DepartmentNotFoundException("Department not found with name: " + name);
        }
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public void removeDepartmentById(long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            departmentRepository.deleteById(id);
        } else {
            throw new DepartmentNotFoundException("Department not found with id: " + id);
        }
    }
}
