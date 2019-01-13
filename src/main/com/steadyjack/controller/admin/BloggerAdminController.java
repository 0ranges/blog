package com.steadyjack.controller.admin;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import steadyjack.entity.Blogger;
import steadyjack.service.BloggerService;
import steadyjack.util.CryptographyUtil;
import steadyjack.util.DateUtil;
import steadyjack.util.ResponseUtil;
import steadyjack.util.WebFileUtil;

/**
 * title:BloggerAdminController.java
 * description:管理员博主Controller层
 * time:2017年1月22日 下午10:27:50
 * author:debug-steadyjack
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

    @Resource
    private BloggerService bloggerService;

    /**
     * title:BloggerAdminController.java
     * description:修改博主信息 - 异步
     * time:2017年1月22日 下午10:28:11
     * author:debug-steadyjack
     * @param imageFile
     * @param blogger
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(@RequestParam("imageFile") MultipartFile imageFile,Blogger blogger,HttpServletRequest request,HttpServletResponse response)throws Exception{
        if(!imageFile.isEmpty()){
            //对上传的文件(个人头像)进行处理:文件命名,转为File
            String filePath=WebFileUtil.getSystemRootPath(request);
            String imageName=DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
            blogger.setImageName(imageName);
        }
        int resultTotal=bloggerService.update(blogger);
        StringBuffer result=new StringBuffer();
        if(resultTotal>0){
            result.append("<script language='javascript'>alert('修改成功！');</script>");
        }else{
            result.append("<script language='javascript'>alert('修改失败！');</script>");
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * title:BloggerAdminController.java
     * description:查询博主信息 - 异步
     * time:2017年1月22日 下午10:41:45
     * author:debug-steadyjack
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/find")
    public String find(HttpServletResponse response)throws Exception{
        Blogger blogger=bloggerService.find();
        JSONObject jsonObject=JSONObject.fromObject(blogger);
        ResponseUtil.write(response, jsonObject);

        return null;
    }

    /**
     * title:BloggerAdminController.java
     * description:修改博主密码
     * time:2017年1月22日 下午11:18:42
     * author:debug-steadyjack
     * @param newPassword
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(String newPassword,HttpServletResponse response)throws Exception{
        Blogger blogger=new Blogger();
        blogger.setPassword(CryptographyUtil.md5(newPassword, "debug"));
        int resultTotal=bloggerService.update(blogger);
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
     * title:BloggerAdminController.java
     * description:注销-退出登录
     * time:2017年1月22日 下午11:18:17
     * author:debug-steadyjack
     * @return
     * @throws Exception
     */
    @RequestMapping("/logout")
    public String  logout()throws Exception{
        SecurityUtils.getSubject().logout();
        return "redirect:/login.jsp";
    }
}
