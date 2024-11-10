package com.hotel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.system.entity.Report;
import com.hotel.system.mapper.ReportMapper;
import com.hotel.system.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <pre>
 *     分类业务逻辑实现类
 * </pre>
 * @author liuyanzhao
 * @date: 2022/04/30
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;


    @Override
    public BaseMapper<Report> getRepository() {
        return reportMapper;
    }

    @Override
    public QueryWrapper<Report> getQueryWrapper(Report report) {
        //对指定字段查询
        QueryWrapper<Report> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }

    /**
     * 查询住房出租数量
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    @Override
    public List<Report> selectReport(String startDateTime, String endDateTime){
        return  reportMapper.selectReport(startDateTime, endDateTime);
    }

}
