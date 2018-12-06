package com.mcy.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class SCaptcha {
    private int width = 120;
    private int height = 40;
    //验证码个数
    private int codeCount = 4;
    //干扰线条数
    private int lineCount = 50;
    private String codeFont = "Times New Roman";
    private String code = null;
    private BufferedImage buffImg = null;
    private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'M', 'N', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
    private Random random = new Random();
    public SCaptcha()
    {
        this.createCode();
    }

    /**
     * 构造器
     * @param width
     * @param height
     */
    public SCaptcha(int width,int height)
    {
        this.width = width;
        this.height = height;
        this.createCode();
    }

    /**
     * @param width
     * @param height
     * @param codeCount
     * @param lineCount
     */
    public SCaptcha(int width, int height, int codeCount, int lineCount,String codeFont) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.codeFont = codeFont;
        this.createCode();
    }
    private void createCode()
    {
        //设置字符位置变量
        int codeX = 0;
        codeX = width / (codeCount + 2);
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffImg.createGraphics();
        g.setColor(getRandomColor(200,255));
        g.fillRect(0,0,width,height);
        //设置字体
        g.setFont(new Font(codeFont, Font.PLAIN, width / codeCount));
        //图片上划线
        g.setColor(getRandomColor(160, 200));
        for (int i = 0; i < lineCount; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        StringBuffer randomCode = new StringBuffer();
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 设置字体颜色
            g.setColor(getRandomColor(0,200));
            // 设置字体位置
            g.drawString(strRand, (i + 1) * codeX, getRandomNumber(height / 4) + height * 3/4);
            randomCode.append(strRand);
        }
        code = randomCode.toString();
        g.dispose();
    }
    //随机颜色
    private Color getRandomColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
    //随机数
    private int getRandomNumber(int number) {
        return random.nextInt(number);
    }
    //输出图片验证码
    public void write(OutputStream sos, boolean isOrNotClose) throws IOException {
        ImageIO.write(buffImg, "png", sos);
        if (isOrNotClose == false)
        {
            sos.close();
        }
    }
    public  String getCode()
    {
        return code;
    }
}

