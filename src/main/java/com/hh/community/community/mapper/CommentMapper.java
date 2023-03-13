package com.hh.community.community.mapper;

import com.hh.community.community.Model.Comment;
import com.hh.community.community.dto.CommentDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id = #{parentId}")
    Comment selectByPrimaryKey(@Param("parentId") Long parentId);

    @Select("select * from comment where parent_id= #{parentId} and type = #{type}")
    List<Comment> listByQuestionId(@Param("parentId")Long parentId,@Param("type") Integer type);
}
