package com.hh.community.community.mapper;

import com.hh.community.community.Model.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("SELECT * FROM USER WHERE token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("SELECT * FROM USER WHERE id = #{id}")
    User findById(@Param("id")Integer id);

    @Select("SELECT * FROM USER WHERE account_id = #{accountId}")
    User findByAccountId(@Param("accountId")String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified} , avatar_url = #{avatarUrl}  where id = #{id}")
    void update(User user);
}
