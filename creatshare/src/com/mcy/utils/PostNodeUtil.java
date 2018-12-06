package com.mcy.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PostNodeUtil {
    private String code = null;
    private Boolean chunked = false;
    public void post(String tel) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost("https://sms.yunpian.com/v2/sms/single_send.json");
        // 创建参数队列
        List list = new ArrayList();
        list.add(new BasicNameValuePair("apikey", "ba14b5ed1abebac0040aade7c6506805"));
        list.add(new BasicNameValuePair("text", "【马晨阳】您的验证码是" + getPost()));
        list.add(new BasicNameValuePair("mobile", tel));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    JSONObject jsonObject = JSONObject.fromObject(entity);
                    chunked = jsonObject.get("chunked").toString().equals("true");
                }
            } finally {
                httpclient.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String getPost() {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            stringBuffer.append(random.nextInt(9));
        }
        code = stringBuffer.toString();
        return stringBuffer.toString();
    }
    public String getCode()
    {
        return code;
    }
    public boolean getChunked()
    {
        return chunked;
    }

    public static void main(String[] args) {
        new PostNodeUtil().post("15136010213");
    }
}
