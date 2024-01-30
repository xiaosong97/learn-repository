package com.ekkosong.protocol;

import com.ekkosong.common.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {
    public String send(String hostName, Integer port, Invocation invocation) {
        // 同样可以根据用户配置来选择不同的客户端实现
        try {
            URL url = new URL("http", hostName, port, "/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            // 配置
            OutputStream outputStream = httpURLConnection.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

            oos.writeObject(invocation);
            oos.flush();
            oos.close();

            // InputStream inputStream = httpURLConnection.getInputStream();
            int status = httpURLConnection.getResponseCode();
            System.out.println("status = " + status);
            String result = "";
            if (status == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                result += IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            } else {
                InputStream error = httpURLConnection.getErrorStream();
                result += "error: " + error.toString();
            }

            return result;
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件" + e);
        } catch (IOException e) {
            System.out.println("其他IO异常" + e);
        }
        return null;
    }
}
