package com.nbp.bear.components.controller;

import com.nbp.bear.components.request.NbpUserRequest;
import com.nbp.bear.components.util.NbpJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
public class NbpUserController {

    @Autowired
    private NbpJwtUtil nbpJwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String Welcome() {
        return "Welcome to Nbp Server Componenets";
    }

    @PostMapping("/login")
    public String login(@RequestBody NbpUserRequest nbpUserRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(nbpUserRequest.getUserName(), nbpUserRequest.getPassword())
            );
            return nbpJwtUtil.generateToken(nbpUserRequest.getUserName());
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }
    }
}
