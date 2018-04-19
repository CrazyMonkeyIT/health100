package com.valueservice.djs.bean;


public class CommonConst {

    /** 默认为第一页 */
    public static int DEFAULT_PAGE_NUM = 1;
    /** 默认每页数量为10 */
    public static int DEFAULT_PAGE_SIZE = 10;

    /** 生效 */
    public static int VALID_VALUE = 1;
    /** 失效 */
    public static int INVALID_VALUE = 0;

    /** 广告正常 */
    public static int ADVERTISEMENT_STATUS_NORMAL = 1;
    /** 广告到期 */
    public static int ADVERTISEMENT_STATUS_EXPIRE = -1;
    /** 广告人工暂停投放 */
    public static int ADVERTISEMENT_STATUS_STOP = -2;

    /**
     * 小程序的appid
     */
    public static final String MINI_PROGRAM_APPID = "wx25a7786747557f5e";

    /**
     * 小程序的app secret
     */
    public static final String MINI_PROGRAM_APPSECRET = "b39a61cbb66ab1766e75edbf7e5209f2";

    /**
     * 小程序的grantType
     */
    public static final String MINI_PORGRAM_GRANT_TYPE = "authorization_code";

    /**
     * 小程序验证授权的URL
     */
    public static final String MINI_PROGRAM_AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session";
}
