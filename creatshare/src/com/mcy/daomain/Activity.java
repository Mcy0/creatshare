package com.mcy.daomain;

import com.mcy.utils.PatternMatcherUtil;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity {
    private String tel;
    private int type = 0;
    private String theme;
    private String place;
    private String info;
    private String src;
    private String star;
    private String end;

    public boolean judgeAllFormat()
    {
        String regExTel = "1\\d{10}";
        String regExDate = "\\d+-\\d+-\\d+ \\d+:\\d+";
        if (tel == null || type == 0 || theme == null || place == null || place == null || info == null || star == null || end == null)
        {
            return false;
        }
        else if (tel.length() != 11 || !PatternMatcherUtil.judgeMatches(tel,regExTel) || !PatternMatcherUtil.judgeMatches(star,regExDate) || !PatternMatcherUtil.judgeMatches(star,regExDate))
        {
            return false;
        }
        else if (theme.length() <= 0 || place.length() <= 0 || place.length() <= 0 || info.length() <= 0)
        {
            return false;
        }
        return true;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Activity[tel:" + tel + "]";
    }

    @Test
    public void test() throws SQLException, ParseException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd HH:mm");//2018-12-06 19:29:54
        System.out.println(format.parse("2018-12-06 19:29"));
        Activity activity = new Activity();
        activity.tel = "15136010213";
        activity.type=1;
        activity.theme="a";
        activity.place="a";
        activity.info="a";
//        activity.star=new Date();//format.format(new Date());
//        activity.end=new Date();//format.format(new Date());
        System.out.println(activity.judgeAllFormat());
//        String sql = "insert into activity values(?,?,?,?,?,?,?,?)";
//        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
//        runner.update(sql,activity.tel,activity.type,activity.theme,activity.place,activity.info,activity.src,activity.star,activity.end);

    }
}
