package steadyjack.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import steadyjack.dao.BlogTypeDao;
import steadyjack.entity.BlogType;
import steadyjack.service.BlogTypeService;

/**
 * title:BlogTypeServiceImpl.java
 * description:博客类型Service实现类
 * time:2017年1月16日 下午10:36:32
 * author:debug-steadyjack
 */
@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService{

    @Resource
    private BlogTypeDao blogTypeDao;

    public List<BlogType> countList() {
        return blogTypeDao.countList();
    }

    public List<BlogType> list(Map<String, Object> map) {
        return blogTypeDao.list(map);
    }

    public Long getTotal(Map<String, Object> map) {
        return blogTypeDao.getTotal(map);
    }

    public Integer add(BlogType blogType) {
        return blogTypeDao.add(blogType);
    }

    public Integer update(BlogType blogType) {
        return blogTypeDao.update(blogType);
    }

    public Integer delete(Integer id) {
        return blogTypeDao.delete(id);
    }

}
