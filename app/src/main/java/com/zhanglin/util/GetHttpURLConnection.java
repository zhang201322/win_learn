package com.zhanglin.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhanglin on 14-11-27.
 */
public class GetHttpURLConnection {
    /**
     * 静态方法，获取到指定url的HttpURLConnection\n
     * 并且设置最大链接时间为5秒
     * @param urlPath 指定的url
     * @return 获取到的HttpURLConnection，如果没有获取到或者获取出错返回null
     */
    public static HttpURLConnection getConnection(String urlPath) {
        try {
            URL url = new URL(urlPath);
            try {
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setConnectTimeout(5000);
                return connection;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
