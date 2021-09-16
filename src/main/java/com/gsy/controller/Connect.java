package com.gsy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Connect {
    CarHandshakeTool cht;
    String vin;
    String firstHandshake_url = "http://192.168.98.42:9008/platform/firstHandshake";
    String secondHandShake_url = "http://192.168.98.42:9008/platform/secondHandShake";
    String sendMessage_url = "sendMessage_url";

    public Connect(CarHandshakeTool cht, String vin) {
        this.cht = cht;
        this.vin = vin;
    }

    /*
车辆激活阶段，获取车辆需要的相关密钥
*/
    public Map<String, String> get_param(String vin) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        String url = "http://49.233.2.68:9006/keys/getAll/" + vin;
        HttpGet httpGet = new HttpGet(url);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            //System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                //System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                String result = EntityUtils.toString(response.getEntity());
                // 生成 JSON 对象
                JSONObject obj = JSONObject.parseObject(result);
                String data = obj.get("data").toString();
                Map<String, String> cipher_map = JSON.parseObject(data, HashMap.class);
                cipher_map.put("message", obj.get("message").toString());
                cipher_map.put("status", obj.get("status").toString());
                //System.out.println("响应内容为:" + result);
                return cipher_map;
            }
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void conn_fh(Map<String, String> cipher_map) {
        String url = firstHandshake_url;
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);

        Map<String, String> map = cht.send_map_first(vin);
        //将Object转换为json字符串
        String jsonString = JSON.toJSONString(map);

        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        //响应模型
        CloseableHttpResponse response = null;

        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            //System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                //System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                String result = EntityUtils.toString(response.getEntity());
                Map<String, String> firstReceive_map = JSON.parseObject(result, HashMap.class);
                //System.out.println("响应内容为:" + result);
                Map<String, String> df_map = cht.dealFirst(firstReceive_map, firstReceive_map.get("clientRandom"), firstReceive_map.get("vinCode"),
                        cipher_map.get("signPrivateKeyStr"), cipher_map.get("exchangePrivateKeyStr"), cipher_map.get("masterEncryptKeyStr"),
                        cipher_map.get("masterSignKeyStr"));
                conn_sh(df_map, firstReceive_map.get("vinCode"));
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void conn_sh(Map<String, String> df_map, String vinCode) {
        String url = secondHandShake_url;
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        //将Object转换为json字符串
        String jsonString = JSON.toJSONString(df_map);

        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        //响应模型
        CloseableHttpResponse response = null;

        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            //System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                //.out.println("响应内容长度为:" + responseEntity.getContentLength());
                String result = EntityUtils.toString(response.getEntity());
                Map<String, String> secondReceive_map = JSON.parseObject(result, HashMap.class);
                //System.out.println("响应内容为:" + result);
                boolean flag = cht.dealSecond(df_map, vinCode);
                System.out.println("dealSecond result:" + flag);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void sendMessage(String message) {
        String url = sendMessage_url;
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        //将Object转换为json字符串
        String jsonString = JSON.toJSONString(message);

        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        //响应模型
        CloseableHttpResponse response = null;

        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            //System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                //.out.println("响应内容长度为:" + responseEntity.getContentLength());
                String result = EntityUtils.toString(response.getEntity());
                Map<String, String> secondReceive_map = JSON.parseObject(result, HashMap.class);
                //System.out.println("响应内容为:" + result);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
