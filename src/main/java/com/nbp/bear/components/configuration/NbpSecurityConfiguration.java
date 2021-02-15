package com.nbp.bear.components.configuration;

import com.nbp.bear.components.service.NbpUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class NbpSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private NbpUserDetailsService nbpUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(nbpUserDetailsService);
    }
}
