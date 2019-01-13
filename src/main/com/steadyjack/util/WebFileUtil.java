package steadyjack.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

/**
 * title:WebFileUtil.java
 * description:文件操作(包括网络上的文件)工具类
 * time:2017年1月22日
 * 下午9:41:57
 * author:debug-steadyjack
 */
public class WebFileUtil {

    /**
     * title:WebFileUtil.java
     * description:获取web运行时项目的根路径
     * time:2017年1月22日 下午10:00:08
     * author:debug-steadyjack
     * @param request
     * @return
     */
    public static String getSystemRootPath(HttpServletRequest request){
        String rootPath=request.getServletContext().getRealPath("/");
        //request.getContextPath();
        return rootPath;
    }

    /**
     * title:WebFileUtil.java
     * description:下载文件(包括网络文件)
     * time:2017年1月22日 下午9:59:24
     * author:debug-steadyjack
     * @param request
     * @param response
     * @param url 文件路径
     * @param fileName 文件名
     */
    public static void downloadFile(HttpServletRequest request,HttpServletResponse response,
                                    String url,String fileName) {

        String root = getSystemRootPath(request);

        //用给定的fileName与url的后缀名拼成一新的字符串作为新的文件名
        fileName = fileName + url.substring(url.lastIndexOf("."));

        OutputStream os = null;
        InputStream fis = null;
        try {
            //解决中文 文件名问题
            fileName = new String(fileName.getBytes(), "iso8859-1");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=\""+ fileName + "\"");

            os=response.getOutputStream();
            if (url.startsWith("http:")) {
                URL tmpURL = new URL(url);
                URLConnection conn = tmpURL.openConnection();
                fis = conn.getInputStream();
            } else {
                fis = new FileInputStream(new File(root + url));
            }

            byte[] b = new byte[1024];
            int i = 0;

            while ((i = fis.read(b)) > 0) {
                os.write(b, 0, i);
            }

            os.flush();
        } catch (Exception e) {
            System.out.println("下载文件发生异常: "+e.getMessage());
        } finally{
            try {
                os.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * title:WebFileUtil.java
     * description:创建文件夹
     * time:2017年2月6日 下午10:50:17
     * author:debug-steadyjack
     * @param dFile
     */
    public static void createFold(File file) {
        if (!file.exists()) file.mkdirs();
    }

    /**
     * title:WebFileUtil.java
     * description:复制百度编辑器中的图片到指定的文件夹下，并返回图片存储的实际路径
     * time:2017年2月6日 下午10:58:58
     * author:debug-steadyjack
     * @param request
     * @param srcPath
     * @param floder
     * @return
     */
    public static String copyFileForUeditor(HttpServletRequest request,String srcPath, String folder){

        String newFolder="";
        String fileName="";

        try {
            File oldFile=new File(getSystemRootPath(request)+srcPath);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

            newFolder="/files/"+folder+"/"+sdf.format(new Date())+"/";
            String newFilePath=getSystemRootPath(request)+newFolder;
            createFold(new File(newFilePath));

            fileName=oldFile.getName();
            File newFile=new File(newFilePath+fileName);
            FileCopyUtils.copy(oldFile, newFile);
            //FileUtils.copyFile(oldFile, saveFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newFolder+fileName;
    }


    /**
     * title:WebFileUtil.java
     * description:删除给定路径下的文件(包括单独的文件;文件夹)
     * time:2017年2月6日 下午11:02:12
     * author:debug-steadyjack
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean deleteFilePath(String path) throws Exception {

        try {
            File file = new File(path);

            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
            if(!file.isDirectory()){
                file.delete();
            }else if(file.isDirectory()){
                String[] filelist = file.list();

                for(int i = 0; i < filelist.length; i++) {
                    File delfile = new File(path + "/" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                    } else if (delfile.isDirectory()) {
                        deleteFilePath(path + "/" + filelist[i]);
                    }
                }

                file.delete();
            }
        } catch (Exception e) {
        }

        return true;
    }

}
