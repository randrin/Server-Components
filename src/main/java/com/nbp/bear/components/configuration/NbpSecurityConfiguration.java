package com.nbp.bear.components.configuration;

import com.nbp.bear.components.constant.NbpEndPoint;
import com.nbp.bear.components.filter.NbpFilter;
import com.nbp.bear.components.service.NbpUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class NbpSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private NbpUserDetailsService nbpUserDetailsService;

    @Autowired
    private NbpFilter nbpFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(nbpUserDetailsService);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(NbpEndPoint.NBP_URL_ROOT + NbpEndPoint.NBP_URL_LOGIN,
                        NbpEndPoint.NBP_URL_ROOT + NbpEndPoint.NBP_URL_REGISTER,
                        NbpEndPoint.NBP_URL_ROOT + NbpEndPoint.NBP_URL_RESET_PASSWORD,
                        NbpEndPoint.NBP_URL_ROOT + NbpEndPoint.NBP_URL_FORGOTTEN_PASSWORD).permitAll();
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
        http.addFilterBefore(nbpFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
