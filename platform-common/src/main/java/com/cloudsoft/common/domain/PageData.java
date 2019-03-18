package com.cloudsoft.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *  分页记录
 */
@Data
public class PageData<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 当前页
	 */
    private int pageNo;
    /**
     * 每页大小
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 返回查询分页对象
     */
    private List<T> data;

    /**
     * page转pageData
     * @param page
     * @param <T>
     * @return
     */
//    public static <T>PageData getPageData(Page<T> page){
//        PageData pageData = new PageData();
//        pageData.setData(page.getContent());
//        pageData.setPageNo(page.getNumber());
//        pageData.setPageSize(page.getSize());
//        pageData.setTotal(page.get());
//        pageData.setPages(page.getPages());
//        return  pageData;
//    }
    
//    /**
//     * pageInfo转pageData
//     * @param pageInfo
//     * @param <T>
//     * @return
//     */
//    public static <T>PageData getPageData(PageInfo<T> pageInfo) {
//    	PageData pageData = new PageData();
//		pageData.setData(pageInfo.getList());
//		pageData.setPageNo(pageInfo.getPageNum());
//		pageData.setPages(pageInfo.getPages());
//		pageData.setPageSize(pageInfo.getPageSize());
//		pageData.setTotal(pageInfo.getTotal());
//        return  pageData;
//    }
//
//    /**
//     * pageInfo和data转pageData
//     * @param pageInfo
//     * @param data
//     * @param <T>
//     * @return
//     */
//    public static <T>PageData getPageData(PageInfo pageInfo, List<T> data) {
//    	PageData pageData = new PageData();
//		pageData.setData(data);
//		pageData.setPageNo(pageInfo.getPageNum());
//		pageData.setPages(pageInfo.getPages());
//		pageData.setPageSize(pageInfo.getPageSize());
//		pageData.setTotal(pageInfo.getTotal());
//        return  pageData;
//    }
}
