package com.weilus.dao;

import com.weilus.entity.Auth2RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Auth2RoleDao {

    @Select("SELECT id,user_id,authority FROM T_ROLE WHERE user_id=#{userId}")
    List<Auth2RoleEntity> loadByUserId(@Param("userId") Long userId);

}
