package com.nbp.bear.components.controller;

import com.nbp.bear.components.constant.NbpConstant;
import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.request.NbpUserRequest;
import com.nbp.bear.components.response.NbpJwtResponse;
import com.nbp.bear.components.service.NbpUserDetailsService;
import com.nbp.bear.components.service.NbpUserService;
import com.nbp.bear.components.util.NbpJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class NbpUserController {

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

    @GetMapping("/welcome")
    public String Welcome() {
        return "Welcome to Nbp Server Components";
    }

    @GetMapping("/users")
    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<NbpUser> NbpLoadUsers() {
        return nbpUserService.NbpGetAllUsers();
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String testUserAccess() {
        return "user can only access this !";
    }

    @PostMapping("/login")
    public NbpJwtResponse NbpUserLogin(@RequestBody NbpUserRequest nbpUserRequest) throws Exception {
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

    @PostMapping("/register")
    public String NbpUserRegister (@RequestBody NbpUser nbpUser) {
        nbpUser.setRoles(NbpConstant.NBP_DEFAULT_ROLE);
        nbpUser.setPassword(bCryptPasswordEncoder.encode(nbpUser.getPassword()));
        return nbpUserService.NbpUserRegisterService(nbpUser);
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
