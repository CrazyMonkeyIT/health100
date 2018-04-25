package com.valueservice.djs.db.bean;


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
    public static final String MINI_PROGRAM_APPID = "wx5698cb3117a1dffe";

    /**
     * 小程序的app secret
     */
    public static final String MINI_PROGRAM_APPSECRET = "425dde9e9fb5afd012d8cf0e7f97419d";

    /**
     * 小程序的grantType
     */
    public static final String MINI_PORGRAM_GRANT_TYPE = "authorization_code";

    /**
     * 小程序验证授权的URL
     */
    public static final String MINI_PROGRAM_AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session";
}
