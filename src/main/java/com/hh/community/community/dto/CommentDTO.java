package com.hh.community.community.dto;

import com.hh.community.community.Model.User;
import lombok.Data;

@Data
public class CommentDTO {

    private Integer id;

    private Long type;

    private Long commentator;

    private Long gmeCreate;

    private Long gmtModified;

    private Long likeCount;

    private String content;

    private User user;
}
