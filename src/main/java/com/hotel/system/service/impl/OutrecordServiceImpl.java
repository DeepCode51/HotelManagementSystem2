package com.hotel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.system.entity.Outrecord;
import com.hotel.system.mapper.OutrecordMapper;
import com.hotel.system.service.OutrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <pre>
 *    退房登记业务逻辑实现类
 * </pre>
 *
 * @author liuyanzhao
 * @date: 2022/04/30
 */
@Service
public class OutrecordServiceImpl implements OutrecordService {

    @Autowired
    private OutrecordMapper outrecordMapper;

    @Override
    public BaseMapper<Outrecord> getRepository() {
        return outrecordMapper;
    }

    @Override
    public QueryWrapper<Outrecord> getQueryWrapper(Outrecord outrecord) {
        //对指定字段查询
        QueryWrapper<Outrecord> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }
    
    @Override
    public Outrecord findByOrderId(Long orderId) {
        QueryWrapper<Outrecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        return outrecordMapper.selectOne(queryWrapper);
    }

    @Override
    public Outrecord insert(Outrecord entity) {
        // 如果订单id存在，则更新
        Outrecord byOrderId = this.findByOrderId(entity.getOrderId());
        if (byOrderId != null) {
            entity.setId(byOrderId.getId());
            outrecordMapper.updateById(entity);
        } else {
            outrecordMapper.insert(entity);
        }

        return entity;
    }

}
