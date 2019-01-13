package steadyjack.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import steadyjack.entity.Blog;
import steadyjack.entity.PageBean;
import steadyjack.service.BlogService;
import steadyjack.util.PageUtil;
import steadyjack.util.StringUtil;
import steadyjack.util.WebFileUtil;

/**
 * title:IndexController.java
 * description:前端主页Controller
 * time:2017年1月21日 下午10:28:50
 * author:debug-steadyjack
 */
@Controller
public class IndexController {

    @Resource
    private BlogService blogService;

    @RequestMapping("/")
    public String defaultPage(){
        return "redirect:/index";
    }

    /**
     * title:IndexController.java
     * description:请求主页
     * time:2017年1月21日 下午10:30:17
     * author:debug-steadyjack
     * @param page
     * @param typeId
     * @param releaseDateStr
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value="page",required=false)String page,
                              @RequestParam(value="typeId",required=false)String typeId,
                              @RequestParam(value="releaseDateStr",required=false)String releaseDateStr,
                              HttpServletRequest request)throws Exception{

        ModelAndView mav=new ModelAndView();
        if(StringUtil.isEmpty(page)){
            page="1";
        }
        Integer pageNo=Integer.parseInt(page);

        PageBean pageBean=new PageBean(pageNo,10);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("typeId", typeId);
        map.put("releaseDateStr", releaseDateStr);
        List<Blog> blogList=blogService.list(map);

        for(Blog blog:blogList){
            //用于存放博客内容里面的图片,从而生成缩略图-用于前端页面展示
            List<String> imagesList=blog.getImagesList();

            String blogInfo=blog.getContent();
            Document doc=Jsoup.parse(blogInfo);

            //查找扩展名是jpg的图片：根据页面需要拿n张图片,这里拿了3张
            Elements jpgs=doc.select("img[src$=.jpg]");
            if (jpgs!=null && jpgs.size()>0) {
                for(int i=0;i<jpgs.size();i++){
                    Element jpg=jpgs.get(i);
                    imagesList.add(jpg.toString());
                    if(i==2){
                        break;
                    }
                }
            }

        }
        mav.addObject("blogList", blogList);

        //查询参数
        StringBuffer param=new StringBuffer();
        if(StringUtil.isNotEmpty(typeId)){
            param.append("typeId="+typeId+"&");
        }
        if(StringUtil.isNotEmpty(releaseDateStr)){
            param.append("releaseDateStr="+releaseDateStr+"&");
        }

        Long totalRecord=blogService.getTotal(map);
        Integer pageSize=10;
        String genPagination=PageUtil.genPagination(request.getContextPath()+"/index.html",totalRecord,pageNo,pageSize,param.toString());

        //分页、主页、页面title、跳转的页面地址
        mav.addObject("pageCode",genPagination);
        mav.addObject("mainPage", "foreground/blog/list.jsp");
        mav.addObject("pageTitle","Java开源博客系统");
        mav.setViewName("mainTemp");

        return mav;
    }

    /**
     * title:IndexController.java
     * description:系统功能介绍页面
     * time:2017年1月22日 下午8:48:10
     * author:debug-steadyjack
     * @return
     * @throws Exception
     */
    @RequestMapping("/download")
    public ModelAndView download()throws Exception{
        ModelAndView mav=new ModelAndView();

        mav.addObject("mainPage", "foreground/system/download.jsp");
        mav.addObject("pageTitle","本站源码下载页面_Java开源博客系统");
        mav.setViewName("mainTemp");

        return mav;
    }

    /**
     * title:IndexController.java
     * description:下载系统源码
     * time:2017年1月22日 下午9:56:12
     * author:debug-steadyjack
     * @param request
     * @throws Exception
     */
    @RequestMapping("/downloadFile")
    public void downloadFile(@RequestParam(value="fileUrl")String fileUrl,
                             @RequestParam(value="fileName")String fileName,HttpServletRequest request,HttpServletResponse response)
            throws Exception{
        WebFileUtil.downloadFile(request, response, fileUrl, fileName);
    }



}
