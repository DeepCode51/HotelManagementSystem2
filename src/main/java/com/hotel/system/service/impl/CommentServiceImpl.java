package com.hotel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.system.entity.Comment;
import com.hotel.system.mapper.CommentMapper;
import com.hotel.system.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <pre>
 *     留言业务逻辑实现类
 * </pre>
 * @author liuyanzhao
 * @date: 2022/04/30
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public BaseMapper<Comment> getRepository() {
        return commentMapper;
    }

    @Override
    public QueryWrapper<Comment> getQueryWrapper(Comment comment) {
        //对指定字段查询
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }


}
