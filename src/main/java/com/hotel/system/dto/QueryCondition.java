package com.hotel.system.dto;

import com.hotel.system.vo.SearchVo;

import java.io.Serializable;

/**
 * 查询封装类
 * @author liuyanzhao
 * @date 2022-03-16 13:45
 */
public class QueryCondition<T> implements Serializable {

    /**
     * 根据字段筛选
     */
    private T data;

    /**
     * 一般筛选
     */
    private SearchVo searchVo;


    public QueryCondition() {
    }

    public QueryCondition(T data) {
        this.data = data;
    }

    public QueryCondition(T data, SearchVo searchVo) {
        this.data = data;
        this.searchVo = searchVo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SearchVo getSearchVo() {
        return searchVo;
    }

    public void setSearchVo(SearchVo searchVo) {
        this.searchVo = searchVo;
    }

    @Override
    public String toString() {
        return "QueryCondition{" +
                "data=" + data +
                ", searchVo=" + searchVo +
                '}';
    }
}
