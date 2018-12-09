package com.mcy.utils;

import com.mcy.daomain.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonStringUtil {
    /**
     *
     * @param code
     * @param object
     * @param token
     * @return
     */
    public static String success(String code, Object object, String token)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("Authentication",token);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(object);
        jsonObject.put("data",jsonArray.get(0));
        return jsonObject.toString();
    }
    public static String success(String code, Object object)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(object);
        jsonObject.put("data",jsonArray);
        return jsonObject.toString();
    }
    public static String success(String code, String token)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("Authentication",token);
        return jsonObject.toString();
    }
    public static String success(String code, String sucess,boolean b)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("success",sucess);
        return jsonObject.toString();
    }
    /**
     *
     * @param code
     * @param msg
     * @return
     */
    public static String fail(String code, String msg)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        return jsonObject.toString();
    }
}
