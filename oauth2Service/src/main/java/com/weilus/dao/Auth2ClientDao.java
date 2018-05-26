package com.weilus.dao;

import com.weilus.entity.Auth2ClientEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * Created by liutq on 2018/5/23.
 */
@Mapper
public interface Auth2ClientDao {
    @Select("SELECT client_id,client_secret,scopes,grant_types,access_token_validity_seconds,refresh_token_validity_seconds " +
            "FROM T_AUTH2_CLIENT WHERE client_id=#{clientId}")
    Auth2ClientEntity loadClientByClientId(@Param("clientId") String clientId);
}
