package steadyjack.service.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import steadyjack.entity.Blog;
import steadyjack.entity.BlogType;
import steadyjack.entity.Blogger;
import steadyjack.entity.Link;
import steadyjack.service.BlogService;
import steadyjack.service.BlogTypeService;
import steadyjack.service.BloggerService;
import steadyjack.service.LinkService;

/**
 * title:InitComponent.java
 * description:初始化组件 把博主信息 根据博客类别分类信息 根据日期归档分类信息
 * 		                   存放到application中，用以提供页面请求性能
 * time:2017年1月16日 下午10:36:50
 * author:debug-steadyjack
 */
@Component
public class InitComponent implements ServletContextListener,ApplicationContextAware{

    //其实这个就是spring的IOC容器-spring上下文应用程序
    private static ApplicationContext applicationContext;

    @SuppressWarnings("static-access")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //application：整个应用的上下文应用程序,可以往此对象中绑定一些经常需要访问的对象
        ServletContext application=servletContextEvent.getServletContext();

        //Spring IOC容器中获取各个bean
        BloggerService bloggerService=(BloggerService) applicationContext.getBean("bloggerService");
        Blogger blogger=bloggerService.find(); // 查询博主信息
        blogger.setPassword(null);
        application.setAttribute("blogger", blogger);

        BlogTypeService blogTypeService=(BlogTypeService) applicationContext.getBean("blogTypeService");
        List<BlogType> blogTypeCountList=blogTypeService.countList(); // 查询博客类别以及博客的数量
        application.setAttribute("blogTypeCountList", blogTypeCountList);

        BlogService blogService=(BlogService) applicationContext.getBean("blogService");
        List<Blog> blogCountList=blogService.countList(); // 根据日期分组查询博客
        application.setAttribute("blogCountList", blogCountList);

        LinkService linkService=(LinkService) applicationContext.getBean("linkService");
        List<Link> linkList=linkService.list(null); // 查询所有的友情链接信息
        application.setAttribute("linkList", linkList);

        String ctx=application.getRealPath("/");
        System.out.println("根路径: "+ctx);
        application.setAttribute("ctx", ctx);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

}
