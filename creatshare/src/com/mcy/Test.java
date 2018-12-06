package com.mcy;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Test {
    public static void main(String[] args) throws IOException {
        String photo = "data:image/jpeg;base64,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\n";
        System.out.println(photo.substring(photo.indexOf("/") + 1,photo.indexOf(";")));
        photo = photo.substring(photo.indexOf("64") + 3);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(photo);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        OutputStream outputStream = new FileOutputStream("/home/mcy/idea/creatshare/creatshare/photo/15136010213.jpeg");
        outputStream.write(b);
        outputStream.flush();
        outputStream.close();
    }
}
