package com.valueservice.djs.db.utils;



import com.google.gson.Gson;
import com.meidusa.fastjson.JSONException;
import com.meidusa.fastjson.JSONObject;
import com.valueservice.djs.db.bean.CommonConst;
import com.valueservice.djs.db.bean.WxAccessToken;
import com.valueservice.djs.db.bean.WxX509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static java.net.Proxy.Type.HTTP;

@Component
public class MiniUtils {

    private static Logger logger = LoggerFactory.getLogger(MiniUtils.class);

    protected static Gson gson = new Gson();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    protected static RedisTemplate<String,String> stringRedisTemplate;


    /**
     * accesstoken的访问地址
     */
    private static String access_token_url
            = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static String qrcode_url
            = "String https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";

    /**
     * 获取access_token
     *
     * @return
     */
    public static WxAccessToken getAccessToken() {
        long nowTime = System.currentTimeMillis();
        final String token_key = CommonConst.MINI_PROGRAM_APPID + "_access_token";
        String obj = stringRedisTemplate.opsForValue().get(token_key);
        if(obj != null){
            WxAccessToken token = gson.fromJson(obj, WxAccessToken.class);
            logger.info("nowTime:" + nowTime + "; nextGetTime:" +token.getNextGetTime() );
            if((token.getNextGetTime() - nowTime) > 0){
                if(CheckAccessToken(token.getToken())){
                    return token;
                }
            }else{
                stringRedisTemplate.delete(token_key);
            }
        }
        logger.info("request for accessToken........");
        return requestAccessToken();
    }

    /**
     * 验证AccessToken是否有效
     */
    public static boolean CheckAccessToken(String accessToken){
        boolean result = false;
        try{
            JSONObject jsonObject = null;
            String requestUrl = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
            requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
            jsonObject = httpsRequest(requestUrl,"GET",null);
            if(jsonObject.get("errcode") == null){
                result = true;
            }
        }catch(Exception e){
            logger.error("验证AccessToken是否有效出错",e);
        }
        return result;
    }

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
                //stringRedisTemplate.opsForValue().set(CommonConst.MINI_PROGRAM_APPID + "_access_token",gson.toJson(accessToken));
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

    //https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN
    //获取小程序码并保存到本地
    public static boolean getMiniInviteQrcodeLocal(String page,Integer width,String fileName){

        String accessToken =
                //"9_3cmpdfKYrREk7m-QDKqetsCSBw4txsFfLJe5dYoyAbbbOAiPpGMPnzNwNgezSDtCilwF3IB3NeI3RMObXsQhNxb3b0MTlfO9jTcC1GPmNdMmNrmDxgICSk60y54yhwqTwys3t9B0EzpRRTIcVDJeABAIQW";//
                requestAccessToken().getToken();
        String requestUrl="https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        Map<String,Object> params = new HashMap<>();
        params.put("path",page);
        params.put("width",width);
        String json = JsonLibUtils.mapToJson(params);
        logger.info("请求二维码参数："+json);
        InputStream imgStream = httpsRequestGetStream(requestUrl, "POST", json);
        try {
            boolean result = saveStreamImage(imgStream,fileName);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getSceneQrcodeLocal(String scene,String page,Integer width,String fileName){
        String accessToken =
                //"9_3cmpdfKYrREk7m-QDKqetsCSBw4txsFfLJe5dYoyAbbbOAiPpGMPnzNwNgezSDtCilwF3IB3NeI3RMObXsQhNxb3b0MTlfO9jTcC1GPmNdMmNrmDxgICSk60y54yhwqTwys3t9B0EzpRRTIcVDJeABAIQW";//
                getAccessToken().getToken();
        String requestUrl="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        Map<String,Object> params = new HashMap<>();
        params.put("scene",scene);
        params.put("path",page);
        params.put("width",width);
        String json = JsonLibUtils.mapToJson(params);
        logger.info("请求二维码参数："+json);
        InputStream imgStream = httpsRequestGetStream(requestUrl, "POST", json);
        try {
            boolean result = saveStreamImage(imgStream,fileName);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static void main(String[] args) {
        Long time = System.currentTimeMillis();
        String pngName = "/Users/maowankui/Documents/" + time + ".png";
        getSceneQrcodeLocal("1232","pages/index/index?ksd=123",430,pngName);
        //getAccessToken();
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


    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static InputStream httpsRequestGetStream(String requestUrl, String requestMethod, String outputStr) {
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
            return inputStream ;
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean saveStreamImage(InputStream instreams,String filename) throws FileNotFoundException {

        boolean result = true;
        File file=new File(filename);//可以是任何图片格式.jpg,.png等
        FileOutputStream fos=new FileOutputStream(file);
        if(instreams != null){
            try {

                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }

            } catch (Exception e) {
                result = false;
                logger.error("read streams error :",e);
            } finally {

                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @PostConstruct
    public void setRedisTemplate() {
        MiniUtils.stringRedisTemplate = redisTemplate;
    }


}
