package life.majiang.community.dto;

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
    private Integer currPage;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer currPage, Integer size) {

        if (totalCount % size == 0){
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (currPage < 1){
            currPage = 1;
        }

        if (currPage > totalPage){
            currPage = totalPage;
        }

        this.currPage = currPage;

        pages.add(currPage);
        for (int i = 1; i <= 3; i++){
            if (currPage - i > 0){
                pages.add(0, currPage - i);
            }

            if (currPage + i <= totalPage){
                pages.add(currPage + i);
            }
        }

        // 是否显示上一页标识
        if (currPage == 1){
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        //是否显示下一页标识
        if (currPage == totalPage){
            showNext = false;
        } else {
            showNext = true;
        }

        //是否显示第一页标识
        if (pages.contains(1)){
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //是否显示最后一页标识
        if (pages.contains(totalPage)){
            showEndPage = false;
        } else {
            showEndPage = true;

        }


    }
}
