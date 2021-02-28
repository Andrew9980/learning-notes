package com.andrew.util;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtil {
   public static String doGet(String url) {
      HttpGet httpGet = new HttpGet(url);
      try {
         return execute(httpGet);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }

   public static String doPost(String url, Map<String, String> param) {
      HttpPost httpPost = new HttpPost(url);
      List<BasicNameValuePair> arrayList = new ArrayList<>();
      Set<String> keySet = param.keySet();
      for (String key : keySet) {
         arrayList.add(new BasicNameValuePair(key, param.get(key)));
      }
      try {
         httpPost.setEntity(new UrlEncodedFormEntity(arrayList));
         return execute(httpPost);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }

   private static String execute(HttpRequestBase request) throws IOException {
      CloseableHttpClient httpClient = HttpClients.createDefault();
      CloseableHttpResponse response = httpClient.execute(request);
      if (200 == response.getStatusLine().getStatusCode()) {
         return EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
      } else {
         System.out.println(EntityUtils.toString(response.getEntity(), Charset.forName("utf-8")));
      }
      return "";
   }

}