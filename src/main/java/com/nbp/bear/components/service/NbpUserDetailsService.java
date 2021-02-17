package com.nbp.bear.components.service;

import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.repository.NbpUserRepository;
import com.nbp.bear.components.util.NbpUserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NbpUserDetailsService implements UserDetailsService {

    @Autowired
    private NbpUserRepository nbpUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<NbpUser> nbpUser = nbpUserRepository.findByUserName(username);
        return nbpUser.map(NbpUserDetailsUtil::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"));
    }
}
