package com.nbp.bear.components.controller;

import com.nbp.bear.components.constant.NbpConstant;
import com.nbp.bear.components.constant.NbpResponse;
import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.request.NbpUserRequest;
import com.nbp.bear.components.response.NbpUserResponse;
import com.nbp.bear.components.service.NbpUserDetailsService;
import com.nbp.bear.components.service.NbpUserService;
import com.nbp.bear.components.util.NbpJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/api")
public class NbpAuthController {

    @Autowired
    private NbpJwtUtil nbpJwtUtil;

    @Autowired
    private NbpUserService nbpUserService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private NbpUserDetailsService nbpUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Object> NbpUserLogin(@RequestBody NbpUserRequest nbpUserRequest) throws Exception {
        try {
            final UserDetails nbpUser = nbpUserDetailsService.loadUserByUsername(nbpUserRequest.getUserName());
            if (nbpUser.isEnabled()) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(nbpUserRequest.getUserName(), nbpUserRequest.getPassword())
                );
                final String jwtToken = nbpJwtUtil.generateToken(nbpUser);
                return new ResponseEntity<Object>(new NbpUserResponse(NbpResponse.NBP_USER_LOGGED, jwtToken), HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(new NbpUserResponse(NbpResponse.NBP_USER_ERROR_DISABLE, ""), HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            return new ResponseEntity<Object>(new NbpUserResponse(NbpResponse.NBP_USER_ERROR_LOGIN, ""), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> NbpUserRegister(@RequestBody @Valid NbpUser nbpUser) {
        nbpUser.setRoles(NbpConstant.NBP_DEFAULT_ROLE);
        nbpUser.setPassword(bCryptPasswordEncoder.encode(nbpUser.getPassword()));
        return nbpUserService.NbpUserRegisterService(nbpUser);
    }
}
