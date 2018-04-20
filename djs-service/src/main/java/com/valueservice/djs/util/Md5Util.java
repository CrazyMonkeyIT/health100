package com.valueservice.djs.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Yang.dd on 2014/11/14.
 */
public class Md5Util {

    /**
     * md5加密
     * @param str 明文
     * @param bit 16或32
     * @param capital   true：转大写，false 小写
     * @return
     */
    public  static String encryption(String str,int bit,boolean capital){
        String result=core(str);
        if(bit==16){
            result = result.substring(8,24);
        }
        if(capital){
            result = result.toUpperCase();
        }
        return  result;
    }

    /**
     * MD5加密算法
     * @param plainText
     * @return
     */
    private static String core(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    public static void main(String[] args) {
//        System.out.println(encryption("123",32, true));
//        System.out.println(encryption("123", 32, false));
//        System.out.println(encryption("123", 16, true));
//        System.out.println(encryption("123", 16, false));

        System.out.println(encryption("01149530cace00936d7c5af2196a",32,true));
    }
}

