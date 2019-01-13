package steadyjack.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import steadyjack.dao.BloggerDao;
import steadyjack.entity.Blogger;
import steadyjack.service.BloggerService;

/**
 * title:BloggerServiceImpl.java
 * description:博主Service实现类
 * time:2017年1月16日 下午10:36:10
 * author:debug-steadyjack
 */
@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService{

    @Resource
    private BloggerDao bloggerDao;

    public Blogger find() {
        return bloggerDao.find();
    }

    public Blogger getByUserName(String userName) {
        return bloggerDao.getByUserName(userName);
    }

    public Integer update(Blogger blogger) {
        return bloggerDao.update(blogger);
    }


}
