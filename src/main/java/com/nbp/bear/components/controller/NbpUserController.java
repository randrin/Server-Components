package com.nbp.bear.components.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class NbpUserController {

    @GetMapping("/welcome")
    public String Welcome() {
        return "Welcome to Nbp Server Componenets";
    }
}
