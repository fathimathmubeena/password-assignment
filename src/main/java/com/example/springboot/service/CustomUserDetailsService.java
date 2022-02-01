package com.example.springboot.service;

import com.example.springboot.entity.CustomUserDetails;
import com.example.springboot.entity.UserTable;
import com.example.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<OldUser> user = userRepository.findByUsername(username);
//
//        user.orElseThrow(()->new UsernameNotFoundException("Not found: " + username));
//
//        return user.map((OldUser oldUser) -> new CustomUserDetails(oldUser)).get();

        Long newUsername = Long.parseLong(username);
        UserTable user = userRepository.findByUsername(newUsername);
        if (user == null) {
            throw new UsernameNotFoundException("User Not found");
        }
        return new CustomUserDetails(user);
    }


}
