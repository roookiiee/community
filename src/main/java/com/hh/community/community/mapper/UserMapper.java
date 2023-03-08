package com.hh.community.community.mapper;

import com.hh.community.community.Model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
