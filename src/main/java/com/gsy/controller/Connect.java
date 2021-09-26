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
        String get_param_url = "http://49.233.2.68:9006/keys/getAll/";
        String firstHandshake_url = "http://49.233.2.68:9008/platform/firstHandshake";
        String secondHandShake_url = "http://49.233.2.68:9008/platform/secondHandshake";
        String sendMessage_url = "http://49.233.2.68:9008/postMap";
/*    String get_param_url = "http://192.168.12.134:9006/keys/getAll/";
    String firstHandshake_url = "http://192.168.12.134:9008/platform/firstHandshake";
    String secondHandShake_url = "http://192.168.12.134:9008/platform/secondHandshake";
    String sendMessage_url = "http://192.168.12.134:9008/postMap";*/

    public Connect(CarHandshakeTool cht, String vin) {
        this.cht = cht;
        this.vin = vin;
    }

    /*
��������׶Σ���ȡ������Ҫ�������Կ
*/
    public Map<String, String> get_param(String vin) {
        // ���Http�ͻ���(�������Ϊ:�������һ�������;ע��:ʵ����HttpClient��������ǲ�һ����)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // ����Get����
        String url = get_param_url + vin;
        HttpGet httpGet = new HttpGet(url);

        // ��Ӧģ��
        CloseableHttpResponse response = null;
        try {
            // �ɿͻ���ִ��(����)Get����
            response = httpClient.execute(httpGet);
            // ����Ӧģ���л�ȡ��Ӧʵ��
            HttpEntity responseEntity = response.getEntity();
            //System.out.println("��Ӧ״̬Ϊ:" + response.getStatusLine());
            if (responseEntity != null) {
                //System.out.println("��Ӧ���ݳ���Ϊ:" + responseEntity.getContentLength());
                String result = EntityUtils.toString(response.getEntity());
                // ���� JSON ����
                JSONObject obj = JSONObject.parseObject(result);
                String data = obj.get("data").toString();
                Map<String, String> cipher_map = JSON.parseObject(data, HashMap.class);
                cipher_map.put("message", obj.get("message").toString());
                cipher_map.put("status", obj.get("status").toString());
                System.out.println("get_param��Ӧ����Ϊ:" + cipher_map);
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
                // �ͷ���Դ
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
        // ���Http�ͻ���
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // ����Post����
        HttpPost httpPost = new HttpPost(url);
        Map<String, String> map = cht.send_map_first(vin);
        System.out.println("cht.send_map_first:" + map);
        //��Objectת��Ϊjson�ַ���
        String jsonString = JSON.toJSONString(map);

        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        // post�����ǽ������������������洫��ȥ��;���ｫentity����post��������
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        //��Ӧģ��
        CloseableHttpResponse response = null;

        try {
            // �ɿͻ���ִ��(����)Post����
            response = httpClient.execute(httpPost);
            // ����Ӧģ���л�ȡ��Ӧʵ��
            HttpEntity responseEntity = response.getEntity();

            //System.out.println("��Ӧ״̬Ϊ:" + response.getStatusLine());
            if (responseEntity != null) {
                //System.out.println("��Ӧ���ݳ���Ϊ:" + responseEntity.getContentLength());
                String result = EntityUtils.toString(response.getEntity());

                Map<String, String> firstReceive_map = JSON.parseObject(result, HashMap.class);
                System.out.println("conn_fh��Ӧ����Ϊ:" + firstReceive_map);
                Map<String, String> df_map = cht.dealFirst(firstReceive_map, firstReceive_map.get("clientRandom"), firstReceive_map.get("vinCode"),
                        cipher_map.get("signPrivateKeyStr"), cipher_map.get("exchangePrivateKeyStr"), cipher_map.get("masterEncryptKeyStr"),
                        cipher_map.get("masterSignKeyStr"));
                System.out.println("cht.dealFirst:" + df_map);
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
                // �ͷ���Դ
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
        // ���Http�ͻ���
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // ����Post����
        HttpPost httpPost = new HttpPost(url);
        //��Objectת��Ϊjson�ַ���
        String jsonString = JSON.toJSONString(df_map);

        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        // post�����ǽ������������������洫��ȥ��;���ｫentity����post��������
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        //��Ӧģ��
        CloseableHttpResponse response = null;

        try {
            // �ɿͻ���ִ��(����)Post����
            response = httpClient.execute(httpPost);
            // ����Ӧģ���л�ȡ��Ӧʵ��
            HttpEntity responseEntity = response.getEntity();

            //System.out.println("��Ӧ״̬Ϊ:" + response.getStatusLine());
            if (responseEntity != null) {
                //System.out.println("��Ӧ���ݳ���Ϊ:" + responseEntity.getContentLength());
                String result = EntityUtils.toString(response.getEntity());
                System.out.println("conn_sh_result:" + result);
                Map<String, String> secondReceive_map = JSON.parseObject(result, HashMap.class);
                System.out.println("conn_sh��Ӧ����Ϊ:" + secondReceive_map);
                boolean flag = cht.dealSecond(secondReceive_map, vinCode);
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
                // �ͷ���Դ
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

        Map<String, String> message_map = new HashMap<>();
        message_map.put("message", message);
        String url = sendMessage_url;
        // ���Http�ͻ���
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // ����Post����
        HttpPost httpPost = new HttpPost(url);
        //��Objectת��Ϊjson�ַ���
        String jsonString = JSON.toJSONString(message_map);

        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        // post�����ǽ������������������洫��ȥ��;���ｫentity����post��������
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        //��Ӧģ��
        CloseableHttpResponse response = null;

        try {
            // �ɿͻ���ִ��(����)Post����
            response = httpClient.execute(httpPost);
            // ����Ӧģ���л�ȡ��Ӧʵ��
            HttpEntity responseEntity = response.getEntity();

            //System.out.println("��Ӧ״̬Ϊ:" + response.getStatusLine());
            if (responseEntity != null) {
                //System.out.println("��Ӧ���ݳ���Ϊ:" + responseEntity.getContentLength());
                String result = EntityUtils.toString(response.getEntity());
                //Map<String, String> secondReceive_map = JSON.parseObject(result, HashMap.class);
                System.out.println("��Ӧ����Ϊ:" + result);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // �ͷ���Դ
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
