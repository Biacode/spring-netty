package org.biacode.spring.netty.router;

import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.biacode.spring.netty.core.annotation.NettyCommand;
import org.biacode.spring.netty.core.annotation.NettyContext;
import org.biacode.spring.netty.core.annotation.NettyController;
import org.biacode.spring.netty.core.annotation.NettyRequest;
import org.biacode.spring.netty.core.registry.ControllerMethodRouteRegistry;
import org.biacode.spring.netty.core.registry.model.NettyControllerMethodRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static java.util.Arrays.stream;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 1:52 PM
 */
@Configuration
public class NettyRouteGeneratorBeanFactoryPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRouteGeneratorBeanFactoryPostProcessor.class);

    //region Dependencies
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Autowired
    private ControllerMethodRouteRegistry registry;
    //endregion

    //region Public methods
    @SuppressWarnings({"squid:S3776"})
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        LOGGER.debug("Starting to generate command routes.");
        final ApplicationContext context = event.getApplicationContext();
        stream(context.getBeanDefinitionNames()).parallel().forEach(beanDefinitionName -> {
            final BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            final String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName == null) {
                return;
            }
            final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            final Class<?> aClass = ClassUtils.resolveClassName(beanClassName, systemClassLoader);
            if (aClass.isAnnotationPresent(NettyController.class)) {
                stream(aClass.getMethods())
                        .filter(it -> it.isAnnotationPresent(NettyCommand.class))
                        .forEach(originalMethod -> {
                            LOGGER.debug("Staring to generate route for method - {}", originalMethod.getName());
                            final Object beanObject = context.getBean(beanDefinitionName);
                            final Method beanMethod = ClassUtils.getMethod(beanObject.getClass(), originalMethod.getName(), originalMethod.getParameterTypes());
                            final NettyCommand annotation = originalMethod.getAnnotation(NettyCommand.class);
                            // If method does not contain any parameter
                            if (originalMethod.getParameterCount() == 0) {
                                LOGGER.debug("Method - {} does not contain any parameter", originalMethod.getName());
                                registry.addRoute(new NettyControllerMethodRoute(annotation.value(), beanMethod, beanObject));
                                return;
                            }
                            // If method contains parameter annotated with NettyRequest and/or NettyContext
                            final Annotation[][] parameterAnnotations = originalMethod.getParameterAnnotations();
                            final Class<?>[] parameterTypes = originalMethod.getParameterTypes();
                            final Mutable<Class<?>> resolvedClassMutable = new MutableObject<>();
                            final Mutable<Boolean> contextNeededMutable = new MutableObject<>(false);
                            for (int i = 0; i < parameterAnnotations.length; i++) {
                                if (stream(parameterAnnotations[i]).anyMatch(it -> it.annotationType().equals(NettyRequest.class))) {
                                    final String parameterTypeName = parameterTypes[i].getName();
                                    final Class<?> resolvedClass = ClassUtils.resolveClassName(parameterTypeName, systemClassLoader);
                                    LOGGER.debug(
                                            "Method - {} contains parameter - {} annotated with - {}",
                                            originalMethod.getName(),
                                            parameterTypeName,
                                            NettyRequest.class.getName()
                                    );
                                    resolvedClassMutable.setValue(resolvedClass);
                                }
                                if (stream(parameterAnnotations[i]).anyMatch(it -> it.annotationType().equals(NettyContext.class))) {
                                    contextNeededMutable.setValue(true);
                                }
                            }
                            if (resolvedClassMutable.getValue() != null) {
                                registry.addRoute(new NettyControllerMethodRoute(
                                        annotation.value(),
                                        beanMethod,
                                        beanObject,
                                        resolvedClassMutable.getValue(),
                                        contextNeededMutable.getValue()
                                ));
                            } else if (contextNeededMutable.getValue()) {
                                registry.addRoute(new NettyControllerMethodRoute(
                                        annotation.value(),
                                        beanMethod,
                                        beanObject,
                                        contextNeededMutable.getValue()
                                ));
                            }
                        });
            }
        });
    }
    //endregion
}
