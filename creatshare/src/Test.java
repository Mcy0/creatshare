
import com.mcy.utils.PatternMatcherUtil;
import com.mcy.utils.PostNodeUtil;

import javax.servlet.http.Cookie;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        String tel = "15136010213";
        String password = "123456";
        String regExTel = "1\\d{10}";
        if(tel.length() == 11 && password.length() <= 16 && password.length() >= 6 && PatternMatcherUtil.judgeMatches(tel,regExTel))
            System.out.println(true);
        else
            System.out.println(false);
    }
}
