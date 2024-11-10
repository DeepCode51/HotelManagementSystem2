package com.hotel.system.service;

import com.hotel.system.common.base.BaseService;
import com.hotel.system.entity.UserRoleRef;

/**
 * @author liuyanzhao
 * @date 2022/04/26
 */

public interface UserRoleRefService extends BaseService<UserRoleRef, Long> {

    /**
     * 根据用户Id删除
     *
     * @param userId 用户Id
     */
    void deleteByUserId(Long userId);


}
