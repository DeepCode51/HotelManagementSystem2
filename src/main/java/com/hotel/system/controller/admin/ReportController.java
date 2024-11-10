package com.hotel.system.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.hotel.system.controller.common.BaseController;
import com.hotel.system.dto.JsonResult;
import com.hotel.system.dto.ReportDTO;
import com.hotel.system.entity.*;
import com.hotel.system.mapper.ReportMapper;
import com.hotel.system.entity.Report;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <pre>
 *     后台统计报表控制器
 * </pre>
 *
 * @author : wpx
 * @date : 2022/04/10
 */
@Controller
@RequestMapping(value = "/admin/report")
public class ReportController extends BaseController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportMapper reportMapper;

    /**
     * 进入到统计报表的页面
     * @return
     */
    @GetMapping
    public String report() {
        return "admin/admin_report";
    }

    /**
     * 处理跳转到新建客房页面
     *
     * @return 模板路径admin/admin_editor
     * @param startDate 开始日期
     * @param endDate 结束日期
     */
    @GetMapping("/chartInfo")
    @ResponseBody
    public JsonResult chartInfo(String startDate, String endDate) {

        List<Report> reportList = reportMapper.selectReport(startDate, endDate);
        List<ReportDTO> reportDTOList = new ArrayList<>();
        reportList.forEach(report -> {
            Date createTime = report.getCreateTime();
            ReportDTO reportDTO = new ReportDTO();
            BeanUtils.copyProperties(report, reportDTO);
            String createDateStr = LocalDateTime.ofInstant(createTime.toInstant(), ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            reportDTO.setCreateDate(createDateStr);
            reportDTOList.add(reportDTO);
        });
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reportList", reportDTOList);
        return JsonResult.success("", jsonObject);
    }





}
