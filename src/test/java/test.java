import com.yan.fullpvp.libs.util.time.TimeParser;
import com.yan.fullpvp.libs.util.time.TimeUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class test {

    public static void main(String[] args) {
        System.out.println(TimeUtils.timeLeft(TimeParser.parseString("45d8h1m6s")));
    }

}
