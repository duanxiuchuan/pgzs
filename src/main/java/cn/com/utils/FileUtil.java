package cn.com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author LiDaDa
 */
public class FileUtil {
    public static void copy(InputStream input ,OutputStream os) {
        try {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }
           
        }catch(Exception ex) {
            throw new RuntimeException("文件复制出错"+ex);
        }
        finally {
            try {
                input.close();
                os.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }
}
