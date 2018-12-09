package com.mcy.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mcy.daomain.User;
import com.mcy.exception.token.TokenCreateException;
import com.mcy.exception.token.TokenTimeoutException;
import com.mcy.exception.token.TokenVerifyOrParseFailException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    /**
     * 1.创建一个32-byte的密匙
     */
    private static final byte[] secret = "geiwodiangasfdjsikolkjikolkijswe".getBytes();


    //生成一个token
    private static String creatToken(Map<String,Object> payloadMap) throws JOSEException {

        //3.先建立一个头部Header
        /**
         * JWSHeader参数：1.加密算法法则,2.类型，3.。。。。。。。
         * 一般只需要传入加密算法法则就可以。
         * 这里则采用HS256
         *
         * JWSAlgorithm类里面有所有的加密算法法则，直接调用。
         */
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        //建立一个载荷Payload
        Payload payload = new Payload(new JSONObject(payloadMap));

        //将头部和载荷结合在一起
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        //建立一个密匙

        JWSSigner jwsSigner = new MACSigner(secret);

        //签名
        jwsObject.sign(jwsSigner);

        //生成token
        return jwsObject.serialize();
//        Date iatDate = new Date();
//        Calendar nowTime = Calendar.getInstance();
//        nowTime.add(Calendar.MINUTE,30);
//        Date expiresDate = nowTime.getTime();
//        String token = JWT.create().withHeader(payloadMap).withExpiresAt(expiresDate).withIssuedAt(iatDate).sign(Algorithm.HMAC256(secret));
//        return token;
    }
    //解析一个token
    private static Map<String,Object> validToken(String token) throws TokenVerifyOrParseFailException, TokenTimeoutException {

//        解析token

        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(token);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new TokenVerifyOrParseFailException("解析异常");
        }

        //获取到载荷
        Payload payload=jwsObject.getPayload();

        //建立一个解锁密匙
        JWSVerifier jwsVerifier = null;
        try {
            jwsVerifier = new MACVerifier(secret);
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new TokenVerifyOrParseFailException("解析异常");
        }

        Map<String, Object> resultMap = new HashMap<>();
        //判断token
        try {
            if (jwsObject.verify(jwsVerifier)) {
                JSONObject jsonObject = payload.toJSONObject();
                //判断token是否过期
                if (jsonObject.containsKey("exp")) {
                    Long expTime = Long.valueOf(jsonObject.get("exp").toString());
                    Long nowTime = new Date().getTime();
                    //判断是否过期
                    if (nowTime > expTime) {
                        //已经过期
                        throw new TokenTimeoutException("token过期");
                    }
                    else
                    {
                        for (String str :
                                jsonObject.keySet()) {
                            resultMap.put(str, jsonObject.get(str));
                        }
                    }
                }
                else
                {
                    throw new TokenVerifyOrParseFailException("验证失败");
                }
            }else {
                throw new TokenVerifyOrParseFailException("验证失败");
            }
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new TokenVerifyOrParseFailException("验证失败");
        }
        System.out.println(resultMap);
        return resultMap;

    }
    //生成token的业务逻辑
    public static String create(Map<String, Object> map) throws TokenCreateException {
        //过期时间
        map.put("exp", new Date().getTime() + 1000 * 60 * 60 * 24 * 3);
        String token = null;
        try {
            token = creatToken(map);
        } catch (JOSEException e) {
            throw new TokenCreateException("创建token失败");
        }
        return token;
    }

    //处理解析的业务逻辑
    public static Map<String, Object> valid(String token) throws TokenTimeoutException, TokenVerifyOrParseFailException {
        //解析token
        Map<String, Object> validMap = null;
        if (token != null) {

            validMap = TokenUtil.validToken(token);
        }
        else {
            throw new TokenVerifyOrParseFailException();
        }
        return validMap;
    }
    @Test
    public  void test() throws TokenTimeoutException, TokenVerifyOrParseFailException, TokenCreateException {
        User user = new User();
        user.setTel("15136010213");
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        String token = create(map);
        Map<String,Object> map1 = valid(token);
        System.out.println(map1);
    }
}
