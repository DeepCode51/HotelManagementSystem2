package com.hotel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.system.entity.Clean;
import com.hotel.system.mapper.CleanMapper;
import com.hotel.system.service.CleanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * <pre>
 *     打扫记录业务逻辑实现类
 * </pre>
 * @author liuyanzhao
 * @date: 2022/04/30
 */
@Service
public class CleanServiceImpl implements CleanService {

    @Autowired
    private CleanMapper cleanMapper;

    @Override
    public BaseMapper<Clean> getRepository() {
        return cleanMapper;
    }

    @Override
    public QueryWrapper<Clean> getQueryWrapper(Clean clean) {
        //对指定字段查询
        QueryWrapper<Clean> queryWrapper = new QueryWrapper<>();
        if (clean != null) {
            if (clean.getStatus() != null && clean.getStatus() != -1) {
                queryWrapper.eq("status", clean.getStatus());
            }
        }
        return queryWrapper;
    }


}
