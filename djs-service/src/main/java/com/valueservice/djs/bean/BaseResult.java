package com.valueservice.djs.bean;

public class BaseResult {
	
	private boolean result;
	
	private String message;

	private Object obj;

	public BaseResult(){
		this.result = false;
		this.message = "系统错误";
	}

	public BaseResult(boolean success){
		this.result = success;
	}

	public BaseResult(boolean result, String msg){
		this.result = result;
		this.message = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
