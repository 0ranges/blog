package steadyjack.util;

import java.util.ArrayList;
import java.util.List;

/**
 * title:StringUtil.java
 * description:字符串简单处理工具类
 * time:2017年1月16日 下午10:44:02
 * author:debug-steadyjack
 */
public class StringUtil {

    /**
     * title:StringUtil.java
     * description:判断是否是空
     * time:2017年1月16日 下午10:44:21
     * author:debug-steadyjack
     * @param str
     * @return boolean
     */
    public static boolean isEmpty(String str){
        if(str==null || "".equals(str.trim())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * title:StringUtil.java
     * description:判断是否不是空
     * time:2017年1月16日 下午10:44:44
     * author:debug-steadyjack
     * @param str
     * @return boolean
     */
    public static boolean isNotEmpty(String str){
        if((str!=null)&&!"".equals(str.trim())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * title:StringUtil.java
     * description: 格式化模糊查询
     * time:2017年1月16日 下午10:45:09
     * author:debug-steadyjack
     * @param str
     * @return String
     */
    public static String formatLike(String str){
        if(isNotEmpty(str)){
            return "%"+str+"%";
        }else{
            return null;
        }
    }

    /**
     * title:StringUtil.java
     * description:过滤掉集合里的空格
     * time:2017年1月16日 下午10:45:34
     * author:debug-steadyjack
     * @param list
     * @return List<String>
     */
    public static List<String> filterWhite(List<String> list){
        List<String> resultList=new ArrayList<String>();
        for(String str:list){
            if(isNotEmpty(str)){
                resultList.add(str);
            }
        }
        return resultList;
    }

}
