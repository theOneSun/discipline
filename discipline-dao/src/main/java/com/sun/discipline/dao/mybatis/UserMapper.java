package com.sun.discipline.dao.mybatis;

import com.sun.discipline.domain.common.User;
import com.sun.discipline.domain.common.WechatUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author sunjian.
 */
@Mapper
public interface UserMapper
{
    User getByCodeAndPassword(@Param("code") String code, @Param("password") String password);

    User getByCode(@Param("code") String code);

    /**
     * 根据手机号查询用户
     *
     * @param mobile 手机号
     * @return 用户
     */
    User getByMobile(@Param("mobile") String mobile);

    //根据openId查询wxUser
    WechatUser getByOpenId(@Param("openId") String openId);

    //根据用户id查询wxUser
    WechatUser getByUserId(@Param("userId") String userId);
}
