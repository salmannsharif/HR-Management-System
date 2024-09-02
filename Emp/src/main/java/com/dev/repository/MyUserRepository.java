package com.dev.repository;

import com.dev.model.Department;
import com.dev.model.MyUser;
import com.dev.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findByUsername(String username);

    Optional<MyUser> findUserByEmail(String email);

    List<MyUser> findByRole(Role role);

    List<MyUser> findByDepartment(Department department);

}
