package com.nbp.bear.components.service;

import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.repository.NbpUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NbpUserDetailsService implements UserDetailsService {

    @Autowired
    private NbpUserRepository nbpUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NbpUser nbpUser = nbpUserRepository.findByUserName(username);
        return new User(nbpUser.getUserName(), nbpUser.getPassword(), new ArrayList<>());
    }
}
