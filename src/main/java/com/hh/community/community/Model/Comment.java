package com.hh.community.community.Model;

import lombok.Data;

@Data
public class Comment {
    private Long id;

    private Long parentId;

    private Integer type;

    private Long commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer likeCount;

    private String content;
}
