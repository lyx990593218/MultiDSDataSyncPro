/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *  封装分页对象
 * @author Fandy Liu
 * @created 2018年12月11日 下午2:02:51
 */
public class PageList {
    /**
     * 当前页
     */
    private int page; // 当前页
    /**
     * 总行数
     */
    private int totalRows; // 总行数
    /**
     * 总页数
     */
    private int pages; // 总页数
    /**
     * 数据对象
     */
    private List list = new ArrayList();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List getList() {
        if (list == null) {
            list = new ArrayList();
        }
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}