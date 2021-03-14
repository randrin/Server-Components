package com.nbp.bear.components.controller;

import com.nbp.bear.components.constant.NbpConstant;
import com.nbp.bear.components.constant.NbpResponse;
import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.repository.NbpUserRepository;
import com.nbp.bear.components.request.NbpUserLoginRequest;
import com.nbp.bear.components.request.NbpUserRegisterRequest;
import com.nbp.bear.components.response.NbpUserResponse;
import com.nbp.bear.components.response.NbpUtilResponse;
import com.nbp.bear.components.service.NbpUserDetailsService;
import com.nbp.bear.components.service.NbpUserService;
import com.nbp.bear.components.util.NbpJwtUtil;
import com.nbp.bear.components.util.NbpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin("*")
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

    @Autowired
    private NbpUserRepository nbpUserRepository;

    @PostMapping("/login")
    public ResponseEntity<Object> NbpUserLogin(@RequestBody @Valid NbpUserLoginRequest nbpUserRequest) throws Exception {
        try {
            final UserDetails nbpUser = nbpUserDetailsService.loadUserByUsername(nbpUserRequest.getUserName());
            if (nbpUser.isEnabled()) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(nbpUserRequest.getUserName(), nbpUserRequest.getPassword())
                );
                final String jwtToken = nbpJwtUtil.generateToken(nbpUser);
                return new ResponseEntity<Object>(new NbpUserResponse(NbpResponse.NBP_USER_LOGGED, jwtToken), HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_DISABLE, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_LOGIN, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> NbpUserRegister(@RequestBody @Valid NbpUserRegisterRequest nbpUserRequest) {
        NbpUser nbpUser = new NbpUser(0, NbpUtil.NbpGenerateUserId(), "Test FullName",
                "https://png.pngtree.com/png-clipart/20210129/ourmid/pngtree-graphic-default-avatar-png-image_2813121.jpg",
                nbpUserRequest.getUserName(), nbpUserRequest.getPassword(), nbpUserRequest.getEmail().toLowerCase(),
                false, NbpConstant.NBP_DEFAULT_ROLE, false, new Date(), new Date());
        nbpUser.setRoles(NbpConstant.NBP_DEFAULT_ROLE);
        nbpUser.setPassword(bCryptPasswordEncoder.encode(nbpUser.getPassword()));
        return nbpUserService.NbpUserRegisterService(nbpUser);
    }

    @PutMapping("/logout")
    public ResponseEntity<Object> NbpUserLogout(@RequestBody @Valid NbpUserLoginRequest nbpUserRequest) {
        try {
            NbpUser nbpUser = nbpUserRepository.findByUserName(nbpUserRequest.getUserName()).get();
            // Update some Business Logic before saving the
            nbpUser.setLastConnexion(new Date());
            nbpUserRepository.save(nbpUser);
            return new ResponseEntity<Object>(new NbpUtilResponse(NbpResponse.NBP_USER_LOGOUT, nbpUser), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
