package com.dev.service;

import com.dev.model.MyUser;
import com.dev.model.Role;

import java.util.List;

public interface UserService {

    MyUser addUser(MyUser user);

    MyUser getUser(long userId);

    long getCountOfUserAvailable();

    void deleteUser(long userId);

    MyUser updateUser(MyUser user);

    MyUser getUserByUsername(String username);

    MyUser getUserByEmail(String email);

    List<MyUser> getAllUsers();

    List<MyUser> getUserByRole(Role role);

    List<MyUser> getUserByDepartment(String department);

}
