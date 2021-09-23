package com.wmy.dygjspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @project_name: dygj-spring
 * @package_name: com.wmy.dygjspring.controller
 * @Author: wmy
 * @Date: 2021/9/23
 * @Major: 数据科学与大数据技术
 * @Post：大数据实时开发
 * @Email：wmy_2000@163.com
 * @Desription:
 * @Version: wmy-version-01
 */
public class Demo1 {
    public static String getPost(URL url, Map<String,Object> params){
        String str=null;
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Encoding","identity");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();

            PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));
            pw.write(objectMapper.writeValueAsString(params));
            pw.flush();
            pw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            StringBuilder result = new StringBuilder();
            // 读取数据
            if(connection.getResponseCode() == HttpStatus.SC_OK){
                int contentLength = connection.getContentLength();
                System.out.println("contentLength:"+contentLength);
                System.out.println("ResponseCode:"+connection.getResponseCode());
                if(contentLength > 0){
                    while ((line = br.readLine()) != null) {
                        result.append(line + "\n");
                    }
                }else{
                    result.append("{\"msg\":\"响应体为空\",\"code\":\"-1\"}");
                }
            }
//            connection.disconnect();
            System.out.println("result:"+result.toString());
            str=result.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static void main(String[] args) throws IOException {
        Map<String,Object> map = new HashMap();
        map.put("id", "1");
        map.put("name", "wmy");
        map.put("age", "21");

        int count = 0;
        for (int i = 0; i < 10000000; i++) {
            try {
                map.put("id", i);
                map.put("name", i);
                map.put("age", i);
                getPost(new URL("http://localhost:8081/applog"), map);
            } catch (MalformedURLException e) {
                System.out.println("count >>> " + count);
                throw new RuntimeException(e);
            }
        }
    }
}