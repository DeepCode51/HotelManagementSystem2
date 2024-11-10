package com.hotel.system.service;

import com.hotel.system.common.base.BaseService;
import com.hotel.system.entity.Report;
import java.util.List;

/**
 * <pre>
 *     报表接口
 * </pre>
 *
 * @author : wpx
 * @date : 2022/04/30
 */
public interface ReportService extends BaseService<Report, Long> {

    /**
     * 查询住房出租数量
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    List<Report> selectReport(String startDateTime, String endDateTime);



}
