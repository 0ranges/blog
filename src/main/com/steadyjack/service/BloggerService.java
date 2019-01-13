package steadyjack.service;

import steadyjack.entity.Blogger;

/**
 * title:BloggerService.java
 * description:博主Service接口
 * time:2017年1月16日 下午10:00:20
 * author:debug-steadyjack
 */
public interface BloggerService {

    /**
     * 查询博主信息
     * @return
     */
    public Blogger find();

    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    public Blogger getByUserName(String userName);

    /**
     * 更新博主信息
     * @param blogger
     * @return
     */
    public Integer update(Blogger blogger);
}
