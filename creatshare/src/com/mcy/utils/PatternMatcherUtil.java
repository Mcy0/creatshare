package com.mcy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//正则表达式，通用判断格式
public class PatternMatcherUtil {
    //字符串是否与正则表达式相匹配
    public static boolean judgeMatches(String str, String regEx)
    {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
