package com.lihebin.sso.dao;

import com.lihebin.sso.model.SsoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lihebin on 2019/4/16.
 */
@Repository
public interface SsoUserDao extends JpaRepository<SsoUser, Long> {

    /**
     * 获取商户用户账号信息
     *
     * @param username
     * @return
     */
    SsoUser findSsoUserByUsername(String username);


}
