import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.runtime.directive.contrib.For;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@SpringBootTest
@Slf4j
public class base {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        String x = "{\"armStepState\":1,\"armXState\":1,\"armYState\":1,\"armZState\":1,\"cabin1State\":1,\"cabin2State\":1,\"liftState\":1,\"signStepAngle\":1,\"signStepState\":1,\"squareXState\":1,\"squareY1State\":1,\"squareY2State\":1,\"turnState\":1}";
        redisTemplate.boundValueOps("/f5a196d2-4cdc-41d3-aea1-c2cb13604119/status/motor").set(JSON.parseObject(x));
    }

    @Test
    public void test2() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "a");
        hashMap.put("2", "b");

    }

    @Test
    public void test3() {
        long startTime = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime minTime = localDateTime.with(LocalTime.MIN);
        LocalDateTime maxTime = localDateTime.with(LocalTime.MAX);
        log.info(String.valueOf(minTime));
        log.info(String.valueOf(maxTime));
        long endTime = System.currentTimeMillis();
        double elapsedTime = (endTime - startTime) / 1000.0;
        log.info("每日统计缺陷 执行时间为 {} 秒", elapsedTime);
    }

    public void printEvenNumbers(int n) {
        System.out.println(n);
    }

    @Test
    public void test4() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        log.info("numbers is :  {} ", numbers);

        System.out.println(numbers);
        numbers.stream()
                .filter(n -> n % 2 == 0)
                .forEach(this::printEvenNumbers);

    }

    @Test
    void test5() {
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("localDateTime is {}", localDateTime);
    }

    @Test
    void test6() {
        DecimalFormat df = new DecimalFormat("0.00000000");//格式化小数
        Double aDouble = Double.valueOf(df.format((double) 1 / 3));
        log.info("aDouble is {}", aDouble);
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Test
    void test7() {
        String theDate = formatter.format(LocalDateTime.now());
        log.info("theDate is {}", theDate);
    }

    @Test
    void test8() {
        Map<String, String> mapImg = new HashMap<>();
        mapImg.put("remotePath", "/违法/20230413/SGG/");
        mapImg.put("fileName", "239.jpg");
        mapImg.put("filePath", "F:/files/event-push/20230413/uav_vio_239.jpg");
        if (!mapImg.isEmpty()) {
            log.info("hhhh");
        }
    }
}

