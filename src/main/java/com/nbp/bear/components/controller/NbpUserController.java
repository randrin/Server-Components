package com.nbp.bear.components.controller;

import com.nbp.bear.components.request.NbpUserRequest;
import com.nbp.bear.components.response.NbpJwtResponse;
import com.nbp.bear.components.service.NbpUserDetailsService;
import com.nbp.bear.components.util.NbpJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
public class NbpUserController {

    @Autowired
    private NbpJwtUtil nbpJwtUtil;

    @Autowired
    private NbpUserDetailsService nbpUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String Welcome() {
        return "Welcome to Nbp Server Components";
    }

    @PostMapping("/login")
    public NbpJwtResponse login(@RequestBody NbpUserRequest nbpUserRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(nbpUserRequest.getUserName(), nbpUserRequest.getPassword())
            );
            final UserDetails nbpUser = nbpUserDetailsService.loadUserByUsername(nbpUserRequest.getUserName());
            final String jwtToken = nbpJwtUtil.generateToken(nbpUser);
            return new NbpJwtResponse(jwtToken);
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }
    }
}
