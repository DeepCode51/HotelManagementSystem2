package com.hotel.system.service.impl;

import com.hotel.system.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.system.dto.PostQueryCondition;
import com.hotel.system.entity.Post;
import com.hotel.system.mapper.PostMapper;
import com.hotel.system.service.PostService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <pre>
 *     客房业务逻辑实现类
 * </pre>
 * @author liuyanzhao
 * @date 2022/04/06
 */
@Service
public class PostServiceImpl implements PostService {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PostServiceImpl.class);
    @Autowired
    private PostMapper postMapper;

    @Override
    public Page<Post> findPostByCondition(PostQueryCondition condition, Page<Post> page) {
        List<Post> postList = postMapper.findPostByCondition(condition, page);
        return page.setRecords(postList);
    }

    @Override
    public BaseMapper<Post> getRepository() {
        return postMapper;
    }

    @Override
    public Post insert(Post post) {
        postMapper.insert(post);
        return post;
    }

    @Override
    public Post update(Post post) {
        postMapper.updateById(post);
        return post;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long postId) {
        Post post = this.get(postId);
        if (post != null) {
            postMapper.deleteById(post.getId());
        }
    }

    @Override
    public QueryWrapper<Post> getQueryWrapper(Post post) {
        //对指定字段查询
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        if (post != null) {
            if (StringUtils.isNotEmpty(post.getPostTitle())) {
                queryWrapper.like("post_title", post.getPostTitle());
            }
            if (StringUtils.isNotEmpty(post.getPostContent())) {
                queryWrapper.like("post_content", post.getPostContent());
            }
            if (post.getPostStatus() != null && post.getPostStatus() != -1) {
                queryWrapper.eq("post_status", post.getPostStatus());
            }
        }
        return queryWrapper;
    }

    @Override
    public Post insertOrUpdate(Post post) {
        if (post.getId() == null) {
            insert(post);
        } else {
            update(post);
        }
        return post;
    }

}

