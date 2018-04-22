package com.valueservice.djs.result;

public class BaseResult {

    private boolean result; //结果

    private String message; //消息

    public BaseResult(){
        this.result = false;
        this.message = "系统错误";
    }

    public boolean isResult() {
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
