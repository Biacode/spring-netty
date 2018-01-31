package org.biacode.hermes.spring.netty.server.configuration;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 4:11 PM
 */
@Configuration
public class SslContextConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SslContextConfiguration.class);

    //region Dependencies
    @Value("${spring.netty.ssl.enabled}")
    private boolean nettySsl;
    //endregion

    //region Public methods
    @Bean
    public SslContext sslContext() throws CertificateException, SSLException {
        if (nettySsl) {
            LOGGER.debug("Configuring SSL");
            final SelfSignedCertificate ssc = new SelfSignedCertificate();
            return SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            LOGGER.debug("SSL is disabled");
            return null;
        }
    }
    //endregion

}
