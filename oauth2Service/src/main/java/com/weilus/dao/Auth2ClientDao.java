package com.weilus.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * Created by liutq on 2018/5/23.
 */
@Mapper
public interface Auth2ClientDao {

    ClientDetails loadClientByClientId(@Param("clientId") String clientId);
}
