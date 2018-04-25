package com.valueservice.djs.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.valueservice.djs.bean.EncryptUserInfo;
import com.valueservice.djs.db.bean.CommonConst;
import org.apache.tomcat.util.codec.binary.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

public class WechatUserEncrypt {

    /**
     * 内置的小程序id
     */
    public static String appid = "";


    /**
     * 解密微信小程序返回的加密用户信息，返回一个JSONObject供调用者自由取用对象数据
     * @param session_key 小程序的sessionkey
     * @param encryptedData
     * @param iv
     * @return
     */
    public static JSONObject userInfoEncrypt(String session_key, String encryptedData,String iv){

        Map map = new HashMap();
        try {
            byte[] resultByte  = AES.decrypt(Base64.decodeBase64(encryptedData),
                    Base64.decodeBase64(session_key),
                    Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                String userInfo = new String(resultByte, "UTF-8");
                JSONObject userInfoJson = JSON.parseObject(userInfo);
                return userInfoJson;
            }else{
                return null;
            }
        }catch (InvalidAlgorithmParameterException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 通过微信wx.login接口拿到的授权code去获取用户的加密信息
     * @param code
     * @return
     */
    public static JSONObject wechatAuth(String code){
        Map<String, String> param = new HashMap<>();
        param.put("appid", CommonConst.MINI_PROGRAM_APPID);
        param.put("secret",CommonConst.MINI_PROGRAM_APPSECRET);
        param.put("js_code",code);
        param.put("grant_type", CommonConst.MINI_PORGRAM_GRANT_TYPE);
        String authResult = HttpClientUtil.doGet(CommonConst.MINI_PROGRAM_AUTH_URL,param);
        return JSON.parseObject(authResult);
    }

    /**
     * 通过微信wx.login接口拿到的授权code去获取用户信息
     * @param code
     * @return
     */
    public static EncryptUserInfo getEncryptUserInfo(String code,String iv,String encryptedData){
        JSONObject wechatAuthJson = wechatAuth(code);
        String sessionKey = wechatAuthJson.getString("session_key");
        JSONObject userInfoJson = userInfoEncrypt(sessionKey,encryptedData,iv);
        EncryptUserInfo encryptUserInfo = new EncryptUserInfo();
        encryptUserInfo.setOpenId(userInfoJson.getString("openId"));
        encryptUserInfo.setAvatarUrl(userInfoJson.getString("avatarUrl"));
        encryptUserInfo.setCity(userInfoJson.getString("city"));
        encryptUserInfo.setCountry(userInfoJson.getString("country"));
        encryptUserInfo.setGender(userInfoJson.getString("gender"));
        encryptUserInfo.setNickName(userInfoJson.getString("nickName"));
        encryptUserInfo.setProvince(userInfoJson.getString("province"));
        return encryptUserInfo;

    }
}
