package com.hh.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;

    private boolean showPrevious;

    private boolean showFirstPage;

    private boolean showNext;

    private boolean showEndPage;

    private Integer page;

    private List<Integer> pages = new ArrayList<>();

    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        this.page = page;

        if (totalCount == 0){
            totalPage = 1;
        }else if (totalCount % size == 0) {
            totalPage = totalCount / size;
        }
        else{
            totalPage = totalCount / size + 1;
        }

        for (int i = page-3; i <=page+3;i++){
            if ( i > 0 && i <=totalPage){
                pages.add(i);
            }
        }


        // 是否为上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        //是否为下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示第一页
        if (pages.contains(1)){
            showFirstPage = false;
        }
        else {
            showFirstPage = true;
        }

        if (pages.contains(totalPage)){
            showEndPage = false;
        }
        else {
            showEndPage = true;
        }
    }
}
