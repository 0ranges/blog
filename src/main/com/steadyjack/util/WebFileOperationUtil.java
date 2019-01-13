package steadyjack.util;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebFileOperationUtil {

    /**
     * title:WebFileOperationUtil.java
     * description:将百度编辑器的内容的图片移动到指定的位置，并将新的位置替换内容中对于的旧位置
     * time:2017年2月7日 下午7:17:35
     * author:debug-steadyjack
     * @param request
     * @param content
     * @return
     */
    public static String copyImageInUeditor(HttpServletRequest request,String content){
        if (StringUtil.isNotEmpty(content)) {
            Document doc=Jsoup.parse(content);

            Elements imageList=doc.select("img");
            if (imageList!=null && imageList.size()>0) {
                for(int i=0;i<imageList.size();i++){
                    Element image=imageList.get(i);
                    String oldImage=image.toString();
                    System.out.println(oldImage);

                    String charIndex="/temporary";
                    int index=oldImage.indexOf(charIndex);
                    if (index>0) {
                        String srcImage=oldImage.substring(index);
                        String secIndex="\"";
                        String realImagePos=srcImage.substring(0,srcImage.indexOf(secIndex));

                        String folder="ueditor";
                        String newImagePos=WebFileUtil.copyFileForUeditor(request, realImagePos, folder);
                        content = content.replace(realImagePos, newImagePos);
                    }
                }
            }
        }

        return content;
    }

    /**
     * title:WebFileOperationUtil.java
     * description:删除百度编辑器内容的图片
     * time:2017年2月7日 下午9:01:23
     * author:debug-steadyjack
     * @param request
     * @param content
     * @throws Exception
     */
    public static void deleteImagesInUeditor(HttpServletRequest request,String content) throws Exception{
        if (StringUtil.isNotEmpty(content)) {
            Document doc=Jsoup.parse(content);

            Elements imageList=doc.select("img");
            if (imageList!=null && imageList.size()>0) {
                for(int i=0;i<imageList.size();i++){
                    Element image=imageList.get(i);
                    String oldImage=image.toString();

                    //图片src必定以 " 开始,以 " 结束
                    String charIndex="\"";
                    int index=oldImage.indexOf(charIndex);
                    if (index>0) {
                        String srcImage=oldImage.substring(index+1);
                        String delImagePos=srcImage.substring(0,srcImage.indexOf(charIndex));
                        System.out.println(delImagePos);

                        WebFileUtil.deleteFilePath(WebFileUtil.getSystemRootPath(request)+delImagePos);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        String image="img src=\"/temporary/2017-02-06/image/1486394487364095169.jpg\"";
        String str="/temporary/2017-02-06/image/1486394487364095169.jpg"; //51

        String charIndex="/temporary";
        int index=image.indexOf(charIndex);
        System.out.println(index);
        String newStr=image.substring(index, str.length()+index);
        System.out.println(newStr);
    }

}
