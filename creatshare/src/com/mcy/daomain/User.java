package com.mcy.daomain;


import com.mcy.utils.PatternMatcherUtil;

import java.io.Serializable;

public class User implements Serializable {
    //必须
    private String tel;//手机
    private String password;//密码最大16
    private String nickname;//昵称
    private String headPortrait;//头像
    private Boolean identity = false;//是否是管理员
    //辅助


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Boolean getIdentity() {
        return identity;
    }

    public void setIdentity(Boolean identity) {
        this.identity = identity;
    }
    //判断属性格式 tel password nickname 是否符合格式要求
    public boolean judgeAttributeFormat()
    {
        String regExTel = "1\\d{10}";
        if(tel != null && password != null && tel.length() == 11 && password.length() <= 16 && password.length() >= 6 && PatternMatcherUtil.judgeMatches(tel,regExTel))
        return true;
        else
        return false;
    }
    public boolean judgeTelFormat()
    {
        String regExTel = "1\\d{10}";
        if(tel != null && tel.length() == 11 && PatternMatcherUtil.judgeMatches(tel,regExTel))
            return true;
        else
            return false;
    }
    public boolean judgeTelFormat(String tel)
    {
        String regExTel = "1\\d{10}";
        if(tel != null && tel.length() == 11 && PatternMatcherUtil.judgeMatches(tel,regExTel))
            return true;
        else
            return false;
    }
    @Override
    public String toString() {
        return "USer [tel=" + tel + ", password=" + password + ", nickname=" + nickname
                + ", headPortrait=" + headPortrait + ", identity=" + identity + "]";
    }

    public static void main(String[] args) {
        User user = new User();
        user.setTel("15136010213");
        user.setPassword("123456");
        System.out.println(user.judgeAttributeFormat());
    }
}
