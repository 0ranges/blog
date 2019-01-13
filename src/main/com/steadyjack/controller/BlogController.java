package steadyjack.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import steadyjack.entity.Blog;
import steadyjack.lucene.BlogIndex;
import steadyjack.service.BlogService;
import steadyjack.service.CommentService;
import steadyjack.util.StringUtil;

/**
 * title:BlogController.java
 * description:前端博客Controller层
 * time:2017年1月17日 下午9:44:19
 * author:debug-steadyjack
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    @Resource
    private CommentService commentService;

    // 博客索引
    private BlogIndex blogIndex=new BlogIndex();


    /**
     * title:BlogController.java
     * description:博客详细信息
     * time:2017年1月17日 下午9:51:50
     * author:debug-steadyjack
     * @param id
     * @param request
     * @throws Exception
     */
    @RequestMapping("/articles/{id}")
    public ModelAndView details(@PathVariable Integer id,HttpServletRequest request)throws Exception{
        ModelAndView modelAndView=new ModelAndView();

        Blog blog=blogService.findById(id);
        String keyWords=blog.getKeyWord();

        if(StringUtil.isNotEmpty(keyWords)){
            String arr[]=keyWords.split(" ");
            modelAndView.addObject("keyWords",StringUtil.filterWhite(Arrays.asList(arr)));
        }else{
            modelAndView.addObject("keyWords",null);
        }

        modelAndView.addObject("blog", blog);

        Integer clickHit=blog.getClickHit();
        clickHit = clickHit==null?0:clickHit+1;
        //博客点击次数加1
        blog.setClickHit(clickHit);
        blogService.update(blog);

        Map<String,Object> map=new HashMap<String,Object>();
        //查询审核通过的评论
        map.put("blogId", blog.getId());
        map.put("state", 1);

        //System.out.println(request.getServletContext().getContextPath());

        modelAndView.addObject("commentList", commentService.list(map));
        modelAndView.addObject("pageCode", this.genUpAndDownPageCode(blogService.getLastBlog(id),blogService.getNextBlog(id),request.getServletContext().getContextPath()));
        modelAndView.addObject("mainPage", "foreground/blog/view.jsp");
        modelAndView.addObject("pageTitle",blog.getTitle());
        modelAndView.setViewName("mainTemp");

        //查询审核通过的评论
        return modelAndView;
    }

    /**
     * title:BlogController.java
     * description:根据查询信息查询相关博客信息
     * time:2017年1月17日 下午11:11:29
     * author:debug-steadyjack
     * @param q
     * @param page
     * @param request
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/q")
    public ModelAndView search(@RequestParam(value="q",required=false)String q,@RequestParam(value="page",required=false)String page,HttpServletRequest request)throws Exception{
        if(StringUtil.isEmpty(page)){
            page="1";
        }
        ModelAndView mav=new ModelAndView();

        mav.addObject("mainPage", "foreground/blog/result.jsp");

        //blogIndex.setRequest(request);
        List<Blog> blogList=blogIndex.searchBlog(q.trim());
        Integer pageNo=Integer.parseInt(page);
        Integer pageSize=10;
        Integer toIndex=blogList.size()>=pageNo*pageSize?pageNo*pageSize:blogList.size();

        //list的subList是分页的一种:返回列表中指定的 fromIndex（包括 ）和 toIndex（不包括）之间的部分视图
        mav.addObject("blogList",blogList.subList((pageNo-1)*pageSize, toIndex));
        mav.addObject("pageCode",this.genUpAndDownPageCode(pageNo, blogList.size(), q,10,request.getServletContext().getContextPath()));
        mav.addObject("q",q);
        mav.addObject("resultTotal",blogList.size());
        mav.addObject("pageTitle","搜索关键字'"+q+"'结果页面_Java修炼之博客系统");
        mav.setViewName("mainTemp");

        return mav;
    }

    /**
     * title:BlogController.java
     * description: 获取下一篇博客和下一篇博客
     * time:2017年1月17日 下午10:14:03
     * author:debug-steadyjack
     * @param lastBlog
     * @param nextBlog
     * @param projectContext
     * @return String
     */
    private String genUpAndDownPageCode(Blog lastBlog,Blog nextBlog,String projectContext){
        StringBuffer pageCode=new StringBuffer();
        if(lastBlog==null || lastBlog.getId()==null){
            pageCode.append("<p>上一篇：没有了</p>");
        }else{
            pageCode.append("<p>上一篇：<a href='"+projectContext+"/blog/articles/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");
        }
        if(nextBlog==null || nextBlog.getId()==null){
            pageCode.append("<p>下一篇：没有了</p>");
        }else{
            pageCode.append("<p>下一篇：<a href='"+projectContext+"/blog/articles/"+nextBlog.getId()+".html'>"+nextBlog.getTitle()+"</a></p>");
        }
        return pageCode.toString();
    }

    /**
     * 获取上一页，下一页代码 查询博客用到(简单的分页)
     * @param page 当前页
     * @param totalNum 总记录数
     * @param q 查询关键字
     * @param pageSize 每页大小
     * @param projectContext
     * @return
     */
    private String genUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize,String projectContext){
        long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        StringBuffer pageCode=new StringBuffer();
        if(totalPage==0){
            return "";
        }else{
            pageCode.append("<nav>");
            pageCode.append("<ul class='pager' >");
            if(page>1){
                pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page-1)+"&q="+q+"'>上一页</a></li>");
            }else{
                pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
            }
            if(page<totalPage){
                pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page+1)+"&q="+q+"'>下一页</a></li>");
            }else{
                pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
            }
            pageCode.append("</ul>");
            pageCode.append("</nav>");
        }
        return pageCode.toString();
    }
}
