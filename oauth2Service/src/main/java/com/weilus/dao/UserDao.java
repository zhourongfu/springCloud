package com.weilus.dao;

import com.weilus.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liutq on 2018/5/23.
 */

@Mapper
public interface UserDao {

    UserEntity loadUserByUsername(@Param("username") String username);

}
