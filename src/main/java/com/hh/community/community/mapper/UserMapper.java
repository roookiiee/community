package com.hh.community.community.mapper;

import com.hh.community.community.Model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface UserMapper {

    @Insert("INSERT INTO USER (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("SELECT * FROM USER WHERE token = #{token}")
    User findByToken(@Param("token") String token);

//    @Select("SELECT * FROM USER WHERE id = #{id}")
    User findById(@Param("id")Long id);

    @Select("SELECT * FROM USER WHERE account_id = #{accountId}")
    User findByAccountId(@Param("accountId")String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified} , avatar_url = #{avatarUrl}  where id = #{id}")
    void update(User user);

    @Select({"<script>"
            + "SELECT * FROM user WHERE id IN"
            + "<foreach item='item' index='index' collection='userIds' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>"})
    List<User> userListById(@Param("userIds") List<Long> userIds);
}
