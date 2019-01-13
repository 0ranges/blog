package steadyjack.dao;

import steadyjack.entity.Blogger;

/**
 * title:BloggerDao.java
 * description:博主Dao接口
 * time:2017年1月16日 下午10:16:44
 * author:debug-steadyjack
 */
public interface BloggerDao {

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
