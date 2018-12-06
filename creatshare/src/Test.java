
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        String string = "aa=a=aa=aaa";
        System.out.println(string.split("=").length);
    }
}
