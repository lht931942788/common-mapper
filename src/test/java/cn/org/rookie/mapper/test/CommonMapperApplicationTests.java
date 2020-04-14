package cn.org.rookie.mapper.test;

import cn.org.rookie.mapper.mapper.DemoMapper;
import cn.org.rookie.mapper.sql.where.Wrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommonMapperApplicationTests {

    @Autowired
    DemoMapper demoMapper;

    @Test
    public void contextLoads() throws InterruptedException {
        demoMapper.selectOne(Wrapper.build().in("username", new Object[]{"ceshi"}));
    }

    @Test
    public void demo() {
        binSearch(new int[]{1, 4, 7, 8, 9, 15, 16, 18, 20}, 4);
    }

    public void binSearch(int[] srcArray, int key) {
        int mid = srcArray.length / 2;
        if (key == srcArray[mid]) {
            System.out.println(mid);
        }
        int start = 0;
        int end = srcArray.length - 1;
        while (start <= end) {
            mid = (end - start) / 2 + start;
            if (key < srcArray[mid]) {
                end = mid - 1;
            } else if (key > srcArray[mid]) {
                start = mid + 1;
            } else {
                System.out.println(mid);
                return;
            }
        }
        System.out.println("mei");
    }

}
