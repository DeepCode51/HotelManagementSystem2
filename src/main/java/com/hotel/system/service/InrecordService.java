package com.hotel.system.service;

import com.hotel.system.common.base.BaseService;
import com.hotel.system.entity.Inrecord;

/**
 * 入住登记服务
 *
 * @author liuyanzhao
 * @date 2022/04/26 11:58 下午
 */

public interface InrecordService extends BaseService<Inrecord, Long> {


    Inrecord findByOrderId(Long orderId);
}