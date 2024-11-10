package com.hotel.system.service.impl;

import com.hotel.system.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.system.entity.Category;
import com.hotel.system.mapper.CategoryMapper;
import com.hotel.system.mapper.PostMapper;
import com.hotel.system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 *     分类业务逻辑实现类
 * </pre>
 * @author liuyanzhao
 * @date: 2022/04/30
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public BaseMapper<Category> getRepository() {
        return categoryMapper;
    }

    @Override
    public QueryWrapper<Category> getQueryWrapper(Category category) {
        //对指定字段查询
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (category != null) {
            if (StringUtils.isNotEmpty(category.getCateName())) {
                queryWrapper.like("cate_name", category.getCateName());
            }
        }
        return queryWrapper;
    }

    @Override
    public Category insert(Category category) {
        categoryMapper.insert(category);
        return category;
    }

    @Override
    public Category update(Category category) {
        categoryMapper.updateById(category);
        return category;
    }

    @Override
    public void delete(Long id) {
        //2.删除分类
        categoryMapper.deleteById(id);
    }


    @Override
    public List<Category> findByUserId(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        return categoryMapper.selectList(queryWrapper);
    }


    @Override
    public Integer countPostByCateId(Long cateId) {
        return postMapper.countPostByCateId(cateId);
    }

    @Override
    public Category insertOrUpdate(Category entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            update(entity);
        }
        return entity;
    }

    @Override
    public Integer deleteByUserId(Long userId) {
        return categoryMapper.deleteByUserId(userId);
    }

    @Override
    public List<Category> cateIdsToCateList(List<Long> cateIds, Long userId) {
        List<Category> categoryList = this.findByUserId(userId);
        List<Long> allCateIds = categoryList.stream().map(Category::getId).collect(Collectors.toList());
        List<Category> result = new ArrayList<>();
        for(Long id : cateIds) {
            if(allCateIds.contains(id)) {
                Category category = new Category();
                category.setId(id);
                result.add(category);
            }
        }
        return result;
    }


}
