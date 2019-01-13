package steadyjack.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import steadyjack.dao.LinkDao;
import steadyjack.entity.Link;
import steadyjack.service.LinkService;

/**
 * title:LinkServiceImpl.java
 * description:友情链接Service实现类
 * time:2017年1月16日 下午10:37:15
 * author:debug-steadyjack
 */
@Service("linkService")
public class LinkServiceImpl implements LinkService{

    @Resource
    private LinkDao linkDao;

    public int add(Link link) {
        return linkDao.add(link);
    }

    public int update(Link link) {
        return linkDao.update(link);
    }

    public List<Link> list(Map<String, Object> map) {
        return linkDao.list(map);
    }

    public Long getTotal(Map<String, Object> map) {
        return linkDao.getTotal(map);
    }

    public Integer delete(Integer id) {
        return linkDao.delete(id);
    }

}
