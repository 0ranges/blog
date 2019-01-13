package steadyjack.realm;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import steadyjack.entity.Blogger;
import steadyjack.service.BloggerService;

/**
 * title:MyRealm.java
 * description:自定义Realm(域)
 * time:2017年1月22日 下午10:50:57
 * author:debug-steadyjack
 */
public class MyRealm extends AuthorizingRealm{

    @Resource
    private BloggerService bloggerService;

    /**
     * 为当限前登录的用户授予角色和权
     * (由于目前系统没有啥资源且只有admin超级用户,故而没写授予角色、权限(资源)逻辑)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 验证当前登录的用户
     * (成功时,将登陆用户绑定到会话中;失败时,其实会报各种exception,理当抛出用于前端页面展示(后期实现))
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取身份(在这里指 用户名)-凭证(在这里指 密码)
        String userName=(String)token.getPrincipal();
        Blogger blogger=bloggerService.getByUserName(userName);
        if(blogger!=null){
            //当前用户信息存到session中
            SecurityUtils.getSubject().getSession().setAttribute("currentUser", blogger);
            AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(blogger.getUserName(),blogger.getPassword(),getName());
            return authcInfo;
        }else{
            return null;
        }
    }

}
