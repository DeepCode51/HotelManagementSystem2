package com.hotel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.system.entity.Inrecord;
import com.hotel.system.mapper.InrecordMapper;
import com.hotel.system.service.InrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <pre>
 *    入住登记业务逻辑实现类
 * </pre>
 *
 * @author liuyanzhao
 * @date: 2022/04/30
 */
@Service
public class InrecordServiceImpl implements InrecordService {

    @Autowired
    private InrecordMapper inrecordMapper;

    @Override
    public BaseMapper<Inrecord> getRepository() {
        return inrecordMapper;
    }

    @Override
    public QueryWrapper<Inrecord> getQueryWrapper(Inrecord inrecord) {
        //对指定字段查询
        QueryWrapper<Inrecord> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }

    @Override
    public Inrecord findByOrderId(Long orderId) {
        QueryWrapper<Inrecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        return inrecordMapper.selectOne(queryWrapper);
    }

    @Override
    public Inrecord insert(Inrecord entity) {
        // 如果订单id存在，则更新
        Inrecord byOrderId = this.findByOrderId(entity.getOrderId());
        if (byOrderId != null) {
            entity.setId(byOrderId.getId());
            inrecordMapper.updateById(entity);
        } else {
            inrecordMapper.insert(entity);
        }

        return entity;
    }
}
