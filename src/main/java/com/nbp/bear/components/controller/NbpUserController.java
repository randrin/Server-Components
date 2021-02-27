package com.nbp.bear.components.controller;

import com.nbp.bear.components.constant.NbpConstant;
import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.request.NbpUserPasswordRequest;
import com.nbp.bear.components.request.NbpUserUpdateRequest;
import com.nbp.bear.components.service.NbpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin("*")
public class NbpUserController {

    @Autowired
    private NbpUserService nbpUserService;

    @GetMapping("/welcome")
    public String Welcome() {
        return "Welcome to Nbp Server Components";
    }

    @GetMapping("/users")
    @Secured(NbpConstant.NBP_ROLE_ADMIN)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<NbpUser> NbpGetUsers() {
        return nbpUserService.NbpGetAllUsers();
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String testUserAccess() {
        return "user can only access this !";
    }

    //If loggedin user is ADMIN -> ADMIN OR MODERATOR
    //If loggedin user is MODERATOR -> MODERATOR

    @GetMapping("/access/{userId}/{userRole}")
    //@Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String NbpGiveAccessToUser(@PathVariable int userId, @PathVariable String userRole, Principal principal) {
        return nbpUserService.NbpGetAccessService(userId, userRole, principal);
    }

    @GetMapping("/user/{token}")
    public ResponseEntity<Object> NbpGetUser(@PathVariable String token) throws Exception {
        return nbpUserService.NbpUserByTokenService(token);
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<Object> NbpUpdateUser(@PathVariable int id, @RequestBody @Valid NbpUserUpdateRequest nbpUserRequest) {
        return nbpUserService.NbpUserUpdateService(id, nbpUserRequest);
    }

    @DeleteMapping("/user/delete/{id}")
    @Secured(NbpConstant.NBP_ROLE_ADMIN)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> NbpDeleteUser(@PathVariable int id) {
        return nbpUserService.NbpUserDeleteService(id);
    }

    @PutMapping("/user/permission/{id}")
    @Secured(NbpConstant.NBP_ROLE_ADMIN)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> NbpActivateOrDisabledUser(@PathVariable int id) {
        return nbpUserService.NbpUserActivateOrDisabledService(id);
    }

    @PostMapping("/user/resetPassword")
    public ResponseEntity<Object> NbpResetPasswordUser(@RequestBody @Valid NbpUserPasswordRequest nbpUserPasswordRequest) {
        return nbpUserService.NbpResetPasswordUserService(nbpUserPasswordRequest.getEmail(), nbpUserPasswordRequest.getPassword());
    }

    @GetMapping("/user/forgottenPassword/{email}")
    public ResponseEntity<Object> NbpGetPasswordUser(@PathVariable String email) {
        return nbpUserService.NbpGetPasswordUserService(email);
    }
}

