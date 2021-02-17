package com.nbp.bear.components.controller;

import com.nbp.bear.components.constant.NbpConstant;
import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.service.NbpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/api")
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
    public List<NbpUser> NbpLoadUsers() {
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
}
