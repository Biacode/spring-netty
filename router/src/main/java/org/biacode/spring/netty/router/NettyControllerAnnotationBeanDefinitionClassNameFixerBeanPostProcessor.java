package org.biacode.spring.netty.router;

import org.biacode.spring.netty.core.annotation.NettyController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 3:54 PM
 * <p>
 * This BPP will fix bean definition class name nullability issue for classes
 * which are annotated with NettyController.
 */
@Configuration
public class NettyControllerAnnotationBeanDefinitionClassNameFixerBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyControllerAnnotationBeanDefinitionClassNameFixerBeanPostProcessor.class);

    //region Dependencies
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;
    //endregion

    //region Public methods
    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(NettyController.class)) {
            final BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            if (beanDefinition.getBeanClassName() == null) {
                final String canonicalName = bean.getClass().getCanonicalName();
                LOGGER.debug("Fixing bean definition - {} class name - {}", beanDefinition, canonicalName);
                beanDefinition.setBeanClassName(canonicalName);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }
    //endregion

}
