package com.weilus.service;

import com.weilus.dao.Auth2RoleDao;
import com.weilus.dao.Auth2UserDao;
import com.weilus.entity.Auth2RoleEntity;
import com.weilus.entity.Auth2UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liutq on 2018/5/23.
 */
@Service
public class Auth2UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(Auth2UserService.class);
    @Autowired
    private Auth2UserDao auth2UserDao;
    @Autowired
    private Auth2RoleDao auth2RoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth2UserEntity user = auth2UserDao.loadUserByUsername(username);
        if(null == user){
            throw new UsernameNotFoundException(String.format("Username %s not found",new Object[] { username }));
        }
        List<Auth2RoleEntity> roles = auth2RoleDao.loadByUserId(user.getId());
        user.setAuthorities(roles);
        return user;
    }
}
