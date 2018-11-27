package com.mcy.daomain;


public class User {
    //必须
    private String tel;//手机
    private String password;//密码最大16
    private String nickname;//昵称
    private String headPortrait;//头像
    private Boolean identity;//是否是管理员
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

        return true;
    }
}
