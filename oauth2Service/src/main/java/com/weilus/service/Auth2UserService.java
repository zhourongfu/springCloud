package com.weilus.service;

import com.weilus.dao.Auth2UserDao;
import com.weilus.entity.Auth2UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by liutq on 2018/5/23.
 */
@Service
public class Auth2UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(Auth2UserService.class);
    @Autowired
    private Auth2UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth2UserEntity user = userDao.loadUserByUsername(username);
        if(null != user)logger.info(user.toString());
        return user;
    }
}
