package com.nbp.bear.components.service;

import com.nbp.bear.components.constant.NbpConstant;
import com.nbp.bear.components.constant.NbpResponse;
import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.repository.NbpUserRepository;
import com.nbp.bear.components.response.NbpUserResponse;
import com.nbp.bear.components.response.NbpUtilResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NbpAuthService {

    @Autowired
    private NbpUserRepository nbpUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<Object> NbpGetPasswordUserService(String email) {
        try {
            NbpUser nbpUser = nbpUserRepository.findByEmail(email).get();
            String nbpPwdGenerator = RandomStringUtils.random(15, NbpConstant.NBP_RANDOM_CHARS);
            nbpUser.setPassword(bCryptPasswordEncoder.encode(nbpPwdGenerator));
            nbpUser.setTemporaryPassword(Boolean.FALSE);
            nbpUserRepository.save(nbpUser);
            return new ResponseEntity<Object>(new NbpUtilResponse(NbpResponse.NBP_USER_PASSWORD_GENERATED, nbpPwdGenerator), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> NbpResetPasswordUserService(String email,String password) {
        try {
            NbpUser nbpUser = nbpUserRepository.findByEmail(email).get();
            nbpUser.setPassword(bCryptPasswordEncoder.encode(password));
            nbpUserRepository.save(nbpUser);
            return new ResponseEntity<Object>(new NbpUtilResponse(NbpResponse.NBP_USER_PASSWORD_GENERATED, nbpUser), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> NbpUserRegisterService(NbpUser nbpUser) {
        Optional<NbpUser> userByUserName = nbpUserRepository.findByUserName(nbpUser.getUserName());

        if (!userByUserName.isPresent()) {
            Optional<NbpUser> userByEmail = nbpUserRepository.findByEmail(nbpUser.getEmail());
            if (!userByEmail.isPresent()) {
                nbpUserRepository.save(nbpUser);
                return new ResponseEntity<Object>(new NbpUserResponse(NbpResponse.NBP_USER_CREATED, ""), HttpStatus.CREATED);
            }
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_EMAIL_EXISTS, HttpStatus.FOUND);
        }
        return new ResponseEntity<Object>(NbpResponse.NBP_USER_USERNAME_EXISTS, HttpStatus.FOUND);
    }

    public ResponseEntity<Object> NbpChangePasswordService(int userId, String oldPassword, String newPassword) {
        try {
            NbpUser nbpUser = nbpUserRepository.findById(userId).get();
            if(bCryptPasswordEncoder.matches(oldPassword, nbpUser.getPassword())) {
                nbpUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
                nbpUser.setTemporaryPassword(Boolean.TRUE);
                nbpUserRepository.save(nbpUser);
                return new ResponseEntity<Object>(new NbpUtilResponse(NbpResponse.NBP_USER_PASSWORD_CHANGED, nbpUser), HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_PASSWORD_NOT_MATCH, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<Object>(NbpResponse.NBP_USER_ERROR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
