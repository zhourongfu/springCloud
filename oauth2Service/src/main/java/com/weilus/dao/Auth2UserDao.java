package com.weilus.dao;

import com.weilus.entity.Auth2UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liutq on 2018/5/23.
 */

@Mapper
public interface Auth2UserDao {

    Auth2UserEntity loadUserByUsername(@Param("username") String username);

}
