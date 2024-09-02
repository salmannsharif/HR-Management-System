package com.dev.repository;

import com.dev.model.Department;
import com.dev.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartment(Department department);

    Optional<Employee> findByPhoneNumber(String phoneNumber);

}
