package com.example.travelapp.service;

import com.example.travelapp.entity.User;
import com.example.travelapp.exceptions.EmailNotFoundException;
import com.example.travelapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws EmailNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null){
            throw new EmailNotFoundException("No user found with this email address");
        }

        return new UserDetailsImpl(user);
    }
}
