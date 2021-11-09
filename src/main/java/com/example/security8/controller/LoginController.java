package com.example.security8.controller;

import com.example.security8.model.JwtResponse;
import com.example.security8.model.Role;
import com.example.security8.model.User;
import com.example.security8.sevice.service.JwtService;
import com.example.security8.sevice.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String token = jwtService.createTokenLogin(authentication);
        User user1 = userService.findByName(username);
        return new ResponseEntity<>(new JwtResponse(user1.getId(), username, token, userDetails.getAuthorities()), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        User user1 = userService.findByName(user.getName());
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(new Long(2), "ROLE_USER"));
        user.setRoles(roles);
        if (user1 == null) {
            userService.save(user);
            return new ResponseEntity<>("thanh cong", HttpStatus.OK);
        }
        return new ResponseEntity<>("that bai", HttpStatus.CONFLICT);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
