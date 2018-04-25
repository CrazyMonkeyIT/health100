package com.valueservice.djs.db.bean;

/**
 * 微信接口调用凭证的实体
 * @author Myron
 * @time 2016-9-19 14:58:45
 */
public class WxAccessToken {

	// 获取到的凭证
    private String token;
    // 凭证有效时间，单位：秒
    private int expiresIn;
    //	下次获取时间
    private long nextGetTime;
    
    private String appid; 
    
    private String appsecret;
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public long getNextGetTime() {
		return nextGetTime;
	}
	public void setNextGetTime(long nextGetTime) {
		this.nextGetTime = nextGetTime;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
}
