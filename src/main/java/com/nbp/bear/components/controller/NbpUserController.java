package com.nbp.bear.components.controller;

import com.nbp.bear.components.constant.NbpConstant;
import com.nbp.bear.components.constant.NbpEndPoint;
import com.nbp.bear.components.model.NbpUser;
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
@RequestMapping(NbpEndPoint.NBP_URL_ROOT)
@CrossOrigin("*")
public class NbpUserController {

    @Autowired
    private NbpUserService nbpUserService;

    @GetMapping("/users")
    @Secured(NbpConstant.NBP_ROLE_ADMIN)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<NbpUser> NbpGetUsers() {
        return nbpUserService.NbpGetAllUsers();
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
    public ResponseEntity<Object> NbpUserByToken(@PathVariable String token) throws Exception {
        return nbpUserService.NbpUserByTokenService(token);
    }

    @PutMapping("/user/update/{userId}")
    public ResponseEntity<Object> NbpUpdateUser(@PathVariable String userId, @RequestBody @Valid NbpUserUpdateRequest nbpUserRequest) {
        return nbpUserService.NbpUserUpdateService(userId, nbpUserRequest);
    }

    @DeleteMapping("/user/delete/{userId}")
    @Secured(NbpConstant.NBP_ROLE_ADMIN)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> NbpDeleteUser(@PathVariable String userId) {
        return nbpUserService.NbpUserDeleteService(userId);
    }

    @PutMapping("/user/permission/{id}")
    @Secured(NbpConstant.NBP_ROLE_ADMIN)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> NbpActivateOrDisabledUser(@PathVariable int id) {
        return nbpUserService.NbpUserActivateOrDisabledService(id);
    }

    @GetMapping("/user/profile/{id}")
    public ResponseEntity<Object> NbpGetUserById(@PathVariable int id) {
        return nbpUserService.NbpGetUserByIdService(id);
    }
}

