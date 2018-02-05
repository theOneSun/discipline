package com.sun.discipline.dao.mybatis;

import com.sun.discipline.domain.common.User;
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
}
