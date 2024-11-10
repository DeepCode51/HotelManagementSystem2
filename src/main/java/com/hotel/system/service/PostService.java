package com.hotel.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.system.common.base.BaseService;
import com.hotel.system.dto.PostQueryCondition;
import com.hotel.system.entity.Post;

/**
 * <pre>
 *     记录/页面业务逻辑接口
 * </pre>
 * @author liuyanzhao
 * @date 2022/04/26
 */
public interface PostService extends BaseService<Post, Long> {

    /**
     * 根据条件获得列表
     * @param condition
     * @return
     */
    Page<Post> findPostByCondition(PostQueryCondition condition, Page<Post> page);



}
