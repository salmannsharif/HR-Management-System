package com.dev.security;

import com.dev.model.MyUser;
import com.dev.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    public final MyUserRepository myUserRepository;

    @Autowired
    public MyUserDetailService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> myUser = myUserRepository.findByUsername(username);
        if (myUser.isPresent()) {
            MyUser user = myUser.get();
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(getRoles(user))
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private String[] getRoles(MyUser myUser) {
        if (myUser.getRole() == null) {
            return new String[]{"USER"};
        } else {
            return myUser.getRole().getRoleName().split(",");
        }
    }

}
