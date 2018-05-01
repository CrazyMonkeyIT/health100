package com.valueservice.djs.db.utils;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.valueservice.djs.db.bean.CommonConst;
import com.valueservice.djs.db.bean.WxAccessToken;
import com.valueservice.djs.db.bean.WxX509TrustManager;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MiniUtils {

    /**
     * accesstoken的访问地址
     */
    private static String access_token_url
            = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static String qrcode_url
            = "String https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";

    private static WxAccessToken requestAccessToken(){
        long nowTime = System.currentTimeMillis();
        String requestUrl = access_token_url.replace("APPID", CommonConst.MINI_PROGRAM_APPID)
                .replace("APPSECRET", CommonConst.MINI_PROGRAM_APPSECRET);
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        WxAccessToken accessToken = new WxAccessToken();
        if(null != jsonObject){
            try {
                long nextGetTime  = (long) (jsonObject.getIntValue("expires_in") * 750) + nowTime;//下次获取时间，90分钟以后
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
                accessToken.setNextGetTime(nextGetTime);
                accessToken.setAppid(CommonConst.MINI_PROGRAM_APPID);
                accessToken.setAppsecret(CommonConst.MINI_PROGRAM_APPSECRET);
            } catch (JSONException e) {
                accessToken = null;

            }
        }
        return accessToken;
    }

    public static String getMiniInviteQrcode(String scene,String page,Integer width){

        String accessToken = requestAccessToken().getToken();
        String requestUrl="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        Map<String,Object> params = new HashMap<>();
        params.put("scene",scene);
        params.put("page",page);
        params.put("width",width);
        String json = JsonLibUtils.mapToJson(params);
        JSONObject jsonObject = httpsRequest(requestUrl, "POST", json);
        int result = jsonObject.getInteger("errcode");
        return null;
    }

    public static void main(String[] args) {
        getMiniInviteQrcode("1232","pages/index/index",430);
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = new JSONObject();
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new WxX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
