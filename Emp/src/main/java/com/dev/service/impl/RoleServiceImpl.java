package com.dev.service.impl;

import com.dev.exception.RoleNotFoundException;
import com.dev.model.Role;
import com.dev.repository.RoleRepository;
import com.dev.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new RoleNotFoundException("Role not found with id: " + roleId);
        }
    }

    @Override
    public void removeRoleById(long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            roleRepository.deleteById(roleId);
        } else {
            throw new RoleNotFoundException("Role not found with id: " + roleId);
        }
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
