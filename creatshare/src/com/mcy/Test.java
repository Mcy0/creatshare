package com.mcy;

import com.mcy.utils.PatternMatcherUtil;
import sun.misc.BASE64Decoder;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Test {
    public static void main(String[] args) throws IOException {
        String photo = "http://132.232.119.121/creatshare/photo?name=15829467352MCY1.jpg&flag=activity";
        String nameStar = photo.substring(photo.indexOf("name=") + 5,photo.indexOf("MCY") + 3);
        String nameEnd = photo.substring(photo.indexOf("MCY") + 4,photo.indexOf("MCY") + 8);
        System.out.println(nameStar + nameEnd);
    }
}
