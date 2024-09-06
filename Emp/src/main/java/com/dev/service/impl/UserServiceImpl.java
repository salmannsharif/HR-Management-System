package com.dev.service.impl;

import com.dev.exception.DepartmentNotFoundException;
import com.dev.exception.RoleNotFoundException;
import com.dev.exception.UserAlreadyExistException;
import com.dev.exception.UserNotFoundException;
import com.dev.model.Department;
import com.dev.model.MyUser;
import com.dev.model.Role;
import com.dev.repository.DepartmentRepository;
import com.dev.repository.MyUserRepository;
import com.dev.repository.RoleRepository;
import com.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final MyUserRepository userRepository;

    private final RoleRepository roleRepository;

    private final DepartmentRepository departmentRepository;

    private final PasswordEncoder passwordEncoder;

    private static final String USER_NOT_FOUND = "USER NOT FOUND";

    @Autowired
    public UserServiceImpl(MyUserRepository userRepository, RoleRepository roleRepository, DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public MyUser addUser(MyUser user) {

        Optional<MyUser> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistException("User already exists with username: " + user.getUsername());
        }

        Optional<Role> role = roleRepository.findById(user.getRole().getId());
        if (role.isPresent()) {
            user.setRole(role.get());
        } else {
            throw new RoleNotFoundException("Role not found with id: " + user.getRole().getId());
        }

        Department department = departmentRepository.findById(user.getDepartment().getId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
        user.setDepartment(department);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }


    @Override
    public MyUser getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND +" "+ userId));
    }

    @Override
    public long getCountOfUserAvailable() {
        return userRepository.count();
    }

    @Override
    public void deleteUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(USER_NOT_FOUND +" "+ userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public MyUser updateUser(MyUser user) {
        Optional<MyUser> userOptional = userRepository.findById(user.getId());
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException(USER_NOT_FOUND+" "+user.getId());
        }

        MyUser existingUser = userOptional.get();

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }

        if (user.getDepartment() != null) {
            existingUser.setDepartment(user.getDepartment());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public MyUser getUserByUsername(String username) {
        Optional<MyUser> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND+" "+username);
        }
    }

    @Override
    public MyUser getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + " " + email));
    }

    @Override
    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<MyUser> getUserByRole(Role role) {
        return userRepository.findByRole(role);
    }


    @Override
    public List<MyUser> getUserByDepartment(String department) {
        Optional<Department> departmentOptional = departmentRepository.findByName(department);
        if (departmentOptional.isPresent()) {
            return userRepository.findByDepartment(departmentOptional.get());
        } else {
            throw new DepartmentNotFoundException("Department not found with name: " + department);
        }
    }
}
