package com.nbp.bear.components.controller;

import com.nbp.bear.components.constant.NbpConstant;
import com.nbp.bear.components.constant.NbpEndPoint;
import com.nbp.bear.components.constant.NbpResponse;
import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.repository.NbpUserRepository;
import com.nbp.bear.components.request.NbpUserChangePasswordRequest;
import com.nbp.bear.components.request.NbpUserLoginRequest;
import com.nbp.bear.components.request.NbpUserPasswordRequest;
import com.nbp.bear.components.request.NbpUserRegisterRequest;
import com.nbp.bear.components.response.NbpUserResponse;
import com.nbp.bear.components.response.NbpUtilResponse;
import com.nbp.bear.components.service.NbpAuthService;
import com.nbp.bear.components.service.NbpUserDetailsService;
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
@RequestMapping(NbpEndPoint.NBP_URL_ROOT)
@CrossOrigin("*")
public class NbpAuthController {

    @Autowired
    private NbpJwtUtil nbpJwtUtil;

    @Autowired
    private NbpAuthService nbpAuthService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private NbpUserDetailsService nbpUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private NbpUserRepository nbpUserRepository;

    @PostMapping(NbpEndPoint.NBP_URL_LOGIN)
    public ResponseEntity<Object> NbpUserLogin(@RequestBody @Valid NbpUserLoginRequest nbpUserRequest) throws Exception {
        boolean nbpUserActive;
        String nbpUsername = "";
        try {
            if (nbpUserRequest.getUserName().contains("@")) {
                final NbpUser nbpUser = nbpUserRepository.findByEmail(nbpUserRequest.getUserName()).get();
                nbpUserActive = nbpUser.isActive();
                nbpUsername = nbpUser.getUserName();
            } else {
                final UserDetails nbpUser = nbpUserDetailsService.loadUserByUsername(nbpUserRequest.getUserName());
                nbpUserActive = nbpUser.isEnabled();
                nbpUsername = nbpUserRequest.getUserName();
            }

            if (nbpUserActive) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(nbpUsername, nbpUserRequest.getPassword())
                );
                final String jwtToken = nbpJwtUtil.generateToken(nbpUsername);
                return new ResponseEntity<Object>(new NbpUserResponse(NbpResponse.NBP_USER_LOGGED, jwtToken), HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_DISABLE, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_LOGIN, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(NbpEndPoint.NBP_URL_REGISTER)
    public ResponseEntity<Object> NbpUserRegister(@RequestBody @Valid NbpUserRegisterRequest nbpUserRequest) {
        NbpUser nbpUser = new NbpUser(0, NbpUtil.NbpGenerateUserId(), "Test FullName",
                "https://png.pngtree.com/png-clipart/20210129/ourmid/pngtree-graphic-default-avatar-png-image_2813121.jpg",
                nbpUserRequest.getUserName(), nbpUserRequest.getPassword(), nbpUserRequest.getEmail().toLowerCase(),
                false, NbpConstant.NBP_DEFAULT_ROLE, false, new Date(), new Date());
        nbpUser.setRoles(NbpConstant.NBP_DEFAULT_ROLE);
        nbpUser.setPassword(bCryptPasswordEncoder.encode(nbpUser.getPassword()));
        return nbpAuthService.NbpUserRegisterService(nbpUser);
    }

    @PutMapping(NbpEndPoint.NBP_URL_LOGOUT)
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

    @PostMapping("/user/resetPassword")
    public ResponseEntity<Object> NbpResetPasswordUser(@RequestBody @Valid NbpUserPasswordRequest nbpUserPasswordRequest) {
        return nbpAuthService.NbpResetPasswordUserService(nbpUserPasswordRequest.getEmail(), nbpUserPasswordRequest.getPassword());
    }

    @GetMapping("/user/forgottenPassword/{email}")
    public ResponseEntity<Object> NbpGetPasswordUser(@PathVariable String email) {
        return nbpAuthService.NbpGetPasswordUserService(email);
    }

    @PutMapping("/user/changePassword/{id}")
    public ResponseEntity<Object> NbpChangePasswordUser(@PathVariable int id, @RequestBody @Valid NbpUserChangePasswordRequest nbpRequest) {
        return nbpAuthService.NbpChangePasswordService(id, nbpRequest.getOldPassword(), nbpRequest.getNewPassword());
    }
}
