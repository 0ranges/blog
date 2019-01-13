package steadyjack.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * title:ResponseUtil.java
 * description: 将数据对象Object写回响应流（可用于js异步调用）
 * time:2017年1月16日 下午10:52:43
 * author:debug-steadyjack
 */
public class ResponseUtil {

    /**
     * title:ResponseUtil.java
     * description: 将数据对象Object写回响应流
     * time:2017年1月16日 下午10:53:23
     * author:debug-steadyjack
     * @param response
     * @param o
     * @throws Exception
     */
    public static void write(HttpServletResponse response,Object o)throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        out.println(o.toString());
        out.flush();
        out.close();
    }
}
