package com.weilus.dao;

import com.weilus.entity.Auth2UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.Alias;

/**
 * Created by liutq on 2018/5/23.
 */

@Mapper
public interface Auth2UserDao {

    @Select("SELECT ID,USERNAME,USERPWD,LOCKED,ENABLE,ACCOUNTEXPIRED FROM T_USER WHERE USERNAME=#{username}")
    Auth2UserEntity loadUserByUsername(@Param("username") String username);

}
