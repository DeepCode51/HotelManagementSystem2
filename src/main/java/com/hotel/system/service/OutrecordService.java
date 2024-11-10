package com.hotel.system.service;

import com.hotel.system.common.base.BaseService;
import com.hotel.system.entity.Outrecord;

/**
 * 退房登记服务
 *
 * @author liuyanzhao
 * @date 2022/04/26 11:58 下午
 */

public interface OutrecordService extends BaseService<Outrecord, Long> {

    Outrecord findByOrderId(Long orderId);

}