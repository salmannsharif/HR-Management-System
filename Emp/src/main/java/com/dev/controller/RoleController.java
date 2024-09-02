package com.dev.controller;

import com.dev.exception.RoleNotFoundException;
import com.dev.model.Role;
import com.dev.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/addRole")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        try {
            Role createdRole = roleService.addRole(role);
            return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getRole/{id}")
    public ResponseEntity<Role> getRole(@PathVariable long id) {
        try {
            Role role = roleService.getRole(id);
            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (RoleNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/removeRole/{id}")
    public ResponseEntity<Void> removeRoleById(@PathVariable long id) {
        try {
            roleService.removeRoleById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RoleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
