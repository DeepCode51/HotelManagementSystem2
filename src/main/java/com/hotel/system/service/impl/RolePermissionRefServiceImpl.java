package com.hotel.system.service.impl;

import com.hotel.system.entity.RolePermissionRef;
import com.hotel.system.mapper.RolePermissionRefMapper;
import com.hotel.system.service.RolePermissionRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author liuyanzhao
 * @date: 2022/04/30
 */
@Service
public class RolePermissionRefServiceImpl implements RolePermissionRefService {

    @Autowired
    private RolePermissionRefMapper rolePermissionRefMapper;

    @Override
    public void deleteRefByRoleId(Long roleId) {
        rolePermissionRefMapper.deleteByRoleId(roleId);
    }

    @Override
    public void saveByRolePermissionRef(RolePermissionRef rolePermissionRef) {
        rolePermissionRefMapper.insert(rolePermissionRef);
    }

    @Override
    public void batchSaveByRolePermissionRef(List<RolePermissionRef> rolePermissionRefs) {
        rolePermissionRefMapper.batchInsert(rolePermissionRefs);
    }
}