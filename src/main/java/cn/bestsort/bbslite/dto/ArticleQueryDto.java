package cn.bestsort.bbslite.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * TODO
 * @author bestsort
 * @date 2019/10/7 下午12:03
 * @version 1.0
 */
@Data
public class ArticleQueryDto {
    private String search;
    private String sortBy;
    private Integer topic;
    private String tag;
    private long beginTime;
    private long endTime;
    private Long userId;
    private Integer category;
    private Integer pageSize;
    private Integer pageNo;
}
