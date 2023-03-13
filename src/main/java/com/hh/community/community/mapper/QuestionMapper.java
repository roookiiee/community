package com.hh.community.community.mapper;

import com.hh.community.community.Model.Question;
import org.apache.ibatis.annotations.*;



import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> findAllQuestion(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{id} limit #{offset},#{size}")
    List<Question> findAllQuestionByUserId(@Param(value = "id") Long id,@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from question where creator = #{id}")
    Integer countByUserId(@Param(value = "id") Long id);

    @Select("select * from question where id = #{id}")
    Question getById(@Param(value = "id") Long id);

    @Update("update question set title = #{title}, description = #{description},tag = #{tag}, gmt_modified = #{gmtModified} where id = #{id}")
    void edit(Question question);

    @Update ("update question set view_count = view_count + 1 where id = #{id}")
    void viewCountAdd(@Param(value = "id") Long id);

    @Update("update question set comment_count = comment_count + 1 where id = #{id}")
    void incCommentCount(Question question);
}
