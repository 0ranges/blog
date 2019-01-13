package steadyjack.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import steadyjack.entity.Comment;
import steadyjack.entity.PageBean;
import steadyjack.service.CommentService;
import steadyjack.util.ResponseUtil;

/**
 * title:CommentAdminController.java
 * description:管理员评论Controller层
 * time:2017年1月23日 下午10:24:52
 * author:debug-steadyjack
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {

    @Resource
    private CommentService commentService;

    /**
     * title:CommentAdminController.java
     * description:分页查询评论信息
     * time:2017年1月23日 下午10:25:04
     * author:debug-steadyjack
     * @param page
     * @param rows
     * @param state
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value="page",required=false)String page,
                       @RequestParam(value="rows",required=false)String rows,
                       @RequestParam(value="state",required=false)String state,HttpServletResponse response)throws Exception{
        PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        //评论状态
        map.put("state", state);

        List<Comment> commentList=commentService.list(map);
        Long total=commentService.getTotal(map);

        JSONObject result=new JSONObject();
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray=JSONArray.fromObject(commentList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);

        return null;
    }

    /**
     * title:CommentAdminController.java
     * description:删除评论信息
     * time:2017年1月23日 下午10:31:32
     * author:debug-steadyjack
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
        String[] idsStr=ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            commentService.delete(Integer.parseInt(idsStr[i]));
        }
        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);

        return null;
    }

    /**
     * title:CommentAdminController.java
     * description:评论审核
     * time:2017年1月23日 下午10:31:58
     * author:debug-steadyjack
     * @param ids
     * @param state
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/review")
    public String review(@RequestParam(value="ids")String ids,@RequestParam(value="state")Integer state,
                         HttpServletResponse response)throws Exception{
        String[] idsStr=ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            Comment comment=new Comment();
            comment.setState(state);
            comment.setId(Integer.parseInt(idsStr[i]));
            commentService.update(comment);
        }
        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);

        return null;
    }
}
