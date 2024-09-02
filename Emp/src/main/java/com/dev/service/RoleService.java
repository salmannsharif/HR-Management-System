package com.dev.service;

import com.dev.model.Role;

import java.util.List;

public interface RoleService {

    Role addRole(Role role);

    Role getRole(long roleId);

    void removeRoleById(long roleId);

    List<Role> getAllRoles();

}
