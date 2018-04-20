package com.valueservice.djs.util;

import com.google.gson.Gson;

public class UnicornUtil {

    private final static Gson gson = new Gson();

    public static String toJson(Object object){
        return gson.toJson(object);
    }

}
