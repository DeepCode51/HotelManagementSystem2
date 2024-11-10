package com.hotel.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.system.entity.Report;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportMapper extends BaseMapper<Report> {

    /**
     * 查询住房出租数量
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    List<Report> selectReport(String startDateTime, String endDateTime);
}