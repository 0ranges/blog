package steadyjack.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * title:CryptographyUtil.java
 * description:加密工具(主要是shiro的md5)
 * time:2017年1月16日 下午10:55:01
 * author:debug-steadyjack
 */
public class CryptographyUtil {

    /**
     * title:CryptographyUtil.java
     * description:Md5加密
     * time:2017年1月16日 下午10:55:20
     * author:debug-steadyjack
     * @param str 密码
     * @param salt 盐
     * @return
     */
    public static String md5(String str,String salt){
        return new Md5Hash(str,salt).toString();
    }

    public static void main(String[] args) {
        String password="linsen";

        System.out.println("Md5加密："+CryptographyUtil.md5(password, "debug"));
    }
}
