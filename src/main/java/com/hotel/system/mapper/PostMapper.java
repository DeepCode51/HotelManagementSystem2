package com.hotel.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.system.dto.PostQueryCondition;
import com.hotel.system.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuyanzhao
 * @date: 2022/04/30
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 根据标签ID查询客房
     *
     * @param condition
     * @param page
     * @return
     */
    List<Post> findPostByCondition(@Param("condition") PostQueryCondition condition, Page page);

    /**
     * 统计该分类的客房
     * @param cateId
     * @return
     */
    Integer countPostByCateId(Long cateId);

    /**
     * 查询可以出租的客房数量
     * @return
     */
    Integer countPost();

    /**
     * 按房型查询住房出租次数
     * @param dateStr
     * @return
     */
    Integer selectRentCountInfo(String dateStr);

}

