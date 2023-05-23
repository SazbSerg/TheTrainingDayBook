package com.strenght.program.services;

import com.strenght.program.entities.User;
import com.strenght.program.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

        @Override
                 public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                     User user = userRepo.findUserByEmail(email);
                     return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
        }

    //  @Override
            //  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //      return userRepo
                //              .findUserByEmail(email)
                //              .map(User::new)
                //              .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + email));
        //  }
}