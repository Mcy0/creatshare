package com.mcy;

import com.mcy.utils.PatternMatcherUtil;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Test {
    public static void main(String[] args) throws IOException {
        String regEx = "\\d+-\\d+-\\d+ \\d+:\\d+";
        System.out.println(PatternMatcherUtil.judgeMatches("2018-11-2 22:22",regEx));
    }
}
