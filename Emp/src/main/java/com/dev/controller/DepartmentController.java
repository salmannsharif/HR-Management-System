package com.dev.controller;

import com.dev.exception.DepartmentNotFoundException;
import com.dev.model.Department;
import com.dev.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/addDepartment")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        try {
            Department createdDepartment = departmentService.addDepartment(department);
            return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
        } catch (DepartmentNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getDepartmentById/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        try {
            Department department = departmentService.getDepartmentById(id);
            return new ResponseEntity<>(department, HttpStatus.OK);
        } catch (DepartmentNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getDepartmentByName/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String name) {
        try {
            Department department = departmentService.getDepartmentByName(name);
            return new ResponseEntity<>(department, HttpStatus.OK);
        } catch (DepartmentNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllDepartments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @DeleteMapping("/removeDepartmentById/{id}")
    public ResponseEntity<Void> removeDepartmentById(@PathVariable long id) {
        try {
            departmentService.removeDepartmentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DepartmentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
