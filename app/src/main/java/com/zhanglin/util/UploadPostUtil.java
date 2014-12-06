package com.zhanglin.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ContentHandler;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhanglin on 14-11-27.
 * 上传器\n
 * 上传器有个对外公开的数据 hasUpload 表示已经上传的字节数
 */
public class UploadPostUtil {
    public int hasUpload;
    private String urlPath;
    private Map<String,String> params;
    private File[] files;

    public UploadPostUtil(File[] files, String urlPath) {
        this.files = files;
        this.urlPath = urlPath;
        hasUpload = 0;
    }

    public UploadPostUtil(String urlPath, Map<String, String> params) {

        this.urlPath = urlPath;
        this.params = params;
        hasUpload = 0;
    }

    /**
     * 初始化构造函数，如果新建一个没有任何参数的下载器，\n
     * 则将三个参数设置为空
     */
    public UploadPostUtil() {
        urlPath = null;
        params = null;
        files = null;
        hasUpload = 0;
    }

    /**
     * 构造函数
     * @param urlPath 上传的地址
     * @param params 要上传的文本类型的键值对，Map<String, String>，前面是name，后面的是value
     * @param files 要上传的文件名和文件对，Map<String, File>，前面是文件名，后面是文件
     */
    public UploadPostUtil(String urlPath, Map<String, String> params, File[] files) {
        this.urlPath = urlPath;
        this.params = params;
        this.files = files;
        hasUpload = 0;
    }

    /**
     * 实现功能的上传函数
     * @return 得到服务器返回的inputStream
     * @throws Throwable 一旦中间有任何一个位置出错，则抛出异常
     */
    public InputStream uploadByPost() throws Throwable {
        String PREFIX = "--";
        String BOUNDARY = PREFIX + UUID.randomUUID().toString();
        String ENDLINE = "\r\n";
        String LINEBEGIN = PREFIX + BOUNDARY + ENDLINE;//传送一段信息的开头
        String TOTALEND = PREFIX + BOUNDARY + PREFIX + ENDLINE;//总的结尾
        HttpURLConnection connection = GetHttpURLConnection.getConnection(urlPath);
        if (connection == null) {
            return null;
        }//如果connection获取到为空，则退出下载；
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        connection.setRequestProperty("Charsert", "utf-8");
        //新建缓存字符
        StringBuffer stringBuffer = new StringBuffer();
        for (String key : params.keySet()) {
            stringBuffer.append(LINEBEGIN);
            stringBuffer.append("Content-Disposition: form-data; name=\"" + key + "\"" + ENDLINE);
            stringBuffer.append(ENDLINE);
            //注意在上面那个换行之后还需要加一个回车和换行符
            stringBuffer.append(params.get(key) + ENDLINE);
        }
        String finishedString = stringBuffer.toString();
        stringBuffer.delete(0, stringBuffer.length());
        OutputStream outputStream = null;
        try {
            outputStream = connection.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputStream.write(finishedString.getBytes());
        //发送文件数据
        if (files != null) {
            for (File file : files) {
                stringBuffer.append(LINEBEGIN);
                stringBuffer.append("Content-Disposition: form-data; name=\"files\"; filename=\""+ file.getName() + "\"" + ENDLINE);
                stringBuffer.append("Content-Type: application/octet-stream;" + ENDLINE);
                stringBuffer.append(ENDLINE);
                //注意在上面那个换行之后还需要加一个回车和换行符
                outputStream.write(stringBuffer.toString().getBytes());
                InputStream inputStream = new FileInputStream(file);
                int length = 0;
                byte[] buffer = new byte[1024];
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer);
                    hasUpload += length;
                }
                inputStream.close();
                outputStream.write(ENDLINE.getBytes());
                stringBuffer.delete(0, stringBuffer.length());
                //上面这个delete的函数是从start开始删除到end-1的位置，所以清空应该是0-stringBuffer.length()
            }
        }
        outputStream.write(TOTALEND.getBytes());
        outputStream.flush();
        outputStream.close();
        if (connection.getResponseCode() == 200) {
            InputStream inputStream = connection.getInputStream();
            return inputStream;
    }
        return null;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }
}
