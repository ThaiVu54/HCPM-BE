package com.example.hcpm_be.security.SecurityConfig;


import com.example.hcpm_be.model.entity.Role;
import com.example.hcpm_be.model.entity.RoleName;
import com.example.hcpm_be.model.entity.User;
import com.example.hcpm_be.security.jwt.JwtEntryPoint;
import com.example.hcpm_be.security.jwt.JwtTokenFilter;
import com.example.hcpm_be.service.IRoleService;
import com.example.hcpm_be.service.IUserService;
import com.example.hcpm_be.service.implement.UserService;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    public void init(){
        List<User> users = (List<User>) userService.findAll();
        List<Role> roles = (List<Role>) roleService.findAll();
        if (roles.isEmpty()){
            Role roleAdmin = new Role();
            roleAdmin.setId(1L);
            roleAdmin.setName(RoleName.ROLE_ADMIN);
            roleService.save(roleAdmin);
            Role employee = new Role();
            employee.setId(2L);
            employee.setName(RoleName.ROLE_EMPLOYEE);
            roleService.save(employee);
            Role roleProvider = new Role();
            roleProvider.setId(3L);
            roleProvider.setName(RoleName.ROLE_CUSTOMER);
            roleService.save(roleProvider);
        }
//        if (users.isEmpty()){
//            User userAdmin = new User();
//            Set<Role> rolesSet = new HashSet<>();
//            rolesSet.add(new Role(1L, RoleName.ROLE_ADMIN));
//            userAdmin.setId(1L);
//            userAdmin.setAvatar("https://firebasestorage.googleapis.com/v0/b/hcpm-eab2d.appspot.com/o/admin.png?alt=media&token=9f080cab-d7be-4a47-bcdb-585d14c8591e");
//            userAdmin.setFullName("admin");
//            userAdmin.setUsername("admin");
//            userAdmin.setPhone("0987654321");
//            userAdmin.setEmail("admin1@gmail.com");
//            userAdmin.setDateStart(LocalDate.now());
//            userAdmin.setPassword(passwordEncoder.encode("123456"));
//            userAdmin.setRoles(rolesSet);
//            userService.save(userAdmin);
//        }
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
