package cn.com.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * <p>TITLE： </p>
 * <p>DESCRIPTION： </p>
 *
 * @version 1.0
 * Created on 2019/1/14
 * Copyright  2019LiDaDa. All rights reserved.
 * @author: LJC
 */
public class FileUtils {
    /**
     * The Unix separator character.
     */
    public static final char UNIX_SEPARATOR = '/';

    /**
     * The extension separator character.
     */
    public static final char EXTENSION_SEPARATOR = '.';

    /**
     * 文件上传的路径
     */
    private static final String UPLOADDIR = "files";



    /**
     * 文件上传路径（不同类型文件不同路径）
     */
    public static final String DEFAULT_PATH = UNIX_SEPARATOR + UPLOADDIR + UNIX_SEPARATOR + "default" + UNIX_SEPARATOR;
    public static final String IMAGE_PATH = UNIX_SEPARATOR + UPLOADDIR + UNIX_SEPARATOR + "image" + UNIX_SEPARATOR;
    public static final String VOICE_PATH = UNIX_SEPARATOR + UPLOADDIR + UNIX_SEPARATOR + "voice" + UNIX_SEPARATOR;
    public static final String VEDIO_PATH = UNIX_SEPARATOR + UPLOADDIR + UNIX_SEPARATOR + "vedio" + UNIX_SEPARATOR;
    public static final String HEAD_PATH = UNIX_SEPARATOR + UPLOADDIR + UNIX_SEPARATOR + "head" + UNIX_SEPARATOR;

    /**
     * 上传图片存储
     * image/2017/0228/1983782.jpg
     *
     * @param extension
     * 			文件后缀名
     * @param tempPath
     * 			文件中间路径（/files/[path]/20180211/...）
     */
    public static String gainFilename(String extension, String tempPath) {
        return tempPath + DateUtils.format(new Date(), "yyyyMMdd") + UNIX_SEPARATOR + UUID.randomUUID() + EXTENSION_SEPARATOR + extension;
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr, File file) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        // 10分钟超时
        conn.setReadTimeout(10 * 60 * 1000);

        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        // 获取自己数组
        byte[] getData = readInputStream(inputStream);

        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        // 文件是否存在
        if (!file.isFile()) {
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        if (conn != null) {
            conn.disconnect();
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
