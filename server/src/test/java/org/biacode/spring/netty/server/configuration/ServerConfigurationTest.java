package org.biacode.spring.netty.server.configuration;

import io.netty.channel.Channel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Arthur Asatryan.
 * Date: 2/10/18
 * Time: 5:50 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ServerConfigurationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfigurationTest.class);

    //region Test methods
    @Test
    public void testStartNettyServer() throws InterruptedException {
        LOGGER.debug("Initializing application context");
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ServerAnnotationDrivenConfiguration.class
        );
        final ServerConfiguration serverConfiguration = applicationContext.getBean(ServerConfiguration.class);
        final Channel channel = serverConfiguration.startedServer();
        assertThat(channel).isNotNull();
    }
    //endregion

}