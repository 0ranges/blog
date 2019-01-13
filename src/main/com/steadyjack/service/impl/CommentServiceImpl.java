package steadyjack.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import steadyjack.dao.CommentDao;
import steadyjack.entity.Comment;
import steadyjack.service.CommentService;

/**
 * title:CommentServiceImpl.java
 * description:评论Service实现类
 * time:2017年1月16日 下午10:36:41
 * author:debug-steadyjack
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService{

    @Resource
    private CommentDao commentDao;

    public int add(Comment comment) {
        return commentDao.add(comment);
    }

    public List<Comment> list(Map<String, Object> map) {
        return commentDao.list(map);
    }

    public Long getTotal(Map<String, Object> map) {
        return commentDao.getTotal(map);
    }

    public Integer delete(Integer id) {
        return commentDao.delete(id);
    }

    public int update(Comment comment) {
        return commentDao.update(comment);
    }

}
