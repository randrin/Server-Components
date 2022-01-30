package com.nbp.bear.components.service;

import com.nbp.bear.components.constant.NbpConstant;
import com.nbp.bear.components.constant.NbpResponse;
import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.repository.NbpUserRepository;
import com.nbp.bear.components.request.NbpUserUpdateRequest;
import com.nbp.bear.components.response.NbpUserResponse;
import com.nbp.bear.components.util.NbpJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NbpUserService {

    @Autowired
    private NbpUserRepository nbpUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private NbpJwtUtil nbpJwtUtil;

    public List<NbpUser> NbpGetAllUsers() {
        return nbpUserRepository.findAll();
    }

    public List<String> NbpGetRolesByLoggedInUser(Principal principal) {
        String roles = NbpGetLoggedInUser(principal).getRoles();
        List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if (assignRoles.contains(NbpConstant.NBP_ROLE_ADMIN)) {
            return Arrays.stream(NbpConstant.NBP_ADMIN_ACCESS).collect(Collectors.toList());
        }
        if (assignRoles.contains(NbpConstant.NBP_ROLE_MODERATOR)) {
            return Arrays.stream(NbpConstant.NBP_MODERATOR_ACCESS).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public NbpUser NbpGetLoggedInUser(Principal principal) {
        return nbpUserRepository.findByUserName(principal.getName()).get();
    }

    public String NbpGetAccessService(int userId, String userRole, Principal principal) {
        NbpUser nbpUser = nbpUserRepository.findById(userId).get();
        List<String> activeRoles = NbpGetRolesByLoggedInUser(principal);
        String newRole = "";
        if (activeRoles.contains(userRole)) {
            newRole = nbpUser.getRoles() + "," + userRole;
            nbpUser.setRoles(newRole);
        }
        nbpUserRepository.save(nbpUser);
        return "Hi " + nbpUser.getUserName() + " New Role assign to you by " + principal.getName();
    }

    public ResponseEntity<Object> NbpUserByTokenService(String token) {
        try {
            String username = nbpJwtUtil.extractUsername(token);
            Optional<NbpUser> nbpUser = nbpUserRepository.findByUserName(username);
            if (nbpUser.isPresent()) {
                return new ResponseEntity<Object>(nbpUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> NbpUserDeleteService(String userId) {
        try {
            if(nbpUserRepository.existsById(Integer.parseInt(userId))) {
                NbpUser nbpUser = nbpUserRepository.findByUserId(userId).get();
                nbpUserRepository.delete(nbpUser);
                return new ResponseEntity<Object>(nbpUser, HttpStatus.OK);
            }
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> NbpUserUpdateService(String userId, NbpUserUpdateRequest nbpUserRequest) {
        try {
            NbpUser nbpUser = nbpUserRepository.findByUserId(userId).get();
            nbpUser.setEmail(nbpUserRequest.getEmail());
            nbpUser.setUserName(nbpUserRequest.getUserName());
            // Add some fields later
            nbpUserRepository.save(nbpUser);
            String jwtToken = nbpJwtUtil.createToken(new HashMap<>(), nbpUser.getUserName());
            return new ResponseEntity<Object>(new NbpUserResponse(NbpResponse.NBP_USER_UPDATED, jwtToken), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> NbpUserActivateOrDisabledService(int userId) {
        try {
            NbpUser nbpUser = nbpUserRepository.findById(userId).get();
            nbpUser.setActive(nbpUser.isActive() ? Boolean.FALSE : Boolean.TRUE);
            nbpUserRepository.save(nbpUser);
            return new ResponseEntity<Object>(nbpUser, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> NbpGetUserByIdService(int userId) {
        try {
            NbpUser nbpUser = nbpUserRepository.findById(userId).get();
            return new ResponseEntity<Object>(nbpUser, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
