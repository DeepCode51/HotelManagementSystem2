package com.hotel.system.config.schedule;

import com.hotel.system.entity.Report;
import com.hotel.system.mapper.PostMapper;
import com.hotel.system.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     定时任务
 * </pre>
 *
 * @author : xuxin
 * @date : 2022/09/26
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ReportSchedule {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PostMapper postMapper;

    //3.添加定时任务
    /**
     *  统计客房每日的客房总数，客房出租数量
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    @Transactional(rollbackFor = Exception.class)
    public void reportTask() {

        String startDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now())+" 00:00:00";
        String endDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now())+" 23:59:59";
        List<Report> reportList = reportService.selectReport(startDateTime, endDateTime);

        Report report = new Report();
        String now = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
        Integer rentCount = postMapper.selectRentCountInfo(now);
        Integer postCount = postMapper.countPost();
        report.setPostCount(postCount);
        report.setRentCount(rentCount);

        if(!CollectionUtils.isEmpty(reportList)){
            Report report1 = reportList.get(0);
            report1.setPostCount(postCount);
            report1.setRentCount(rentCount);
            report1.setUpdateTime(new Date());
            reportService.update(report1);
        } else {
            reportService.insert(report);
        }


    }

}
