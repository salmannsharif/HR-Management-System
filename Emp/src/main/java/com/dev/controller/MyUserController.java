package com.dev.controller;

import com.dev.exception.RoleNotFoundException;
import com.dev.exception.UserAlreadyExistException;
import com.dev.exception.UserNotFoundException;
import com.dev.model.MyUser;
import com.dev.model.Role;
import com.dev.repository.RoleRepository;
import com.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class MyUserController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    @Autowired
    public MyUserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/addUser")
    public ResponseEntity<MyUser> addUser(@RequestBody MyUser user) {
        try {
            MyUser createdUser = userService.addUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getUser/{userId}")
    public ResponseEntity<MyUser> getUser(@PathVariable long userId) {
        try {
            MyUser user = userService.getUser(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RoleNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCountOfUserAvailable")
    public ResponseEntity<Long> getCountOfUserAvailable() {
        long count = userService.getCountOfUserAvailable();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RoleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/updateUser")
    public ResponseEntity<MyUser> updateUser(@RequestBody MyUser user) {
        try {
            MyUser updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getUserByUsername")
    public ResponseEntity<MyUser> getUserByUsername(@RequestParam String username) {
        try {
            MyUser user = userService.getUserByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getUserByEmail")
    public ResponseEntity<MyUser> getUserByEmail(@RequestParam String email) {
        try {
            MyUser user = userService.getUserByEmail(email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<MyUser>> getAllUsers() {
        List<MyUser> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUserByRole")
    public ResponseEntity<List<MyUser>> getUserByRole(@RequestParam String roleName) {
        try {
            Role role = roleRepository.findRoleByRoleName(roleName)
                    .orElseThrow(() -> new RoleNotFoundException("Role not found with name: " + roleName));

            List<MyUser> users = userService.getUserByRole(role);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (RoleNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getUserByDepartment")
    public ResponseEntity<List<MyUser>> getUserByDepartment(@RequestParam String department) {
        try {
            List<MyUser> users = userService.getUserByDepartment(department);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
