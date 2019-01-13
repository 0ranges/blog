package steadyjack.controller.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import steadyjack.entity.Link;
import steadyjack.entity.PageBean;
import steadyjack.service.LinkService;
import steadyjack.util.ResponseUtil;


/**
 * title:LinkAdminController.java
 * description:友情链接Controller层
 * time:2017年1月23日 下午10:32:33
 * author:debug-steadyjack
 */
@Controller
@RequestMapping("/admin/link")
public class LinkAdminController {

    @Resource
    private LinkService linkService;

    /**
     * title:LinkAdminController.java
     * description:分页查询友情链接信息
     * time:2017年1月23日 下午10:32:41
     * author:debug-steadyjack
     * @param page
     * @param rows
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value="page",required=false)String page,
                       @RequestParam(value="rows",required=false)String rows,
                       HttpServletResponse response)throws Exception{
        PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());

        List<Link> linkList=linkService.list(map);
        Long total=linkService.getTotal(map);
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(linkList);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);

        return null;
    }

    /**
     * title:LinkAdminController.java
     * description:添加或者修改友情链接信息
     * time:2017年1月23日 下午10:33:17
     * author:debug-steadyjack
     * @param link
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(Link link,HttpServletResponse response)throws Exception{
        //操作的记录条数
        int resultTotal=0;

        if(link.getId()==null){
            resultTotal=linkService.add(link);
        }else{
            resultTotal=linkService.update(link);
        }
        JSONObject result=new JSONObject();

        if(resultTotal>0){
            result.put("success", true);
        }else{
            result.put("success", false);
        }
        ResponseUtil.write(response, result);

        return null;
    }

    /**
     * title:LinkAdminController.java
     * description:删除友情链接信息
     * time:2017年1月23日 下午10:33:47
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
            linkService.delete(Integer.parseInt(idsStr[i]));
        }
        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);

        return null;
    }
}
