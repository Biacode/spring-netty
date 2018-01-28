package org.biacode.hermes.spring.netty.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 2:50 PM
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:context.xml")
public class NettyControllerTest {

    public NettyControllerTest() {
    }

    @Test
    public void testCtx() {
    }

}
