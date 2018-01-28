package org.biacode.hermes.spring.netty.config

import org.biacode.hermes.spring.netty.config.annotation.NettyCommand
import org.biacode.hermes.spring.netty.model.NettyControllerMethodRoute
import org.biacode.hermes.spring.netty.model.NettyControllerRequest
import org.biacode.hermes.spring.netty.service.ControllerMethodRouteRegistryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.util.ClassUtils

/**
 * Created by Arthur Asatryan.
 * Date: 1/27/18
 * Time: 8:56 PM
 */
@Configuration
class CommandRouteGeneratorBeanFactoryPostProcessor : ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private lateinit var beanFactory: ConfigurableListableBeanFactory

    @Autowired
    private lateinit var controllerMethodRouteRegistryService: ControllerMethodRouteRegistryService

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val context = event.applicationContext
        val controllerMethodRouteWrapper = controllerMethodRouteRegistryService
        context.beanDefinitionNames.forEach { beanDefinitionName ->
            val beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName)
            val beanClassName = beanDefinition.beanClassName ?: return@forEach
            val originalClass = ClassUtils.resolveClassName(beanClassName, ClassLoader.getSystemClassLoader())
            for (originalMethod in originalClass.methods) {
                if (originalMethod.isAnnotationPresent(NettyCommand::class.java)) {
                    val beanObject = context.getBean(beanDefinitionName)
                    val beanMethod = beanObject.javaClass.getMethod(originalMethod.name, *originalMethod.parameterTypes)
                    val annotation = originalMethod.getAnnotation(NettyCommand::class.java)
                    for (parameterType in originalMethod.parameterTypes) {
                        val resolvedClass = ClassUtils.resolveClassName(
                                parameterType.name,
                                ClassLoader.getSystemClassLoader()
                        )
                        if (NettyControllerRequest::class.java.isAssignableFrom(resolvedClass)) {
                            controllerMethodRouteWrapper.addRoute(
                                    annotation.value,
                                    NettyControllerMethodRoute(beanMethod, resolvedClass, beanObject)
                            )
                            break
                        }
                    }
                }
            }
        }
    }
}

