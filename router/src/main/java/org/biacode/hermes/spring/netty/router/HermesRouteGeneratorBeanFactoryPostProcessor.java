package org.biacode.hermes.spring.netty.router;

import org.biacode.hermes.spring.netty.core.annotation.HermesCommand;
import org.biacode.hermes.spring.netty.core.annotation.HermesRequest;
import org.biacode.hermes.spring.netty.core.registry.ControllerMethodRouteRegistry;
import org.biacode.hermes.spring.netty.core.registry.model.HermesControllerMethodRoute;
import org.biacode.hermes.spring.netty.core.request.HermesControllerRequest;
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
public class HermesRouteGeneratorBeanFactoryPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HermesRouteGeneratorBeanFactoryPostProcessor.class);

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
            stream(ClassUtils.resolveClassName(beanClassName, systemClassLoader).getMethods())
                    .filter(it -> it.isAnnotationPresent(HermesCommand.class))
                    .forEach(originalMethod -> {
                        LOGGER.debug("Staring to generate route for method - {}", originalMethod.getName());
                        final Object beanObject = context.getBean(beanDefinitionName);
                        final Method beanMethod = ClassUtils.getMethod(beanObject.getClass(), originalMethod.getName(), originalMethod.getParameterTypes());
                        final HermesCommand annotation = originalMethod.getAnnotation(HermesCommand.class);
                        // If method contains exactly one parameter
                        if (originalMethod.getParameterCount() == 1) {
                            final String parameterTypeName = originalMethod.getParameterTypes()[0].getName();
                            final Class<?> resolvedClass = ClassUtils.resolveClassName(parameterTypeName, systemClassLoader);
                            LOGGER.debug(
                                    "Method - {} contains exactly one parameter - {}",
                                    originalMethod.getName(),
                                    parameterTypeName
                            );
                            registry.addRoute(new HermesControllerMethodRoute(annotation.value(), beanMethod, beanObject, resolvedClass));
                            return;
                        }
                        // If method contains parameter annotated with NettyRequest
                        final Annotation[][] parameterAnnotations = originalMethod.getParameterAnnotations();
                        final Class<?>[] parameterTypes = originalMethod.getParameterTypes();
                        for (int i = 0; i < parameterAnnotations.length; i++) {
                            if (stream(parameterAnnotations[i]).anyMatch(it -> it.annotationType().equals(HermesRequest.class))) {
                                final String parameterTypeName = parameterTypes[i].getName();
                                final Class<?> resolvedClass = ClassUtils.resolveClassName(parameterTypeName, systemClassLoader);
                                LOGGER.debug(
                                        "Method - {} contains parameter - {} annotated with - {}",
                                        originalMethod.getName(),
                                        parameterTypeName,
                                        HermesRequest.class.getName()
                                );
                                registry.addRoute(new HermesControllerMethodRoute(annotation.value(), beanMethod, beanObject, resolvedClass));
                                return;
                            }
                        }
                        // If method contains parameter which is instance of NettyControllerRequest
                        for (final Class<?> parameterType : originalMethod.getParameterTypes()) {
                            final String parameterTypeName = parameterType.getName();
                            final Class<?> resolvedClass = ClassUtils.resolveClassName(parameterType.getName(), systemClassLoader);
                            if (HermesControllerRequest.class.isAssignableFrom(resolvedClass)) {
                                LOGGER.debug(
                                        "Method - {} contains parameter - {} which is instance of - {}",
                                        originalMethod.getName(),
                                        parameterTypeName,
                                        HermesControllerRequest.class.getName()
                                );
                                registry.addRoute(new HermesControllerMethodRoute(annotation.value(), beanMethod, beanObject, resolvedClass));
                                return;
                            }
                        }
                    });
        });
    }
    //endregion
}
