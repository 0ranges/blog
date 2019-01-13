package steadyjack.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import steadyjack.entity.Blogger;
import steadyjack.service.BloggerService;
import steadyjack.util.CryptographyUtil;

/**
 * title:BloggerController.java
 * description:博主Controller层
 * time:2017年1月23日 下午10:01:10
 * author:debug-steadyjack
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

    @Resource
    private BloggerService bloggerService;

    /**
     * title:BloggerController.java
     * description:用户登录
     * time:2017年1月23日 下午10:01:20
     * author:debug-steadyjack
     * @param blogger
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login(Blogger blogger,HttpServletRequest request){
        Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(), "debug"));
        try{
            //登录验证
            subject.login(token);
            return "redirect:/admin/main.jsp";
        }catch(Exception e){
            e.printStackTrace();
            request.setAttribute("blogger", blogger);
            request.setAttribute("errorInfo", "用户名或密码错误！");
            return "login";
        }
    }

    /**
     * title:BloggerController.java
     * description:查找博主信息
     * time:2017年1月23日 下午10:12:45
     * author:debug-steadyjack
     * @return
     * @throws Exception
     */
    @RequestMapping("/aboutMe")
    public ModelAndView aboutMe()throws Exception{
        ModelAndView mav=new ModelAndView();

        mav.addObject("blogger",bloggerService.find());
        mav.addObject("mainPage", "foreground/blogger/info.jsp");
        mav.addObject("pageTitle","关于博主_Java开源博客系统");
        mav.setViewName("mainTemp");

        return mav;
    }
}
