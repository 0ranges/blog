package steadyjack.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import steadyjack.entity.Blog;
import steadyjack.entity.Comment;
import steadyjack.service.BlogService;
import steadyjack.service.CommentService;
import steadyjack.util.ResponseUtil;

/**
 * 评论Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private BlogService blogService;

    /**
     * title:CommentController.java
     * description:添加或者修改评论
     * time:2017年1月23日 下午8:02:05
     * author:debug-steadyjack
     * @param comment
     * @param imageCode
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(Comment comment,@RequestParam("imageCode") String imageCode,
                       HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
        //获取系统生成的验证码
        String sRand=(String) session.getAttribute("sRand");

        JSONObject result=new JSONObject();

        //操作的记录条数
        int resultTotal=0;
        if(!imageCode.equals(sRand)){
            result.put("success", false);
            result.put("errorInfo", "验证码填写错误！");
        }else{
            //获取用户IP
            String userIp=request.getRemoteAddr();
            comment.setUserIp(userIp);
            if(comment.getId()==null){
                resultTotal=commentService.add(comment);
                //该博客的回复次数加1
                Blog blog=blogService.findById(comment.getBlog().getId());
                blog.setReplyHit(blog.getReplyHit()+1);
                blogService.update(blog);
            }else{

            }
            if(resultTotal>0){
                result.put("success", true);
            }else{
                result.put("success", false);
            }
        }
        ResponseUtil.write(response, result);
        return null;
    }

}
