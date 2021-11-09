package com.example.security8.sevice.user;

import com.example.security8.model.User;
import com.example.security8.sevice.IGeneralService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IGeneralService<User>, UserDetailsService {

    User findByName(String name);
}
