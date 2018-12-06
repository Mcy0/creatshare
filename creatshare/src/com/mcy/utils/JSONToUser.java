package com.mcy.utils;

import com.mcy.daomain.User;
import net.minidev.json.JSONObject;

public class JSONToUser {
    public static User getUser(JSONObject jsonObject)
    {
        User user = new User();
        user.setTel((String) jsonObject.get("tel"));
        user.setPassword((String) jsonObject.get("password"));
        user.setNickname((String) jsonObject.get("nickname"));
        user.setHeadPortrait((String) jsonObject.get("headPortrait"));
//        user.setIdentity(Boolean.valueOf((String)jsonObject.get("identity")) );
        user.setIdentity((Boolean) jsonObject.get("identity"));
        return user;
    }
}
